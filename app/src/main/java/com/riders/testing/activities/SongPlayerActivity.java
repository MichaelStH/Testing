package com.riders.testing.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.riders.testing.R;
import com.riders.testing.utils.SongsPlayerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongPlayerActivity extends AppCompatActivity {

    private static final String TAG = SongPlayerActivity.class.getSimpleName();

    //Views
    @BindView(R.id.songs_player_gridLayout)
    GridLayout mGridLayout;
    @BindView(R.id.songs_player_listView)
    ListView mSongsListView;
    @BindView(R.id.songs_player_textView)
    TextView mSongsTextView;
    @BindView(R.id.song_player_toolbar)
    Toolbar toolbar;

    SongsPlayerManager mManager;
    ArrayList<HashMap<String, String>> playList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_player);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mManager = new SongsPlayerManager();
        Log.d(TAG, "Preparing data");
        prepareData();

    }


    private void prepareData() {

        playList = mManager.getPlayList();

        //debug
        if (playList != null && playList.size() == 0) {
            mSongsListView.setAdapter(new SimpleAdapter(
                    this,
                    playList,
                    android.R.layout.simple_list_item_1,
                    new String[]{"La Liste est vide"},
                    new int[]{android.R.id.text1}

            ));
            Log.d(TAG, "Leave the function");
            return;
        }

        Log.d(TAG, "Debug the files found");
        for (HashMap<String, String> song : playList) {
            for (Map.Entry<String, String> key : song.entrySet()) {
                Log.e(TAG, key.getKey() + " - " + key.getValue());
            }
        }

        Log.d(TAG, "Fill the listView with data");
        renderPost();
    }

    private void renderPost() {
        //mSongsListView.setAdapter(new SongsAdapter( playList ) );

        mSongsListView.setAdapter(new SimpleAdapter(
                this,
                playList,
                android.R.layout.simple_list_item_2,
                new String[]{mManager.SONG_MAP_KEY, mManager.SONG_MAP_VALUE},
                new int[]{android.R.id.text1, android.R.id.text2}

        ));
    }
}
