package studentConsole.layouts;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.MyPicasso;
import net.UrlGenerator;
import net.requests.LogoutRequest;

import auth.ChangePasswordActivity;
import auth.LoginActivity;
import in.ac.iilm.iilm.R;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import utils.ProgressBarUtil;
import utils.UserInformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private ProgressBarUtil mProgressBar;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_settings, container, false);

        MyPicasso.with(getContext())
                .load(UrlGenerator.getUrl("profile-img"))
                .error(R.drawable.profile_placeholder)
                .resize(256, 256)
                .transform(new CropCircleTransformation())
                .into((ImageView) view.findViewById(R.id.iv_settings_profile_pic));

        mProgressBar = new ProgressBarUtil(
                view.findViewById(R.id.logout_progress_background),
                view.findViewById(R.id.logout_progress)
        );

        TextView name = view.findViewById(R.id.tv_settings_name);
        name.setText(UserInformation.getString(getContext(), UserInformation.StringKey.NAME));

        TextView uid = view.findViewById(R.id.tv_settings_uid);
        uid.setText(UserInformation.getString(getContext(), UserInformation.StringKey.UID));

        TextView branch = view.findViewById(R.id.tv_settings_branch);
        branch.setText(UserInformation.getString(getContext(), UserInformation.StringKey.BRANCH));

        TextView year = view.findViewById(R.id.tv_settings_year);
        year.setText(UserInformation.getString(getContext(), UserInformation.StringKey.YEAR));

        TextView roomNumber = view.findViewById(R.id.tv_settings_room_number);
        roomNumber.setText(UserInformation.getString(getContext(), UserInformation.StringKey.ROOM_NUMBER));

        TextView scope = view.findViewById(R.id.tv_settings_scope);
        scope.setText(UserInformation.getString(getContext(), UserInformation.StringKey.SCOPE));

        TextView phoneNumber = view.findViewById(R.id.tv_settings_phone_number);
        phoneNumber.setText(UserInformation.getString(getContext(), UserInformation.StringKey.PHONE_NUMBER));

        TextView gender = view.findViewById(R.id.tv_settings_gender);
        gender.setText(UserInformation.getString(getContext(), UserInformation.StringKey.GENDER));

        Button feedback = view.findViewById(R.id.bt_settings_feedback);
        feedback.setOnClickListener(v -> {
            Intent feedbackIntent = new Intent(Intent.ACTION_SENDTO);
            feedbackIntent.setData(Uri.parse("mailto:iilmcetweb@gmail.com"));

            try {
                startActivity(feedbackIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(),
                        "No email application found", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        Button developerInfo = view.findViewById(R.id.bt_settings_dev_site);
        developerInfo.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://jayantsingh.in"))));

        Button changePassword = view.findViewById(R.id.bt_change_password);
        changePassword.setOnClickListener(v ->
                startActivity(new Intent(getContext(),
                        ChangePasswordActivity.class)));

        Button button = view.findViewById(R.id.btLogout);
        button.setOnClickListener(view1 -> {
            mProgressBar.showProgress();
            new LogoutRequest().execute(getContext(), success -> {
                if (success) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Logout Failed", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.hideProgress();
            });
        });
        return view;
    }

}
