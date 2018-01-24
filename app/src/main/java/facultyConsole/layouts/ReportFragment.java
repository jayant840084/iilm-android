/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package facultyConsole.layouts;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import net.models.OutPassModel;
import net.requests.LeavingTodayReportRequest;
import net.requests.LeftTodayReportRequest;
import net.requests.ReturnedTodayReportRequest;
import net.requests.YetToReturnReportRequest;

import java.util.Arrays;
import java.util.List;

import constants.OutPassSource;
import constants.ReportTypes;
import db.CrudLeavingToday;
import db.CrudLeftToday;
import db.CrudReturnedToday;
import db.CrudYetToReturn;
import db.DbHelper;
import facultyConsole.adapters.ReportAdapter;
import in.ac.iilm.iilm.R;
import retrofit2.Response;


public class ReportFragment extends Fragment {

    private static final List<String> reportTypesList = Arrays.asList(
            ReportTypes.LEAVING_TODAY,
            ReportTypes.YET_TO_RETURN,
            ReportTypes.RETURNED_TODAY,
            ReportTypes.LEFT_TODAY
    );

    private SwipeRefreshLayout refreshLayout;
    private Spinner mSpinner;
    private CrudLeavingToday mCrudLeavingToday;
    private CrudLeftToday mCrudLeftToday;
    private CrudReturnedToday mCrudReturnedToday;
    private CrudYetToReturn mCrudYetToReturn;

    private ReportAdapter mReportAdapter;

    private LeavingTodayReportRequest leavingTodayReportRequest;
    private ReturnedTodayReportRequest returnedTodayReportRequest;
    private YetToReturnReportRequest yetToReturnReportRequest;
    private LeftTodayReportRequest leftTodayReportRequest;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbHelper dbHelper = new DbHelper(getContext());
        leavingTodayReportRequest = new LeavingTodayReportRequest();
        returnedTodayReportRequest = new ReturnedTodayReportRequest();
        yetToReturnReportRequest = new YetToReturnReportRequest();
        leftTodayReportRequest = new LeftTodayReportRequest();

