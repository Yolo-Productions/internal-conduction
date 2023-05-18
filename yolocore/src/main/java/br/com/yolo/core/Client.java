package br.com.yolo.core;

import br.com.yolo.core.server.type.ServerType;
import br.com.yolo.core.storage.module.AccountModule;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;

public class Client {

    @Getter
    @Setter
    private static ServerType server = ServerType.UNKNOWN;

    @Getter
    @Setter
    private static String serverName, serverId = RandomStringUtils.randomAlphanumeric(4);

    @Getter
    private static final AccountModule accountModule = new AccountModule();
}
