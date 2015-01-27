package com.samsidx.ismappbeta.activities;

import android.app.Fragment;

import com.samsidx.ismappbeta.fragments.SignUpFragment;

public class SignUpActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new SignUpFragment();
    }
}
