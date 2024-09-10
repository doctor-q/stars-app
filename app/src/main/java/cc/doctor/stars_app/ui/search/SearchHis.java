package cc.doctor.stars_app.ui.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchHis {
    private String his;
    private Date hisTime;

    public SearchHis(String his, Date hisTime) {
        this.his = his;
        this.hisTime = hisTime;
    }

    public String getHis() {
        return his;
    }

    public void setHis(String his) {
        this.his = his;
    }

    public Date getHisTime() {
        return hisTime;
    }

    public void setHisTime(Date hisTime) {
        this.hisTime = hisTime;
    }

    public static List<SearchHis> top3() {
        return topN(3);
    }

    public static List<SearchHis> all() {
        return topN(10);
    }

    private static List<SearchHis> topN(int n) {
        List<SearchHis> his = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            his.add(new SearchHis("搜索历史" + i, new Date()));
        }
        return his;
    }
}
