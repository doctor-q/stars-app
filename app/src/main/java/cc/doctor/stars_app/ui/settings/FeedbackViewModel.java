package cc.doctor.stars_app.ui.settings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cc.doctor.stars_app.http.Response;
import cc.doctor.stars_app.http.RetrofitFactory;
import cc.doctor.stars_app.http.user.FeedbackResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class FeedbackViewModel extends ViewModel {
    private MutableLiveData<Response<String>> response = new MutableLiveData<>();
    private MutableLiveData<Response<List<FeedbackResponse>>> feedbackResponse = new MutableLiveData<>();

    public MutableLiveData<Response<String>> getResponse() {
        return response;
    }

    public MutableLiveData<Response<List<FeedbackResponse>>> getFeedbackResponse() {
        return feedbackResponse;
    }

    public void feedback(RequestBody feedback, List<MultipartBody.Part> parts, String token) {
        Call<Response<String>> call = RetrofitFactory.userApi.feedback(feedback, parts, token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(response));
    }

    public void getFeedback(String token) {
        Call<Response<List<FeedbackResponse>>> call = RetrofitFactory.userApi.getFeedback(token);
        call.enqueue(new RetrofitFactory.ResponseCallback<>(feedbackResponse));
    }
}
