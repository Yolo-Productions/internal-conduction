package br.com.yolo.spigot.command.registry;

import br.com.yolo.core.command.CommandListener;
import br.com.yolo.core.command.annotation.Command;
import br.com.yolo.core.command.annotation.Completer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ExampleCommand implements CommandListener {

    @Command(names = {"example", "ex"}) // Exclusive to Players
    public void example(Player sender, String[] args) {
        sender.sendMessage("One day you'll leave this world behind...");
        sender.sendMessage("So live a life you will remember!");
    }

    @Command(names = {"example2", "ex2"}) // Both CONSOLE and Player can execute
    public void exampleGeneric(CommandSender sender, String[] args) {
        sender.sendMessage("One day you'll leave this world behind...");
        sender.sendMessage("So live a life you will remember!");
    }

    @Completer(names = {"example", "ex", "example2", "ex2"})
    public List<String> exampleCompleter(Player sender, String[] args) {
        return Arrays.stream(new String[] {"live", "a", "life", "you'll", "remember"})
                .collect(Collectors.toList());
    }
}
