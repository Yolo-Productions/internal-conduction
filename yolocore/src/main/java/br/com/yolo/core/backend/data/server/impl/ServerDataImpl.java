package br.com.yolo.core.backend.data.server.impl;

import br.com.yolo.core.backend.data.server.ServerData;
import br.com.yolo.core.backend.database.redis.RedisConnection;
import br.com.yolo.core.server.Server;
import br.com.yolo.core.server.type.ServerType;

import java.util.UUID;

public final class ServerDataImpl extends ServerData {
    public ServerDataImpl(RedisConnection redis) {
        super(redis);
    }

    @Override
    public void start(Server server) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void update(Server server, String fieldName) {

    }

    @Override
    public void post(Server server) {

    }

    @Override
    public void get(ServerType server) {

    }

    @Override
    public boolean exists(ServerType server) {
        return false;
    }

    @Override
    public void join(UUID uniqueId) {

    }

    @Override
    public void leave(UUID uniqueId) {

    }
}
