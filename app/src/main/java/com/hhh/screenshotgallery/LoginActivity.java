package com.hhh.screenshotgallery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hhh.screenshotgallery.api.NetworkClient;
import com.hhh.screenshotgallery.api.UserApi;
import com.hhh.screenshotgallery.model.PhotoRes;
import com.hhh.screenshotgallery.model.UserReq;
import com.hhh.screenshotgallery.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPasswd;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView txtSignUp = findViewById(R.id.txtSignUp);
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 액티비티를 띄어준다.
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editEmail = findViewById(R.id.editEmail);
        editPasswd = findViewById(R.id.editPasswd);

        Button btnDone = findViewById(R.id.btnDone);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 이메일과 비번을 가져온다
                String email = editEmail.getText().toString().trim();
                String password = editPasswd.getText().toString().trim();
                // 2. 이메일이 정상적인지 확인한다.
                if(email.isEmpty()){
                    Toast.makeText(LoginActivity.this, "이메일이 올바르지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 3. 비밀번호 갯수가 정상적인지 확인한다.
                if(password.isEmpty() || password.length() < 4 ||
                        password.length() > 12){
                    Toast.makeText(LoginActivity.this, "비밀번호 길이가 맞지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                showProgress("로그인 중입니다...");
                // 4. 네트워크 통해서 API 호출한다.
                Retrofit retrofit = NetworkClient.getRetrofitClient(LoginActivity.this);
                UserApi api = retrofit.create(UserApi.class);

                // 바디에 데이터 담아서 보낸다.
                UserReq userReq = new UserReq(email, password);
                Call<PhotoRes> call = api.userLogin(userReq);
                call.enqueue(new Callback<PhotoRes>() {
                    @Override
                    public void onResponse(Call<PhotoRes> call, Response<PhotoRes> response) {
                        Log.i("LoginActivity", ""+response.code());

                        dismissProgress();

                        // 5. 호출 결과를 보고, 처리해줄 코드를 작성한다.
                        if(response.isSuccessful()){
                            // 1. accessToken을 저장하고,
                            SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME, MODE_PRIVATE);

                            String accessToken = response.body().getResult();

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("accessToken", accessToken);
                            editor.apply();

                            // 2. 메인액티비티를 띄운다.
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{

                        }

                    }

                    @Override
                    public void onFailure(Call<PhotoRes> call, Throwable t) {
                        dismissProgress();
                    }
                });


            }
        });

    }

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
}