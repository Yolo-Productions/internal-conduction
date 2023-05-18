package br.com.yolo.core.backend.database.redis;

import br.com.yolo.core.backend.Backend;
import br.com.yolo.core.backend.Credentials;
import br.com.yolo.core.util.callback.Callback;
import lombok.Getter;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Getter
public class RedisConnection extends Credentials implements Backend {
    private JedisPool pool;

    public RedisConnection(String hostname, int port) {
        super(hostname, "", "", "", port);
    }

    @Override
    public boolean openConnection(Callback callback) {
        if (!pool.isClosed())
            return false;

        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(128);

            pool = new JedisPool(config, getHostname(), getPort());

            callback.done(null);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            callback.done(ex);

            return false;
        }
    }

    @Override
    public boolean closeConnection(Callback callback) {
        if (pool.isClosed())
            return false;

        pool.destroy();
        return true;
    }

    @Override
    public boolean isConnected() {
        return !pool.isClosed();
    }

    @Override
    public long ping() throws Throwable {
        return 0;
    }
}
