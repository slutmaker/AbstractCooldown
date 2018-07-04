package ru.abstractcoder.abstractcooldown.cooldown;

import java.util.UUID;

public interface CooldownCleaner {

    void clearAll();

    void clearFor(UUID uuid);

}