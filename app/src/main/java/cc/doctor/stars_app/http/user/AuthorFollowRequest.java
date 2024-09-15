package cc.doctor.stars_app.http.user;

public class AuthorFollowRequest {
    private Integer authorId;
    private Integer follow;

    public AuthorFollowRequest(Integer authorId, Integer follow) {
        this.authorId = authorId;
        this.follow = follow;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }
}
