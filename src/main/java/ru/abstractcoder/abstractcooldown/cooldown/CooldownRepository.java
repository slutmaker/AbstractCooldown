package ru.abstractcoder.abstractcooldown.cooldown;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.abstractcoder.abstractcooldown.handle.ProcessableCommand;
import ru.abstractcoder.abstractcooldown.yml.MainConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.UUID;

public class CooldownRepository {

    private final CooldownStorage cooldownStorage;
    private final MainConfig mainConfig;
    private final File cdFile;
    private final Gson gson = new GsonBuilder().create();

    CooldownRepository(CooldownStorage cooldownStorage, MainConfig mainConfig, File cdFile) {
        this.cooldownStorage = cooldownStorage;
        this.mainConfig = mainConfig;
        this.cdFile = cdFile;

        if (cdFile.length() == 0) {
            return;
        }
        Type type = new TypeToken<Map<UUID, Map<String, Long>>>(){}.getType();
        Map<UUID, Map<String, Long>> tempMap;
        try {
            tempMap = gson.fromJson(new String(Files.readAllBytes(cdFile.toPath())), type);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        tempMap.forEach((uuid, map) -> map.forEach((cmd, time) -> cooldownStorage.getCooldownTable().put(uuid, cmd, new CooldownImpl(time + System.currentTimeMillis()))));
    }

    public void saveRequiredCooldowns() {
        final Table<UUID, String, Long> saveRequiredTable = HashBasedTable.create();
        final Map<String, Map<UUID, Cooldown>> columnMap = cooldownStorage.getCooldownTable().columnMap();
        mainConfig.getProcessableCommandSet().stream().filter(ProcessableCommand::isSaveOnStop).map(ProcessableCommand::getLabel).filter(columnMap::containsKey)
                .forEach(cmd -> columnMap.get(cmd).forEach((uuid, cooldown) -> {
                    if (cooldown.isExpire()) {
                        return;
                    }
                    saveRequiredTable.put(uuid, cmd, cooldown.getTimeLeft());
                }));
        try {
            Files.write(cdFile.toPath(), gson.toJson(saveRequiredTable.rowMap()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}