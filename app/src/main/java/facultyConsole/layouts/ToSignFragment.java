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

import net.ApiClient;
import net.ApiInterface;

import db.FacultyToSignPasses;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import models.OutPassModel;

import java.util.List;

import javax.annotation.Nullable;

import facultyConsole.adapters.ToSignAdapter;
import in.ac.iilm.iilm.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.UserInformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToSignFragment extends Fragment {

    private static final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private static final int limit = 500;
    private static Call<List<FacultyToSignPasses>> call;
    private static boolean lastCallFinished = true;
    private static int offset = 0;
    private RecyclerView recyclerView;
    private ToSignAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private RealmResults<FacultyToSignPasses> facultyToSignPasses;
    private Realm realm = Realm.getDefaultInstance();


    public ToSignFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facultyToSignPasses = realm.where(FacultyToSignPasses.class).findAllAsync();
        facultyToSignPasses.addChangeListener((facultyToSignPasses, changeSet) -> adapter.notifyDataSetChanged());
        adapter = new ToSignAdapter(getContext(), facultyToSignPasses);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_faculty_to_sign, container, false);

        recyclerView = view.findViewById(R.id.rv_console);
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

        refreshLayout = view.findViewById(R.id.srl_console);
        refreshLayout.setOnRefreshListener(() -> getOutpasses(true));
        getOutpasses(true);

        return view;
    }

    @Override
    public void onPause() {
        refreshLayout.setRefreshing(false);
        refreshLayout.clearAnimation();
        super.onPause();
    }

    private void getOutpasses(boolean refresh) {
        if (!refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
        }
        if (call == null || lastCallFinished) {

            if (refresh) offset = 0;
            else offset = adapter.getItemCount();

            call = apiInterface.getOutPassesUnsigned(
                    UserInformation.getString(getContext(), UserInformation.StringKey.TOKEN),
                    true,
                    offset,
                    limit
            );

            lastCallFinished = false;

            call.enqueue(new Callback<List<FacultyToSignPasses>>() {
                @Override
                public void onResponse(Call<List<FacultyToSignPasses>> call, Response<List<FacultyToSignPasses>> response) {
                    realm.executeTransactionAsync(realm -> {
                        if (offset == 0) {
                            realm.where(FacultyToSignPasses.class).findAll().deleteAllFromRealm();
                        }
                        realm.copyToRealm(response.body());
                        finishCall();
                    });
                }

                @Override
                public void onFailure(Call<List<FacultyToSignPasses>> call, Throwable t) {
                    finishCall();
                    if (getActivity() != null)
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
    public void onDestroy() {
        call.cancel();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        getOutpasses(true);
        super.onResume();
    }
}
