package com.riders.testing.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.riders.testing.R;
import com.riders.testing.adapters.YoutubeListAdapter;
import com.riders.testing.application.MyApplication;
import com.riders.testing.bus.FetchYoutubeContentEvent;
import com.riders.testing.interfaces.YoutubeListClickListener;
import com.riders.testing.model.Video;
import com.riders.testing.utils.DeviceManagerUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoutubeActivity extends AppCompatActivity implements YoutubeListClickListener {

    private static final String TAG = YoutubeActivity.class.getSimpleName();
    private Context mContext;

    //Views and adapter
    @BindView(R.id.no_connection_linear_container)
    LinearLayout mLinearNoConnectionContainer;

    @BindView(R.id.youtube_content_loader)
    ProgressBar mLoader;

    @BindView(R.id.youtube_content_recyclerView)
    RecyclerView contentRecyclerView;

    private ArrayList<Video> youtubeList;
    private YoutubeListAdapter contentAdapter;


    ///////////////////////////////////////////
    //
    //  Override methods
    //
    ///////////////////////////////////////////
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        mContext = this;
        ButterKnife.bind(this);


        //create a new progress bar for each image to be loaded
        mLoader.setVisibility(View.VISIBLE);

        //Test the internet's connection
        if (!DeviceManagerUtils.isConnected(mContext)) {
            Log.e(TAG, "No Internet connection");
//            Utils.dismissLoader(mLoader);
            mLoader.setVisibility(View.GONE);

            mLinearNoConnectionContainer.setVisibility(View.VISIBLE);

//            Utils.showActionInToast(mContext, mContext.getResources().getString(R.string.pas_de_connexion));

        } else {

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            contentRecyclerView.setLayoutManager(layoutManager);
            contentRecyclerView.setItemAnimator(new DefaultItemAnimator());

            Log.e(TAG, "Fetch Content");
            fetchContent();
        }
    }

    private void fetchContent() {
        Call<List<Video>> call = MyApplication.getInstance().getYoutubeRestClient().getApiService().fetchYoutubeVideos();
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().toString());

                    youtubeList = new ArrayList<>();
                    youtubeList = (ArrayList<Video>) response.body();

                    EventBus.getDefault().post(new FetchYoutubeContentEvent());

                } else if (response.code() >= 400) {
                    Log.e(TAG, "error 400+ : " + response.code());
                } else {
                    Log.e(TAG, "error 500+ : " + response.code());

                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    ///////////////////////////////////////////
    //
    //  Override methods
    //
    ///////////////////////////////////////////


    ///////////////////////////////////////////
    //
    //  EventBus methods
    //
    ///////////////////////////////////////////
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFetchEvent(FetchYoutubeContentEvent event) {

        Toast.makeText(this, event.toString(), Toast.LENGTH_SHORT).show();

        contentAdapter = new YoutubeListAdapter(mContext, youtubeList, this);

        mLoader.setVisibility(View.GONE);

        if (contentRecyclerView != null && !contentRecyclerView.isInLayout()) {
            contentRecyclerView.setVisibility(View.VISIBLE);
        }

        contentRecyclerView.setAdapter(contentAdapter);
    }
    ///////////////////////////////////////////
    //
    //  EventBus methods
    //
    ///////////////////////////////////////////


    ///////////////////////////////////////////
    //
    //  Listeners methods
    //
    ///////////////////////////////////////////
    @Override
    public void onYoutubeItemClicked(@NonNull ImageView view, Video video, int position) {

        Log.e(TAG, "Click on : " + position + ", " + video.getName());
        Intent intent = new Intent(YoutubeActivity.this, YoutubeDetailActivity.class);

        intent.putExtra(YoutubeDetailActivity.VIDEO_OBJECT_ARG, video);

        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            // Apply activity transition
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(YoutubeActivity.this,
                            view,
                            ViewCompat.getTransitionName(view));
            startActivity(intent, options.toBundle());

        } else {
            // Swap without transition
            startActivity(intent);
        }
    }

    ///////////////////////////////////////////
    //
    //  Listeners methods
    //
    ///////////////////////////////////////////
}
