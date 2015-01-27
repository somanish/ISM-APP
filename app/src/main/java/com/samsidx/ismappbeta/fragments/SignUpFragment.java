package com.samsidx.ismappbeta.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.parse.ParseException;
import com.parse.SignUpCallback;
import com.samsidx.ismappbeta.R;
import com.samsidx.ismappbeta.data.User;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by samsidx(Majeed Siddiqui) on 17/1/15.
 */
public class SignUpFragment extends BaseFragment
        implements View.OnClickListener{

    private static final int MIN_PASSWORD_LENGTH = 6;

    @InjectView(R.id.sign_up_first_name) EditText mFirstNameField;
    @InjectView(R.id.sign_up_last_name) EditText mLastNameField;
    @InjectView(R.id.sign_up_gender) RadioGroup mGenderGroup;
    @InjectView(R.id.sign_up_email) EditText mEmailField;
    @InjectView(R.id.sign_up_pass) EditText mPassField;
    @InjectView(R.id.sign_up_btn) Button mSignUpBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.inject(this, view);

        mSignUpBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        String firstName = mFirstNameField.getText().toString();
        String lastName = mLastNameField.getText().toString();
        String email = mEmailField.getText().toString();
        String password = mPassField.getText().toString();

        // check for firstName emptiness
        if (firstName.isEmpty()) {
            showAlert(R.string.error_empty_first_name);
            return;
        }

        // check for lastName emptiness
        if (lastName.isEmpty()) {
            showAlert(R.string.error_empty_last_name);
            return;
        }

        // check for email emptiness
        if (email.isEmpty()) {
            showAlert(R.string.error_empty_email);
            return;
        }

        // check for password emptiness
        if (password.isEmpty()) {
            showAlert(R.string.error_empty_pass);
            return;
        }

        // check for length of password
        if (password.length() < MIN_PASSWORD_LENGTH) {
            showAlert(R.string.error_min_pass_len);
            return;
        }

        // create new user user
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        // currently, set hardcoded role
        // after we will use buttons for switching b/w student and prof
        user.setRole(User.ROLE_STUDENT);

        // setting gender
        int gender = mGenderGroup.getCheckedRadioButtonId();
        if (gender == R.id.sign_up_gender_female) {
            user.setGender(User.GENDER_FEMALE);
        }
        else if (gender == R.id.sing_up_gender_male) {
            user.setGender(User.GENDER_MALE);
        }
        else {
            showAlert(R.string.error_gender_not_set);
            return;
        }

        user.setEmail(email);

        // using email as username
        user.setUsername(email);
        user.setPassword(password);

        loadingStart(R.string.loading_title_sign_up, R.string.loading_message_sign_up);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                loadingFinish();
                if (e == null) {
                    // success
                    successSignUp();
                } else {
                    switch (e.getCode()) {
                        case ParseException.INVALID_EMAIL_ADDRESS:
                            showAlert(R.string.error_invalid_email);
                            break;
                        // for sign up user name and email are same,
                        // so error message will be same
                        case ParseException.USERNAME_TAKEN:
                        case ParseException.EMAIL_TAKEN:
                            showAlert(R.string.error_email_taken);
                            break;
                        default:
                            showAlert(R.string.error_sign_up_unknown);
                    }
                }
            }
        });
    }

    private void successSignUp() {
        // TODO: launch main activity
        showConfirm(R.string.confirm_sign_up_success);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
    }
}
