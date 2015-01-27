package com.samsidx.ismappbeta.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.samsidx.ismappbeta.R;
import com.samsidx.ismappbeta.data.ContentWithMedia;
import com.samsidx.ismappbeta.data.Post;
import com.samsidx.ismappbeta.helper.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class PostActivity extends ActionBarActivity {

    private static final String TAG = PostActivity.class.getSimpleName();

    public static final int TAKE_PHOTO_REQUEST = 0;
    public static final int TAKE_VIDEO_REQUEST = 1;
    public static final int CHOOSE_PHOTO_REQUEST = 2;
    public static final int CHOOSE_VIDEO_REQUEST = 3;

    public static final int MEDIA_TYPE_IMAGE = 4;
    public static final int MEDIA_TYPE_VIDEO = 5;

    public static final int FILE_SIZE_LIMIT = 1024*1024*10; // 10 MB

    private Uri mMediaUri;

    protected DialogInterface.OnClickListener mDialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                case 0: // take picture
                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mMediaUri = getOutputMediaUri(MEDIA_TYPE_IMAGE);
                    if (mMediaUri == null) {
                        Toast.makeText(PostActivity.this,
                                "External storage error!", Toast.LENGTH_SHORT)
                                .show();
                    }
                    else {
                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                        startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
                    }
                    break;
                case 1: // take video
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    mMediaUri = getOutputMediaUri(MEDIA_TYPE_VIDEO);
                    if (mMediaUri == null) {
                        Toast.makeText(PostActivity.this,
                                "External storage error!", Toast.LENGTH_SHORT)
                                .show();
                    }
                    else {
                        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                        takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);

                        startActivityForResult(takeVideoIntent, TAKE_VIDEO_REQUEST);
                    }
                    break;
                case 2: // choose picture
                    Intent choosePicture = new Intent(Intent.ACTION_GET_CONTENT);
                    choosePicture.setType("image/*");
                    startActivityForResult(choosePicture, CHOOSE_PHOTO_REQUEST);
                    break;
                case 3: // choose video
                    Toast.makeText(PostActivity.this, "File size exceeded!", Toast.LENGTH_LONG).show();
                    Intent chooseVideo = new Intent(Intent.ACTION_GET_CONTENT);
                    chooseVideo.setType("video/*");
                    startActivityForResult(chooseVideo, CHOOSE_VIDEO_REQUEST);
                    break;
            }
        }

        private Uri getOutputMediaUri(int mediaType) {
            if (isExternalStorageAvailable()) {

                // 1. Get the external storage directory
                String appName = PostActivity.this.getString(R.string.app_name);
                File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        appName);

                // 2. Create our own subdirectory
                if (!mediaStorageDir.exists()) {
                    if(!mediaStorageDir.mkdirs()) {
                        Log.e(TAG, "Failed to create directory");
                        return null;
                    }
                }

                // 3. Create file name
                // 4. Create the file
                File  mediaFile;
                Date now = new Date();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

                String path = mediaStorageDir.getPath() + File.separator;
                if (mediaType == MEDIA_TYPE_IMAGE) {
                    mediaFile = new File(path + "IMG_" + timeStamp + ".jpg");
                }
                else if (mediaType == MEDIA_TYPE_VIDEO) {
                    mediaFile = new File(path + "VID_" + timeStamp + ".mp4");
                }
                else {
                    return null;
                }

                Log.d(TAG, "File: " + Uri.fromFile(mediaFile));

                // 5. Return the file's Uri
                return Uri.fromFile(mediaFile);
            }
            else {
                return null;
            }
        }

        private boolean isExternalStorageAvailable() {
            String state = Environment.getExternalStorageState();
            return state.equals(Environment.MEDIA_MOUNTED);
        }
    };

    @InjectView(R.id.editText) EditText mText;
    @InjectView(R.id.button) Button mImage;
    @InjectView(R.id.button2) Button mPost;
    @InjectView(R.id.button3) Button mViewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.inject(this);

        if (ParseUser.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(PostActivity.this)
                        .setItems(R.array.media_chooser_array, mDialogListener)
                        .create()
                        .show();
            }
        });

        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentWithMedia content = new ContentWithMedia();
                content.setText(mText.getText().toString());
                content.setMedia(FileHelper.getParseFileFromUri(PostActivity.this, mMediaUri, FileHelper.MEDIA_TYPE_IMAGE));

                Post newPost = new Post();
                newPost.setOwner((com.samsidx.ismappbeta.data.User) ParseUser.getCurrentUser());
                newPost.setContent(content);
                newPost.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Crouton.makeText(PostActivity.this, "Success!", Style.CONFIRM)
                                .show();
                    }
                });
            }
        });

        mViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, PostDisplayActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CHOOSE_PHOTO_REQUEST || requestCode == CHOOSE_VIDEO_REQUEST) {
                if (data == null) {
                    Toast.makeText(this, "Picked content error!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                else {
                    mMediaUri = data.getData();
                }

                Log.i(TAG, "Media Uri: " + mMediaUri);
                if (requestCode == CHOOSE_VIDEO_REQUEST) {
                    int fileSize = 0;

                    InputStream inputStream = null;
                    try {
                        inputStream = getContentResolver().openInputStream(mMediaUri);
                        fileSize = inputStream.available();
                    }
                    catch (FileNotFoundException ex) {
                        Toast.makeText(this, "File not found!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    catch (IOException ex) {
                        Toast.makeText(this, "Picked content error!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                /* Intentionally left blank */
                            }
                        }
                    }

                    if (fileSize >= FILE_SIZE_LIMIT) {
                        Toast.makeText(this, "File too large!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
            else {
                Intent mediaScanner = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanner.setData(mMediaUri);
                sendBroadcast(mediaScanner);
            }
        }
        else if (resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Failed to save picture!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
