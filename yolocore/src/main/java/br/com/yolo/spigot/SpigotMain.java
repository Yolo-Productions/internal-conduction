package br.com.yolo.spigot;

import br.com.yolo.core.Client;
import br.com.yolo.spigot.listener.Listener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SpigotMain extends JavaPlugin {
    @Getter
    private static SpigotMain plugin;

    @Override
    public void onLoad() {
        plugin = this;
        saveDefaultConfig();

        Client.initialize(new SpigotManagement());
    }

    @Override
    public void onEnable() {
        try {
            new Listener(this, "br.com.yolo.spigot").sendPacket();
        } catch (Exception ex) {
            ex.printStackTrace();
            getServer().shutdown();
        }
    }

    @Override
    public void onDisable() {

    }
}
