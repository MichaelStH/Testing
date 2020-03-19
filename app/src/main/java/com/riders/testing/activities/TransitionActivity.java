package com.riders.testing.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.riders.testing.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TransitionActivity extends AppCompatActivity {

    @BindView(R.id.transition_imageView)
    ImageView transitionImageView;

    @BindView(R.id.transition_button)
    Button transitionButton;

    private View.OnClickListener transitionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(TransitionActivity.this, TransitionDetailActivity.class);

            // Check if we're running on Android 5.0 or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Apply activity transition
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(TransitionActivity.this,
                                transitionImageView,
                                ViewCompat.getTransitionName(transitionImageView));
                startActivity(intent, options.toBundle());
            } else {
                // Swap without transition
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        ButterKnife.bind(this);

        Glide.with(this)
                .load(R.drawable.lion)
                .into(transitionImageView);

        supportPostponeEnterTransition();
        transitionImageView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        transitionImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                        supportStartPostponedEnterTransition();
                        return true;
                    }
                }
        );

        transitionButton.setOnClickListener(transitionListener);

    }
}
