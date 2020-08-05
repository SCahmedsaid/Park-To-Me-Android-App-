package com.staresmiles.amracodes.amitproject.activities;


import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.staresmiles.amracodes.amitproject.R;
import com.staresmiles.amracodes.amitproject.control.Controller;
import com.staresmiles.amracodes.amitproject.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    private FrameLayout containerFrameLayout;
    private List<BaseFragment> fragmentList;
    private Toolbar toolbar;
    private TextView titleTextView;
    private View backActionView, menuActionView;
    private DrawerLayout drawerLayout;
    private final int DRAWER_CLOSE_DELAY = 300;

    public static String LANGUAGE_AR = "ar";
    public static String LANGUAGE_EN = "en";
    private TextView userName;
    private LinearLayout aboutUs;
    private LinearLayout changeLanguage;
    private LinearLayout logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        fragmentList = new ArrayList<>();  // initialization


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        customizeActionBarActions();
        removeToolbarShadow();
        if (isMenuButtonEnabled()) {
            customizeSideMenu();

        }
        containerFrameLayout = (FrameLayout) findViewById(R.id.container_frame_layout);
    }

    public void removeToolbarShadow() {
        findViewById(R.id.appbar).bringToFront();
    }

    public BaseFragment getCurrentFragment() {
        if (fragmentList.size() > 0) {
            return fragmentList.get(fragmentList.size() - 1);
        } else {
            return null;
        }
    }


    public void addFragmentToView(BaseFragment fragment) {
        if (fragment != null) {
            if (fragment.isFragmentAdded()) {
                View currentView = null;
                if (getCurrentFragment() != null) {
                    currentView = getCurrentFragment().getView();
                }
                if (currentView != null) {
                    currentView.setVisibility(View.GONE);
                }
                getSupportFragmentManager().beginTransaction().
                        add(containerFrameLayout.getId(), fragment).commit();
            } else
                getSupportFragmentManager().beginTransaction().
                        replace(containerFrameLayout.getId(), fragment).
                        commitAllowingStateLoss();

            fragmentList.add(fragment);


        }
    }

    private void customizeActionBarActions() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTextView = (TextView) findViewById(R.id.textView_title);

        if (isHeaderEnabled()) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setContentInsetsAbsolute(0, 0);
            backActionView = findViewById(R.id.action_back);

            if (!isTitleShown()) {
                titleTextView.setVisibility(View.INVISIBLE);
            }

            if (!isBackEnabled()) {
                if (isMenuButtonEnabled())
                    backActionView.setVisibility(View.GONE);
                else
                    backActionView.setVisibility(View.INVISIBLE);
            } else
                backActionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleBackAction();

                    }
                });


        } else {
            toolbar.setVisibility(View.GONE);
        }

        menuActionView = findViewById(R.id.action_menu);
        if (isMenuButtonEnabled()) {
            menuActionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleMenuAction();
                }
            });
        } else {
            menuActionView.setVisibility(View.GONE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }
    protected void customizeSideMenu() {
        userName = (TextView) findViewById(R.id.user_name_txt);
        aboutUs = (LinearLayout) findViewById(R.id.our_services_linear);
        changeLanguage = (LinearLayout) findViewById(R.id.about_us_linear);
        logOut = (LinearLayout) findViewById(R.id.know_your_doctor_Linear);
        if (Controller.getInstance(this).getLoggedUser() != null) {
            userName.setText((Controller.getInstance(this).getLoggedUser().getName()));
        }

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                startActivity(new  In);
            }
        });

        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private boolean isTitleShown() {
        return true;
    }

    public void handleBackAction() {
        if (backActionView != null && (backActionView.getVisibility() == View.GONE || backActionView.getVisibility() == View.INVISIBLE))
            return;

        else {
            proceedWithBack();
        }
    }

    public void proceedWithBack() {
        if (fragmentList.size() > 1) {

            if (fragmentList.get(fragmentList.size() - 1).isFragmentAdded()) {
                getSupportFragmentManager().beginTransaction().remove(fragmentList.get(fragmentList.size() - 1)).commit();
                fragmentList.remove(fragmentList.size() - 1);
                if (getCurrentFragment() != null) {
                    if (getCurrentFragment().getView() != null) {
                        getCurrentFragment().getView().setVisibility(View.VISIBLE);
                    }
                }
            } else {
                fragmentList.remove(fragmentList.size() - 1);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frame_layout, fragmentList.get(fragmentList.size() - 1)).commit();
            }

        } else {

            if (this instanceof LoginActivity) {
                moveTaskToBack(true);
            } else {
                super.onBackPressed();
            }

        }
    }

    private void handleMenuAction() {
        toggleDrawer(false);
    }

    public void toggleDrawer(boolean isCloseDelayed) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        if (conf.locale.getDisplayLanguage().toLowerCase().equals(new Locale(LANGUAGE_AR).getDisplayLanguage().toLowerCase())) {

            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawer(Gravity.LEFT);
                    }
                }, isCloseDelayed ? DRAWER_CLOSE_DELAY : 0);

            } else {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        } else {
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawer(Gravity.RIGHT);
                    }
                }, isCloseDelayed ? DRAWER_CLOSE_DELAY : 0);

            } else {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
    }

    protected boolean isMenuButtonEnabled() {
        return true;
    }

    protected boolean isBackEnabled() {
        return true;
    }

    protected boolean isHeaderEnabled() {
        return true;
    }

    public  void setHeaderName(String s){
        titleTextView.setText(s);
    }
}
