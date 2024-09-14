package cc.doctor.stars_app.ui.mine;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.UserDetailResponse;
import retrofit2.Call;

public class MineViewModel extends ViewModel {

    private MutableLiveData<Response<UserDetailResponse>> userDetailResponse = new MutableLiveData<>();

    public MutableLiveData<Response<UserDetailResponse>> getUserDetailResponse() {
        return userDetailResponse;
    }

    public void getUserDetail(String token) {
        Call<Response<UserDetailResponse>> call = RetrofitFactory.userApi.getUserDetail(token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(userDetailResponse));
    }
}