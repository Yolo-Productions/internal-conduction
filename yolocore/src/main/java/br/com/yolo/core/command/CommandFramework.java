package br.com.yolo.core.command;

import br.com.yolo.core.plugin.RegisteredCommand;

import java.util.List;

public interface CommandFramework {

    void execute(Object sender, String label, String[] args);

    List<String> tabComplete(Object sender, String label, String[] args);

    void registerAll(String pakage);

    void registerAll(CommandListener listener);

    void clearCommands();

    RegisteredCommand getCommand(String name);

    Class<?> getSenderType();
}
