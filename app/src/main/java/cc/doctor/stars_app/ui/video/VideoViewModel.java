package cc.doctor.stars_app.ui.video;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cc.doctor.stars_app.enums.YesNo;
import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.RsCollectRequest;
import cc.doctor.stars_app.http.user.RsDetailResponse;
import retrofit2.Call;

public class VideoViewModel extends ViewModel {
    private MutableLiveData<Response<List<RsDetailResponse>>> rsDetailList = new MutableLiveData<>();
    private MutableLiveData<Integer> process = new MutableLiveData<>();
    private MutableLiveData<RsDetailResponse> current = new MutableLiveData<>();
    public MutableLiveData<Response<Integer>> collectResponse = new MutableLiveData<>();

    public MutableLiveData<Response<List<RsDetailResponse>>> getRsDetailList() {
        return rsDetailList;
    }

    public MutableLiveData<Integer> getProcess() {
        return process;
    }

    public MutableLiveData<RsDetailResponse> getCurrent() {
        return current;
    }

    public MutableLiveData<Response<Integer>> getCollectResponse() {
        return collectResponse;
    }

    public void collect(String token) {
        RsDetailResponse rsDetail = current.getValue();
        Call<Response<Integer>> call = RetrofitFactory.resourceApi.collect(new RsCollectRequest(rsDetail.getId(), YesNo.reverse(rsDetail.getCollectStatus())),
                token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(collectResponse));
    }
}
