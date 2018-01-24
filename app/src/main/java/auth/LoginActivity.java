/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package auth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import net.ApiClient;
import net.ApiInterface;
import net.models.LoginModel;
import net.models.UserModel;
import net.requests.AddFirebaseTokenRequest;
import net.requests.LogoutRequest;

import facultyConsole.FacultyConsoleActivity;
import guardConsole.GuardConsoleActivity;
import in.ac.iilm.iilm.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studentConsole.StudentConsoleActivity;
import utils.ActivityTracker;
import utils.DeveloperInfo;
import utils.ProgressBarUtil;
import utils.UserInformation;

public class LoginActivity extends AppCompatActivity {

    private EditText mUIDView;
    private EditText mPasswordView;

    private ProgressBarUtil mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        mProgressBar = new ProgressBarUtil(
                findViewById(R.id.login_progress_background),
                findViewById(R.id.login_progress));

        // Set up the login form.
        mUIDView = findViewById(R.id.etUid);
        mPasswordView = findViewById(R.id.etPassword);
        Button mSignInButton = findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(view -> attemptLogin());

        Button forgotPasswordButton = findViewById(R.id.bt_login_forgot_password);
        forgotPasswordButton.setOnClickListener(v -> forgotPassword());

        new DeveloperInfo(this)
                .visitDeveloperSite(findViewById(R.id.bt_login_dev_site));
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Store values at the time of the login attempt.
        String uid = mUIDView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(uid)) {
            mUIDView.setError(getString(R.string.error_field_required));
            focusView = mUIDView;
            cancel = true;
        } else if (!isUIDValid(uid)) {
            mUIDView.setError(getString(R.string.error_invalid_uid));
            focusView = mUIDView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mProgressBar.showProgress();

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<LoginModel> call = apiInterface.getLogin(uid, password);
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.code() == 200) {
                        getUserInfo(response.body().getToken());
                    } else {
                        invalidPassword();
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    loginFailed();
                }
            });
        }
    }

    private void forgotPassword() {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    private boolean isUIDValid(String uid) {
        return uid.length() >= 2 && uid.length() <= 100;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4 && password.length() <= 200;
    }

    private void getUserInfo(final String token) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<UserModel> call = apiInterface.getUser(token);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                onRetrievingUserInfo(token, response);
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                loginFailed();
            }
        });

    }

    private void onRetrievingUserInfo(String token, Response<UserModel> response) {
        UserInformation.putString(LoginActivity.this, UserInformation.StringKey.UID, response.body().getUid());
        UserInformation.putString(LoginActivity.this, UserInformation.StringKey.NAME, response.body().getName());
        UserInformation.putString(LoginActivity.this, UserInformation.StringKey.BRANCH, response.body().getBranch());
        UserInformation.putString(LoginActivity.this, UserInformation.StringKey.GENDER, response.body().getGender());
        UserInformation.putString(LoginActivity.this, UserInformation.StringKey.PHONE_NUMBER, response.body().getPhoneNumber());
        UserInformation.putString(LoginActivity.this, UserInformation.StringKey.YEAR, response.body().getYear());
        UserInformation.putString(LoginActivity.this, UserInformation.StringKey.ROOM_NUMBER, response.body().getRoomNumber());
        UserInformation.putString(LoginActivity.this, UserInformation.StringKey.SCOPE, response.body().getScope());
        UserInformation.putString(LoginActivity.this, UserInformation.StringKey.EMAIL, response.body().getEmail());
        UserInformation.putString(LoginActivity.this, UserInformation.StringKey.TOKEN, token);

        // add firebase token to server if available
        if (FirebaseInstanceId.getInstance().getToken() != null) {
            new AddFirebaseTokenRequest().execute(this, success -> {
                if (success) finishLogin();
                else loginFailed();
            });
        } else {
            Log.d("FIREBASE TOKEN", "Firebase token not available");
            finishLogin();
        }
    }

    private void loginFailed() {
        mProgressBar.hideProgress();
        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
    }

    private void invalidPassword() {
        mProgressBar.hideProgress();
        Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();
    }

    private void finishLogin() {
        switch (UserInformation.getString(this, UserInformation.StringKey.SCOPE)) {
            case "student":
                startActivity(new Intent(this, StudentConsoleActivity.class));
                finish();
                break;
            case "hod":
            case "director":
            case "warden":
                startActivity(new Intent(this, FacultyConsoleActivity.class));
                finish();
                break;
            case "guard":
                startActivity(new Intent(this, GuardConsoleActivity.class));
                finish();
                break;
            default:
                new LogoutRequest().execute(getBaseContext(), success -> {
                    startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                    finish();
                });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityTracker.setActivityRunning(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityTracker.setActivityRunning(false);
    }
}

