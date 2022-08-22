package com.hhh.screenshotgallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.hhh.screenshotgallery.adapter.PhotoAdapter;
import com.hhh.screenshotgallery.adapter.PhotoTagAdapter;
import com.hhh.screenshotgallery.api.NetworkClient;
import com.hhh.screenshotgallery.api.PhotoApi;
import com.hhh.screenshotgallery.model.Photo;
import com.hhh.screenshotgallery.model.PhotoList;
import com.hhh.screenshotgallery.model.PhotoTag;
import com.hhh.screenshotgallery.model.PhotoTagList;
import com.hhh.screenshotgallery.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    // 리스트를 페이징 처리하는데 필요한 변수들
    RecyclerView recyclerView;
    int offset = 0;
    int limit = 25;
    int cnt;
    List<PhotoTag> photoTagList = new ArrayList<>();

    PhotoTagAdapter adapter;

    ProgressBar progressBar;

    String accessToken;
    private ProgressDialog progressDialog;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("ScreenshotGallery", "MainActivity");

        // 억세스 토큰이 있는지 확인, (쉐어드 프리퍼런스에)
        SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME, MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");
        Log.i("Main_point1", accessToken);
        if(accessToken.isEmpty()){
            // 로그인 액티비티를 띄운다.
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            // 메인액티비티에서 네트워크를 통해서, 내 메모 리스트를 서버로부터 가져온다.

            progressBar = findViewById(R.id.progressBar);

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            // todo addOnScrollListener

            // 네트워크를 통해서, 메모 데이터를 가져온다.
            getNetworkData();

        }
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhotoAddActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }





    // 처음에만 실행할 함수.
    private void getNetworkData() {
        offset = 0;
        cnt = 0;
        photoTagList.clear();

//        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
        PhotoApi api = retrofit.create(PhotoApi.class);

        Call<PhotoTagList> call = api.getPhotoTagList("Bearer " + accessToken,
                offset, limit);

        call.enqueue(new Callback<PhotoTagList>() {
            @Override
            public void onResponse(Call<PhotoTagList> call, Response<PhotoTagList> response) {
                if (response.isSuccessful()){
                    // 어댑터 만들어서, 리사이클러뷰에 붙여준다.
                    // 그러면 화면에, 리스트가 표시된다.
                    Log.i("여기 지나가니", "???????????????????????????????");
                    photoTagList = response.body().getResult();
                    Log.i("Main_getNetwork", ""+photoTagList.size());

                    adapter = new PhotoTagAdapter(MainActivity.this, photoTagList);
                    // 어댑터를 새로 만드는 코드 바로 아래에, 클릭 이벤트를 여기에 작성
                    adapter.setOnItemClickListener(new PhotoTagAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int index) {
                            PhotoTag photoTag = photoTagList.get(index);
                            // 정보를 통으로 photoActivity에 넘겨주기
                            Intent i = new Intent(MainActivity.this, PhotoActivity.class);
                            i.putExtra("photoTag", photoTag);
                            startActivityForResult(i, 2);
                        }

                        @Override
                        public void onDeleteClick(int index) {
                            position = index;
                            showAlertDialog();
                        }
                    }); // 어뎁터 클릭 이벤트 끝


                    recyclerView.setAdapter(adapter);

                    // 스크롤 처리를 위한 변수 값 셋팅
                    cnt = response.body().getCount();
                    offset = offset + cnt;

                }else{
                    // 응답이 성공적이지 않다면
                    // 로그인이 풀린 상태이므로, 억세스토큰이 유효하지 않다
                    // 따라서 로그인 화면을 띄운다.
                    if(response.code() == 500){
                        Intent intent = new Intent(MainActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } //response.isSuccessful() end
            }

            @Override
            public void onFailure(Call<PhotoTagList> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
            }
        }); //call.enqueue 끝

    } //getNetworkData END




    // 우리가 만든 함수. 화면에 네트워크 처리중이라고 표시할 것.
    private void showProgress(String message){
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    private void dismissProgress(){
        progressDialog.dismiss();
    }


    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("정말 삭제하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // todo 네트워크를 통해서, api 호출하여 삭제를 수행한다.
//                        deleteMemo();
                    }
                }).setNegativeButton("아니오", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("MyMemoApp", ""+requestCode+" "+resultCode);
        if(requestCode == 1 && resultCode == 3) {
            // 메모가 생성되어서, 다시 메인으로 돌아온경우.
            getNetworkData();
        } else if (requestCode == 2 && resultCode == 4){
            // 메모가 업데이트 된 경우, 다시 메인으로 돌아왔으므로,
            getNetworkData();
        }
    }


}