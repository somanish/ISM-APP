package com.samsidx.ismappbeta.appcontroller;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.samsidx.ismappbeta.data.Batch;
import com.samsidx.ismappbeta.data.Club;
import com.samsidx.ismappbeta.data.Cluster;
import com.samsidx.ismappbeta.data.Comment;
import com.samsidx.ismappbeta.data.Contact;
import com.samsidx.ismappbeta.data.Content;
import com.samsidx.ismappbeta.data.ContentWithMedia;
import com.samsidx.ismappbeta.data.Post;
import com.samsidx.ismappbeta.data.User;

/**
 * Created by samsidx(Majeed Siddiqui) on 13/1/15.
 */

/**
 * This class is used for initializing Parse.com BaaS
 */

public class ApplicationController extends Application {

    private static final String PARSE_APPLICATION_ID = "Wl3PSyjArlxaE5dUKiqHlaWr1l4mMA8h8b4mmkM2";
    private static final String PARSE_CLIENT_ID = "Efe65zIMRhoIiqlCxrDUlWHgAgxJPra7tz9tVyC0";
    private static final String PARSE_USER_ID = "userId";

    public static void updatePushNotificationInstallation() {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(PARSE_USER_ID, ParseUser.getCurrentUser().getObjectId());
        installation.saveInBackground();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Parse.com requires subclasses to be explicitly registered
        // NOTE: Parse.com registered class's objects are not serializable

        // register data classes

        // register user class
        ParseUser.registerSubclass(User.class);

        // contact class of user information
        ParseObject.registerSubclass(Contact.class);

        // register batch and club class
        ParseObject.registerSubclass(Batch.class);
        ParseObject.registerSubclass(Club.class);

        // register post and comment class
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Post.class);

        // register content and content with media class
        ParseObject.registerSubclass(Content.class);
        ParseObject.registerSubclass(ContentWithMedia.class);

        // this class won't be created on parse but batch, club will inherit
        // from it batch and club are inherit
        ParseObject.registerSubclass(Cluster.class);

        // enable local data store
//        Parse.enableLocalDatastore(this);

        // initialize parse
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_ID);

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.saveInBackground();
    }
}
