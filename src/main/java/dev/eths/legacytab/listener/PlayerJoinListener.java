package dev.eths.legacytab.listener;

import dev.eths.legacytab.LegacyTab;
import dev.eths.legacytab.example.ExampleAdapter;
import dev.eths.legacytab.player.WrappedTabPlayer;
import dev.eths.legacytab.util.IListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoinListener extends IListener {
    public PlayerJoinListener(JavaPlugin plugin, LegacyTab service) {
        super(plugin, service);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        service.getPlayerMap().put(
                player.getUniqueId(),
                new WrappedTabPlayer(player));


        service.setAdapter(player, new ExampleAdapter());
    }
}
