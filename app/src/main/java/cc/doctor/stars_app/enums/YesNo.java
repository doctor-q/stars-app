package cc.doctor.stars_app.enums;

public enum YesNo {

    YES(1, "是"),
    NO(0, "否");

    private final int value;
    private final String name;

    YesNo(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static YesNo reverse(YesNo yn) {
        return yn == YES ? NO : YES;
    }

    public static Integer reverse(Integer yn) {
        return yn == YES.value ? NO.value : YES.value;
    }
}
