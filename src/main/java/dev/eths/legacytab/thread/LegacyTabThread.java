package dev.eths.legacytab.thread;

import dev.eths.legacytab.tab.TabAdapter;
import lombok.SneakyThrows;
import dev.eths.legacytab.LegacyTab;

import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class LegacyTabThread extends Thread{

    private final LegacyTab service;

    public LegacyTabThread(LegacyTab service) {
        super("legacy-tab-thread");

        this.service = service;
    }

    @SneakyThrows
    public void run() {
        while (true) {
            HashSet<TabAdapter> tickedTabs = new HashSet<TabAdapter>();

            long startTime = System.currentTimeMillis();
            try {
                new CopyOnWriteArrayList<>(service.getPlayerMap().values()).forEach(tabPlayer -> {
                    TabAdapter adapter = tabPlayer.getTabAdapter();

                    if (!tickedTabs.contains(adapter)) {
                        tickedTabs.add(adapter);
                        adapter.tick();
                    }

                    tabPlayer.getTabImpl().tick(tabPlayer.getTabAdapter());
                });
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            Thread.sleep(Math.max(0, 50 - (System.currentTimeMillis() - startTime)));
        }

    }

}
