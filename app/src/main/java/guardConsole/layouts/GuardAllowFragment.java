/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package guardConsole.layouts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.MyPicasso;
import net.UrlGenerator;
import net.models.GuardLogModel;

import in.ac.iilm.iilm.R;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import utils.GuardPassHelper;
import utils.PassHelper;
import utils.ToDateTime;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuardAllowFragment extends Fragment {

    private GuardLogModel guardLogModel;

    public GuardAllowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_guard_allow, container, false);

        guardLogModel = GuardPassHelper.getGuardLogModel();

        MyPicasso.with(getContext())
                .load(UrlGenerator.getUrlProfilePic(guardLogModel.getUid()))
                .placeholder(R.color.bgColor)
                .error(R.drawable.profile_placeholder)
                .transform(new CropCircleTransformation())
                .into((ImageView) view.findViewById(R.id.iv_guard_profile_pic));

        new PassHelper(getContext()).setStatus(
                guardLogModel.isAllowed(),
                view.findViewById(R.id.guard_pass_info_allowed));

        final ToDateTime dateTimeLeave = new ToDateTime(
                Long.parseLong(guardLogModel.getTimeLeave()));
        final ToDateTime dateTimeReturn = new ToDateTime(
                Long.parseLong(guardLogModel.getTimeReturn()));

        ((TextView) view.findViewById(R.id.guard_pass_info_name))
                .setText(guardLogModel.getName());

        ((TextView) view.findViewById(R.id.guard_pass_info_date_leave))
                .setText(dateTimeLeave.getDate());

        ((TextView) view.findViewById(R.id.tguard_pass_info_time_leave))
                .setText(dateTimeLeave.getTime());

        ((TextView) view.findViewById(R.id.guard_pass_info_date_return))
                .setText(dateTimeReturn.getDate());

        ((TextView) view.findViewById(R.id.guard_pass_info_time_return))
                .setText(dateTimeReturn.getTime());

        return view;
    }
}
