/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package facultyConsole;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.ApiClient;
import net.ApiInterface;
import net.MyPicasso;
import net.UrlGenerator;
import net.models.OutPassModel;

import constants.OutPassAttributes;
import constants.OutPassSource;
import constants.UserRoles;
import db.CrudLeavingToday;
import db.CrudLeftToday;
import db.CrudOutPassSigned;
import db.CrudOutPassToSign;
import db.CrudReturnedToday;
import db.CrudYetToReturn;
import db.DbHelper;
import in.ac.iilm.iilm.R;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.PassHelper;
import utils.ProgressBarUtil;
import utils.ToDateTime;
import utils.UserInformation;

public class NightPassResponseActivity extends AppCompatActivity {

    private ProgressBarUtil mProgressBar;
    private OutPassModel outPass;

    private CheckBox checkBox;
    private EditText remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_night_pass_response);

        mProgressBar = new ProgressBarUtil(
                findViewById(R.id.login_progress_background),
                findViewById(R.id.approve_deny_progress));

        final DbHelper dbHelper = new DbHelper(this);

        final Bundle extras = getIntent().getExtras();

        switch (extras.getInt(OutPassSource.LABEL)) {
            case OutPassSource.OUT_PASS_SIGNED:
                final CrudOutPassSigned crudOutPassSigned = new CrudOutPassSigned(this, dbHelper);
                outPass = crudOutPassSigned.getOutPass(extras.getString(OutPassAttributes.ID));
                break;
            case OutPassSource.OUT_PASS_TO_SIGN:
                final CrudOutPassToSign crudOutPassToSign = new CrudOutPassToSign(this, dbHelper);
                outPass = crudOutPassToSign.getOutPass(extras.getString(OutPassAttributes.ID));
                break;
            case OutPassSource.LEAVING_TODAY:
                final CrudLeavingToday crudLeavingToday = new CrudLeavingToday(this, dbHelper);
                outPass = crudLeavingToday.getOutPass(extras.getString(OutPassAttributes.ID));
                break;
            case OutPassSource.LEFT_TODAY:
                final CrudLeftToday crudLeftToday = new CrudLeftToday(this, dbHelper);
                outPass = crudLeftToday.getOutPass(extras.getString(OutPassAttributes.ID));
                break;
            case OutPassSource.RETURNED_TODAY:
                final CrudReturnedToday crudReturnedToday = new CrudReturnedToday(this, dbHelper);
                outPass = crudReturnedToday.getOutPass(extras.getString(OutPassAttributes.ID));
                break;
            case OutPassSource.YET_TO_RETURN:
                final CrudYetToReturn crudYetToReturn = new CrudYetToReturn(this, dbHelper);
                outPass = crudYetToReturn.getOutPass(extras.getString(OutPassAttributes.ID));
                break;
        }

        getSupportActionBar().setTitle(outPass.getOutPassType());

        ImageView ivProfile = findViewById(R.id.iv_approve_deny_profile_pic);
        ivProfile.setOnClickListener(view -> {
            /*
             * TODO:// open ImageViewProfile and view the image
             */
        });

        MyPicasso.with(this)
                .load(UrlGenerator.getUrlProfilePic(outPass.getUid()))
                .placeholder(R.color.bgColor)
                .error(R.drawable.profile_placeholder)
                .transform(new CropCircleTransformation())
                .into(ivProfile);

        ToDateTime dateTimeLeave = new ToDateTime(Long.parseLong(outPass.getTimeLeave()));
        ToDateTime dateTimeReturn = new ToDateTime(Long.parseLong(outPass.getTimeReturn()));

        TextView dateLeave = findViewById(R.id.tv_approve_date_leave);
        dateLeave.setText(dateTimeLeave.getDate());

        TextView timeLeave = findViewById(R.id.tv_approve_time_leave);
        timeLeave.setText(dateTimeLeave.getTime());

        TextView timeReturn = findViewById(R.id.tv_approve_time_return);
        timeReturn.setText(dateTimeReturn.getTime());

        TextView dateReturn = findViewById(R.id.tv_approve_date_return);
        dateReturn.setText(dateTimeReturn.getDate());

        TextView name = findViewById(R.id.tv_approve_name);
        name.setText(outPass.getName());

        final TextView phoneNumberStudent = findViewById(R.id.tv_approve_phone_number_student);
        phoneNumberStudent.setText(outPass.getPhoneNumber());

        final ImageView studentCall = findViewById(R.id.iv_student_call);
        studentCall.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_CALL)
                        .setData(Uri.parse("tel:" + phoneNumberStudent.getText()))));

        final TextView batch = findViewById(R.id.tv_approve_batch);
        batch.setText(String.format("%s %s", outPass.getYear(), outPass.getBranch()));

        final TextView roomNumber = findViewById(R.id.tv_approve_room_number);
        roomNumber.setText(outPass.getRoomNumber());

        final TextView address = findViewById(R.id.tv_approve_address);
        address.setText(outPass.getVisitingAddress());

        final TextView phoneNumber = findViewById(R.id.tv_approve_phone_number);
        phoneNumber.setText(outPass.getPhoneNumberVisiting());

        final TextView reason = findViewById(R.id.tv_approve_reason);
        reason.setText(outPass.getReasonVisit());

        ((TextView) findViewById(R.id.tv_approve_student_remark))
                .setText(outPass.getStudentRemark());

        final ImageView parentCall = findViewById(R.id.iv_approve_call);
        parentCall.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_CALL)
                        .setData(Uri.parse("tel:" + phoneNumber.getText()))));

        PassHelper passHelper = new PassHelper(this);
        passHelper.setStatus(outPass.getWardenSigned(), findViewById(R.id.tv_approve_warden_response));
        passHelper.setStatus(outPass.getHodSigned(), findViewById(R.id.tv_approve_hod_response));
        passHelper.setStatus(outPass.getDirectorSigned(), findViewById(R.id.tv_approve_director_response));

        passHelper.setCalled(outPass.getWardenTalkedToParent(), findViewById(R.id.tv_approve_warden_called));
        passHelper.setCalled(outPass.getHodTalkedToParent(), findViewById(R.id.tv_approve_hod_called));
        passHelper.setCalled(outPass.getDirectorPrioritySign(), findViewById(R.id.tv_approve_director_priority));

        ((TextView) findViewById(R.id.tv_approve_warden_remark)).setText(outPass.getWardenRemark());
        ((TextView) findViewById(R.id.tv_approve_hod_remark)).setText(outPass.getHodRemark());
        ((TextView) findViewById(R.id.tv_approve_director_remark)).setText(outPass.getDirectorRemark());

        checkBox = findViewById(R.id.cb_pass_response);
        switch (UserInformation.getString(this, UserInformation.StringKey.SCOPE)) {
            case UserRoles.DIRECTOR:
                checkBox.setText("Priority (Only Director's Sign required)");
                break;
            case UserRoles.WARDEN:
            case UserRoles.HOD:
                checkBox.setText("Talked To Parent");
                break;
            default:
                try {throw new Exception("Invalid Role");}
                catch (Exception e) {e.printStackTrace();}
        }

        remark = findViewById(R.id.et_pass_response_remark);

        final Button deny = findViewById(R.id.bt_deny);
        deny.setOnClickListener(view -> makeLogRequest(false));

        final Button allow = findViewById(R.id.bt_allow);
        allow.setOnClickListener(view -> makeLogRequest(true));
    }

    private void makeLogRequest(boolean isAllowed) {
        mProgressBar.showProgress();

        String message = isAllowed? "ALLOWED": "DENIED";

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = null;
        switch (UserInformation.getString(this, UserInformation.StringKey.SCOPE)) {
            case UserRoles.DIRECTOR:
                if (!checkBox.isChecked() || isAllowed) {
                    call = apiInterface.putSignDirector(
                            UserInformation.getString(getBaseContext(), UserInformation.StringKey.TOKEN),
                            outPass.getId(),
                            isAllowed,
                            checkBox.isChecked(),
                            remark.getText().toString()
                    );
                } else {
                    mProgressBar.hideProgress();
                    Toast.makeText(this,
                            "You can only allow with priority",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case UserRoles.WARDEN:
                call = apiInterface.putSignWarden(
                        UserInformation.getString(getBaseContext(), UserInformation.StringKey.TOKEN),
                        outPass.getId(),
                        isAllowed,
                        checkBox.isChecked(),
                        remark.getText().toString()
                );
                break;
            case UserRoles.HOD:
                call = apiInterface.putSignHod(
                        UserInformation.getString(getBaseContext(), UserInformation.StringKey.TOKEN),
                        outPass.getId(),
                        isAllowed,
                        checkBox.isChecked(),
                        remark.getText().toString()
                );
                break;
            default:
                try {throw new Exception("Invalid Role");}
                catch (Exception e) {e.printStackTrace();}
        }

        if (call != null)
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 203) {
                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                        success();
                    } else {
                        failed();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failed();
                }
            });
    }

    private void failed() {
        mProgressBar.hideProgress();
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
    }

    private void success() {
        finish();
    }

    @Override
    public void finish() {
        mProgressBar.hideProgress();
        super.finish();
    }
}
