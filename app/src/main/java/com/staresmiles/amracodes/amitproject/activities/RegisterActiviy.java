package com.staresmiles.amracodes.amitproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.staresmiles.amracodes.amitproject.R;
import com.staresmiles.amracodes.amitproject.fragments.LoginFragment;
import com.staresmiles.amracodes.amitproject.fragments.RegisterFragment;

public class RegisterActiviy extends BaseActivity {

    private RegisterFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        registerFragment = new RegisterFragment();
        addFragmentToView(registerFragment);
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
