/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package facultyConsole.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import constants.ReportTypes;
import db.ReportLeavingToday;
import db.ReportLeftToday;
import db.ReportReturnedToday;
import db.ReportYetToReturn;
import io.realm.RealmResults;
import models.OutPassModel;

import java.util.List;

import constants.OutPassAttributes;
import constants.OutPassSource;
import constants.OutPassType;
import in.ac.iilm.iilm.R;
import studentConsole.DayCollegeHoursPassViewActivity;
import studentConsole.DayPassViewActivity;
import studentConsole.NightPassViewActivity;
import utils.ToDateTime;

/**
 * Created by jayant on 7/1/18.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private RealmResults<ReportLeavingToday> reportLeavingToday;
    private RealmResults<ReportReturnedToday> reportReturnedToday;
    private RealmResults<ReportYetToReturn> reportYetToReturn;
    private RealmResults<ReportLeftToday> reportLeftToday;

    private int outPassSource;

    public ReportAdapter(
            RealmResults<ReportLeavingToday> reportLeavingToday,
            RealmResults<ReportReturnedToday> reportReturnedToday,
            RealmResults<ReportYetToReturn> reportYetToReturn,
            RealmResults<ReportLeftToday> reportLeftToday,
            int outPassSource) {
        this.reportLeavingToday = reportLeavingToday;
        this.reportReturnedToday = reportReturnedToday;
        this.reportYetToReturn = reportYetToReturn;
        this.reportLeftToday = reportLeftToday;
        this.outPassSource = outPassSource;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faculty_console_report_leaving_today_item,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDateTime dateTimeLeave;
        switch (outPassSource) {
            case OutPassSource.LEAVING_TODAY:
                dateTimeLeave = new ToDateTime(Long.parseLong(reportLeavingToday.get(position).getTimeLeave()));
                holder.name.setText(reportLeavingToday.get(position).getName());
                holder.roomNumber.setText(reportLeavingToday.get(position).getRoomNumber());
                holder.leaveTime.setText(dateTimeLeave.getTime());
                holder.reportLeavingToday = reportLeavingToday.get(position);
                break;
            case OutPassSource.LEFT_TODAY:
                dateTimeLeave = new ToDateTime(Long.parseLong(reportLeftToday.get(position).getTimeLeave()));
                holder.name.setText(reportLeftToday.get(position).getName());
                holder.roomNumber.setText(reportLeftToday.get(position).getRoomNumber());
                holder.leaveTime.setText(dateTimeLeave.getTime());
                holder.reportLeftToday = reportLeftToday.get(position);
                break;
            case OutPassSource.RETURNED_TODAY:
                dateTimeLeave = new ToDateTime(Long.parseLong(reportReturnedToday.get(position).getTimeLeave()));
                holder.name.setText(reportReturnedToday.get(position).getName());
                holder.roomNumber.setText(reportReturnedToday.get(position).getRoomNumber());
                holder.leaveTime.setText(dateTimeLeave.getTime());
                holder.reportReturnedToday = reportReturnedToday.get(position);
                break;
            case OutPassSource.YET_TO_RETURN:
                dateTimeLeave = new ToDateTime(Long.parseLong(reportYetToReturn.get(position).getTimeLeave()));
                holder.name.setText(reportYetToReturn.get(position).getName());
                holder.roomNumber.setText(reportYetToReturn.get(position).getRoomNumber());
                holder.leaveTime.setText(dateTimeLeave.getTime());
                holder.reportYetToReturn = reportYetToReturn.get(position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        switch (outPassSource) {
            case OutPassSource.LEAVING_TODAY:
                return reportLeavingToday.size();
            case OutPassSource.LEFT_TODAY:
                return reportLeftToday.size();
            case OutPassSource.RETURNED_TODAY:
                return reportReturnedToday.size();
            case OutPassSource.YET_TO_RETURN:
                return reportYetToReturn.size();
            default:
                return 0;
        }
    }

    public void changeSource(int outPassSource) {
        Log.wtf("SOURCE", outPassSource + "");
        this.outPassSource = outPassSource;
        this.notifyDataSetChanged();
    }

    public int getCurrentSource() {
        return outPassSource;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView roomNumber;
        TextView leaveTime;
        ReportLeavingToday reportLeavingToday;
        ReportReturnedToday reportReturnedToday;
        ReportYetToReturn reportYetToReturn;
        ReportLeftToday reportLeftToday;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_out_pass_report_name);
            roomNumber = itemView.findViewById(R.id.tv_out_pass_report_room_number);
            leaveTime = itemView.findViewById(R.id.tv_out_pass_report_leave_time);
            Log.wtf("SOURCEee", getCurrentSource() + "");
            switch (getCurrentSource()) {
                case OutPassSource.LEAVING_TODAY:
                    leavingToday(itemView);
                    break;
                case OutPassSource.LEFT_TODAY:
                    leftToday(itemView);
                    break;
                case OutPassSource.RETURNED_TODAY:
                    returnedToday(itemView);
                    break;
                case OutPassSource.YET_TO_RETURN:
                    yetToReturn(itemView);
                    break;
            }
        }

        private void leavingToday(View itemView) {
            itemView.setOnClickListener(view -> {
                Intent intent = null;
                switch (reportLeavingToday.getOutPassType()) {
                    case OutPassType.DAY:
                        intent = new Intent(itemView.getContext(), DayPassViewActivity.class);
                        break;
                    case OutPassType.DAY_COLLEGE_HOURS:
                        intent = new Intent(itemView.getContext(), DayCollegeHoursPassViewActivity.class);
                        break;
                    case OutPassType.NIGHT:
                        intent = new Intent(itemView.getContext(), NightPassViewActivity.class);
                        break;
                }
                if (intent != null) {
                    intent.putExtra(OutPassAttributes.ID, reportLeavingToday.getId());
                    intent.putExtra(OutPassSource.LABEL, OutPassSource.LEAVING_TODAY);
                    intent.putExtra(OutPassSource.SHOW_QR, false);
                    itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(itemView.getContext(), "Invalid Out Pass", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void returnedToday(View itemView) {
            itemView.setOnClickListener(view -> {
                Intent intent = null;
                switch (reportReturnedToday.getOutPassType()) {
                    case OutPassType.DAY:
                        intent = new Intent(itemView.getContext(), DayPassViewActivity.class);
                        break;
                    case OutPassType.DAY_COLLEGE_HOURS:
                        intent = new Intent(itemView.getContext(), DayCollegeHoursPassViewActivity.class);
                        break;
                    case OutPassType.NIGHT:
                        intent = new Intent(itemView.getContext(), NightPassViewActivity.class);
                        break;
                }
                if (intent != null) {
                    intent.putExtra(OutPassAttributes.ID, reportReturnedToday.getId());
                    intent.putExtra(OutPassSource.LABEL, OutPassSource.RETURNED_TODAY);
                    intent.putExtra(OutPassSource.SHOW_QR, false);
                    itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(itemView.getContext(), "Invalid Out Pass", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void yetToReturn(View itemView) {
            itemView.setOnClickListener(view -> {
                Intent intent = null;
                switch (reportYetToReturn.getOutPassType()) {
                    case OutPassType.DAY:
                        intent = new Intent(itemView.getContext(), DayPassViewActivity.class);
                        break;
                    case OutPassType.DAY_COLLEGE_HOURS:
                        intent = new Intent(itemView.getContext(), DayCollegeHoursPassViewActivity.class);
                        break;
                    case OutPassType.NIGHT:
                        intent = new Intent(itemView.getContext(), NightPassViewActivity.class);
                        break;
                }
                if (intent != null) {
                    intent.putExtra(OutPassAttributes.ID, reportYetToReturn.getId());
                    intent.putExtra(OutPassSource.LABEL, OutPassSource.YET_TO_RETURN);
                    intent.putExtra(OutPassSource.SHOW_QR, false);
                    itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(itemView.getContext(), "Invalid Out Pass", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void leftToday(View itemView) {
            itemView.setOnClickListener(view -> {
                Intent intent = null;
                switch (reportLeftToday.getOutPassType()) {
                    case OutPassType.DAY:
                        intent = new Intent(itemView.getContext(), DayPassViewActivity.class);
                        break;
                    case OutPassType.DAY_COLLEGE_HOURS:
                        intent = new Intent(itemView.getContext(), DayCollegeHoursPassViewActivity.class);
                        break;
                    case OutPassType.NIGHT:
                        intent = new Intent(itemView.getContext(), NightPassViewActivity.class);
                        break;
                }
                if (intent != null) {
                    intent.putExtra(OutPassAttributes.ID, reportLeftToday.getId());
                    intent.putExtra(OutPassSource.LABEL, OutPassSource.LEFT_TODAY);
                    intent.putExtra(OutPassSource.SHOW_QR, false);
                    itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(itemView.getContext(), "Invalid Out Pass", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
