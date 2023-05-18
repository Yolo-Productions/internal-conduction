package br.com.yolo.spigot.api.inventory;

import br.com.yolo.spigot.api.inventory.build.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public abstract class Inventory implements InventoryHolder {
    private final String title;
    private final int rows;
    private org.bukkit.inventory.Inventory inventory;

    private final Map<Integer, ItemBuilder> buildMap = new LinkedHashMap<>();

    public Inventory(String title, int rows, org.bukkit.inventory.Inventory inventory) {
        this.title = title;
        this.rows = 9 * rows;
        this.inventory = inventory;
    }

    public Inventory(String title, int rows) {
        this(title, rows, null);
    }

    public void show(Player player) {
        inventory = Bukkit.createInventory(this, rows, title);
        buildMap.forEach((slot, build) -> inventory.setItem(slot, build));

        player.openInventory(inventory);
    }

    public void hide(Player player) {
        player.closeInventory();
    }
}
