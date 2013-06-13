package Nanashi.AdvancedTools;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUQBlazeBlade extends ItemUniqueArms
{
	private int coolTime;
	private int saftyCnt;

	protected ItemUQBlazeBlade(int var1, EnumToolMaterial var2)
	{
		super(var1, var2);
	}

	protected ItemUQBlazeBlade(int var1, EnumToolMaterial var2, int var3)
	{
		super(var1, var2);
		this.weaponStrength = var3;
	}
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void func_94581_a(IconRegister par1IconRegister)
//	{
//		this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "BlazeBlade");
//	}
	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
	{
		super.onUpdate(var1, var2, var3, var4, var5);

		if (this.coolTime > 0)
		{
			--this.coolTime;
		}
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

			if (var7 > 1.0F)
			{
				var7 = 1.0F;
			}

			Entity_BBFireBall var8 = new Entity_BBFireBall(var2, var3, var7 * 2.0F, var7 == 1.0F);

			if (!var2.isRemote)
			{
				var2.spawnEntityInWorld(var8);
			}

			if (!var3.capabilities.isCreativeMode)
			{
				this.coolTime += 20;

				if (this.coolTime > 100)
				{
					if (this.coolTime > 500)
					{
						this.coolTime = 500;
					}

					if (this.coolTime > 350)
					{
						this.saftyCnt += 3;
					}
					else if (this.coolTime > 200)
					{
						this.saftyCnt += 2;
					}
					else
					{
						++this.saftyCnt;
					}

					if (this.saftyCnt >= 3)
					{
						var3.getFoodStats().addStats(-1, 1.0f);
						this.saftyCnt = 0;
					}
				}
				else
				{
					this.saftyCnt = 0;
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
		par3List.add("Ability : Fire Ball");
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		int var4 = var3.getFoodStats().getFoodLevel();

		if (var4 > 6)
		{
			var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
		}

		return var1;
	}

	/**
	 * Returns the damage against a given entity.
	 */
	public int getDamageVsEntity(Entity var1)
	{
		var1.setFire(4);
		return super.getDamageVsEntity(var1);
	}
}
