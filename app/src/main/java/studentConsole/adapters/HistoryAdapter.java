/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package studentConsole.adapters;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.models.OutPassModel;

import java.util.List;

import constants.OutPassAttributes;
import constants.OutPassType;
import in.ac.iilm.iilm.R;
import studentConsole.DayPassViewActivity;
import studentConsole.NightPassViewActivity;
import utils.ToDateTime;

/**
 * Created by jayan on 16-11-2016.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<OutPassModel> outPassList;

    public HistoryAdapter(List<OutPassModel> outPassList) {
        this.outPassList = outPassList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_outpass_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDateTime dateTimeLeave = new ToDateTime(Long.parseLong(outPassList.get(position).getTimeLeave()));

        holder.date.setText(dateTimeLeave.getDate());
        holder.time.setText(dateTimeLeave.getTime());
        holder.address.setText(outPassList.get(position).getVisitingAddress());

        Log.d("TYPEEEEEEE", outPassList.get(position).getOutPassType());
        if (outPassList.get(position).getOutPassType().equals(OutPassType.DAY_NOT_COLLEGE_HOURS)) {
            try {
                if (outPassList.get(position).getWardenSigned()) {
                    holder.allowed.setText("ALLOWED");
                    holder.allowed.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.allow));
                    holder.stateIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.allow));
                } else {
                    holder.allowed.setText("DENIED");
                    holder.allowed.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.deny));
                    holder.stateIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.deny));
                }
            } catch (NullPointerException e) {
                holder.allowed.setText("WAITING");
                holder.allowed.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.waiting));
                holder.stateIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.waiting));
            }
        } else {
            try {
                if (outPassList.get(position).getHodSigned()
                        && outPassList.get(position).getDirectorSigned()
                        && outPassList.get(position).getWardenSigned()) {
                    holder.allowed.setText("ALLOWED");
                    holder.allowed.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.allow));
                    holder.stateIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.allow));
                } else {
                    holder.allowed.setText("DENIED");
                    holder.allowed.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.deny));
                    holder.stateIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.deny));
                }
            } catch (NullPointerException e) {
                holder.allowed.setText("WAITING");
                holder.allowed.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.waiting));
                holder.stateIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.waiting));
            }
        }

        holder.outPassResponseData = outPassList.get(position);
    }

    @Override
    public int getItemCount() {
        return outPassList.size();
    }

    public void updateDataSet(List<OutPassModel> outPasses) {
        outPassList = outPasses;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView address;
        TextView time;
        TextView allowed;
        LinearLayout stateIndicator;
        OutPassModel outPassResponseData;

        ViewHolder(final View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_oupass_history_date);
            time = itemView.findViewById(R.id.tv_oupass_history_time);
            address = itemView.findViewById(R.id.tv_oupass_history_address);
            allowed = itemView.findViewById(R.id.tv_oupass_history_allowed);
            stateIndicator = itemView.findViewById(R.id.ll_state_indicator);
            itemView.setOnClickListener(view -> {
                Intent intent;
                if (outPassResponseData.getOutPassType().equals(OutPassType.DAY_NOT_COLLEGE_HOURS)) {
                    intent = new Intent(itemView.getContext(), DayPassViewActivity.class);
                } else {
                    intent = new Intent(itemView.getContext(), NightPassViewActivity.class);
                }
                intent.putExtra(OutPassAttributes.ID, outPassResponseData.getId());
                intent.putExtra(OutPassAttributes.DATE_LEAVE, outPassResponseData.getTimeLeave());
                intent.putExtra(OutPassAttributes.DATE_RETURN, outPassResponseData.getTimeReturn());
                intent.putExtra(OutPassAttributes.ADDRESS, outPassResponseData.getVisitingAddress());
                intent.putExtra(OutPassAttributes.PHONE_NUMBER, outPassResponseData.getPhoneNumber());
                intent.putExtra(OutPassAttributes.REASON, outPassResponseData.getReasonVisit());
                intent.putExtra(OutPassAttributes.HOD_SIGNED, outPassResponseData.getHodSigned());
                intent.putExtra(OutPassAttributes.OUT_PASS_TYPE, outPassResponseData.getOutPassType());
                intent.putExtra(OutPassAttributes.WARDEN_SIGNED, outPassResponseData.getWardenSigned());
                intent.putExtra(OutPassAttributes.DIRECTOR_SIGNED, outPassResponseData.getDirectorSigned());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
