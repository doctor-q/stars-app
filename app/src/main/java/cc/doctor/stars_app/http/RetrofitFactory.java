package cc.doctor.stars_app.http;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    public static StarsApi starsApi;

    static {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.31.155:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        starsApi = retrofit.create(StarsApi.class);
    }

    public static class ResponseCallback<T> implements Callback<Response<T>> {

        private MutableLiveData<Response<T>> response;

        public ResponseCallback(MutableLiveData<Response<T>> response) {
            this.response = response;
        }

        @Override
        public void onResponse(Call<Response<T>> call, retrofit2.Response<Response<T>> response) {
            this.response.setValue(response.body());
        }

        @Override
        public void onFailure(Call<Response<T>> call, Throwable t) {
            Log.e(call.request().method(), t.getMessage(), t);
            this.response.setValue(Response.fail(Response.CALL_FAIL, "请求失败"));
        }
    }
}
