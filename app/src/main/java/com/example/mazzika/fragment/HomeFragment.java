package com.example.mazzika.fragment;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mazzika.NotificationReceiver;
import com.example.mazzika.R;
import com.example.mazzika.activity.MainActivity;
import com.example.mazzika.activity.PlayerActivity;
import com.example.mazzika.adapter.Item_Main_RecycleView_Adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {

    public static final String CHANNEL_ID_1 = "channel1";


    //
    Item_Main_RecycleView_Adapter adapter;
    private RecyclerView Main_recyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        Main_recyclerView = view.findViewById(R.id.main_recyclerview);
        adapter = new Item_Main_RecycleView_Adapter(getContext(), MainActivity.Song);
        Main_recyclerView.setAdapter(adapter);
        Main_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

}