package br.com.yolo.spigot.command;

import br.com.yolo.core.command.annotation.Command;
import br.com.yolo.core.resolver.field.FieldResolver;
import br.com.yolo.plugin.command.SimpleCommandFramework;
import br.com.yolo.spigot.Core;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public final class BukkitCommandFramework extends SimpleCommandFramework {

    private CommandMap map;

    public BukkitCommandFramework(Core plugin) {
        super(plugin, CommandSender.class);
        if (plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
            SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();
            try {
                Field field = new FieldResolver(SimplePluginManager.class, "commandMap").resolve();
                map = (CommandMap) field.get(manager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerServerCommand(Command tag) {
        BukkitCommand command = new BukkitCommand(tag.names()[0], tag.description(),
                tag.usage(), tag.names().length > 1 ? tag.names().length == 2 ? new String[]{tag.names()[1]} :
                Arrays.copyOfRange(tag.names(), 1, (tag.names().length - 1)) : new String[0]);
        command.setPermission(tag.permission());
        if (!tag.permissionMessage().isEmpty())
            command.setPermissionMessage(tag.permissionMessage());
        map.register(((Core) management).getDescription().getName(), command);
    }

    class BukkitCommand extends org.bukkit.command.Command {

        public BukkitCommand(String name, String description, String usageMessage,
                             String[] aliases) {
            super(name, description, usageMessage, Arrays.asList(aliases));
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            dispatchCommand(sender, label, args);
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
            List<String> completions = BukkitCommandFramework.this
                    .tabComplete(sender, alias, args);
            if (completions != null)
                return completions;
            return super.tabComplete(sender, alias, args);
        }
    }
}
