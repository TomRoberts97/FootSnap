package com.example.tom.projectv1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceView;
import org.opencv.android.JavaCameraView;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

//public class TakeNewPhotoActivity extends AppCompatActivity implements CvCameraViewListener2, View.OnTouchListener {
public class TakeNewPhotoActivity extends AppCompatActivity implements CvCameraViewListener2 {
    // used for logging success or failure messages
    private static final String TAG = "TAG::Activity";
    //Loads camera view using OpenCV
    private CameraBridgeViewBase mOpenCvCameraView;
    private static ImageButton takePhotoButton;
   // private myCameraView mOpenCvCameraView;

    //Used in Camera selection
    //private boolean mIsJavaCamera = true;
    //private MenuItem mItemSwitchCamera = null;




    //Used to fix camera orientation
    Mat mRgba;
    Mat mRgbaF;
    Mat mRgbaT;
    Mat imgCanny;


    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    //mOpenCvCameraView.setOnTouchListener(TakeNewPhotoActivity.this);
                    TakePhotoButtonPress();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public TakeNewPhotoActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_take_new_photo);


        // button calls

        //OpenCV camera code
        //mOpenCvCameraView = (myCameraView) findViewById(R.id.show_camera_activity_java_surface_view);
        mOpenCvCameraView = (JavaCameraView) findViewById(R.id.show_camera_activity_java_surface_view2);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);




    }


    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
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

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }


    // recives image data when camera preview starts
    public void onCameraViewStarted(int width, int height) {

       mRgba = new Mat(height, width, CvType.CV_8UC4);
        mRgbaF = new Mat(height, width, CvType.CV_8UC4);
        mRgbaT = new Mat(width, width, CvType.CV_8UC4);
        imgCanny = new Mat(width, width, CvType.CV_8UC1);
    }
    //stops revicing of camera data
    public void onCameraViewStopped() {
        mRgba.release();
    }


    // used to fix the oreintation of the camera view
    //WHERE THE CANNY ALOGRITHM NEEDS TO BE IMPLEMENTED
    // CHECK THE CANNY APP
    // IT USED A DIFFERENT TYPE OF IMPLEMENTATION I THINK
    // USING A MAT , MAY BE POSSIBLE TO COMBINE NOT SURE YET
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        // TODO Auto-generated method stub
        mRgba = inputFrame.rgba();

        // Rotate mRgba 90 degrees
       /* Core.transpose(mRgba, mRgbaT);
        Imgproc.resize(mRgbaT, mRgbaF, mRgbaF.size(), 0,0, 0);
        Core.flip(mRgbaF, mRgba, 1 );*/

        // converts camera to canny ( not need for actual app)
        //Imgproc.Canny( mRgba, imgCanny,  100, 200, 3, true);
        Imgproc.Canny( mRgba, imgCanny,  50, 100);
        //return mRgba; // returns normal portrait camera
       // return imgCanny; // returns canny portrait camera

      return mRgba;
    }

    @SuppressLint("SimpleDateFormat")






    public void TakePhotoButtonPress(){
        takePhotoButton = (ImageButton)findViewById(R.id.takePhotoButton);
        takePhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       // File direct = new File(Environment.getExternalStorageDirectory() + "/ProjectV1");
                        String userName = User.getInstance().getUserName();
                        File direct = new File(Environment.getExternalStorageDirectory() + "/FootSnap" + userName);

                        if (!direct.exists()) {
                            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getPath() + "/FootSnap" + userName +"/");
                            wallpaperDirectory.mkdirs();
                        }

                      /*
                        if (!direct.exists()) {
                            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getPath() + "/ProjectV1/");
                            wallpaperDirectory.mkdirs();
                        }*/


                        // IF A USER/PATIENT CLASS IS CREATED THE DETAILS FROM THIS.CLASSSTUFF COLD BE
                        // INSERTED INTO THE SAVE ADDRESS FOR THE IMAGES SO THEY CAN BE READ BACK TO THE APP EASIER

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                        String currentDateandTime = sdf.format(new Date());
                        /*String fileName = Environment.getExternalStorageDirectory().getPath() +
                                "/ProjectV1/FIRSTphoto_user1_" + currentDateandTime + ".jpg";*/
                       // Toast.makeText(this, fileName + " saved", Toast.LENGTH_SHORT).show();

                        /*String FileNameGhost = Environment.getExternalStorageDirectory().getPath() +
                                "/ProjectV1/GHOST_image" + ".jpg";*/
                        String FileNameGhost = Environment.getExternalStorageDirectory().getPath() +
                                "/FootSnap" + userName + "/GHOST_image" + ".jpg";


                        Toast.makeText(TakeNewPhotoActivity.this, "Picture saved at " + FileNameGhost, Toast.LENGTH_SHORT).show();
                        // Imgcodecs.imwrite(fileName,mRgba);
                        //Log.i(TAG,"imwrite peformed" + fileName);
                        Imgcodecs.imwrite(FileNameGhost,mRgba);
                        Log.i(TAG,"imwrite peformed" + FileNameGhost);
                        //return false;

                        Intent intent = new Intent("com.example.tom.projectv1.MenuActivity");
                        startActivity(intent);

                    }
                }
        );
    } // END of TakePhoto Press














}
