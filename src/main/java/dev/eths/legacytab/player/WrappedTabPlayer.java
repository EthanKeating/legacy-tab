package dev.eths.legacytab.player;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.player.User;
import lombok.Getter;
import lombok.Setter;
import dev.eths.legacytab.tab.TabAdapter;
import dev.eths.legacytab.tab.TabImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class WrappedTabPlayer {

    private final UUID uuid;
    private final User user;

    private final TabImpl tabImpl;
    private final boolean isLegacy;

    @Getter @Setter
    private TabAdapter tabAdapter;

    public WrappedTabPlayer(final Player player) {
        this.uuid = player.getUniqueId();
        this.user = PacketEvents.getAPI().getPlayerManager().getUser(player);
        this.tabImpl = new TabImpl(this);
        this.isLegacy = user.getClientVersion().isOlderThanOrEquals(ClientVersion.V_1_7_10);
        this.tabAdapter = TabAdapter.DEFAULT;
    }

    public Player toBukkit() {
        return Bukkit.getPlayer(uuid);
    }

}
