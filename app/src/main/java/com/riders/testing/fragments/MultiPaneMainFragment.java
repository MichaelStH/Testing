package com.riders.testing.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riders.testing.R;
import com.riders.testing.adapters.MoviesAdapter;
import com.riders.testing.constants.MovieImagesList;
import com.riders.testing.model.Movie;
import com.riders.testing.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MultiPaneMainFragment extends Fragment {

    private static final String TAG = MultiPaneMainFragment.class.getSimpleName();
    private Context mContext = null;

    @BindView(R.id.rv_multi_pane_main)
    RecyclerView recyclerView;
    private Unbinder unbinder;

    private List<Movie> movieList;
    private MoviesAdapter mAdapter;

    /**
     * passing data between fragments
     */
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        void onMovieItemSelected(String movieTitle, String movieGenre, String movieYear, String url);
    }


    public static MultiPaneMainFragment newInstance() {

        Bundle args = new Bundle();

        MultiPaneMainFragment fragment = new MultiPaneMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    ///////////////////////////////////////////
    //
    //  Override methods
    //
    ///////////////////////////////////////////
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MultiPaneActivity.OnItemSelectedListener");
        }
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_multi_pane_main, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prepareMovieData();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Movie movie = movieList.get(position);
                Toast.makeText(mContext, movie.getTitle() + " is selected", Toast.LENGTH_SHORT).show();

                // send data to activity
                listener.onMovieItemSelected(movie.getTitle(), movie.getGenre(), movie.getYear(), movie.getUrlThumbnail());

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
}
