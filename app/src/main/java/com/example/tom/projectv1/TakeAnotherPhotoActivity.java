package com.example.tom.projectv1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeAnotherPhotoActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    private static final String TAG = "TAG::Activity";
    private CameraBridgeViewBase mOpenCvCameraView;
    private double transVal;
    private int value = 123;
    Mat mRgba;
    Mat mRgbaF;
    Mat mRgbaT;
    Mat imgCanny;

    private static ImageView DisplayImageIV;
    private static ImageButton TakeNewPhotoButton;
    private static  ImageButton TransUpButton;
    private static  ImageButton TransDownButton;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                  //  mOpenCvCameraView.setOnTouchListener(TakeAnotherPhotoActivity.this);
                    loadGhostImage();
                    TakeNewPhotoButtonPress();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public TakeAnotherPhotoActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_take_another_photo);


        TransUpButtonPress();
        TransDownButtonPress();

        mOpenCvCameraView = (JavaCameraView) findViewById(R.id.show_camera_activity_java_surface_view);
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

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        // TODO Auto-generated method stub
        mRgba = inputFrame.rgba();

        // Rotate mRgba 90 degrees
       /* Core.transpose(mRgba, mRgbaT);
        Imgproc.resize(mRgbaT, mRgbaF, mRgbaF.size(), 0,0, 0);
        Core.flip(mRgbaF, mRgba, 1 );*/

        // converts camera to canny ( not need for actual app)
        Imgproc.Canny( mRgba, imgCanny,  190, 200, 3, true);

        //return mRgba; // returns normal portrait camera
       // return imgCanny; // returns canny portrait camera

        return mRgba;
    }

    // COULD ADD A COUNTER TO TRACK HOW MANY PHOTOS A PERSON HAS TAKEN
    // NEED TO WORK OUT HOW TO HAVE A PERSON CLASS/ACCOUNT WHICH SAVES WHEN THE APP IS CLOSED?
    @SuppressLint("SimpleDateFormat")
    /*@Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG,"onTouch event");


        File direct = new File(Environment.getExternalStorageDirectory() + "/ProjectV1");

        if (!direct.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getPath() + "/ProjectV1/");
            wallpaperDirectory.mkdirs();
        }

        // IF A USER/PATIENT CLASS IS CREATED THE DETAILS FROM THIS.CLASSSTUFF COLD BE
        // INSERTED INTO THE SAVE ADDRESS FOR THE IMAGES SO THEY CAN BE READ BACK TO THE APP EASIER

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateandTime = sdf.format(new Date());
        String fileName = Environment.getExternalStorageDirectory().getPath() +
                "/ProjectV1/ANOTHERphoto_user1_" + currentDateandTime + ".jpg";
        Toast.makeText(this, fileName + " saved", Toast.LENGTH_SHORT).show();


        Imgcodecs.imwrite(fileName,mRgba);
        Log.i(TAG,"imwrite peformed" + fileName);
        return false;

    }*/



    //Mat m = Imgcodecs.imread("/media/path_to_image");
    public void loadGhostImage(){
        DisplayImageIV = (ImageView) findViewById(R.id.imageView2);
        // only postion for my phone will need to be changed for other phones (WILL NEED TO USE PERCENTAGES OF SCREEN WIDTH AND HEIGTH)
       // DisplayImageIV.setX(168);
        //DisplayImageIV.setY(-124);
        //DisplayImageIV.setX(168);
        //DisplayImageIV.setY(-124);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) DisplayImageIV.getLayoutParams();

        //DisplayImageIV.setY(-124);
        params.width = 2500;
        params.height  = 2000;
// existing height is ok as is, no need to edit it
        DisplayImageIV.setLayoutParams(params);

        String userName = User.getInstance().getUserName();
        Log.i(TAG,"LoadGhostImage method STARTED!");
        /*String FileNameGhost = Environment.getExternalStorageDirectory().getPath() +
                "/ProjectV1/GHOST_image" + ".jpg";*/


        String FileNameGhost = Environment.getExternalStorageDirectory().getPath() +
                "/FootSnap" + userName + "/GHOST_image" + ".jpg";

        Log.i(TAG,"FILE NAME TO BE LOADED : " + FileNameGhost);

        Mat m = Imgcodecs.imread(FileNameGhost);

        if( m.empty() )
        {
            Toast.makeText(this,  "GHOST IMAGE NOT FOUND!", Toast.LENGTH_SHORT).show();
            Log.i(TAG,"MAT IS EMPTY");
        }else{
            Toast.makeText(this,  "GHOST IMAGE FOUND!", Toast.LENGTH_SHORT).show();
            Log.i(TAG,"MAT NOT EMPTY");

            imgCanny = new Mat(m.width(), m.width(), CvType.CV_8UC1);
            //Imgproc.Canny( m, imgCanny,  190, 200, 3, true);
            Imgproc.Canny( m, imgCanny,  50, 100);

            Bitmap bm = Bitmap.createBitmap(m.cols(), m.rows(),Bitmap.Config.ARGB_8888);
            //Utils.matToBitmap(m, bm);
            Utils.matToBitmap(imgCanny, bm);
            DisplayImageIV.setImageBitmap(bm);
            DisplayImageIV.setImageAlpha(value);
        }



    }

    public void TakeNewPhotoButtonPress(){
        TakeNewPhotoButton = (ImageButton)findViewById(R.id.takePhotoButton);
        TakeNewPhotoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String userName = User.getInstance().getUserName();

                        File direct = new File(Environment.getExternalStorageDirectory() + "/FootSnap" + userName);

                        if (!direct.exists()) {
                            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getPath() + "/FootSnap" + userName +"/");
                            wallpaperDirectory.mkdirs();
                        }
                        /*File direct = new File(Environment.getExternalStorageDirectory() + "/ProjectV1");

                        if (!direct.exists()) {
                            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory().getPath() + "/ProjectV1/");
                            wallpaperDirectory.mkdirs();
                        }*/

                        // IF A USER/PATIENT CLASS IS CREATED THE DETAILS FROM THIS.CLASSSTUFF COLD BE
                        // INSERTED INTO THE SAVE ADDRESS FOR THE IMAGES SO THEY CAN BE READ BACK TO THE APP EASIER

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                        String currentDateandTime = sdf.format(new Date());
                        /*String fileName = Environment.getExternalStorageDirectory().getPath() +
                                "/ProjectV1/ANOTHERphoto_user1_" + currentDateandTime + ".jpg";*/

                        String fileName = Environment.getExternalStorageDirectory().getPath() +
                                "/FootSnap" + userName + "/photo_" + currentDateandTime + ".jpg";


                        Toast.makeText(TakeAnotherPhotoActivity.this, fileName + " saved", Toast.LENGTH_SHORT).show();


                        Imgcodecs.imwrite(fileName,mRgba);
                        Log.i(TAG,"imwrite peformed" + fileName);
                       // return false;




                    }
                }
        );
    } // END of LogOutButton

    public void TransUpButtonPress(){
        TransUpButton = (ImageButton)findViewById(R.id.transUpButton);

        TransUpButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                if(value < 240) {


                    value = value + 10;
                    // between 0 and 255
                    DisplayImageIV.setImageAlpha(value);
                }

                    }
                }
        );
    } // END of LogOutButton
    public void TransDownButtonPress(){
        TransDownButton = (ImageButton)findViewById(R.id.transDownButton);

        TransDownButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(value > 10) {


                            value = value - 10;
                            // between 0 and 255
                            DisplayImageIV.setImageAlpha(value);
                        }

                    }
                }
        );
    } // END of LogOutButton

}
