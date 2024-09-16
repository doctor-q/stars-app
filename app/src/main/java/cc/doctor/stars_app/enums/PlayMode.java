package cc.doctor.stars_app.enums;

public enum PlayMode {
    LOOP(0, "循环播放"),
    ROLL(1, "滚动播放");

    private final int mode;
    private final String name;

    PlayMode(int mode, String name) {
        this.mode = mode;
        this.name = name;
    }
}
