package com.samsidx.ismappbeta.data;

import android.net.Uri;

/**
 * Created by samsidx(Majeed Siddiqui) on 19/1/15.
 */
public class NavListItem {

    // for profile view
    private Uri mProfilePicUri;
    private String mFullName;
    private String mAcademicInfo;
    private boolean mIsProfileView;

    // for navigation item view
    private String mItemName;
    private int mItemIconResId;

    // constructor for profile view
    public NavListItem(Uri profilePicUri, String fullName, String academicInfo) {
        mProfilePicUri = profilePicUri;
        mFullName = fullName;
        mAcademicInfo = academicInfo;
        mIsProfileView = true;
    }

    // constructor for navigation item view
    public NavListItem(String itemName, int itemIconResId) {
        mItemName = itemName;
        mItemIconResId = itemIconResId;
    }

    public Uri getProfilePicUri() {
        return mProfilePicUri;
    }

    public void setProfilePicUri(Uri profilePicUri) {
        mProfilePicUri = profilePicUri;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getAcademicInfo() {
        return mAcademicInfo;
    }

    public void setAcademicInfo(String academicInfo) {
        mAcademicInfo = academicInfo;
    }

    public boolean isProfileView() {
        return mIsProfileView;
    }

    public void setProfileView(boolean isProfileView) {
        mIsProfileView = isProfileView;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public int getItemIconResId() {
        return mItemIconResId;
    }

    public void setItemIconResId(int itemIconResId) {
        mItemIconResId = itemIconResId;
    }
}
