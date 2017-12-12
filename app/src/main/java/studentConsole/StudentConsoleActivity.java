package studentConsole;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.MyPicasso;
import net.UrlGenerator;

import in.ac.iilm.iilm.R;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import studentConsole.layouts.HistoryFragment;
import studentConsole.layouts.RequestFragment;
import studentConsole.layouts.SettingsFragment;
import utils.ActivityTracker;
import utils.UserInformation;

public class StudentConsoleActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Out Pass");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        MyPicasso.with(this)
                .load(UrlGenerator.getUrl("profile-img"))
                .error(R.drawable.profile_placeholder)
                .resize(256, 256)
                .centerCrop()
                .transform(new CropCircleTransformation())
                .into((ImageView) header.findViewById(R.id.iv_nav_profile_pic));

        TextView userName = header.findViewById(R.id.user_name);
        userName.setText(UserInformation.getString(this, UserInformation.StringKey.NAME));

        TextView userUID = header.findViewById(R.id.user_email);
        userUID.setText(UserInformation.getString(this, UserInformation.StringKey.EMAIL));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContent, new HistoryFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        for (int index = 0; index < menu.size(); index++) {
            MenuItem item = menu.getItem(index);
            item.getActionView().setBackgroundColor(ContextCompat.getColor(this, R.color.waiting));
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (!item.isChecked()) {
            getSupportActionBar().setTitle(item.getTitle());
            if (id == R.id.nav_outpass) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContent, new RequestFragment())
                        .commit();
                toolbar.setTitle(R.string.nav_outpass_request);
            } else if (id == R.id.nav_outpass_history) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContent, new HistoryFragment())
                        .commit();
                toolbar.setTitle(R.string.nav_outpass_history);
            } else if (id == R.id.nav_settings) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContent, new SettingsFragment())
                        .commit();
                toolbar.setTitle(R.string.nav_settings);
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityTracker.setActivityRunning(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityTracker.setActivityRunning(false);
    }
}
