package com.riders.testing.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.riders.testing.R;
import com.riders.testing.utils.CompatibilityManagerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private Context context;

    @BindView(R.id.splash_video)
    VideoView splashVideoView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (CompatibilityManagerUtils.isTablet(this))
            //Force screen orientation to Landscape mode
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            //Force screen orientation to Portrait mode
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_splash);

        context = this;

        ButterKnife.bind(this);

        try {
            //set the uri of the video to be played
            splashVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_intro_testing_sound));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        splashVideoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        splashVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                //if we have a position on savedInstanceState, the video playback should start from here
                splashVideoView.seekTo(position);
                if (position == 0) {
                    splashVideoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    splashVideoView.pause();
                }
            }
        });

        splashVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e("SplashActivity", "Video done");

                if (null != progressBar) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (null != progressBar) {
                            progressBar = null;
                        }

                        startActivity(new Intent(context, MainActivity.class));
                        finish();

                    }
                }, 2000);
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", splashVideoView.getCurrentPosition());
        splashVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        position = savedInstanceState.getInt("Position");
        splashVideoView.seekTo(position);
    }
}
