package br.com.yolo.spigot.listener.account;

import br.com.yolo.core.Client;
import br.com.yolo.core.Constant;
import br.com.yolo.core.account.Account;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AccountListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public synchronized void account(AsyncPlayerPreLoginEvent event) {
        try {
            Account account = Client.getAccountData().loadAccount(event.getUniqueId());

            if (account == null)
                Client.getAccountData().createAccount(event.getUniqueId(), event.getName());

            Client.getAccountModule().register(account);
        } catch (Exception ex) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Constant.ACCOUNT_NOT_LOADED);
        }
    }

    @EventHandler
    public void unlink(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Client.getAccountModule().remove(player.getUniqueId());
    }

    @EventHandler
    public void unlink(PlayerKickEvent event) {
        Player player = event.getPlayer();

        Client.getAccountModule().remove(player.getUniqueId());
    }
}
