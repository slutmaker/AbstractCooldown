package ru.abstractcoder.abstractcooldown.command.subcommand;

import org.bukkit.command.CommandSender;
import ru.abstractcoder.abstractcooldown.cooldown.CooldownCleaner;
import ru.abstractcoder.abstractcooldown.yml.LanguageConfig;

public class ClearAllSubCommand extends SubCommand {

    private final CooldownCleaner cooldownCleaner;
    private final LanguageConfig languageConfig;

    public ClearAllSubCommand(CooldownCleaner cooldownCleaner, LanguageConfig languageConfig) {
        super("clearall", 1, "&b/acd clearall &f- очистить все кулдауны");
        this.cooldownCleaner = cooldownCleaner;
        this.languageConfig = languageConfig;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        cooldownCleaner.clearAll();
        sender.sendMessage(languageConfig.getMsg("allCleaned", true));
    }

}
