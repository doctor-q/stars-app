package cc.doctor.stars_app.http.user;

import java.time.LocalDateTime;

public class RsCollectResponse extends RsResponse {
    private LocalDateTime collectTime;

    public LocalDateTime getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(LocalDateTime collectTime) {
        this.collectTime = collectTime;
    }
}
