package VRGenerator;

import ic2.api.Items;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import compactengine.CompactEngine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CESolarBlock extends BlockContainer
{
	public CESolarBlock(int i)
	{
		super(i, Material.iron);
		setHardness(3F);
		setStepSound(soundMetalFootstep);
		blockIndexInTexture = 16;
	}

	@Override
	public String getTextureFile()
	{
		return VisibleRayGenerator.texture;
	}

	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int side)
	{
		return getBlockTextureFromSideAndMetadata(side, iblockaccess.getBlockMetadata(i,j,k));
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		int tex = (side > 2) ? 2: side;
		return blockIndexInTexture + meta + tex * 16;
	}

	@Override
	public int getBlockTextureFromSide(int side)
	{
		return getBlockTextureFromSideAndMetadata(side, 0);
	}

	@Override
	public int idDropped(int i, Random random, int j)
	{
		return Items.getItem("machine").itemID;
	}

	@Override
	public int damageDropped(int i)
	{
		return Items.getItem("machine").getItemDamage();
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}

//	@Override
//	public TileEntityBlock getBlockEntity(int i)
//	{
//		int solar = CESolarTileEntity.power.length;
//		if(i >= 0 && i < solar)
//		{
//			return new CESolarTileEntity(i);
//		}
//		else if(i < solar + CEGeneratorTileEntity.power.length)
//		{
//			return new CEGeneratorTileEntity(i - solar);
//		}
//		return null;
//	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile == null)
			return false;
		else if (entityplayer.getCurrentEquippedItem() != null)
		{
			if(entityplayer.getCurrentEquippedItem().itemID == Items.getItem("wrench").itemID
					|| entityplayer.getCurrentEquippedItem().itemID == Items.getItem("electricWrench").itemID)
			{
		    	if(tile instanceof CESolarTileEntity)
		    	{
		            for(int l = 0; l < ((CESolarTileEntity)tile).getSizeInventory(); l++)
		            {
		               ItemStack ist = ((CESolarTileEntity)tile).getStackInSlot(l);
		               if(ist == null || ist.stackSize <= 0)
		               {
		                  continue;
		               }
		               EntityItem eit = new EntityItem(world, (double)x+0.5D, (double)y+0.5D, (double)z+0.5D, ist);
		               if(!world.isRemote)
		            	   world.spawnEntityInWorld(eit);
		            }
		    	}
				return false;
			}
			else if(entityplayer.getCurrentEquippedItem().itemID == Items.getItem("ecMeter").itemID)
			{
				return false;
			}
			else if(VisibleRayGenerator.isCE && entityplayer.getCurrentEquippedItem().itemID == CompactEngine.itemID_energyChecker)
			{
				if(tile instanceof CESolarTileEntity)
				{
					CESolarTileEntity ce = (CESolarTileEntity)tile;
					//VisibleRayGenerator.addChat("lv%d pr%d max%d st%d", ce.level, ce.production, ce.maxStorage, ce.storage);
					VisibleRayGenerator.addChat("level:%d  production:%d EU/t", ce.level, ce.production);
				}
				else if(tile instanceof CEGeneratorTileEntity)
				{
					CEGeneratorTileEntity ce = (CEGeneratorTileEntity)tile;
					VisibleRayGenerator.addChat("level:%d  production:%d EU/t", ce.level, ce.production);
				}
				return false;
			}
			else
			{
				if(tile instanceof CEGeneratorTileEntity) return false;
				if(entityplayer.isSneaking())return false;
				entityplayer.openGui(VisibleRayGenerator.instance, VisibleRayGenerator.artificialSunGuiID, world, x, y, z);
				return true;
			}
		}
		else
		{
			if(tile instanceof CEGeneratorTileEntity) return false;
			if(entityplayer.isSneaking())return false;
			entityplayer.openGui(VisibleRayGenerator.instance, VisibleRayGenerator.artificialSunGuiID, world, x, y, z);
			return true;
		}
	}
    public void breakBlock(World world, int x, int y, int z, int id, int meta)
    {
    	CETileEntityGenerator tile = (CETileEntityGenerator) world.getBlockTileEntity(x, y, z);
    	if(tile != null && tile instanceof CESolarTileEntity)
    	{
            for(int l = 0; l < ((CESolarTileEntity)tile).getSizeInventory(); l++)
            {
               ItemStack ist = ((CESolarTileEntity)tile).getStackInSlot(l);
               if(ist == null || ist.stackSize <= 0)
               {
                  continue;
               }
               EntityItem eit = new EntityItem(world, (double)x+0.5D, (double)y+0.5D, (double)z+0.5D, ist);
               if(!world.isRemote)
            	   world.spawnEntityInWorld(eit);
            }
    	}
        super.breakBlock(world, x, y, z, id, meta);
    }
    @SideOnly(Side.CLIENT)

    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List arraylist)
    {
		for(int i = 0; i < CESolarTileEntity.power.length + CEGeneratorTileEntity.power.length; i++)
		{
			arraylist.add(new ItemStack(this, 1, i));
		}
    }
//	@Override
//	public void addCreativeItems(ArrayList arraylist)
//	{
//		for(int i = 0; i < CESolarTileEntity.power.length + CEGeneratorTileEntity.power.length; i++)
//		{
//			arraylist.add(new ItemStack(this, 1, i));
//		}
//	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return this.createNewTileEntity(var1, 0);
	}
	@Override
	public TileEntity createNewTileEntity(World world, int i)
	{
		int solar = CESolarTileEntity.power.length;
		if(i >= 0 && i < solar)
		{
			return new CESolarTileEntity(i);
		}
		else if(i < solar + CEGeneratorTileEntity.power.length)
		{
			return new CEGeneratorTileEntity(i - solar);
		}
		return null;
	}

}
