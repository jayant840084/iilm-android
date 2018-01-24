/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package guardConsole.layouts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.ac.iilm.iilm.R;
import utils.GuardPassHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuardDenyFragment extends Fragment {

    public GuardDenyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guard_deny, container, false);

        ((TextView) view.findViewById(R.id.guard_pass_denied_message))
                .setText(GuardPassHelper.getMessage());

        return view;
    }

}
