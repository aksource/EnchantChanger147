package icaddonusefultools;

import ic2.api.ElectricItem;
import ic2.api.IElectricItem;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemElFire extends Item implements IElectricItem
{
	private int maxCharge;
	private int tier;
	private int transferLimit;
	private int operationEnergyCost;
	public ItemElFire(int id)
	{
		super(id);
		this.maxStackSize = 1;
		this.setMaxDamage(13);
		maxCharge = 10000;
		tier = 1;
		transferLimit = 100;
		this.operationEnergyCost = 50;
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setTextureFile(ICUsefulTools.itemTex);
	}
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if (!ElectricItem.use(par1ItemStack, this.operationEnergyCost, par2EntityPlayer)) return false;
		if (par7 == 0)
		{
			--par5;
		}

		if (par7 == 1)
		{
			++par5;
		}

		if (par7 == 2)
		{
			--par6;
		}

		if (par7 == 3)
		{
			++par6;
		}

		if (par7 == 4)
		{
			--par4;
		}

		if (par7 == 5)
		{
			++par4;
		}

		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
		{
			return false;
		}
		else
		{
			int var11 = par3World.getBlockId(par4, par5, par6);

			if (var11 == 0)
			{
				par3World.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				par3World.setBlockWithNotify(par4, par5, par6, Block.fire.blockID);
			}

//			par1ItemStack.damageItem(1, par2EntityPlayer);
			return true;
		}
	}
	@Override
	public boolean isRepairable()
	{
		return false;
	}
	@Override
	public boolean canProvideEnergy() {
		return false;
	}

	@Override
	public int getChargedItemId() {
		return this.itemID;
	}

	@Override
	public int getEmptyItemId() {
		return this.itemID;
	}

	@Override
	public int getMaxCharge() {
		return this.maxCharge;
	}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public int getTransferLimit() {
		return this.transferLimit;
	}
}