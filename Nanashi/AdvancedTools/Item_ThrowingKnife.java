package Nanashi.AdvancedTools;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Item_ThrowingKnife extends Item
{
	public boolean addPoison;

	public Item_ThrowingKnife(int var1, boolean var2)
	{
		super(var1);
		this.maxStackSize = 16;
		this.addPoison = var2;
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
//		if(this.getUnlocalizedName().equals("item.ThrowingKnife"))
//			this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "ThrowingKnife");
//		else if(this.getUnlocalizedName().equals("item.PoisonKnife"))
//			this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "PoisonKnife");
//	}
	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		if (var3.capabilities.isCreativeMode || var3.inventory.hasItem(this.itemID))
		{
			Entity_ThrowingKnife var4 = new Entity_ThrowingKnife(var2, var3, 1.5F, this.addPoison);
			var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
			--var1.stackSize;
			Side side = FMLCommonHandler.instance().getEffectiveSide();
//			if (side == Side.SERVER)
			if(!var2.isRemote)
			{
				var2.spawnEntityInWorld(var4);
			}
		}

		return var1;
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack var1, EntityLiving var2, EntityLiving var3)
	{
		if (this.addPoison && var3 instanceof EntityPlayer)
		{
			var2.addPotionEffect(new PotionEffect(Potion.poison.id, 60, 1));
			--var1.stackSize;

			if (!((EntityPlayer)var3).inventory.addItemStackToInventory(new ItemStack(AdvancedTools.ThrowingKnife)))
			{
				var3.dropItem(AdvancedTools.ThrowingKnife.itemID, 1);
			}
		}

		return true;
	}
}
