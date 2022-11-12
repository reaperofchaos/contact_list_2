package com.jacobconner.mynewcontactlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter{
    private ArrayList<Contact> contactList;
    private View.OnClickListener mOnItemClickListener;
    private Context parentContext;
    private boolean ableToDelete;

    public boolean canDelete(){return this.ableToDelete;}

    public void setCanDelete(boolean ableToDelete) {
        this.ableToDelete = ableToDelete;
        this.notifyDataSetChanged();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public TextView textName;
        public TextView textAddress;
        public TextView textAddress2;
        public Button deleteButton;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.txtName);
            textAddress = itemView.findViewById(R.id.txtAddress);
            textAddress2 = itemView.findViewById(R.id.txtAddress2);
            deleteButton = itemView.findViewById(R.id.btnDelete);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getName() {
            return textName;
        }
        public TextView getTextAddress() {
            return textAddress;
        }
        public TextView getTextAddress2() {
            return textAddress2;
        }
        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public ContactAdapter(ArrayList<Contact> arrayList,  Context context) {
        contactList = arrayList;
        parentContext = context;

    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ContactViewHolder rvh = (ContactViewHolder) holder;
        rvh.getName().setText(contactList.get(position).getName());
        rvh.getTextAddress().setText(contactList.get(position).getAddress());
        String city = contactList.get(position).getCity();
        String state = contactList.get(position).getState();
        Integer zipcode = contactList.get(position).getZipCode();
        rvh.getTextAddress2().setText(city + ", " + state + " " + zipcode);
        if(!canDelete()){
            rvh.getDeleteButton().setVisibility(View.GONE);
            //notifyDataSetChanged();
        }else{
            rvh.getDeleteButton().setVisibility(View.VISIBLE);
        }
        rvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deleteItem(rvh.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    private void deleteItem(int position) {
        Contact contact = contactList.get(position);
        ContactDataSource ds = new ContactDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteContact(contact.getId());
            ds.close();
            if (didDelete) {
                contactList.remove(position);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e) {
            Toast.makeText(parentContext, "Delete Failed! " + e, Toast.LENGTH_LONG).show();

        }
    }

}
