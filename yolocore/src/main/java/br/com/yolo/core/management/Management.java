package br.com.yolo.core.management;

import br.com.yolo.core.storage.module.AccountModule;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public interface Management {

    Logger getLogger();

    File getFile();

    boolean startup();

    boolean enable();

    boolean stop();

    boolean isAllowingPlayers();

    AccountModule getAccountModule();

    String getNameOf(UUID uuid);

    UUID getUUIDOf(String name);

    void runTaskLater(Runnable task, long delay, TimeUnit timeUnit);

    void runTaskAsync(Runnable task);

    // sendAlertMessage by Rank

    void sendAlertMessage(String message, String permission);

    <T> T getPlayer(String name, Class<T> clazz);
}
