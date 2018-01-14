/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package facultyConsole;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.ApiClient;
import net.ApiInterface;
import net.MyPicasso;
import net.UrlGenerator;

import constants.OutPassAttributes;
import in.ac.iilm.iilm.R;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.ProgressBarUtil;
import utils.ToDateTime;
import utils.UserInformation;

public class PassResponseActivity extends AppCompatActivity {

    private ProgressBarUtil mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_pass_response);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        mProgressBar = new ProgressBarUtil(
                findViewById(R.id.login_progress_background),
                findViewById(R.id.approve_deny_progress));

        final Bundle extras = getIntent().getExtras();

        ImageView ivProfile = findViewById(R.id.iv_approve_deny_profile_pic);
        ivProfile.setOnClickListener(view -> {
            /*
             * TODO:// open ImageViewProfile and view the image
             */
        });

        MyPicasso.with(this)
                .load(UrlGenerator.getUrlProfilePic(extras.getString(OutPassAttributes.UID)))
                .placeholder(R.color.bgColor)
                .error(R.drawable.profile_placeholder)
                .transform(new CropCircleTransformation())
                .into(ivProfile);

        ToDateTime dateTimeLeave = new ToDateTime(Long.parseLong(extras.getString(OutPassAttributes.DATE_LEAVE)));
        ToDateTime dateTimeReturn = new ToDateTime(Long.parseLong(extras.getString(OutPassAttributes.DATE_RETURN)));

        TextView dateLeave = findViewById(R.id.tv_approve_date_leave);
        dateLeave.setText(dateTimeLeave.getDate());

        TextView timeLeave = findViewById(R.id.tv_approve_time_leave);
        timeLeave.setText(dateTimeLeave.getTime());

        TextView timeReturn = findViewById(R.id.tv_approve_time_return);
        timeReturn.setText(dateTimeReturn.getTime());

        TextView dateReturn = findViewById(R.id.tv_approve_date_return);
        dateReturn.setText(dateTimeReturn.getDate());

        TextView name = findViewById(R.id.tv_approve_name);
        name.setText(extras.getString(OutPassAttributes.NAME));

        final TextView phoneNumberStudent = findViewById(R.id.tv_approve_phone_number_student);
        phoneNumberStudent.setText(extras.getString(OutPassAttributes.PHONE_NUMBER));

        final ImageView studentCall = findViewById(R.id.iv_student_call);
        studentCall.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_CALL)
                        .setData(Uri.parse("tel:" + phoneNumberStudent.getText()))));

        final TextView batch = findViewById(R.id.tv_approve_batch);
        batch.setText(String.format("%s %s",
                extras.getString(OutPassAttributes.YEAR),
                extras.getString(OutPassAttributes.BRANCH)));

        final TextView roomNumber = findViewById(R.id.tv_approve_room_number);
        roomNumber.setText(extras.getString(OutPassAttributes.ROOM_NUMBER));

        final TextView address = findViewById(R.id.tv_approve_address);
        address.setText(extras.getString(OutPassAttributes.ADDRESS));

        final TextView phoneNumber = findViewById(R.id.tv_approve_phone_number);
        phoneNumber.setText(extras.getString(OutPassAttributes.PHONE_NUMBER_VISITING));

        final TextView reason = findViewById(R.id.tv_approve_reason);
        reason.setText(extras.getString(OutPassAttributes.REASON));

        final ImageView parentCall = findViewById(R.id.iv_approve_call);
        parentCall.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_CALL)
                        .setData(Uri.parse("tel:" + phoneNumber.getText()))));

        final Button deny = findViewById(R.id.bt_deny);
        deny.setOnClickListener(view -> {

            mProgressBar.showProgress();

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<ResponseBody> call = apiInterface.putSignOutPass(
                    UserInformation.getString(getBaseContext(), UserInformation.StringKey.TOKEN),
                    extras.getString(OutPassAttributes.ID),
                    false
            );

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 203) {
                        Toast.makeText(getBaseContext(), "DENIED", Toast.LENGTH_SHORT).show();
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
        });

        final Button allow = findViewById(R.id.bt_allow);
        allow.setOnClickListener(view -> {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<ResponseBody> call = apiInterface.putSignOutPass(
                    UserInformation.getString(getBaseContext(), UserInformation.StringKey.TOKEN),
                    extras.getString(OutPassAttributes.ID),
                    true
            );

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 203) {
                        Toast.makeText(getBaseContext(), "ALLOWED", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        failed();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    failed();
                }
            });
        });
    }

    private void failed() {
        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
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
