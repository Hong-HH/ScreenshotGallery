package com.hhh.screenshotgallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hhh.screenshotgallery.adapter.TagAdapter;
import com.hhh.screenshotgallery.model.Photo;
import com.hhh.screenshotgallery.model.PhotoTag;
import com.hhh.screenshotgallery.model.Tag;
import com.hhh.screenshotgallery.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    TextView detailTitle;
    TextView detailContent;
    ImageView detailPhoto;
    RecyclerView recyclerView;
    TagAdapter adapter;
    List<Tag> tagList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        detailTitle = findViewById(R.id.title_detail);
        detailContent = findViewById(R.id.content_detail);
        detailPhoto = findViewById(R.id.photo_detail);

        // 메인액티비티로부터 넘겨받은 데이터를 가져온다.
        PhotoTag photoTag = (PhotoTag)getIntent().getSerializableExtra("photoTag");

        // 연결
        Glide.with(this).load(Utils.IMAGE_URL+photoTag.getPhoto_url())
                .into(detailPhoto);

        detailTitle.setText(photoTag.getTitle());
        detailContent.setText(photoTag.getContent());


        // 태그
        // https://stackoverflow.com/questions/3240331/horizontal-listview-in-android
        recyclerView = findViewById(R.id.tagRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(PhotoActivity.this));


        tagList = photoTag.getTag();
        Log.i("photoActivity", "tag 갯수 : "+ tagList.size());
//        List<String> tagNameList =  new ArrayList<>(tagList.size());
//        Integer i = 0;
//        for ( Tag tag1 : tagList ){
//            String word = tag1.getTag();
//            tagNameList.add(word);
//        }


//        adapter = new TagAdapter(PhotoActivity.this, tagNameList);
        adapter = new TagAdapter(PhotoActivity.this, tagList);

        adapter.setOnItemClickListener(new TagAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                Tag tag = tagList.get(index);
                // todo 눌렀을시 검색화면으로 넘어가게
                Toast.makeText(PhotoActivity.this, ""+tag.getTag()+"클릭하셨습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int index) {

            }
        });

        recyclerView.setAdapter(adapter);


    }
}