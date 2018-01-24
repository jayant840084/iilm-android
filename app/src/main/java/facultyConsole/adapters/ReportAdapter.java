/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package facultyConsole.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.models.OutPassModel;

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

    private List<OutPassModel> mData;
    private int outPassSource;

    public ReportAdapter(List<OutPassModel> data, int outPassSource) {
        this.mData = data;
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
        ToDateTime dateTimeLeave = new ToDateTime(Long.parseLong(mData.get(position).getTimeLeave()));
        holder.name.setText(mData.get(position).getName());
        holder.roomNumber.setText(mData.get(position).getRoomNumber());
        holder.leaveTime.setText(dateTimeLeave.getTime());
        holder.outPass = mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateData(List<OutPassModel> data, int outPassSource) {
        this.outPassSource = outPassSource;
        this.mData.clear();
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView roomNumber;
        TextView leaveTime;
        OutPassModel outPass;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_out_pass_report_name);
            roomNumber = itemView.findViewById(R.id.tv_out_pass_report_room_number);
            leaveTime = itemView.findViewById(R.id.tv_out_pass_report_leave_time);
            itemView.setOnClickListener(view -> {
                Intent intent = null;
                switch (outPass.getOutPassType()) {
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
                    intent.putExtra(OutPassAttributes.ID, outPass.getId());
                    intent.putExtra(OutPassSource.LABEL, outPassSource);
                    intent.putExtra(OutPassSource.SHOW_QR, false);
                    itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(itemView.getContext(), "Invalid Out Pass", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
