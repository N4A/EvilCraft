package evilcraft.render.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChest;
import net.minecraft.util.ResourceLocation;
import evilcraft.api.render.RenderItemModel;
import evilcraft.blocks.BloodChest;

/**
 * Item renderer for the {@link BloodChest}.
 * @author rubensworks
 *
 */
public class RenderItemBloodChest extends RenderItemModel {

    /**
     * Make a new instance.
     * @param model The model to render.
     * @param texture The texture to render the model with.
     */
    public RenderItemBloodChest(ModelBase model, ResourceLocation texture) {
        super(model, texture);
    }
    
    @Override
    protected void renderModel(ModelBase model) {
    	((ModelChest)model).renderAll();
    }

}