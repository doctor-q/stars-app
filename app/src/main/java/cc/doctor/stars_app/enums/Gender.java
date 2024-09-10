package cc.doctor.stars_app.enums;

public enum Gender {
    MALE(0, "男"),
    FEMALE(1, "女");

    private final int gender;
    private final String name;

    Gender(int gender, String name) {
        this.gender = gender;
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public static int getGenderPosition(int position) {
        Gender[] values = values();
        if (position >= values.length || position < 0) {
            return -1;
        }
        return values[position].gender;
    }
}
