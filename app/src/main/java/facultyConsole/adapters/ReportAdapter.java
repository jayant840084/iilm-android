package facultyConsole.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.models.OutPassModel;

import java.util.List;

import in.ac.iilm.iilm.R;
import utils.ToDateTime;

/**
 * Created by jayant on 7/1/18.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private List<OutPassModel> mData;

    public ReportAdapter(List<OutPassModel> data) {
        this.mData = data;
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateDatasetChanged(List<OutPassModel> data) {
        this.mData.clear();
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView roomNumber;
        TextView leaveTime;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_out_pass_report_name);
            roomNumber = itemView.findViewById(R.id.tv_out_pass_report_room_number);
            leaveTime = itemView.findViewById(R.id.tv_out_pass_report_leave_time);
        }
    }
}
