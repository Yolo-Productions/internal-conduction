package br.com.yolo.core.backend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Credentials {

    private final String hostname, database, username, password;
    private final int port;
}
