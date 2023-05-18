package br.com.yolo.core.command.plugin;

import br.com.yolo.core.command.CommandException;
import br.com.yolo.core.command.CommandListener;
import br.com.yolo.core.command.annotation.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Method;

@RequiredArgsConstructor
@Getter
public final class RegisteredCommand {

    private final CommandListener listener;
    private final Method method;
    private final Command tag;
    @Setter private RegisteredCompleter completer;

    public void execute(Object sender, String[] args) throws CommandException {
        try {
            method.invoke(sender, args);
        } catch (Throwable e) {
            throw new CommandException("Unhandled exception when dispatching command /" + tag.names()[0], e);
        }
    }
}
