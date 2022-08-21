package com.hhh.screenshotgallery.api;

import com.hhh.screenshotgallery.model.PhotoRes;
import com.hhh.screenshotgallery.model.UserReq;
import com.hhh.screenshotgallery.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    // 로그인 API
    @POST("/v1/user/login")
    Call<PhotoRes> userLogin(@Body UserReq userReq);

    // 회원가입 API
    @POST("/v1/user/register")
    Call<PhotoRes> userSignUp(@Body UserReq userReq);
}
