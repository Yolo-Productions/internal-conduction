package br.com.yolo.core.account;

import br.com.yolo.core.resolver.method.MethodResolver;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class AccountMap extends ConcurrentHashMap<UUID, Account> {

    @Getter private static final AccountMap instance = new AccountMap();

    private Method getUniqueId;

    @Override
    public Account get(Object key) {
        if (key instanceof UUID)
            return super.get(key);
        try {
            return super.get(getOrCreateResolver(key).invoke(key));
        } catch (Exception ignored) {
        }
        return null;
    }

    private Method getOrCreateResolver(Object paramPlayer) {
        // This method will ever have the same reference for this player type class
        if (getUniqueId == null)
            getUniqueId = new MethodResolver(paramPlayer.getClass(), "getUniqueId")
                    .resolve();
        return getUniqueId;
    }
}
