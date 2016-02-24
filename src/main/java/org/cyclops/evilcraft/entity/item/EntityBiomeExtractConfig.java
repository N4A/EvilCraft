package org.cyclops.evilcraft.entity.item;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.cyclops.cyclopscore.config.extendedconfig.EntityConfig;
import org.cyclops.evilcraft.EvilCraft;
import org.cyclops.evilcraft.core.client.render.RenderThrowable;
import org.cyclops.evilcraft.item.BiomeExtract;

/**
 * Config for the {@link EntityWeatherContainer}.
 * @author rubensworks
 *
 */
public class EntityBiomeExtractConfig extends EntityConfig {

    /**
     * The unique instance.
     */
    public static EntityBiomeExtractConfig _instance;

    /**
     * Make a new instance.
     */
    public EntityBiomeExtractConfig() {
        super(
                EvilCraft._instance,
        	true,
            "entityBiomeExtract",
            null,
            EntityBiomeExtract.class
        );
    }
    
    @Override
    public boolean sendVelocityUpdates() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected Render getRender(RenderManager renderManager, RenderItem renderItem) {
        return new RenderThrowable(renderManager, BiomeExtract.getInstance(), renderItem);
    }
}