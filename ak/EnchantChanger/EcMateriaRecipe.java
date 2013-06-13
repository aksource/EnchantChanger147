package ak.EnchantChanger;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class EcMateriaRecipe implements IRecipe
{
	private ItemStack output = null;
	@Override
	public boolean matches(InventoryCrafting var1, World var2)
	{
		ItemStack materia1 = null;
		ItemStack materia2 = null;
		ItemStack expBottle = null;
		ItemStack exExpBottle = null;
		boolean flag = false;
		ItemStack craftitem;
		for(int i=0; i< var1.getSizeInventory();i++)
		{
			craftitem = var1.getStackInSlot(i);
			if(craftitem == null)
				continue;
			if(craftitem.getItem() instanceof EcItemMateria)
			{
				if(materia1 == null)
					materia1 = craftitem;
				else if(materia2 == null)
					materia2 = craftitem;
				else
					return false;
			}
			else if(craftitem.getItem().itemID == Item.expBottle.itemID && expBottle == null && exExpBottle == null && materia2 == null)
				expBottle = craftitem;
			else if(craftitem.getItem().itemID == EnchantChanger.ExExpBottleID && expBottle == null && exExpBottle == null && materia2 == null)
				exExpBottle = craftitem;
			else
				return false;
		}
		if(materia2 != null)
		{
			if(materia1.getItemDamage() == materia2.getItemDamage() && materia1.getItemDamage() % EnchantChanger.MaxLv !=0)
			{
				this.output = new ItemStack(EnchantChanger	.MateriaID, 1, materia1.getItemDamage() + 1);
				flag = true;
			}
		}
		else if(materia1 != null)
		{
			if(materia1.getItemDamage() != 0)
			{
				if(expBottle != null && materia1.getItemDamage() % EnchantChanger.MaxLv > 0 &&materia1.getItemDamage() % EnchantChanger.MaxLv < 6)
				{
					this.output = new ItemStack(EnchantChanger.MateriaID, 1, materia1.getItemDamage() + 1);
					flag = true;
				}
				else if(exExpBottle != null && materia1.getItemDamage() % EnchantChanger.MaxLv != 0 &&materia1.getItemDamage() % EnchantChanger.MaxLv > 5)
				{
					this.output = new ItemStack(EnchantChanger.MateriaID, 1, materia1.getItemDamage() + 1);
					flag = true;
				}
				else if( expBottle == null && exExpBottle == null)
				{
					this.output = new ItemStack(EnchantChanger.MateriaID , 1, 0);
					flag = true;
				}
			}
		}
		return flag;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1)
	{
		return this.output.copy();
	}

	@Override
	public int getRecipeSize()
	{
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return this.output;
	}

}