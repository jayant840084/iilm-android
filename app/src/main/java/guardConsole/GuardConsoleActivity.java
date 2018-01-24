/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package guardConsole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.models.GuardLogModel;
import net.requests.LogLeaveRequest;
import net.requests.LogReturnRequest;
import net.requests.LogoutRequest;

import auth.LoginActivity;
import guardConsole.layouts.GuardDenyFragment;
import guardConsole.layouts.GuardAllowFragment;
import in.ac.iilm.iilm.R;
import pojo.GuardLogPojo;
import pojo.GuardLogReturnPOJO;
import utils.GuardPassHelper;
import utils.ProgressBarUtil;

public class GuardConsoleActivity extends AppCompatActivity {

    private final CharSequence[] dialogButtons = new CharSequence[]{
            "Log Leave",
            "Log Return"
    };
    private ProgressBarUtil mProgressBar;
    private CardView mRequestFailed;
    private int currentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard);

        mRequestFailed = findViewById(R.id.tv_guard_check_failed);

        mProgressBar = new ProgressBarUtil(
                findViewById(R.id.guard_progress_background),
                findViewById(R.id.guard_progress)
        );

        final Button scan = findViewById(R.id.bt_guard_scan);
        scan.setOnClickListener(v -> generateDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.guard_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_guard_logout:
                new LogoutRequest().execute(this, success -> {
                    if (success) {
                        startActivity(new Intent(this, LoginActivity.class));
                        this.finish();
                    } else {
                        Toast.makeText(this,
                                getString(R.string.logout_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void generateDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select type");
        dialog.setItems(dialogButtons, (dialogInterface, i) -> {

            currentType = i;
            mRequestFailed.setVisibility(View.GONE);

            IntentIntegrator intentIntegrator = new IntentIntegrator(GuardConsoleActivity.this);
            intentIntegrator.initiateScan();
        });

        dialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        Gson gson = new Gson();
        GuardLogPojo qrData = gson.fromJson(result.getContents(), GuardLogPojo.class);

        switch (currentType) {
            case 0:
                if (qrData != null) {
                    mProgressBar.showProgress();
                    initiateLeave(qrData);
                }
                break;
            case 1:
                if (qrData != null) {
                    mProgressBar.showProgress();
                    initiateReturn(qrData);
                }
                break;
            default:
                throw new Error("Unhandled Id");
        }
    }

    private void initiateLeave(GuardLogPojo data) {
        new LogLeaveRequest().execute(this, data.getId(), (errMsg, guardLogPass) -> {
            if (guardLogPass != null) {
                if (guardLogPass.isAllowed()) {
                    allow(guardLogPass);
                }
            } else if (errMsg != null) {
                deny(errMsg);
            } else {
                requestFailed();
            }
            mProgressBar.hideProgress();
        });
    }

    private void initiateReturn(GuardLogPojo data) {
        new LogReturnRequest().execute(this, data.getId(), (errMsg, guardLogPass) -> {
            if (guardLogPass != null) {
                if (guardLogPass.isAllowed()) {
                    allow(guardLogPass);
                }
            } else if (errMsg != null) {
                deny(errMsg);
            } else {
                requestFailed();
            }
            mProgressBar.hideProgress();
        });
    }

    private void allow(GuardLogModel guardLogModel) {
        GuardPassHelper.setGuardLogModel(guardLogModel);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.guard_pass_info_container, new GuardAllowFragment())
                .commit();
    }

    private void deny(String message) {
        GuardPassHelper.setMessage(message);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.guard_pass_info_container, new GuardDenyFragment())
                .commit();
    }

    private void requestFailed() {
        if (getSupportFragmentManager().getFragments().size() > 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(getSupportFragmentManager().getFragments().get(0))
                    .commit();
        }
        mRequestFailed.setVisibility(View.VISIBLE);
    }
}
