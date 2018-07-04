package ru.abstractcoder.abstractcooldown.command.subcommand;

import org.bukkit.command.CommandSender;
import ru.abstractcoder.abstractcooldown.util.ColorUtils;

public abstract class SubCommand {

    private String label;
    private int minArgs;
    private String usage;

    SubCommand(String label, int minArgs, String usage) {
        this.label = label;
        this.minArgs = minArgs;
        this.usage = ColorUtils.color(usage);
    }

    public String getLabel() {
        return label;
    }

    public boolean checkPermission(CommandSender sender) {
        return sender.hasPermission("AbstractCooldown." + label);
    }

    public int getMinArgs() {
        return minArgs;
    }

    public String getUsage() {
        return usage;
    }

    public abstract void execute(CommandSender sender, String[] args);

}