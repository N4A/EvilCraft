package evilcraft.items;

import evilcraft.api.config.ExtendedConfig;

public class WerewolfFleshConfig extends ExtendedConfig {
    
    public static WerewolfFleshConfig _instance;

    public WerewolfFleshConfig() {
        super(
            4003,
            "WerewolfFlesh",
            "werewolfflesh",
            null,
            WerewolfFlesh.class
        );
    }
    
}