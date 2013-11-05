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

    public void onCreated(ItemStack var1, World var2, EntityPlayer var3)
    {
        super.onCreated(var1, var2, var3);

        if (var1.getItem() == AdvancedTools.SmashBat)
        {
            var1.addEnchantment(Enchantment.knockback, 10);
        }
    }

    public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
    {
        super.onUpdate(var1, var2, var3, var4, var5);

        if (!var1.hasTagCompound() || var1.getEnchantmentTagList() == null)
        {
            if (var1.getItem() == AdvancedTools.SmashBat)
            {
                var1.addEnchantment(Enchantment.knockback, 10);
            }
        }
    }


    public int getDamageVsEntity(Entity var1)
    {
        return this.weaponStrength < 0 ? super.getDamageVsEntity(var1) : this.weaponStrength;
    }
}
