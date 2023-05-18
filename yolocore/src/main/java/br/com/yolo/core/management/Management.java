package br.com.yolo.core.management;

import net.md_5.bungee.api.chat.BaseComponent;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public interface Management {
    Logger getLogger();
    File getFile();

    <T> T getPlayer(UUID uniqueId, Class<T> clazz);
    <T> T getPlayer(String name, Class<T> clazz);

    void sendMessage(UUID uniqueId, String message);
    void sendMessage(UUID uniqueId, String... message);
    void sendMessage(UUID uniqueId, BaseComponent message);
    void sendMessage(UUID uniqueId, BaseComponent... message);

    void runAsync(Runnable runnable);
    void runLater(Runnable runnable, long delay, TimeUnit format);
}