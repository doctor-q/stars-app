package cc.doctor.stars_app.ui.search.result;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private String cover;
    private String description;
    private String avatar;
    private String author;
    private String createTime;

    public SearchResult(String cover, String description, String avatar, String author, String createTime) {
        this.cover = cover;
        this.description = description;
        this.avatar = avatar;
        this.author = author;
        this.createTime = createTime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public static List<SearchResult> results() {
        List<SearchResult> results = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            results.add(new SearchResult("", "description" + i, "", "author" + i, "2020.01.01"));
        }
        return results;
    }
}
