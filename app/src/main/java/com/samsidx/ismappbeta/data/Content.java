package com.samsidx.ismappbeta.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by samsidx(Majeed Siddiqui) on 14/1/15.
 *
 */

@ParseClassName("CommentContent")
public class Content extends ParseObject {

    public static final String KEY_TEXT = "text"; // text content of message

    public String getText() {
        return getString(KEY_TEXT);
    }

    public void setText(String text) {
        put(KEY_TEXT, text);
    }
}
