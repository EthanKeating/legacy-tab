package dev.eths.legacytab.example;

import dev.eths.legacytab.player.WrappedTabPlayer;
import dev.eths.legacytab.tab.TabAdapter;
import dev.eths.legacytab.tab.types.TabItem;
import dev.eths.legacytab.util.Pair;
import dev.eths.legacytab.util.Skin;

import java.util.*;

public class ExampleAdapter extends TabAdapter {

    private final ArrayList<Skin> randomSkins = new ArrayList<>();

    public ExampleAdapter() {
        Skin[] skins = new Skin[] {Skin.DEAD, Skin.RED, Skin.GREEN_ICON, Skin.YELLOW_ICON, Skin.HUAHWI, Skin.PRIVATE, Skin.WARRIOR, };

        for(int i = 0; i < 80; i++) {
            randomSkins.add(skins[new Random().nextInt(skins.length)]);
        }
    }

    @Override
    public HashMap<Integer, TabItem> getItems(WrappedTabPlayer tabPlayer) {
        HashMap<Integer, TabItem> items = new HashMap<>();
        for(int i = 0; i < 80; i++) {
            String number = String.valueOf(i);
            String text = "&" + number.charAt(number.length() - 1) + UUID.randomUUID().toString().substring(0, 12);

            items.put(i, new TabItem(text, randomSkins.get(i), 0));
        }

        return items;
    }

    @Override
    public Pair<List<String>, List<String>> getHeaderFooter(WrappedTabPlayer tabPlayer) {
        return new Pair<>(Arrays.asList("&aThis is the header", "&eThis is another line of the header"),
                Arrays.asList("&cThis is the footer", "&6This is another line of the footer"));
    }

    @Override
    public void tick() {

    }
}
