package ru.abstractcoder.abstractcooldown.cooldown;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.UUID;

public class CooldownStorage {

    private final Table<UUID, String, Cooldown> cooldownTable = HashBasedTable.create();

    Table<UUID, String, Cooldown> getCooldownTable() {
        return cooldownTable;
    }

}