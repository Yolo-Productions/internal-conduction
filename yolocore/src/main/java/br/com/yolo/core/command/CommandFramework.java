package br.com.yolo.core.command;

import br.com.yolo.core.command.annotation.Command;
import br.com.yolo.core.command.engine.RegisteredCommand;

import java.lang.reflect.Method;
import java.util.List;

public interface CommandFramework {

    void dispatchCommand(Object sender, String label, String[] args);

    List<String> tabComplete(Object sender, String label, String[] args);

    void registerAll(String pakage);

    void registerAll(CommandListener listener);

    void registerCompleter(CommandListener listener, Method method);

    void registerServerCommand(Command tag);

    RegisteredCommand getCommand(String name);

    Class<?> getSenderType();
}
