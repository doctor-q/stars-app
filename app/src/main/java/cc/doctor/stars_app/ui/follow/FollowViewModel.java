package cc.doctor.stars_app.ui.follow;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cc.doctor.stars_app.enums.YesNo;
import cc.doctor.stars_app.http.PageRequest;
import cc.doctor.stars_app.http.PageResponse;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.AuthorFollowRequest;
import cc.doctor.stars_app.http.user.AuthorFollowResponse;
import cc.doctor.stars_app.state.LoginState;
import retrofit2.Call;

public class FollowViewModel extends ViewModel {
    private MutableLiveData<PageResponse<AuthorFollowResponse>> authorFollowResponse = new MutableLiveData<>();
    private MutableLiveData<Response<Integer>> followResponse = new MutableLiveData<>();

    public MutableLiveData<PageResponse<AuthorFollowResponse>> getAuthorFollowResponse() {
        return authorFollowResponse;
    }

    public MutableLiveData<Response<Integer>> getFollowResponse() {
        return followResponse;
    }

    public void searchAuthor(String keywords, String token) {
        PageRequest<String> request = PageRequest.pageRequest(1, 10);
        request.setData(keywords);
        Call<PageResponse<AuthorFollowResponse>> call = RetrofitFactory.userApi.searchAuthor(request.getFieldValues(), token);
        call.enqueue(new RetrofitFactory.PageResponseCallback<>(authorFollowResponse));
    }

    public void follow(Integer authorId, Integer followStatus, String token) {
        Call<Response<Integer>> call = RetrofitFactory.userApi.follow(new AuthorFollowRequest(authorId, followStatus), token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(followResponse));
    }
}
