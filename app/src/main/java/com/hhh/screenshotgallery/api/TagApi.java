package com.hhh.screenshotgallery.api;

import com.hhh.screenshotgallery.model.PhotoReq;
import com.hhh.screenshotgallery.model.PhotoRes;
import com.hhh.screenshotgallery.model.TagReq;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TagApi {
    // 메모 업데이트 API
    @PUT("/v1/tag/{photoID}")
    Call<PhotoRes> updateMemo(@Header("Authorization") String accessToken,
                              @Path("photoID") int photoID,
                              @Body TagReq tagReq);
}
