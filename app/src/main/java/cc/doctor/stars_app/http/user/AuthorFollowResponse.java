package cc.doctor.stars_app.http.user;

import java.time.LocalDateTime;

public class AuthorFollowResponse extends AuthorResponse {
    private Integer userId;
    private Integer followStatus;
    private String followTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }
}
