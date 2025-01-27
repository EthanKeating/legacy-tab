package dev.eths.legacytab.tab;

import dev.eths.legacytab.player.WrappedTabPlayer;
import dev.eths.legacytab.tab.types.TabItem;
import dev.eths.legacytab.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class TabAdapter {

    public static TabAdapter DEFAULT = new TabAdapter() {
        @Override
        public HashMap<Integer, TabItem> getItems(WrappedTabPlayer tabPlayer) {
            return new HashMap<>();
        }

        @Override
        public Pair<List<String>, List<String>> getHeaderFooter(WrappedTabPlayer tabPlayer) {
            return new Pair<>(Arrays.asList(), Arrays.asList());
        }

        @Override
        public void tick() {

        }
    };

    public abstract HashMap<Integer, TabItem> getItems(WrappedTabPlayer tabPlayer);

    public abstract Pair<List<String>, List<String>> getHeaderFooter(WrappedTabPlayer tabPlayer);

    public abstract void tick();
}
