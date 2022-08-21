package com.hhh.screenshotgallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hhh.screenshotgallery.model.Photo;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        // 메인액티비티로부터 넘겨받은 데이터를 가져온다.
        Photo photo = (Photo)getIntent().getSerializableExtra("photo");
    }
}