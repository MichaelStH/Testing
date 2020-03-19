package com.riders.testing.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.riders.testing.R;
import com.riders.testing.model.Video;

import butterknife.BindView;
import butterknife.ButterKnife;


public class YoutubeDetailActivity extends AppCompatActivity {

    //TAG and context
    private static final String TAG = YoutubeDetailActivity.class.getSimpleName();
    private Context mContext;

    //Bundle Arguments
    public static final String VIDEO_OBJECT_ARG = "content_video";

    @BindView(R.id.content_image_thumb)
    ImageView imageThumb;
    @BindView(R.id.content_image_thumb_blurred)
    ImageView imageThumbBlurred;
    @BindView(R.id.content_text_name)
    TextView titleTextView;
    @BindView(R.id.content_text_description)
    TextView descriptionTextView;

    private Video item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Where myBitmap is the Image from which you want to extract the color.
        Also for API 21 and above, you'll need to add the following flags if you're planning to color the status bar and navigation bar:
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        setContentView(R.layout.activity_youtube_detail);

        mContext = this;
        ButterKnife.bind(this);

        getBundle();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadContent();
    }

    private void getBundle() {
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            Log.e(TAG, "bundle is null - check the data you are trying to pass through please !");
        } else {
            Log.e(TAG, "get the data one by one");

            item = extras.getParcelable(VIDEO_OBJECT_ARG);
        }
    }

    private void loadContent() {

        //Load the background  thumb image
        Glide.with(this)
                .load(item.getImageThumb())
                .into(imageThumbBlurred);

//        ImageManagerUtils.setBlurredImage(this, imageThumbBlurred, 5);

        ViewCompat.setTransitionName(imageThumb, "thumb");


        //Load the thumb image clicked before
        Glide.with(this)
                .load(item.getImageThumb())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        //retrouver le bitmap téléchargé par Picasso
                        Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();

                        //demande à la palette de générer ses coleurs, de façon asynchrone
                        //afin de ne pas bloquer l'interface graphique
                        new Palette.Builder(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {

                                //lorsque la palette est générée, je l'utilise sur mes textViews
                                generatePalette(palette);
                            }
                        });

                        return false;
                    }
                })
                .into(imageThumb);

        titleTextView.setText(item.getName());
        descriptionTextView.setText(item.getDescription());

    }


    /**
     * Generate palette in order to change toolbar's color
     * @param palette
     */
    public void generatePalette(Palette palette) {
        {
            Palette.Swatch muted = palette.getMutedSwatch();

            //il se peut que la palette ne génère pas tous les swatch
            if (muted != null) {
                //j'utilise getRgb() en tant que couleur de fond de ma toolbar
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(muted.getRgb()));
            }
        }
        {
            Palette.Swatch mutedDark = palette.getDarkMutedSwatch();
            if (mutedDark != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(mutedDark.getRgb());
                }
            }

        }
    }
}
