package com.samsidx.ismappbeta.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.samsidx.ismappbeta.R;
import com.samsidx.ismappbeta.adapters.ScheduleListAdapter;
import com.samsidx.ismappbeta.data.ScheduleData;
import com.samsidx.ismappbeta.helper.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleFragment extends Fragment {
    TextView yesterday,today,tomorrow;
    String[] days,time;
    int lenDay=5,lenTime=11,i,position=0;
    float initialX;
    DBHelper db;
    RecyclerView recyclerView;
    ScheduleListAdapter listAdapterSchedule;
    List<ScheduleData> itemList;

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        //Initialize
        db= new DBHelper(rootView.getContext());
        yesterday=(TextView)rootView.findViewById(R.id.textViewYesterday);
        today=(TextView)rootView.findViewById(R.id.textViewToday);
        tomorrow=(TextView)rootView.findViewById(R.id.textViewTomorrow);
        days=getResources().getStringArray(R.array.Day);
        time= getResources().getStringArray(R.array.Time);
        lenDay=days.length;
        lenTime=time.length;

        //Select the day to be as Today
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.US);
        String weekDay = dayFormat.format(Calendar.getInstance().getTime());
        dayFormat = new SimpleDateFormat("hh:mm aa");
        String currTime = dayFormat.format(Calendar.getInstance().getTime());
        for(i=0;i<lenDay;i++)
            if(weekDay.equals(days[i]))
                position=i;
        Cursor c=db.getMaxSlotOfDay(position);
        if(c.moveToFirst())
            i= c.getInt(c.getColumnIndex(DBHelper.KEY_SLOT));
        dayFormat = new SimpleDateFormat("hh:mm aa");

        try {
            Date one = dayFormat.parse(time[i]);
            Date two = dayFormat.parse(currTime);
            if(weekDay.equals(days[position])&& one.compareTo(two)>=0)
                position=(position+1)%lenDay;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 1. get a reference to RecyclerView
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerViewAdd);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        // Create & Set Adapter and Set data
        updateData(position);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Touch Events
        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v,MotionEvent event) {
                today=(TextView)getActivity().findViewById(R.id.textViewToday);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float finalX = event.getX();
                        if (initialX > finalX)
                            position = (position + 1)%lenDay;
                        else
                            position = (position + 4)%lenDay;
                        updateData(position);
                        break;
                }
                return true;
            }
        });

        //Return the fragment view
        return rootView;
    }

    public void updateData(int pos) {
        itemList=new ArrayList<ScheduleData>();
        Cursor cursor= db.getSchByDay(position);
        for(i=0;i<time.length;i++){
            ScheduleData item=new ScheduleData(-1," ",i,position);
            itemList.add(item);
        }
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                int slot = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SLOT));
                long id = cursor.getLong(cursor.getColumnIndex(DBHelper.KEY_ID));
                String subject = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SUB));
                itemList.get(slot).setSub(subject);
                itemList.get(slot).setId(id);
                cursor.moveToNext();
            }
        }
        cursor.close();
        listAdapterSchedule = new ScheduleListAdapter(itemList,time);
        recyclerView.setAdapter(listAdapterSchedule);

        yesterday.setText(days[(pos+4)%lenDay].toString());
        today.setText(days[pos]);
        tomorrow.setText(days[(pos+1)%lenDay].toString());
    }
}
