package br.com.yolo.core.util.resolver.field;

import br.com.yolo.core.util.resolver.Resolver;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;

@RequiredArgsConstructor
public final class FieldResolver extends Resolver<Field> {

    private final Class<?> mainClass;
    private final String name;

    @Override
    public Field resolve() {
        Field found = null;
        Class<?> search = this.mainClass;
        try {
            found = search.getDeclaredField(name);
            found.setAccessible(true);
        } catch (Exception err) {
            while ((search = search.getSuperclass()) != null && found == null) {
                try {
                    (found = search.getDeclaredField(name)).setAccessible(true);
                } catch (Exception err2) {
                }
            }
        }
        return found;
    }
}
