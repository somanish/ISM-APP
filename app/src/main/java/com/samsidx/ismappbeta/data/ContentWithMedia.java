package com.samsidx.ismappbeta.data;

import android.net.Uri;

import com.parse.ParseClassName;
import com.parse.ParseFile;

/**
 * Created by samsidx(Majeed Siddiqui) on 14/1/15.
 */

@ParseClassName("PostContent")
public class ContentWithMedia extends Content {

    // supported media types
    public static final String MEDIA_TYPE_IMAGE = "image";
    public static final String MEDIA_TYPE_VIDEO = "video";
    public static final String MEDIA_TYPE_NONE = "none";

    // These constants denotes field/column name on our backend
    public static String KEY_MEDIA = "media"; // image or video
    public static String KEY_MEDIA_TYPE = "mediaType"; // type of media

    public Uri getMediaUri() {
        ParseFile mediaFile = getParseFile(KEY_MEDIA);

        // if content do not have any media return null
        if (mediaFile == null) {
            return null;
        }

        // return uri of file
        return Uri.parse(mediaFile.getUrl());
    }

    public void setMedia(ParseFile file) {
        if (file != null) {
            put(KEY_MEDIA, file);
        }
    }

    public String getMediaType() {
        return getString(KEY_MEDIA_TYPE);
    }

    public void setMediaType(String mediaType) {
        put(KEY_MEDIA_TYPE, mediaType);
    }
}
