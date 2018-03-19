package com.example.tom.projectv1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tom.projectv1.User;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Tom on 27/02/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int databaseVersion = 1;
    // Database Name
    private static final String databaseName = "UserManager1.db";
    // User table name
    private static final String tableUser = "user";
   // private static final String tableUser = "userTableTest1";
    // User Table Columns names
    private static final String columnUserID = "userID";
    private static final String columnUsername = "userUsername";
    private static final String columnPassword = "userPassword";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + tableUser + "(" + columnUserID + " INTEGER PRIMARY KEY AUTOINCREMENT,"  + columnUsername + " TEXT," + columnPassword + " TEXT" + ")";
    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + tableUser;








    public void deleteTable(SQLiteDatabase db) {
        db.execSQL("delete from UserManager.db");
        Log.i(TAG,"Table Deleted : ");
    }





    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        Log.i(TAG,"Table Created : "  + columnUserID + " " + columnUsername + " " + " " + columnPassword);
        //db.execSQL("delete from UserManager.db");
        //Log.i(TAG,"Table Deleted : ");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        // Create tables again
        onCreate(db);
    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(columnUsername, user.getUserName());
        values.put(columnPassword, user.getPassword());
        // Inserting Row
        db.insert(tableUser, null, values);
        Log.i(TAG,"User Added : " + values);
        db.close();
    }

    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                columnUserID,
                columnUsername,
                columnPassword
        };
        // sorting orders
        String sortOrder =
                columnUsername + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(tableUser, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(columnUserID))));
                user.setUserName(cursor.getString(cursor.getColumnIndex(columnUsername)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(columnPassword)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return userList;
    }





    private String rawQuery = "SELECT " + columnUserID + " FROM " + tableUser + " WHERE " + columnUsername + " = ? AND " + columnPassword + " = ?";

    public int checkUser(User us)
    {
        int id=-1;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(rawQuery,new String[]{us.getUserName(),us.getPassword()});
       // Cursor cursor=db.rawQuery("SELECT id FROM user WHERE name=? AND password=?",new String[]{us.getUserName(),us.getPassword()});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            id=cursor.getInt(0);
            cursor.close();
        }
        return id;
    }
}







