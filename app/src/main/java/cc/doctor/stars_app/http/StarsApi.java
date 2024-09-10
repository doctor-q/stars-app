package cc.doctor.stars_app.http;

import cc.doctor.stars_app.ui.login.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface StarsApi {

    @GET("ping")
    Call<Response<String>> ping();

    @POST("login")
    Call<Response<LoginResponse>> login(@Body LoginRequest request);

    @POST("register/email/verify")
    Call<Response<String>> emailVerify(@Body EmailRequest request);

    @POST("register")
    Call<Response<String>> register(@Body RegisterRequest request);
}
