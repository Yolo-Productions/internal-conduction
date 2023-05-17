package br.com.yolo.core.storage.module;

import br.com.yolo.core.account.Account;
import br.com.yolo.core.storage.Storage;

import java.util.UUID;

public class AccountModule extends Storage<UUID, Account> {

    public void register(Account account) {
        add(account.getUniqueId(), account);
    }

    public Account read(String name) {
        return toList().stream().filter(account -> account.getName().equals(name)).findFirst().orElse(null);
    }
}
