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

import net.models.OutPassModel;

import java.util.List;

import constants.OutPassAttributes;
import facultyConsole.PassResponseActivity;
import in.ac.iilm.iilm.R;
import utils.ToDateTime;

/**
 * Created by Jayant Singh on 17-11-2016.
 * <p>
 * Adapter for ToSignFragment RecyclerView
 */

public class ToSignAdapter extends RecyclerView.Adapter<ToSignAdapter.ViewHolder> {

    private List<OutPassModel> mData;

    public ToSignAdapter(List<OutPassModel> data) {
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_outpass_console_item,
                parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDateTime dateTimeLeave = new ToDateTime(Long.parseLong(mData.get(position).getTimeLeave()));

        holder.name.setText(mData.get(position).getName());
        holder.date.setText(dateTimeLeave.getDate());
        holder.time.setText(dateTimeLeave.getTime());
        holder.year.setText(String.format("Branch: %s Year: %s",
                mData.get(position).getBranch().toUpperCase(),
                mData.get(position).getYear()));
        holder.outPassResponseData = mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateDataSet(List<OutPassModel> outPasses) {
        mData.clear();
        mData.addAll(outPasses);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView date;
        TextView time;
        TextView year;
        OutPassModel outPassResponseData;

        ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_oupass_history_name);
            date = itemView.findViewById(R.id.tv_oupass_history_date);
            time = itemView.findViewById(R.id.tv_oupass_history_time);
            year = itemView.findViewById(R.id.tv_oupass_history_year);
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), PassResponseActivity.class);
                intent.putExtra(OutPassAttributes.ID, outPassResponseData.getId());
                intent.putExtra(OutPassAttributes.UID, outPassResponseData.getUid());
                intent.putExtra(OutPassAttributes.NAME, outPassResponseData.getName());
                intent.putExtra(OutPassAttributes.PHONE_NUMBER_VISITING, outPassResponseData.getPhoneNumberVisiting());
                intent.putExtra(OutPassAttributes.BRANCH, outPassResponseData.getBranch());
                intent.putExtra(OutPassAttributes.YEAR, outPassResponseData.getYear());
                intent.putExtra(OutPassAttributes.OUT_PASS_TYPE, outPassResponseData.getOutPassType());
                intent.putExtra(OutPassAttributes.ROOM_NUMBER, outPassResponseData.getRoomNumber());
                intent.putExtra(OutPassAttributes.DATE_LEAVE, outPassResponseData.getTimeLeave());
                intent.putExtra(OutPassAttributes.DATE_RETURN, outPassResponseData.getTimeReturn());
                intent.putExtra(OutPassAttributes.ADDRESS, outPassResponseData.getVisitingAddress());
                intent.putExtra(OutPassAttributes.PHONE_NUMBER, outPassResponseData.getPhoneNumber());
                intent.putExtra(OutPassAttributes.REASON, outPassResponseData.getReasonVisit());
                intent.putExtra(OutPassAttributes.DIRECTOR_SIGNED, outPassResponseData.getDirectorSigned());
                intent.putExtra(OutPassAttributes.HOD_SIGNED, outPassResponseData.getHodSigned());
                intent.putExtra(OutPassAttributes.WARDEN_SIGNED, outPassResponseData.getWardenSigned());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
