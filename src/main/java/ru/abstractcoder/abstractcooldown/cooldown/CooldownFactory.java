package ru.abstractcoder.abstractcooldown.cooldown;

import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.UUID;

public interface CooldownFactory {

    Cooldown createCooldown(UUID uuid, String cmd, Duration duration);

    default Cooldown createCooldown(Player player, String cmd, Duration duration) {
        return createCooldown(player.getUniqueId(), cmd, duration);
    }

}