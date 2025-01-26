package dev.eths.legacytab.tab;

import dev.eths.legacytab.player.WrappedTabPlayer;
import dev.eths.legacytab.tab.types.TabItem;

import java.util.HashMap;

public abstract class TabAdapter {

    public static TabAdapter DEFAULT = new TabAdapter() {
        @Override
        public HashMap<Integer, TabItem> getItems(WrappedTabPlayer tabPlayer) {
            return new HashMap<>();
        }

        @Override
        public void tick() {

        }
    };

    public abstract HashMap<Integer, TabItem> getItems(WrappedTabPlayer tabPlayer);

    public abstract void tick();
}
