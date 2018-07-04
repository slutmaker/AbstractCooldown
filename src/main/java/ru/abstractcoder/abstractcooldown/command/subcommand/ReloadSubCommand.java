package ru.abstractcoder.abstractcooldown.command.subcommand;

import org.bukkit.command.CommandSender;
import ru.abstractcoder.abstractcooldown.yml.LanguageConfig;
import ru.abstractcoder.abstractcooldown.yml.MainConfig;

public class ReloadSubCommand extends SubCommand {

    private final MainConfig mainConfig;
    private final LanguageConfig languageConfig;

    public ReloadSubCommand(MainConfig mainConfig, LanguageConfig languageConfig) {
        super("reload", 1, "&b/acd reload &f- перезагрузить конфиг и сообщения");
        this.mainConfig = mainConfig;
        this.languageConfig = languageConfig;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        mainConfig.reload();
        languageConfig.reload();
        sender.sendMessage(languageConfig.getMsg("reloaded", true));
    }
}