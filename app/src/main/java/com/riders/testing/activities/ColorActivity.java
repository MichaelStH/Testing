package com.riders.testing.activities;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.riders.testing.R;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class ColorActivity extends AppCompatActivity {

    private static final String TAG = ColorActivity.class.getSimpleName();

    @BindView(R.id.target_color_textView)
    TextView targetColorTextView;

    @BindView(R.id.change_color_button)
    Button changeColorButton;

    private static String baseColor = "rgb(33,214,9)";
    private static String baseColor2 = "rgb(255,156,0)";
    private static String baseColor3 = "rgb(255,0,0)";

    String colors[];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        ButterKnife.bind(this);

        colors = new String[]{baseColor, baseColor2, baseColor3};
    }

    @OnClick(R.id.change_color_button)
    public void changeColor(View view) {

        Pattern c = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Matcher m = c.matcher(colors[new Random().nextInt(colors.length)]);

        if (m.matches()) {

            int red = Integer.valueOf(m.group(1).trim());
            int green = Integer.valueOf(m.group(2).trim());
            int blue = Integer.valueOf(m.group(3).trim());


            Log.e(TAG, "int value of red : " + red + ", green : " + green + " blue : " + blue);

//            String hex = "0x" + Integer.toHexString(Color.rgb(red, green, blue)).toUpperCase().substring(2);

            int R = (red >> 16) & 0xff;
            int G = (green >> 8) & 0xff;
            int B = (blue) & 0xff;

            Log.e(TAG, "hex value of R : " + R + ", G : " + G + " B : " + B);

            int color = (R & 0xff) << 16 | (G & 0xff) << 8 | (B & 0xff);

            Log.e(TAG, "value of color variable : " + color);

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{color, 0x00000000});
            gd.setCornerRadius(0f);

            targetColorTextView.setTextColor(Color.rgb(red, green, blue));
        }

    }
}
