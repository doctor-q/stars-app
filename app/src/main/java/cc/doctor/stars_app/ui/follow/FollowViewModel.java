package cc.doctor.stars_app.ui.follow;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cc.doctor.stars_app.http.PageRequest;
import cc.doctor.stars_app.http.PageResponse;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.AuthorFollowResponse;
import retrofit2.Call;

public class FollowViewModel extends ViewModel {
    private MutableLiveData<PageResponse<AuthorFollowResponse>> authorFollowResponse = new MutableLiveData<>();

    public MutableLiveData<PageResponse<AuthorFollowResponse>> getAuthorFollowResponse() {
        return authorFollowResponse;
    }

    public void searchAuthor(String keywords, String token) {
        PageRequest<String> request = PageRequest.pageRequest(1, 10);
        request.setData(keywords);
        Call<PageResponse<AuthorFollowResponse>> call = RetrofitFactory.userApi.searchAuthor(request.getFieldValues(), token);
        call.enqueue(new RetrofitFactory.PageResponseCallback<>(authorFollowResponse));
    }
}
