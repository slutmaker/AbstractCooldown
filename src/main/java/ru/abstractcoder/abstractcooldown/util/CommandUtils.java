package ru.abstractcoder.abstractcooldown.util;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public final class CommandUtils {

    private static CommandMap commandMap;

    static {
        try {
            MethodHandle handle = MethodHandles.lookup().findVirtual(Bukkit.getServer().getClass(), "getCommandMap", MethodType.methodType(SimpleCommandMap.class));
            commandMap = (CommandMap) handle.invoke(Bukkit.getServer());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private CommandUtils() {
    }

    public static Command getCommandByAlias(String alias) {
        return commandMap.getCommand(alias);
    }

}