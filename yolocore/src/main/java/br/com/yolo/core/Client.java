package br.com.yolo.core;

import br.com.yolo.core.storage.module.AccountModule;
import lombok.Getter;

public class Client {

    @Getter
    private static final AccountModule accountModule = new AccountModule();
}