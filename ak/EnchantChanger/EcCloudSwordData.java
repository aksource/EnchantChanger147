package ak.EnchantChanger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class EcCloudSwordData extends WorldSavedData implements IInventory
{
	public ItemStack[] swords = new ItemStack[5];
	private boolean init = false;
	private boolean upDate;

	public EcCloudSwordData(String par1Str)
	{
		super(par1Str);
	}
	public void onUpdate(World var1, EntityPlayer var2)
	{
		if(!this.init)
		{
			this.init = true;
			this.onInventoryChanged();
		}
		if(var1.getWorldTime() % 80l == 0l)
			this.upDate = true;
		if(this.upDate)
		{
			this.markDirty();
			this.upDate = false;
		}
	}
	@Override
	public int getSizeInventory()
	{
		return 5;
	}

	@Override
	public ItemStack getStackInSlot(int var1)
	{
		return swords[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		if(swords[var1] != null)
		{
			ItemStack var3;
			if(swords[var1].stackSize <= var2)
			{
				var3 = swords[var1];
				swords[var1] = null;
				this.onInventoryChanged();
				return var3;
			}
			else
			{
				var3 = this.swords[var1].splitStack(var2);

				if (this.swords[var1].stackSize == 0)
				{
					this.swords[var1] = null;
				}

				this.onInventoryChanged();
				return var3;
			}
		}
		else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		swords[var1] = var2;
	}

	@Override
	public String getInvName() {
		return "CloudSword";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void onInventoryChanged() {
		this.upDate = true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public void readFromNBT(NBTTagCompound var1) {
		NBTTagList var2 = var1.getTagList("Items");
		this.swords = new ItemStack[5];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;

			if (var5 >= 0 && var5 < this.swords.length)
			{
				this.swords[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound var1) {
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.swords.length; ++var3)
		{
			if (this.swords[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				this.swords[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		var1.setTag("Items", var2);
	}
}