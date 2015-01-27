package com.samsidx.ismappbeta.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.parse.ParseFile;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileHelper {

    public static final String TAG = FileHelper.class.getSimpleName();
    public static final int MEDIA_TYPE_IMAGE = 0;
    public static final int MEDIA_TYPE_VIDEO = 1;

    public static final int SHORT_SIDE_TARGET = 800;

    public static byte[] getByteArrayFromFile(Context context, Uri uri) {

        if (uri == null) {
            return null;
        }

        byte[] fileBytes = null;
        InputStream inStream = null;
        ByteArrayOutputStream outStream = null;

        if (uri.getScheme().equals("content")) {
            try {
                inStream = context.getContentResolver().openInputStream(uri);
                outStream = new ByteArrayOutputStream();

                byte[] bytesFromFile = new byte[1024 * 1024]; // buffer size (1 MB)
                int bytesRead = inStream.read(bytesFromFile);
                while (bytesRead != -1) {
                    outStream.write(bytesFromFile, 0, bytesRead);
                    bytesRead = inStream.read(bytesFromFile);
                }

                fileBytes = outStream.toByteArray();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } finally {
                try {
                    inStream.close();
                    outStream.close();
                } catch (IOException e) { /*( Intentionally blank */ }
            }
        } else {
            try {
                File file = new File(uri.getPath());
                FileInputStream fileInput = new FileInputStream(file);
                fileBytes = IOUtils.toByteArray(fileInput);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return fileBytes;
    }

    public static byte[] reduceImageForUpload(byte[] imageData) {
        Bitmap bitmap = ImageResizer.resizeImageMaintainAspectRatio(imageData, SHORT_SIDE_TARGET);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        byte[] reducedData = outputStream.toByteArray();
        try {
            outputStream.close();
        } catch (IOException e) {
            // Intentionally blank
        }

        return reducedData;
    }

    public static String getFileName(Context context, Uri uri, int mediaType) {
        String fileName = "uploaded_file.";

        if (mediaType == MEDIA_TYPE_IMAGE) {
            fileName += "jpg";
        } else {
            // For video, we want to get the actual file extension
            if (uri.getScheme().equals("content")) {
                // do it using the mime type
                String mimeType = context.getContentResolver().getType(uri);
                int slashIndex = mimeType.indexOf("/");
                String fileExtension = mimeType.substring(slashIndex + 1);
                fileName += fileExtension;
            } else {
                fileName = uri.getLastPathSegment();
            }
        }

        return fileName;
    }

    public static Uri getLocalDeviceOutputUri(String appName, int mediaType) {

        // check external storage is mounted or not
        // if not return null
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        // file name prefix to save on local device
        final String IMAGE_PREFIX = "IMG_";
        final String VIDEO_PREFIX = "VID_";

        // format of file to save on local device
        final String IMAGE_FORMAT = ".jpg";
        final String VIDEO_FORMAT = ".mp4";

        // format of time stamp
        final String TIME_STAMP_FORMAT = "yyyyMMdd_HHmmss";

        // get application's media storage directory
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), appName
        );

        // check media storage directory exists
        // if not try to create one
        // if failed to create new return null
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // get time stamp
        String timeStamp = new SimpleDateFormat(TIME_STAMP_FORMAT, Locale.US).format(new Date());

        // get media storage directory's path and append file separator
        String path = mediaStorageDir.getPath() + File.separator;

        // create an empty file
        File mediaFile;

        // check for type of media and assign corresponding name
        if (mediaType == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(path + IMAGE_PREFIX + timeStamp + IMAGE_FORMAT);
        } else if (mediaType == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(path + VIDEO_PREFIX + timeStamp + VIDEO_FORMAT);
        } else {
            // unknown file format return null
            return null;
        }

        // return Uri of created empty file
        return Uri.fromFile(mediaFile);
    }

    public static ParseFile getParseFileFromUri(Context context, Uri mediaUri, int mediaType) {
        byte[] fileBytes = getByteArrayFromFile(context, mediaUri);

        if (fileBytes == null) {
            return null;
        }

        // reduce image size (must be less than 10MB because Parse.com restrictions)
        if (mediaType == MEDIA_TYPE_IMAGE) {
            fileBytes = reduceImageForUpload(fileBytes);
        }

        // get name of file for backend
        String fileName = getFileName(context, mediaUri, mediaType);

        // create parse file and return
        return new ParseFile(fileName, fileBytes);
    }
}