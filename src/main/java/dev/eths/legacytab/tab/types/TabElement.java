package dev.eths.legacytab.tab.types;

import com.github.retrooper.packetevents.protocol.player.UserProfile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import dev.eths.legacytab.util.Skin;

@Getter
@Setter
@RequiredArgsConstructor
@Accessors(chain = true)
public class TabElement {

    private final UserProfile userProfile;

    private String text = "                     ";
    private Skin skin = Skin.LIGHT_GRAY;
    private short ping = 0;

}
