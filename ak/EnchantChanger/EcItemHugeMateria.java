package ak.EnchantChanger;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EcItemHugeMateria extends Item
{
	public EcItemHugeMateria(int par1)
	{
		super(par1);
		this.setTextureFile(EnchantChanger.EcSprites);
	}
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		switch(par7)
		{
		case 0:par5-=3;break;
		case 1:par5++;break;
		case 2:par6--;break;
		case 3:par6++;break;
		case 4:par4--;break;
		case 5:par4++;break;
		}
		Block hugemateria = EnchantChanger.HugeMateria;
		if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 2, par6, par7, par1ItemStack))
		{
			if (!hugemateria.canPlaceBlockAt(par3World, par4, par5, par6) || !hugemateria.canPlaceBlockAt(par3World, par4, par5 + 1, par6) || !hugemateria.canPlaceBlockAt(par3World, par4, par5 + 2, par6))
			{
				return false;
			}
			else
			{
				par3World.editingBlocks = true;
				par3World.setBlockAndMetadataWithNotify(par4, par5, par6, hugemateria.blockID, 0);
				par3World.setBlockAndMetadataWithNotify(par4, par5 + 1, par6, hugemateria.blockID, 1);
				par3World.setBlockAndMetadataWithNotify(par4, par5 + 2, par6, hugemateria.blockID, 2);
				par3World.editingBlocks = false;
				par3World.notifyBlocksOfNeighborChange(par4, par5, par6, hugemateria.blockID);
				par3World.notifyBlocksOfNeighborChange(par4, par5 + 1, par6, hugemateria.blockID);
				par3World.notifyBlocksOfNeighborChange(par4, par5 + 2, par6, hugemateria.blockID);
				--par1ItemStack.stackSize;
				return true;
			}
		}
		else
		{
			return false;
		}
	}
//	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
//	{
//		par3List.add(new ItemStack(this));
//	}
}