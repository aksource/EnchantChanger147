package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class mod_psWater extends BaseMod
{
	@Override
	public String getVersion()
	{
		return "1.0";
	}
	@Override
    public String getPriorities()
	{
		return "after:*";
	}
	public static Block waterMoving;

	@Override
	public void load()
	{
		Block.blocksList[Block.waterMoving.blockID] = null;
		waterMoving = (new BlockFlowing2(8, Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("water").setRequiresSelfNotify();
	}
}