package compactengine;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.core.Position;
import buildcraft.energy.ItemEngine;
import buildcraft.energy.TileEngine;

public class ItemCompactEngine extends ItemEngine
{
	public ItemCompactEngine(int i)
	{
		super(i);
	}

    @Override
	public String getItemNameIS(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage()+1;
		return (meta > 5) ? null: "tile.CompactEngineWood.level_" + meta;
	}

	//1.4.7ではなぜかgetItemNameISをローカライズできなかったので、表示名を直接変更して対応（仮実装）
	@Override
	public String getItemDisplayName(ItemStack par1ItemStack)
	{
		return (StringTranslate.getInstance().translateNamedKey(this.getLocalItemName(par1ItemStack)));
	}

	//エンジン設置時の動作を改善
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		if (!world.setBlockAndMetadataWithNotify(x, y, z, this.getBlockID(), metadata))
		{
			return false;
		}

		TileEngine tileengine = (TileEngine)world.getBlockTileEntity(x, y, z);
		ForgeDirection o = ForgeDirection.values()[side];
		Position pos = new Position(tileengine.xCoord, tileengine.yCoord, tileengine.zCoord, o);
		pos.moveForwards(1);
		TileEntity tile = world.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);
		if (!tileengine.isPoweredTile(tile))
		{
			tileengine.switchOrientation();
		}

		if (world.getBlockId(x, y, z) == this.getBlockID())
		{
			Block.blocksList[this.getBlockID()].onBlockPlacedBy(world, x, y, z, player);
			Block.blocksList[this.getBlockID()].onPostBlockPlaced(world, x, y, z, metadata);
		}
		return true;
	}
}
