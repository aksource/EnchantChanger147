package compactengine;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import buildcraft.energy.BlockEngine;
import buildcraft.energy.TileEngine;

public class BlockCompactEngine extends BlockEngine
{
	public BlockCompactEngine(int i)
	{
		super(i);
		this.setResistance(10.0f);	//爆破耐性は丸石と同じ
		/*	メモ
			爆発力＜爆破耐性/5になると、自分の爆発で自分が壊れなくなるため、無限爆発が発生する。
			意図的に発生させても面白いが、破壊以外に止める方法がないのが困る。
		*/
	}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileCompactEngine();
	}

	@Override
	public void getSubBlocks(int blockid, CreativeTabs par2CreativeTabs, List arraylist)
	{
		arraylist.add(new ItemStack(this, 1, 0));
		arraylist.add(new ItemStack(this, 1, 1));
		arraylist.add(new ItemStack(this, 1, 2));
		if(CompactEngine.isAddCompactEngine512and2048)
		{
			arraylist.add(new ItemStack(this, 1, 3));
			arraylist.add(new ItemStack(this, 1, 4));
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
    {
		TileEngine tileengine = (TileEngine)world.getBlockTileEntity(x, y, z);

		if(entityplayer.getCurrentEquippedItem() != null )
		{
			int itemID = entityplayer.getCurrentEquippedItem().getItem().itemID;

			//クリエイティブならブレイズロッドでエンジンを温める
			if (entityplayer.capabilities.isCreativeMode && itemID == Item.blazeRod.itemID)
			{
				tileengine.engine.energy += tileengine.engine.maxEnergy / 8;
				return true;
			}
		}

		return super.onBlockActivated(world, x, y, z, entityplayer, par6, par7, par8, par9);

    }

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int side)
	{
	}

	//必要ないかも？
	@Override
    public String getBlockName()
    {
        return "tile.CompactEngineWood";
    }
}
