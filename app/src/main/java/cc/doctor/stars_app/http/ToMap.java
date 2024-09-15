package cc.doctor.stars_app.http;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ToMap {
    default Map<String, Object> getFieldValues() {
        Class<?> valueClass = this.getClass();
        List<Field> fields = new ArrayList<>(Arrays.asList(valueClass.getDeclaredFields()));
        Class<?> superClass = valueClass.getSuperclass();
        while (superClass != null && superClass != Objects.class) {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }

        Map<String, Object> fieldValues = new HashMap<>();
        fields.forEach(field -> {
            field.setAccessible(true);
            try {
                Object v = field.get(this);
                if (v != null) {
                    fieldValues.put(field.getName(), v);
                }
            } catch (IllegalAccessException e) {
                // ignore
            }
        });
        return fieldValues;
    }
}
