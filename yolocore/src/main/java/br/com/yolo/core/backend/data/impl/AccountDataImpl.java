package br.com.yolo.core.backend.data.impl;

import br.com.yolo.core.Client;
import br.com.yolo.core.Constant;
import br.com.yolo.core.account.Account;
import br.com.yolo.core.backend.data.AccountData;
import br.com.yolo.core.backend.database.mysql.MySQLConnection;
import br.com.yolo.core.backend.database.redis.RedisConnection;
import com.google.gson.JsonObject;
import lombok.Getter;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class AccountDataImpl implements AccountData {

    private final MySQLConnection backend;
    private final RedisConnection redis;

    public AccountDataImpl(MySQLConnection backend, RedisConnection redis) {
        this.backend = backend;
        this.redis = redis;

        try {
            try (Connection con = getBackend().getSource().getConnection();
                 Statement createStmt = con.createStatement()) {
                createStmt.executeUpdate("CREATE TABLE IF NOT EXISTS `"
                        + getTableName() + "` (" + "`uuid` CHAR(36), "
                        + "`json` LONGTEXT NOT NULL);");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTableName() {
        return "accounts";
    }

    @Override
    public Account loadAccount(UUID uniqueId) {
        try {
            try (Connection con = getBackend().getSource().getConnection()) {
                try (PreparedStatement getStmt = con.prepareStatement("SELECT * FROM `" +
                        getTableName() + "` WHERE `uuid`=?")) {

                    getStmt.setString(1, uniqueId.toString());

                    try (ResultSet resultSet = getStmt.executeQuery()) {
                        if (resultSet.next())
                            return Client.getJsonModule().read("main").fromJson(resultSet
                                    .getString(2), Account.class);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return null;
    }

    @Override
    public Account createAccount(UUID uniqueId, String name) throws Exception {
        try (Connection con = getBackend().getSource().getConnection()) {
            try (PreparedStatement insertStmt = con.prepareStatement("INSERT INTO `" +
                    getTableName() + "` (`uuid`, `json`) VALUES (?, ?);")) {

                Account account = new Account(uniqueId, name);

                insertStmt.setString(1, account.getUniqueId().toString());
                insertStmt.setString(2, Client.getJsonModule().read("main")
                        .toJson(account));

                insertStmt.execute();

                try (Jedis jedis = redis.getPool().getResource()) {
                    Map<String, String> mapToInsert = new HashMap<>();

                    JsonObject object = (JsonObject) Client.GSON.toJsonTree(account);
                    object.entrySet().forEach(entry -> mapToInsert.put(entry.getKey(), Client.GSON.toJson(entry.getValue())));

                    jedis.hmset(Constant.REDIS_ACCOUNT_KEY + uniqueId.toString(), mapToInsert);
                }

                return account;
            }
        }
    }

    @Override
    public boolean deleteAccount(UUID uniqueId) {
        try (Connection con = getBackend().getSource().getConnection()) {
            try (PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM `" +
                    getTableName() + "` WHERE `uuid`=?;")) {

                deleteStmt.setString(1, uniqueId.toString());

                deleteStmt.execute();
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void saveAccount(Account account) {
        Client.getManagement().runAsync(() -> {
            try (Connection con = getBackend().getSource().getConnection()) {
                try (PreparedStatement saveStmt = con.prepareStatement("UPDATE `" +
                        getTableName() + "` SET `json`=? WHERE `uuid`=?")) {

                    saveStmt.setString(1, Client.getJsonModule().read("main")
                            .toJson(account));
                    saveStmt.setString(2, account.getUniqueId().toString());

                    saveStmt.execute();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void saveAccount(Account account, String field, Object data) {
        Client.getManagement().runAsync(() -> {
            try (Connection con = getBackend().getSource().getConnection()) {
                try (PreparedStatement saveStmt = con.prepareStatement("UPDATE `" +
                        getTableName() + "` JSON_REPLACE(`json`, '$." + field
                        + "', ?) WHERE `uuid`=?")) {

                    saveStmt.setString(1, Client.getJsonModule().read("main")
                            .toJson(String.valueOf(data)));
                    saveStmt.setString(2, account.getUniqueId().toString());

                    saveStmt.execute();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
