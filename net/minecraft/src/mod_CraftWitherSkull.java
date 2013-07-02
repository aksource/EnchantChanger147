package net.minecraft.src;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class mod_CraftWitherSkull extends BaseMod
{

	@Override
	public String getVersion() 
	{
		return "1.0";
	}

	@Override
	public void load() 
	{
		for(int i=0; i < 5 ;i++)
		ModLoader.addShapelessRecipe(new ItemStack(Item.skull, 1,1), new Object[]{new ItemStack(Item.skull,1,i)});
	}
}