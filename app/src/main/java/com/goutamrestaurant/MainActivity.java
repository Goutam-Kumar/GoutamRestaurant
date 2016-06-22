package com.goutamrestaurant;

import android.support.annotation.BoolRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goutamrestaurant.fragmenthandler.MainFragment;

public class MainActivity extends AppCompatActivity
implements View.OnClickListener{

    private LinearLayout lin_menu;
    private TextView tv_title;
    private FrameLayout frame_container;
    private DrawerLayout drawer_layout;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean isOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lin_menu = (LinearLayout) findViewById(R.id.lin_menu);
        tv_title = (TextView) findViewById(R.id.tv_title);
        frame_container = (FrameLayout) findViewById(R.id.frame_container);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mf = new MainFragment();
        fragmentTransaction.add(R.id.frame_container,mf);
        fragmentTransaction.commit();

        tv_title.setText("Food Court");

        lin_menu.setOnClickListener(this);
        drawer_layout.setDrawerListener(new ActionBarDrawerToggle(this,
                drawer_layout,
                null,
                0,
                0){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                isOpen = false;
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isOpen = true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_menu:
                if (isOpen){
                    drawer_layout.closeDrawer(Gravity.LEFT);
                    isOpen = false;
                }else {
                    drawer_layout.openDrawer(Gravity.LEFT);
                    isOpen = true;
                }
                break;
        }
    }

}
