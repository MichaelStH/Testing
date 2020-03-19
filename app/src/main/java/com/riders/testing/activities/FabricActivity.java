package com.riders.testing.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.riders.testing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FabricActivity extends AppCompatActivity {

    @BindView(R.id.trigger_button)
    Button triggerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_fabric);

        ButterKnife.bind(this);

        // TODO: Move this to where you establish a user session
        logUser();

        // TODO: Use your own attributes to track content views in your app
        /*Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Tweet")
                .putContentType("Video")
                .putContentId("1234")
                .putCustomAttribute("Favorites Count", 20)
                .putCustomAttribute("Screen Orientation", "Landscape"));*/

    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }

    // TODO: Move this method and use your own event name to track your key metrics
    public void onKeyMetric(View view) {
        // TODO: Use your own string attributes to track common values over time
        // TODO: Use your own number attributes to track median value over time
        /*Answers.getInstance().logCustom(new CustomEvent("Video Played")
                .putCustomAttribute("Category", "Comedy")
                .putCustomAttribute("Length", 350));*/
    }


    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        /*Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");*/
    }

}
