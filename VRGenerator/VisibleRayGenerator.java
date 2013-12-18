package VRGenerator;

import gregtechmod.api.GregTech_API;
import ic2.api.Items;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
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
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="VisibleRayGenerator", name="VisibleRayGenerator", version="build 3 (for mc1.4.7  ic21.115.231if  Forge#534 )", dependencies ="required-after:IC2")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class VisibleRayGenerator
{
	public static boolean isCE;
	public static boolean isGreg;

	public static int blockID_Solar;
	public static int blockID_artificialSun;
	public static int itemID_lavaUpdater;
	public static int LavaUpdateArea = 3;
	public static boolean isSolarCostUp;
	public static boolean isCrazyRecipe;
//	public static boolean isICstacksizeUp;
	public static boolean isResistanceVanilla;
	public static Block solarBlock;
//	public static Block artificiaalSun;
	public static Item lavaUpdater;
	private CreativeTabs IcTab;
	
	public static int artificialSunGuiID = 0;
	
	public static String texture = "/VRGenerator/texture/VRGenerator.png";
	@Instance("VisibleRayGenerator")
	public static VisibleRayGenerator instance;
	@SidedProxy(clientSide = "VRGenerator.ClientProxy", serverSide = "VRGenerator.CommonProxy")
	public static CommonProxy proxy;
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		blockID_Solar = config.get(Configuration.CATEGORY_BLOCK, "Solar ID", 175).getInt();
//		blockID_artificialSun = config.get(Configuration.CATEGORY_BLOCK, "VRGenerator ID", 1002).getInt();
		itemID_lavaUpdater = config.get(Configuration.CATEGORY_ITEM, "LavaUpdaterID", 4440).getInt();
//		LavaUpdateArea = config.get(Configuration.CATEGORY_GENERAL, "LavaUpdateArea", 4, "Effective range radius of Lava Updater. is set 4 = 9x9x9 min=1, max=63").getInt();
		isSolarCostUp = config.get(Configuration.CATEGORY_GENERAL, "isSolarCostUp", true, "Using a capacitor instead of a transformer recipe CE-Solar").getBoolean(true);
//		isCrazyRecipe = config.get(Configuration.CATEGORY_GENERAL, "isCrazyRecipe", false, "Make Recipes Crazy. But it's normal in Greg-tech.").getBoolean(false);
//		isICstacksizeUp = config.get(Configuration.CATEGORY_GENERAL, "isICstacksizeUp", true, "I will up the stacksize of IC2 some items").getBoolean(true);
		isResistanceVanilla = config.get(Configuration.CATEGORY_GENERAL, "isResistanceVanilla", true, "Set in the vanilla resistance of Obsidian, Water, Lava").getBoolean(true);
		config.save();
	}
	@Init
	public void load(FMLInitializationEvent event)
	{
		proxy.registerClientInfo();
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		solarBlock = new CESolarBlock(blockID_Solar).setBlockName("CE_Solar");
		GameRegistry.registerBlock(solarBlock, CESolarItem.class, "CE_Solar");
		lavaUpdater = new CEItemLavaUpdater(itemID_lavaUpdater).setIconIndex(6).setItemName("CE_LavaUpdater");
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
		{
			getICTab();
		}
		solarBlock.setCreativeTab(IcTab);
		lavaUpdater.setCreativeTab(IcTab);
	}
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		isCE = Loader.isModLoaded("CompactEngine");
		isGreg = isCrazyRecipe = Loader.isModLoaded("GregTech_Addon");
		initIC();
		registerRecipesIC();
	}
	private void getICTab()
	{
		for(int i=0;i<CreativeTabs.creativeTabArray.length;i++)
		{
			if(CreativeTabs.creativeTabArray[i].getTabLabel() == "IC2")
			{
				IcTab = CreativeTabs.creativeTabArray[i];
			}
		}
	}
	public static void addChat(String message)
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
		{
			Minecraft mc = Minecraft.getMinecraft();
			mc.ingameGUI.getChatGUI().printChatMessage(message);
		}
	}

	public static void addChat(String format,Object... args)
	{
		addChat(String.format(format,args));
	}
	
	public void initIC()
	{
		LanguageRegistry.addName(solarBlock, "CE Solar");
		LanguageRegistry.instance().addNameForObject(solarBlock, "ja_JP", "人工光ソーラー");
		LanguageRegistry.instance().addStringLocalization("tile.CESolar.name", "VisibleRay Generator");
		LanguageRegistry.instance().addStringLocalization("tile.CESolar.name", "ja_JP", "人工光ソーラー");
		LanguageRegistry.addName(lavaUpdater, "Lava Updater");
		LanguageRegistry.instance().addNameForObject(lavaUpdater, "ja_JP", "溶岩流更新器");
		for(int i = 0; i < CESolarTileEntity.power.length; i++)
		{
			LanguageRegistry.instance().addStringLocalization("CE.block.Solar."+i+".name", "CE Solar "+CESolarTileEntity.power[i]+" EU/t");
			LanguageRegistry.instance().addStringLocalization("CE.block.Solar."+i+".name", "ja_JP", "人工光ソーラー "+CESolarTileEntity.power[i]+" EU/t");
		}
		for(int i = 0; i < CEGeneratorTileEntity.power.length; i++)
		{
			addName("CE.block.Generator."+i+".name",
				"CE Permanent light generator "+CEGeneratorTileEntity.power[i]+" EU/t",
				"永久光発電機 "+CEGeneratorTileEntity.power[i]+" EU/t");
		}
		GameRegistry.registerTileEntity(CESolarTileEntity.class, "CE_Solar");
		GameRegistry.registerTileEntity(CEGeneratorTileEntity.class, "CE_Generator");
		
//		if(isICstacksizeUp)
//		{
//			VRGItemFieldAccessHelper.changeField(Items.getItem("energyCrystal").getItem(), 64);
//			VRGItemFieldAccessHelper.changeField(Items.getItem("lapotronCrystal").getItem(), 64);
//			VRGItemFieldAccessHelper.changeField(Items.getItem("treetap").getItem(), 64);
//			Items.getItem("reBattery").getItem().setMaxStackSize(64);
//			Items.getItem("coolingCell").getItem().setMaxStackSize(64);
//			Items.getItem("reinforcedDoorBlock").getItem().setMaxStackSize(64);
//		}
		
		if(isResistanceVanilla)
		{
			Block.obsidian.setResistance(2000F);
			Block.waterMoving.setResistance(500F);
			Block.waterStill.setResistance(500F);
			Block.lavaStill.setResistance(500F);
		}
	}
	private void registerRecipesIC()
	{
		if(this.isCrazyRecipe)
			recipeCrazy();
		else
			recipeNormal();
	}
	private void recipeNormal()
	{
		ItemStack solar1 = new ItemStack(solarBlock, 1, 0);
		ItemStack solar2 = new ItemStack(solarBlock, 1, 1);
		ItemStack solar3 = new ItemStack(solarBlock, 1, 2);
		ItemStack solar4 = new ItemStack(solarBlock, 1, 3);
/*
		Item.lightStoneDust			//グロウストーンダスト
		Block.glowStone				//グロウストーン
		Block.glass					//ガラス
		Ic2Items.generator;			//地熱発電機
		Ic2Items.coalDust;			//石炭の粉
		Ic2Items.electronicCircuit;	//電子回路
		Ic2Items.advancedCircuit;	//発展回路
		Ic2Items.solarPanel;		//ソーラーパネル
		Ic2Items.batBox;
		Ic2Items.mfeUnit;
		Ic2Items.mfsUnit;
		Ic2Items.lvTransformer;		//低圧変圧器
		Ic2Items.mvTransformer;		//中圧変圧器
		Ic2Items.hvTransformer;		//高圧変圧器
		Ic2Items.energyCrystal;
		Ic2Items.lapotronCrystal;
		Ic2Items.reBattery;
		Ic2Items.treetap;
		Ic2Items.lavaCell;
		Ic2Items.carbonPlate;
		Ic2Items.coalChunk;
		Ic2Items.advancedAlloy;
		Ic2Items.iridiumPlate;
		Ic2Items.advancedMachine;
*/
		GameRegistry.addRecipe(new ItemStack(this.lavaUpdater), "c", "a", "i", 
				'c', Items.getItem("lavaCell"), 'a', Items.getItem("advancedCircuit"), 'i', Items.getItem("refinedIronIngot"));
		GameRegistry.addShapelessRecipe(solar1, Items.getItem("solarPanel"), Items.getItem("advancedCircuit"));
		if(isSolarCostUp)
		{
			GameRegistry.addRecipe(solar2, "sss", "sms", "sss", 's', solar1, 'm', Items.getItem("batBox"));
			GameRegistry.addRecipe(solar3, "sss", "sms", "sss", 's', solar2, 'm', Items.getItem("mfeUnit"));
			GameRegistry.addRecipe(solar4, "sss", "sms", "sss", 's', solar3, 'm', Items.getItem("mfsUnit"));
		}else{
			GameRegistry.addRecipe(solar2, "sss", "sms", "sss", 's', solar1, 'm', Items.getItem("lvTransformer"));
			GameRegistry.addRecipe(solar3, "sss", "sms", "sss", 's', solar2, 'm', Items.getItem("mvTransformer"));
			GameRegistry.addRecipe(solar4, "sss", "sms", "sss", 's', solar3, 'm', Items.getItem("hvTransformer"));
		}
		
		ItemStack[] rlg = new ItemStack[11];
		rlg[0] = solar1;
		for(int i = 0; i < 10; i++) rlg[i+1] = new ItemStack(solarBlock, 1, i+4);
		ItemStack[] items = {
			  new ItemStack(Block.glowStone) 
			, Items.getItem("electronicCircuit")
			, Items.getItem("advancedCircuit")
			, Items.getItem("lvTransformer")
			, Items.getItem("mvTransformer")
			, Items.getItem("hvTransformer")
			, Items.getItem("advancedMachine")
			, Items.getItem("advancedMachine")
			, Items.getItem("advancedMachine")
			, rlg[7]
			};
		ItemStack[] plates = {
				Items.getItem("carbonPlate")
			, Items.getItem("carbonPlate")
			, Items.getItem("carbonPlate")
			, Items.getItem("carbonPlate")
			, Items.getItem("carbonPlate")
			, Items.getItem("advancedAlloy")
			, Items.getItem("advancedAlloy")
			, Items.getItem("iridiumPlate")
			, Items.getItem("iridiumPlate")
			, Items.getItem("iridiumPlate")
			};
		
		GameRegistry.addRecipe(rlg[5], "psp","gmg","psp", 
			'p',Items.getItem("coalChunk"), 'g',solar4, 'm',Items.getItem("mvTransformer"), 's',Block.glowStone);
			
		for(int i = 0; i < 10; i++)
		{
			GameRegistry.addRecipe(rlg[i+1], "pgp","gmg","pgp", 'p',plates[i], 'g',rlg[i], 'm',items[i]);
		}
	}
	private void recipeCrazy()
	{
		ItemStack solar1 = new ItemStack(solarBlock, 1, 0);
		ItemStack solar2 = new ItemStack(solarBlock, 1, 1);
		ItemStack solar3 = new ItemStack(solarBlock, 1, 2);
		ItemStack solar4 = new ItemStack(solarBlock, 1, 3);
		ItemStack itemEnergyFlowCircuit = GregTech_API.getGregTechItem(3, 1, 0);
		ItemStack itemDataStorageCircuit = GregTech_API.getGregTechItem(3, 1, 3);
		ItemStack blockAB = Items.getItem("advancedMachine");
//		ItemStack blockHAB = ;
		
		GameRegistry.addShapelessRecipe(solar1, Items.getItem("solarPanel"), itemDataStorageCircuit);
		GameRegistry.addRecipe(solar2, "ese", "sms", "ese", 's', solar1,'e', itemEnergyFlowCircuit,  'm', blockAB);
		GameRegistry.addRecipe(solar3, "sss", "sms", "sss", 's', solar2, 'm', Items.getItem("mfeUnit"));
		GameRegistry.addRecipe(solar4, "sss", "sms", "sss", 's', solar3, 'm', Items.getItem("mfsUnit"));
		
	}
	public static void addName(Block block, String en, String jp)
	{
		LanguageRegistry.addName(block, en);
		LanguageRegistry.instance().addNameForObject(block, "ja_JP", jp);
	}

	public static void addName(Item item, String en, String jp)
	{
		LanguageRegistry.addName(item, en);
		LanguageRegistry.instance().addNameForObject(item, "ja_JP", jp);
	}

	public static void addName(String name, String en, String jp)
	{
		LanguageRegistry.instance().addStringLocalization(name, en);
		LanguageRegistry.instance().addStringLocalization(name, "ja_JP", jp);
	}

	public static void localize(String name, String jp)
	{
		LanguageRegistry.instance().addStringLocalization(name, "ja_JP", jp);
	}
}