package ru.abstractcoder.abstractcooldown.cooldown;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface CooldownResolver {

    Optional<Cooldown> resolveCooldown(UUID uuid, String cmd);

    default Optional<Cooldown> resolveCooldown(Player player, String cmd) {
        return resolveCooldown(player.getUniqueId(), cmd);
    }

}