package Nanashi.AdvancedTools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;

public class ItemUGPickaxe extends ItemUGTool
{
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered};
	protected ItemUGPickaxe(int var1, EnumToolMaterial var2, float var3)
	{
		super(var1, 2, var2, blocksEffectiveAgainst, var3);
		this.toolMaterial = var2;
//		this.setTextureFile(AdvancedTools.itemTexture);
	}

	protected ItemUGPickaxe(int var1, EnumToolMaterial var2)
	{
		super(var1, 2, var2, blocksEffectiveAgainst, 1.0F);
	}
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void func_94581_a(IconRegister par1IconRegister)
//	{
//		if(this.getUnlocalizedName().equals("item.UpgradedWoodenPickaxe"))
//		{
//	    	this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "UGWoodpickaxe");
//		}
//		else if(this.getUnlocalizedName().equals("item.UpgradedStonePickaxe"))
//		{
//	    	this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "UGStonepickaxe");
//		}
//		else if(this.getUnlocalizedName().equals("item.UpgradedIronPickaxe"))
//		{
//	    	this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "UGIronpickaxe");
//		}
//		else if(this.getUnlocalizedName().equals("item.UpgradedGoldenPickaxe"))
//		{
//	    	this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "UGGoldpickaxe");
//		}
//		else if(this.getUnlocalizedName().equals("item.UpgradedDiamondPickaxe"))
//		{
//	    	this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "UGDiamondpickaxe");
//		}
//		else if(this.getUnlocalizedName().equals("item.InfinityPickaxe"))
//		{
//	    	this.iconIndex = par1IconRegister.func_94245_a(AdvancedTools.textureDomain + "Infinitypickaxe");
//		}
//	}
	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	public boolean canHarvestBlock(Block var1)
	{
//		return var1 == Block.obsidian ? this.toolMaterial.getHarvestLevel() == 3 : (var1 != Block.blockDiamond && var1 != Block.oreDiamond ? (var1 != Block.oreEmerald && var1 != Block.blockEmerald ? (var1 != Block.blockGold && var1 != Block.oreGold ? (var1 != Block.blockSteel && var1 != Block.oreIron ? (var1 != Block.blockLapis && var1 != Block.oreLapis ? (var1 != Block.oreRedstone && var1 != Block.oreRedstoneGlowing ? (var1.blockMaterial == Material.rock ? true : (var1.blockMaterial == Material.iron ? true : var1.blockMaterial == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
		if (var1 == Block.obsidian)
		{
			return this.toolMaterial.getHarvestLevel() == 3;
		}
		else if (var1 != Block.blockDiamond && var1 != Block.oreDiamond)
		{
			if (var1 != Block.blockGold && var1 != Block.oreGold)
			{
				if (var1 != Block.blockSteel && var1 != Block.oreIron)
				{
					if (var1 != Block.blockLapis && var1 != Block.oreLapis)
					{
						if (var1 != Block.oreRedstone && var1 != Block.oreRedstoneGlowing)
						{
							if (var1.blockMaterial == Material.rock)
							{
								return true;
							}
							else
							{
								for (int var2 = 0; var2 < blocksEffectiveAgainst.length; ++var2)
								{
									if (blocksEffectiveAgainst[var2] == var1)
									{
										return true;
									}
								}

								return var1.blockMaterial == Material.iron;
							}
						}
						else
						{
							return this.toolMaterial.getHarvestLevel() >= 2;
						}
					}
					else
					{
						return this.toolMaterial.getHarvestLevel() >= 1;
					}
				}
				else
				{
					return this.toolMaterial.getHarvestLevel() >= 1;
				}
			}
			else
			{
				return this.toolMaterial.getHarvestLevel() >= 2;
			}
		}
		else
		{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
	}

	/**
	 * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	 * sword
	 */
	public float getStrVsBlock(ItemStack var1, Block var2)
	{
		return var2 != null && (var2.blockMaterial == Material.iron || var2.blockMaterial == Material.rock) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(var1, var2);
	}

	public boolean doChainDestraction(Block var1)
	{
		return var1 == Block.oreDiamond ? this.toolMaterial.getHarvestLevel() >= 2 : (var1 == Block.oreGold ? this.toolMaterial.getHarvestLevel() >= 2 : (var1 == Block.oreIron ? this.toolMaterial.getHarvestLevel() >= 1 : (var1 == Block.oreLapis ? this.toolMaterial.getHarvestLevel() >= 1 : (var1 != Block.oreRedstone && var1 != Block.oreRedstoneGlowing ? var1 == Block.oreCoal : this.toolMaterial.getHarvestLevel() >= 2))));
	}
}
