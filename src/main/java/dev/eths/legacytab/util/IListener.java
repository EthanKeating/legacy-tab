package dev.eths.legacytab.util;

import dev.eths.legacytab.LegacyTab;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class IListener implements Listener {

    protected final JavaPlugin plugin;
    protected final LegacyTab service;

    public IListener(JavaPlugin plugin, LegacyTab service) {
        this.plugin = plugin;
        this.service = service;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

}
