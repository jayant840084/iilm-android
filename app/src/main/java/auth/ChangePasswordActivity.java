package auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.requests.ChangePasswordRequest;
import net.requests.LogoutRequest;

import in.ac.iilm.iilm.R;
import utils.ProgressBarUtil;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText mEtOldPassword;
    private EditText mEtNewPassword;
    private EditText mEtReNewPassword;

    private ProgressBarUtil mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_change_password);

        getSupportActionBar().setTitle((getResources()
                .getString(R.string.change_password))
                .toUpperCase());

        mProgressBar = new ProgressBarUtil(
                findViewById(R.id.cp_progress_bg),
                findViewById(R.id.cp_progress)
        );

        mEtOldPassword = findViewById(R.id.et_cp_old);
        mEtNewPassword = findViewById(R.id.et_cp_new);
        mEtReNewPassword = findViewById(R.id.et_cp_re_new);

        Button changePassword = findViewById(R.id.bt_cp_change);
        changePassword.setOnClickListener(v -> {
            if (isFormValid()) {
                changePassword();
            }
        });
    }

    private boolean isFormValid() {
        return oldPasswordValid() &&
                newPasswordsMatchAndValid();
    }

    private boolean newPasswordsMatchAndValid() {
        boolean flag = false;
        if (mEtNewPassword.getText().toString().length() < 4) {
            mEtNewPassword.setError("Password should be 4 characters or more");
        } else if (!mEtNewPassword.getText().toString()
                .equals(mEtReNewPassword.getText().toString())) {
            mEtReNewPassword.setError("Passwords do not match");
        } else {
            flag = true;
        }
        return flag;
    }

    private boolean oldPasswordValid() {
        boolean flag = false;
        if (mEtOldPassword.getText().toString().equals("")) {
            mEtOldPassword.setError("Please enter the current password");
        } else {
            flag = true;
        }
        return flag;
    }

    private void changePassword() {
        mProgressBar.showProgress();
        new ChangePasswordRequest().execute(
                this,
                mEtOldPassword.getText().toString(),
                mEtNewPassword.getText().toString(),
                (isSuccess, err) -> {
                    if (isSuccess) {
                        new LogoutRequest().execute(this, success -> {
                            if (success) {
                                startActivity(new Intent(this,
                                        LoginActivity.class));
                                finish();
                            }
                        });
                    } else {
                        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
                    }
                    mProgressBar.hideProgress();
                }
        );
    }
}
