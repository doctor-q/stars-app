package cc.doctor.stars_app.ui.author;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.AuthorDetailResponse;
import retrofit2.Call;

public class AuthorViewModel extends ViewModel {
    private MutableLiveData<Response<AuthorDetailResponse>> authorDetailResponse = new MutableLiveData<>();

    public MutableLiveData<Response<AuthorDetailResponse>> getAuthorDetailResponse() {
        return authorDetailResponse;
    }

    public void getAuthorDetail(Integer authorId, String token) {
        Call<Response<AuthorDetailResponse>> call = RetrofitFactory.userApi.getAuthorDetail(authorId, token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(authorDetailResponse));
    }
}