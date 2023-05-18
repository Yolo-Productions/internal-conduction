package br.com.yolo.core;

import br.com.yolo.core.backend.data.impl.ServerDataImpl;
import br.com.yolo.core.backend.database.mysql.MySQLConnection;
import br.com.yolo.core.backend.database.redis.RedisConnection;
import br.com.yolo.core.server.type.ServerType;
import br.com.yolo.core.storage.json.JsonModule;
import br.com.yolo.core.storage.module.AccountModule;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;

public class Client {

    @Getter
    @Setter
    private static Management management;

    @Getter
    @Setter
    private static ServerType server = ServerType.UNKNOWN;

    @Getter
    @Setter
    private static String serverName, serverId = RandomStringUtils.randomAlphanumeric(4);

    @Getter
    @Setter
    private static MySQLConnection mySQLConnection;
    @Getter
    @Setter
    private static RedisConnection redisConnection;

    @Getter
    @Setter
    private static ServerDataImpl serverData;

    @Getter
    private static final AccountModule accountModule = new AccountModule();
    @Getter
    private static final JsonModule jsonModule = new JsonModule();

    public static void initialize(Management newManagement) {
        MySQLConnection sql = new MySQLConnection("127.0.0.1", "yolo", "root", "", 3306);
        RedisConnection redis = new RedisConnection("127.0.0.1", 6379);

        sql.openConnection(thrown -> {
            if (thrown != null) {
                System.out.println("> Ocorreu um erro ao tentar estabelecer conexão com o MysQL.");
                return;
            }
        });
        redis.openConnection(thrown -> {
            if (thrown != null) {
                System.out.println("> Ocorreu um erro ao tentar estabelecer conexão com o Redis.");
                return;
            }
        });

        setMySQLConnection(sql);
        setRedisConnection(redis);
        setManagement(newManagement);

        setServerData(new ServerDataImpl(redisConnection));
    }
}
