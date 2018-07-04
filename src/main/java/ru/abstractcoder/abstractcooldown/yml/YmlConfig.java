package ru.abstractcoder.abstractcooldown.yml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class YmlConfig {

    final YamlConfiguration yml;
    private final JavaPlugin plugin;
    private final File file;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    YmlConfig(JavaPlugin plugin, String name, boolean isResource) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), name + ".yml");
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            if (isResource) {
                plugin.saveResource(name + ".yml", false);
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        yml = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getFileConfiguration() {
        return yml;
    }


    public void save() {
        try {
            yml.save(file);
        } catch (Exception e) {
            plugin.getLogger().severe("Не удалось сохранить " + file.getName());
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            yml.load(file);
        } catch (Exception e) {
            plugin.getLogger().severe("Не удалось обновить " + file.getName());
            e.printStackTrace();
        }
    }

}
