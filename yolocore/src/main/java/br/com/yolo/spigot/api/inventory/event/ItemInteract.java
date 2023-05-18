package br.com.yolo.spigot.api.inventory.event;

import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemInteract {
    void run(PlayerInteractEvent event);
}
