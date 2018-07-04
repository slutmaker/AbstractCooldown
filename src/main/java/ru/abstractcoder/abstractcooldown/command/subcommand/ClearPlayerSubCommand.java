package ru.abstractcoder.abstractcooldown.command.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.abstractcoder.abstractcooldown.cooldown.CooldownCleaner;
import ru.abstractcoder.abstractcooldown.yml.LanguageConfig;

public class ClearPlayerSubCommand extends SubCommand {

    private final CooldownCleaner cooldownCleaner;
    private final LanguageConfig languageConfig;

    public ClearPlayerSubCommand(CooldownCleaner cooldownCleaner, LanguageConfig languageConfig) {
        super("clearplayer", 2, "&b/acd clearplayer <игрок> &f- очистить все кулдауны для указанного игрока");
        this.cooldownCleaner = cooldownCleaner;
        this.languageConfig = languageConfig;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void execute(CommandSender sender, String[] args) {
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(languageConfig.getMsg("playerNotOnline", true, args[1]));
            return;
        }
        cooldownCleaner.clearFor(player.getUniqueId());
        sender.sendMessage(languageConfig.getMsg("playerCleaned", true, player.getName()));
    }
}
