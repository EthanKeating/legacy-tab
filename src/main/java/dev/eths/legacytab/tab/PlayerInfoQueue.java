package dev.eths.legacytab.tab;

import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import dev.eths.legacytab.player.WrappedTabPlayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PlayerInfoQueue {
    private final WrappedTabPlayer tabPlayer;
    private final WrapperPlayServerPlayerInfo.Action action;
    private final Queue<WrapperPlayServerPlayerInfo.PlayerData> queue = new LinkedList<>();

    public PlayerInfoQueue(WrappedTabPlayer tabPlayer, WrapperPlayServerPlayerInfo.Action action) {
        this.tabPlayer = tabPlayer;
        this.action = action;
    }

    public void push(WrapperPlayServerPlayerInfo.PlayerData data) {
        queue.add(data);
    }

    public void flush() {
        if (queue.isEmpty()) {
            return;
        }

        tabPlayer.getUser().sendPacket(
                new WrapperPlayServerPlayerInfo(
                    WrapperPlayServerPlayerInfo.Action.REMOVE_PLAYER,
                    new ArrayList<>(queue) // Copy because we will reuse the queue
                )
        );

        queue.clear();
    }
}
