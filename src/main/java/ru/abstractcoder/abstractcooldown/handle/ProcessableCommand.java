package ru.abstractcoder.abstractcooldown.handle;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import ru.abstractcoder.abstractcooldown.util.ColorUtils;
import ru.abstractcoder.abstractcooldown.util.CommandUtils;
import ru.abstractcoder.abstractcooldown.util.StringUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessableCommand {

    private String label;
    private boolean checkAliases;
    private List<String> similarCommands;
    private String permission;
    private int minArgsLength;
    private String customMessage;
    private Duration defaultTime;
    private Map<String, Duration> groupsDuration;

    ProcessableCommand(String label, boolean checkAliases, List<String> similarCommands, String permission, int minArgsLength, String customMessage,
                       Duration defaultTime, Map<String, Duration> groupsDuration) {
        String[] split = label.split(" ");
        Command command = CommandUtils.getCommandByAlias(split[0]);
        if (command != null) {
            split[0] = command.getName();
        }
        this.label = StringUtils.combine(split).toLowerCase();
        this.checkAliases = checkAliases;
        if (similarCommands != null) {
            similarCommands = similarCommands.stream().map(String::toLowerCase).collect(Collectors.toList());
        }
        this.similarCommands = similarCommands;
        this.permission = permission;
        this.minArgsLength = minArgsLength;
        if (customMessage != null) {
            customMessage = ColorUtils.color(customMessage);
        }
        this.customMessage = customMessage;
        this.defaultTime = defaultTime;
        this.groupsDuration = groupsDuration;
    }

    public boolean isAn(String cmd) {
        String s = cmd;
        String[] split1 = label.split(" ");
        String[] split2 = cmd.split(" ");
        if (split2.length > 1) {
            if (split1.length > split2.length) {
                return false;
            }
            s = split2[0];
        }
        if (similarCommands != null && similarCommands.contains(s)) {
            return true;
        }
        if (!checkAliases) {
            return cmd.startsWith(label);
        }
        Command command = CommandUtils.getCommandByAlias(s);
        if (command != null) {
            split2[0] = command.getName();
        }
        return StringUtils.combine(split2).startsWith(label);
    }

    public String getLabel() {
        return label;
    }

    public boolean checkPermission(Player player) {
        return permission == null || player.hasPermission(permission);
    }

    public int getMinArgsLength() {
        return minArgsLength;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public Duration getDefaultTime() {
        return defaultTime;
    }

    public Map<String, Duration> getGroupsDuration() {
        return groupsDuration;
    }

}