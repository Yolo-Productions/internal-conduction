package br.com.yolo.spigot;

import br.com.yolo.core.Client;
import br.com.yolo.core.account.Account;
import br.com.yolo.core.account.datahandler.DataHandler;
import br.com.yolo.core.account.setting.SettingTag;
import br.com.yolo.core.management.Management;
import br.com.yolo.core.storage.module.AccountModule;
import br.com.yolo.spigot.adapter.LocationAdapter;
import br.com.yolo.spigot.command.BukkitCommandFramework;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class Core extends JavaPlugin implements Management {

    @Getter private static boolean correctlyStarted = false;
    private static boolean allowingPlayers = false;

    private BukkitCommandFramework commandFramework;

    public Core() {
        /**
         * "main" gson
         */
        Client.getJsonModule().registerGson("main", new GsonBuilder()
                .registerTypeAdapter(Location.class, new LocationAdapter())
                // register adapters
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC));
        /**
         * "pretty" gson
         */
        Client.getJsonModule().registerGson("pretty", new GsonBuilder()
                .registerTypeAdapter(Location.class, new LocationAdapter())
                // register adapters
                        .setPrettyPrinting()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC));
    }

    @Override
    public void onLoad() {
        // CORE LOAD
        if (startup()) {
            correctlyStarted = true;
            getLogger().info("Server started! Waiting for enablement...");
        } else {
            getLogger().info("Server not correctly started!");
        }
    }

    @Override
    public void onEnable() {
        if (correctlyStarted) {
            // CORE ENABLE

            commandFramework = new BukkitCommandFramework(this);
            commandFramework.registerAll("br.com.yolo.spigot.command.registry");

            if (enable()) {
                allowingPlayers = true;
                getLogger().info("Server enabled! Players may now join.");
            } else {
                getLogger().info("Server not correctly enabled! Not allowing players for bug preventions.");
            }
        } else {
            getLogger().info("Server not correctly started! Enablement process ignored.");
        }
    }

    @Override
    public void onDisable() {
        // CORE DISABLE
        if (stop()) {
            getLogger().info("Server disablement successful!");
        } else {
            getLogger().info("Server not correctly disabled! Check logs for more detailed info.");
        }
    }

    @Override
    public File getFile() {
        return super.getFile();
    }

    @Override
    public boolean isAllowingPlayers() {
        return allowingPlayers;
    }

    @Override
    public AccountModule getAccountModule() {
        return Client.getAccountModule();
    }

    @Override
    public String getNameOf(UUID uuid) {
        return null;
    }

    @Override
    public UUID getUUIDOf(String name) {
        return null;
    }

    @Override
    public void runTaskLater(Runnable task, long delay, TimeUnit timeUnit) {

    }

    @Override
    public void runTaskAsync(Runnable task) {

    }

    @Override
    public void sendAlertMessage(String message, String permission) {
        getServer().getOnlinePlayers().stream()
                .filter(p -> p.hasPermission(permission))
                .filter(p -> {
                    Account account = getAccountModule().read(p.getUniqueId());
                    DataHandler dataHandler = account.getDataHandler();
                    return dataHandler.getSetting(SettingTag.ALERT_MESSAGES)
                            .getAsBoolean();
                }).forEach(p -> p.sendMessage(message));
    }

    @Override
    public <T> T getPlayer(String name, Class<T> clazz) {
        return null;
    }
}
