package VRGenerator;

import ic2.api.IElectricItem;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class CESolarSlot extends Slot
{
	int tier;
    public CESolarSlot(IInventory iinventory, int i, int j, int k, int l)
    {
        super(iinventory, j, k, l);
        tier = i;
    }

    public CESolarSlot(IInventory iinventory, int i, int j, int k)
    {
        this(iinventory, 0x7fffffff, i, j, k);
    }

    public int getSlotStackLimit()
    {
        return 1;
    }

    public boolean isItemValid(ItemStack itemstack)
    {
        return (itemstack.getItem() instanceof ItemBlock && Block.lightValue[itemstack.itemID] >= 7) || isItemValidCharge(itemstack);
    }
    public boolean isItemValidCharge(ItemStack itemstack)
    {
        return (itemstack.getItem() instanceof IElectricItem) && ((IElectricItem)itemstack.getItem()).getTier() <= tier;
    }
}
