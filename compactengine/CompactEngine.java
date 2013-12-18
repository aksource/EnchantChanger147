package compactengine;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraftforge.common.Configuration;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftEnergy;
import buildcraft.BuildCraftSilicon;
import buildcraft.BuildCraftTransport;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="CompactEngine", name="CompactEngine", version="build 5 (for mc1.4.7  bc3.4.3  Forge#534 )", dependencies ="required-after:BuildCraft|Energy")
@NetworkMod(clientSideRequired=true, serverSideRequired=false/*, channels={"EC|Levi","EC|CSC","EC|CS","EC|Sw"}, packetHandler=Packet_EnchantChanger.class*/)
public class CompactEngine
{
	@Instance("CompactEngine")
	public static CompactEngine instance;
	@SidedProxy(clientSide = "compactengine.Client.ClientProxy", serverSide = "compactengine.CommonProxy")
	public static CommonProxy proxy;

	public static final String TEX = "/texture/";
	public static BlockCompactEngine engineBlock;
	public static Item engineItem;
	public static Item energyChecker;
	public static String texture = "texture/CompactEngines.png";

	public static int blockID_CompactEngine;
	public static int itemID_energyChecker;
	public static boolean isAddCompactEngine512and2048;
	public static int CompactEngineExplosionPowerLevel;
	public static int CompactEngineExplosionTimeLevel;
	public static int CompactEngineExplosionAlertMinute;
	public static boolean neverExplosion;
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		blockID_CompactEngine = config.get(Configuration.CATEGORY_BLOCK, "CompactEngineId", 1529).getInt();
		itemID_energyChecker = config.get(Configuration.CATEGORY_ITEM, "EnergyCheckerId", 19500).getInt();
		isAddCompactEngine512and2048 = config.get(Configuration.CATEGORY_GENERAL, "Add high compact engine", false,"add Engine is x512 and x2048 (Note explosion)").getBoolean(false);
		CompactEngineExplosionPowerLevel = config.get(Configuration.CATEGORY_GENERAL, "CompactEngineExplosionPowerLevel", 1,"min=0, max=3").getInt();
		CompactEngineExplosionTimeLevel = config.get(Configuration.CATEGORY_GENERAL, "CompactEngineExplosionTimeLevel", 1,"min=0, max=3").getInt();
		CompactEngineExplosionAlertMinute = config.get(Configuration.CATEGORY_GENERAL, "CompactEngineExplosionAlertMinute", 3,"0 is not alert display, min=0.0D, max=30.0D").getInt();
		neverExplosion = config.get(Configuration.CATEGORY_GENERAL, "neverExplosion", false, "Engine No Explosion").getBoolean(false);
		config.save();
	}
	@Init
	public void load(FMLInitializationEvent event)
	{
		engineBlock = new BlockCompactEngine(blockID_CompactEngine);
		engineItem  = new ItemCompactEngine(blockID_CompactEngine - 256).setItemName("compactEngine");

//		TileEntitySpecialRenderer sr = new RenderEngine();
//		TileEntityRenderer.instance.specialRendererMap.put(TileCompactEngine.class, sr);
//		sr.setTileEntityRenderer(TileEntityRenderer.instance);
		proxy.registerTileEntitySpecialRenderer();
		GameRegistry.registerTileEntity(TileCompactEngine.class, "tile.compactengine");
		/*
        ModLoader.registerTileEntity(buildcraft.energy.TileCompactEngine.class, "CompactEngine", new RenderEngine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEngine.class, new RenderEngine());
		 */
		ItemStack engine1 = new ItemStack(engineBlock, 1, 0);
		ItemStack engine2 = new ItemStack(engineBlock, 1, 1);
		ItemStack engine3 = new ItemStack(engineBlock, 1, 2);
		ItemStack engine4 = new ItemStack(engineBlock, 1, 3);
		ItemStack engine5 = new ItemStack(engineBlock, 1, 4);
		ItemStack woodEngine = new ItemStack(BuildCraftEnergy.engineBlock, 1, 0);
		ItemStack ironEngine = new ItemStack(BuildCraftEnergy.engineBlock, 1, 2);
		ItemStack ironGear = new ItemStack(BuildCraftCore.ironGearItem);
		ItemStack diaGear = new ItemStack(BuildCraftCore.diamondGearItem);
		ItemStack diaChip = new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 3);
		ItemStack goldORGate = new ItemStack(BuildCraftTransport.pipeGate, 1, 4);
		ItemStack diaORGate = new ItemStack(BuildCraftTransport.pipeGate, 1, 6);

		GameRegistry.addRecipe(engine1, new Object[]{"www", "wgw", "www", 'w', woodEngine, 'g', ironGear});
		GameRegistry.addRecipe(engine2, new Object[]{"geg", "eie", "geg", 'e', engine1, 'g', diaGear, 'i', ironEngine});
		GameRegistry.addRecipe(engine3, new Object[]{"geg", "eie", "geg", 'e', engine2, 'g', diaChip, 'i', ironEngine});

		if(isAddCompactEngine512and2048)
		{
			GameRegistry.addRecipe(engine4, new Object[]{"geg", "eie", "geg", 'e', engine3, 'g', goldORGate, 'i', ironEngine});
			GameRegistry.addRecipe(engine5, new Object[]{"geg", "eie", "geg", 'e', engine4, 'g', diaORGate, 'i', ironEngine});
		}

		LanguageRegistry.addName(engine1, "Redstone Engine x8");
		LanguageRegistry.instance().addNameForObject(engine1, "ja_JP", "8倍圧縮 木エンジン");
		LanguageRegistry.addName(engine2, "Redstone Engine x32");
		LanguageRegistry.instance().addNameForObject(engine2, "ja_JP", "32倍圧縮 木エンジン");
		LanguageRegistry.addName(engine3, "Redstone Engine x128");
		LanguageRegistry.instance().addNameForObject(engine3, "ja_JP", "128倍圧縮 木エンジン");
		LanguageRegistry.addName(engine4, "Redstone Engine x512");
		LanguageRegistry.instance().addNameForObject(engine4, "ja_JP", "512倍圧縮 木エンジン");
		LanguageRegistry.addName(engine5, "Redstone Engine x2048");
		LanguageRegistry.instance().addNameForObject(engine5, "ja_JP", "2048倍圧縮 木エンジン");

		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_1.name", "Redstone Engine x8");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_1.name", "ja_JP", "8倍圧縮 木エンジン");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_2.name", "Redstone Engine x32");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_2.name", "ja_JP", "32倍圧縮 木エンジン");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_3.name", "Redstone Engine x128");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_3.name", "ja_JP", "128倍圧縮 木エンジン");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_4.name", "Redstone Engine x512");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_4.name", "ja_JP", "512倍圧縮 木エンジン");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_5.name", "Redstone Engine x2048");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_5.name", "ja_JP", "2048倍圧縮 木エンジン");

		LanguageRegistry.instance().addStringLocalization("energyChecker.maxPower"  , "maxPower"  );
		LanguageRegistry.instance().addStringLocalization("energyChecker.maxPower"  , "ja_JP", "出力"  );
		LanguageRegistry.instance().addStringLocalization("energyChecker.energy"    , "energy"    );
		LanguageRegistry.instance().addStringLocalization("energyChecker.energy"    , "ja_JP", "熱量"    );
		LanguageRegistry.instance().addStringLocalization("energyChecker.workEnergy", "workEnergy");
		LanguageRegistry.instance().addStringLocalization("energyChecker.workEnergy", "ja_JP", "受入限界");
		LanguageRegistry.instance().addStringLocalization("energyChecker.pipeEnergy", "pipeEnergy");
		LanguageRegistry.instance().addStringLocalization("energyChecker.pipeEnergy", "ja_JP", "流量");
		LanguageRegistry.instance().addStringLocalization("energyChecker.heat",       "Heat");
		LanguageRegistry.instance().addStringLocalization("energyChecker.heat",       "ja_JP","温度");
		LanguageRegistry.instance().addStringLocalization("engine.alert", "CompactEngine x %d explosion is limit %d:00 (x:%d y:%d z:%d)");
		LanguageRegistry.instance().addStringLocalization("engine.alert", "ja_JP", "%d倍圧縮木エンジンがあと%d分で爆発します。  座標 x:%d y:%d z:%d");
		LanguageRegistry.instance().addStringLocalization("engine.explode", "Explode! range %d (x:%d y:%d z:%d)");
		LanguageRegistry.instance().addStringLocalization("engine.explode", "ja_JP", "爆発力%dの圧縮木エンジンが爆発しました。  座標 x:%d y:%d z:%d");

		if(itemID_energyChecker < 32000)
		{
			energyChecker = new ItemEnergyChecker(itemID_energyChecker - 256).setItemName("CompactEngine:energyChecker");
			LanguageRegistry.addName(energyChecker, "Energy Checker");
			LanguageRegistry.instance().addNameForObject(energyChecker, "ja_JP", "エネルギー計測器");
			GameRegistry.addRecipe(new ItemStack(energyChecker), new Object[]{"w", "i",
				'w', BuildCraftTransport.pipePowerWood, 'i', Item.ingotIron});
		}
	}
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{

	}
	public static void addChat(String message)
	{
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			Minecraft mc = Minecraft.getMinecraft();
			mc.ingameGUI.getChatGUI().printChatMessage(message);
		}
		else if(FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			PacketDispatcher.sendPacketToAllPlayers(new Packet3Chat(message,false));
		}
	}

	public static void addChat(String format,Object... args)
	{
		addChat(String.format(format,args));
	}
}