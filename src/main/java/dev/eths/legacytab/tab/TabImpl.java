package dev.eths.legacytab.tab;

import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerListHeaderAndFooter;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams;
import lombok.Getter;
import dev.eths.legacytab.player.WrappedTabPlayer;
import dev.eths.legacytab.tab.types.TabColumn;
import dev.eths.legacytab.tab.types.TabElement;
import dev.eths.legacytab.tab.types.TabItem;
import dev.eths.legacytab.util.ColorUtil;
import dev.eths.legacytab.util.Pair;
import dev.eths.legacytab.util.Skin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TabImpl {

    @Getter private static final HashMap<Integer, String> CODES = new HashMap<>();
    private static final String orderPrefix = "!0000000000000";
    static {
        AtomicInteger slot = new AtomicInteger(0);
        for(ChatColor firstColor : ChatColor.values()) for(ChatColor secondColor : ChatColor.values()) for(ChatColor thirdColor : ChatColor.values())
            CODES.put(slot.getAndIncrement(), firstColor.toString() + secondColor.toString() + thirdColor.toString() + ChatColor.RESET);
    }

    private final WrappedTabPlayer tabPlayer;
    private final HashMap<Integer, TabElement> elements = new HashMap<>();
    private Pair<List<String>, List<String>> headerFooter = new Pair<>(Collections.emptyList(), Collections.emptyList());
    private final PlayerInfoQueue addQueue, removeQueue, nameQueue, pingQueue;

    public TabImpl(WrappedTabPlayer tabPlayer) {
        this.tabPlayer = tabPlayer;
        this.addQueue = new PlayerInfoQueue(tabPlayer, WrapperPlayServerPlayerInfo.Action.ADD_PLAYER);
        this.removeQueue = new PlayerInfoQueue(tabPlayer, WrapperPlayServerPlayerInfo.Action.REMOVE_PLAYER);
        this.nameQueue = new PlayerInfoQueue(tabPlayer, WrapperPlayServerPlayerInfo.Action.UPDATE_DISPLAY_NAME);
        this.pingQueue = new PlayerInfoQueue(tabPlayer, WrapperPlayServerPlayerInfo.Action.UPDATE_LATENCY);


        for (int i = 0; i < 80; i++) {
            create(i);
        }

        addQueue.flush();
    }

    public TabElement getElement(int row, TabColumn column) {
        row = Math.min(19, row);
        int id = row + column.getSlot();

        if (tabPlayer.isLegacy()) {
            id = ((row * 3) + column.getIndex());
        }

        return elements.get(id);
    }

    public int getIndex(int row, TabColumn column) {

        row = Math.min(19, row);
        int id = row + column.getSlot();

        if (tabPlayer.isLegacy()) {
            id = ((row * 3) + column.getIndex());
        }

        return id;
    }

    public Pair<Integer, TabColumn> getRowColumn(int index) {
        int column = index / 20;
        int row = index - (column * 20);

        return new Pair<>(row, TabColumn.values()[column]);
    }

    public void tick(TabAdapter tabAdapter) {
        updateHeaderFooter(tabAdapter.getHeaderFooter(tabPlayer));

        for (int i = 0; i < 80; i++) {

            if (tabPlayer.isLegacy() && i >= 60) break;

            Pair<Integer, TabColumn> rowColumn = getRowColumn(i);
            int modifiedIndex = getIndex(rowColumn.getKey(), rowColumn.getValue());

            TabElement tabElement = elements.get(modifiedIndex);
            TabItem tabItem = tabAdapter.getItems(tabPlayer).getOrDefault(i, TabItem.DEFAULT);

            boolean updateSkin = !tabPlayer.isLegacy() && !tabElement.getSkin().matches(tabItem.getSkin());
            boolean updatePing = !tabPlayer.isLegacy() && tabElement.getPing() != tabItem.getPing();
            boolean updateText = !tabElement.getText().equals(ColorUtil.translate(tabItem.getText()));

            tabElement.setText(ColorUtil.translate(tabItem.getText()));
            tabElement.setSkin(tabItem.getSkin());
            tabElement.setPing((short) Math.min(Math.max(0, tabItem.getPing()), Short.MAX_VALUE));


            if (updateSkin) {
                updateSkin(i, tabElement);
            } else {
                if (updatePing) {
                    updatePing(tabElement);
                }

                if (updateText) {
                    updateText(i, tabElement);
                }
            }
        }

        removeQueue.flush();
        addQueue.flush();
        pingQueue.flush();
        nameQueue.flush();
    }

    private void updateText(int id, TabElement tabElement) {
        if (tabPlayer.isLegacy()) {
            Pair<String, String> splitLine = ColorUtil.splitLine(tabElement.getText());

            tabPlayer.getUser().sendPacket(
                    new WrapperPlayServerTeams(
                            tabElement.getUserProfile().getName(),//team name
                            WrapperPlayServerTeams.TeamMode.UPDATE,
                            new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                                    Component.text(""), //displayname - not required?
                                    Component.text(splitLine.getKey()), //prefix
                                    Component.text(splitLine.getValue()), //suffix
                                    WrapperPlayServerTeams.NameTagVisibility.ALWAYS,
                                    WrapperPlayServerTeams.CollisionRule.ALWAYS,
                                    NamedTextColor.WHITE,
                                    WrapperPlayServerTeams.OptionData.NONE
                            ),
                            Collections.singletonList(CODES.get(id)) //Player names
                    )
            );
        } else {
            nameQueue.push(new WrapperPlayServerPlayerInfo.PlayerData(
                    Component.text(tabElement.getText()),
                    tabElement.getUserProfile(),
                    GameMode.SURVIVAL,
                    tabElement.getPing()
            ));
        }
    }

    private void updateHeaderFooter(Pair<List<String>, List<String>> newHeaderFooter) {
        List<String> header = newHeaderFooter.getKey();
        List<String> footer = newHeaderFooter.getValue();

        if (!header.equals(headerFooter.getKey()) || !footer.equals(headerFooter.getValue())) {
            this.headerFooter = newHeaderFooter;

            WrapperPlayServerPlayerListHeaderAndFooter headerAndFooter = new WrapperPlayServerPlayerListHeaderAndFooter(
                    Component.text(String.join("\n", ColorUtil.translate(header))),
                    Component.text(String.join("\n", ColorUtil.translate(footer)))
            );

            tabPlayer.getUser().sendPacket(headerAndFooter);
        }
    }

    private void updatePing(TabElement tabElement) {
        pingQueue.push(new WrapperPlayServerPlayerInfo.PlayerData(
                Component.text(ColorUtil.translate(tabElement.getText())),
                tabElement.getUserProfile(),
                GameMode.SURVIVAL,
                tabElement.getPing()
        ));
    }

    private void updateSkin(int id, TabElement tabElement) {
        WrapperPlayServerPlayerInfo.PlayerData info = new WrapperPlayServerPlayerInfo.PlayerData(
                Component.text(ColorUtil.translate(tabElement.getText())),
                tabElement.getUserProfile(),
                GameMode.SURVIVAL,
                tabElement.getPing()
        );

        removeQueue.push(info);

        tabElement.getUserProfile().setTextureProperties(tabElement.getSkin().toTexture());

        addQueue.push(info);
    }

    private void create(int id) {
        UserProfile userProfile = new UserProfile(
                UUID.randomUUID(),
                orderPrefix + (id <= 9 ? "0" + id : id),
                Skin.LIGHT_GRAY.toTexture()
        );

        TabElement tabElement = new TabElement(userProfile);

        elements.put(id, tabElement);

        addQueue.push(new WrapperPlayServerPlayerInfo.PlayerData(
                Component.text(tabPlayer.isLegacy() ? CODES.get(id) : ColorUtil.translate(tabElement.getText())),
                userProfile,
                GameMode.SURVIVAL,
                tabElement.getPing()
        ));

        if (tabPlayer.isLegacy()) {
            Pair<String, String> splitLine = ColorUtil.splitLine(tabElement.getText());

            tabPlayer.getUser().sendPacket(
                    new WrapperPlayServerTeams(
                            userProfile.getName(),//team name
                            WrapperPlayServerTeams.TeamMode.CREATE,
                            new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                                    Component.text(""), //displayname - not required?
                                    Component.text(splitLine.getKey()), //prefix
                                    Component.text(splitLine.getValue()), //suffix
                                    WrapperPlayServerTeams.NameTagVisibility.ALWAYS,
                                    WrapperPlayServerTeams.CollisionRule.ALWAYS,
                                    NamedTextColor.WHITE,
                                    WrapperPlayServerTeams.OptionData.NONE
                            ),
                            Collections.singletonList(CODES.get(id)) //Player names
                    )
            );
        }
    }

}

