package com.samsidx.ismappbeta.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;


/**
 * Created by samsidx(Majeed Siddiqui) on 14/1/15.
 * <p/>
 * Comment class is extended from ParesObject to simplify getters and setters.
 * Comment is feature of post in club, batch.
 * It has its owner(the one commented) and content(text only).
 * <p/>
 * DO NOT MODIFY ANYTHING
 */

@ParseClassName("Comment")
public class Comment extends ParseObject {

    // These constants denotes field/column name on our backend
    public static final String KEY_OWNER = "owner"; // the one who commented
    public static final String KEY_CONTENT = "content"; // content of the comment

    public Comment() {
    }

    public User getOwner() {
        return (User) get(KEY_OWNER);
    }

    public void setOwner(User user) {
        put(KEY_OWNER, user);
    }

    public String getContent() {
        return getString(KEY_CONTENT);
    }

    public void setContent(Content content) {
        put(KEY_CONTENT, content);
    }
}
