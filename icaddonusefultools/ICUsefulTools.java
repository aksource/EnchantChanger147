package icaddonusefultools;

import ic2.api.Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="ICUsefulTools", name="ICUsefulTools", version="build 1 (for mc1.4.7  ic115  Forge#534 )", dependencies ="required-after:IC2")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels = "EMW",packetHandler = PacketHandler.class)

public class ICUsefulTools
{
	@Instance("ICUsefulTools")
	public static ICUsefulTools instance;
	
	@SidedProxy(clientSide = "icaddonusefultools.ClientProxy", serverSide = "icaddonusefultools.CommonProxy")
	public static CommonProxy proxy;
	
	public static Item ElFire;
	public static Block BlockEMW;
	
	public static int ElFireId;
	public static int EMWId;
	
	public static String itemTex = "/icaddonusefultools/usefultools.png";
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		ElFireId = config.get(Configuration.CATEGORY_ITEM, "Electric Ignitor", 7000).getInt();
		EMWId = config.get(Configuration.CATEGORY_BLOCK, "BlockEMWID", 4000).getInt();
		config.save();
	}
	@Init
	public void load(FMLInitializationEvent event)
	{
		ElFire = new ItemElFire(ElFireId).setIconIndex(0).setItemName("elfire");
		BlockEMW = new BlockEMW(EMWId).setBlockName("Blockemw");
		GameRegistry.registerBlock(BlockEMW, "Blockemw");
		GameRegistry.registerTileEntity(TileEMW.class, "TileEMW");
		GameRegistry.addRecipe(new ItemStack(ElFire), new Object[]{"A","B","C", Character.valueOf('A'), Item.flintAndSteel, Character.valueOf('B'), Items.getItem("electronicCircuit"), Character.valueOf('C'), Items.getItem("reBattery")});
	}
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		LanguageRegistry.addName(ElFire, "Electric Igniter");
	}
}