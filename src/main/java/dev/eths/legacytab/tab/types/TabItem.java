package dev.eths.legacytab.tab.types;

import lombok.*;
import lombok.experimental.Accessors;
import dev.eths.legacytab.util.Skin;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TabItem {

    public static TabItem DEFAULT = new TabItem();

    private String text = "                     ";
    private Skin skin = Skin.LIGHT_GRAY;
    private int ping = 0;

}
