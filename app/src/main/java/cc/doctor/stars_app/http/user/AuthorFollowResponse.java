package cc.doctor.stars_app.http.user;

import java.time.LocalDateTime;

import cc.doctor.stars_app.enums.YesNo;

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
        return followStatus == null ? YesNo.NO.getValue() : followStatus;
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
