package ru.abstractcoder.abstractcooldown.cooldown;

public class CooldownManagementFacade {

    private final CooldownFactory cooldownFactory;
    private final CooldownResolver cooldownResolver;
    private final CooldownCleaner cooldownCleaner;

    public CooldownManagementFacade() {
        CooldownStorage cooldownStorage = new CooldownStorage();
        cooldownFactory = new CooldownFactoryImpl(cooldownStorage);
        cooldownResolver = new CooldownResolverImpl(cooldownStorage);
        cooldownCleaner = new CooldownCleanerImpl(cooldownStorage);
    }

    public CooldownFactory getCooldownFactory() {
        return cooldownFactory;
    }

    public CooldownResolver getCooldownResolver() {
        return cooldownResolver;
    }

    public CooldownCleaner getCooldownCleaner() {
        return cooldownCleaner;
    }

}