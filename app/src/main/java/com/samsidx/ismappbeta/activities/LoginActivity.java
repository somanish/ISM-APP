package com.samsidx.ismappbeta.activities;

import android.app.Fragment;

import com.samsidx.ismappbeta.fragments.LoginFragment;

/**
 * Created by samsidx(Majeed Siddiqui) on 17/1/15.
 */
public class LoginActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new LoginFragment();
    }
}
