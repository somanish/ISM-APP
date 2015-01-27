package com.samsidx.ismappbeta.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by samsidx(Majeed Siddiqui) on 14/1/15.
 * <p/>
 * Club and Batch currently have same features inherited from Cluster class
 * <p/>
 * Objects of this class are static and be already created on backend
 * Currently users DO NOT have feature to create their own club
 */

@ParseClassName("Club")
public class Club extends ParseObject {

    /**
     *  In future, extra features for club can be added
     *  also our backend will have separate table/class named Club instead of more
     *  general Cluster
     */
}
