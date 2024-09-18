package cc.doctor.stars_app.http;

import java.util.List;
import java.util.Map;

import cc.doctor.stars_app.http.user.AuthorDetailResponse;
import cc.doctor.stars_app.http.user.AuthorFollowRequest;
import cc.doctor.stars_app.http.user.AuthorFollowResponse;
import cc.doctor.stars_app.http.user.FeedbackResponse;
import cc.doctor.stars_app.http.user.UserDetailResponse;
import cc.doctor.stars_app.http.user.UserInfo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface UserApi {

    @GET("user/detail")
    Call<Response<UserDetailResponse>> getUserDetail(@Header("token") String token);

    @GET("user/info")
    Call<Response<UserInfo>> getUser(@Header("token") String token);

    @Multipart
    @POST("file/upload")
    Call<Response<Integer>> upload(@Part MultipartBody.Part file, @Header("token") String token);

    @POST("user/update")
    Call<Response<String>> updateUser(@Body UserInfo request, @Header("token") String token);

    @Multipart
    @POST("feedback/submit")
    Call<Response<String>> feedback(@Part("feedback") RequestBody feedback, @Part List<MultipartBody.Part> fileList, @Header("token") String token);

    @GET("feedback/list")
    Call<Response<List<FeedbackResponse>>> getFeedback(@Header("token") String token);

    @GET("author/search/page")
    Call<PageResponse<AuthorFollowResponse>> searchAuthor(@QueryMap Map<String, Object> request, @Header("token") String token);

    @POST("author/follow")
    Call<Response<Integer>> follow(@Body AuthorFollowRequest request, @Header("token") String token);

    @GET("author/detail")
    Call<Response<AuthorDetailResponse>> getAuthorDetail(@Query("authorId") Integer authorId, @Header("token") String token);
}
