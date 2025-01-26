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

public class LegacyTabPlugin extends JavaPlugin {

    public void onEnable() {
        new LegacyTab(this);
    }

    public void onDisable() {

    }

}
