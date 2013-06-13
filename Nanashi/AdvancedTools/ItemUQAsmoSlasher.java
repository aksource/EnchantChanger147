package Nanashi.AdvancedTools;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUQAsmoSlasher extends ItemUniqueArms
{
	protected ItemUQAsmoSlasher(int var1, EnumToolMaterial var2)
	{
		super(var1, var2);
	}

	protected ItemUQAsmoSlasher(int var1, EnumToolMaterial var2, int var3)
	{
		super(var1, var2);
		this.weaponStrength = var3;
	}
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void func_94581_a(IconRegister par1IconRegister)
//	{
//		this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "AsmoSlasher");
//	}
	/**
	 * Returns the damage against a given entity.
	 */
	public int getDamageVsEntity(Entity var1)
	{
		if (var1 instanceof EntityCreeper)
		{
			((EntityCreeper)var1).getDataWatcher().updateObject(17, Byte.valueOf((byte)1));
		}

		if (var1 instanceof EntityPig)
		{
			((EntityPig)var1).onStruckByLightning((EntityLightningBolt)null);
			return 0;
		}
		else
		{
			return super.getDamageVsEntity(var1);
		}
	}

	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
	{
		super.onUpdate(var1, var2, var3, var4, var5);
	}

	/**
	 * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
	 */
	public void onPlayerStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4)
	{
		int var5 = var3.getFoodStats().getFoodLevel();

		if (var5 > 6)
		{
			int var6 = this.getMaxItemUseDuration(var1) - var4;
			float var7 = (float)var6 / 20.0F;
			var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

			if (var7 >= 1.0F && var5 > 7)
			{
				for (int var18 = 0; var18 < 8; ++var18)
				{
					double var9 = Math.PI * (double)var18 / 4.0D;
					double var11 = var3.posX + 5.5D * Math.sin(var9);
					double var13 = var3.posZ + 5.5D * Math.cos(var9);
					double var15 = (double)var2.getHeightValue((int)(var11 - 0.5D), (int)(var13 - 0.5D));
					EntityLightningBolt var17 = new EntityLightningBolt(var2, var11, var15, var13);

//					if (!var2.isRemote)
//					{
						var2.spawnEntityInWorld(var17);
//					}
				}

				if (!var3.capabilities.isCreativeMode)
				{
					var3.getFoodStats().addStats(-1, 1.0f);
				}
			}
			else
			{
				double var8 = var3.posX;
				double var10 = var3.posZ;
				var8 -= 6.0D * Math.sin((double)(var3.rotationYaw / 180.0F) * Math.PI);
				var10 += 6.0D * Math.cos((double)(var3.rotationYaw / 180.0F) * Math.PI);
				double var12 = (double)var2.getHeightValue((int)(var8 - 0.5D), (int)(var10 - 0.5D));
				EntityLightningBolt var14 = new EntityLightningBolt(var2, var8, var12, var10);

//				if (!var2.isRemote)
//				{
					var2.spawnEntityInWorld(var14);
//				}

				if (!var3.capabilities.isCreativeMode)
				{
					var3.getFoodStats().addStats(-1, 1.0f);
				}
			}

			var1.damageItem(1, var3);
			var3.swingItem();
			var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
		}
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack var1)
	{
		return EnumAction.bow;
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("Ability : Lightning Caller");
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		int var4 = var3.getFoodStats().getFoodLevel();

		if (var4 > 6 && var2.canBlockSeeTheSky((int)(var3.posX - 0.5D), (int)var3.posY, (int)(var3.posZ - 0.5D)))
		{
			var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
		}

		return var1;
	}
}
