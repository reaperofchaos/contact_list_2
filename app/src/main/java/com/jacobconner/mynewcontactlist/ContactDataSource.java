package com.jacobconner.mynewcontactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

public class ContactDataSource {
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public ContactDataSource(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public boolean insertContact(Contact contact) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("name", contact.getName());
            initialValues.put("address", contact.getAddress());
            initialValues.put("city", contact.getCity());
            initialValues.put("state", contact.getState());
            initialValues.put("zipcode", contact.getZipCode());
            initialValues.put("email", contact.getEmail());
            initialValues.put("phonenumber", contact.getHomePhone());
            initialValues.put("cell", contact.getCellPhone());


            didSucceed = database.insert("contact", null, initialValues) > 0;

        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateContact(Contact contact) {
        boolean didSucceed = false;
        try {
            long rowid = (long) contact.getId();
            ContentValues updateValues = new ContentValues();

            updateValues.put("name", contact.getName());
            updateValues.put("address", contact.getAddress());
            updateValues.put("city", contact.getCity());
            updateValues.put("state", contact.getState());
            updateValues.put("zipcode", contact.getZipCode());
            updateValues.put("email", contact.getEmail());
            updateValues.put("phonenumber", contact.getHomePhone());
            updateValues.put("cell", contact.getCellPhone());

            didSucceed = database.update("contact", updateValues, "_id=" + rowid, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            String query = "SELECT  * FROM contact";
            Cursor cursor = database.rawQuery(query, null);

            Contact newContact;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newContact = new Contact();
                newContact.setId(cursor.getInt(0));
                newContact.setName(cursor.getString(1));
                newContact.setAddress(cursor.getString(2));
                newContact.setCity(cursor.getString(3));
                newContact.setState(cursor.getString(4));
                newContact.setZipCode(cursor.getInt(5));
                newContact.setHomePhone(cursor.getString(6));
                newContact.setCellPhone(cursor.getString(7));
                newContact.setEmail(cursor.getString(8));
                contacts.add(newContact);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            contacts = new ArrayList<>();
        }
        return contacts;
    }

    public Contact getContactById(String id) {
        Contact newContact = new Contact();
        try {
            String query = "SELECT  * FROM contact WHERE _id = " + id;
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newContact = new Contact();
                newContact.setId(cursor.getInt(0));
                newContact.setName(cursor.getString(1));
                newContact.setAddress(cursor.getString(2));
                newContact.setCity(cursor.getString(3));
                newContact.setState(cursor.getString(4));
                newContact.setZipCode(cursor.getInt(5));
                newContact.setHomePhone(cursor.getString(6));
                newContact.setCellPhone(cursor.getString(7));
                newContact.setEmail(cursor.getString(8));
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            newContact = new Contact();
        }
        return newContact;
    }

    public boolean deleteContact(int id) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("contact", "_id=" + id, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }

}
