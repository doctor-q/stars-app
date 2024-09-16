package cc.doctor.stars_app.ui.video;

import cc.doctor.stars_app.http.user.RsDetailResponse;

public class VideoState {
    private Integer process;
    // 0-推荐，1-关注
    private Integer loadType;
    private RsDetailResponse rsDetail;

    public VideoState(RsDetailResponse rsDetail) {
        this.rsDetail = rsDetail;
        this.process = 0;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public Integer getLoadType() {
        return loadType;
    }

    public void setLoadType(Integer loadType) {
        this.loadType = loadType;
    }

    public RsDetailResponse getRsDetail() {
        return rsDetail;
    }

    public void setRsDetail(RsDetailResponse rsDetail) {
        this.rsDetail = rsDetail;
    }
}
