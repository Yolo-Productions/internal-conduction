package br.com.yolo.core.plugin.command;

import br.com.yolo.core.command.annotation.Command;
import br.com.yolo.core.command.CommandFramework;
import br.com.yolo.core.command.CommandListener;
import br.com.yolo.core.command.annotation.Completer;
import br.com.yolo.core.Management;
import br.com.yolo.core.plugin.RegisteredCommand;
import br.com.yolo.core.plugin.RegisteredCompleter;
import br.com.yolo.core.util.resolver.ClassGetter;
import br.com.yolo.core.util.resolver.method.MethodResolver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SimpleCommandFramework implements CommandFramework {

    protected final Management plugin;
    protected final Class<?> senderType;

    private Class<?> playerClass;

    protected Map<String, RegisteredCommand> knownCommands = new HashMap<>();
    protected Map<String, RegisteredCompleter> knownCompleters = new HashMap<>();

    public SimpleCommandFramework(Management plugin, Class<?> senderType) {
        this.plugin = plugin;
        this.senderType = senderType;
        // Player
        if ((this.playerClass = ClassGetter.forNameOrNull("org.bukkit.craftbukkit.v1_8_R3" +
                ".entity.CraftPlayer"))
                == null) {
            // ProxiedPlayer
            this.playerClass = ClassGetter.forNameOrNull("net.md_5.bungee.UserConnection");
        }
    }

    @Override
    public void execute(Object sender, String label, String[] args) {
        RegisteredCommand command = knownCommands.get(label.toLowerCase());
        if (command != null) {
            Command tag = command.getTag();
            if (tag.playerOnly() && playerClass.isAssignableFrom(sender.getClass())) {
                if (tag.permission().isEmpty() || hasPermission(sender, tag.permission())) {
                    if (tag.async()) {
                        plugin.runTaskAsync(() -> {
                            try {
                                command.getMethod().invoke(sender, args);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } else {
                        try {
                            command.getMethod().invoke(sender, args);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (tag.permissionMessage().isEmpty())
                        sendMessage(sender, "§cVocê não possui permissão para executar este comando.");
                    else
                        sendMessage(sender, tag.permissionMessage());
                }
            } else {
                sendMessage(sender, "§cComando disponível apenas in-game!");
            }
        }
    }

    private void sendMessage(Object sender, String message) {
        try {
            new MethodResolver(sender.getClass(), "sendMessage", String.class)
                    .resolve().invoke(sender, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasPermission(Object sender, String name) {
        try {
            return (boolean) new MethodResolver(sender.getClass(), "hasPermission", String.class)
                    .resolve().invoke(sender, name);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> tabComplete(Object sender, String label, String[] args) {
        return null;
    }

    @Override
    public void registerAll(String pakage) {
        for (Class<?> clazz : ClassGetter.getClassesForPackageByFile(plugin.getFile(),
                pakage)) {
            if (CommandListener.class.isAssignableFrom(clazz)) {
                try {
                    registerAll((CommandListener) clazz.getConstructor().newInstance());
                } catch (Exception e) {
                    plugin.getLogger().warning("Could instantiate command listener class '"
                            + clazz.getSimpleName() + "': " + e);
                }
            }
        }
    }

    @Override
    public void registerAll(CommandListener listener) {
        for (Method method : listener.getClass().getMethods()) {
            method.setAccessible(true);
            Command command = method.getAnnotation(Command.class);
            if (command != null) {
                Class<?>[] paramTypes = method.getParameterTypes();
                if (paramTypes.length != 2) {
                    if (senderType.isAssignableFrom(paramTypes[0])) {
                        if (String[].class.isAssignableFrom(paramTypes[1])) {
                            for (String name : command.names())
                                knownCommands.put(name.toLowerCase(),
                                        new RegisteredCommand(listener, method, command));
                            String realName = command.names()[0];
                            plugin.getLogger().info("Command '" + realName + "' registered!");
                        } else {
                            plugin.getLogger().warning("Command parameter[1] method must be" +
                                    " an String[].class array!");
                        }
                    } else {
                        plugin.getLogger().warning("Command parameter[0] method must extends "
                                + senderType.getName() + "!");
                    }
                } else {
                    plugin.getLogger().warning("Parameter index for commands must be 2, " +
                            "but was " + paramTypes.length + "!");
                }
            }
            Completer completer = method.getAnnotation(Completer.class);
            if (completer != null) {
                Class<?>[] paramTypes = method.getParameterTypes();
                if (paramTypes.length != 2) {
                    if (senderType.isAssignableFrom(paramTypes[0])) {
                        if (String[].class.isAssignableFrom(paramTypes[1])) {
                            for (String name : completer.names())
                                knownCompleters.put(name.toLowerCase(),
                                        new RegisteredCompleter(listener, method));
                            String realName = completer.names()[0];
                            plugin.getLogger().info("Completer '" + realName + "' registered!");
                        } else {
                            plugin.getLogger().warning("Completer parameter[1] method must be" +
                                    " an String[].class array!");
                        }
                    } else {
                        plugin.getLogger().warning("Completer parameter[0] method must extends "
                                + senderType.getName() + "!");
                    }
                } else {
                    plugin.getLogger().warning("Parameter index for completers must be 2, " +
                            "but was " + paramTypes.length + "!");
                }
            }
        }
    }

    @Override
    public synchronized void clearCommands() {
        knownCommands.clear();
    }

    @Override
    public RegisteredCommand getCommand(String name) {
        return knownCommands.get(name.toLowerCase());
    }

    @Override
    public Class<?> getSenderType() {
        return senderType;
    }
}
