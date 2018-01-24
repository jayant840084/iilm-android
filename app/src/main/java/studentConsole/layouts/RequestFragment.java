/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package studentConsole.layouts;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import net.ApiClient;
import net.ApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import constants.OutPassType;
import in.ac.iilm.iilm.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.ProgressBarUtil;
import utils.UserInformation;

/**
 * prevent dates of past from being selected.
 */

public class RequestFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String NOT_APPLICABLE = "Not Applicable";

    private int mYear, mMonth, mDay, mHour, mMinute;

    private EditText etPhoneNumber;
    private EditText etVisitingAddress;
    private EditText etReasonVisit;
    private EditText etStudentRemark;

    private String tempDateLeave;
    private String tempTimeLeave;
    private String tempDateReturn;
    private String tempTimeReturn;

    private long dateLeaveMilliSec;
    private long dateReturnMilliSec;

    private ProgressBarUtil mProgressBar;

    private Button timeOfLeave;
    private Button timeOfReturn;
    private Button dateOfReturn;
    private Button dateOfLeave;

    private Spinner mSpinner;

    private SimpleDateFormat dateFormat;

    public RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_student_request, container, false);

        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);
        etVisitingAddress = view.findViewById(R.id.etVisitingAddress);
        etReasonVisit = view.findViewById(R.id.etReasonLeave);
        etStudentRemark = view.findViewById(R.id.etStudentRemark);

        mProgressBar = new ProgressBarUtil(
                view.findViewById(R.id.outpass_request_progress_background),
                view.findViewById(R.id.outpass_request_progress)
        );
        mSpinner = view.findViewById(R.id.outpass_spinner);

        List<String> list = new LinkedList<>();
        list.add(OutPassType.DAY);
        list.add(OutPassType.DAY_COLLEGE_HOURS);
        list.add(OutPassType.NIGHT);
        list.add("Select Type");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                list) {

            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                return super.getView(position, convertView, parent);
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0);
        mSpinner.setOnItemSelectedListener(this);

        dateOfLeave = view.findViewById(R.id.btDateOfLeave);
        dateOfLeave.setOnClickListener(view13 -> {

            // get current date
            final Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);

            // launch time picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (datePicker, i, i1, i2) -> {
                tempDateLeave = i2 + "/" + (i1 + 1) + "/" + i;
                String dateLeaveString = "Leave Date: " + i2 + " - " + (i1 + 1) + " - " + i;
                dateOfLeave.setText(dateLeaveString);
                /*
                 *  not applicable since the leave date and return date are the same.
                 */
                if (mSpinner.getSelectedItem().toString().equals(OutPassType.DAY) ||
                        mSpinner.getSelectedItem().toString().equals(OutPassType.DAY_COLLEGE_HOURS))
                    dateOfReturn.setText(NOT_APPLICABLE);
            }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
        timeOfLeave = view.findViewById(R.id.btTimeOfLeave);
        timeOfLeave.setOnClickListener(view14 -> {

            // Get current time
            final Calendar calendar = Calendar.getInstance();
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);

            // launch time picker dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (timePicker, i, i1) -> {
                String timeLeaveString = "Leave Time: " + i + " : " + i1;
                timeOfLeave.setText(timeLeaveString);
                tempTimeLeave = i + ":" + i1;
            }, mHour, mMinute, false);
            timePickerDialog.show();
        });

        dateOfReturn = view.findViewById(R.id.btDateOfReturn);
        dateOfReturn.setOnClickListener(view15 -> {

            // get current date
            final Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);

            // launch date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (datePicker, i, i1, i2) -> {
                tempDateReturn = i2 + "/" + (i1 + 1) + "/" + i;
                String dateReturnString = "Return Date: " + i2 + " - " + (i1 + 1) + " - " + i;
                dateOfReturn.setText(dateReturnString);
            }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(dateLeaveMilliSec);
            datePickerDialog.show();
        });

        timeOfReturn = view.findViewById(R.id.btTimeOfReturn);
        timeOfReturn.setOnClickListener(view16 -> {

            // Get current time
            final Calendar calendar = Calendar.getInstance();
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);

            // launch time picker dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (timePicker, i, i1) -> {
                String timeReturnString = "Return Time: " + i + " : " + i1;
                timeOfReturn.setText(timeReturnString);
                tempTimeReturn = i + ":" + i1;
            }, mHour, mMinute, false);
            timePickerDialog.show();
        });


        final Button submit = view.findViewById(R.id.btSubmit);
        submit.setOnClickListener(view1 -> {

            boolean isDataValid = validateData();

            if (isDataValid) {
                try {
                    Date dateReturn;
                    Date dateLeave = dateFormat.parse(tempDateLeave + " " + tempTimeLeave);
                    if (isDayOutPass())
                        dateReturn = dateFormat.parse(tempDateLeave + " " + tempTimeReturn);
                    else
                        dateReturn = dateFormat.parse(tempDateReturn + " " + tempTimeReturn);
                    dateLeaveMilliSec = dateLeave.getTime();
                    dateReturnMilliSec = dateReturn.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                mProgressBar.showProgress();

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

                Call<ResponseBody> call = apiInterface.postRequestOutPass(
                        UserInformation.getString(getContext(), UserInformation.StringKey.TOKEN),
                        mSpinner.getSelectedItem().toString(),
                        dateLeaveMilliSec,
                        dateReturnMilliSec,
                        etPhoneNumber.getText().toString(),
                        etVisitingAddress.getText().toString(),
                        etReasonVisit.getText().toString(),
                        etStudentRemark.getText().toString()
                );

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 201) {
                            outPassRequestSuccessful();
                        } else {
                            outPassRequestFailed();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        outPassRequestFailed();
                    }
                });
            }
        });

        return view;
    }

    private boolean validateData() {
        return validateReasonLeave() &&
                validatePhoneNumber() &&
                validateAddress() &&
                validateLeaveDate() &&
                validateLeaveTime() &&
                validateReturnDate() &&
                validateReturnTime() &&
                validateStudentRemark();
    }

    private boolean validateReasonLeave() {
        String reason = etReasonVisit.getText().toString().trim();
        boolean flag = reason.length() > 0 && reason.length() <= 1000;
        if (!flag)
            etReasonVisit.setError("Enter a valid reason");
        return flag;
    }


    private boolean validatePhoneNumber() {
        int length = etPhoneNumber.getText().toString().trim().length();
        boolean flag = length >= 6 && length <= 20;
        if (!flag)
            etPhoneNumber.setError("Enter a valid phone number");
        return flag;
    }

    private boolean validateAddress() {
        String address = etVisitingAddress.getText().toString().trim();
        boolean flag = address.length() > 0 && address.length() <= 10000;
        if (!flag)
            etVisitingAddress.setError("Enter a valid address");
        return flag;
    }

    private boolean validateLeaveDate() {
        dateOfLeave.setError(null);
        boolean flag = tempDateLeave != null;
        if (!flag)
            dateOfLeave.setError("Select leave date");
        return flag;
    }

    private boolean validateLeaveTime() {
        timeOfLeave.setError(null);
        boolean flag = tempTimeLeave != null;
        if (!flag)
            timeOfLeave.setError("Select leave time");
        return flag;
    }

    private boolean validateReturnDate() {
        dateOfReturn.setError(null);
        if (!isDayOutPass()) {
            boolean flag = tempDateReturn != null;
            if (!flag)
                dateOfReturn.setError("Select return date");
            return flag;
        }

        return true;
    }

    private boolean validateReturnTime() {
        timeOfReturn.setError(null);
        boolean flag = tempTimeReturn != null;
        if (!flag)
            timeOfReturn.setError("Select return time");
        return flag;
    }

    private boolean validateStudentRemark() {
        // student remark is optional hence any value is valid
        return true;
    }

    private boolean isDayOutPass() {
        return mSpinner.getSelectedItem().toString().equals(OutPassType.DAY) ||
                mSpinner.getSelectedItem().toString().equals(OutPassType.DAY_COLLEGE_HOURS);
    }

    private void outPassRequestSuccessful() {
        mProgressBar.hideProgress();
        Toast.makeText(getContext(), "Request Successful", Toast.LENGTH_SHORT).show();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContent, new RequestFragment())
                .commit();
    }

    private void outPassRequestFailed() {
        mProgressBar.hideProgress();
        Toast.makeText(getContext(), "Request Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (mSpinner.getSelectedItem().toString().equals(OutPassType.DAY) ||
                mSpinner.getSelectedItem().toString().equals(OutPassType.DAY_COLLEGE_HOURS)) {
            dateOfReturn.setVisibility(View.GONE);
        } else {
            dateOfReturn.setVisibility(View.VISIBLE);
            dateOfReturn.setText(null);
            dateOfReturn.setHint("Return Date");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
