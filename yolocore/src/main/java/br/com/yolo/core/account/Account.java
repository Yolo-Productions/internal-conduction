package br.com.yolo.core.account;

import br.com.yolo.core.account.datahandler.DataHandler;
import lombok.Getter;

import java.util.UUID;

@Getter
public final class Account {
    /* mandatory build options */
    private final UUID uniqueId;
    private final String name;
    private final DataHandler dataHandler;

    public Account(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;

        dataHandler = new DataHandler(uniqueId);
    }
}
