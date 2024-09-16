package cc.doctor.stars_app.http.user;

public class RsResponse {
    private Integer id;
    private Integer rsType;
    private Integer rsMimeType;
    private String rsUri;
    private Aweme aweme;
    private Author author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRsType() {
        return rsType;
    }

    public void setRsType(Integer rsType) {
        this.rsType = rsType;
    }

    public Integer getRsMimeType() {
        return rsMimeType;
    }

    public void setRsMimeType(Integer rsMimeType) {
        this.rsMimeType = rsMimeType;
    }

    public String getRsUri() {
        return rsUri;
    }

    public void setRsUri(String rsUri) {
        this.rsUri = rsUri;
    }

    public Aweme getAweme() {
        return aweme;
    }

    public void setAweme(Aweme aweme) {
        this.aweme = aweme;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public static class Aweme {
        private Integer authorId;
        private String awAuthorNickname;
        private String awAuthorSecId;
        private String awTitle;
        private String awCreateTime;
        private String awCoverUrl;

        public Integer getAuthorId() {
            return authorId;
        }

        public void setAuthorId(Integer authorId) {
            this.authorId = authorId;
        }

        public String getAwAuthorNickname() {
            return awAuthorNickname;
        }

        public void setAwAuthorNickname(String awAuthorNickname) {
            this.awAuthorNickname = awAuthorNickname;
        }

        public String getAwAuthorSecId() {
            return awAuthorSecId;
        }

        public void setAwAuthorSecId(String awAuthorSecId) {
            this.awAuthorSecId = awAuthorSecId;
        }

        public String getAwTitle() {
            return awTitle;
        }

        public void setAwTitle(String awTitle) {
            this.awTitle = awTitle;
        }

        public String getAwCreateTime() {
            return awCreateTime;
        }

        public void setAwCreateTime(String awCreateTime) {
            this.awCreateTime = awCreateTime;
        }

        public String getAwCoverUrl() {
            return awCoverUrl;
        }

        public void setAwCoverUrl(String awCoverUrl) {
            this.awCoverUrl = awCoverUrl;
        }
    }

    public static class Author {
        private Integer id;
        private String nickname;
        private String avatarUrl;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
