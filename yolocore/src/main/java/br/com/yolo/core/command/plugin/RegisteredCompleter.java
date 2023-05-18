package br.com.yolo.core.command.plugin;

import br.com.yolo.core.command.CommandException;
import br.com.yolo.core.command.CommandListener;
import br.com.yolo.core.command.annotation.Completer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;

@RequiredArgsConstructor
public final class RegisteredCompleter {

    @Getter private final CommandListener listener;
    @Getter private final Method method;
    @Getter private final Completer tag;

    @SuppressWarnings(value = "all")
    public List<String> complete(Object sender, String[] args) throws CommandException {
        try {
            List<String> completions = (List<String>) method.invoke(listener, sender, args);
            if (completions != null)
                return completions;
            return null;
        } catch (Throwable e) {
            throw new CommandException("Unhandled exception when tab completing /" + tag.names()[0], e);
        }
    }
}
