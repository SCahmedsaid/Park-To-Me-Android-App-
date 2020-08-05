package com.staresmiles.amracodes.amitproject.activities;

import android.os.Bundle;

import com.staresmiles.amracodes.amitproject.fragments.HomeFragment;
import com.staresmiles.amracodes.amitproject.fragments.LoginFragment;

/**
 * Created by amra on 5/7/2018.
 */

public class HomeActivity extends BaseActivity {
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeFragment = new HomeFragment();
        addFragmentToView(homeFragment);
    }


    @Override
    protected boolean isHeaderEnabled() {
        return true;
    }


}
