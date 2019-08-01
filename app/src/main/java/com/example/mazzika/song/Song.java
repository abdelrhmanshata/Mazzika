package com.example.mazzika.song;

import com.example.mazzika.Singer;

public class Song {

    private String imageUrl, songUrl, title;
    public Singer singer;
    private Category category;

    public Song() {
    }

    public Song(String imageUrl, String songUrl, String title, Singer singer, Category category) {
        this.imageUrl = imageUrl;
        this.songUrl = songUrl;
        this.title = title;
        this.singer = singer;
        this.category = category;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public String getTitle() {
        return title;
    }

    public Singer getSinger() {
        return singer;
    }

    public Category getCategory() {
        return category;
    }
}
