package cc.doctor.stars_app.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cc.doctor.stars_app.http.login.EmailRequest;
import cc.doctor.stars_app.http.login.RegisterRequest;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import retrofit2.Call;
import retrofit2.Callback;

public class RegisterViewModel extends ViewModel {
    private final MutableLiveData<Response<?>> registerResponse = new MutableLiveData<>();
    private final MutableLiveData<Response<?>> emailVerifyResponse = new MutableLiveData<>();

    public void register(String email, String verifyCode, String password,
                         String nickName, Integer role,
                         Integer gender, String birth) {
        RegisterRequest request = new RegisterRequest();
        request.setEmail(email);
        request.setEmailVerifyCode(verifyCode);
        request.setPassword(password);
        request.setNickname(nickName);
        request.setRole(role);
        request.setChildGender(gender);
        request.setChildBirth(birth);
        Call<Response<String>> call = RetrofitFactory.loginApi.register(request);
        call.enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                registerResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable t) {
                registerResponse.setValue(Response.fail(Response.CALL_FAIL, "请求失败"));
            }
        });
    }

    public MutableLiveData<Response<?>> getRegisterResponse() {
        return registerResponse;
    }

    public MutableLiveData<Response<?>> getEmailVerifyResponse() {
        return emailVerifyResponse;
    }

    public void verifyEmail(String email) {
        EmailRequest request = new EmailRequest();
        request.setEmail(email);
        Call<Response<String>> call = RetrofitFactory.loginApi.emailVerify(request);
        call.enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                emailVerifyResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable t) {
                emailVerifyResponse.setValue(Response.fail(Response.CALL_FAIL, "请求失败"));
            }
        });
    }
}
