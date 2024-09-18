package cc.doctor.stars_app.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cc.doctor.stars_app.http.PageRequest;
import cc.doctor.stars_app.http.PageResponse;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.SearchHisResponse;
import retrofit2.Call;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<PageResponse<SearchHisResponse>> searchHisResponse = new MutableLiveData<>();
    private MutableLiveData<List<SearchHisResponse>> searchHis = new MutableLiveData<>();
    private MutableLiveData<Response<List<String>>> searchSuggest = new MutableLiveData<>();

    public MutableLiveData<PageResponse<SearchHisResponse>> getSearchHisResponse() {
        return searchHisResponse;
    }

    public MutableLiveData<List<SearchHisResponse>> getSearchHis() {
        return searchHis;
    }

    public MutableLiveData<Response<List<String>>> getSearchSuggest() {
        return searchSuggest;
    }

    public void searchHis(String token) {
        PageRequest<?> request = PageRequest.pageRequest(1, 10);
        Call<PageResponse<SearchHisResponse>> call = RetrofitFactory.searchApi.searchHis(request.getFieldValues(), token);
        call.enqueue(new RetrofitFactory.PageResponseCallback<>(searchHisResponse));
    }

    public void searchSuggest(String keywords, String token) {
        Call<Response<List<String>>> call = RetrofitFactory.searchApi.searchSuggest(keywords, token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(searchSuggest));
    }
}