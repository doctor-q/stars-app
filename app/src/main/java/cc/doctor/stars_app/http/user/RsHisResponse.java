package cc.doctor.stars_app.http.user;

import java.time.LocalDateTime;

public class RsHisResponse extends RsResponse {
    private LocalDateTime viewTime;

    public LocalDateTime getViewTime() {
        return viewTime;
    }

    public void setViewTime(LocalDateTime viewTime) {
        this.viewTime = viewTime;
    }
}
