package com.example.mazzika.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mazzika.DataBase.DB_Song;
import com.example.mazzika.R;
import com.example.mazzika.song.T_Song;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Random;

public class PlayerActivity extends AppCompatActivity {

    public static int Category = 0;
    public static int Sound = 0;
    private boolean REPLAY = false, SHUFFLE = false, FAVORITE = false;
    private DB_Song dbSong;
    private ImageView ParentBackGround, FavoriteSong, lowVolume;
    private TextView SongName, SingerName, ToolBar_Text, tv_song_current_duration, tv_song_total_duration;
    private ImageButton B_Shuffle, B_Repeat;
    private View parent_view;
    private AppCompatSeekBar seek_song_progressbar;
    private FloatingActionButton B_Play;
    private CircularImageView SoungImageView;
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;
    private Handler mHandler = new Handler();
    private MusicUtils utils;
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            updateTimerAndSeekbar();
            if (MainActivity.soundPlay.isPlaying()) {
                mHandler.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initialize();
        setMusicPlayerComponents();

        ConstraintLayout layout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


    }

    private void initialize() {
        dbSong = new DB_Song(this);
        /*ParentBackGround = findViewById(R.id.parent_BackGround);*/
        SoungImageView = findViewById(R.id.soung_imageView);
        SongName = findViewById(R.id.songName);
        SingerName = findViewById(R.id.singerName);
        FavoriteSong = findViewById(R.id.favoriteSong);
        ToolBar_Text = findViewById(R.id.toolbar_text);
        B_Shuffle = findViewById(R.id.btn_shuffle);
        B_Play = findViewById(R.id.btn_play);
        B_Repeat = findViewById(R.id.btn_repeat);
        volumeSeekbar = findViewById(R.id.seekBarVolume);
        lowVolume = findViewById(R.id.lowVolume);

        parent_view = findViewById(R.id.parent_view);
        seek_song_progressbar = findViewById(R.id.seek_song_progressbar);

        seek_song_progressbar.setProgress(0);
        seek_song_progressbar.setMax(MusicUtils.MAX_PROGRESS);

        tv_song_current_duration = findViewById(R.id.tv_song_current_duration);
        tv_song_total_duration = findViewById(R.id.total_duration);
    }

