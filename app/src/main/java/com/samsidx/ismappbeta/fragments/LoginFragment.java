package com.samsidx.ismappbeta.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.samsidx.ismappbeta.R;
import com.samsidx.ismappbeta.activities.PostActivity;
import com.samsidx.ismappbeta.activities.SignUpActivity;
import com.samsidx.ismappbeta.appcontroller.ApplicationController;
import com.samsidx.ismappbeta.data.User;
import com.samsidx.ismappbeta.helper.NetworkHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by samsidx(Majeed Siddiqui) on 17/1/15.
 */
public class LoginFragment extends BaseFragment
        implements View.OnClickListener {

    @InjectView(R.id.log_in_email) EditText mEmailField; // email edit text
    @InjectView(R.id.log_in_pass) EditText mPassField; // password edit text
    @InjectView(R.id.login_btn) Button mLogInBtn;  // login button
    @InjectView(R.id.sign_up_btn) Button mSignUpBtn; // sign up button

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);

        mLogInBtn.setOnClickListener(this);

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start sign up activity
                Intent signUpActivityIntent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(signUpActivityIntent);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        String username = mEmailField.getText().toString(); // get email/username
        String password = mPassField.getText().toString();  // get password

        // check for username emptiness
        if (username.isEmpty()) {
            showAlert(R.string.error_empty_user_name);
            return;
        }

        // check for password emptiness
        if (password.isEmpty()) {
            showAlert(R.string.error_empty_pass);
            return;
        }

        // check for internet connection
        if (!NetworkHelper.isNetworkAvailable(getActivity())) {
            showAlert(R.string.error_no_internet);
            return;
        }

        // show loading dialog
        loadingStart(
                R.string.loading_title_login,
                R.string.loading_message_login
        );
        User.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // discard loading dialog
                loadingFinish();
                if (user != null) {
                    // if success
                    successLogin();
                } else {
                    // if failure show proper message
                    if (e != null) {
                        e.printStackTrace();
                        if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                            showAlert(R.string.error_invalid_credentials);
                        } else {
                            showAlert(R.string.error_login_unknown);
                        }
                    }
                }
            }
        });
    }

    public void successLogin() {
        // register current user for notification
        ApplicationController.updatePushNotificationInstallation();
//        showConfirm(R.string.confirm_log_in_success);
        // TODO: start main activity
        Intent intent = new Intent(getActivity(), PostActivity.class);
        startActivity(intent);
    }
}
