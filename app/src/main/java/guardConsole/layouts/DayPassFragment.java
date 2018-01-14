/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package guardConsole.layouts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.MyPicasso;
import net.UrlGenerator;

import constants.OutPassAttributes;
import in.ac.iilm.iilm.R;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import utils.ToDateTime;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayPassFragment extends Fragment {

    public DayPassFragment() {
        // Required empty public constructor
    }

    public static DayPassFragment newInstance(Bundle args) {
        DayPassFragment fragment = new DayPassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guard_day_pass_info, container, false);

        MyPicasso.with(getContext())
                .load(UrlGenerator.getUrlProfilePic(getArguments()
                        .getString(OutPassAttributes.UID)))
                .placeholder(R.color.bgColor)
                .error(R.drawable.profile_placeholder)
                .transform(new CropCircleTransformation())
                .into((ImageView) view.findViewById(R.id.iv_guard_profile_pic));

        TextView isAllowedView = view.findViewById(R.id.guard_pass_info_allowed);

        if (getArguments().getBoolean(OutPassAttributes.ALLOWED)) {
            isAllowedView.setTextColor(ContextCompat.getColor(getContext(), R.color.allow));
            isAllowedView.setText(getString(R.string.allowed).toUpperCase());

            ToDateTime dateTimeLeave = new ToDateTime(
                    Long.parseLong(getArguments().getString(
                            OutPassAttributes.TIME_STAMP_LEAVE)));
            ToDateTime dateTimeReturn = new ToDateTime(
                    Long.parseLong(getArguments().getString(
                            OutPassAttributes.TIME_STAMP_RETURN)));

            ((TextView) view.findViewById(R.id.guard_pass_info_name))
                    .setText(getArguments().getString(OutPassAttributes.NAME));

            ((TextView) view.findViewById(R.id.guard_pass_info_date_leave))
                    .setText(dateTimeLeave.getDate());

            ((TextView) view.findViewById(R.id.tguard_pass_info_time_leave))
                    .setText(dateTimeLeave.getTime());

            ((TextView) view.findViewById(R.id.guard_pass_info_time_return))
                    .setText(dateTimeReturn.getTime());

            ((TextView) view.findViewById(R.id.guard_pass_info_address))
                    .setText(getArguments().getString(OutPassAttributes.ADDRESS));

            ((TextView) view.findViewById(R.id.guard_pass_info_phone_number))
                    .setText(getArguments().getString(OutPassAttributes.PHONE_NUMBER));

            ((TextView) view.findViewById(R.id.guard_pass_info_reason))
                    .setText(getArguments().getString(OutPassAttributes.REASON));


            TextView wardenApproved = view.findViewById(R.id.guard_pass_info_warden);
            final String wardenSigned = getArguments().getString(OutPassAttributes.WARDEN_SIGNED);
            if (wardenSigned != null) {
                if (Boolean.parseBoolean(wardenSigned)) {
                    wardenApproved.setText(getString(R.string.allowed));
                    wardenApproved.setTextColor(ContextCompat.getColor(getContext(), R.color.allow));
                } else {
                    wardenApproved.setText(getString(R.string.denied));
                    wardenApproved.setTextColor(ContextCompat.getColor(getContext(), R.color.deny));
                }
            } else {
                wardenApproved.setText(getString(R.string.waiting));
                wardenApproved.setTextColor(ContextCompat.getColor(getContext(), R.color.waiting));
            }

        } else {
            isAllowedView.setTextColor(ContextCompat.getColor(getContext(), R.color.deny));
            isAllowedView.setText(getString(R.string.denied).toUpperCase());
        }

        return view;
    }

}
