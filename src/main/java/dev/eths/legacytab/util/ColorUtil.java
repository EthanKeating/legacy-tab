package dev.eths.legacytab.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class ColorUtil {

    public String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static Pair<String, String> splitLine(String string) {
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

    public static String displayTimeMS(int seconds) {
        StringBuilder stringBuilder = new StringBuilder();
        if (seconds / 60 > 0)
            stringBuilder.append(seconds / 60).append("m");
        stringBuilder.append(seconds % 60).append("s");
        return stringBuilder.toString();
    }

    public static String displayTime(int seconds) {
        return seconds / 60 + ":" + (seconds % 60 > 9 ? "" : "0") + seconds % 60;
    }

    public static String toPascalCase(String string) {
        // Replace underscores with spaces
        String replaced = string.replace('_', ' ');

        // Convert to Pascal Case: capitalize first letter of each word
        String[] words = replaced.split(" ");  // Split by spaces
        StringBuilder pascalCase = new StringBuilder();

        // Capitalize the first letter of each word and add it to the StringBuilder
        for (String word : words) {
            // Capitalize the first letter and append the rest of the word
            pascalCase.append(word.substring(0, 1).toUpperCase()); // Capitalize first letter
            pascalCase.append(word.substring(1).toLowerCase()); // Lowercase the rest of the word
            pascalCase.append(" "); // Add space between words
        }

        // Remove the trailing space (optional)
        return pascalCase.toString().trim();
    }

}
