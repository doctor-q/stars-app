package cc.doctor.stars_app.http.user;

public class RsCollectRequest {

    private Integer resId;
    private Integer collect;

    public RsCollectRequest(Integer resId, Integer collect) {
        this.resId = resId;
        this.collect = collect;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }
}
