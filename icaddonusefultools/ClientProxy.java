package icaddonusefultools;

import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy {
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}