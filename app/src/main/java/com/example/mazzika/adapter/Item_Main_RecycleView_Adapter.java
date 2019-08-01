package com.example.mazzika.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mazzika.R;
import com.example.mazzika.song.T_Song;

import java.util.ArrayList;

public class Item_Main_RecycleView_Adapter extends RecyclerView.Adapter<Item_Main_RecycleView_Adapter.ViewHolder> {

    private static final String TAG = "RecycleView_Adapter";
    private Context context;

    private ArrayList<ArrayList<T_Song>> Song = new ArrayList<>();

    public Item_Main_RecycleView_Adapter(Context context, ArrayList<ArrayList<T_Song>> song) {
        this.context = context;
        Song = song;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_main, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder : called.");

        if (!Song.get(position).isEmpty()) {
            holder.Titel_Song.setText(Song.get(position).get(0).getCategory());
            Item_RecycleView_Adapter adapter = new Item_RecycleView_Adapter(context, (Song.get(position)));
            holder.recyclerView.setAdapter(adapter);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    @Override
    public int getItemCount() {

        /*int Num=0;
        for (int I=0;I<Song.size();I++)
        {
            if(!(Song.get(I).isEmpty()))
                Num++;
        }*/

        return Song.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Titel_Song;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Titel_Song = itemView.findViewById(R.id.titel_song_itemMain);
            recyclerView = itemView.findViewById(R.id.main_recyclerview_item);

        }
    }
}
