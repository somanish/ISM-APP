package com.samsidx.ismappbeta.data;

import com.samsidx.ismappbeta.interfaces.PinCodeService;

import retrofit.RestAdapter;

/**
 * Created by samsidx(Majeed Siddiqui) on 18/1/15.
 */
public class PinCodeClient {

    private static final String END_POINT = "http://getpincodes.info";
    private PinCodeService mPinCodeService;

    public PinCodeClient() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .build();
        mPinCodeService = adapter.create(PinCodeService.class);
    }

    public PinCodeService getPinCodeService() {
        return mPinCodeService;
    }
}
