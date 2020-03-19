package com.riders.testing.activities;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import com.riders.testing.R;
import com.riders.testing.utils.CompatibilityManagerUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by michael on 12/01/2016.
 */
public class PaletteActivity extends AppCompatActivity {

    private static final String TAG = PaletteActivity.class.getSimpleName();

    private static String IMAGE_URL = "http://i.ytimg.com/vi/aNHOfJCphwk/maxresdefault.jpg";

    @BindView(R.id.palette_image)
    ImageView imageView;

    @BindView(R.id.textVibrant)
    TextView textVibrant;
    @BindView(R.id.textVibrantLight)
    TextView textVibrantLight;
    @BindView(R.id.textVibrantDark)
    TextView textVibrantDark;
    @BindView(R.id.textMuted)
    TextView textMuted;
    @BindView(R.id.textMutedLight)
    TextView textMutedLight;
    @BindView(R.id.textMutedDark)
    TextView textMutedDark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (CompatibilityManagerUtils.isTablet(this))
            //Force screen orientation to Landscape
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_palette);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(TAG);

        // InitViews
        ButterKnife.bind(this);
        Log.i(TAG, "View initialized");

        getImage();
    }


    public void getImage() {
        //j'utilise picasso afin de récupérer l'image
        Picasso.get()
                .load(IMAGE_URL)
                //.load(R.drawable.image1)
                .fit()
                .centerCrop()
                .into(imageView,
                        //j'écoute le chargement via picasso
                        new Callback() {
                            @Override

                            //puis lorsque l'image a bien été chargée
                            public void onSuccess() {

                                Log.i(TAG, "Image is correctly downloaded");

                                //retrouver le bitmap téléchargé par Picasso
                                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                                //demande à la palette de générer ses coleurs, de façon asynchrone
                                //afin de ne pas bloquer l'interface graphique
                                new Palette.Builder(bitmap).generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {

                                        //lorsque la palette est générée, je l'utilise sur mes textViews
                                        appliquerPalette(palette);
                                    }
                                });
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                                e.printStackTrace();
                            }
                        });
    }


    public void appliquerPalette(Palette palette) {

        {
            //je récupère le swatch Vibrant

            Palette.Swatch vibrant = palette.getVibrantSwatch();
            if (vibrant != null) { //il se peut que la palette ne génère pas tous les swatch

                //j'utilise getRgb() en tant que couleurs de fond te ma textView
                textVibrant.setBackgroundColor(vibrant.getRgb());

                //getBodyTextColor() est prévu pour être affiché dessus une vue en background getRgb()
                textVibrant.setTextColor(vibrant.getBodyTextColor());
            }
        }
        {
            Palette.Swatch vibrantDark = palette.getDarkVibrantSwatch();
            if (vibrantDark != null) {
                textVibrantDark.setBackgroundColor(vibrantDark.getRgb());
                textVibrantDark.setTextColor(vibrantDark.getBodyTextColor());
            }
        }
        {
            Palette.Swatch vibrantLight = palette.getLightVibrantSwatch();
            if (vibrantLight != null) {
                textVibrantLight.setBackgroundColor(vibrantLight.getRgb());
                textVibrantLight.setTextColor(vibrantLight.getBodyTextColor());
            }
        }

        {
            Palette.Swatch muted = palette.getMutedSwatch();
            if (muted != null) {
                textMuted.setBackgroundColor(muted.getRgb());
                textMuted.setTextColor(muted.getBodyTextColor());
            }
        }
        {
            Palette.Swatch mutedDark = palette.getDarkMutedSwatch();
            if (mutedDark != null) {
                textMutedDark.setBackgroundColor(mutedDark.getRgb());
                textMutedDark.setTextColor(mutedDark.getBodyTextColor());
            }
        }
        {
            Palette.Swatch lightMuted = palette.getLightMutedSwatch();
            if (lightMuted != null) {
                textMutedLight.setBackgroundColor(lightMuted.getRgb());
                textMutedLight.setTextColor(lightMuted.getBodyTextColor());
            }
        }
    }

}