    private void setMusicPlayerComponents() {

        MainActivity.soundPlay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                B_Play.setImageResource(R.drawable.ic_play_arrow);
            }
        });

       /* try {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            AssetFileDescriptor afd = getAssets().openFd("webna_maad_amr_diab.mp3"); // this is music
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (Exception e) {
            Snackbar.make(parent_view, "Cannot load audio file", Snackbar.LENGTH_SHORT).show();
        }*/

        utils = new MusicUtils();
        seek_song_progressbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = MainActivity.soundPlay.getDuration();
                int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
                MainActivity.soundPlay.seekTo(currentPosition);
                mHandler.post(mUpdateTimeTask);
            }
        });
        buttonPlayerAction();
        updateTimerAndSeekbar();
    }

    private void buttonPlayerAction() {
        B_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (MainActivity.soundPlay.isPlaying()) {
                    MainActivity.soundPlay.pause();
                    B_Play.setImageResource(R.drawable.ic_play_arrow);
                } else {
                    MainActivity.soundPlay.start();
                    B_Play.setImageResource(R.drawable.ic_pause);
                    mHandler.post(mUpdateTimeTask);
                }
                rotateTheDisk();
            }
        });
    }

    public void controlClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_prev: {
                isShuffle();
                if (!isShuffle())
                    previousSong();
                toggleButtonColor((ImageButton) v);
                break;
            }
            case R.id.btn_next: {
                isShuffle();
                if (!isShuffle())
                    nextSong();
                toggleButtonColor((ImageButton) v);
                break;
            }
        }
    }

    private boolean toggleButtonColor(ImageButton bt) {
        String selected = (String) bt.getTag(bt.getId());
        if (selected != null) { // selected
            bt.setColorFilter(getResources().getColor(R.color.colorDarkOrange), PorterDuff.Mode.SRC_ATOP);
            bt.setTag(bt.getId(), null);
            return false;
        } else {
            bt.setTag(bt.getId(), "selected");
            bt.setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
            return true;
        }
    }

    private void updateTimerAndSeekbar() {
        long totalDuration = MainActivity.soundPlay.getDuration();
        long currentDuration = MainActivity.soundPlay.getCurrentPosition();

        tv_song_total_duration.setText(utils.milliSecondsToTimer(totalDuration));
        tv_song_current_duration.setText(utils.milliSecondsToTimer(currentDuration));

        int progress = (int) (utils.getProgressSeekBar(currentDuration, totalDuration));
        seek_song_progressbar.setProgress(progress);
    }

    private void rotateTheDisk() {
        if (!MainActivity.soundPlay.isPlaying()) return;
        SoungImageView.animate().setDuration(100).rotation(SoungImageView.getRotation() + 2f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rotateTheDisk();
                controlsVolume();
                super.onAnimationEnd(animation);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    private void controlsVolume() {
        try {

            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));

            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));

            if (volumeSeekbar.getProgress() == 0) {
                lowVolume.setImageResource(R.drawable.ic_volume_mute_black_24dp);
            } else if (volumeSeekbar.getProgress() < 10) {
                lowVolume.setImageResource(R.drawable.ic_volume_down);
            } else {
                lowVolume.setImageResource(R.drawable.ic_volume_up);
            }

            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Refresh() {
        Thread thread;
        thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(100);
                        controlsVolume();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private void Ckeck() {

        if (MainActivity.soundPlay.isPlaying()) {
            B_Play.setImageResource(R.drawable.ic_pause);
        } else {
            B_Play.setImageResource(R.drawable.ic_play_arrow);
        }

        if (MainActivity.songObject.getFavorite() == 0) {
            FAVORITE = true;
            FavoriteSong.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } else {
            FAVORITE = false;
            FavoriteSong.setImageResource(R.drawable.ic_favorite_black_24dp);
        }

        if (!REPLAY) {
            MainActivity.soundPlay.setLooping(true);
            B_Repeat.setColorFilter(getResources().getColor(R.color.colorDarkOrange), PorterDuff.Mode.SRC_ATOP);
        } else {
            MainActivity.soundPlay.setLooping(false);
            B_Repeat.setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
        }

        if (!SHUFFLE) {
            B_Shuffle.setColorFilter(getResources().getColor(R.color.colorDarkOrange), PorterDuff.Mode.SRC_ATOP);
        } else {
            B_Shuffle.setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void getFavoriteSong(View view) {
        if (FAVORITE) {
          //  Snackbar.make(parent_view, "FAVORITE true", Snackbar.LENGTH_SHORT).show();
            FavoriteSong.setImageResource(R.drawable.ic_favorite_black_24dp);
            FAVORITE = false;
            MainActivity.songObject.setFavorite(1);
            dbSong.updateData(MainActivity.songObject);
        } else {
          //  Snackbar.make(parent_view, "FAVORITE false", Snackbar.LENGTH_SHORT).show();
            FavoriteSong.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            FAVORITE = true;
            MainActivity.songObject.setFavorite(0);
            dbSong.updateData(MainActivity.songObject);
        }
    }

    public void getReplay(View view) {
        if (REPLAY) {
          //  Snackbar.make(parent_view, "Repeat true", Snackbar.LENGTH_SHORT).show();
            B_Repeat.setColorFilter(getResources().getColor(R.color.colorDarkOrange), PorterDuff.Mode.SRC_ATOP);
            MainActivity.soundPlay.setLooping(true);
            REPLAY = false;
        } else {
          //  Snackbar.make(parent_view, "Repeat false", Snackbar.LENGTH_SHORT).show();
            B_Repeat.setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
            MainActivity.soundPlay.setLooping(false);
            REPLAY = true;
        }
    }

    public void getShuffle(View view) {

        if (SHUFFLE) {
          //  Snackbar.make(parent_view, "Shuffle true", Snackbar.LENGTH_SHORT).show();
            B_Shuffle.setColorFilter(getResources().getColor(R.color.colorDarkOrange), PorterDuff.Mode.SRC_ATOP);
            SHUFFLE = false;
        } else {
           // Snackbar.make(parent_view, "Shuffle false", Snackbar.LENGTH_SHORT).show();
            B_Shuffle.setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
            SHUFFLE = true;
        }
    }

    private void nextSong() {

        MainActivity.soundPlay.stop();
        MainActivity.soundPlay.release();

        Category = getCategory(MainActivity.songObject);
        Sound = MainActivity.Song.get(Category).indexOf(MainActivity.songObject);
        Sound++;

        if (Sound < (MainActivity.Song.get(getCategory(MainActivity.songObject)).size()))
            MainActivity.songObject = MainActivity.Song.get(Category).get(Sound);
        else {
            Sound = 0;
            MainActivity.songObject = MainActivity.Song.get(Category).get(0);
        }

        Configure_sound();
    }

    private void previousSong() {

        MainActivity.soundPlay.stop();
        MainActivity.soundPlay.release();

        Category = getCategory(MainActivity.songObject);

        Sound = MainActivity.Song.get(Category).indexOf(MainActivity.songObject);

        if (Sound == 0)
            Sound = (MainActivity.Song.get(getCategory(MainActivity.songObject)).size()) - 1;
        else
            Sound--;
        MainActivity.songObject = MainActivity.Song.get(Category).get(Sound);
        Configure_sound();
    }


    private boolean isShuffle() {
        if (!SHUFFLE) {

            Random random = new Random();
            Category = random.nextInt(MainActivity.Song.size());
            Sound = random.nextInt(MainActivity.Song.get(Category).size());

            MainActivity.soundPlay.stop();
            MainActivity.soundPlay.release();

            MainActivity.songObject = MainActivity.Song.get(Category).get(Sound);
            Configure_sound();
            return true;
        }
        return false;
    }

    private void Configure_sound() {
        ToolBar_Text.setText(MainActivity.songObject.getCategory());
        SongName.setText(MainActivity.songObject.getSongName());
        SingerName.setText(MainActivity.songObject.getSingerName());
        SoungImageView.setImageResource(MainActivity.songObject.getSongImage());
        MainActivity.soundPlay = MediaPlayer.create(this, MainActivity.songObject.getSongSound());
        MainActivity.soundPlay.start();

        Ckeck();
        rotateTheDisk();
        mUpdateTimeTask.run();

        SharedPreferences preferences = getSharedPreferences("ID_Song", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ID", MainActivity.songObject.getID());
        editor.apply();
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
    protected void onResume() {
        super.onResume();
        ToolBar_Text.setText(MainActivity.songObject.getCategory());
        SongName.setText(MainActivity.songObject.getSongName());
        SingerName.setText(MainActivity.songObject.getSingerName());
        SoungImageView.setImageResource(MainActivity.songObject.getSongImage());

        SharedPreferences preferences = getSharedPreferences("Data_Setting", getApplicationContext().MODE_PRIVATE);
        REPLAY = preferences.getBoolean("REPLAY", false);
        SHUFFLE = preferences.getBoolean("SHUFFLE", false);

        setMusicPlayerComponents();
        Refresh();
        Ckeck();
        rotateTheDisk();
        mUpdateTimeTask.run();
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences("Data_Setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("REPLAY", REPLAY);
        editor.putBoolean("SHUFFLE", SHUFFLE);
        editor.apply();
    }

}
