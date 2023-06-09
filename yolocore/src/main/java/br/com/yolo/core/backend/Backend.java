package br.com.yolo.core.backend;

import br.com.yolo.core.util.callback.Callback;

public interface Backend {

    boolean openConnection(Callback callback);

    boolean closeConnection(Callback callback);

    boolean isConnected();

    long ping() throws Throwable;
}
