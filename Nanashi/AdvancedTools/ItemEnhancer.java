package Nanashi.AdvancedTools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEnhancer extends Item
{
    private int id;

    protected ItemEnhancer(int var1)
    {
        super(var1);
        this.id = var1;
		this.setTextureFile(AdvancedTools.itemTexture);
    }
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void func_94581_a(IconRegister par1IconRegister)
//    {
//    	if(id == AdvancedTools.ItemID_INDEX )
//    		this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "EnhancerR");
//    	else
//    		this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "EnhancerB");
//    }
    public boolean hasEffect(ItemStack var1)
    {
        return true;
    }

    /**
     * Return an item rarity from EnumRarity
     */
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1)
    {
        if(this.id == AdvancedTools.ItemID_INDEX)
        	return EnumRarity.uncommon;
        else
        	return EnumRarity.rare;
    }
}
