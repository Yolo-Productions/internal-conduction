package br.com.yolo.core.plugin;

import br.com.yolo.core.command.CommandException;
import br.com.yolo.core.command.CommandListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;

@RequiredArgsConstructor
public final class RegisteredCompleter {

    @Getter private final CommandListener listener;
    @Getter private final Method method;

    @SuppressWarnings(value = "all")
    public List<String> tabComplete(Object sender, String[] args) throws CommandException {
        try {
            List<String> completions = (List<String>) method.invoke(listener, sender, args);
            if (completions != null)
                return completions;
            return null;
        } catch (Throwable e) {
            throw new CommandException(e.getMessage());
        }
    }
}
