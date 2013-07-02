package ak.EnchantChanger;
import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class EcContainerHugeMateria extends Container {

//	public IInventory materializeSource = new EcSlotMaterializer(this, "MaterializerSource", 9);
//	public IInventory materializeResult = new EcSlotResult(this, "MaterializerResult", 9);
	public int[] vanillaEnch = new int[]{0,1,2,3,4,5,6,16,17,18,19,20,21,32,33,34,35,48,49,50,51};
	public static int ResultSlotNum = 9;
	public static int SourceSlotNum = 9;
	protected EcTileEntityHugeMateria tileEntity;
	protected InventoryPlayer InvPlayer;
	private ArrayList<Integer> ItemEnchList = new ArrayList<Integer>();
	private ArrayList<Integer> ItemEnchLvList = new ArrayList<Integer>();
	private ArrayList<Integer> MateriaEnchList = new ArrayList<Integer>();
	private boolean Debug = EnchantChanger.Debug;
//	private Minecraft mc = EnchantChanger.mc;
	private boolean materiadecLv = EnchantChanger.DecMateriaLv;
	private int lastMaterializingTime = 0;

	public EcContainerHugeMateria (InventoryPlayer inventoryPlayer, EcTileEntityHugeMateria te){
		tileEntity = te;
		InvPlayer = inventoryPlayer;
		addSlotToContainer( new Slot(te, 0,26,17));
		addSlotToContainer( new Slot(te, 1,26,48));
		addSlotToContainer( new Slot(te, 2,53,48));
//		addSlotToContainer( new Slot(te, 3,98,6));
//		addSlotToContainer( new Slot(te, 4,134,6));
		addSlotToContainer( new Slot(te, 3,80,34));
//		addSlotToContainer( new Slot(te, 6,152,34));
//		addSlotToContainer( new Slot(te, 7,98,63));
//		addSlotToContainer( new Slot(te, 8,134,63));
		addSlotToContainer( new EcSlotMakeMateria(te, 4,116,34));
		//commonly used vanilla code that adds the player's inventory
		bindPlayerInventory(inventoryPlayer);
//		this.onCraftMatrixChanged(this.materializeSource);
	}


	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntity.isUseableByPlayer(player);
	}
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.tileEntity.MaterializingTime);
    }
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);

            if (this.lastMaterializingTime != this.tileEntity.MaterializingTime)
            {
                var2.sendProgressBarUpdate(this, 0, this.tileEntity.MaterializingTime);
            }

        }

        this.lastMaterializingTime = this.tileEntity.MaterializingTime;
    }
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
        	this.tileEntity.MaterializingTime = par2;
        }
    }
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}
	@Override
	protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
	{

	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack retitem = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack = slot.getStack();
			retitem = itemstack.copy();

			if (par2 >=0 && par2 < 5)
			{
				if (!this.mergeItemStack(itemstack, 5, 5 + 36, true))
				{
					return null;
				}
			}
			else
			{
				if(itemstack.getItem() instanceof EcItemMasterMateria)
				{
					if (!this.mergeItemStack(itemstack, 0, 1, false))
					{
						return null;
					}
				}
				else if(itemstack.getItem() instanceof EcItemMateria && itemstack.getItemDamage() ==0)
				{
					if (!this.mergeItemStack(itemstack, 1, 2, false))
					{
						return null;
					}
				}
				else if(itemstack.getItem() instanceof Item && itemstack.getItem().itemID == Item.diamond.itemID)
				{
					if (!this.mergeItemStack(itemstack, 2, 3, false))
					{
						return null;
					}
				}
				else
				{
					if (!this.mergeItemStack(itemstack, 3, 4, false))
					{
						return null;
					}
				}
			}

			if (itemstack.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack.stackSize == retitem.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack);
		}

		return retitem;

		//return super.transferStackInSlot(par1EntityPlayer, par2);
	}
	/**
	 * Callback for when the crafting matrix is changed.
	 */
	 @Override
	 public void onCraftMatrixChanged(IInventory par1IInventory)
	{

	}
	 /**
	  * Callback for when the crafting gui is closed.
	  */
	 @Override
	 public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	 {
		 super.onCraftGuiClosed(par1EntityPlayer);

	 }
}