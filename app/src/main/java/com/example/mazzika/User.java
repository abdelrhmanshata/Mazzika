package com.example.mazzika;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User extends Person {

    private List<String> recently;
    private List<String> favorite;
    private Map<String, List<String>> playLists;

    public User(String phone) {
        super(phone);
        recently = new ArrayList<>();
        favorite = new ArrayList<>();
        playLists = new HashMap<>();
    }

    public User(Name name, String country, String phone) {
        super(name, country, phone, "");
        this.recently = new ArrayList<>();
        this.favorite = new ArrayList<>();
        this.playLists = new HashMap<>();
    }

    public User(Name name, String country, String phone, List<String> recently, List<String> favorite, Map<String, List<String>> playLists) {
        super(name, country, phone, "");
        this.recently = recently;
        this.favorite = favorite;
        this.playLists = playLists;
    }

    public List<String> getRecently() {
        return recently;
    }

    public List<String> getFavorite() {
        return favorite;
    }

    public Map<String, List<String>> getPlayLists() {
        return playLists;
    }
}
