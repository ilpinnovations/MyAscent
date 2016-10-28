package ilp.innovations.tcs.com.myascent.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ilp.innovations.tcs.com.myascent.R;
import ilp.innovations.tcs.com.myascent.beans.UserBean;
import ilp.innovations.tcs.com.myascent.fragments.DisplayScheduleFragment;
import ilp.innovations.tcs.com.myascent.fragments.InformationFragment;
import ilp.innovations.tcs.com.myascent.fragments.UserInformationFragment;
import ilp.innovations.tcs.com.myascent.utilities.ManageUserDetails;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar toolbar;
    private boolean mFlag = true;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout1);

        UserBean userBean = new ManageUserDetails().getUserDetails(MainActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        View header = nav_view.getHeaderView(0);
        TextView textView = (TextView) header.findViewById(R.id.user_salutation);
        TextView textView1 = (TextView) header.findViewById(R.id.user_salutation1);
        textView.setText(userBean.getUserName());
        textView1.setText(userBean.getEmail());


    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayScheduleFragment dsf = new DisplayScheduleFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container1, dsf, "").commit();
        toolbar.setTitle("Schedule");
        navigationView.setCheckedItem(R.id.nav_schedule);
        mFlag = true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mFlag) {
                finish();
            } else {
                mFlag = true;
                navigationView.setCheckedItem(R.id.nav_schedule);
                toolbar.setTitle("Schedule");
                DisplayScheduleFragment dsf = new DisplayScheduleFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container1, dsf, "").commit();
            }
        }


    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user_details) {
            mFlag = false;
            toolbar.setTitle("User Details");
            UserInformationFragment uif = new UserInformationFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container1, uif);
            ft.commit();


        } else if (id == R.id.nav_schedule) {
            mFlag = true;
            toolbar.setTitle("Schedule");
            DisplayScheduleFragment btf = new DisplayScheduleFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container1, btf, "");
            ft.commit();


        } else if (id == R.id.nav_information) {
            mFlag = false;
            toolbar.setTitle("Help");
            InformationFragment mIf = new InformationFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container1, mIf);
            ft.commit();

        } else if (id == R.id.nav_logout) {
            new ManageUserDetails().clearUserDetails(MainActivity.this);
            finish();

        } else if (id == R.id.nav_rate) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "ilp.innovations.tcs.com.myascent&hl=en"));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
