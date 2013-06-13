package Nanashi.AdvancedTools;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemUniqueArms extends ItemSword
{
    protected int weaponStrength = -1;

    protected ItemUniqueArms(int var1, EnumToolMaterial var2)
    {
        super(var1, var2);
		this.setTextureFile(AdvancedTools.itemTexture);
    }

    protected ItemUniqueArms(int var1, EnumToolMaterial var2, int var3)
    {
        super(var1, var2);
        this.weaponStrength = var3;
		this.setTextureFile(AdvancedTools.itemTexture);
    }
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void func_94581_a(IconRegister par1IconRegister)
//    {
//    	if(this.getUnlocalizedName().equals("item.SmashBat"))
//    		this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "SmashBat");
//    	else if(this.getUnlocalizedName().equals("item.InfiniteSword"))
//    		this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "Infinitysword");
//    	else if(this.getUnlocalizedName().equals("item.GenocideBlade"))
//    		this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "GenocideBlade");
//    }
    /**
     * Called when item is crafted/smelted. Used only by maps so far.
     */
    public void onCreated(ItemStack var1, World var2, EntityPlayer var3)
    {
        super.onCreated(var1, var2, var3);

        if (var1.getItem() == AdvancedTools.SmashBat)
        {
            var1.addEnchantment(Enchantment.knockback, 10);
        }
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
    {
        super.onUpdate(var1, var2, var3, var4, var5);

        if (!var1.hasTagCompound())
        {
            if (var1.getItem() == AdvancedTools.SmashBat)
            {
                var1.addEnchantment(Enchantment.knockback, 10);
            }
        }
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity var1)
    {
        return this.weaponStrength < 0 ? super.getDamageVsEntity(var1) : this.weaponStrength;
    }
}
