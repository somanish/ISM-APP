package com.samsidx.ismappbeta.interfaces;

import com.samsidx.ismappbeta.data.Area;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by samsidx(Majeed Siddiqui) on 18/1/15.
 */
public interface PinCodeService {

    // query pincode to getpincodes.info
    @GET("/api.php")
    public void fetchAreas(@Query("pincode") int pinCode, Callback<List<Area>> response);
}
