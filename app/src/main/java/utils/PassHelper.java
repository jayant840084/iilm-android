/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import constants.OutPassSource;
import db.FacultySignedPasses;
import db.FacultyToSignPasses;
import db.ReportLeavingToday;
import db.ReportLeftToday;
import db.ReportReturnedToday;
import db.ReportYetToReturn;
import db.StudentHistory;

import constants.OutPassType;
import in.ac.iilm.iilm.R;
import io.realm.Realm;
import models.OutPassModel;

/**
 * Created by Jayant Singh on 20/1/18.
 */

public class PassHelper {

    private Context context;
    private GeneralizeOutPass generalizeOutPass = new GeneralizeOutPass();

    public PassHelper(Context context) {
        this.context = context;
    }

    public void setStatus(Boolean status, TextView tv) {
        if (status != null) {
            if (status) {
                tv.setText(context.getString(R.string.allowed));
                tv.setTextColor(ContextCompat.getColor(
                        context,
                        R.color.allow));
            } else {
                tv.setText(context.getString(R.string.denied));
                tv.setTextColor(ContextCompat.getColor(
                        context,
                        R.color.deny));
            }
        } else {
            tv.setText(context.getString(R.string.waiting));
            tv.setTextColor(ContextCompat.getColor(
                    context,
                    R.color.waiting));
        }
    }

    public void setCalled(Boolean called, TextView tv) {
        if (called != null && called) {
            tv.setText("Yes");
            tv.setTextColor(ContextCompat.getColor(
                    context,
                    R.color.allow));
        } else {
            tv.setText("No");
            tv.setTextColor(ContextCompat.getColor(
                    context,
                    R.color.deny));
        }
    }

    public boolean isAllowed(OutPassModel outPass) {
        switch (outPass.getOutPassType()) {
            case OutPassType.DAY:
                return outPass.getWardenSigned() != null &&
                        outPass.getWardenSigned();
            case OutPassType.DAY_COLLEGE_HOURS:
                return outPass.getHodSigned() != null &&
                        outPass.getHodSigned();
            case OutPassType.NIGHT:
                return outPass.getWardenSigned() != null &&
                        outPass.getHodSigned() != null &&
                        outPass.getDirectorSigned() != null &&
                        outPass.getWardenSigned() &&
                        outPass.getHodSigned() &&
                        outPass.getDirectorSigned();
            default:
                return false;
        }
    }

    public OutPassModel getOutPassFromDb(int sourceType, String passId) {
        switch (sourceType) {
            case OutPassSource.OUT_PASS:
                return generalizeOutPass.general(Realm.getDefaultInstance()
                        .where(StudentHistory.class)
                        .equalTo("id", passId)
                        .findFirst());
            case OutPassSource.OUT_PASS_SIGNED:
                return generalizeOutPass.general(Realm.getDefaultInstance()
                        .where(FacultySignedPasses.class)
                        .equalTo("id", passId)
                        .findFirst());
            case OutPassSource.OUT_PASS_TO_SIGN:
                return generalizeOutPass.general(Realm.getDefaultInstance()
                        .where(FacultyToSignPasses.class)
                        .equalTo("id", passId)
                        .findFirst());
            case OutPassSource.LEAVING_TODAY:
                return generalizeOutPass.general(Realm.getDefaultInstance()
                        .where(ReportLeavingToday.class)
                        .equalTo("id", passId)
                        .findFirst());
            case OutPassSource.LEFT_TODAY:
                return generalizeOutPass.general(Realm.getDefaultInstance()
                        .where(ReportLeftToday.class)
                        .equalTo("id", passId)
                        .findFirst());
            case OutPassSource.RETURNED_TODAY:
                return generalizeOutPass.general(Realm.getDefaultInstance()
                        .where(ReportReturnedToday.class)
                        .equalTo("id", passId)
                        .findFirst());
            case OutPassSource.YET_TO_RETURN:
                return generalizeOutPass.general(Realm.getDefaultInstance()
                        .where(ReportYetToReturn.class)
                        .equalTo("id", passId)
                        .findFirst());
            default:
                Toast.makeText(context, "Invalid Out Pass", Toast.LENGTH_SHORT).show();
                return null;
        }
    }
}
