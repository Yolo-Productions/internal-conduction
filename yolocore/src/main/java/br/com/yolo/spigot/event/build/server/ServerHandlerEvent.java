package br.com.yolo.spigot.event.build.server;

import br.com.yolo.core.server.type.ServerType;
import br.com.yolo.spigot.event.EventBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServerHandlerEvent extends EventBuilder {
    protected final int entryLimit;

    private final String id;
    private final ServerType type;
}
