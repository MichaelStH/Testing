package com.riders.testing.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.riders.testing.R;
import com.squareup.picasso.Picasso;


/**
 * Created by cpu on 09/12/2015.
 */
public class ContactListDetailActivity extends AppCompatActivity {

    private static final String TAG = ContactListDetailActivity.class.getSimpleName();

    private Context mContext = null;

    private CollapsingToolbarLayout mCollapsingToolbar = null;
    private ImageView mImageDetailToolbar;
    private TextView mNameTextView, mSurnameTextView, mEmailTextView;

    private String CONTACT_NAME = "contact_name";
    private String CONTACT_SURNAME = "contact_surname";
    private String CONTACT_EMAIL = "contact_email";
    private String CONTACT_IMAGE = "contact_image";

    String itemNameDetail, itemSurnameDetail, itemEmailDetail, itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list_detail);

        retrieveBundleData();
        initViews();

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), String.valueOf(R.drawable.image5));
        supportPostponeEnterTransition();

        //setSupportActionBar((Toolbar) findViewById(R.id.contact_detail_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbar.setTitle(itemNameDetail);
        mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        setTexts();

    }

    /**
     * Retrieve bundle data
     */
    private void retrieveBundleData() {
        itemNameDetail = getIntent().getStringExtra(CONTACT_NAME);
        itemSurnameDetail = getIntent().getStringExtra(CONTACT_SURNAME);
        itemEmailDetail = getIntent().getStringExtra(CONTACT_EMAIL);
        itemImage = getIntent().getStringExtra(CONTACT_IMAGE);
    }

    /**
     * Initialize views
     */
    private void initViews() {

        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.contact_detail_collapsing_toolbar);

        mNameTextView = (TextView) findViewById(R.id.tv_name_detail);
        mSurnameTextView = (TextView) findViewById(R.id.tv_surname_detail);
        mEmailTextView = (TextView) findViewById(R.id.tv_email_detail);

        mImageDetailToolbar = (ImageView) findViewById(R.id.iv_image_detail_toolbar);

    }

    /**
     * Set Text in UI elements
     */
    private void setTexts() {
        mNameTextView.setText(itemNameDetail);
        mSurnameTextView.setText(itemSurnameDetail);
        mEmailTextView.setText(itemEmailDetail);

        Picasso.with(this)
                .load(itemImage)
                .into(mImageDetailToolbar);
    }
}
