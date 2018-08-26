package com.example.fouad.picturetaking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class pic_reveiver extends AppCompatActivity {

    ImageView img ;
    ImageView backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_reveiver);
        backbutton=(ImageView) findViewById(R.id.backclick);
        img=(ImageView)findViewById(R.id.imageView);
        byte[] data;
        data= getIntent().getByteArrayExtra("data");

        Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);

        ImageView img = (ImageView)findViewById(R.id.imageView);
        img.setImageBitmap(rotate(realImage,90));



        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(pic_reveiver.this ,MainActivity.class);
                startActivity(i);
            }
        });


    }
    // Rotate camera with int degree  from left to right
    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        //       mtx.postRotate(degree);
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
}
