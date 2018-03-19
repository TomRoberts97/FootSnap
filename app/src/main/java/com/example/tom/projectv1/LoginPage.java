package com.example.tom.projectv1;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tom.projectv1.User;
import com.example.tom.projectv1.DatabaseHelper;
//USE the working openCV app for help !!
//
// help with layout for camera ect
public class LoginPage extends AppCompatActivity {

    private final AppCompatActivity activity = LoginPage.this;

    private DatabaseHelper databaseHelper;
    private User user;

    private static EditText usernameET;
    private static EditText passwordET;
    private static Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // removes the top action bar for view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_page);
        LoginButtonPress();
        initObjects();
       // usernameET.setGravity(Gravity.CENTER_HORIZONTAL);
        //passwordET.setGravity(Gravity.CENTER_HORIZONTAL);
        //passwordET.setGravity(Gravity.CENTER_VERTICAL);
       // usernameET.setText("USERNAME");
        // CURRENT USERS ARE "User001" "password" AND "User002" "password2"

        user.setId(2);
        user.setUserName("User001");
        user.setPassword("pass");
        databaseHelper.addUser(user);

    }


    public void LoginButtonPress(){
        usernameET = (EditText)findViewById(R.id.editTextUsername);
        passwordET = (EditText)findViewById(R.id.editTextPassword);
        loginButton = (Button)findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* //USE A LIST OR SOMETHING TO STORE MORE USERNAMES AND PASSWORD THEN USE CHECKER TO ALLOW LOGIN
                        if (username.getText().toString().equals("user") && password.getText().toString().equals("pass"))
                        {
                            Toast.makeText(LoginPage.this,"Username and password is correct", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent("com.example.tom.projectv1.MenuActivity");
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginPage.this,"Username and password is NOT correct", Toast.LENGTH_SHORT).show();
                        }*/

                        String name = usernameET.getText().toString();
                        String password = passwordET.getText().toString();


                        int id = checkUser(new User(name,password));
                        if(id==-1)
                        {
                            Toast.makeText(LoginPage.this,"User Does Not Exist",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(LoginPage.this,"User Exist "+name,Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginPage.this,"Username and password is correct", Toast.LENGTH_SHORT).show();
                            user.setUserName(name);

                            User.getInstance().setUserName(name);

                            Intent intent = new Intent("com.example.tom.projectv1.MenuActivity");
                            startActivity(intent);
                        }



                    }
                }
        );
    } // END of LoginButton

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {

        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }

    public int checkUser(User user)
    {
        return databaseHelper.checkUser(user);
    }

}
