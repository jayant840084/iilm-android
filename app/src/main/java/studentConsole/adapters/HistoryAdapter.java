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
        OutPassModel outPass = outPassList.get(position);

        ToDateTime dateTimeLeave = new ToDateTime(Long.parseLong(outPass.getTimeLeave()));

        holder.date.setText(dateTimeLeave.getDate());
        holder.time.setText(dateTimeLeave.getTime());
        holder.address.setText(outPass.getVisitingAddress());

        switch (outPass.getOutPassType()) {
            case OutPassType.DAY:
                try {
                    if (outPass.getWardenSigned()) {
                        setAllow(holder);
                    } else {
                        setDenied(holder);
                    }
                } catch (NullPointerException e) {
                    setWaiting(holder);
                }
                break;
            case OutPassType.DAY_COLLEGE_HOURS:
                if (outPass.getHodSigned() != null &&
                        !outPass.getHodSigned()) {
                    setDenied(holder);
                } else if (outPass.getWardenSigned() != null &&
                        !outPass.getWardenSigned()) {
                    setDenied(holder);
                } else if (outPass.getHodSigned() != null &&
                        outPass.getWardenSigned() != null) {
                    setAllow(holder);
                } else {
                    setWaiting(holder);
                }
                break;
            case OutPassType.NIGHT:
                if (outPass.getDirectorPrioritySign() != null &&
                        outPass.getDirectorPrioritySign()) {
                    setAllow(holder);
                } else if (outPass.getDirectorSigned() != null &&
                        !outPass.getDirectorSigned()) {
                    setDenied(holder);
                } else if (outPass.getHodSigned() != null &&
                        !outPass.getHodSigned()) {
                    setDenied(holder);
                } else if (outPass.getWardenSigned() != null &&
                        !outPass.getWardenSigned()) {
                    setDenied(holder);
                } else if (outPass.getHodSigned() != null &&
                        outPass.getDirectorSigned() != null &&
                        outPass.getWardenSigned() != null) {
                    setAllow(holder);
                } else {
                    setWaiting(holder);
                }
                break;
        }

        holder.outPassResponseData = outPassList.get(position);
    }

    private void setAllow(ViewHolder holder) {
        holder.allowed.setText("ALLOWED");
        holder.allowed.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.allow));
        holder.stateIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.allow));
    }

    private void setDenied(ViewHolder holder) {
        holder.allowed.setText("DENIED");
        holder.allowed.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.deny));
        holder.stateIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.deny));
    }

    private void setWaiting(ViewHolder holder) {
        holder.allowed.setText("WAITING");
        holder.allowed.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.waiting));
        holder.stateIndicator.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.waiting));
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
                Intent intent = null;
                switch (outPassResponseData.getOutPassType()) {
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
                    intent.putExtra(OutPassAttributes.ID, outPassResponseData.getId());
                    intent.putExtra(OutPassSource.LABEL, OutPassSource.OUT_PASS);
                    intent.putExtra(OutPassSource.SHOW_QR, true);
                    itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(itemView.getContext(), "Invalid Out Pass", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
