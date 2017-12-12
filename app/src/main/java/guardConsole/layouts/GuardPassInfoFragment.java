package guardConsole.layouts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.ac.iilm.iilm.R;

public class GuardPassInfoFragment extends Fragment {
    private static final String ARG_ALLOWED = "isAllowed";
    private static final String ARG_NAME = "name";
    private static final String ARG_DATE_LEAVE = "dateLeave";
    private static final String ARG_TIME_LEAVE = "timeLeave";
    private static final String ARG_DATE_RETURN = "dateReturn";
    private static final String ARG_TIME_RETURN = "timeReturn";
    private static final String ARG_ADDRESS = "address";
    private static final String ARG_PHONE_NUMBER = "phoneNumber";
    private static final String ARG_REASON = "reason";
    private static final String ARG_DIRECTOR_SIGNED = "directorSigned";
    private static final String ARG_HOD_SIGNED = "hodSigned";
    private static final String ARG_WARDEN_SIGNED = "wardenSigned";

    private boolean mAllowed;


    public GuardPassInfoFragment() {
        // Required empty public constructor
    }

    public static GuardPassInfoFragment newInstance(Bundle args) {
        GuardPassInfoFragment fragment = new GuardPassInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAllowed = getArguments().getBoolean(ARG_ALLOWED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guard_pass_info, container, false);

        TextView isAllowedView = view.findViewById(R.id.guard_pass_info_allowed);

        if (mAllowed) {

            isAllowedView.setTextColor(ContextCompat.getColor(getContext(), R.color.allow));
            isAllowedView.setText("ALLOWED");

            ((TextView) view.findViewById(R.id.guard_pass_info_name))
                    .setText(getArguments().getString(ARG_NAME));

            ((TextView) view.findViewById(R.id.guard_pass_info_date_leave))
                    .setText(getArguments().getString(ARG_DATE_LEAVE));

            ((TextView) view.findViewById(R.id.tguard_pass_info_time_leave))
                    .setText(getArguments().getString(ARG_TIME_LEAVE));

            ((TextView) view.findViewById(R.id.guard_pass_info_date_return))
                    .setText(getArguments().getString(ARG_DATE_RETURN));

            ((TextView) view.findViewById(R.id.guard_pass_info_time_return))
                    .setText(getArguments().getString(ARG_TIME_RETURN));

            ((TextView) view.findViewById(R.id.guard_pass_info_address))
                    .setText(getArguments().getString(ARG_ADDRESS));

            ((TextView) view.findViewById(R.id.guard_pass_info_phone_number))
                    .setText(getArguments().getString(ARG_PHONE_NUMBER));

            ((TextView) view.findViewById(R.id.guard_pass_info_reason))
                    .setText(getArguments().getString(ARG_REASON));

            TextView directorApprove = view.findViewById(R.id.guard_pass_info_director);
            if (getArguments().getString(ARG_DIRECTOR_SIGNED) != null) {
                if (getArguments().getBoolean(ARG_DIRECTOR_SIGNED)) {
                    directorApprove.setText("ALLOWED");
                    directorApprove.setTextColor(ContextCompat.getColor(getContext(), R.color.allow));
                } else {
                    directorApprove.setText("DENIED");
                    directorApprove.setTextColor(ContextCompat.getColor(getContext(), R.color.deny));
                }
            } else {
                directorApprove.setText("WAITING");
                directorApprove.setTextColor(ContextCompat.getColor(getContext(), R.color.waiting));
            }

            TextView hodApproved = view.findViewById(R.id.guard_pass_info_hod);
            if (getArguments().getString(ARG_HOD_SIGNED) != null) {
                if (getArguments().getBoolean(ARG_HOD_SIGNED)) {
                    hodApproved.setText("ALLOWED");
                    hodApproved.setTextColor(ContextCompat.getColor(getContext(), R.color.allow));
                } else {
                    hodApproved.setText("DENIED");
                    hodApproved.setTextColor(ContextCompat.getColor(getContext(), R.color.deny));
                }
            } else {
                hodApproved.setText("WAITING");
                hodApproved.setTextColor(ContextCompat.getColor(getContext(), R.color.waiting));
            }

            TextView wardenApproved = view.findViewById(R.id.guard_pass_info_warden);
            if (getArguments().getString(ARG_WARDEN_SIGNED) != null) {
                if (getArguments().getBoolean(ARG_WARDEN_SIGNED)) {
                    wardenApproved.setText("ALLOWED");
                    wardenApproved.setTextColor(ContextCompat.getColor(getContext(), R.color.allow));
                } else {
                    wardenApproved.setText("DENIED");
                    wardenApproved.setTextColor(ContextCompat.getColor(getContext(), R.color.deny));
                }
            } else {
                wardenApproved.setText("WAITING");
                wardenApproved.setTextColor(ContextCompat.getColor(getContext(), R.color.waiting));
            }

        } else {
            isAllowedView.setTextColor(ContextCompat.getColor(getContext(), R.color.deny));
            isAllowedView.setText("DENIED");
        }

        return view;
    }
}
