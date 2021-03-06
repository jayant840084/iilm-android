/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package studentConsole;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import models.OutPassModel;

import constants.OutPassAttributes;
import constants.OutPassSource;
import in.ac.iilm.iilm.R;
import pojo.GuardLogPojo;
import utils.PassHelper;
import utils.QrHelper;
import utils.ToDateTime;

public class NightPassViewActivity extends AppCompatActivity {

    private OutPassModel outPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_out_pass_view);

        Bundle extras = getIntent().getExtras();

        PassHelper passHelper = new PassHelper(this);

        outPass = passHelper.getOutPassFromDb(extras.getInt(OutPassSource.LABEL),
                extras.getString(OutPassAttributes.ID));

        getSupportActionBar().setTitle(outPass.getOutPassType());

        // only generate the qr code if the out pass has been confirmed
        if (extras.getBoolean(OutPassSource.SHOW_QR)) {
            Log.wtf("RRRRRR", outPass.getDirectorPrioritySign() + "");
            if (outPass.getDirectorPrioritySign() != null &&
                    outPass.getDirectorPrioritySign()) {
                GuardLogPojo guardLogPojo = new GuardLogPojo();
                guardLogPojo.setId(outPass.getId());
                guardLogPojo.setName(outPass.getName());

                new QrHelper(this).generateAndSetQr(
                        findViewById(R.id.iv_QRCode),
                        guardLogPojo
                );
            } else if (outPass.getWardenSigned() != null &&
                    outPass.getHodSigned() != null &&
                    outPass.getDirectorSigned() != null) {
                if (outPass.getWardenSigned() &&
                        outPass.getHodSigned() &&
                        outPass.getDirectorSigned()) {
                    GuardLogPojo guardLogPojo = new GuardLogPojo();
                    guardLogPojo.setId(outPass.getId());
                    guardLogPojo.setName(outPass.getName());

                    new QrHelper(this).generateAndSetQr(
                            findViewById(R.id.iv_QRCode),
                            guardLogPojo
                    );
                }
            }
        }

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

        TextView address = findViewById(R.id.tv_approve_address);
        address.setText(outPass.getVisitingAddress());

        TextView phoneNumber = findViewById(R.id.tv_approve_phone_number);
        phoneNumber.setText(outPass.getPhoneNumber());

        TextView reason = findViewById(R.id.tv_approve_reason);
        reason.setText(outPass.getReasonVisit());

        ((TextView) findViewById(R.id.tv_approve_student_remark))
                .setText(outPass.getStudentRemark());

        ((TextView) findViewById(R.id.tv_approve_warden_remark))
                .setText(outPass.getWardenRemark());

        passHelper.setStatus(outPass.getWardenSigned(),
                findViewById(R.id.tv_approve_warden));

        ((TextView) findViewById(R.id.tv_approve_hod_remark))
                .setText(outPass.getHodRemark());

        passHelper.setStatus(outPass.getHodSigned(),
                findViewById(R.id.tv_approve_hod));

        ((TextView) findViewById(R.id.tv_approve_director_remark))
                .setText(outPass.getDirectorRemark());

        passHelper.setStatus(getDirectorSigned(outPass),
                findViewById(R.id.tv_approve_director));
    }

    private Boolean getDirectorSigned(OutPassModel outPass) {
        if (outPass.getDirectorPrioritySign() != null &&
                outPass.getDirectorPrioritySign()) {
            return outPass.getDirectorPrioritySign();
        } else {
            return outPass.getDirectorSigned();
        }
    }
}
