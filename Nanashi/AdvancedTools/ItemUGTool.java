package Nanashi.AdvancedTools;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUGTool extends ItemTool
{
	public String BaseName;
	public boolean canDestroyABlock = false;
	protected int cDestroyRange;
	private int[] rangeArray = new int[]{2,4,7,9,9};
	private int saftyCount;
	private int breakcount;

	protected ItemUGTool(int var1, int var2, EnumToolMaterial var3, Block[] var4)
	{
		super(var1, var2, var3, var4);
		this.cDestroyRange = AdvancedTools.UGTools_SaftyCounter;
		this.saftyCount = AdvancedTools.UGTools_SaftyCounter;
		this.setTextureFile(AdvancedTools.itemTexture);
	}

	protected ItemUGTool(int var1, int var2, EnumToolMaterial var3, Block[] var4, float var5)
	{
		super(var1, var2, var3, var4);
		this.cDestroyRange = AdvancedTools.UGTools_SaftyCounter;
		this.saftyCount = AdvancedTools.UGTools_SaftyCounter;
		this.setMaxDamage((int)(var5 * (float)this.getMaxDamage()));
		this.setTextureFile(AdvancedTools.itemTexture);
	}

	public boolean doChainDestraction(Block var1)
	{
		return false;
	}

	/**
	 * set name of item from language file
	 */
	public Item setItemName(String var1)
	{
		super.setItemName(var1);

		if (this.BaseName == null)
		{
			this.BaseName = var1;
		}

		return this;
	}
	@Override
	public boolean onBlockDestroyed(ItemStack item, World world, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
	{
		item.damageItem(1, par7EntityLiving);
		this.breakcount = 0;
		int range;
		if(item.hasTagCompound() && item.getTagCompound().hasKey("range"))
		{
			range = item.getTagCompound().getInteger("range");
		}
		else
		{
			range = AdvancedTools.UGTools_DestroyRangeLV;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("range", range);
			item.setTagCompound(nbt);
		}
		if (this.canHarvestBlock(Block.blocksList[par3]) && range != 0 && par7EntityLiving instanceof EntityPlayer)
		{
			if (!world.isRemote)
			{
				this.destroyAroundBlock(item, world, par3, par4, par5, par6, (EntityPlayer)par7EntityLiving, range);
				PacketDispatcher.sendPacketToPlayer(PacketHandler.getPacketTool(this), (Player) par7EntityLiving);
			}
			item.damageItem(this.breakcount, par7EntityLiving);
			return true;
		}
		else
		{
			return true;
		}
	}

	protected void searchAndDestroyBlock(World world, int var1, int var2, int var3, int var4, int var5, ItemStack var6, EntityPlayer var7, int range)
	{
		ArrayList var8 = new ArrayList();
		var8.add(new ChunkPosition(var1, var2, var3));
		int var9;
		int var10;
		int var11;
		int var12;
		int var13;
		int var14;

		if (!this.doChainDestraction(Block.blocksList[var5]))
		{
			var9 = var1 - range;
			var10 = var2 - range;
			var11 = var3 - range;
			var12 = var1 + range;
			var13 = var2 + range;
			var14 = var3 + range;

			if (var4 == 0 || var4 == 1)
			{
				var10 = var2;
				var13 = var2;
			}

			if (var4 == 2 || var4 == 3)
			{
				var11 = var3;
				var14 = var3;
				var10 += range - 1;
				var13 += range - 1;
			}

			if (var4 == 4 || var4 == 5)
			{
				var9 = var1;
				var12 = var1;
				var10 += range - 1;
				var13 += range - 1;
			}
		}
		else
		{
			var9 = var1 - this.cDestroyRange;
			var10 = var2 - this.cDestroyRange;
			var11 = var3 - this.cDestroyRange;
			var12 = var1 + this.cDestroyRange;
			var13 = var2 + this.cDestroyRange;
			var14 = var3 + this.cDestroyRange;
		}

		ChunkPosition var15 = new ChunkPosition(var9, var10, var11);
		ChunkPosition var16 = new ChunkPosition(var12, var13, var14);

		for (int var17 = 0; var17 < this.saftyCount; ++var17)
		{
			ArrayList var18 = new ArrayList();

			for (int var19 = 0; var19 < var8.size(); ++var19)
			{
				var18.addAll(this.searchAroundBlock(world,(ChunkPosition)var8.get(var19), var15, var16, var5, var6, var7));
			}

			if (var18.isEmpty())
			{
				break;
			}

			var8.clear();
			var8.addAll(var18);
		}
	}

	protected ArrayList searchAroundBlock(World world,ChunkPosition var1, ChunkPosition var2, ChunkPosition var3, int var4, ItemStack var5, EntityPlayer var6)
	{
		ArrayList var7 = new ArrayList();
		ChunkPosition[] var8 = new ChunkPosition[6];

		if (var1.y > var2.y)
		{
			var8[0] = new ChunkPosition(var1.x, var1.y - 1, var1.z);
		}

		if (var1.y < var3.y)
		{
			var8[1] = new ChunkPosition(var1.x, var1.y + 1, var1.z);
		}

		if (var1.z > var2.z)
		{
			var8[2] = new ChunkPosition(var1.x, var1.y, var1.z - 1);
		}

		if (var1.z < var3.z)
		{
			var8[3] = new ChunkPosition(var1.x, var1.y, var1.z + 1);
		}

		if (var1.x > var2.x)
		{
			var8[4] = new ChunkPosition(var1.x - 1, var1.y, var1.z);
		}

		if (var1.x < var3.x)
		{
			var8[5] = new ChunkPosition(var1.x + 1, var1.y, var1.z);
		}

		for (int var9 = 0; var9 < 6; ++var9)
		{
			if (var8[var9] != null && this.destroyBlock(world,var8[var9], var4, var5, var6))
			{
				var7.add(var8[var9]);
			}
		}

		return var7;
	}

	private boolean destroyAroundBlock(ItemStack var1, World world, int var2, int var3, int var4, int var5, EntityPlayer var6, int range)
	{
		int var7 = AdvancedTools.setMousePoint(world, var6).sideHit;
		this.searchAndDestroyBlock(world,var3, var4, var5, var7, var2, var1, var6, range);
		return true;
	}

	private boolean destroyChainedBlock()
	{
		return true;
	}

	protected boolean destroyBlock(World world, ChunkPosition var1, int blockid, ItemStack var3, EntityPlayer var4)
	{
		int var5 = world.getBlockId(var1.x, var1.y, var1.z);

		if (var5 == 0)
		{
			return false;
		}
		else
		{
			if (blockid == -1)
			{
				if (!this.canHarvestBlock(Block.blocksList[var5]))
				{
					return false;
				}
			}
			else if (Block.blocksList[blockid] != Block.oreRedstone && Block.blocksList[blockid] != Block.oreRedstoneGlowing)
			{
				if (Block.blocksList[blockid] != Block.dirt && Block.blocksList[blockid] != Block.grass)
				{
					if (var5 != blockid)
					{
						return false;
					}
				}
				else if (Block.blocksList[var5] != Block.dirt && Block.blocksList[var5] != Block.grass)
				{
					return false;
				}
			}
			else if (Block.blocksList[var5] != Block.oreRedstone && Block.blocksList[var5] != Block.oreRedstoneGlowing)
			{
				return false;
			}

			return this.checkandDestroy(world,var1, var5, var3, var4);
		}
	}

	private boolean checkandDestroy(World world,ChunkPosition var1, int var2, ItemStack var3, EntityPlayer var4)
	{
		int var5 = world.getBlockMetadata(var1.x, var1.y, var1.z);
		boolean var6 = world.setBlock(var1.x, var1.y, var1.z, 0);

		if (var6)
		{
			Block.blocksList[var2].onBlockDestroyedByPlayer(world, var2, var1.x, var1.y, var1.z);
			if(AdvancedTools.dropGather)
			{
				Block.blocksList[var2].harvestBlock(world, var4, MathHelper.ceiling_double_int( var4.posX), MathHelper.ceiling_double_int( var4.posY), MathHelper.ceiling_double_int( var4.posZ), var5);
			}
			else
			{
				Block.blocksList[var2].harvestBlock(world, var4, var1.x, var1.y, var1.z, var5);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, var3) <= 0)
			{
				this.breakcount++;
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		if (!var2.isRemote) {
			EntityPlayerMP player = (EntityPlayerMP) var3;
			int range;
			int toolMaterialOrd = this.toolMaterial.ordinal();
			if(var1.hasTagCompound() && var1.getTagCompound().hasKey("range"))
			{
				range = var1.getTagCompound().getInteger("range");
			}
			else
			{
				range = AdvancedTools.UGTools_DestroyRangeLV;
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("range", range);
				var1.setTagCompound(nbt);
			}
			if(!var3.isSneaking())
			{
				++range;
				if(range > this.rangeArray[toolMaterialOrd])
				{
					range = 0;
				}
			}
			else
			{
				--range;
				if(range < 0)
				{
					range = this.rangeArray[toolMaterialOrd];
				}
			}
			var1.getTagCompound().setInteger("range", range);
			if (range == 0)
			{
				player.addChatMessage(this.BaseName + " will harvest only one.");
			}
			else
			{
				player.addChatMessage(this.BaseName + "\'s range was changed to " + (range * 2 + 1) + "x" + (range * 2 + 1));
			}
		}
		return var1;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List par3List, boolean par4)
	{
		super.addInformation(item, player, par3List, par4);
		int range;
		if(item.hasTagCompound() && item.getTagCompound().hasKey("range"))
		{
			range = item.getTagCompound().getInteger("range");
		}
		else
		{
			range = AdvancedTools.UGTools_DestroyRangeLV;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("range", range);
			item.setTagCompound(nbt);
		}
		if (range == 0)
		{
			par3List.add("Range: Only one");
		}
		else
		{
			par3List.add("Range: " + (range * 2 + 1) + "x" + (range * 2 + 1));
		}
	}
	public void readPacketData(ByteArrayDataInput data)
	{
		try
		{
			this.breakcount = data.readInt();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void writePacketData(DataOutputStream dos)
	{
		try
		{
			dos.writeInt(breakcount);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
