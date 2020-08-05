package com.staresmiles.amracodes.amitproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.staresmiles.amracodes.amitproject.R;
import com.staresmiles.amracodes.amitproject.activities.HomeActivity;
import com.staresmiles.amracodes.amitproject.activities.RegisterActiviy;
import com.staresmiles.amracodes.amitproject.control.Controller;
import com.staresmiles.amracodes.amitproject.control.models.User;


public class LoginFragment extends BaseFragment {

    private View loginFragmentView;
    private EditText userName;
    private EditText password;
    private TextView register;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginFragmentView = inflater.inflate(R.layout.fragment_login, container, false);


        userName = (EditText) loginFragmentView.findViewById(R.id.user_name_edit_txt);
        password = (EditText) loginFragmentView.findViewById(R.id.password_edit_text);
        loginButton = (Button) loginFragmentView.findViewById(R.id.login_button);
        register = (TextView) loginFragmentView.findViewById(R.id.register_txt);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegisterActiviy.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doValidationAndContinue();
            }
        });


        return loginFragmentView;
    }

    private void doValidationAndContinue() {

        User loginUser = null;
        if (userName.getText() == null || userName.
                getText().toString().equals("")) {

            userName.setError(getString(R.string.str_enter_valid_value));

        } else if (password.getText() == null ||
                password.getText().toString().equals("")) {

            password.setError(getString(R.string.str_enter_valid_value));

        } else {
            loginUser = Controller.getInstance(getActivity()).login(userName.getText().toString(), password.getText().toString());
            if (loginUser != null) {
                Controller.getInstance(getActivity()).setLoggedUser(loginUser);
                startActivity(new Intent(getActivity(), HomeActivity.class));
            } else {

                Toast.makeText(getActivity(), getString(R.string.str_invalid_login_message), Toast.LENGTH_LONG).show();
            }
        }


    }


}
