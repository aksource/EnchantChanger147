package VRGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	public void registerClientInfo(){}
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,int x, int y, int z) {
		if(ID == VisibleRayGenerator.artificialSunGuiID)
		{
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof CESolarTileEntity)
			{
				return new CESolarContainer(player, (CESolarTileEntity) tileEntity);
			}
			else return null;
		}
		else return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == VisibleRayGenerator.artificialSunGuiID)
		{
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity != null && tileEntity instanceof CESolarTileEntity)
			{
				return new CESolarGui(player, (CESolarTileEntity) tileEntity);
			}
			else return null;
		}
		else return null;
	}
	public World getClientWorld()
	{
		return null;
	}
}