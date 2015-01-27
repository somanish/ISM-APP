package com.samsidx.ismappbeta.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseRelation;

/**
 * Created by samsidx(Majeed Siddiqui) on 14/1/15.
 * <p/>
 * Post class is extended from ParesObject to simplify getters and setters.
 * Post is feature for clubs and batches.
 * It has its owner(who posted), content(text, image, video only) and associated comments
 */

@ParseClassName("Post")
public class Post extends ParseObject {

    // These constants denotes field/column name on our backend
    public static final String KEY_OWNER = "owner"; // the one who posted
    public static final String KEY_CONTENT = "content"; // the content of the post
    public static final String COMMENTS_RELATION = "comments"; // comments associated with post
    public static final String KEY_COMMENTS_COUNT = "numberOfComments"; // total number of comments on post

    public Post() {
    }

    public User getOwner() {
        return (User) get(KEY_OWNER);
    }

    public void setOwner(User user) {
        put(KEY_OWNER, user);
    }

    public ContentWithMedia getContent() {
        return (ContentWithMedia) get(KEY_CONTENT);
    }

    public void setContent(ContentWithMedia content) {
        put(KEY_CONTENT, content);
    }

    public ParseRelation<Comment> getCommentsRelation() {
        return getRelation(COMMENTS_RELATION);
    }

    public int getCommentsCount() {
        return getInt(KEY_COMMENTS_COUNT);
    }

    public void incrementCommentsCount() {
        increment(KEY_COMMENTS_COUNT);
    }
}
