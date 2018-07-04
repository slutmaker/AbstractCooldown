package ru.abstractcoder.abstractcooldown.yml;

import org.bukkit.plugin.java.JavaPlugin;
import ru.abstractcoder.abstractcooldown.util.ColorUtils;

import java.util.HashMap;
import java.util.Map;

public class LanguageConfig extends YmlConfig {

    private final Map<String, String> messageMap = new HashMap<>();

    public LanguageConfig(JavaPlugin plugin) {
        super(plugin, "language", true);
        this.update();
    }

    @Override
    public void reload() {
        super.reload();
        this.update();
    }

    private void update() {
        messageMap.clear();
        yml.getKeys(false).stream().filter(yml::isString).forEach(key -> messageMap.put(key, ColorUtils.color(yml.getString(key))));
    }

    public String getMsg(String path, boolean needPrefix, String... replacements) {
        String msg =  messageMap.get(path);
        for (int i = 0; i < replacements.length; i++) {
            msg = msg.replace("{" + i + "}", replacements[i]);
        }
        if (needPrefix) {
            msg = messageMap.get("prefix") + msg;
        }
        return msg;
    }

}
