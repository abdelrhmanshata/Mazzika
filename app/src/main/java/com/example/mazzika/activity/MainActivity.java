package com.example.mazzika.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mazzika.DataBase.DB_Song;
import com.example.mazzika.R;
import com.example.mazzika.song.T_Song;
import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    public static ArrayList<ArrayList<T_Song>> Song;

    public static MediaPlayer soundPlay = null;
    public static T_Song songObject = null;

    public static DB_Song dbSong = null;

    private int ID = 1;

    SQLiteDatabase database ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbSong = new DB_Song(this);
        Song = new ArrayList<>();

       /* dbSong.insertSong();*/

        SharedPreferences preferences = getSharedPreferences("ID_Song", getApplicationContext().MODE_PRIVATE);
        ID = preferences.getInt("ID", 1);

        creatSong();
        songObject = dbSong.getSong_By_Id(ID);
        soundPlay = MediaPlayer.create(this, songObject.getSongSound());

        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        finish();

        //IFireBase fireBase = new FireBaseFireStore(FireBaseFireStore.USER, new User("+20102268183"));
        //fireBase.createNewUser();
        // fireBase.changeName(new User(new Name("Tarek", "Alaa", "Attia"), "Egypt", "+201022268183", new ArrayList<Song>(), new ArrayList<Song>(), new ArrayList<PlayList>()),new Name("alaa","tarek","yomna"));
        // fireBase.createPlayList("new play list2");
        // fireBase.addSongToPlayList("new play list2", "song1");
        //FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        // firebaseFirestore.collection(FireBaseFireStore.SONG).document("song1").set(new Song());

    }

    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build());
        // Create and launch sign-in intent
        System.out.println("done");
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]

    }

    private void creatSong() {

        Song.add(dbSong.getCategoryBy_Name("JAZZ"));
        Song.add(dbSong.getCategoryBy_Name("HIP_HOP"));
        Song.add(dbSong.getCategoryBy_Name("ROCK"));
        Song.add(dbSong.getCategoryBy_Name("ELECTRONIC"));
        Song.add(dbSong.getCategoryBy_Name("DANCE"));
        Song.add(dbSong.getCategoryBy_Name("HEAVY_METAL"));
        Song.add(dbSong.getCategoryBy_Name("FOLK"));
        Song.add(dbSong.getCategoryBy_Name("CLASSICAL"));
        Song.add(dbSong.getCategoryBy_Name("MAHRGANNAT"));
    }
}
