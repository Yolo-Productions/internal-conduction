package br.com.yolo.spigot.listener.inventory;

import br.com.yolo.spigot.api.inventory.Inventory;
import br.com.yolo.spigot.api.inventory.build.ItemBuilder;
import br.com.yolo.spigot.api.inventory.event.ItemInteract;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void interact(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof Inventory) {
            Inventory inventory = (Inventory) holder;
            ItemBuilder builder = inventory.getBuildMap().get(event.getSlot());

            if (builder != null && !builder.isSimilar(new ItemStack(Material.AIR))) {
                ItemInteract interact = builder.getInteract();

                if (interact != null)
                    interact.run(event);
            }
        }
    }
}
