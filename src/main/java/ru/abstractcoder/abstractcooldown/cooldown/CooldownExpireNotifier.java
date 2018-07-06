package ru.abstractcoder.abstractcooldown.cooldown;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.abstractcoder.abstractcooldown.handle.ProcessableCommand;
import ru.abstractcoder.abstractcooldown.yml.LanguageConfig;
import ru.abstractcoder.abstractcooldown.yml.MainConfig;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CooldownExpireNotifier extends BukkitRunnable {

    private final MainConfig mainConfig;
    private final LanguageConfig languageConfig;
    private final CooldownStorage cooldownStorage;

    CooldownExpireNotifier(Plugin plugin, MainConfig mainConfig, LanguageConfig languageConfig, CooldownStorage cooldownStorage) {
        this.mainConfig = mainConfig;
        this.languageConfig = languageConfig;
        this.cooldownStorage = cooldownStorage;
        runTaskTimerAsynchronously(plugin, 20L, 20L);
    }

    @Override
    public void run() {
        Map<String, Map<UUID, Cooldown>> columnMap = cooldownStorage.getCooldownTable().columnMap();
        mainConfig.getProcessableCommandSet().stream().filter(ProcessableCommand::isNotifyOnExpire).map(ProcessableCommand::getLabel).filter(columnMap::containsKey)
                .forEach(cmd -> columnMap.get(cmd).forEach((uuid, cooldown) -> {
                    if (!cooldown.isExpire()) {
                        return;
                    }
                    Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(p -> p.sendMessage(languageConfig.getMsg("cooldownExpireNotify", true, "/" + cmd)));
                    cooldownStorage.getCooldownTable().remove(uuid, cmd);
                }));
    }

}