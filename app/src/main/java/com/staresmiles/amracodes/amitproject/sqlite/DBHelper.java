package com.staresmiles.amracodes.amitproject.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amra on 4/25/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final int DATABASE_VERSION = 1;
    public static final String USERS_TABLE_NAME = "users";
    public static final String USER_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_USER_NAME = "user_name";
    public static final String CONTACTS_COLUMN_PASSWORD = "password";
    public static final String CONTACTS_COLUMN_CITY = "city";
    public static final String CONTACTS_COLUMN_MOBILE = "mobile";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table users (id integer , "+CONTACTS_COLUMN_NAME+" text, "+CONTACTS_COLUMN_MOBILE +" text, email text, user_name text primary key, password text,city text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean insertUser(String name, String mobile,
                              String email, String userName,
                              String password, String city) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        contentValues.put(CONTACTS_COLUMN_MOBILE, mobile);
        contentValues.put(CONTACTS_COLUMN_EMAIL, email);
        contentValues.put(CONTACTS_COLUMN_USER_NAME, userName);
        contentValues.put(CONTACTS_COLUMN_PASSWORD, password);
        contentValues.put(CONTACTS_COLUMN_CITY, city);
        db.insert(USERS_TABLE_NAME,
                null, contentValues);
        return true;
    }

    public Cursor getUser(String userName, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from users where user_name =?  " +
                "AND password =?"
                ,new String[] {userName, password}, null);

        return res;
    }



    public Integer deleteContact(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users",
                "user_name = ? ",
                new String[]{(userName)});
    }
}
