package com.samsidx.ismappbeta.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseQueryAdapter;
import com.samsidx.ismappbeta.R;
import com.samsidx.ismappbeta.data.ContentWithMedia;
import com.samsidx.ismappbeta.data.Post;
import com.samsidx.ismappbeta.data.User;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by samsidx(Majeed Siddiqui) on 20/1/15.
 */
public class PostAdapter extends ParseQueryAdapter<Post> {

    private Context mContext;

    public PostAdapter(Context context, QueryFactory<Post> queryFactory) {
        super(context, queryFactory);
        mContext = context;
    }

    @Override
    public View getItemView(Post post, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.post_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // set pro pic if available or set default based on gender
        User owner = post.getOwner();
        Uri profilePicUri = owner.getProfilePicUri();

        if (profilePicUri == null) {
            if (owner.getGender().equals(User.GENDER_MALE)) {
                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.raw.male_profile);
                holder.mPostProPic.setImageBitmap(bitmap);
            }
            else {
                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.raw.female_profile);
                holder.mPostProPic.setImageBitmap(bitmap);
            }
        }
        else {
            Picasso.with(mContext).load(profilePicUri).into(holder.mPostProPic);
        }

        // set name
        holder.mFullName.setText(owner.getFullName());

        // set academic info
        // may be we add profs, so they won't be having academic info test for that
        if (owner.getRole() != null && owner.getRole().equals(User.ROLE_STUDENT)) {

            String batchOf = owner.getBatchOf();
            String degreeName = owner.getDegreeName();
            String branchName = owner.getBranchName();

            String academicInfo = batchOf;

            if (academicInfo == null) {
                academicInfo = "";
            }

            if (degreeName != null && !degreeName.isEmpty()) {
                academicInfo += "-" + owner.getDegreeName();
            }

            if (branchName != null && !degreeName.isEmpty()) {
                academicInfo += "-" + owner.getBranchName();
            }

            holder.mAcademicInfo.setText(academicInfo);
        }

        // set post content
        ContentWithMedia content = post.getContent();

        // set post text if available
        String textContent = content.getText();
        if (textContent == null || textContent.isEmpty()) {
            holder.mPostText.setVisibility(View.GONE);
        }
        else {
            holder.mPostText.setText(textContent);
        }

        // set post pic if available
        Uri postPicUri = content.getMediaUri();
        if (postPicUri == null) {
            holder.mPostContentPic.setVisibility(View.GONE);
        }
        else {
            holder.mPostContentPic.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(postPicUri).into(holder.mPostContentPic);
        }

        // set number of comments if greater than 0
        int commentsCnt = post.getCommentsCount();
        if (commentsCnt <= 0) {
            holder.mCommentCounter.setVisibility(View.GONE);
        }
        else {
            String commentsCntLabel = commentsCnt + " comment";
            if (commentsCnt > 1) {
                commentsCntLabel += "s";
            }
            holder.mCommentCounter.setText(commentsCntLabel);
        }

        // TODO: set comment button text on click listener don't know where :(
        // TODO: set elapsed time text don't know how :(

        return convertView;
    }

    protected class ViewHolder {

        @InjectView(R.id.post_pro_pic) public ImageView mPostProPic;
        @InjectView(R.id.post_full_name) public TextView mFullName;
        @InjectView(R.id.post_academic_info) public TextView mAcademicInfo;
        @InjectView(R.id.post_text_content) public TextView mPostText;
        @InjectView(R.id.post_content_pic) public ImageView mPostContentPic;
        @InjectView(R.id.post_counter_text) public TextView mCommentCounter;
        @InjectView(R.id.post_comment_text) public TextView mCommentButton;
        @InjectView(R.id.post_time_elapsed_text) public TextView mElapsedTime;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
