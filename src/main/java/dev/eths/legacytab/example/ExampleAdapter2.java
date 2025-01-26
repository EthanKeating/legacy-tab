package dev.eths.legacytab.example;

import dev.eths.legacytab.player.WrappedTabPlayer;
import dev.eths.legacytab.tab.TabAdapter;
import dev.eths.legacytab.tab.types.TabColumn;
import dev.eths.legacytab.tab.types.TabItem;
import dev.eths.legacytab.util.Skin;

import java.util.HashMap;

public class ExampleAdapter extends TabAdapter {

    @Override
    public HashMap<Integer, TabItem> getItems(WrappedTabPlayer tabPlayer) {
        HashMap<Integer, TabItem> items = new HashMap<>();

        items.put(0, new TabItem() //Set the tab item of the first slot
                .setPing(0) //Set 0 ping to hide it on most pvp clients
                .setSkin(Skin.DEAD) //Use the built-in Skins or Cache skins using 'new Skin(UUID)'
                .setText("&cHello World!")); //Sets the text displayed on the fake player

        return items;
    }

    @Override
    public void tick() { } //Called every tick (50ms). Can be useful for time handling
}
