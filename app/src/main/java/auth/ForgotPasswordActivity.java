/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package auth;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.requests.ResetPasswordRequest;

import java.io.IOException;

import in.ac.iilm.iilm.R;
import okhttp3.ResponseBody;
import retrofit2.Response;
import utils.ProgressBarUtil;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ProgressBarUtil mProgressBar;

    private CardView messageView;
    private CardView inputView;
    private TextView message;
    private EditText uid;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_forgot_password);

        mProgressBar = new ProgressBarUtil(
                findViewById(R.id.login_progress_background),
                findViewById(R.id.login_progress));

        messageView = findViewById(R.id.forgot_password_message_view);
        inputView = findViewById(R.id.forgot_password_input_view);
        message = findViewById(R.id.forgot_password_message);
        uid = findViewById(R.id.forgot_password_et_uid);
        resetButton = findViewById(R.id.forgot_password_button);

        resetButton.setOnClickListener(v -> initiatePasswordReset());
    }

    private void initiatePasswordReset() {
        if (isDataValid()) {
            mProgressBar.showProgress();
            new ResetPasswordRequest().execute(this, uid.getText().toString(), (errMsg, response) -> {
                if (errMsg != null) {
                    Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
                } else {
                    if (response.code() == 203) {
                        inputView.setVisibility(View.GONE);
                        message.setText(String.format("Reset link has been sent to %s", response.body().getEmail()));
                    }
                    else if (response.code() == 400)
                        message.setText("Invalid User ID, please check again.");
                    else if (response.code() == 404)
                        message.setText("No Email address found associated with this user. Contact college for a new password");
                    messageView.setVisibility(View.VISIBLE);
                }
                mProgressBar.hideProgress();
            });
        }
    }

    private boolean isDataValid() {
        return isUIDValid();
    }

    private boolean isUIDValid() {
        final String tempUid = uid.getText().toString();
        boolean isValid = tempUid.length() >= 2 && tempUid.length() <= 100;
        if (!isValid) {
            uid.setError("Invalid User ID");
        }
        return isValid;
    }
}
