package ak.MultiToolHolders.Client;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import ak.MultiToolHolders.CommonProxy;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;

public class ClientProxy extends CommonProxy
{
	public static KeyBinding OpenKey = new KeyBinding("Key.openToolHolder",Keyboard.KEY_F);
	public static KeyBinding NextKey = new KeyBinding("Key.nextToolHolder",Keyboard.KEY_T);
	public static KeyBinding PrevKey = new KeyBinding("Key.prevToolHolder",Keyboard.KEY_R);
	public static KeyBinding[] Keys = new KeyBinding[]{OpenKey, NextKey,PrevKey};
	public static boolean[] repeat = new boolean[]{false, false, false};
	@Override
	public void registerClientInformation()
	{
//		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
//		TickRegistry.registerTickHandler(new CommonTickHandler(), Side.SERVER);
		KeyBindingRegistry.registerKeyBinding(new MTHKeyHandler(Keys, repeat));
		MinecraftForgeClient.preloadTexture("/ak/MultiToolHolders/textures/items.png");
	}

	@Override
	public void registerTileEntitySpecialRenderer()
	{
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPEnchantmentTable.class, new RenderPEnchantmentTable());
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}