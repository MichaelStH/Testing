package com.riders.testing.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riders.testing.R;
import com.riders.testing.adapters.ContactAdapter;
import com.riders.testing.model.ContactInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Michaël on 31/01/2018.
 */

public class ContactListActivity extends AppCompatActivity /*implements RecyclerView.OnItemTouchListener*/ {

    private static final String TAG = ContactListActivity.class.getSimpleName();

    private static MainActivity MAIN_LAUNCH = new MainActivity();

    private Context mContext = null;

    private RecyclerView mContactsRecyclerView = null;
    private LinearLayoutManager mLinearLayoutManager = null;

    private List<ContactInfo> mListOfContact = new ArrayList<>();
    private ContactInfo mContactInfo = null;
    private ContactAdapter mContactAdapter = null;

    private String CONTACT_NAME = "contact_name";
    private String CONTACT_SURNAME = "contact_surname";
    private String CONTACT_EMAIL = "contact_email";
    private String CONTACT_IMAGE = "contact_image";

    private String[] urls_thumb = {
            "http://a5.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY5NjUxODQzNTUw.jpg",
            "http://a4.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY4NTc4MzEwMTU0.jpg",
            "http://a5.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY4MDQxMjI5NTg2.jpg",
            "http://a2.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY3MjM2MTg2NTkw.jpg",
            "http://a2.files.theultralinx.com/image/upload/c_fit,cs_srgb,w_620/MTI5MDI0MjY2NDMwNjgyMzg2.png",
            "http://a3.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY1NjI1MzIzNTMw.jpg",
            "http://a3.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY0ODIwMDA0MTE0.jpg",
            "http://a2.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY0MDE0NjcwNDY3.jpg",
            "http://a3.files.theultralinx.com/image/upload/c_fit,cs_srgb,w_620/MTI5MDI0MjYzMjA5MzkyNjA2.png",
            "http://a3.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjYyNDA0MjM5MzMw.jpg"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        mContext = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(TAG);

        mContactsRecyclerView = (RecyclerView) findViewById(R.id.rv_contact_list);
        mContactsRecyclerView.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mContactsRecyclerView.setLayoutManager(mLinearLayoutManager);

        mContactAdapter = new ContactAdapter(createList(30));

        mContactAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

                Log.d("OHOHO", "" + position);

                String name = ((TextView) itemView.findViewById(R.id.txtName)).getText().toString();
                String surname = ((TextView) itemView.findViewById(R.id.txtSurname)).getText().toString();
                String email = ((TextView) itemView.findViewById(R.id.txtEmail)).getText().toString();

                int idx = new Random().nextInt(urls_thumb.length);
                String randomImg = (urls_thumb[idx]);


                Log.i(TAG, name + "\n"
                        + surname + "\n"
                        + email + "\n"
                        + randomImg + "\n");

                Intent intent = new Intent(mContext, ContactListDetailActivity.class);

                intent.putExtra(CONTACT_NAME, name);
                intent.putExtra(CONTACT_SURNAME, surname);
                intent.putExtra(CONTACT_EMAIL, email);
                intent.putExtra(CONTACT_IMAGE, randomImg);

                startActivity(intent);

            }
        }); /*{
            @Override
            public void onItemClick(View itemView, ContactInfo contactInfo) {

                Log.i(TAG, "" + position);

                mContactInfo = mContactAdapter.getContact(position);

                String name = mContactInfo.getName();
                String surname = mContactInfo.getSurname();
                String email = mContactInfo.getEmail();

                Log.i(TAG, name + "\n"
                    + surname + "\n"
                    + email + "\n");

                //MAIN_LAUNCH.launchActivity(mContext, ContactListDetailActivity.class);
            }
        });*/
        mContactsRecyclerView.setAdapter(mContactAdapter);

    }


    private List<ContactInfo> createList(int size) {

        List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i = 1; i <= size; i++) {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.name = ContactInfo.NAME_PREFIX + i;
            contactInfo.surname = ContactInfo.SURNAME_PREFIX + i;
            contactInfo.email = ContactInfo.EMAIL_PREFIX + i + "@test.com";

            result.add(contactInfo);

        }

        return result;
    }

/*
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        Log.d(TAG, "clicked");

        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, rv.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
*/
}
