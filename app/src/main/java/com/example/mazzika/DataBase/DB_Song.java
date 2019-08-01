package com.example.mazzika.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mazzika.R;
import com.example.mazzika.activity.MainActivity;
import com.example.mazzika.song.Category;
import com.example.mazzika.song.T_Song;

import java.util.ArrayList;


public class DB_Song extends SQLiteOpenHelper {

    private static final String DB_Name = "Table.db";

    public DB_Song(Context context) {
        super(context, DB_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  my_song ( id INTEGER PRIMARY KEY AUTOINCREMENT , category TEXT , songName TEXT ,singerName TEXT,song INTEGER,songImage INTEGER,favorite INTEGER DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS my_song");
        onCreate(db);
    }



    public boolean insert(T_Song tSong) {

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // add values in the row in table you shoued the key equal the row name
        values.put("category", tSong.getCategory());
        values.put("songName", tSong.getSongName());
        values.put("singerName", tSong.getSingerName());
        values.put("song", tSong.getSongSound());
        values.put("songImage", tSong.getSongImage());
        values.put("favorite", tSong.getFavorite());
        //                           table name                     values
        long result = DB.insert("my_song", null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public void insertSong(){

            insert(new T_Song("JAZZ", "My Favorite Things jazz ver", "Singer JAZZ", R.raw.jazz1, R.drawable.jazz1, 0));
            insert(new T_Song("JAZZ", "Jazz Club", "Singer JAZZ", R.raw.jazz2, R.drawable.jazz2, 0));


            insert(new T_Song("HIP_HOP", "The Top_ HipHop Instrumental", "Singer HIP_HOP", R.raw.hiphop1, R.drawable.hiphop1, 0));
            insert(new T_Song("HIP_HOP", "Hard Hip-Hop Rap Instrumental Beat The Secret", "Singer HIP_HOP", R.raw.hiphop2, R.drawable.hiphop2, 0));


            insert(new T_Song("ROCK", "Position Music The Sound Of War", "Singer ROCK", R.raw.rook1, R.drawable.rook1, 0));
            insert(new T_Song("ROCK", "Greta Van Fleet Highway Tune", "Singer ROCK", R.raw.rook2, R.drawable.rook2, 0));


            insert(new T_Song("ELECTRONIC", "Electro Light Symbolism", "Singer ELECTRONIC", R.raw.electro1, R.drawable.electro1, 0));
            insert(new T_Song("ELECTRONIC", "Different Heaven Safe And Sound", "Singer ELECTRONIC", R.raw.electro2, R.drawable.electro2, 0));


            insert(new T_Song("DANCE", "J.Geco Chicken Song", "Singer DANCE", R.raw.dance1, R.drawable.dance1, 0));
            insert(new T_Song("DANCE", "Capital Cities Safe And Sound", "Singer DANCE", R.raw.dance2, R.drawable.dance2, 0));


            insert(new T_Song("HEAVY_METAL", "Heavy Metal by Scott Watson Score Sound", "Singer HEAVY_METAL", R.raw.heavy_metal1, R.drawable.heavy_metal1, 0));
            insert(new T_Song("HEAVY_METAL", "Best Metal Scales How To Sound Evil", "Singer HEAVY_METAL", R.raw.heavy_metal2, R.drawable.heavy_metal2, 0));


            insert(new T_Song("FOLK", "Acoustic Folk Instrumental", "Singer FOLK", R.raw.folk1, R.drawable.folk1, 0));
            insert(new T_Song("FOLK", "Canon in D piano", "Singer FOLK", R.raw.folk2, R.drawable.folk2, 0));


            insert(new T_Song("CLASSICAL", "Funny Orchestra plays Microsoft Windows", "Singer CLASSICAL", R.raw.classical1, R.drawable.classical1, 0));

            insert(new T_Song("MAHRGANNAT", "GOD OF WAR SONG Ode To Fury by Miracle Of Sound", "Singer MAHRGANNAT", R.raw.mhraganat1, R.drawable.mhraganat1, 0));

            getAllDate();
        }

    public ArrayList<T_Song> getAllDate() {
        ArrayList<T_Song> list = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from my_song ", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int Id = cursor.getInt(cursor.getColumnIndex("id"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            String songName = cursor.getString(cursor.getColumnIndex("songName"));
            String singerName = cursor.getString(cursor.getColumnIndex("singerName"));
            int song = cursor.getInt(cursor.getColumnIndex("song"));
            int songImage = cursor.getInt(cursor.getColumnIndex("songImage"));
            int favorite = cursor.getInt(cursor.getColumnIndex("favorite"));

            T_Song Song = new T_Song(Id, category, songName, singerName, song, songImage, favorite);
            list.add(Song);
            cursor.moveToNext();
        }
        return list;
    }

    //  Method Get Song By ID
    public T_Song getSong_By_Id(int id) {

        SQLiteDatabase DB = this.getReadableDatabase();
        String select_query = "select * from my_song where id=" + id;

        Cursor cursor = DB.rawQuery(select_query, null);
        T_Song Song = null;

        if (cursor.moveToFirst()) {
            //   int Id = cursor.getInt(cursor.getColumnIndex("id"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            String songName = cursor.getString(cursor.getColumnIndex("songName"));
            String singerName = cursor.getString(cursor.getColumnIndex("singerName"));
            int song = cursor.getInt(cursor.getColumnIndex("song"));
            int songImage = cursor.getInt(cursor.getColumnIndex("songImage"));
            int favorite = cursor.getInt(cursor.getColumnIndex("favorite"));
            Song = new T_Song(id, category, songName, singerName, song, songImage, favorite);
        }
        return Song;
    }

    // Method Search Song By Name
    public ArrayList<T_Song> getSongBy_Name(String S_Name) {

        ArrayList<T_Song> list = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from my_song where songName like '%" + S_Name + "%'", null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {

            int Id = cursor.getInt(cursor.getColumnIndex("id"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            String songName = cursor.getString(cursor.getColumnIndex("songName"));
            String singerName = cursor.getString(cursor.getColumnIndex("singerName"));
            int song = cursor.getInt(cursor.getColumnIndex("song"));
            int songImage = cursor.getInt(cursor.getColumnIndex("songImage"));
            int favorite = cursor.getInt(cursor.getColumnIndex("favorite"));

            T_Song Song = new T_Song(Id, category, songName, singerName, song, songImage, favorite);
            list.add(Song);
            cursor.moveToNext();
        }
        return list;
    }

    // Method Search Category By Name
    public ArrayList<T_Song> getCategoryBy_Name(String S_Name) {

        ArrayList<T_Song> list = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from my_song where category like '%" + S_Name + "%'", null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {

            int Id = cursor.getInt(cursor.getColumnIndex("id"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            String songName = cursor.getString(cursor.getColumnIndex("songName"));
            String singerName = cursor.getString(cursor.getColumnIndex("singerName"));
            int song = cursor.getInt(cursor.getColumnIndex("song"));
            int songImage = cursor.getInt(cursor.getColumnIndex("songImage"));
            int favorite = cursor.getInt(cursor.getColumnIndex("favorite"));

            T_Song Song = new T_Song(Id, category, songName, singerName, song, songImage, favorite);
            list.add(Song);
            cursor.moveToNext();
        }
        return list;
    }

    // Method Search Favorite Song
    public ArrayList<T_Song> getFavorite() {

        ArrayList<T_Song> list = new ArrayList<>();
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from my_song where favorite = " + 1, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {

            int Id = cursor.getInt(cursor.getColumnIndex("id"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            String songName = cursor.getString(cursor.getColumnIndex("songName"));
            String singerName = cursor.getString(cursor.getColumnIndex("singerName"));
            int song = cursor.getInt(cursor.getColumnIndex("song"));
            int songImage = cursor.getInt(cursor.getColumnIndex("songImage"));
            int favorite = cursor.getInt(cursor.getColumnIndex("favorite"));

            T_Song Song = new T_Song(Id, category, songName, singerName, song, songImage, favorite);
            list.add(Song);
            cursor.moveToNext();
        }
        return list;
    }
    // Method Search Favorite Song
    public ArrayList<T_Song> getFavoriteCategory(ArrayList<T_Song> tSongs) {

        ArrayList<T_Song> list = new ArrayList<>();

        for (int I=0;I<tSongs.size();I++)
        {
            if(tSongs.get(I).getFavorite()==1){
                list.add(tSongs.get(I));
            }
        }
        return list;
    }


    public boolean updateData(T_Song tSong) {

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("category", tSong.getCategory());
        values.put("songName", tSong.getSongName());
        values.put("singerName", tSong.getSingerName());
        values.put("song", tSong.getSongSound());
        values.put("songImage", tSong.getSongImage());
        values.put("favorite", tSong.getFavorite());
        DB.update("my_song", values, "id= ?", new String[]{String.valueOf(tSong.getID())});
        return true;
    }

    public void DeletSong(int id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete("my_song", "id= ?", new String[]{String.valueOf(id)});
    }
}

