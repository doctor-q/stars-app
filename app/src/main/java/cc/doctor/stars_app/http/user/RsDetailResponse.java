package cc.doctor.stars_app.http.user;

import cc.doctor.stars_app.enums.YesNo;

public class RsDetailResponse extends RsResponse {
    private Integer collectStatus;
    private String subtitle;
    private Integer followStatus;
    private AwemeDetail awemeDetail;

    public Integer getCollectStatus() {
        return collectStatus == null ? YesNo.NO.getValue() : collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getFollowStatus() {
        return followStatus == null ? YesNo.NO.getValue() : followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }

    public AwemeDetail getAwemeDetail() {
        return awemeDetail;
    }

    public void setAwemeDetail(AwemeDetail awemeDetail) {
        this.awemeDetail = awemeDetail;
    }

    public static class AwemeDetail extends RsResponse.Aweme {
        private String awemeId;
        private Integer width;
        private Integer height;
        private Integer duration;
        private String playUrl;

        public String getAwemeId() {
            return awemeId;
        }

        public void setAwemeId(String awemeId) {
            this.awemeId = awemeId;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }
    }
}
