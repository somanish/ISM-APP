package com.samsidx.ismappbeta.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseRelation;

/**
 * Created by samsidx(Majeed Siddiqui) on 14/1/15.
 * <p/>
 * Common features of groups in app are implemented in this class.
 * A cluster will have subscribed users, with an admin.
 * Anybody who is member of the cluster can post in it.
 * All others will get notified.
 * <p/>
 * Admin can:
 * 1. Approve a join request
 * 2. Kick out some one from group
 * 3. Add another someone as admin
 * <p/>
 * Objects of this class are static and be already created on backend
 * Currently users DO NOT have feature to create their own cluster
 * coming soon :)
 */

@ParseClassName("Cluster")
public class Cluster extends ParseObject {

    public static final String KEY_CLUSTER_NAME = "name"; // name of the cluster
    public static final String KEY_ADMIN = "admin"; // admin of the cluster
    public static final String MEMBERS_RELATION = "members"; // members of cluster
    public static final String POSTS_RELATION = "posts"; // posts in this cluster

    public String getClusterName() {
        return getString(KEY_CLUSTER_NAME);
    }

    public void setClusterName(String clusterName) {
        put(KEY_CLUSTER_NAME, clusterName);
    }

    public User getAdmin() {
        return (User) get(KEY_ADMIN);
    }

    public void setAdmin(User admin) {
        put(KEY_ADMIN, admin);
    }

    public ParseRelation<User> getMembersRelation() {
        return getRelation(MEMBERS_RELATION);
    }

    public ParseRelation<Post> getPostsRelation() {
        return getRelation(POSTS_RELATION);
    }
}
