package com.example.tom.projectv1;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraCaptureSession;
import android.util.AttributeSet;

import org.opencv.android.JavaCameraView;

import java.util.List;

import java.io.FileOutputStream;
import java.util.List;

import org.opencv.android.JavaCameraView;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
/**
 * Created by Tom on 10/02/2018.
 */
// THINK I NEED TO UPDATE TO CAMERA2 CALLBACK INSTEAD OF        THIS             MIGHT BE CAMERACAPTURESESSION.CAPUTURECALLBACK
public class myCameraView extends JavaCameraView implements PictureCallback {
// THIS IMPLEMENTS MAY BE ABLE TO CAPTURE IMAGES USING public Mat onCameraFrame(CvCameraViewFrame cameraviewframe)
//public class myCameraView extends JavaCameraView implements CvCameraViewListener2 {

//public class myCameraView extends JavaCameraView implements CameraCaptureSession.CaptureCallback {

    private static final String TAG = "TAG :: myCameraView";
    private String mPictureFileName;

    public myCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void takePicture(final String fileName) {
        Log.i(TAG, "Taking picture");
        this.mPictureFileName = fileName;
        // Postview and jpeg are sent in the same buffers if the queue is not empty when performing a capture.
        // Clear up buffers to avoid mCamera.takePicture to be stuck because of a memory issue
        mCamera.setPreviewCallback(null);

        // PictureCallback is implemented by the current class
        mCamera.takePicture(null, null, this);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.i(TAG, "Saving a bitmap to file");
        // The camera preview was automatically stopped. Start it again.
        mCamera.startPreview();
        mCamera.setPreviewCallback(this);

        // Write the image in a file (in jpeg format)
        try {
            FileOutputStream fos = new FileOutputStream(mPictureFileName);

            fos.write(data);
            fos.close();

        } catch (java.io.IOException e) {
            Log.e("PictureDemo", "Exception in photoCallback", e);
        }

    }

}
