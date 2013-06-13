package ak.EnchantChanger;

import java.util.List;
import java.util.Random;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EcItemMateria extends Item
{
	public static final String[] MateriaNames = new String[] {"Protection", "Fire Protection", "Feather Falling", "Blast Protection", "Projectile Protection", "Respiration", "Aqua Affinity","Thorns", "Sharpness", "Smite", "Bane of Arthropods", "Knockback", "Fire Aspect", "Looting", "Efficiency", "Silk Touch", "Unbreaking","Fortune","Power","Punch","Flame","Infinity"};
	public static final String[] MateriaJPNames = new String[]{"ダメージ軽減", "火炎耐性", "落下耐性", "爆発耐性", "飛び道具耐性", "水中呼吸", "水中採掘","棘", "ダメージ増加", "アンデッド特効", "虫特効", "ノックバック", "火属性", "ドロップ増加", "効率強化", "シルクタッチ", "耐久力","幸運","矢ダメージ増加","パンチ","フレイム","無限"};
	public static final String[] MateriaMagicNames = new String[]{"Black","White","Teleport","Floating","Thunder","Despell","Haste","Absorption"};
	public static final String[] MateriaMagicJPNames = new String[]{"黒","白","瞬間移動","浮遊","雷","解呪","加速","吸収"};
	public static int MagicMateriaNum = MateriaMagicNames.length;

	public static int[] vanillaEnch = new int[]{0,1,2,3,4,5,6,7,16,17,18,19,20,21,32,33,34,35,48,49,50,51};
	//	public static double homeX = 0;
	//	public static double homeY = 512;
	//	public static double homeZ = 0;
	public static boolean GGEnable = false;
	private int materiamax = EnchantChanger.materiamax;
	private int maxlv = EnchantChanger.MaxLv;
	private int vanilla = 22;//EnchantChanger.VanillaEnchNum;
	private boolean LvCap = EnchantChanger.LevelCap;
	private boolean Debug = EnchantChanger.Debug;
	private boolean tera = EnchantChanger.YouAreTera;
	protected static Random rand;
	private double BoxSize = 5D;
	public EcItemMateria(int par1)
	{
		super(par1);
		setHasSubtypes(true);
		maxStackSize = 64;
		setMaxDamage(0);
		this.rand = new Random();
		this.setTextureFile(EnchantChanger.EcSprites);
	}

	public int getIconFromDamage(int par1)
	{
		int[] Icon = new int[]{8,8,8,8,8,4,4,8,13,13,13,14,1,9,5,7,3,9,13,14,1,12};
		if(par1 == 0)
		{
			return this.iconIndex;
		}
		else if(par1 <= materiamax)
		{
			int materiaId = (par1 - 1)/ maxlv;
			return Icon[materiaId];
		}
		else if(par1 <= materiamax + EnchantChanger.MagicMateriaNumMax)
		{
			int var1 = (par1 - materiamax - 1) / maxlv;
			switch(var1)
			{
			case 0 :return this.iconIndex;
			case 1 :return 15;
			case 2 :return 5;
			case 3 :return 9;
			case 4 :return 11;
			case 5 :return 7;
			case 6 :return 6;
			case 7 :return 10;
			default :return this.iconIndex;
			}
		}
		else if(EnchantChanger.ExtraEnchantIdArray.size() > 0)
		{
			return 10;
		}
		else
		{
			return this.iconIndex;
		}
	}
	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
	private boolean jump01 = false;
	private boolean jump02 = false;
	private int flyToggleTimer = 0;
	private boolean isLevitation = false;
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		if(entity instanceof EntityLiving)
		{
			EntityLiving entityliving = (EntityLiving)entity;
			if(itemstack.getItemDamage() >0  && itemstack.getItemDamage() <= materiamax)
			{
				int MateriaKind = (itemstack.getItemDamage() - 1) / maxlv;
				int enchLv = itemstack.getItemDamage() % maxlv;
				int potionNum;
				String Message;
				String EntityName=entityliving.getEntityData().getName();
				switch (MateriaKind)
				{
				case 1:potionNum = 12;Message="fire resistance";break;
				case 5:potionNum = 13;Message="more Oxygen";break;
				case 2:potionNum = 8;Message="high jump";break;
				case 0:potionNum = 11;Message="defence power";break;
				default:return false;
				}
				if(player.experienceLevel > LevelUPEXP(MateriaKind,enchLv) || player.capabilities.isCreativeMode)
				{
					entityliving.addPotionEffect(new PotionEffect(potionNum,20*60*EnchantChanger.MateriaPotionMinutes,enchLv));
					//					 player.removeExperience(LevelUPEXP(MateriaKind,enchLv));
					player.addChatMessage(EntityName + " gets "+ Message);
					player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-6);
					return true;
				}
			}
			else
			{
				int var1 = (itemstack.getItemDamage() -  materiamax - 1) / maxlv;
				switch(var1)
				{
				case 5:Despell(player,entityliving);return true;
				case 6:Haste(player,entityliving);player.addChatMessage("Haste!");return true;
				default:return false;
				}
			}
			return false;
		}
		return false;
	}
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(itemstack.stackSize>1)
		{
			return itemstack;
		}
		int enchLv = itemstack.getItemDamage() % maxlv;
		if(itemstack.getItemDamage() > 0 && itemstack.getItemDamage() <= materiamax)
		{
			int MateriaKind = (itemstack.getItemDamage()-1) / maxlv;
			if(entityplayer.isSneaking()&&LvCap && enchLv > 1)
			{
				//					entityplayer.addExperience(this.LevelUPEXP2(MateriaKind, enchLv-1));
				entityplayer.addExperienceLevel(this.LevelUPEXP(MateriaKind, enchLv-1));
				itemstack.setItemDamage(itemstack.getItemDamage() - 1);
			}
			else if ((entityplayer.experienceLevel >= LevelUPEXP(MateriaKind,enchLv) || entityplayer.capabilities.isCreativeMode) && enchLv != 0)
			{
				//					entityplayer.addExperience(-this.LevelUPEXP2(MateriaKind, enchLv));
				entityplayer.addExperienceLevel(-this.LevelUPEXP(MateriaKind, enchLv));
				itemstack.setItemDamage(itemstack.getItemDamage() + 1);
			}
		}
		else if(itemstack.getItemDamage() > materiamax && itemstack.getItemDamage() <= materiamax + EnchantChanger.MagicMateriaNumMax)
		{
			int var1 = (itemstack.getItemDamage() -  materiamax - 1) / maxlv;
			switch(var1)
			{
			case 0:Meteo(world,entityplayer);break;
			case 1:
				if(entityplayer.isSneaking())
				{
					GGEnable=!GGEnable;
					entityplayer.addChatMessage("Great Gospel Mode " + GGEnable);
				}
				else
				{
					Holy(world,entityplayer);
				}break;
			case 2:Teleport(itemstack,world,entityplayer);break;
			case 4:Thunder(world,entityplayer);break;
			case 5:Despell(entityplayer, entityplayer);break;
			case 6:Haste(entityplayer,entityplayer);break;
			default:;
			}
		}
		else if(itemstack.getItemDamage() > materiamax + EnchantChanger.MagicMateriaNumMax && EnchantChanger.ExtraEnchantIdArray.size() > 0)
		{
			int var2 = itemstack.getItemDamage() - (materiamax + EnchantChanger.MagicMateriaNumMax);
			int ExtraEnchLv = var2 % maxlv;
			if(entityplayer.isSneaking() && LvCap && ExtraEnchLv > 1)
			{
				entityplayer.addExperienceLevel(15);
				//					entityplayer.addExperience(250);
				itemstack.setItemDamage(itemstack.getItemDamage() - 1);
			}
			else if(ExtraEnchLv != 0 && (entityplayer.experienceLevel >= 15 || entityplayer.capabilities.isCreativeMode))
			{
				entityplayer.addExperienceLevel(-15);
				//					entityplayer.addExperience(-250);
				itemstack.setItemDamage(itemstack.getItemDamage() + 1);
			}
		}
		return itemstack;
	}


	public String getItemNameIS(ItemStack par1ItemStack)
	{
		if(par1ItemStack.getItemDamage() ==0)
		{
			return "ItemMateria.Base";
		}
		else if(par1ItemStack.getItemDamage() <= this.materiamax)
		{
			int var1 = (par1ItemStack.getItemDamage()-1)/maxlv;
			int var2 = (par1ItemStack.getItemDamage() % maxlv != 0)? par1ItemStack.getItemDamage() % this.maxlv:this.maxlv;
			return "ItemMateria." + Enchantment.enchantmentsList[this.vanillaEnch[var1]].getName()+"."+var2;
		}
		else if(par1ItemStack.getItemDamage() <= this.materiamax + EnchantChanger.MagicMateriaNumMax)
		{
			int var3 =(par1ItemStack.getItemDamage()-this.materiamax -1)/this.maxlv;
			return "ItemMateria."+this.MateriaMagicNames[var3];
		}
		else if(EnchantChanger.ExtraEnchantIdArray.size() > 0)
		{
			int var4 = (par1ItemStack.getItemDamage() - (this.materiamax + EnchantChanger.MagicMateriaNumMax) -1) /this.maxlv;
			int var6 = (par1ItemStack.getItemDamage() - (this.materiamax + EnchantChanger.MagicMateriaNumMax)) % this.maxlv;
			if(var4 < EnchantChanger.ExtraEnchantNameArray.size())
			{
				return "ItemMateria."+EnchantChanger.ExtraEnchantNameArray.get(var4)+"."+var6;
			}
			else
			{
				return "";
			}
		}
		else
		{
			return "";
		}
	}
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if(EnchantChanger.Debug)
			par3List.add("ItemDmg:" + par1ItemStack.getItemDamage());
	}
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
		int var1 =  par1ItemStack.getItemDamage();
		if(var1 <= materiamax)
			return false;
		else if(var1 <= materiamax + EnchantChanger.MagicMateriaNumMax)
			return true;
		else
			return false;
	}
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(this, 1, 0));
		for (int x = 0; x < this.vanilla; x++)
		{
			par3List.add(new ItemStack(this, 1, x*maxlv + 1 ));
			par3List.add(new ItemStack(this, 1, x*maxlv + 10 ));
			if(EnchantChanger.Debug)
				par3List.add(new ItemStack(this, 1, x*maxlv + maxlv));
		}
		for(int i=0;i < MagicMateriaNum; i++)
		{
			par3List.add(new ItemStack(this, 1, materiamax + 1 + i*maxlv));
		}
		for(int i=0; i< EnchantChanger.ExtraEnchantIdArray.size();i++)
		{
			par3List.add(new ItemStack(this, 1, materiamax + EnchantChanger.MagicMateriaNumMax + 1 + i*maxlv));
		}
	}
	/**
	 * Return an item rarity from EnumRarity
	 */
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack var1)
	{
		if(var1.getItemDamage() ==0)
		{
			return EnumRarity.common;
		}
		else if(var1.getItemDamage() <= materiamax)
		{
			int var2 = var1.getItemDamage() % maxlv;
			switch(var2)
			{
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				return EnumRarity.common;
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				return EnumRarity.uncommon;
			default :
				return EnumRarity.epic;
			}
		}
		else if(var1.getItemDamage() <= materiamax + EnchantChanger.MagicMateriaNumMax)
		{
			return EnumRarity.rare;
		}
		else
		{
			return EnumRarity.common;
		}
	}
	public int LevelUPEXP(int materiakind, int materialv)
	{
		int[] LvUPBase = new int[]{10,5,5,5,5,10,20,10,10,5,5,5,5,10,10,20,10,10,10,5,5,20};
		int[] LvUPOver5 = new int[]{30,20,20,20,20,30,30,30,30,20,20,20,20,30,30,20,30,30,30,20,20,20};
		if(materialv < 5 || EnchantChanger.Difficulty == 0)
		{
			return LvUPBase[materiakind];
		}
		else
		{
			return LvUPOver5[materiakind];
		}
	}
	public int LevelUPEXP2(int materiakind, int materialv)
	{
		int[] LvUPBase = new int[]{10*20,5*20,5*20,5*20,5*20,10*20,20*20,10*20,10*20,5*20,5*20,5*20,5*20,10*20,10*20,20*20,10*20,10*20,10*20,5*20,5*20,20*20};;
		int[] LvUPOver5 = new int[]{30*20,20*20,20*20,20*20,20*20,30*20,30*20,30*20,30*20,20*20,20*20,20*20,20*20,30*20,30*20,20*20,30*20,30*20,30*20,20*20,20*20,20*20};
		if(materialv < 5 || EnchantChanger.Difficulty == 0)
		{
			return LvUPBase[materiakind];
		}
		else
		{
			return LvUPOver5[materiakind];
		}
	}
	public WeightedRandomChestContent addMateriaInChest(int kind, int par2, int par3, int par4)
	{
		int materiaKind = this.materiamax + 1 + maxlv * kind;
		ItemStack var6 = new ItemStack(this, 1,materiaKind);
		return new WeightedRandomChestContent(var6, par2, par3, par4);
	}
	public static void Teleport(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		ChunkCoordinates Spawn;
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !EnchantChanger.YouAreTera && !entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(entityplayer.getBedLocation() !=null)
		{
			Spawn = entityplayer.getBedLocation();
		}
		else
		{
			Spawn = world.getSpawnPoint();
		}
		double SpawnX = Spawn.posX;
		double SpawnY = Spawn.posY;
		double SpawnZ = Spawn.posZ;
		if(entityplayer.isSneaking() && entityplayer.dimension == 0)
		{
			/**
    		if(setTeleportPoint(world,entityplayer) !=null)
    		{
    			double PlayerPosX = (double)MathHelper.floor_float((float)entityplayer.posX);
    			double PlayerPosY = (double)MathHelper.floor_float((float)entityplayer.posY)-1;
    			double PlayerPosZ = (double)MathHelper.floor_float((float)entityplayer.posZ);
    			double TPX = setTeleportPoint(world,entityplayer).xCoord;
    			double TPY = setTeleportPoint(world,entityplayer).yCoord;
    			double TPZ = setTeleportPoint(world,entityplayer).zCoord;
    			Vec3D HomeVec = Vec3D.createVector(PlayerPosX, PlayerPosY, PlayerPosZ);
    			System.out.println(HomeVec);
    			System.out.println(setTeleportPoint(world,entityplayer));
    			if(PlayerPosX == TPX && PlayerPosY == TPY && PlayerPosZ == TPZ)
    				setHomePoint(itemstack, world, entityplayer);
    			else
    			{
	    			if(homeY < 512)
	    				entityplayer.setPositionAndUpdate(SpawnX, SpawnY, SpawnZ);
	    			else
	    				entityplayer.setPositionAndUpdate(homeX, homeY, homeZ);
	    			entityplayer.getFoodStats().addStats(-20, 1.0F);
	    			entityplayer.fallDistance = 0.0F;
	    			for (int var2 = 0; var2 < 32; ++var2)
	                {
	    				world.spawnParticle("portal", entityplayer.posX, entityplayer.posY + this.rand.nextDouble() * 2.0D, entityplayer.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
	                }
    			}
    		}
			 */
			entityplayer.setPositionAndUpdate(SpawnX, SpawnY, SpawnZ);
			if(!entityplayer.capabilities.isCreativeMode)
			{
				//entityplayer.getFoodStats().setFoodLevel(entityplayer.getFoodStats().getFoodLevel()-20);
				entityplayer.getFoodStats().addStats(-20, 1.0F);
				entityplayer.fallDistance = 0.0F;
			}
			for (int var2 = 0; var2 < 32; ++var2)
			{
				world.spawnParticle("portal", entityplayer.posX, entityplayer.posY + EcItemMateria.rand.nextDouble() * 2.0D, entityplayer.posZ, EcItemMateria.rand.nextGaussian(), 0.0D, EcItemMateria.rand.nextGaussian());
			}
		}
		else
		{
			if(setTeleportPoint(world,entityplayer) !=null)
			{
				entityplayer.setPositionAndUpdate(setTeleportPoint(world,entityplayer).xCoord, setTeleportPoint(world,entityplayer).yCoord, setTeleportPoint(world,entityplayer).zCoord);
				if(!entityplayer.capabilities.isCreativeMode)
				{
					//entityplayer.getFoodStats().setFoodLevel(entityplayer.getFoodStats().getFoodLevel()-2);
					entityplayer.getFoodStats().addStats(-2, 1.0F);
					entityplayer.fallDistance = 0.0F;
				}
				for (int var2 = 0; var2 < 32; ++var2)
				{
					world.spawnParticle("portal", entityplayer.posX, entityplayer.posY + EcItemMateria.rand.nextDouble() * 2.0D, entityplayer.posZ, EcItemMateria.rand.nextGaussian(), 0.0D, EcItemMateria.rand.nextGaussian());
				}
			}
		}
	}
	public static Vec3 setTeleportPoint(World world, EntityPlayer entityplayer)
	{
		float var1=1F;
		double Dislimit = 150.0D;
		double viewX = entityplayer.getLookVec().xCoord;
		double viewY = entityplayer.getLookVec().yCoord;
		double viewZ = entityplayer.getLookVec().zCoord;
		double PlayerposX = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)var1;
		double PlayerposY = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)var1 + 1.62D - (double)entityplayer.yOffset;
		double PlayerposZ = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)var1;
		Vec3 PlayerPosition = Vec3.createVectorHelper(PlayerposX, PlayerposY, PlayerposZ);
		Vec3 PlayerLookVec = PlayerPosition.addVector(viewX*Dislimit, viewY*Dislimit, viewZ*Dislimit);
		MovingObjectPosition MOP = world.rayTraceBlocks_do(PlayerPosition, PlayerLookVec, true);
		if(MOP !=null)
		{
			if (MOP.typeOfHit == EnumMovingObjectType.TILE)
			{
				int BlockposX = MOP.blockX;
				int BlockposY = MOP.blockY;
				int BlockposZ = MOP.blockZ;
				int Blockside = MOP.sideHit;
				switch(Blockside)
				{
				case 0:BlockposY -=2;break;
				case 1:BlockposY++;break;
				case 2:BlockposZ--;break;
				case 3:BlockposZ++;break;
				case 4:BlockposX--;break;
				case 5:BlockposX++;break;
				}
				Vec3 TelepoVec = Vec3.createVectorHelper(BlockposX, BlockposY, BlockposZ);
				return TelepoVec;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	public static void Holy(World world, EntityPlayer entityplayer)
	{
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !EnchantChanger.YouAreTera && !entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(!entityplayer.capabilities.isCreativeMode)
		{
			//entityplayer.getFoodStats().setFoodLevel(entityplayer.getFoodStats().getFoodLevel()-6);
			entityplayer.getFoodStats().addStats(-6, 1.0F);
		}
		List EntityList = world.getEntitiesWithinAABB(EntityLiving.class, entityplayer.boundingBox.expand(5D, 5D, 5D));
		for (int i=0; i < EntityList.size();i++)
		{
			Entity entity=(Entity) EntityList.get(i);
			if(((EntityLiving)entity).isEntityUndead())
			{
				int var1 = MathHelper.floor_float(((EntityLiving) entity).getMaxHealth()/2);
				entity.attackEntityFrom(DamageSource.magic, var1);
			}
		}
	}
	public static void Meteo(World world, EntityPlayer entityplayer)
	{
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !EnchantChanger.YouAreTera && !entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(!entityplayer.capabilities.isCreativeMode)
		{
			//entityplayer.getFoodStats().setFoodLevel(entityplayer.getFoodStats().getFoodLevel()-6);
			entityplayer.getFoodStats().addStats(-6, 1.0F);
		}
		Vec3 EndPoint = setTeleportPoint(world,entityplayer);
		if(EndPoint != null && !world.isRemote)
			world.spawnEntityInWorld( new EcEntityMeteo(world, EndPoint.xCoord,(double)200,EndPoint.zCoord,0.0D,-1D,0D,0.0F,0.0F));
	}
	public static void Thunder(World world, EntityPlayer entityplayer)
	{
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !EnchantChanger.YouAreTera && !entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(!entityplayer.capabilities.isCreativeMode)
		{
			//entityplayer.getFoodStats().setFoodLevel(entityplayer.getFoodStats().getFoodLevel()-6);
			entityplayer.getFoodStats().addStats(-6, 1.0F);
		}
		Vec3 EndPoint = setTeleportPoint(world,entityplayer);
		if(EndPoint != null)
			world.spawnEntityInWorld( new EntityLightningBolt(world, EndPoint.xCoord, EndPoint.yCoord, EndPoint.zCoord));
	}
	public void Despell(EntityPlayer player,Entity entity)
	{
		if(entity instanceof EntityLiving)
		{
			((EntityLiving) entity).clearActivePotions();
			for (int i=0;i<33;i++)
			{
				((EntityLiving) entity).removePotionEffect(i);
				//System.out.println("Despell "+i);
			}
			if(!player.capabilities.isCreativeMode)
			{
				//player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-2);
				player.getFoodStats().addStats(-2, 1.0F);
			}
		}
	}
	public void Haste(EntityPlayer player,EntityLiving entityliving)
	{
		entityliving.addPotionEffect(new PotionEffect(1,20*60*5,1));
		if(!player.capabilities.isCreativeMode)
		{
			//player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-2);
			player.getFoodStats().addStats(-2, 1.0F);
		}
	}
}
