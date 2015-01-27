package com.samsidx.ismappbeta.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samsidx(Majeed Siddiqui) on 18/1/15.
 */
public class Area {

    @SerializedName("pincode") private String mPinCode;
    @SerializedName("city") private String mCity;
    @SerializedName("district") private String mDistrict;
    @SerializedName("state") private String mState;

    public String getPincode() {
        return mPinCode;
    }

    public void setPincode(String pincode) {
        mPinCode = pincode;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public void setDistrict(String district) {
        mDistrict = district;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }
}
