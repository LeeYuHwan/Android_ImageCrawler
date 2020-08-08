package com.example.imagecrawlerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class imgListView extends AppCompatActivity {
    private ListView mList;
    private String[] data;
    private imgAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_list_view);

        mList = (ListView) findViewById(R.id.list);
        Intent intent = getIntent();
        data = intent.getStringArrayExtra("data");

        final imgData mData[] = new imgData[data.length];
        for(int i = 0 ; i < data.length; i++){
            mData[i] = new imgData(data[i]);
        }
        mAdapter = new imgAdapter(this, mData);
        mList.setAdapter(mAdapter);

    }

    public void exit(View v){
        finish();
    }
}