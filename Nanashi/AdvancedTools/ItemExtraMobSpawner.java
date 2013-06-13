package Nanashi.AdvancedTools;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

public class ItemExtraMobSpawner extends Item
{
	public ItemExtraMobSpawner(int par1)
	{
		super(par1);
		setIconCoord(12, 0);
		setHasSubtypes(true);
	}
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
		String str = "";
		switch(par1ItemStack.getItemDamage())
		{
		case 0:str="HighSkeleton";break;
		case 1:str="SkeletonSniper";break;
		case 2:str="ZombieWarrior";break;
		case 3:str="FireZombie";break;
		case 4:str="HighSpeedCreeper";break;
		case 5:str="GoldCreeper";break;
		case 6:str="Skeleton";break;
		default:;
		}
		if(str !="")
		{
			ModContainer mc = FMLCommonHandler.instance().findContainerFor(AdvancedTools.instance);
			String entityModName = String.format("%s.%s", mc.getModId(), str);
			par3World.editingBlocks = true;
			par3World.setBlockAndMetadataWithNotify(par4, par5, par6, Block.mobSpawner.blockID, 0);
			TileEntity te = par3World.getBlockTileEntity(par4, par5, par6);
			((TileEntityMobSpawner)te).setMobID(entityModName);
			par3World.editingBlocks = false;
			return true;
		}
		else
			return false;
    }
	public String getItemNameIS(ItemStack par1ItemStack)
	{
		String str = "";
		switch(par1ItemStack.getItemDamage())
		{
		case 0:str="HighSkeleton";break;
		case 1:str="SkeletonSniper";break;
		case 2:str="ZombieWarrior";break;
		case 3:str="FireZombie";break;
		case 4:str="HighSpeedCreeper";break;
		case 5:str="GoldCreeper";break;
		default:;
		}
		return str + "Spawner";
	}
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < 6; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
	}
}