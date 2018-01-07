package db;

import android.content.Context;

import net.models.OutPassModel;

import java.util.List;

/**
 * Created by jayant on 7/1/18.
 */

public class ReportCrud {

    private OutPassDbHelper helper;
    private Context context;

    public ReportCrud(Context context, OutPassDbHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    public List<OutPassModel> getLeavingTodayOutPasses() {
        return new OutPassCrudSigned(context, helper).getOutpassesSigned();
    }
}
