package br.com.yolo.spigot.listener;

import br.com.yolo.core.resolver.ClassGetter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public class Listener {
    private final Plugin plugin;
    private final String directory;

    public void sendPacket() {
        for (Class<?> clazz : ClassGetter.getClassesForPackageByPlugin(plugin, directory))
            if (org.bukkit.event.Listener.class.isAssignableFrom(clazz))
                try {
                    org.bukkit.event.Listener listener = (org.bukkit.event.Listener) clazz.newInstance();

                    Bukkit.getPluginManager().registerEvents(listener, plugin);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
    }
}
