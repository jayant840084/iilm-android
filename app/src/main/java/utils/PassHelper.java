/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.models.OutPassModel;

import constants.OutPassAttributes;
import constants.OutPassSource;
import constants.OutPassType;
import db.CrudLeavingToday;
import db.CrudLeftToday;
import db.CrudOutPass;
import db.CrudOutPassSigned;
import db.CrudOutPassToSign;
import db.CrudReturnedToday;
import db.CrudYetToReturn;
import db.DbHelper;
import in.ac.iilm.iilm.R;

/**
 * Created by Jayant Singh on 20/1/18.
 */

public class PassHelper {

    private Context context;
    private DbHelper dbHelper;

    public PassHelper(Context context) {
        this.context = context;
        this.dbHelper = new DbHelper(context);
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
                return outPass.getWardenSigned() != null && outPass.getWardenSigned();
            case OutPassType.DAY_COLLEGE_HOURS:
                return outPass.getWardenSigned() != null &&
                        outPass.getHodSigned() != null &&
                        outPass.getWardenSigned() &&
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
                final CrudOutPass crudOutPass = new CrudOutPass(context, dbHelper);
                return crudOutPass.getOutPass(passId);
            case OutPassSource.OUT_PASS_SIGNED:
                final CrudOutPassSigned crudOutPassSigned = new CrudOutPassSigned(context, dbHelper);
                return crudOutPassSigned.getOutPass(passId);
            case OutPassSource.OUT_PASS_TO_SIGN:
                final CrudOutPassToSign crudOutPassToSign = new CrudOutPassToSign(context, dbHelper);
                return crudOutPassToSign.getOutPass(passId);
            case OutPassSource.LEAVING_TODAY:
                final CrudLeavingToday crudLeavingToday = new CrudLeavingToday(context, dbHelper);
                return crudLeavingToday.getOutPass(passId);
            case OutPassSource.LEFT_TODAY:
                final CrudLeftToday crudLeftToday = new CrudLeftToday(context, dbHelper);
                return crudLeftToday.getOutPass(passId);
            case OutPassSource.RETURNED_TODAY:
                final CrudReturnedToday crudReturnedToday = new CrudReturnedToday(context, dbHelper);
                return crudReturnedToday.getOutPass(passId);
            case OutPassSource.YET_TO_RETURN:
                final CrudYetToReturn crudYetToReturn = new CrudYetToReturn(context, dbHelper);
                return crudYetToReturn.getOutPass(passId);
            default:
                Toast.makeText(context, "Invalid Out Pass", Toast.LENGTH_SHORT).show();
                return null;
        }
    }
}
