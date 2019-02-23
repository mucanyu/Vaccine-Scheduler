package com.sourcey.materiallogindemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.sourcey.materiallogindemo.Fragments.OfficialScheduleFragment;
import com.sourcey.materiallogindemo.Fragments.VaccineScheduleFragment;
import com.sourcey.materiallogindemo.Fragments.myChildrenFragment;


public class MainActivity extends AppCompatActivity {
    public BottomBar bottomBar;
    public Fragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CustomAdapter adapter;

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        final BottomBarTab item1 = bottomBar.getTabWithId(R.id.tab_mySchedule);

        final BottomBarTab item2 = bottomBar.getTabWithId(R.id.tab_myChildrens);

        final BottomBarTab item3 = bottomBar.getTabWithId(R.id.tab_government);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_mySchedule) {
                    myFragment = new VaccineScheduleFragment();
                    setFragment(myFragment);
                }
                else if (tabId == R.id.tab_myChildrens) {
                    myFragment = new myChildrenFragment();
                    setFragment(myFragment);
                }
                else if (tabId == R.id.tab_government) {
                    myFragment = new OfficialScheduleFragment();
                    setFragment(myFragment);
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_myChildrens) {
                    // The tab with id R.id.tab_favorites was reselected,
                    // change your content accordingly.
                    myFragment = new myChildrenFragment();
                    setFragment(myFragment);
                } else if (tabId == R.id.tab_mySchedule) {
                    // The tab with id R.id.tab_favorites was reselected,
                    // change your content accordingly.
                    myFragment = new VaccineScheduleFragment();
                    setFragment(myFragment);
                }
            }
        });
    }

    public void setFragment(Fragment fr) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fr);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void moveToAnotherPage(Class anotherPage) {
        Intent intent = new Intent(getApplicationContext(), anotherPage);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }
}
