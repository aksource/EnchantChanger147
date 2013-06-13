package Nanashi.AdvancedTools;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Item_Negi extends ItemFood
{
	public Item_Negi(int var1, int var2, float var3, boolean var4)
	{
		super(var1, var2, var3, var4);
		this.setMaxDamage(87);
		this.setMaxStackSize(1);
		this.setTextureFile(AdvancedTools.itemTexture);
	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void func_94581_a(IconRegister par1IconRegister)
//	{
//		this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "NEGI");
//	}
	@Override
	public ItemStack onFoodEaten(ItemStack var1, World var2, EntityPlayer var3)
	{
		float var4 = 0.5F * (float)var1.getItemDamage() / (float)this.getMaxDamage();

		if (var4 > 0.0F)
		{
			this.setPotionEffect(Potion.hunger.id, 30, 0, var4);
		}

		if (0.65F - var4 >= var2.rand.nextFloat())
		{
			var3.addPotionEffect(new PotionEffect(Potion.heal.id, 1, 0));
		}

		return super.onFoodEaten(var1, var2, var3);
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack var1, EntityLiving var2, EntityLiving var3)
	{
		var1.damageItem(1, var3);
		return true;
	}

	public boolean onBlockDestroyed(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
	{
		var1.damageItem(2, var6);
		return true;
	}

	/**
	 * Returns the damage against a given entity.
	 */
	public int getDamageVsEntity(Entity var1)
	{
		return 3;
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack var1, List var2)
	{
		var2.add("You can eat this.");
	}
}
