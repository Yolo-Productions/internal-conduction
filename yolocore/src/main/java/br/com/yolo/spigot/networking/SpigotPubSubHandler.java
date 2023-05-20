package br.com.yolo.spigot.networking;

import br.com.yolo.core.Client;
import br.com.yolo.core.account.Account;
import br.com.yolo.core.util.reflection.Reflection;
import com.google.gson.JsonObject;
import redis.clients.jedis.JedisPubSub;

import java.lang.reflect.Field;
import java.util.UUID;

public class SpigotPubSubHandler extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        JsonObject object = Client.PARSER.parse(message).getAsJsonObject();

        switch (channel) {
            case "account-field": {
                UUID uniqueId = UUID.fromString(object.get("uniqueId").getAsString());
                Account account = Client.getAccountData().loadAccount(uniqueId);

                if (account == null)
                    break;

                Field field = Reflection.getField(Account.class, object.get("field").getAsString());

                if (field == null)
                    break;

                Object value = Client.GSON.fromJson(object.get("value"), field.getGenericType());

                try {
                    field.setAccessible(true);
                    field.set(account, value);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;
            }
        }
    }
}
