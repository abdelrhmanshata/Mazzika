package com.example.mazzika.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mazzika.DataBase.DB_Song;
import com.example.mazzika.R;
import com.example.mazzika.adapter.Item_RecycleView_Adapter;
import com.example.mazzika.song.T_Song;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerViewSearch;
    private EditText textSearch;
    private Item_RecycleView_Adapter adapter;
    private DB_Song dbSong;
    private ArrayList<T_Song> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerViewSearch = view.findViewById(R.id.recyclerView_search);
        textSearch = view.findViewById(R.id.editText_search);
        dbSong = new DB_Song(getContext());
        arrayList = dbSong.getAllDate();

        textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayList = dbSong.getSongBy_Name(s + "");
                if (!arrayList.isEmpty()) {
                    adapter = new Item_RecycleView_Adapter(getContext(), arrayList);
                    recyclerViewSearch.setAdapter(adapter);
                    recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    Toasty.warning(getContext(), "Song Not Found !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        return view;
    }
}
