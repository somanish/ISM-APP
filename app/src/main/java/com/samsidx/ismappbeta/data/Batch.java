package com.samsidx.ismappbeta.data;

import com.parse.ParseClassName;

/**
 * Created by samsidx(Majeed Siddiqui) on 14/1/15.
 * <p/>
 * This cluster denotes batch of particular yr, stream
 * e.g. 1. CSE 2k16 denotes 3rd yr, CSE in 2014-15 session
 * 2. For a new year 2015 we need to create batch(for each stream) like, CSE 2k19, ECE 2k19 etc.
 * <p/>
 * Batch and Club currently have same features inherited from Cluster class
 */

@ParseClassName("Batch")
public class Batch extends Cluster {

    /**
     *  In future, extra features for batch can be added
     *  also our backend will have separate table/class named Club instead of more
     *  general Cluster
     */
}
