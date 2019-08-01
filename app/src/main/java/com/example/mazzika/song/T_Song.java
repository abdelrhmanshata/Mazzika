package com.example.mazzika.song;

public class T_Song {

    private int ID, songSound, songImage, favorite;
    private String category, songName, singerName;

    public T_Song(int ID, String category, String songName, String singerName, int songSound, int songImage, int favorite) {
        this.ID = ID;
        this.category = category;
        this.songName = songName;
        this.singerName = singerName;
        this.songSound = songSound;
        this.songImage = songImage;
        this.favorite = favorite;
    }

    public T_Song(String category, String songName, String singerName, int songSound, int songImage, int favorite) {
        this.category = category;
        this.songName = songName;
        this.singerName = singerName;
        this.songSound = songSound;
        this.songImage = songImage;
        this.favorite = favorite;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getSongSound() {
        return songSound;
    }

    public void setSongSound(int songSound) {
        this.songSound = songSound;
    }

    public int getSongImage() {
        return songImage;
    }

    public void setSongImage(int songImage) {
        this.songImage = songImage;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}


