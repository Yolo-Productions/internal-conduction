package br.com.yolo.spigot;

import br.com.yolo.core.Client;
import br.com.yolo.spigot.api.inventory.Inventory;
import br.com.yolo.spigot.command.impl.SpigotCommandFramework;
import br.com.yolo.spigot.listener.Listener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

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

        Client.initialize(new SpigotManagement());
    }

    @Override
    public void onEnable() {
        try {
            inventoriesInAction = new LinkedHashMap<>();

            new Listener(this, "br.com.yolo.spigot").sendPacket();
            new SpigotCommandFramework(this).registerAll("br.com.yolo.spigot.command.list");
        } catch (Exception ex) {
            ex.printStackTrace();
            getServer().shutdown();
        }
    }

    @Override
    public void onDisable() {

    }
}
