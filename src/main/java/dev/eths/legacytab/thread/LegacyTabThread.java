package dev.eths.legacytab.thread;

import lombok.SneakyThrows;
import dev.eths.legacytab.LegacyTab;
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
            long startTime = System.currentTimeMillis();
            try {
                new CopyOnWriteArrayList<>(service.getPlayerMap().values()).forEach(tabPlayer -> {
                    tabPlayer.getTabImpl().tick(tabPlayer.getTabAdapter());
                });
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            Thread.sleep(Math.max(0, 50 - (System.currentTimeMillis() - startTime)));
        }

    }

}
