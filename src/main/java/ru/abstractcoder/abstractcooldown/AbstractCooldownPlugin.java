package ru.abstractcoder.abstractcooldown;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.abstractcoder.abstractcooldown.command.AbstractCooldownCommand;
import ru.abstractcoder.abstractcooldown.cooldown.CooldownCleaner;
import ru.abstractcoder.abstractcooldown.cooldown.CooldownManagementFacade;
import ru.abstractcoder.abstractcooldown.handle.ProcessableCommandFactory;
import ru.abstractcoder.abstractcooldown.listener.PlayerListener;
import ru.abstractcoder.abstractcooldown.yml.LanguageConfig;
import ru.abstractcoder.abstractcooldown.yml.MainConfig;

public class AbstractCooldownPlugin extends JavaPlugin {

    private CooldownCleaner cooldownCleaner;

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();
        ProcessableCommandFactory processableCommandFactory = new ProcessableCommandFactory();
        MainConfig mainConfig = new MainConfig(this, processableCommandFactory);
        LanguageConfig languageConfig = new LanguageConfig(this);
        CooldownManagementFacade cmf = new CooldownManagementFacade();
        cooldownCleaner = cmf.getCooldownCleaner();
        Permission permission = null;
        if (mainConfig.isUsingVault() && pluginManager.isPluginEnabled("Vault")) {
            RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
            if (permissionProvider != null) {
                permission = permissionProvider.getProvider();
            }
        }
        getCommand("abstractcooldown").setExecutor(new AbstractCooldownCommand(languageConfig, mainConfig, cmf));
        pluginManager.registerEvents(new PlayerListener(mainConfig, languageConfig, cmf.getCooldownResolver(), cmf.getCooldownFactory(), permission), this);
    }

    @Override
    public void onDisable() {
        cooldownCleaner.clearAll();
    }

}