package com.example.mazzika;

import com.example.mazzika.song.Song;

import java.util.ArrayList;
import java.util.List;

public class Singer extends Person {

    private List<Song> uploaded;

    public Singer(String phone) {
        super(phone);
        uploaded = new ArrayList<>();
    }

    public Singer() {
        uploaded = new ArrayList<>();
    }

    public Singer(Name name, String country, String phone, List<Song> uploaded, String profileImageUrl) {
        super(name, country, phone, profileImageUrl);
        this.uploaded = uploaded;
    }

    public List<Song> getUploaded() {
        return uploaded;
    }

}

