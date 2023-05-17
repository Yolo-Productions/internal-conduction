package br.com.yolo.core.player.account;

import br.com.yolo.core.management.Management;
import br.com.yolo.core.player.account.datahandler.DataHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public final class Account {

    @Getter private final UUID uniqueId;
    @Getter private final String name;

    @Getter private final DataHandler dataHandler;

    public Account(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.dataHandler = new DataHandler(uniqueId);
    }

    public static Account getAccountByUUID(UUID id) {
        return AccountMap.getInstance().get(id);
    }

    @SuppressWarnings(value = "all") // "Suspicious call" are u kidding me!?
    public static Account getAccountByPlayer(Object paramPlayer) {
        return AccountMap.getInstance().get(paramPlayer);
    }
}
