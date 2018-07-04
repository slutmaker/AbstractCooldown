package ru.abstractcoder.abstractcooldown.util;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class ColorUtils {

    private ColorUtils() {
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> color(Collection<String> strings) {
        return strings.stream().map(ColorUtils::color).collect(Collectors.toList());
    }

    public static String[] color(String[] strings) {
        return Arrays.stream(strings).map(ColorUtils::color).toArray(String[]::new);
    }

}