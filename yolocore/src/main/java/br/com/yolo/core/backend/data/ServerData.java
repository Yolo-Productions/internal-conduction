package br.com.yolo.core.backend.data;

import br.com.yolo.core.backend.database.redis.RedisConnection;
import br.com.yolo.core.server.Server;
import br.com.yolo.core.server.type.ServerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class ServerData {
    private final RedisConnection redis;

    public abstract void start(Server server);
    public abstract void stop();

    public abstract void update(Server server, String fieldName);
    public abstract void post(Server server);

    public abstract void get(ServerType server);
    public abstract boolean exists(ServerType server);

    public abstract void join(UUID uniqueId);
    public abstract void leave(UUID uniqueId);
}
