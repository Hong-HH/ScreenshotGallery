package com.hhh.screenshotgallery.api;

import com.hhh.screenshotgallery.model.PhotoList;
import com.hhh.screenshotgallery.model.PhotoReq;
import com.hhh.screenshotgallery.model.PhotoRes;
import com.hhh.screenshotgallery.model.PhotoTagList;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PhotoApi {
    // 메모 생성 API
    @Multipart
    @POST("/v1/photo")
    Call<PhotoRes> addPhoto(@Header("Authorization") String accessToken,
                          @Part MultipartBody.Part photo,
                          @PartMap Map<String, RequestBody> params);

    // 내 메모 리스트 가져오는 API
    @GET("/v1/photo")
    Call<PhotoList> getPhotoList(@Header("Authorization") String accessToken,
                                @Query("offset") int offset,
                                @Query("limit") int limit);

    // 메모 업데이트 API
    @PUT("/v1/photo/{photoID}")
    Call<PhotoRes> updatePhoto(@Header("Authorization") String accessToken,
                              @Path("photoID") int photoId,
                              @Body PhotoReq photoReq);

    // 메모 삭제 API
    @DELETE("/v1/photo/{photoID}")
    Call<PhotoRes> deletePhoto(@Header("Authorization") String accessToken,
                             @Path("photoID") int photoId);

    // 내 메모 리스트 가져오는 API
    @GET("/v1/photoTag")
    Call<PhotoTagList> getPhotoTagList(@Header("Authorization") String accessToken,
                                       @Query("offset") int offset,
                                       @Query("limit") int limit);
}
