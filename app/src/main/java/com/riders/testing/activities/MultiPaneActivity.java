package com.riders.testing.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riders.testing.R;
import com.riders.testing.adapters.MoviesAdapter;
import com.riders.testing.constants.MovieImagesList;
import com.riders.testing.fragments.MultiPaneDetailFragment;
import com.riders.testing.fragments.MultiPaneMainFragment;
import com.riders.testing.model.Movie;
import com.riders.testing.utils.CompatibilityManagerUtils;
import com.riders.testing.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultiPaneActivity extends AppCompatActivity implements MultiPaneMainFragment.OnItemSelectedListener {

    //Tag & Context
    private static final String TAG = MultiPaneActivity.class.getSimpleName();
    private Context mContext;


    @BindView(R.id.rv_multi_pane_main)
    RecyclerView recyclerView;

    private List<Movie> movieList;
    private MoviesAdapter mAdapter;

    ///////////////////////////////////////////
    //
    //  Override methods
    //
    ///////////////////////////////////////////
    //TODO : Check if tablet then multi pane or new activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (CompatibilityManagerUtils.isTablet(this))
            //Force screen orientation to Landscape mode
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            //Force screen orientation to Portrait mode
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_multi_pane);

        mContext = this;
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(TAG);


        prepareMovieData();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Movie movie = movieList.get(position);
                Toast.makeText(mContext, movie.getTitle() + " is selected", Toast.LENGTH_SHORT).show();

                // send data to activity
//                listener.onMovieItemSelected(movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getUrlThumbnail());

            }

            @Override
            public void onLongClick(View view, int position) {
                Movie movie = movieList.get(position);
                Toast.makeText(mContext, "Long click on : " + movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }));

        mAdapter = new MoviesAdapter(movieList);
        recyclerView.setAdapter(mAdapter);
    }
    ///////////////////////////////////////////
    //
    //  Override methods
    //
    ///////////////////////////////////////////

    ///////////////////////////////////////////
    //
    //  Class methods
    //
    ///////////////////////////////////////////
    private void prepareMovieData() {
        movieList = new ArrayList<>();

        Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015", MovieImagesList.MAD_MAX.url);
        movieList.add(movie);

        movie = new Movie("Inside Out", "Animation, Kids & Family", "2015", MovieImagesList.INSIDE_OUT.url);
        movieList.add(movie);

        movie = new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015", MovieImagesList.STAR_WARS.url);
        movieList.add(movie);

        movie = new Movie("Shaun the Sheep", "Animation", "2015", MovieImagesList.SHAUN_THE_SHEEP.url);
        movieList.add(movie);

        movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015", MovieImagesList.THE_MARTIAN.url);
        movieList.add(movie);

        movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015", MovieImagesList.MI_ROGUE_NATION.url);
        movieList.add(movie);

        movie = new Movie("Up", "Animation", "2009", MovieImagesList.UP.url);
        movieList.add(movie);

        movie = new Movie("Star Trek", "Science Fiction", "2009", MovieImagesList.STAR_TREK.url);
        movieList.add(movie);

        movie = new Movie("The LEGO Movie", "Animation", "2014", MovieImagesList.LEGO.url);
        movieList.add(movie);

        movie = new Movie("Iron Man", "Action & Adventure", "2008", MovieImagesList.IRON_MAN.url);
        movieList.add(movie);

        movie = new Movie("Aliens", "Science Fiction", "1986", MovieImagesList.ALIENS.url);
        movieList.add(movie);

        movie = new Movie("Chicken Run", "Animation", "2000", MovieImagesList.CHICKEN_RUN.url);
        movieList.add(movie);

        movie = new Movie("Back to the Future", "Science Fiction", "1985", MovieImagesList.BACK_TO_FUTURE.url);
        movieList.add(movie);

        movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981", MovieImagesList.RAIDERS_OF_THE_LOST_ARK.url);
        movieList.add(movie);

        movie = new Movie("Goldfinger", "Action & Adventure", "1965", MovieImagesList.GOLFINGER.url);
        movieList.add(movie);

        movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014", MovieImagesList.GUARDIANS_OF_THE_GALXY.url);
        movieList.add(movie);
    }
    ///////////////////////////////////////////
    //
    //  Class methods
    //
    ///////////////////////////////////////////

    ///////////////////////////////////////////
    //
    //  Listeners methods
    //
    ///////////////////////////////////////////
    @Override
    public void onMovieItemSelected(String movieTitle, String movieGenre, String movieYear, String movieUrl) {

        Log.d(TAG, "Implemented onMovieItemSelected()");

        MultiPaneDetailFragment fragment = (MultiPaneDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);

        if (fragment != null &&
                fragment.isInLayout() &&
                fragment.isVisible()) {
            fragment.setTextOnFragment(movieTitle, movieGenre, movieYear, movieUrl);
        } else {
            Log.e(TAG, "Open detail activity");
        }
    }
    ///////////////////////////////////////////
    //
    //  Listeners methods
    //
    ///////////////////////////////////////////
}
