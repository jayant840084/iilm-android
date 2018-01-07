package studentConsole.layouts;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.ApiClient;
import net.ApiInterface;
import net.models.OutPassModel;

import java.util.List;

import db.OutPassCrud;
import db.OutPassDbHelper;
import in.ac.iilm.iilm.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studentConsole.adapters.HistoryAdapter;
import utils.ActivityTracker;
import utils.UserInformation;

public class HistoryFragment extends Fragment {

    private static final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private static final int limit = 100;
    private static Call<List<OutPassModel>> call;
    private static boolean lastCallFinished = true;
    private static int offset = 0;
    private HistoryAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private OutPassCrud crud;
    private OutPassDbHelper dbHelper;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new OutPassDbHelper(getContext());
        crud = new OutPassCrud(getContext(), dbHelper);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_history);
        adapter = new HistoryAdapter(crud.getOutpasses());
        recyclerView.setAdapter(adapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1)
                    getOutpasses(false);
            }
        });

        refreshLayout = view.findViewById(R.id.srl_history);
        refreshLayout.setOnRefreshListener(() -> getOutpasses(true));
        getOutpasses(true);
        return view;
    }

    private void getOutpasses(boolean refresh) {
        if (!refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
        }
        if (call == null || lastCallFinished) {

            // change offset to zero. It will delete all old data and add just the latest data to the database.
            if (refresh) offset = 0;
            else offset = adapter.getItemCount();

            call = apiInterface.getOutPasses(
                    UserInformation.getString(getContext(), UserInformation.StringKey.TOKEN),
                    offset,
                    limit
            );

            lastCallFinished = false;

            call.enqueue(new Callback<List<OutPassModel>>() {
                @Override
                public void onResponse(Call<List<OutPassModel>> call,
                                       Response<List<OutPassModel>> response) {
                    if (offset == 0) {
                        crud.deleteAllAndAdd(response.body(), outpasses -> {
                            Activity activity = getActivity();
                            if (activity != null) {
                                activity.runOnUiThread(() -> {
                                    adapter.updateDataSet(outpasses);
                                    refreshLayout.setRefreshing(false);
                                    lastCallFinished = true;
                                });
                            }
                        });
                    } else {
                        crud.addOrUpdateOutapss(response.body(), outpasses -> {
                            Activity activity = getActivity();
                            if (activity != null) {
                                getActivity().runOnUiThread(() -> {
                                    adapter.updateDataSet(outpasses);
                                    refreshLayout.setRefreshing(false);
                                    lastCallFinished = true;
                                });
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<List<OutPassModel>> call, Throwable t) {
                    if (ActivityTracker.isActivityRunning() && refreshLayout.isRefreshing())
                        Toast.makeText(getContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                    refreshLayout.setRefreshing(false);
                    lastCallFinished = true;
                }
            });
        }
    }

    @Override
    public void onPause() {
        refreshLayout.setRefreshing(false);
        refreshLayout.clearAnimation();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        dbHelper.close();
        call.cancel();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        getOutpasses(false);
        super.onResume();
    }
}
