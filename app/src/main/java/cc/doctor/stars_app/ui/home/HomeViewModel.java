package cc.doctor.stars_app.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.RsDetailResponse;
import retrofit2.Call;

public class HomeViewModel extends ViewModel {

    public void recommend(String token, MutableLiveData<Response<List<RsDetailResponse>>> data) {
        Call<Response<List<RsDetailResponse>>> call = RetrofitFactory.resourceApi.recommend(token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(data));
    }

    public void follow(String token, MutableLiveData<Response<List<RsDetailResponse>>> data) {
        Call<Response<List<RsDetailResponse>>> call = RetrofitFactory.resourceApi.follow(token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(data));
    }
}