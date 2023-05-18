package br.com.yolo.spigot.api.inventory.event;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface ItemInteract {
    void run(InventoryClickEvent event);
}
