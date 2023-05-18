package br.com.yolo.core.util.resolver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ReflectionUtils {

    public static Field setAccessible(Field field) throws ReflectiveOperationException {
        field.setAccessible(true);
        int modifiers = field.getModifiers();
        if (Modifier.isFinal(modifiers)) {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
        }
        return field;
    }

    public static Method setAccessible(Method method) {
        method.setAccessible(true);
        return method;
    }
}

