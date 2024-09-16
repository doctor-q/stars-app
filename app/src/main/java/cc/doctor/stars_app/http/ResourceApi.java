package cc.doctor.stars_app.http;

import cc.doctor.stars_app.http.user.RsCollectRequest;
import cc.doctor.stars_app.http.user.RsDetailResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ResourceApi {
    @POST("rs/collect")
    Call<Response<Integer>> collect(@Body RsCollectRequest request, @Header("token") String token);

    @GET("rs/recommend")
    Call<Response<RsDetailResponse>> recommend(@Header("token") String token);

    @GET("rs/follow")
    Call<Response<RsDetailResponse>> follow(@Header("token") String token);

}
