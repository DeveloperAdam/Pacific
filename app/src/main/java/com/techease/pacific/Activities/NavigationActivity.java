package com.techease.pacific.Activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.techease.pacific.Fragments.CompletedOrdersFragment;
import com.techease.pacific.Fragments.HelpFragment;
import com.techease.pacific.Fragments.HomeFragment;
import com.techease.pacific.Fragments.SettingFragment;
import com.techease.pacific.R;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Fragment fragment;
    TextView tvUserName;
    String getUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);

        sharedPreferences =getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        getUserName=sharedPreferences.getString("name","");
        tvUserName=headerLayout.findViewById(R.id.tvUserName);
         tvUserName.setText(getUserName);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        fragment=new HomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.navContainer,fragment).commit();
        setTitle("HOME");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            fragment=new HomeFragment();
            getFragmentManager().beginTransaction().replace(R.id.navContainer,fragment).commit();
            setTitle("HOME");
        } else if (id == R.id.logout) {
            editor.clear();
            editor.putString("token","").clear().commit();
            editor.putString("token","").clear().commit();
            startActivity(new Intent(NavigationActivity.this,FullscreenActivity.class));
            finish();

        } else if (id == R.id.setting) {
            fragment=new SettingFragment();
            getFragmentManager().beginTransaction().replace(R.id.navContainer,fragment).commit();
            setTitle("SETTING");
        } else if (id == R.id.help) {
            fragment=new HelpFragment();
            getFragmentManager().beginTransaction().replace(R.id.navContainer,fragment).commit();
            setTitle("HELP");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