        mCrudLeavingToday = new CrudLeavingToday(getContext(), dbHelper);
        mCrudLeftToday = new CrudLeftToday(getContext(), dbHelper);
        mCrudReturnedToday = new CrudReturnedToday(getContext(), dbHelper);
        mCrudYetToReturn = new CrudYetToReturn(getContext(), dbHelper);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_faculty_report, container, false);

        refreshLayout = view.findViewById(R.id.srl_report);
        refreshLayout.setOnRefreshListener(() -> getOutPasses(true));

        mSpinner = view.findViewById(R.id.report_spinner);

        ArrayAdapter adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                reportTypesList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getOutPasses(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mReportAdapter = new ReportAdapter(mCrudLeavingToday.getOutPasses(), getCurrentSource());

        RecyclerView mRecyclerView = view.findViewById(R.id.rv_report);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mReportAdapter);
        return view;
    }

    private void getOutPasses(boolean refresh) {
        // cancel all requests and then execute the new request
        if (refreshLayout.isRefreshing()) {
            /*
             * Check for null before cancelling
             */
//            leavingTodayReportRequest.cancel();
//            leftTodayReportRequest.cancel();
//            returnedTodayReportRequest.cancel();
//            leftTodayReportRequest.cancel();
        } else {
            refreshLayout.setRefreshing(true);
        }
        switch (mSpinner.getSelectedItem().toString()) {
            case ReportTypes.LEAVING_TODAY:
                showLeavingToday(refresh);
                break;
            case ReportTypes.YET_TO_RETURN:
                showYetToReturn(refresh);
                break;
            case ReportTypes.RETURNED_TODAY:
                returnedToday(refresh);
                break;
            case ReportTypes.LEFT_TODAY:
                leftToday(refresh);
                break;
        }
    }

    private void showLeavingToday(boolean refresh) {
        int offset, limit = 100;
        mReportAdapter.updateData(mCrudLeavingToday.getOutPasses(), getCurrentSource());
        if (refresh) offset = 0;
        else offset = mReportAdapter.getItemCount();

        leavingTodayReportRequest.execute(
                getContext(),
                offset,
                limit,
                new LeavingTodayReportRequest.Callback() {
                    @Override
                    public void onResponse(Response<List<OutPassModel>> response) {
                        if (response != null) {
                            if (offset == 0) {
                                mCrudLeavingToday.deleteAllAndAdd(response.body(),
                                        outPasses -> updateData(outPasses));
                            } else {
                                mCrudLeavingToday.addOrUpdateOutPass(response.body(),
                                        outPasses -> updateData(outPasses));
                            }
                        }
                    }

                    @Override
                    public void onFailure() {
                        refreshLayout.setRefreshing(false);
                        if (getActivity() != null)
                            Toast.makeText(getContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showYetToReturn(boolean refresh) {
        int offset, limit = 100;
        mReportAdapter.updateData(mCrudYetToReturn.getOutPasses(), getCurrentSource());
        if (refresh) offset = 0;
        else offset = mReportAdapter.getItemCount();

        yetToReturnReportRequest.execute(getContext(),
                offset,
                limit,
                new YetToReturnReportRequest.Callback() {
                    @Override
                    public void onResponse(Response<List<OutPassModel>> response) {
                        if (response != null) {
                            if (offset == 0) {
                                mCrudYetToReturn.deleteAllAndAdd(response.body(),
                                        outPasses -> updateData(outPasses));
                            } else {
                                mCrudYetToReturn.addOrUpdateOutPass(response.body(),
                                        outPasses -> updateData(outPasses));
                            }
                        }
                    }

                    @Override
                    public void onFailure() {
                        refreshLayout.setRefreshing(false);
                        if (getActivity() != null)
                            Toast.makeText(getContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void returnedToday(boolean refresh) {
        int offset, limit = 100;
        mReportAdapter.updateData(mCrudReturnedToday.getOutPasses(), getCurrentSource());
        if (refresh) offset = 0;
        else offset = mReportAdapter.getItemCount();

        returnedTodayReportRequest.execute(getContext(),
                offset,
                limit,
                new ReturnedTodayReportRequest.Callback() {
                    @Override
                    public void onResponse(Response<List<OutPassModel>> response) {
                        if (response != null) {
                            if (offset == 0) {
                                mCrudReturnedToday.deleteAllAndAdd(response.body(),
                                        outPasses -> updateData(outPasses));
                            } else {
                                mCrudReturnedToday.addOrUpdateOutPass(response.body(),
                                        outPasses -> updateData(outPasses));
                            }
                        }
                    }

                    @Override
                    public void onFailure() {
                        refreshLayout.setRefreshing(false);
                        if (getActivity() != null)
                            Toast.makeText(getContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void leftToday(boolean refresh) {
        int offset, limit = 100;
        mReportAdapter.updateData(mCrudLeftToday.getOutPasses(), getCurrentSource());
        if (refresh) offset = 0;
        else offset = mReportAdapter.getItemCount();

        leftTodayReportRequest.execute(getContext(),
                offset,
                limit,
                new LeftTodayReportRequest.Callback() {
                    @Override
                    public void onResponse(Response<List<OutPassModel>> response) {
                        if (response != null) {
                            if (offset == 0) {
                                mCrudLeftToday.deleteAllAndAdd(response.body(),
                                        outPasses -> updateData(outPasses));
                            } else {
                                mCrudLeftToday.addOrUpdateOutPass(response.body(),
                                        outPasses -> updateData(outPasses));
                            }
                        }
                    }

                    @Override
                    public void onFailure() {
                        refreshLayout.setRefreshing(false);
                        if (getActivity() != null)
                            Toast.makeText(getContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateData(List<OutPassModel> outPasses) {
        Activity activity = getActivity();
        if (activity != null) {
            getActivity().runOnUiThread(() -> {
                mReportAdapter.updateData(outPasses, getCurrentSource());
                refreshLayout.setRefreshing(false);
            });
        }
    }

    private int getCurrentSource() {
        switch (mSpinner.getSelectedItem().toString()) {
            case ReportTypes.LEAVING_TODAY:
                return OutPassSource.LEAVING_TODAY;
            case ReportTypes.LEFT_TODAY:
                return OutPassSource.LEFT_TODAY;
            case ReportTypes.RETURNED_TODAY:
                return OutPassSource.RETURNED_TODAY;
            case ReportTypes.YET_TO_RETURN:
                return OutPassSource.YET_TO_RETURN;
            default:
                Toast.makeText(getContext(), "Invalid selection", Toast.LENGTH_SHORT).show();
                return 0;
        }
    }
}
