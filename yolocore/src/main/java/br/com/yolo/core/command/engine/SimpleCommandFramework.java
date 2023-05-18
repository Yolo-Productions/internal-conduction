package br.com.yolo.core.command.engine;

import br.com.yolo.core.command.CommandFramework;
import br.com.yolo.core.command.CommandListener;
import br.com.yolo.core.command.annotation.Command;
import br.com.yolo.core.command.annotation.Completer;
import br.com.yolo.core.management.Management;
import br.com.yolo.core.resolver.ClassGetter;
import br.com.yolo.core.resolver.method.MethodResolver;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public abstract class SimpleCommandFramework implements CommandFramework {

    protected final Management management;
    protected final Class<?> senderType;

    private Class<?> playerClass;

    protected Map<String, RegisteredCommand> knownCommands = new HashMap<>();

    public SimpleCommandFramework(Management management, Class<?> senderType) {
        this.management = management;
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
    public void dispatchCommand(Object sender, String label, String[] args) {
        RegisteredCommand command = knownCommands.get(label.toLowerCase());
        if (command != null) {
            Command tag = command.getTag();
            // Checa se este comando é apenas para Players (void cmd(Player sender, String[] args))
            if (command.getMethod().getParameterTypes()[0].isAssignableFrom(playerClass) &&
                    // Checa se este sender é um Player
                    playerClass.isAssignableFrom(sender.getClass())) {
                if (tag.permission().isEmpty() || hasPermission(sender, tag.permission())) {
                    if (tag.async()) {
                        management.runTaskAsync(() -> {
                            command.execute(sender, args);
                        });
                    } else {
                        command.execute(sender, args);
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

    @Override
    public List<String> tabComplete(Object sender, String label, String[] args) {
        RegisteredCommand command = knownCommands.get(label.toLowerCase());
        if (command != null) {
            Command tag = command.getTag();
            if (!tag.permission().isEmpty() && !hasPermission(sender, tag.permission()))
                return null;
            return command.getCompleter().complete(sender, args);
        }
        return null;
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
    public void registerAll(String pakage) {
        for (Class<?> clazz : ClassGetter.getClassesForPackageByFile(management.getFile(),
                pakage)) {
            if (CommandListener.class.isAssignableFrom(clazz)) {
                try {
                    registerAll((CommandListener) clazz.getConstructor().newInstance());
                } catch (Exception e) {
                    management.getLogger().warning("Couldn't instantiate command listener class '"
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
                    if (getSenderType().isAssignableFrom(paramTypes[0])) {
                        if (String[].class.isAssignableFrom(paramTypes[1])) {
                            for (String name : command.names())
                                knownCommands.put(name.toLowerCase(),
                                        new RegisteredCommand(listener, method, command));
                            registerServerCommand(command);
                            String realName = command.names()[0];
                            management.getLogger().info("Command '" + realName + "' registered!");
                        } else {
                            management.getLogger().warning("Command parameter[1] method must be" +
                                    " an String[].class array!");
                        }
                    } else {
                        management.getLogger().warning("Command parameter[0] method must extends "
                                + getSenderType().getName() + "!");
                    }
                } else {
                    management.getLogger().warning("Parameter index for commands must be 2, " +
                            "but was " + paramTypes.length + "!");
                }
            }
            registerCompleter(listener, method);
        }
    }

    @Override
    public void registerCompleter(CommandListener listener, Method method) {
        Completer completer = method.getAnnotation(Completer.class);
        if (completer != null) {
            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != 2) {
                if (getSenderType().isAssignableFrom(paramTypes[0])) {
                    if (String[].class.isAssignableFrom(paramTypes[1])) {
                        AtomicReference<RegisteredCompleter> ref = new AtomicReference<>();
                        knownCommands.values().stream().filter(cmd -> Arrays.stream(cmd.getTag().names())
                                        .map(String::toLowerCase)
                                        .anyMatch(completer.names()[0].toLowerCase()::equals))
                                .forEach(cmd -> {
                                    if (ref.get() == null)
                                        ref.set(new RegisteredCompleter(listener, method,
                                                completer));
                                    cmd.setCompleter(ref.get());
                                });
                        if (ref.get() != null) {
                            management.getLogger().info("Completer '" + completer.names()[0] + "' registered!");
                        }
                    } else {
                        management.getLogger().warning("Completer parameter[1] method must be" +
                                " an String[].class array!");
                    }
                } else {
                    management.getLogger().warning("Completer parameter[0] method must extends "
                            + getSenderType().getName() + "!");
                }
            } else {
                management.getLogger().warning("Parameter index for completers must be 2, " +
                        "but was " + paramTypes.length + "!");
            }
        }
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
