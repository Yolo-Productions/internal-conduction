package br.com.yolo.core.resolver.method;

import br.com.yolo.core.resolver.Resolver;

import java.lang.reflect.Method;

public final class MethodResolver extends Resolver<Method> {

    private final Class<?> mainClass;
    private final String name;
    private final Class<?>[] parameterTypes;

    public MethodResolver(Class<?> mainClass, String name, Class<?>... parameterTypes) {
        this.mainClass = mainClass;
        this.name = name;
        this.parameterTypes = parameterTypes;
    }

    @Override
    public Method resolve() {
        Method found = null;
        Class<?> search = this.mainClass;
        try {
            found = search.getDeclaredMethod(name, parameterTypes);
            found.setAccessible(true);
        } catch (Exception err) {
            while ((search = search.getSuperclass()) != null && found == null) {
                try {
                    (found = search.getDeclaredMethod(name, parameterTypes)).setAccessible(true);
                } catch (Exception err2) {
                }
            }
        }
        return found;
    }
}
