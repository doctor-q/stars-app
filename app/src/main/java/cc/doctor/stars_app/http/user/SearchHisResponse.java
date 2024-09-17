package cc.doctor.stars_app.http.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchHisResponse {
    private String keywords;
    private String createTime;

    public SearchHisResponse(String keywords, String createTime) {
        this.keywords = keywords;
        this.createTime = createTime;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
