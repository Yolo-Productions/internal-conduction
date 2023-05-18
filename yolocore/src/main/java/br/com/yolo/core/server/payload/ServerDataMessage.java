package br.com.yolo.core.server.payload;

import br.com.yolo.core.server.Server;
import br.com.yolo.core.server.type.ServerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ServerDataMessage<T> {

	private final String source;
	private final ServerType serverType;
	private final Action action;
	private final T payload;

	@Getter
	@RequiredArgsConstructor
	public static class start {
		private final Server server;
	}

	@Getter
	@RequiredArgsConstructor
	public static class stop {
		private final ServerType server;
	}

	@Getter
	@RequiredArgsConstructor
	public static class join {
		private final UUID uniqueId;
	}

	@Getter
	@RequiredArgsConstructor
	public static class leave {
		private final UUID uniqueId;
	}

	public enum Action {
		START, STOP, JOIN, LEAVE
	}
}