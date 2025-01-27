package dev.eths.legacytab.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.List;

@UtilityClass
public class ColorUtil {

    public String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public List<String> translate(List<String> lines) {
        for(int i = 0; i < lines.size(); i++)
            lines.set(i, translate(lines.get(i)));

        return lines;
    }

    public Pair<String, String> splitLine(String string) {
        String prefix = string, suffix = "";
        string = ColorUtil.translate(string);

        if(string.length() > 16) {
            int splitAt = string.charAt(15) == ChatColor.COLOR_CHAR ? 15 : 16;
            prefix = string.substring(0, splitAt);
            suffix = ChatColor.getLastColors(prefix) + string.substring(splitAt);
            suffix = suffix.substring(0, Math.min(suffix.length(), 16));
        }

        return new Pair<>(translate(prefix), translate(suffix));
    }

}
