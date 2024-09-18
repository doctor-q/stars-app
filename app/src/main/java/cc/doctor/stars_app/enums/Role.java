package cc.doctor.stars_app.enums;

public enum Role {
    FATHER(0, "父亲"),
    MOTHER(1, "母亲"),
    GRANDPA(2, "爷爷"),
    GRANDMA(3, "奶奶"),
    GRANDFATHER(4, "外公"),
    GRANDMOTHER(5, "外婆"),
    ;

    private final int role;
    private final String name;

    Role(int role, String name) {
        this.role = role;
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public static int getRolePosition(int position) {
        Role[] values = values();
        if (position >= values.length || position < 0) {
            return -1;
        }
        return values[position].role;
    }

    public static String getName(Integer role) {
        if (role == null) {
            return null;
        }
        for (Role value : values()) {
            if (value.role == role) {
                return value.name;
            }
        }
        return null;
    }
}
