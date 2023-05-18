package br.com.yolo.spigot.listener.inventory;

import br.com.yolo.spigot.SpigotMain;
import br.com.yolo.spigot.api.inventory.Inventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;

public class FrameworkListener implements Listener {

    @EventHandler
    public void register(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof Inventory) {
            Inventory inventory = (Inventory) holder;

            SpigotMain.getInstance().getInventoriesInAction().put(player.getUniqueId(), inventory);
        }
    }

    @EventHandler
    public void unregister(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        SpigotMain.getInstance().getInventoriesInAction().remove(player.getUniqueId());
    }

    @EventHandler
    public void unregister(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        SpigotMain.getInstance().getInventoriesInAction().remove(player.getUniqueId());
    }

    @EventHandler
    public void unregister(PlayerKickEvent event) {
        Player player = event.getPlayer();

        SpigotMain.getInstance().getInventoriesInAction().remove(player.getUniqueId());
    }
}
