/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package facultyConsole.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import db.FacultySignedPasses;
import io.realm.RealmResults;
import models.OutPassModel;

import java.util.List;

import constants.OutPassAttributes;
import constants.OutPassSource;
import constants.OutPassType;
import facultyConsole.DayPassResponseActivity;
import facultyConsole.DayWorkingPassResponseActivity;
import facultyConsole.NightPassResponseActivity;
import in.ac.iilm.iilm.R;
import utils.ToDateTime;

/**
 * Created by Jayant Singh on 19-11-2016.
 * <p>
 * Adapter for HistoryFragment RecyclerView
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mContext;
    private RealmResults<FacultySignedPasses> mOutPassList;
    private String mScope;

    public HistoryAdapter(Context context, RealmResults<FacultySignedPasses> outPassList, String scope) {
        this.mContext = context;
        this.mOutPassList = outPassList;
        this.mScope = scope;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_console_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDateTime dateTimeLeave = new ToDateTime(
                Long.parseLong(mOutPassList.get(position).getTimeLeave()));
        holder.date.setText(dateTimeLeave.getDate());
        holder.time.setText(dateTimeLeave.getTime());
        holder.address.setText(mOutPassList.get(position).getVisitingAddress());
        try {
            switch (mScope) {
                case "director":
                    setMessage(getDirectorSigned(mOutPassList.get(position)), holder);
                    break;
                case "hod":
                    setMessage(mOutPassList.get(position).getHodSigned(), holder);
                    break;
                case "warden":
                    setMessage(mOutPassList.get(position).getWardenSigned(), holder);
                    break;
            }
        } catch (NullPointerException ignore) {
        }
        holder.outPass = mOutPassList.get(position);
    }

    @Override
    public int getItemCount() {
        return mOutPassList.size();
    }

    private void setMessage(boolean signed, ViewHolder holder) {
        if (signed) {
            setAllowed(holder);
        } else {
            setDenied(holder);
        }
    }

    private void setAllowed(ViewHolder holder) {
        holder.status.setText(mContext.getString(R.string.allowed));
        holder.status.setTextColor(
                ContextCompat.getColor(
                        holder.itemView.getContext(),
                        R.color.allow));
        holder.stateIndicator.setBackgroundColor(
                ContextCompat.getColor(
                        holder.itemView.getContext(),
                        R.color.allow));
    }

    private void setDenied(ViewHolder holder) {
        holder.status.setText(mContext.getString(R.string.denied));
        holder.status.setTextColor(
                ContextCompat.getColor(
                        holder.itemView.getContext(),
                        R.color.deny));
        holder.stateIndicator.setBackgroundColor(
                ContextCompat.getColor(
                        holder.itemView.getContext(),
                        R.color.deny));
    }

    private Boolean getDirectorSigned(FacultySignedPasses outPass) {
        if (outPass.getDirectorPrioritySign() != null &&
                outPass.getDirectorPrioritySign()) {
            return outPass.getDirectorPrioritySign();
        } else {
            return outPass.getDirectorSigned();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView address;
        TextView time;
        TextView status;
        LinearLayout stateIndicator;
        FacultySignedPasses outPass;

        ViewHolder(final View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_oupass_history_date);
            time = itemView.findViewById(R.id.tv_oupass_history_time);
            address = itemView.findViewById(R.id.tv_oupass_history_address);
            status = itemView.findViewById(R.id.tv_oupass_history_status);
            stateIndicator = itemView.findViewById(R.id.ll_state_indicator);
            itemView.setOnClickListener(view -> {
                Intent intent = null;
                switch (outPass.getOutPassType()) {
                    case OutPassType.DAY:
                        intent = new Intent(itemView.getContext(), DayPassResponseActivity.class);
                        break;
                    case OutPassType.DAY_COLLEGE_HOURS:
                        intent = new Intent(itemView.getContext(), DayWorkingPassResponseActivity.class);
                        break;
                    case OutPassType.NIGHT:
                        intent = new Intent(itemView.getContext(), NightPassResponseActivity.class);
                        break;
                }
                if (intent != null) {
                    intent.putExtra(OutPassAttributes.ID, outPass.getId());
                    intent.putExtra(OutPassSource.LABEL, OutPassSource.OUT_PASS_SIGNED);
                    intent.putExtra(OutPassSource.SHOW_QR, false);
                    itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(itemView.getContext(), "Invalid Out Pass", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
