package dev.eths.legacytab.listener;

import dev.eths.legacytab.LegacyTab;
import dev.eths.legacytab.util.IListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerQuitListener extends IListener {
    public PlayerQuitListener(JavaPlugin plugin, LegacyTab service) {
        super(plugin, service);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        service.getPlayerMap().remove(player.getUniqueId());
    }
}
