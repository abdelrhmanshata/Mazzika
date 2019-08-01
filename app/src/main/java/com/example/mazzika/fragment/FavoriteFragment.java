package com.example.mazzika.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mazzika.DataBase.DB_Song;
import com.example.mazzika.R;
import com.example.mazzika.adapter.Item_Main_RecycleView_Adapter;
import com.example.mazzika.song.T_Song;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment {

    public static ArrayList<ArrayList<T_Song>> Favorite_Song;
    RecyclerView recyclerView;
    DB_Song dbSong;
    Item_Main_RecycleView_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_favorite);
        dbSong = new DB_Song(getContext());
        Favorite_Song = new ArrayList<>();

        creatMusic();
        /*  Favorite_Song=dbSong.getFavorite();*/

        adapter = new Item_Main_RecycleView_Adapter(getContext(), Favorite_Song);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void creatMusic() {
        Favorite_Song.clear();
        Favorite_Song.add(dbSong.getFavoriteCategory(dbSong.getCategoryBy_Name("JAZZ")));
        Favorite_Song.add(dbSong.getFavoriteCategory(dbSong.getCategoryBy_Name("HIP_HOP")));
        Favorite_Song.add(dbSong.getFavoriteCategory(dbSong.getCategoryBy_Name("ROCK")));
        Favorite_Song.add(dbSong.getFavoriteCategory(dbSong.getCategoryBy_Name("ELECTRONIC")));
        Favorite_Song.add(dbSong.getFavoriteCategory(dbSong.getCategoryBy_Name("DANCE")));
        Favorite_Song.add(dbSong.getFavoriteCategory(dbSong.getCategoryBy_Name("HEAVY_METAL")));
        Favorite_Song.add(dbSong.getFavoriteCategory(dbSong.getCategoryBy_Name("FOLK")));
        Favorite_Song.add(dbSong.getFavoriteCategory(dbSong.getCategoryBy_Name("CLASSICAL")));
        Favorite_Song.add(dbSong.getFavoriteCategory(dbSong.getCategoryBy_Name("MAHRGANNAT")));
    }

    @Override
    public void onResume() {
        super.onResume();
        creatMusic();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
