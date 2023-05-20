package br.com.yolo.core.account.other.rank.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum Group {

    UNKNOWN("Membro", '7', "§7Membro", new String[]{ "player", "jogador" });

    private final String name;
    private final char color;
    private final String prefix;
    private final String[] aliases;

    private static final Map<String, Group> GROUP_MAP = new LinkedHashMap<>();
    static {
        for (Group group : values()) {
            GROUP_MAP.put(group.getName(), group);
            GROUP_MAP.put(Arrays.toString(group.getAliases()), group);
        }
    }

    public static Group read(String name) {
        return GROUP_MAP.values().stream().filter(group -> group.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
