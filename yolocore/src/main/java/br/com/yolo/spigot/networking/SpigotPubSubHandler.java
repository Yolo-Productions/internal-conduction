package br.com.yolo.spigot.networking;

import br.com.yolo.core.Client;
import br.com.yolo.core.account.Account;
import br.com.yolo.core.server.Server;
import br.com.yolo.core.server.payload.ServerDataMessage;
import br.com.yolo.core.server.status.ServerStatus;
import br.com.yolo.core.server.type.ServerType;
import br.com.yolo.core.util.reflection.Reflection;
import br.com.yolo.spigot.event.build.server.ServerHandlerEvent;
import com.google.common.reflect.TypeToken;
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

            case "server-field": {
                String source = object.get("source").getAsString();
                ServerType server = ServerType.valueOf(object.get("serverType").getAsString());

                if (server.equals(ServerType.UNKNOWN))
                    break;

                ServerDataMessage.Action action = ServerDataMessage.Action.valueOf(object.get("action").getAsString());

                switch (action) {
                    case START: {
                        ServerDataMessage<ServerDataMessage.Start> payLoad = Client.GSON.fromJson(object, new TypeToken<ServerDataMessage<ServerDataMessage.Start>>() {}.getType());
                        Server handler = payLoad.getPayload().getServer();

                        Client.getServerModule().add(payLoad.getServerType(), handler);
                        new ServerHandlerEvent(handler.getSlots(), handler.getId(), handler.getType());
                        break;
                    }

                    case JOIN: {
                        break;
                    }

                    case LEAVE: {
                        break;
                    }

                    case STOP: {
                        break;
                    }
                }
            }
        }
    }
}
