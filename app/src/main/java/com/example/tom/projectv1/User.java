package com.example.tom.projectv1;

import android.app.Application;

/**
 * Created by Tom on 26/02/2018.
 */

public class User {
    //private variables
    private int id;
    private String userName;
    private String password;

    public User(){


    }
    public User(String userName){
        this.userName = userName;

    }
    public User( String userName, String password){
        this.userName = userName;
        this.password = password;

    }
    // Empty constructor
    /*public User(){

    }
    // constructor
    public User( int id , String userName, String password){
        this.userName = userName;
        this.password = password;

    }

    // constructor
   /* public User(String name, String _phone_number){
        this._name = name;
        this._phone_number = _phone_number;
    }*/


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUserName(){
        return this.userName;
    }


    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getPassword(){
        return this.password;
    }


    public void setPassword(String password){
        this.password = password;
    }

    private int myVar = 0;
    private static User instance;

    static {
        instance = new User();
    }



    public static User getInstance() {
        return User.instance;
    }

}
