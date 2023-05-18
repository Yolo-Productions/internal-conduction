package br.com.yolo.core.account;

import br.com.yolo.core.Client;
import br.com.yolo.core.account.datahandler.DataHandler;
import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public final class Account {
    private final UUID uniqueId;
    private final String name;
    private final DataHandler dataHandler;

    public Account(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;

        dataHandler = new DataHandler(uniqueId);
    }

    public Player toPlayer() {
        return Client.getManagement().getPlayer(uniqueId, Player.class);
    }

    public ProxiedPlayer toProxied() {
        return Client.getManagement().getPlayer(uniqueId, ProxiedPlayer.class);
    }

    public void sendMessage(String message) {
        Client.getManagement().sendMessage(uniqueId, message);
    }

    public void sendMessage(String... message) {
        Client.getManagement().sendMessage(uniqueId, message);
    }

    public void sendMessage(BaseComponent message) {
        Client.getManagement().sendMessage(uniqueId, message);
    }

    public void sendMessage(BaseComponent[] message) {
        Client.getManagement().sendMessage(uniqueId, message);
    }
}
