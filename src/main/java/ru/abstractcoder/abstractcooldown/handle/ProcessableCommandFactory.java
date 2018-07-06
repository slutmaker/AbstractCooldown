package ru.abstractcoder.abstractcooldown.handle;

import org.bukkit.configuration.ConfigurationSection;
import ru.abstractcoder.abstractcooldown.util.DurationUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessableCommandFactory {

    public ProcessableCommand createProcessableCommand(final String label, final ConfigurationSection section) {
        final boolean checkAliases = section.getBoolean("checkAliases", false);
        final boolean notifyOnExpire = section.getBoolean("notifyOnExpire", false);
        final boolean saveOnStop = section.getBoolean("saveOnStop", false);
        final List<String> similarCommands = section.isList("similarCommands") ? section.getStringList("similarCommands") : null;
        final String permission = section.getString("permission", null);
        final int minArgsLength = section.getInt("minArgsLength", 0);
        final String customMessage = section.getString("customMessage", null);
        final Map<String, Duration> groupsDuration = section.isConfigurationSection("groupsTime") ? new HashMap<>() : null;
        final Duration defaultDuration = DurationUtils.parseDuration(section.getString("defaultTime"));
        if (groupsDuration != null) {
            final ConfigurationSection groupsTime = section.getConfigurationSection("groupsTime");
            groupsTime.getKeys(false).forEach(key -> groupsDuration.put(key.toLowerCase(), DurationUtils.parseDuration(groupsTime.getString(key))));
        }
        return new ProcessableCommand(label, checkAliases, saveOnStop, notifyOnExpire, similarCommands, permission, minArgsLength, customMessage, defaultDuration, groupsDuration);
    }

}
