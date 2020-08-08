package com.example.imagecrawlerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class imageView extends AppCompatActivity {
    private ImageView imgView;
    private TextView textView;
    private String img_url;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imgView = (ImageView)findViewById(R.id.show_image);
        textView = (TextView)findViewById(R.id.show_url);

        Intent getIntent = getIntent();
        img_url = getIntent.getStringExtra("img_url");

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(img_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

        try {
            mThread.join();
            imgView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            imgView.setImageResource(R.drawable.ic_launcher_foreground);
        }

        textView.setText(img_url);
    }

    public void imgViewClick(View v){
        switch (v.getId()){
            case R.id.img_exit:
                finish();
                break;
        }
    }
}