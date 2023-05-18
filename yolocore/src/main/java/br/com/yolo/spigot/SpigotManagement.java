package br.com.yolo.spigot;

import br.com.yolo.core.Management;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SpigotManagement implements Management {
    @Override
    public Logger getLogger() {
        return SpigotMain.getInstance().getLogger();
    }

    @Override
    public File getFile() {
        return SpigotMain.getInstance().getDataFolder();
    }

    @Override
    public <T> T getPlayer(UUID uniqueId, Class<T> clazz) {
        Player player = Bukkit.getPlayer(uniqueId);

        return clazz.cast(player);
    }

    @Override
    public <T> T getPlayer(String name, Class<T> clazz) {
        Player player = Bukkit.getPlayer(name);

        return clazz.cast(player);
    }

    @Override
    public void sendMessage(UUID uniqueId, String message) {
        Player player = getPlayer(uniqueId, Player.class);
        if (player == null)
            return;

        player.sendMessage(message);
    }

    @Override
    public void sendMessage(UUID uniqueId, String... message) {
        Player player = getPlayer(uniqueId, Player.class);
        if (player == null)
            return;

        player.sendMessage(message);
    }

    @Override
    public void sendMessage(UUID uniqueId, BaseComponent message) {
        Player player = getPlayer(uniqueId, Player.class);
        if (player == null)
            return;

        player.sendMessage(BaseComponent.toLegacyText(message));
    }

    @Override
    public void sendMessage(UUID uniqueId, BaseComponent... message) {
        Player player = getPlayer(uniqueId, Player.class);
        if (player == null)
            return;

        player.sendMessage(BaseComponent.toLegacyText(message));
    }

    @Override
    public void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotMain.getInstance(), runnable);
    }

    @Override
    public void runLater(Runnable runnable, long delay, TimeUnit format) {
        Bukkit.getScheduler().runTaskLater(SpigotMain.getInstance(), runnable, format.convert(delay, format));
    }
}
