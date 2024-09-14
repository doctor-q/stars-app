package cc.doctor.stars_app.http.user;

import java.time.LocalDateTime;

public class AuthorFollowResponse extends AuthorResponse {
    private Integer userId;
    private LocalDateTime followTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getFollowTime() {
        return followTime;
    }

    public void setFollowTime(LocalDateTime followTime) {
        this.followTime = followTime;
    }
}
