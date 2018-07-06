package ru.abstractcoder.abstractcooldown.cooldown;

import org.bukkit.plugin.Plugin;
import ru.abstractcoder.abstractcooldown.yml.LanguageConfig;
import ru.abstractcoder.abstractcooldown.yml.MainConfig;

import java.io.File;
import java.io.IOException;

public class CooldownManagementFacade {

    private final CooldownFactory cooldownFactory;
    private final CooldownResolver cooldownResolver;
    private final CooldownCleaner cooldownCleaner;
    private final CooldownRepository cooldownRepository;
    private final CooldownExpireNotifier cooldownExpireNotifier;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public CooldownManagementFacade(Plugin plugin, MainConfig mainConfig, LanguageConfig languageConfig) {
        File cdFile = new File(plugin.getDataFolder(), "data.json");
        if (!cdFile.exists()) {
            try {
                cdFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CooldownStorage cooldownStorage = new CooldownStorage();
        cooldownRepository = new CooldownRepository(cooldownStorage, mainConfig, cdFile);
        cooldownFactory = new CooldownFactoryImpl(cooldownStorage);
        cooldownResolver = new CooldownResolverImpl(cooldownStorage);
        cooldownCleaner = new CooldownCleanerImpl(cooldownStorage);
        cooldownExpireNotifier = new CooldownExpireNotifier(plugin, mainConfig, languageConfig, cooldownStorage);
    }

    public CooldownFactory getCooldownFactory() {
        return cooldownFactory;
    }

    public CooldownResolver getCooldownResolver() {
        return cooldownResolver;
    }

    public CooldownCleaner getCooldownCleaner() {
        return cooldownCleaner;
    }

    public CooldownRepository getCooldownRepository() {
        return cooldownRepository;
    }

    public CooldownExpireNotifier getCooldownExpireNotifier() {
        return cooldownExpireNotifier;
    }
}