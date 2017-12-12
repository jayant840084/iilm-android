package guardConsole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.requests.LogLeaveRequest;
import net.requests.LogReturnRequest;
import net.requests.LogoutRequest;

import auth.LoginActivity;
import constants.OutPassAttributes;
import constants.OutPassType;
import guardConsole.layouts.DayPassFragment;
import guardConsole.layouts.GuardPassInfoFragment;
import guardConsole.layouts.NightPassFragment;
import in.ac.iilm.iilm.R;
import pojo.GuardLogLeavePOJO;
import pojo.GuardLogReturnPOJO;
import utils.ProgressBarUtil;

public class GuardConsoleActivity extends AppCompatActivity {

    private final CharSequence[] dialogButtons = new CharSequence[]{
            "Log Leave",
            "Log Return"
    };
    private ProgressBarUtil mProgressBar;
    private TextView mRequestFailed;
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
                        Toast.makeText(this, "Logout Failed", Toast.LENGTH_SHORT).show();
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

        switch (currentType) {
            case 0:
                GuardLogLeavePOJO leaveData = gson.fromJson(result.getContents(), GuardLogLeavePOJO.class);
                if (leaveData != null) {
                    mProgressBar.showProgress();
                    initiateLeave(leaveData);
                }
                break;
            case 1:
                GuardLogReturnPOJO returnData = gson.fromJson(result.getContents(), GuardLogReturnPOJO.class);
                if (returnData != null) {
                    mProgressBar.showProgress();
                    initiateReturn(returnData);
                }
                break;
            default:
                throw new Error("Unhandled Id");
        }
    }

    private void initiateLeave(GuardLogLeavePOJO data) {

        new LogLeaveRequest().execute(this, data.getId(), (success, passData) -> {

            if (success) {
                if (passData.getBoolean(OutPassAttributes.ALLOWED, false)) {
                    allowLeaveExit(passData);
                } else {
                    denyLeaveExit();
                }
            } else {
                requestFailed();
            }
            mProgressBar.hideProgress();
        });
    }

    private void initiateReturn(GuardLogReturnPOJO data) {

        new LogReturnRequest().execute(this, data.getId(), (success, passData) -> {

            if (success) {
                if (passData.getBoolean(OutPassAttributes.ALLOWED, false)) {
                    allowReturnEntry(passData);
                } else {
                    denyReturnEntry();
                }
            } else {
                requestFailed();
            }
            mProgressBar.hideProgress();
        });
    }

    private void allowLeaveExit(Bundle passData) {

        if (passData.getString(OutPassAttributes.OUT_PASS_TYPE).equals(OutPassType.DAY))
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.guard_pass_info_container, DayPassFragment.newInstance(passData))
                    .commit();
        else
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.guard_pass_info_container, NightPassFragment.newInstance(passData))
                    .commit();
    }

    private void denyLeaveExit() {

        Bundle args = new Bundle();
        args.putString("name", "");
        args.putBoolean("isAllowed", false);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.guard_pass_info_container, GuardPassInfoFragment.newInstance(args))
                .commit();
    }

    private void requestFailed() {
        mRequestFailed.setVisibility(View.VISIBLE);
    }

    private void allowReturnEntry(Bundle passData) {

        if (passData.getString(OutPassAttributes.OUT_PASS_TYPE).equals(OutPassType.DAY))
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.guard_pass_info_container, DayPassFragment.newInstance(passData))
                    .commit();
        else
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.guard_pass_info_container, NightPassFragment.newInstance(passData))
                    .commit();
    }

    private void denyReturnEntry() {

        Bundle args = new Bundle();
        args.putString("name", "");
        args.putBoolean("isAllowed", false);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.guard_pass_info_container, GuardPassInfoFragment.newInstance(args))
                .commit();
    }
}
