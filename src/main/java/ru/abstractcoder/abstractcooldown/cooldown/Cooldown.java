package ru.abstractcoder.abstractcooldown.cooldown;

public interface Cooldown {

    boolean isExpire();

    long getTimeLeft();

}
