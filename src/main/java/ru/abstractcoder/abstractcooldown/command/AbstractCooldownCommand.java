package ru.abstractcoder.abstractcooldown.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.abstractcoder.abstractcooldown.command.subcommand.ClearAllSubCommand;
import ru.abstractcoder.abstractcooldown.command.subcommand.ClearPlayerSubCommand;
import ru.abstractcoder.abstractcooldown.command.subcommand.ReloadSubCommand;
import ru.abstractcoder.abstractcooldown.command.subcommand.SubCommand;
import ru.abstractcoder.abstractcooldown.cooldown.CooldownManagementFacade;
import ru.abstractcoder.abstractcooldown.yml.LanguageConfig;
import ru.abstractcoder.abstractcooldown.yml.MainConfig;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AbstractCooldownCommand implements CommandExecutor {

    private final Set<SubCommand> subCommandSet = new HashSet<>();
    private final LanguageConfig languageConfig;
    private final MainConfig mainConfig;
    private final CooldownManagementFacade cooldownManagementFacade;

    public AbstractCooldownCommand(LanguageConfig languageConfig, MainConfig mainConfig, CooldownManagementFacade cooldownManagementFacade) {
        this.languageConfig = languageConfig;
        this.mainConfig = mainConfig;
        this.cooldownManagementFacade = cooldownManagementFacade;
        registerSubCommands();
    }

    private void registerSubCommands() {
        subCommandSet.add(new ReloadSubCommand(mainConfig, languageConfig));
        subCommandSet.add(new ClearPlayerSubCommand(cooldownManagementFacade.getCooldownCleaner(), languageConfig));
        subCommandSet.add(new ClearAllSubCommand(cooldownManagementFacade.getCooldownCleaner(), languageConfig));
    }

    private void sendHelp(CommandSender sender) {
        subCommandSet.stream().filter(subCommand -> subCommand.checkPermission(sender)).map(SubCommand::getUsage).forEach(sender::sendMessage);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmdLabel, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        Optional<SubCommand> subCommandOptional = subCommandSet.stream().filter(subCommand -> subCommand.getLabel().equalsIgnoreCase(args[0])).findFirst();
        if (!subCommandOptional.isPresent()) {
            sendHelp(sender);
            return true;
        }
        SubCommand subCommand = subCommandOptional.get();
        if (!subCommand.checkPermission(sender)) {
            sender.sendMessage(languageConfig.getMsg("notEnoughPermission", true));
            return true;
        }
        if (args.length < subCommand.getMinArgs()) {
            sender.sendMessage(subCommand.getUsage());
            return true;
        }
        subCommand.execute(sender, args);
        return true;
    }

}
