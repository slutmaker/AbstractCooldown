package ru.abstractcoder.abstractcooldown.cooldown;

import java.time.Duration;
import java.util.UUID;

class CooldownFactoryImpl implements CooldownFactory {

    private CooldownStorage cooldownStorage;

    CooldownFactoryImpl(CooldownStorage cooldownStorage) {
        this.cooldownStorage = cooldownStorage;
    }

    @Override
    public Cooldown createCooldown(UUID uuid, String cmd, Duration duration) {
        Cooldown cooldown = new CooldownImpl(System.currentTimeMillis() + duration.toMillis());
        cooldownStorage.getCooldownTable().put(uuid, cmd, cooldown);
        return cooldown;
    }

}
