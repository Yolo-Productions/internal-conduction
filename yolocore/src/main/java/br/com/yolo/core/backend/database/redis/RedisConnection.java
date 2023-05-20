package br.com.yolo.core.backend.database.redis;

import br.com.yolo.core.backend.Backend;
import br.com.yolo.core.backend.Credentials;
import br.com.yolo.core.util.callback.Callback;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

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

    public static class PubSubListener implements Runnable {

        private RedisConnection redis;
        private JedisPubSub jpsh;

        private final String[] channels;

        public PubSubListener(RedisConnection redis, JedisPubSub s, String... channels) {
            this.redis = redis;
            this.jpsh = s;
            this.channels = channels;
        }

        @Override
        public void run() {
            try (Jedis jedis = redis.getPool().getResource()) {
                try {
                    jedis.subscribe(jpsh, channels);
                } catch (Exception e) {
                    try {
                        jpsh.unsubscribe();
                    } catch (Exception e1) {

                    }
                    run();
                }
            }
        }

        public void addChannel(String... channel) {
            jpsh.subscribe(channel);
        }

        public void removeChannel(String... channel) {
            jpsh.unsubscribe(channel);
        }

        public void poison() {
            jpsh.unsubscribe();
        }
    }
}
