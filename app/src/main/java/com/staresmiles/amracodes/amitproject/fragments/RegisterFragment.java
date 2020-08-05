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
import android.widget.Toast;

import com.staresmiles.amracodes.amitproject.R;
import com.staresmiles.amracodes.amitproject.activities.LoginActivity;
import com.staresmiles.amracodes.amitproject.control.Controller;
import com.staresmiles.amracodes.amitproject.control.models.User;


public class RegisterFragment extends BaseFragment {

    private View registerView;
    private EditText name;
    private EditText userName;
    private EditText mobile;
    private EditText password;
    private EditText email;
    private EditText city;
    private Button registeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        registerView = inflater.inflate(R.layout.fragment_register,
                container, false);

        name = (EditText) registerView.findViewById(R.id.name_editText);
        userName = (EditText) registerView.findViewById(R.id.user_name_editText);
        password = (EditText) registerView.findViewById(R.id.password_editText);
        email = (EditText) registerView.findViewById(R.id.email_editText);
        mobile = (EditText) registerView.findViewById(R.id.mobile_editText);
        city = (EditText) registerView.findViewById(R.id.city_editText);
        registeButton = (Button) registerView.findViewById(R.id.register_button);
        registeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doValidationAndContinue();
            }
        });

        return registerView;
    }


    private void doValidationAndContinue() {
        User registerUser = null;
        if (name.getText() == null || name.getText().toString().equals("")) {

            name.setError(getString(R.string.str_enter_valid_value));

        } else if (userName.getText() == null || userName.getText().toString().equals("")) {

            userName.setError(getString(R.string.str_enter_valid_value));

        } else if (email.getText() == null || email.getText().toString().equals("")) {

            email.setError(getString(R.string.str_enter_valid_value));
        } else if (mobile.getText() == null || mobile.getText().toString().equals("")) {

            mobile.setError(getString(R.string.str_enter_valid_value));


        } else if (password.getText() == null || password.getText().toString().equals("")) {

            password.setError(getString(R.string.str_enter_valid_value));


        } else if (city.getText() == null || city.getText().toString().equals("")) {

            city.setError(getString(R.string.str_enter_valid_value));

        } else {
            registerUser = new User(name.getText().toString(),
                    userName.getText().toString()
                    , mobile.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString(), city.getText().toString());

           boolean registered =  Controller.
                   getInstance(getActivity()).register(registerUser);

           if(registered){
               Toast.makeText(getActivity(), getString(R.string.str_registered_successfully), Toast.LENGTH_LONG);
               startActivity(new Intent(getActivity(), LoginActivity.class));
           }else{
               Toast.makeText(getActivity(), getString(R.string.str_not_registered_successfully), Toast.LENGTH_LONG);
           }
        }
    }


}
