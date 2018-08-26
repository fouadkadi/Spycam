package com.example.fouad.picturetaking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.provider.MediaStore.Files.FileColumns;
import android.widget.Toast;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MainActivity extends AppCompatActivity {


    public ImageButton back ;
    public ImageButton front;

    private Camera mCamera=null;
    private CameraPreview mPreview=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Get buttons ref
        back=(ImageButton)findViewById(R.id.back);
        front=(ImageButton)findViewById(R.id.front);


        //using the preview
       /*LinearLayout preview = (LinearLayout) findViewById(R.id.frameview);
        preview.addView(mPreview);*/


        // back button click
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // Create an instance of Camera
                mCamera = getBackCameraInstance();

                // Create our Preview view and set it as the content of our activity.
                mPreview = new CameraPreview(getApplicationContext(), mCamera);





                // without the preview ( better make on the click )
                final SurfaceView vieww = new SurfaceView(getApplicationContext());
                try {
                    mCamera.setPreviewDisplay(vieww.getHolder());
                    mCamera.startPreview();
                }catch (Exception e){}


                // take the shoot
                mCamera.takePicture(null, null, mPicture);


            }
        });


        // front button click
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create an instance of Camera
                mCamera = getFrontcaminstance();

                // Create our Preview view and set it as the content of our activity.
                mPreview = new CameraPreview(getApplicationContext(), mCamera);





                // without the preview ( better make on the click )
                final SurfaceView vieww = new SurfaceView(getApplicationContext());
                try {
                    mCamera.setPreviewDisplay(vieww.getHolder());
                    mCamera.startPreview();
                }catch (Exception e){}



                mCamera.takePicture(null, null, mPicture);

            }
        });






    }



    // Do the callback to get the picture ( yeaaaaah )

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        String TAG="";
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {


            Intent intent = new Intent(MainActivity.this, pic_reveiver.class);

            intent.putExtra("data",data);
            Toast.makeText(getApplicationContext(),"been here",Toast.LENGTH_LONG).show();

            Log.e("byte",data.toString());
            startActivity(intent);


        }


    };





    // get the instance of the camere using its ID ( none is default for back camera )

    public static Camera getBackCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    // how to get the front camera :p
    private static int getFrontCameraId(){
        int camId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo ci = new Camera.CameraInfo();

        for(int i = 0;i < numberOfCameras;i++){
            Camera.getCameraInfo(i,ci);
            if(ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                camId = i;
            }
        }

        return camId;
    }


    public static Camera getFrontcaminstance(){
        Camera c = null;
        try {
            c = Camera.open(getFrontCameraId()); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

}

