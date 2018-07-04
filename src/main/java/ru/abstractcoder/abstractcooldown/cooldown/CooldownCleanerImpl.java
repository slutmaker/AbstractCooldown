package ru.abstractcoder.abstractcooldown.cooldown;

import java.util.UUID;

class CooldownCleanerImpl implements CooldownCleaner {

    private CooldownStorage cooldownStorage;

    CooldownCleanerImpl(CooldownStorage cooldownStorage) {
        this.cooldownStorage = cooldownStorage;
    }

    @Override
    public void clearAll() {
        cooldownStorage.getCooldownTable().clear();
    }

    @Override
    public void clearFor(UUID uuid) {
        cooldownStorage.getCooldownTable().row(uuid).clear();
    }

}
