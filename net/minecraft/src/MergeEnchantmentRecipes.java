package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class MergeEnchantmentRecipes extends ShapelessRecipes {
	public ItemStack enchItem;
	public ItemStack subItem;
	public static  Enchantment[] SameEnch = new Enchantment[256];
	public static int SameEnchindex=0;

	public MergeEnchantmentRecipes(ItemStack enchItem, ItemStack subItem)
	{
		super(getOutputItem(enchItem), getShapelessRecipe(enchItem, subItem));
		this.enchItem = enchItem;
		this.subItem = subItem;
		//System.out.println(enchItem);
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world)
	{
		int n = 0;
		boolean flag = false;
		ItemStack anotherItem = null;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				ItemStack itemstack = inv.getStackInRowAndColumn(j, i);

				if (itemstack == null) {
					continue;
				}

				if (subItem.isItemEqual(itemstack)) {
					if (flag) {
						return false;
					}
					flag = true;
					continue;
				}
				//System.out.println(enchItem);
				//System.out.println(enchItem.getItem().getClass());
				if (enchItem.getItem().getClass().isInstance(itemstack.getItem())) {
					if (enchItem.getItemDamage() == -1 || enchItem.getItemDamage() == itemstack.getItemDamage()) {
						if (++n > 2 || anotherItem != null && itemstack.itemID != anotherItem.itemID) {
							return false;
						}
						anotherItem = itemstack;
						continue;
					}
				}
			}
		}

		return flag && n == 2;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
	{
		ItemStack items[] = new ItemStack[2];
		int n = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				ItemStack itemstack = inventorycrafting.getStackInRowAndColumn(j, i);
				if (itemstack == null) {
					continue;
				}
				if (!enchItem.getItem().getClass().isInstance(itemstack.getItem())) {
					continue;
				}
				int d = enchItem.getItemDamage();
				if (d != -1 && d != itemstack.getItemDamage()) {
					continue;
				}
				if (n >= 2) {
					return null;
				}
				items[n++] = itemstack;
			}
		}

		ArrayList<EnchantmentData> alist = new ArrayList<EnchantmentData>();
		for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
			int lv = getMaxEnchantmentLevel(i, items);
			if (lv > 0) {
				alist.add(new EnchantmentData(Enchantment.enchantmentsList[i], lv));
			}
		}

		for (Iterator<EnchantmentData> it = alist.iterator(); it.hasNext();) {
			EnchantmentData data = it.next();
			for (int i = 0; i < alist.size(); i++) {
				EnchantmentData data2 = alist.get(i);
				if (!data.enchantmentobj.canApplyTogether(data2.enchantmentobj)
						&& data.enchantmentLevel < data2.enchantmentLevel) {
					it.remove();
					break;
				}
			}
		}

		ItemStack result = new ItemStack(items[0].itemID, 1, items[0].getItemDamage());
		SameEnchindex =0;
		for (int i = 0; i < alist.size(); i++) {
			EnchantmentData data = alist.get(i);
			if(SameEnch[SameEnchindex]!=null && SameEnch[SameEnchindex].equals(data.enchantmentobj))
			{
				System.out.println("LvUpBonus.");
				result.addEnchantment(data.enchantmentobj, data.enchantmentLevel+1);
				SameEnchindex++;
			}
			else
			{
				result.addEnchantment(data.enchantmentobj, data.enchantmentLevel);
			}
		}
		boolean flag;
		if (result.getItem().isDamageable() && items[0].stackSize == 1 && items[1].stackSize == 1) {
			int a1 = result.getMaxDamage() - items[0].getItemDamage();
			int a2 = result.getMaxDamage() - items[1].getItemDamage();
			int a3 = result.getMaxDamage() - (a1 + a2);
			if (a3 < 0) {
				a3 = 0;
			}
			result.setItemDamage(a3);
		}
		SameEnchindex=0;
		return result;
	}

	private static ArrayList getShapelessRecipe(ItemStack enchItem, ItemStack subItem)
	{
		ArrayList list = new ArrayList();
		list.add(enchItem.copy());
		list.add(enchItem.copy());
		list.add(subItem.copy());
		return list;
	}

	private static ItemStack getOutputItem(ItemStack enchItem)
	{
		ItemStack itemstack = enchItem.copy();
		if (itemstack.getItemDamage() == -1) {
			itemstack.setItemDamage(0);
		}
		return itemstack;
	}

	private static int getMaxEnchantmentLevel(int i, ItemStack aitemstack[])
	{
		int j = 0;
		int intarray[] = new int[]{0,0};
		ItemStack aitemstack1[] = aitemstack;
		int k = aitemstack1.length;
		for (int l = 0; l < k; l++) {
			ItemStack itemstack = aitemstack1[l];
			int i1 = EnchantmentHelper.getEnchantmentLevel(i, itemstack);
			intarray[l] = (i1 > 0)? i1:0;
			if (i1 > j) {
				j = i1;
			}
		}
		if(intarray[0] == intarray[1] && intarray[0] != 0)
		{
			SameEnch[SameEnchindex] =  Enchantment.enchantmentsList[i];
			SameEnchindex++;
			System.out.println("Same Lv");
		}
		return j;
	}
}
