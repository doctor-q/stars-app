package cc.doctor.stars_app.ui.search.result;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.RsResponse;
import retrofit2.Call;

public class SearchResultViewModel extends ViewModel {
    private MutableLiveData<Response<List<RsResponse>>> searchRsResponse = new MutableLiveData<>();

    public MutableLiveData<Response<List<RsResponse>>> getSearchRsResponse() {
        return searchRsResponse;
    }

    public void searchRs(String keywords, String token) {
        Call<Response<List<RsResponse>>> call = RetrofitFactory.searchApi.searchRs(keywords, 15, token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(searchRsResponse));
    }
}