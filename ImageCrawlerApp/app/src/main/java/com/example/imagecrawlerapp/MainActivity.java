package com.example.imagecrawlerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText mEditText;
    private EditText mCount;
    private TextView textView;
    private Button imgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText)findViewById(R.id.value);
        mCount = (EditText)findViewById(R.id.count);
        textView = (TextView)findViewById(R.id.textView);
        imgButton = (Button)findViewById(R.id.imgBtn);
    }

    public void mOnClick(View v) {
        String search_value = mEditText.getText().toString();
        final int count = Integer.parseInt(mCount.getText().toString());

        switch (v.getId()){
            case R.id.parsingBtn:

                final String[] data = new String[count];
                final String search_url = "https://search.naver.com/search.naver?where=image&sm=tab_jum&query=" + search_value;

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Document doc = Jsoup.connect(search_url).get();
                            Elements img = doc.select("div[class=photo_grid _box]").select("div[class=img_area _item]");
                            int i = 0;
                            for (Element e : img) {
                                String url = e.select("div[class=img_area _item] a img").attr("data-source");

                                if(url != "") {
                                    data[i] = url;
                                    i++;
                                }

                                if(i >= count) break;
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            String text = "";
                            @Override
                            public void run() {
                                for(int i = 0; i < count; i++){
                                    text += data[i] + "\n";

                                }
                                textView.setText(text);
                            }
                        });
                    }
                });

                thread.start();
                try {
                    thread.join();
                } catch (Exception e) {
                    // TODO: handle exception
                }

                imgButton.setVisibility(View.VISIBLE);
                break;
            case R.id.imgBtn:
                String tmp = textView.getText().toString();;
                String[] tmp_data = tmp.split("\n");

                Intent intent = new Intent(this, imgListView.class);
                intent.putExtra("data", tmp_data);
                startActivityForResult(intent, 0);
                break;
        }

    }

}