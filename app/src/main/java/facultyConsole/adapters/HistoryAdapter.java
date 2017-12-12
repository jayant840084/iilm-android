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

import net.models.OutPassModel;

import java.util.List;

import constants.OutPassAttributes;
import facultyConsole.PassResponseActivity;
import in.ac.iilm.iilm.R;
import utils.ToDateTime;

/**
 * Created by Jayant Singh on 19-11-2016.
 *
 * Adapter for HistoryFragment RecyclerView
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mContext;
    private List<OutPassModel> mOutPassList;
    private String mScope;

    public HistoryAdapter(Context context, List<OutPassModel> outPassList, String scope) {
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
                    setMessage(mOutPassList.get(position).getDirectorSigned(), holder);
                    break;
                case "hod":
                    setMessage(mOutPassList.get(position).getHodSigned(), holder);
                    break;
                case "warden":
                    setMessage(mOutPassList.get(position).getWardenSigned(), holder);
                    break;
            }
        } catch (NullPointerException ignore) {}
        holder.outPassResponseData = mOutPassList.get(position);
    }

    @Override
    public int getItemCount() {
        return mOutPassList.size();
    }

    public void updateDataSet(List<OutPassModel> outPasses) {
        mOutPassList.clear();
        mOutPassList.addAll(outPasses);
        this.notifyDataSetChanged();
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

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView address;
        TextView time;
        TextView status;
        LinearLayout stateIndicator;
        OutPassModel outPassResponseData;

        ViewHolder(final View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_oupass_history_date);
            time = itemView.findViewById(R.id.tv_oupass_history_time);
            address = itemView.findViewById(R.id.tv_oupass_history_address);
            status = itemView.findViewById(R.id.tv_oupass_history_status);
            stateIndicator = itemView.findViewById(R.id.ll_state_indicator);
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
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
