package dev.eths.legacytab;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
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

@Getter
public class LegacyTab {

    private final HashMap<UUID, WrappedTabPlayer> playerMap = new HashMap<>();

    public LegacyTab(JavaPlugin plugin) {
        LegacyTab service = this;

        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(plugin));
        PacketEvents.getAPI().getSettings().checkForUpdates(false).bStats(false);
        PacketEvents.getAPI().load();

        new PlayerJoinListener(plugin, service);
        new PlayerQuitListener(plugin, service);
        new LegacyTabThread(service).start();
    }

    public void setAdapter(Player player, TabAdapter tabAdapter) {
        WrappedTabPlayer tabPlayer = playerMap.get(player.getUniqueId());
        if (tabPlayer != null)
            tabPlayer.setTabAdapter(tabAdapter);
    }

    public void removeAdapter(Player player) {
        WrappedTabPlayer tabPlayer = playerMap.get(player.getUniqueId());
        if (tabPlayer != null)
            tabPlayer.setTabAdapter(TabAdapter.DEFAULT);
    }

}
