package com.example.mazzika.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mazzika.R;
import com.example.mazzika.activity.HomeActivity;
import com.example.mazzika.activity.MainActivity;
import com.example.mazzika.fragment.HomeFragment;
import com.example.mazzika.song.T_Song;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Item_RecycleView_Adapter extends RecyclerView.Adapter<Item_RecycleView_Adapter.ViewHolder> {

    private static final String TAG = "RecycleView_Adapter";

    private Context context;

    private ArrayList<T_Song> Song = new ArrayList<>();

    public Item_RecycleView_Adapter(Context context, ArrayList<T_Song> song) {
        this.context = context;
        Song = song;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder : called.");
        Glide.with(context)
                .asBitmap()
                .load(Song.get(position).getSongImage())
                .into(holder.songImage);

        holder.songName.setText(Song.get(position).getSongName());

        holder.Parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick : Clicked On" + Song.get(position).getSongName());

                if (MainActivity.soundPlay.isPlaying()) {
                    MainActivity.soundPlay.stop();
                    MainActivity.soundPlay.reset();
                }
                MainActivity.soundPlay = MediaPlayer.create(context, Song.get(position).getSongSound());
                MainActivity.soundPlay.start();

                MainActivity.songObject = Song.get(position);
                HomeActivity.CheckButton();

                HomeActivity.imageSong_Home.setImageResource(Song.get(position).getSongImage());
                HomeActivity.nameSongHome.setText("" + Song.get(position).getSongName());
                HomeActivity.nameSingerHome.setText("" + Song.get(position).getSingerName());

                /*Play_Sound_Activity.Category = position;
                Play_Sound_Activity.Sound = position;*/

                // Toast.makeText(context, "" + Song.get(position).getSongName(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return Song.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView songImage;
        TextView songName;
        RelativeLayout Parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            songImage = itemView.findViewById(R.id.song_image_item);
            songName = itemView.findViewById(R.id.song_name_item);
            Parent_layout = itemView.findViewById(R.id.parent_layout_item);
        }
    }
}
