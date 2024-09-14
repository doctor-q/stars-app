package cc.doctor.stars_app.http.user;

import cc.doctor.stars_app.http.PageResponse;

public class UserDetailResponse extends UserInfo {
    private String roleName;
    private String childAge;

    private PageResponse<RsCollectResponse> rsCollectPage;
    private PageResponse<RsHisResponse> rsHisPage;
    private PageResponse<AuthorResponse> followPage;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getChildAge() {
        return childAge;
    }

    public void setChildAge(String childAge) {
        this.childAge = childAge;
    }

    public PageResponse<RsCollectResponse> getRsCollectPage() {
        return rsCollectPage;
    }

    public void setRsCollectPage(PageResponse<RsCollectResponse> rsCollectPage) {
        this.rsCollectPage = rsCollectPage;
    }

    public PageResponse<RsHisResponse> getRsHisPage() {
        return rsHisPage;
    }

    public void setRsHisPage(PageResponse<RsHisResponse> rsHisPage) {
        this.rsHisPage = rsHisPage;
    }

    public PageResponse<AuthorResponse> getFollowPage() {
        return followPage;
    }

    public void setFollowPage(PageResponse<AuthorResponse> followPage) {
        this.followPage = followPage;
    }
}
