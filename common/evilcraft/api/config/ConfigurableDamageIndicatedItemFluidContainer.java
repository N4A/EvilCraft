package evilcraft.api.config;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import evilcraft.Reference;
import evilcraft.api.item.DamageIndicatedItemFluidContainer;
import evilcraft.blocks.LiquidBlockBloodConfig;

/**
 * Item food that can hold ExtendedConfigs
 * @author Ruben Taelman
 *
 */
public abstract class ConfigurableDamageIndicatedItemFluidContainer extends DamageIndicatedItemFluidContainer implements Configurable{
    
    protected ExtendedConfig eConfig = null;
    protected boolean canPickUp = true;
    
    public static ElementType TYPE = ElementType.ITEM;
    
    protected ConfigurableDamageIndicatedItemFluidContainer(ExtendedConfig eConfig, int capacity, Fluid fluid) {
        super(eConfig.ID, capacity, fluid);
        eConfig.ID = this.itemID; // This could've changed.
        this.setConfig(eConfig);
        this.setUnlocalizedName(this.getUniqueName());
    }

    // Set a configuration for this item
    public void setConfig(ExtendedConfig eConfig) {
        this.eConfig = eConfig;
    }
    
    public String getUniqueName() {
        return "items."+eConfig.NAMEDID;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Reference.MOD_ID+":"+eConfig.NAMEDID);
    }
    
    public boolean isEntity() {
        return false;
    }
    
}