/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package studentConsole.layouts;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.ApiClient;
import net.ApiInterface;

import db.StudentHistory;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import models.OutPassModel;

import java.util.List;

import javax.annotation.Nullable;

import in.ac.iilm.iilm.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studentConsole.adapters.HistoryAdapter;
import utils.UserInformation;

public class HistoryFragment extends Fragment {

    private static final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private static final int limit = 100;
    private static Call<List<StudentHistory>> call;
    private static boolean lastCallFinished = true;
    private static int offset = 0;
    private HistoryAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private Realm realm = Realm.getDefaultInstance();
    private RealmResults<StudentHistory> studentHistoryRealmResults;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentHistoryRealmResults = realm.where(StudentHistory.class).findAllAsync();
        adapter = new HistoryAdapter(studentHistoryRealmResults);
        studentHistoryRealmResults.addChangeListener((studentHistories, changeSet)
                -> adapter.updateDataSet(studentHistories));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_history);
        recyclerView.setAdapter(adapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
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

            call.enqueue(new Callback<List<StudentHistory>>() {
                @Override
                public void onResponse(Call<List<StudentHistory>> call,
                                       Response<List<StudentHistory>> response) {
                    realm.executeTransactionAsync(realm -> {
                        if (offset == 0) {
                            realm.where(StudentHistory.class).findAll().deleteAllFromRealm();
                        }
                        realm.copyToRealmOrUpdate(response.body());
                        finishCall();
                    });
                }

                @Override
                public void onFailure(Call<List<StudentHistory>> call, Throwable t) {
                    finishCall();
                    if (getContext() != null && refreshLayout.isRefreshing())
                        Toast.makeText(getContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void finishCall() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
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
        call.cancel();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        getOutPasses(false);
        super.onResume();
    }
}
