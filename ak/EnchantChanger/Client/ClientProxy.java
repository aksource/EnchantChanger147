package ak.EnchantChanger.Client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.input.Keyboard;

import ak.EnchantChanger.CommonProxy;
import ak.EnchantChanger.CommonTickHandler;
import ak.EnchantChanger.EcEntityApOrb;
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
    public static KeyBinding MagicKey = new KeyBinding("Key.EcMagic",Keyboard.KEY_V);
	@Override
	public void registerRenderInformation()
	{
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/items.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/terrain.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia0.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia1.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia3.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia4.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia5.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia6.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia7.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia8.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia9.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia10.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia11.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia12.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia13.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia14.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materia15.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/HugeMateriaContainer.png");
		MinecraftForgeClient.preloadTexture("/ak/EnchantChanger/mod_EnchantChanger/gui/materializer.png");
		RenderingRegistry.registerEntityRenderingHandler(EcEntityExExpBottle.class, new EcRenderItemThrowable(EnchantChanger.ItemExExpBottle.getIconFromDamage(0),0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EcEntityMeteo.class, new EcRenderItemThrowable(22,EnchantChanger.MeteoSize));
		RenderingRegistry.registerEntityRenderingHandler(EcEntityApOrb.class, new EcRenderApOrb());
		//		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
		TickRegistry.registerTickHandler(new CommonTickHandler(), Side.SERVER);
		KeyBindingRegistry.registerKeyBinding(new EcKeyHandler(new KeyBinding[]{MagicKey},new boolean[]{false}));
		MinecraftForgeClient.registerItemRenderer(EnchantChanger.SephirothSwordItemID, (IItemRenderer) EnchantChanger.ItemSephirothSword);
		MinecraftForgeClient.registerItemRenderer(EnchantChanger.ZackSwordItemID, (IItemRenderer) EnchantChanger.ItemZackSword);
		MinecraftForgeClient.registerItemRenderer(EnchantChanger.FirstSwordItemID, (IItemRenderer) EnchantChanger.ItemCloudSwordCore);
		MinecraftForgeClient.registerItemRenderer(EnchantChanger.CloudSwordItemID, (IItemRenderer) EnchantChanger.ItemCloudSword);
		MinecraftForgeClient.registerItemRenderer(EnchantChanger.UltimateWeaponItemID, (IItemRenderer) EnchantChanger.ItemUltimateWeapon);
		MinecraftForgeClient.registerItemRenderer(EnchantChanger.ImitateSephSwordID, (IItemRenderer)EnchantChanger.ItemImitateSephirothSword);
		MinecraftForgeClient.registerItemRenderer(EnchantChanger.MateriaID, (IItemRenderer) EnchantChanger.ItemMat);
		MinecraftForgeClient.registerItemRenderer(EnchantChanger.MasterMateriaID, (IItemRenderer) EnchantChanger.MasterMateria);
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