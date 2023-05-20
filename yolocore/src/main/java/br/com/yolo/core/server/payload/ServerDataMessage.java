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
	public static class Start {
		private final Server server;
	}

	@Getter
	@RequiredArgsConstructor
	public static class Stop {
		private final ServerType server;
	}

	@Getter
	@RequiredArgsConstructor
	public static class Join {
		private final UUID uniqueId;
	}

	@Getter
	@RequiredArgsConstructor
	public static class Leave {
		private final UUID uniqueId;
	}

	public enum Action {
		START, STOP, JOIN, LEAVE
	}
}