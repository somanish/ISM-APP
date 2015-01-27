package com.samsidx.ismappbeta.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by samsidx(Majeed Siddiqui) on 18/1/15.
 */
public class BaseFragment extends Fragment {

    private ProgressDialog mProgressDialog;

    protected void showAlert(String text) {
        Crouton.makeText(getActivity(), text, Style.ALERT).show();
    }

    protected void showAlert(int textResId) {
        Crouton.makeText(getActivity(), textResId, Style.ALERT).show();
    }

    protected void showConfirm(String text) {
        Crouton.makeText(getActivity(), text, Style.CONFIRM).show();
    }

    protected void showConfirm(int textResId) {
        Crouton.makeText(getActivity(), textResId, Style.CONFIRM).show();
    }

    protected void showInfo(String text) {
        Crouton.makeText(getActivity(), text, Style.INFO).show();
    }

    protected void showInfo(int textResId) {
        Crouton.makeText(getActivity(), textResId, Style.INFO).show();
    }

    protected void loadingStart(int titleResId, int messageResId) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getActivity().getString(titleResId));
        mProgressDialog.setMessage(getActivity().getString(messageResId));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected void loadingFinish() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
