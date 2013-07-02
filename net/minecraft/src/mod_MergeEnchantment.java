package net.minecraft.src;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class mod_MergeEnchantment extends BaseMod {
    public static CraftingManager cm;
    public static Item items[] = {
        Item.pickaxeDiamond,// Item.pickaxeGold, Item.pickaxeSteel, Item.pickaxeStone, Item.pickaxeWood,
        Item.axeDiamond,// Item.axeGold, Item.axeSteel, Item.axeStone, Item.axeWood,
        Item.shovelDiamond,// Item.shovelGold, Item.shovelSteel, Item.shovelStone, Item.shovelWood,
        //Item.hoeDiamond, Item.hoeGold, Item.hoeSteel, Item.hoeStone, Item.hoeWood,
        Item.swordDiamond,// Item.swordGold, Item.swordSteel, Item.swordStone, Item.swordWood,
        //Item.helmetChain, Item.helmetDiamond, Item.helmetGold, Item.helmetLeather, Item.helmetSteel,
        Item.plateChain,// Item.plateDiamond, Item.plateGold, Item.plateLeather, Item.plateSteel,
        //Item.legsChain, Item.legsDiamond, Item.legsGold, Item.legsLeather, Item.legsSteel,
        //Item.bootsChain, Item.bootsDiamond, Item.bootsGold, Item.bootsLeather, Item.bootsSteel,
        Item.bow,Item.shears
    };
    ItemStack book;

    @MLProp
    public static String extraItemIDs = "";
    @MLProp
    public static String extraBaseIDs = "";

    @MLProp
    public static String priority = "after:*";

    @Override
    public String getVersion()
    {
        return "1.2";
    }

    @Override
    public String getPriorities()
    {
        return priority;
    }

    @Override
    public void load()
    {
        book = new ItemStack(Item.book);
        cm = CraftingManager.getInstance();
        cm.getRecipeList().add(new AddEnchantmentRecipes());
//        List craftlist = ModLoader.getPrivateValue(CraftingManager.class, cm, "recipes");
//        craftlist.add(new AddEnchantmentRecipes());
//        ModLoader.setPrivateValue(CraftingManager.class, cm, "recipes", craftlist);
        for (int i = 0; i < items.length; i++) {
            addMergeEnchantmentRecipe(new ItemStack(items[i], 1, -1), book);
        }
        registerExtraItems(extraItemIDs, 0);
        registerExtraItems(extraBaseIDs, 256);
    }

    private void registerExtraItems(String str, int offset)
    {
        String[] idList = str.split(",");
        for (int i = 0; i < idList.length; i++) {
            if (idList[i].equals("")) {
                break;
            }
            if (idList[i].indexOf(':') < 0) {
                int id = Integer.parseInt(idList[i]);
                addMergeEnchantmentRecipe(new ItemStack(id + offset, 1, -1), book);
            } else {
                String[] pair = idList[i].split(":");
                int id = Integer.parseInt(pair[0]);
                int damage = Integer.parseInt(pair[1]);
                addMergeEnchantmentRecipe(new ItemStack(id + offset, 1, damage), book);
            }
        }
    }

    public boolean addMergeEnchantmentRecipe(ItemStack enchItem, ItemStack subItem)
    {
        if (enchItem.isItemEqual(subItem) && !enchItem.getHasSubtypes()) {
            return false;
        }
        cm.getRecipeList().add(new MergeEnchantmentRecipes(enchItem.copy(), subItem.copy()));

        System.out.print("[MergeEnchantment]recipe: ");
        System.out.println(enchItem);
        return true;
    }
}
