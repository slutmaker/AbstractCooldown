package ru.abstractcoder.abstractcooldown.listener;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import ru.abstractcoder.abstractcooldown.cooldown.Cooldown;
import ru.abstractcoder.abstractcooldown.cooldown.CooldownFactory;
import ru.abstractcoder.abstractcooldown.cooldown.CooldownResolver;
import ru.abstractcoder.abstractcooldown.handle.ProcessableCommand;
import ru.abstractcoder.abstractcooldown.util.DurationUtils;
import ru.abstractcoder.abstractcooldown.yml.LanguageConfig;
import ru.abstractcoder.abstractcooldown.yml.MainConfig;

import java.time.Duration;
import java.util.Optional;

public class PlayerListener implements Listener {

    private final MainConfig mainConfig;
    private final LanguageConfig languageConfig;
    private final CooldownResolver cooldownResolver;
    private final CooldownFactory cooldownFactory;
    private final Permission permission;

    public PlayerListener(MainConfig mainConfig, LanguageConfig languageConfig, CooldownResolver cooldownResolver, CooldownFactory cooldownFactory, Permission permission) {
        this.mainConfig = mainConfig;
        this.languageConfig = languageConfig;
        this.cooldownResolver = cooldownResolver;
        this.cooldownFactory = cooldownFactory;
        this.permission = permission;
    }

    @EventHandler(ignoreCancelled = true)
    public void onCommandProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("abstractcooldown.bypassall")) {
            return;
        }
        String cmd = event.getMessage().substring(1).toLowerCase();
        if (cmd.length() == 0) {
            return;
        }
        ProcessableCommand processableCommand = null;
        for (ProcessableCommand pc : mainConfig.getProcessableCommandSet()) {
            String label = pc.getLabel();
            if (!label.contains(" ") || !pc.isAn(cmd)) {
                continue;
            }
            processableCommand = pc;
            break;
        }
        if (processableCommand == null) {
            for (ProcessableCommand pc : mainConfig.getProcessableCommandSet()) {
                String label = pc.getLabel();
                if (label.contains(" ") || !pc.isAn(cmd)) {
                    continue;
                }
                processableCommand = pc;
                break;
            }
        }
        if (processableCommand == null) {
            return;
        }
        if (!processableCommand.checkPermission(player)) {
            return;
        }
        if (cmd.split(" ").length - 1 < processableCommand.getMinArgsLength()) {
            return;
        }
        Optional<Cooldown> cooldownOptional = cooldownResolver.resolveCooldown(player, processableCommand.getLabel());
        if (cooldownOptional.isPresent()) {
            Cooldown cooldown = cooldownOptional.get();
            if (!cooldown.isExpire()) {
                event.setCancelled(true);
                String msg = processableCommand.getCustomMessage() == null ? languageConfig.getMsg("waitCooldown", true) : processableCommand.getCustomMessage();
                player.sendMessage(msg.replace("{time}", DurationUtils.formatDuration(Duration.ofMillis(cooldown.getTimeLeft()))));
                return;
            }
        }
        Duration duration = processableCommand.getDefaultTime();
        if (permission != null && processableCommand.getGroupsDuration() != null) {
            String group = permission.getPrimaryGroup(player).toLowerCase();
            int priority = mainConfig.getGroupPriority(group);
            if (processableCommand.getGroupsDuration().containsKey(group)) {
                duration = processableCommand.getGroupsDuration().get(group);
            } else {
                for (int i = 0; i < mainConfig.getGroupPriorities().size(); i++) {
                    if (priority < mainConfig.getGroupPriorities().size() - i || !processableCommand.getGroupsDuration().containsKey(mainConfig.getGroupPriorities().get(i))) {
                        continue;
                    }
                    duration = processableCommand.getGroupsDuration().get(mainConfig.getGroupPriorities().get(i));
                    break;
                }
            }
        }
        if (duration.isZero()) {
            return;
        }
        cooldownFactory.createCooldown(player, processableCommand.getLabel(), duration);
    }

}