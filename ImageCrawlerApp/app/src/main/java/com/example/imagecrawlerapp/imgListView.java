package com.example.imagecrawlerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        Intent getIntent = getIntent();
        data = getIntent.getStringArrayExtra("data");

        final imgData mData[] = new imgData[data.length];
        for(int i = 0 ; i < data.length; i++){
            mData[i] = new imgData(data[i]);
        }
        mAdapter = new imgAdapter(this, mData);
        mList.setAdapter(mAdapter);

        final Intent intent = new Intent(this, imageView.class);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("img_url", mData[position].icon);
                startActivityForResult(intent, 0);
            }
        });

    }

    public void exit(View v){
        finish();
    }
}