package com.samsidx.ismappbeta.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samsidx.ismappbeta.R;
import com.samsidx.ismappbeta.data.NavListItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by samsidx(Majeed Siddiqui) on 19/1/15.
 */
public class NavListAdapter extends ArrayAdapter<NavListItem> {

    private Context mContext;
    private ArrayList<NavListItem> mData;

    public NavListAdapter(Context context, int resource, ArrayList<NavListItem> data) {
        super(context, resource, data);
        mContext = context;
        mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.nav_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        NavListItem item = getItem(position);

        if (item.isProfileView()) {
            holder.mProfileInfoContainer.setVisibility(View.VISIBLE);
            holder.mNavItem.setVisibility(View.GONE);

            Uri profilePicUri = item.getProfilePicUri();
            if (profilePicUri != null) {
                Picasso.with(mContext).load(item.getProfilePicUri()).into(holder.mProfilePic);
            }
            holder.mFullName.setText(item.getFullName());
            holder.mAcademicInfo.setText(item.getAcademicInfo());
        }
        else {
            holder.mProfileInfoContainer.setVisibility(View.GONE);
            holder.mNavItem.setVisibility(View.VISIBLE);

            holder.mNavItem.setText(item.getItemName());
//            holder.mNavItem.setCompoundDrawablesWithIntrinsicBounds(item.getItemIconResId(), 0, 0, 0);
        }

        return convertView;
    }

    protected class ViewHolder {

        @InjectView(R.id.profile_info_container) public LinearLayout mProfileInfoContainer;
        @InjectView(R.id.post_pro_pic) public ImageView mProfilePic;
        @InjectView(R.id.post_full_name) public TextView mFullName;
        @InjectView(R.id.post_academic_info) public TextView mAcademicInfo;

        @InjectView(R.id.nav_item) public TextView mNavItem;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
