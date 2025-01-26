package dev.eths.legacytab;

import lombok.Getter;
import dev.eths.legacytab.listener.PlayerJoinListener;
import dev.eths.legacytab.listener.PlayerQuitListener;
import dev.eths.legacytab.player.WrappedTabPlayer;
import dev.eths.legacytab.tab.TabAdapter;
import dev.eths.legacytab.thread.LegacyTabThread;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class LegacyTab {

    private LegacyTab service;

    @Getter private final HashMap<UUID, WrappedTabPlayer> playerMap = new HashMap<>();

    public LegacyTab(JavaPlugin plugin) {
        this.service = this;

        new PlayerJoinListener(plugin, service);
        new PlayerQuitListener(plugin, service);
        new LegacyTabThread(service).start();
    }

    public void setAdapter(Player player, TabAdapter tabAdapter) {
        WrappedTabPlayer tabPlayer = playerMap.get(player.getUniqueId());
        if (tabPlayer != null)
            tabPlayer.setTabAdapter(tabAdapter);
    }

}
