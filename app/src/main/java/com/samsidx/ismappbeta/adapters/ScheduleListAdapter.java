package com.samsidx.ismappbeta.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.samsidx.ismappbeta.R;
import com.samsidx.ismappbeta.data.ScheduleData;
import com.samsidx.ismappbeta.helper.DBHelper;

import java.util.List;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {
    private List<ScheduleData> itemList;
    String[] time;

    public ScheduleListAdapter(List<ScheduleData> itemList, String[] time) {
        this.itemList=itemList;
        this.time=time;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ScheduleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_layout, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }
 
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
         
        // - get data from your itemsData at this position
        final int slot=itemList.get(position).getSlot();
        final String sub=itemList.get(position).getSub();
        viewHolder.txtViewSub.setText(sub);
        viewHolder.txtViewSlot.setText(time[slot]);
        viewHolder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                final EditText input = new EditText(v.getContext());
                input.setHeight(150);
                input.setWidth(400);
                input.setGravity(Gravity.LEFT);
                input.setImeOptions(EditorInfo.IME_ACTION_DONE);

                new AlertDialog.Builder(v.getContext())
                        .setTitle("Enter Subject")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelper db=new DBHelper(v.getContext());
                                String updatedSub =input.getText().toString();
                                itemList.get(position).setSub(updatedSub);
                                notifyItemChanged(position);
                                if(sub.equals(" "))
                                    db.createSch(itemList.get(position), updatedSub);
                                else
                                    db.updateSch(itemList.get(position), updatedSub);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing
                            }
                        })
                        .setView(input).show();
            }
        });
    }
     
    // inner class to hold a reference to each item of RecyclerView 
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtViewSub;
        public TextView txtViewSlot;
         
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewSub = (TextView) itemLayoutView.findViewById(R.id.textScheduleSub);
            txtViewSlot = (TextView) itemLayoutView.findViewById(R.id.textScheduleSlot);
        }
    }
 
 
    public interface MyOnClickListener {
        public void onItemClick(View view, int position);
    }
    
    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}