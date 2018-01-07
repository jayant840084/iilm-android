package facultyConsole.layouts;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import db.OutPassDbHelper;
import db.ReportCrud;
import facultyConsole.adapters.ReportAdapter;
import in.ac.iilm.iilm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {

    private static final class ReportTypes{
        static final String LEAVING_TODAY = "Leaving Today";
        static final String YET_TO_RETURN = "Yet To Return";
    }

    private static final List<String> reportTypesList = Arrays.asList(
            ReportTypes.LEAVING_TODAY,
            ReportTypes.YET_TO_RETURN
    );

    private Spinner mSpinner;

    private ReportCrud mReportCrud;

    private RecyclerView mRecyclerView;
    private ReportAdapter mReportAdapter;
    private OutPassDbHelper dbHelper;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new OutPassDbHelper(getContext());
        mReportCrud = new ReportCrud(getContext(), dbHelper);
        mReportAdapter = new ReportAdapter(mReportCrud.getLeavingTodayOutPasses());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_faculty_report, container, false);

        mRecyclerView = view.findViewById(R.id.rv_report);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setAdapter(mReportAdapter);

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
                switch (adapterView.getSelectedItem().toString()) {
                    case ReportTypes.LEAVING_TODAY:
                        showLeavingToday();
                        break;
                    case ReportTypes.YET_TO_RETURN:
                        showYetToReturn();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }


    private void showLeavingToday() {
        mReportAdapter.updateDatasetChanged(mReportCrud.getLeavingTodayOutPasses());
    }


    private void showYetToReturn() {
        mReportAdapter.updateDatasetChanged(new LinkedList<>());
    }

}
