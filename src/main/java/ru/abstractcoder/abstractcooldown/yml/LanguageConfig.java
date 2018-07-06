package ru.abstractcoder.abstractcooldown.yml;

import org.bukkit.plugin.java.JavaPlugin;
import ru.abstractcoder.abstractcooldown.util.ColorUtils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LanguageConfig extends YmlConfig {

    private Map<String, String> messageMap;

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
        messageMap = yml.getKeys(false).stream().filter(yml::isString).collect(Collectors.toMap(Function.identity(), s -> ColorUtils.color(yml.getString(s))));
    }

    public String getMsg(String path, boolean needPrefix, String... replacements) {
        String msg = messageMap.get(path);
        if (replacements.length > 0) {
            msg = MessageFormat.format(msg, (Object[]) replacements);
        }
        if (needPrefix) {
            msg = messageMap.get("prefix") + msg;
        }
        return msg;
    }

}