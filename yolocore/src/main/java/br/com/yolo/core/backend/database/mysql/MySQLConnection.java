package br.com.yolo.core.backend.database.mysql;

import br.com.yolo.core.backend.Backend;
import br.com.yolo.core.backend.Credentials;
import br.com.yolo.core.util.callback.Callback;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Getter;

@Getter
public class MySQLConnection extends Credentials implements Backend {
    private HikariDataSource source;

    public MySQLConnection(String hostname, String database, String username, String password, int port) {
        super(hostname, database, username, password, port);
    }

    @Override
    public boolean openConnection(Callback callback) {
        try {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl("jdbc:mysql://" + getHostname() + ":" + getPort() + "/" + getDatabase().toLowerCase());
            config.setUsername(getUsername());
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            source = new HikariDataSource(config);
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
        return false;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public long ping() throws Throwable {
        return 0;
    }
}
