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
import android.widget.TextView;
import android.widget.Toast;

import net.models.OutPassModel;

import java.util.List;

import constants.OutPassAttributes;
import constants.OutPassSource;
import constants.OutPassType;
import constants.UserAttributes;
import constants.UserRoles;
import facultyConsole.DayPassResponseActivity;
import facultyConsole.DayWorkingPassResponseActivity;
import facultyConsole.NightPassResponseActivity;
import in.ac.iilm.iilm.R;
import studentConsole.DayCollegeHoursPassViewActivity;
import studentConsole.DayPassViewActivity;
import studentConsole.NightPassViewActivity;
import utils.ToDateTime;
import utils.UserInformation;

/**
 * Created by Jayant Singh on 17-11-2016.
 * <p>
 * Adapter for ToSignFragment RecyclerView
 */

public class ToSignAdapter extends RecyclerView.Adapter<ToSignAdapter.ViewHolder> {

    private Context context;
    private List<OutPassModel> mData;

    public ToSignAdapter(Context context, List<OutPassModel> data) {
        this.context = context;
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
        holder.outPass = mData.get(position);
        holder.setBackgroundForPass();
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
        OutPassModel outPass;

        ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_oupass_history_name);
            date = itemView.findViewById(R.id.tv_oupass_history_date);
            time = itemView.findViewById(R.id.tv_oupass_history_time);
            year = itemView.findViewById(R.id.tv_oupass_history_year);
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
                    intent.putExtra(OutPassSource.LABEL, OutPassSource.OUT_PASS_TO_SIGN);
                    intent.putExtra(OutPassSource.SHOW_QR, false);
                    itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(itemView.getContext(), "Invalid Out Pass", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setBackgroundForPass() {
            switch (UserInformation.getString(context, UserInformation.StringKey.SCOPE)) {
                case UserRoles.DIRECTOR:
                    if (outPass.getWardenSigned() == null ||
                            outPass.getHodSigned() == null) {
                        itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.bgColor));
                    } else if (!outPass.getWardenSigned() ||
                            !outPass.getHodSigned()) {
                        itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.bgColor));
                    }
            }
        }
    }
}
