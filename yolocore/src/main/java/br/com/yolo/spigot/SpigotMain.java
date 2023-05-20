package br.com.yolo.spigot;

import br.com.yolo.core.Client;
import br.com.yolo.spigot.adapter.LocationAdapter;
import br.com.yolo.spigot.api.inventory.Inventory;
import br.com.yolo.spigot.command.impl.SpigotCommandFramework;
import br.com.yolo.spigot.event.build.ServerTimeEvent;
import br.com.yolo.spigot.listener.Listener;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public abstract class SpigotMain extends JavaPlugin {
    @Getter
    private static SpigotMain instance;
    public SpigotMain() {
        instance = this;
    }

    protected Map<UUID, Inventory> inventoriesInAction;

    @Override
    public void onLoad() {
        saveDefaultConfig();

        Client.getJsonModule().registerGson("main", new GsonBuilder()
                .registerTypeAdapter(Location.class, new LocationAdapter())
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
                .create());
        Client.initialize(new SpigotManagement());
    }

    @Override
    public void onEnable() {
        try {
            inventoriesInAction = new LinkedHashMap<>();

            new Listener(this, "br.com.yolo.spigot").sendPacket();
            new SpigotCommandFramework(this).registerAll("br.com.yolo.spigot.command.list");

            getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
                new ServerTimeEvent().call();
            }, 20L, 20L);
        } catch (Exception ex) {
            ex.printStackTrace();
            getServer().shutdown();
        }
    }

    @Override
    public void onDisable() {

    }
}
