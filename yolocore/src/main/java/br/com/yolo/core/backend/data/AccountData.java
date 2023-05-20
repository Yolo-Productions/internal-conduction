package br.com.yolo.core.backend.data;

import br.com.yolo.core.account.Account;

import java.util.UUID;

public interface AccountData {

    Account loadAccount(UUID uniqueId) throws Exception;

    Account createAccount(UUID uniqueId, String name) throws Exception;

    boolean deleteAccount(UUID uniqueId);

    void saveAccount(Account account);

    void saveAccount(Account account, String jsonField, Object data);
}
