package com.example.praty.notestaker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{
    private List<Notes> mNotes;
    private ItemClickListener mItemClickListener;
    private static final String TAG = "NotesAdapter";

    public NotesAdapter(List<Notes> mNotes, ItemClickListener mItemClickListener) {
        this.mNotes = mNotes;
        this.mItemClickListener=mItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row,parent,false);
        final ViewHolder holder=new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v,holder.getAdapterPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            String month=mNotes.get(position).getTimestamp().substring(0,2);
            month=Utility.getMonthFromNumber(month);
            String year=mNotes.get(position).getTimestamp().substring(3);
            String timestamp=month+" "+year;
            holder.timestamp.setText(timestamp);
            holder.notetitle.setText(mNotes.get(position).getTitle());
        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolder: NullPointerException " + e.getMessage());
        }



    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView notetitle;
        TextView timestamp;
        public ViewHolder(View itemView) {
            super(itemView);

            notetitle=itemView.findViewById(R.id.my_title);
            timestamp=itemView.findViewById(R.id.my_timestamp);
        }
    }


}
