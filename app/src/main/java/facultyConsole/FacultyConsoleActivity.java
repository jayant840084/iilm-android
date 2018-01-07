package facultyConsole;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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

import facultyConsole.layouts.HistoryFragment;
import facultyConsole.layouts.ReportFragment;
import facultyConsole.layouts.SettingsFragment;
import facultyConsole.layouts.ToSignFragment;
import in.ac.iilm.iilm.R;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import utils.UserInformation;

public class FacultyConsoleActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_console);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("To Sign");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        MyPicasso.with(this)
                .load(UrlGenerator.getUrl("profile-img"))
                .error(R.drawable.profile_placeholder)
                .resize(100, 100)
                .centerCrop()
                .transform(new CropCircleTransformation())
                .into((ImageView) header.findViewById(R.id.iv_nav_profile_pic));

        TextView userName = header.findViewById(R.id.user_name);
        userName.setText(UserInformation.getString(this, UserInformation.StringKey.NAME));

        TextView userUID = header.findViewById(R.id.user_uid);
        userUID.setText(UserInformation.getString(this, UserInformation.StringKey.UID));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentConsoleContainer, new ToSignFragment())
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (!item.isChecked()) {
            getSupportActionBar().setTitle(item.getTitle());
            if (id == R.id.nav_console_sign) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentConsoleContainer, new ToSignFragment())
                        .commit();
                toolbar.setTitle(R.string.nav_sign);
            } else if (id == R.id.nav_console_history) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentConsoleContainer, new HistoryFragment())
                        .commit();
                toolbar.setTitle(R.string.nav_console_history);
            } else if (id == R.id.nav_console_report) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentConsoleContainer, new ReportFragment())
                        .commit();
                toolbar.setTitle(R.string.nav_console_report);
            } else if (id == R.id.nav_console_settings) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentConsoleContainer, new SettingsFragment())
                        .commit();
                toolbar.setTitle(R.string.nav_settings);
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
