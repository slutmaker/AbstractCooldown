package ru.abstractcoder.abstractcooldown.cooldown;

import java.util.concurrent.TimeUnit;

class CooldownImpl implements Cooldown {

    private long time;

    CooldownImpl(long time) {
        this.time = time;
    }

    @Override
    public boolean isExpire() {
        return time < System.currentTimeMillis();
    }

    @Override
    public long getTimeLeft() {
        if (this.isExpire()) {
            return -1;
        }
        return time - System.currentTimeMillis();
    }
}
