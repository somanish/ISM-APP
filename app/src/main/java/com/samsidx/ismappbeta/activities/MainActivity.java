package com.samsidx.ismappbeta.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.samsidx.ismappbeta.R;
import com.samsidx.ismappbeta.adapters.NavListAdapter;
import com.samsidx.ismappbeta.data.NavListItem;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.nav_list) ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ArrayList<NavListItem> mNavListItems;

    private NavListAdapter mNavListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setUpActionBar();

        mTitle = mDrawerTitle = getTitle();
        mNavListItems = new ArrayList<>();

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // TODO: populate navigation drawer
        mNavListItems.add(new NavListItem(null, "Majeed Siddiqui", "3rd - BTech - CSE"));
        for (int i = 0; i < 20; ++i) {
            mNavListItems.add(new NavListItem("Home", 0));
        }

        // create and set navigation adapter
        mNavListAdapter = new NavListAdapter(this, 0, mNavListItems);
        mDrawerList.setAdapter(mNavListAdapter);

        // set OnListItemClickListener on list view
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                displayFragment(position);
            }
        });

        // set up drawer toggle
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.access_drawer_open,
                R.string.access_drawer_close
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // set drawer toggle
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // initialize first fragment to show when activity started
        if (savedInstanceState == null) {
            displayFragment(1);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT)
                    .show();
        }
        else if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setUpActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void displayFragment(int position) {
        Fragment fragment = getDisplayFragment(position);
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        if (position != 0) {
            setTitle(mNavListItems.get(position).getItemName());
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public Fragment getDisplayFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = PlaceHolderFragment.newInstance("Hello", 0);
                break;
            case 1:
                fragment = PlaceHolderFragment.newInstance("Profile", 1);
                break;
            case 2:
                fragment = PlaceHolderFragment.newInstance("Groups", 2);
                break;
            default:
                fragment = PlaceHolderFragment.newInstance("Groups", 2);
                break;
        }
        return fragment;
    }

    public static class PlaceHolderFragment extends Fragment {

        public static final String ARG_ITEM_NAME = "ARGM_ITEM_NAME";
        public static final String ARG_ITEM_RES_ID = "ARGM_ITEM_RES_ID";

        public static PlaceHolderFragment newInstance(String itemName, int itemResId) {
            Bundle args = new Bundle();
            args.putString(ARG_ITEM_NAME, itemName);
            args.putInt(ARG_ITEM_RES_ID, itemResId);
            PlaceHolderFragment fragment = new PlaceHolderFragment();
            fragment.setArguments(args);
            return fragment;
        }

        private int mItemResId;
        private String mItemName;

        private TextView mItemNameView;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle args = getArguments();
            mItemName = args.getString(ARG_ITEM_NAME);
            mItemResId = args.getInt(ARG_ITEM_RES_ID);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_placeholder, container, false);
            mItemNameView = (TextView) view.findViewById(R.id.textView);
            mItemNameView.setText(mItemName);
            return view;
        }
    }
}
