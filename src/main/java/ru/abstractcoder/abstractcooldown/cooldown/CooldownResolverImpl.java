package ru.abstractcoder.abstractcooldown.cooldown;

import java.util.Optional;
import java.util.UUID;

class CooldownResolverImpl implements CooldownResolver {

    private CooldownStorage cooldownStorage;

    CooldownResolverImpl(CooldownStorage cooldownStorage) {
        this.cooldownStorage = cooldownStorage;
    }

    @Override
    public Optional<Cooldown> resolveCooldown(UUID uuid, String cmd) {
        return Optional.ofNullable(cooldownStorage.getCooldownTable().get(uuid, cmd.toLowerCase()));
    }

}
