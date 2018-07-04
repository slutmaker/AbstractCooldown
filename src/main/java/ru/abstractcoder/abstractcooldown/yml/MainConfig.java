package ru.abstractcoder.abstractcooldown.yml;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import ru.abstractcoder.abstractcooldown.handle.ProcessableCommand;
import ru.abstractcoder.abstractcooldown.handle.ProcessableCommandFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainConfig extends YmlConfig {

    private final ProcessableCommandFactory processableCommandFactory;
    private final Set<ProcessableCommand> processableCommandSet = new HashSet<>();
    private List<String> groupPriorities;

    public MainConfig(JavaPlugin plugin, ProcessableCommandFactory processableCommandFactory) {
        super(plugin, "config", true);
        this.processableCommandFactory = processableCommandFactory;
        this.update();
    }

    private void update() {
        if (isUsingVault()) {
            groupPriorities = yml.isList("groupPriorities") ? yml.getStringList("groupPriorities").stream().map(String::toLowerCase).collect(Collectors.toList()) : new ArrayList<>();
        }
        processableCommandSet.clear();
        ConfigurationSection commandsSection = yml.getConfigurationSection("commands");
        commandsSection.getKeys(false).forEach(key -> processableCommandSet.add(processableCommandFactory.createProcessableCommand(key, commandsSection.getConfigurationSection(key))));
    }

    @Override
    public void reload() {
        super.reload();
        this.update();
    }

    public Set<ProcessableCommand> getProcessableCommandSet() {
        return processableCommandSet;
    }

    public boolean isUsingVault() {
        return yml.getBoolean("useVault");
    }

    public List<String> getGroupPriorities() {
        return groupPriorities;
    }

    public int getGroupPriority(String group) {
        for (int i = 0; i < groupPriorities.size(); i++) {
            if (groupPriorities.get(i).equals(group)) {
                return groupPriorities.size() - i;
            }
        }
        return 0;
    }

}