/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package facultyConsole.layouts;


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

import net.models.OutPassModel;
import net.requests.GetSignedPassesRequest;

import java.util.List;

import db.CrudOutPassSigned;
import db.DbHelper;
import facultyConsole.adapters.HistoryAdapter;
import in.ac.iilm.iilm.R;
import utils.UserInformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private static final int limit = 100;
    private static boolean lastCallFinished = true;
    private static int offset = 0;
    private HistoryAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private CrudOutPassSigned crud;
    private DbHelper dbHelper;
    private GetSignedPassesRequest request;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = new GetSignedPassesRequest();
        dbHelper = new DbHelper(getContext());
        crud = new CrudOutPassSigned(getContext(), dbHelper);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_faculty_history,
                container,
                false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_history);
        adapter = new HistoryAdapter(
                getContext(),
                crud.getOutPassesSigned(),
                UserInformation.getString(
                        getContext(),
                        UserInformation.StringKey.SCOPE));
        recyclerView.setAdapter(adapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1)
                    getOutPasses(false);
            }
        });

        refreshLayout = view.findViewById(R.id.srl_history);
        refreshLayout.setOnRefreshListener(() -> getOutPasses(true));
        getOutPasses(true);
        return view;
    }

    private void getOutPasses(boolean refresh) {
        if (!refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
        }
        if (lastCallFinished) {

            /*
              set offset to '0' when refreshing so that latest data is retrieved otherwise
              set it to the number of already present elements so that data after the preexisting
              data is retrieved
              */
            if (refresh) offset = 0;
            else offset = adapter.getItemCount();

            request.execute(
                    getContext(),
                    offset,
                    limit,
                    (response) -> {
                        if (response != null) {
                            if (offset == 0) {
                                crud.deleteAllAndAdd(response.body(), this::updateData);
                            } else {
                                crud.addOrUpdateOutPass(response.body(), this::updateData);
                            }
                        } else {
                            if (getActivity() != null) {
                                Toast.makeText(getContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                            }
                            refreshLayout.setRefreshing(false);
                            lastCallFinished = true;
                        }
                    }
            );

            lastCallFinished = false;


        }
    }

    private void updateData(List<OutPassModel> outPasses) {
        Activity activity = getActivity();
        if (activity != null) {
            getActivity().runOnUiThread(() -> {
                adapter.updateDataSet(outPasses);
                refreshLayout.setRefreshing(false);
                lastCallFinished = true;
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
        request.cancel();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        getOutPasses(false);
        super.onResume();
    }
}
