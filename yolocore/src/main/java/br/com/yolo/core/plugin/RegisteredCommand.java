package br.com.yolo.core.plugin;

import br.com.yolo.core.command.Command;
import br.com.yolo.core.command.CommandListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public final class RegisteredCommand {

    @Getter private final CommandListener listener;
    @Getter private final Method method;
    @Getter private final Command tag;
}
