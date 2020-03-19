package com.riders.testing.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.riders.testing.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by cpu on 05/01/2016.
 */
public class ScheduleJobActivity extends AppCompatActivity {

    @BindView(R.id.button_start_schedule_job)
    Button mButtonScheduleJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_job);

        ButterKnife.bind(this);

        mButtonScheduleJob.setText("Error, Activity not working yet !");
    }
}
