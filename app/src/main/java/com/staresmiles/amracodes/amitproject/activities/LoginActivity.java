package com.staresmiles.amracodes.amitproject.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.staresmiles.amracodes.amitproject.R;
import com.staresmiles.amracodes.amitproject.fragments.LoginFragment;

public class LoginActivity extends BaseActivity {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginFragment = new LoginFragment();
        addFragmentToView(loginFragment);
    }


    @Override
    protected boolean isHeaderEnabled() {
        return true;
    }

    @Override
    protected boolean isBackEnabled() {
        return false;
    }

    @Override
    protected boolean isMenuButtonEnabled() {
        return false;
    }
}
