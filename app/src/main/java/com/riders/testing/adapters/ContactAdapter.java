package com.riders.testing.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.riders.testing.R;
import com.riders.testing.model.ContactInfo;

import java.util.List;


/**
 * Created by cpu on 09/12/2015.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{

    private List<ContactInfo> contactList;

    /**
     * OnClickListener
     */
    // Define listener member variable
    private static OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    /**
     *
     */


    public ContactAdapter(List<ContactInfo> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int position) {

        ContactInfo contactInfo = contactList.get(position);

        contactViewHolder.tvName.setText(contactInfo.name);
        contactViewHolder.tvSurname.setText(contactInfo.surname);
        contactViewHolder.tvEmail.setText(contactInfo.email);
        contactViewHolder.tvTitle.setText(contactInfo.name + " " + contactInfo.surname);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_contact_list, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvName;
        protected TextView tvSurname;
        protected TextView tvEmail;
        protected TextView tvTitle;

        private Context mContext;

        public ContactViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.txtName);
            tvSurname = (TextView) view.findViewById(R.id.txtSurname);
            tvEmail = (TextView) view.findViewById(R.id.txtEmail);
            tvTitle = (TextView) view.findViewById(R.id.title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(view, getLayoutPosition());
                }
            });
        }
    }
}
