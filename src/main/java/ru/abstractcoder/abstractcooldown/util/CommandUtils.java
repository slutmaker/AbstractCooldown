package ru.abstractcoder.abstractcooldown.util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public final class CommandUtils {

    private CommandUtils() {
    }

    public static Command getCommandByAlias(String alias) {
        Field field;
        try {
            field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        } catch (NoSuchFieldException e) {
            return null;
        }
        field.setAccessible(true);
        CommandMap map;
        try {
            map = (CommandMap) field.get(Bukkit.getServer());
        } catch (IllegalAccessException e) {
            return null;
        }
        return map.getCommand(alias);
    }

}