package ak.EnchantChanger.Client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import ak.EnchantChanger.CommonProxy;
import ak.EnchantChanger.CommonTickHandler;
import ak.EnchantChanger.EcEntityExExpBottle;
import ak.EnchantChanger.EcEntityMeteo;
import ak.EnchantChanger.EcTileEntityHugeMateria;
import ak.EnchantChanger.EnchantChanger;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{
    public static KeyBinding MagicKey = new KeyBinding("EcMagicKey",88);
    public static KeyBinding[] EcKeys = new KeyBinding[]{MagicKey};
    public static boolean[] EcKeysRepeat = new boolean[]{false};
	@Override
	public void registerRenderInformation()
	{
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/items.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/terrain.png");
		RenderingRegistry.registerEntityRenderingHandler(EcEntityExExpBottle.class, new EcRenderItemThrowable(EnchantChanger.ItemExExpBottle.getIconFromDamage(0),0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EcEntityMeteo.class, new EcRenderItemThrowable(22,EnchantChanger.MeteoSize));
//		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
		TickRegistry.registerTickHandler(new CommonTickHandler(), Side.SERVER);
		KeyBindingRegistry.registerKeyBinding(new EcKeyHandler(EcKeys,EcKeysRepeat));
	}

	@Override
	public void registerTileEntitySpecialRenderer()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(EcTileEntityHugeMateria.class, new EcRenderHugeMateria());
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}