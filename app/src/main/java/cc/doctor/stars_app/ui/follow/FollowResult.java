package cc.doctor.stars_app.ui.follow;

import java.util.ArrayList;
import java.util.List;

public class FollowResult {
    private String avatar;
    private String name;
    private String description;

    public FollowResult(String avatar, String name, String description) {
        this.avatar = avatar;
        this.name = name;
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<FollowResult> results() {
        List<FollowResult> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(new FollowResult("", "name" + i, "description........................." + i));
        }
        return results;
    }
}
