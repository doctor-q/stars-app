package cc.doctor.stars_app.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.RsDetailResponse;
import retrofit2.Call;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Response<RsDetailResponse>> recommend = new MutableLiveData<>();
    private MutableLiveData<Response<RsDetailResponse>> follow = new MutableLiveData<>();

    public MutableLiveData<Response<RsDetailResponse>> getRecommend() {
        return recommend;
    }

    public MutableLiveData<Response<RsDetailResponse>> getFollow() {
        return follow;
    }

    public void recommend(String token) {
        Call<Response<RsDetailResponse>> call = RetrofitFactory.resourceApi.recommend(token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(recommend));
    }

    public void follow(String token) {
        Call<Response<RsDetailResponse>> call = RetrofitFactory.resourceApi.follow(token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(follow));
    }
}