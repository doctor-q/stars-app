package cc.doctor.stars_app.http.user;


import cc.doctor.stars_app.http.PageResponse;

public class AuthorDetailResponse extends AuthorFollowResponse {
    private PageResponse<RsResponse> rsPage;
    private PageResponse<UserInfo> userPage;

    public PageResponse<RsResponse> getRsPage() {
        return rsPage;
    }

    public void setRsPage(PageResponse<RsResponse> rsPage) {
        this.rsPage = rsPage;
    }

    public PageResponse<UserInfo> getUserPage() {
        return userPage;
    }

    public void setUserPage(PageResponse<UserInfo> userPage) {
        this.userPage = userPage;
    }
}
