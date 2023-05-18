package br.com.yolo.core.server;

import br.com.yolo.core.Client;
import br.com.yolo.core.server.status.ServerStatus;
import br.com.yolo.core.server.type.ServerType;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Server {
    private String name, world;

    private ServerType type;
    private ServerStatus status;

    private final Set<UUID> players;
    private int slots;

    public Server(ServerType type, int slots) {
        this.type = type;
        this.slots = slots;

        name = Client.getServerName();
        world = "world";

        status = ServerStatus.ONLINE;
        players = new LinkedHashSet<>();
    }
}
