package VRGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerClientInfo()
	{
		MinecraftForgeClient.preloadTexture("/VRGenerator/texture/VRGenerator.png");
	}
	@Override
	public World getClientWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
}