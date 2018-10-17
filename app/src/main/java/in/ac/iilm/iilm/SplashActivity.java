/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package in.ac.iilm.iilm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import net.ApiClient;
import net.requests.CheckUpdateRequest;
import net.requests.LogoutRequest;

import auth.LoginActivity;
import facultyConsole.FacultyConsoleActivity;
import guardConsole.GuardConsoleActivity;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import studentConsole.StudentConsoleActivity;
import utils.UserInformation;


public class SplashActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_ACCESS_NETWORK_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build());
        ApiClient.with(this);
        setContentView(R.layout.activity_splash);

        // if all permissions are not available this boolean will stop the SplashActivity from ending
        boolean permissionsAvailable = true;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            permissionsAvailable = false;

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSION_REQUEST_ACCESS_NETWORK_STATE);
        }
        TextView versionAndUpdate = findViewById(R.id.updateRequired);
        try {
            versionAndUpdate.setText(getApplication()
                    .getPackageManager()
                    .getPackageInfo(getPackageName(), 0)
                    .versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new CheckUpdateRequest().execute(this);

        try {
            if (PreferenceManager
                    .getDefaultSharedPreferences(this)
                    .getInt("versionRequired", Integer.MAX_VALUE) >= this
                    .getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionCode) {
                versionAndUpdate.setText(this.getString(R.string.update_message));
            } else {
                if (permissionsAvailable) {
                    continueSplash();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_ACCESS_NETWORK_STATE:
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                }
        }
        continueSplash();
    }

    void continueSplash() {

        if (!UserInformation.getString(this, UserInformation.StringKey.TOKEN).equals("")) {
            if (UserInformation.getBoolean(this, UserInformation.BooleanKey.LOGOUT_FLAG) ||
                    !UserInformation.isTokenValid(this)) {
                logout();
            } else {
                showUserConsole();
            }
        } else {
            showLogin();
        }
    }

    // user reaches here only if everything checks out and the application should continue to dash with login
    private void showUserConsole() {
        // refresh the user info
        UserInformation.getFromServer(this, success -> {
            // choose the appropriate dash for the user
            switch (UserInformation.getString(SplashActivity.this, UserInformation.StringKey.SCOPE)) {
                case "student":
                    startActivity(new Intent(SplashActivity.this, StudentConsoleActivity.class));
                    break;
                case "hod":
                case "director":
                case "warden":
                    startActivity(new Intent(SplashActivity.this, FacultyConsoleActivity.class));
                    break;
                case "guard":
                    startActivity(new Intent(SplashActivity.this, GuardConsoleActivity.class));
                    break;
            }
            finish();
        });
    }

    private void showLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void logout() {
        new LogoutRequest().execute(this, success -> {
            if (success) {
                showLogin();
            } else {
                Toast.makeText(SplashActivity.this,
                        R.string.logout_failed,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
