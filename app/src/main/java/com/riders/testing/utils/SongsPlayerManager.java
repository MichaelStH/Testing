package com.riders.testing.utils;

import android.os.Environment;
import android.util.Log;


import com.riders.testing.application.MyApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by michael on 22/02/2017.
 */

public class SongsPlayerManager {

    private static final String TAG = SongsPlayerManager.class.getSimpleName();

    final String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
    final String SD_CARD_PATH = Environment.getExternalStorageState();

    private ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
    public String SONG_MAP_KEY = "songTitle";
    public String SONG_MAP_VALUE = "songPath";

    private String mp3Pattern = ".mp3";
    private String m4aPattern = ".m4a";


    public SongsPlayerManager() {
        Log.d( TAG, MEDIA_PATH );
        Log.d( TAG, SD_CARD_PATH );
    }


    /**
     * Function to read all mp3 files and store the details in
     * ArrayList
     */
    public ArrayList<HashMap<String, String>> getPlayList() {

        if ( CompatibilityManagerUtils.isLollipopPlus() ){
            Utils.showActionInToast( MyApplication.getAppContext(), "App's API version above Lollipop" );
            return songsList;
        }

        if ( MEDIA_PATH != null ) {
            File home = new File( MEDIA_PATH );
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    Log.d( TAG, file.getAbsolutePath() );
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        if ( SD_CARD_PATH != null ) {
            File home = new File( SD_CARD_PATH );
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    Log.d( TAG, file.getAbsolutePath() );
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        // return songs list array
        return songsList;
    }

    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }

                }
            }
        }
    }

    private void addSongToList(File song) {
        if ( song.getName().endsWith( mp3Pattern )
                || song.getName().endsWith( m4aPattern ) ) {
            HashMap<String, String> songMap = new HashMap<String, String>();
            songMap.put(SONG_MAP_KEY, song.getName().substring(0, (song.getName().length() - 4)));
            songMap.put(SONG_MAP_VALUE, song.getPath());

            // Adding each song to SongList
            songsList.add(songMap);
        }
    }
}