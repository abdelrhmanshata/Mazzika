package com.example.mazzika.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.mazzika.NotificationReceiver;
import com.example.mazzika.R;
import com.example.mazzika.adapter.SectionPageAdapter;
import com.example.mazzika.fragment.FavoriteFragment;
import com.example.mazzika.fragment.HomeFragment;
import com.example.mazzika.fragment.PlayListFragment;
import com.example.mazzika.fragment.ProfileFragment;
import com.example.mazzika.fragment.SearchFragment;
import com.example.mazzika.song.T_Song;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity {

    public static final String CHANNEL_ID_1 = "channel1";
    public static final String CHANNEL_NAME_1 = "FRISTCHANNEL";

    private long backPressedTime;
    private Toast backToast;

    public static CircleImageView imageSong_Home;
    public static TextView nameSongHome, nameSingerHome;
    public static FloatingActionButton Play_sound;
    public static boolean is_PLAY = true;
    private boolean SHUFFLE = false;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SectionPageAdapter sectionPageAdapter;
    private NotificationManagerCompat mNotificationManagerCompat;
    private RelativeLayout Detalis_Song;

    public static void CheckButton() {

        if (MainActivity.soundPlay.isPlaying()) {
            Play_sound.setImageResource(R.drawable.ic_pause);
            is_PLAY = false;
        } else {
            Play_sound.setImageResource(R.drawable.ic_play_arrow);
            is_PLAY = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();
        setUpTabs();
        Detalis_Song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PlayerActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

    private void initialize() {

        imageSong_Home = findViewById(R.id.song_image_home);
        nameSongHome = findViewById(R.id.song_name_play_home);
        nameSingerHome = findViewById(R.id.singer_name_play_home);

        Play_sound = findViewById(R.id.play_home);

        mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        Detalis_Song = findViewById(R.id.details_SongPlay);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.container);
        tabLayout.setupWithViewPager(viewPager);
        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
    }

    public void B_Play(View view) {
          sendNotification();
        if (!MainActivity.soundPlay.isPlaying()) {
            MainActivity.soundPlay.start();
            is_PLAY = false;
            Play_sound.setImageResource(R.drawable.ic_pause);
        } else {
            MainActivity.soundPlay.pause();
            is_PLAY = true;
            Play_sound.setImageResource(R.drawable.ic_play_arrow);
        }
    }

    public void B_Next(View view) {
        isShuffle();
        if (!isShuffle())
            nextSong();
    }

    public void B_Previous(View view) {
        isShuffle();
        if (!isShuffle())
            previousSong();
    }

    private void Refresh() {
        Thread thread;
        thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(100);
                        CheckButton();
                        MainActivity.soundPlay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                Play_sound.setImageResource(R.drawable.ic_play_arrow);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private void setUpTabs() {
        sectionPageAdapter.addFragment(new HomeFragment(), "Home");
        sectionPageAdapter.addFragment(new SearchFragment(), "Search");
        sectionPageAdapter.addFragment(new FavoriteFragment(), "Favorite");
        sectionPageAdapter.addFragment(new PlayListFragment(), "play list");
        sectionPageAdapter.addFragment(new ProfileFragment(), "mee");
        viewPager.setAdapter(sectionPageAdapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.home_50dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search);
        tabLayout.getTabAt(2).setIcon(R.drawable.favorite_35dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_playlist);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_user);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences("ID_Song", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ID", MainActivity.songObject.getID());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckButton();
        imageSong_Home.setImageResource(MainActivity.songObject.getSongImage());
        nameSongHome.setText(MainActivity.songObject.getSongName() + "");
        nameSingerHome.setText(MainActivity.songObject.getSingerName() + "");
        SharedPreferences preferences = getSharedPreferences("Data_Setting", getApplicationContext().MODE_PRIVATE);
        SHUFFLE = preferences.getBoolean("SHUFFLE", false);
    }

    public boolean isShuffle() {
        if (!SHUFFLE) {
            Random random = new Random();
            PlayerActivity.Category = random.nextInt(MainActivity.Song.size());
            PlayerActivity.Sound = random.nextInt(MainActivity.Song.get(PlayerActivity.Category).size());

            MainActivity.soundPlay.stop();
            MainActivity.soundPlay.release();
            MainActivity.songObject = MainActivity.Song.get(PlayerActivity.Category).get(PlayerActivity.Sound);

            Configure_sound();

            return true;
        }
        return false;
    }

    public void nextSong() {

        MainActivity.soundPlay.stop();
        MainActivity.soundPlay.release();

        PlayerActivity.Category = getCategory(MainActivity.songObject);
        PlayerActivity.Sound = MainActivity.Song.get(PlayerActivity.Category).indexOf(MainActivity.songObject);
        PlayerActivity.Sound++;
        if (PlayerActivity.Sound < (MainActivity.Song.get(getCategory(MainActivity.songObject)).size()))
            MainActivity.songObject = MainActivity.Song.get(PlayerActivity.Category).get(PlayerActivity.Sound);
        else {
            PlayerActivity.Sound = 0;
            MainActivity.songObject = MainActivity.Song.get(PlayerActivity.Category).get(0);
        }

        Configure_sound();
    }

    public void previousSong() {

        MainActivity.soundPlay.stop();
        MainActivity.soundPlay.release();

        PlayerActivity.Category = getCategory(MainActivity.songObject);

        PlayerActivity.Sound = MainActivity.Song.get(PlayerActivity.Category).indexOf(MainActivity.songObject);

        if (PlayerActivity.Sound == 0)
            PlayerActivity.Sound = (MainActivity.Song.get(getCategory(MainActivity.songObject)).size()) - 1;
        else
            PlayerActivity.Sound--;
        MainActivity.songObject = MainActivity.Song.get(PlayerActivity.Category).get(PlayerActivity.Sound);
        Configure_sound();
    }

    private void Configure_sound() {

        MainActivity.soundPlay = MediaPlayer.create(getApplicationContext(), MainActivity.songObject.getSongSound());
        MainActivity.soundPlay.start();

        imageSong_Home.setImageResource(MainActivity.songObject.getSongImage());
        nameSongHome.setText(MainActivity.songObject.getSongName());
        nameSingerHome.setText(MainActivity.songObject.getSingerName());
        CheckButton();
    }

    private int getCategory(T_Song tSong) {

        if (tSong.getCategory().equalsIgnoreCase("JAZZ"))
            return 0;
        else if (tSong.getCategory().equalsIgnoreCase("HIP_HOP"))
            return 1;
        else if (tSong.getCategory().equalsIgnoreCase("ROCK"))
            return 2;
        else if (tSong.getCategory().equalsIgnoreCase("ELECTRONIC"))
            return 3;
        else if (tSong.getCategory().equalsIgnoreCase("DANCE"))
            return 4;
        else if (tSong.getCategory().equalsIgnoreCase("HEAVY_METAL"))
            return 5;
        else if (tSong.getCategory().equalsIgnoreCase("FOLK"))
            return 6;
        else if (tSong.getCategory().equalsIgnoreCase("CLASSICAL"))
            return 7;
        else if (tSong.getCategory().equalsIgnoreCase("MAHRGANNAT"))
            return 8;
        else
            return 0;
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toasty.info(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    public void sendNotification() {

        Bitmap largeImage = ((BitmapDrawable) imageSong_Home.getDrawable()).getBitmap();

        Intent intent = new Intent(HomeActivity.this , NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        Notification channel = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID_1)

                //  .setAutoCancel(true)
                //.setLocalOnly(true)
                //.setSmallIcon(R.drawable.ic_music,10)
                //.setCategory("JAZZ")
                .setBadgeIconType((int) R.drawable.images)
                .setOnlyAlertOnce(true)
                //
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_music)
                .setContentTitle("Let me Love Notification")
                .setContentText("" + nameSongHome.getText().toString())
                .setLargeIcon(largeImage)
                .addAction(R.drawable.ic_favorite_black_24dp, "Favorite",pendingIntent)
                .addAction(R.drawable.ic_skip_previous, "Previous", pendingIntent)
                .addAction(R.drawable.ic_play_arrow, "Play",pendingIntent)
                .addAction(R.drawable.ic_skip_next, "Next", pendingIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3))
                .build();
        mNotificationManagerCompat.notify(1, channel);
    }
}
