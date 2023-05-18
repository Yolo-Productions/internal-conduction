package br.com.yolo.spigot.api.inventory.build;

import br.com.yolo.spigot.api.inventory.event.ItemInteract;
import br.com.yolo.spigot.api.inventory.event.ItemUpdate;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Getter
public class ItemBuilder extends ItemStack {

    private static final Set<ItemBuilder> itemBuilderList = new LinkedHashSet<>();

    private ItemInteract interact;
    private ItemUpdate update;

    public ItemBuilder(Material material) {
        super(material);
    }

    public ItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    public ItemBuilder(Material material, int amount, int id) {
        super(material, amount, (short) id);
    }

    /* handling options */
    public ItemBuilder displayName(String display) {
        ItemMeta newMeta = getItemMeta();
        newMeta.setDisplayName(display);

        setItemMeta(newMeta);

        return(this);
    }

    public ItemBuilder displayDescription(String... display) {
        ItemMeta newMeta = getItemMeta();
        newMeta.setLore(Arrays.asList(display));

        setItemMeta(newMeta);

        return(this);
    }

    public ItemBuilder handleEnchantment(Enchantment enchantment) {
        ItemMeta newMeta = getItemMeta();
        newMeta.addEnchant(enchantment, 1, true);

        setItemMeta(newMeta);

        return(this);
    }

    public ItemBuilder handleEnchantment(Enchantment enchantment, int level) {
        ItemMeta newMeta = getItemMeta();
        newMeta.addEnchant(enchantment, level, true);

        setItemMeta(newMeta);

        return(this);
    }

    public ItemBuilder skullName(String name) {
        SkullMeta newMeta = (SkullMeta) getItemMeta();
        newMeta.setOwner(name);

        setItemMeta(newMeta);

        return(this);
    }

    public ItemBuilder skullUrl(String url) {
        SkullMeta skullMeta = (SkullMeta) getItemMeta();

        if (url != null) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", Base64.getEncoder().encodeToString(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes(StandardCharsets.UTF_8))));
            try {
                Field field = skullMeta.getClass().getDeclaredField("profile");
                field.setAccessible(true);
                field.set(skullMeta, profile);

                setItemMeta(skullMeta);
            } catch (Exception ex) { ex.printStackTrace(); }
        }

        return(this);
    }

    /* stock options */
    public ItemBuilder toInteract(ItemInteract interact) {
        this.interact = interact;
        itemBuilderList.add(this);

        return(this);
    }

    public ItemBuilder toUpdate(ItemUpdate update) {
        this.update = update;
        itemBuilderList.add(this);

        return(this);
    }

    public static ItemBuilder read(ItemStack stack) {
        return itemBuilderList.stream().filter(item -> item.isSimilar(stack)).findFirst().orElse(null);
    }
}