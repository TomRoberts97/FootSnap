package com.example.tom.projectv1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class MenuActivity extends AppCompatActivity {

    private static Button logOutButton;
    private static Button TakeNewPhotoButton;
    private static Button TakeAnotherPhotoButton;
    private static TextView userNameTextView;




    private static final String TAG = "TAG::Activity";

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    TakeNewPhotoButtonPress();
                    TakeAnotherPhotoButtonPress();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    public MenuActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        TakeNewPhotoButtonPress();
        TakeAnotherPhotoButtonPress();
        LogOutButtonPress();

        String s = User.getInstance().getUserName();
        userNameTextView = (TextView) findViewById(R.id.userNameTVw);
        userNameTextView.setText(s);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    // NEED TO ADD A ARE YOU SURE? THING
    // GOOGLE IT (SEE IF IT CAN BE DONE WITH SOMETHING LIKE A TOAST)
    public void LogOutButtonPress(){
        logOutButton = (Button)findViewById(R.id.buttonLogOut);
        logOutButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        //set up for a dialog box not sure with                  THIS BIT
                        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                        builder.setMessage("Are you sure?");

                        builder.setNegativeButton("No", dialogLogOutClickListener);
                        builder.setPositiveButton("Yes", dialogLogOutClickListener);

                        builder.show();


/*
                        Toast.makeText(MenuActivity.this,"Log Out Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MenuActivity.this, LoginPage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);*/
                    }
                }
        );
    } // END of LogOutButton

    public void TakeNewPhotoButtonPress(){
        TakeNewPhotoButton = (Button)findViewById(R.id.buttonTakeNewPhotoPage);
        TakeNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        String userName = User.getInstance().getUserName();
                        Log.i(TAG,"LoadGhostImage method STARTED!");
                        String FileNameGhost = Environment.getExternalStorageDirectory().getPath() +
                                "/FootSnap" + userName + "/GHOST_image" + ".jpg";

                        Log.i(TAG,"FILE NAME TO BE LOADED : " + FileNameGhost);
                        Mat m = Imgcodecs.imread(FileNameGhost);
                        if( m.empty() )
                        {







                            Toast.makeText(MenuActivity.this,  "No Ghost image found", Toast.LENGTH_SHORT).show();
                            Log.i(TAG,"MAT IS EMPTY");
                            Intent intent = new Intent("com.example.tom.projectv1.TakeNewPhotoActivity");
                            startActivity(intent);
                        }else{

                            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                            builder.setMessage("Are you sure you want to retake your ghost image?");

                            builder.setNegativeButton("No", dialogGhostImageButtonClickListener);
                            builder.setPositiveButton("Yes", dialogGhostImageButtonClickListener);

                            builder.show();

                        }

                       // Intent intent = new Intent("com.example.tom.projectv1.TakeNewPhotoActivity");
                        //startActivity(intent);

                    }
                }
        );
    } // END of TakeNewPhotoButton

    public void TakeAnotherPhotoButtonPress(){
        TakeAnotherPhotoButton = (Button)findViewById(R.id.buttonTakeAnotherPhoto);
        TakeAnotherPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String userName = User.getInstance().getUserName();
                        Log.i(TAG,"LoadGhostImage method STARTED!");
                        String FileNameGhost = Environment.getExternalStorageDirectory().getPath() +
                                "/FootSnap" + userName + "/GHOST_image" + ".jpg";

                        Log.i(TAG,"FILE NAME TO BE LOADED : " + FileNameGhost);
                        Mat m = Imgcodecs.imread(FileNameGhost);
                        if( m.empty() )
                        {
                            Toast.makeText(MenuActivity.this,  "No Ghost image found", Toast.LENGTH_SHORT).show();

                        }else{
                            //Toast.makeText(MenuActivity.this,  "First Photo already taken!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MenuActivity.this,  "Ghost image found", Toast.LENGTH_SHORT).show();
                            Log.i(TAG,"Ghost Image Found!");
                            Intent intent = new Intent("com.example.tom.projectv1.TakeAnotherPhotoActivity");
                            startActivity(intent);
                        }

                       // Intent intent = new Intent("com.example.tom.projectv1.TakeAnotherPhotoActivity");
                        //startActivity(intent);

                    }
                }
        );
    } // END of TakeNewPhotoButton




    // used to build yes no dialog boxes
    DialogInterface.OnClickListener dialogLogOutClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    Toast.makeText(MenuActivity.this,"Log Out Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MenuActivity.this, LoginPage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    DialogInterface.OnClickListener dialogGhostImageButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    Intent intent = new Intent("com.example.tom.projectv1.TakeNewPhotoActivity");
                    startActivity(intent);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };



} // END of main
