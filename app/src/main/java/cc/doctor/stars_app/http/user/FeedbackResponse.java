package cc.doctor.stars_app.http.user;

import java.util.List;

public class FeedbackResponse {
    private String feedback;
    private List<String> urls;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
