package evilcraft.api.config;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import evilcraft.EvilCraft;
import evilcraft.Reference;
import evilcraft.api.Helpers;

/**
 * Block that can hold ExtendedConfigs
 * @author Ruben Taelman
 *
 */
public abstract class ConfigurableBlockContainer extends BlockContainer implements Configurable{
    
    protected ExtendedConfig eConfig = null;
    
    public static ElementType TYPE = ElementType.BLOCKCONTAINER;
    
    protected Random random;
    private Class<? extends TileEntity> tileEntity;
    
    public ConfigurableBlockContainer(ExtendedConfig eConfig, Material material, Class<? extends TileEntity> tileEntity) {
        super(eConfig.ID, material);
        eConfig.ID = this.blockID; // This could've changed.
        this.setConfig(eConfig);
        this.setUnlocalizedName(this.getUniqueName());
        this.random = new Random();
        this.tileEntity = tileEntity;
        setHardness(5F);
        setStepSound(Block.soundAnvilFootstep);
    }
    
    public Class<? extends TileEntity> getTileEntity() {
        return this.tileEntity;
    }

    // Set a configuration for this item
    @Override
    public void setConfig(ExtendedConfig eConfig) {
        this.eConfig = eConfig;
    }
    
    @Override
    public String getUniqueName() {
        return "blocks."+eConfig.NAMEDID;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(getTextureName());
    }
    
    @Override
    public String getTextureName() {
        return Reference.MOD_ID+":"+eConfig.NAMEDID;
    }
    
    @Override
    public boolean isEntity() {
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity(World var1) {
        try {
            return tileEntity.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        Helpers.preDestroyBlock(world, x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }

}
