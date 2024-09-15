package cc.doctor.stars_app.http.retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Converter;
import retrofit2.Retrofit;

public class QueryObjectConverterFactory extends Converter.Factory {

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (!has(annotations)) {
            return super.stringConverter(type, annotations, retrofit);
        }
        return new Converter<Object, String>() {
            @Override
            public String convert(Object value) throws IOException {
                Map<String, Object> fieldValues = getFieldValues(value);
                return fieldValues.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
            }
        };
    }

    private boolean has(final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof QueryObject) {
                return true;
            }
        }
        return false;
    }

    private Map<String, Object> getFieldValues(Object value) {
        Class<?> valueClass = value.getClass();
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
                Object v = field.get(value);
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
