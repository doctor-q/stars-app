package cc.doctor.stars_app.ui.settings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.UserInfo;
import okhttp3.MultipartBody;
import retrofit2.Call;

public class SettingsUserViewModel extends ViewModel {

    private MutableLiveData<Response<UserInfo>> userInfoResponse = new MutableLiveData<>();
    private MutableLiveData<Response<String>> updateUserResponse = new MutableLiveData<>();
    private MutableLiveData<Response<Integer>> uploadResponse = new MutableLiveData<>();

    public MutableLiveData<Response<UserInfo>> getUserInfoResponse() {
        return userInfoResponse;
    }

    public MutableLiveData<Response<String>> getUpdateUserResponse() {
        return updateUserResponse;
    }

    public MutableLiveData<Response<Integer>> getUploadResponse() {
        return uploadResponse;
    }

    public void getUserInfo(String token) {
        Call<Response<UserInfo>> call = RetrofitFactory.userApi.getUser(token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(userInfoResponse));
    }

    public void upload(MultipartBody.Part part, String token) {
        Call<Response<Integer>> call = RetrofitFactory.userApi.upload(part,  token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(uploadResponse));
    }

    public void updateUserInfo(UserInfo userInfo, String token) {
        Call<Response<String>> call = RetrofitFactory.userApi.updateUser(userInfo,  token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(updateUserResponse));
    }
}