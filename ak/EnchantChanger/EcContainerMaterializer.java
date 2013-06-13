package ak.EnchantChanger;



import java.io.DataOutputStream;
import java.util.ArrayList;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentDigging;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentFireAspect;
import net.minecraft.enchantment.EnchantmentLootBonus;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.enchantment.EnchantmentUntouching;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;

public class EcContainerMaterializer extends Container {

	public static int ResultSlotNum = 9;
	public static int SourceSlotNum = 9;
	public IInventory materializeSource = new EcSlotMaterializer(this, "MaterializerSource", SourceSlotNum);
	public IInventory materializeResult = new EcSlotResult(this, "MaterializerResult", ResultSlotNum);
	public int[] vanillaEnch = new int[]{0,1,2,3,4,5,6,7,16,17,18,19,20,21,32,33,34,35,48,49,50,51};
	protected EcTileEntityMaterializer tileEntity;
	protected InventoryPlayer InvPlayer;
	protected int materiamax = EnchantChanger.materiamax ;
	private ArrayList<Integer> ItemEnchList = new ArrayList<Integer>();
	private ArrayList<Integer> ItemEnchLvList = new ArrayList<Integer>();
	private ArrayList<Integer> MateriaEnchList = new ArrayList<Integer>();
	private ArrayList<Integer> MateriaEnchLvList = new ArrayList<Integer>();
	private int maxlv = EnchantChanger.MaxLv;
    private World worldPointer;
    private EntityPlayer player;
    private int xCoord;
	private int yCoord;
	private int zCoord;
	private int SlotID;
    private boolean materiadecLv = EnchantChanger.DecMateriaLv;


    public EcContainerMaterializer (World par1world, InventoryPlayer inventoryPlayer){
            //tileEntity = te;
            InvPlayer = inventoryPlayer;
            worldPointer = par1world;
            //the Slot constructor takes the IInventory and the slot number in that it binds to
            //and the x-y coordinates it resides on-screen
    		addSlotToContainer(new EcSlotItemToEnchant(this, this.materializeResult, this.materializeSource, 0, 35, 17));
    		addSlotToContainer(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 1, 8, 36));
    		addSlotToContainer(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 2, 26, 36));
    		addSlotToContainer(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 3, 44, 36));
    		addSlotToContainer(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 4, 62, 36));
    		addSlotToContainer(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 5, 8, 54));
    		addSlotToContainer(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 6, 26, 54));
    		addSlotToContainer(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 7, 44, 54));
    		addSlotToContainer(new EcSlotItemMateria(this, this.materializeResult, this.materializeSource, 8, 62, 54));
    		addSlotToContainer(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 0, 125, 17));
    		addSlotToContainer(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 1, 98, 36));
    		addSlotToContainer(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 2, 116, 36));
    		addSlotToContainer(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 3, 134, 36));
    		addSlotToContainer(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 4, 152, 36));
    		addSlotToContainer(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 5, 98, 54));
    		addSlotToContainer(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 6, 116, 54));
    		addSlotToContainer(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 7, 134, 54));
    		addSlotToContainer(new EcSlotEnchantedItem(this, this.materializeSource, this.materializeResult, 8, 152, 54));
            //commonly used vanilla code that adds the player's inventory
            bindPlayerInventory(inventoryPlayer);
            this.onCraftMatrixChanged(this.materializeSource);
    }


    @Override
    public boolean canInteractWith(EntityPlayer player) {return true;}

	@Override
	protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer){}
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
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
    	ItemStack retitem = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack = slot.getStack();
			retitem = itemstack.copy();

			if (par2 >=0 && par2 < ResultSlotNum + SourceSlotNum)
			{
				if (!this.mergeItemStack(itemstack, ResultSlotNum + SourceSlotNum, ResultSlotNum + SourceSlotNum + 36, true))
				{
					return null;
				}
			}
			else
			{
				if(itemstack.getItem() instanceof EcItemMateria)
				{
					for(int i=1;i<this.SourceSlotNum;i++)
					{
						if(!((Slot)this.inventorySlots.get(i)).getHasStack())
						{
							((Slot)this.inventorySlots.get(i)).putStack(itemstack.copy());
							itemstack.stackSize--;
							i=this.SourceSlotNum;
						}
					}
				}
				else if (((Slot)this.inventorySlots.get(0)).getHasStack() || !(itemstack.getItem() instanceof Item))
				{
					return null;
				}
				else if (itemstack.hasTagCompound() && itemstack.stackSize == 1)
				{
					((Slot)this.inventorySlots.get(0)).putStack(itemstack.copy());
					itemstack.stackSize = 0;
				}
				else if (itemstack.stackSize >= 1)
				{
					((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack.itemID, 1, itemstack.getItemDamage()));
					--itemstack.stackSize;
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
    	ItemStack enchitem = this.materializeSource.getStackInSlot(0);
		 if (enchitem != null)
		 {
			 if(! (enchitem.getItem() instanceof Item))
			 {
				 return;
			 }
			 NBTTagList enchOnItem = enchitem.getEnchantmentTagList();
			 int itemdmg = enchitem.getItemDamage();
			 float dmgratio = (enchitem.getMaxDamage() == 0)? 1: (enchitem.getMaxDamage() - itemdmg) / enchitem.getMaxDamage();
			 int itemID = enchitem.itemID;
//			 ItemStack Result = new ItemStack(itemID, 1, itemdmg);
			 ItemStack Result = enchitem.copy();
			 if(Result.hasTagCompound())
			 {
				 Result.getTagCompound().removeTag("ench");
			 }
			 if(enchOnItem != null)
			 {
				 for (int i = 0; i < enchOnItem.tagCount(); ++i)
				 {
					 if(((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl") > 0)
					 {
						 this.ItemEnchList.add((int) ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("id"));
						 this.ItemEnchLvList.add((int) ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl"));
						 if(i >=8)
						 {
							 Result.addEnchantment(Enchantment.enchantmentsList[this.ItemEnchList.get(i)], this.ItemEnchLvList.get(i));
						 }
					 }
				 }
			 }
			 if(this.checkMateriafromSlot(materializeSource))
			 {
				 for(int i=1;i<this.materializeSource.getSizeInventory();i++)
				 {
					 ItemStack materiaitem = this.materializeSource.getStackInSlot(i);
					 if(materiaitem == null)
					 {
						 continue;
					 }
					 int materiaNum = materiaitem.getItemDamage();
					 int enchLv = this.EnchLv(materiaNum);
					 int materiaKind = (materiaitem.getItemDamage()-1) / maxlv;
					 Enchantment enchKind = this.EnchKind(materiaNum);

					 if(!this.func_92037_a(enchKind, enchitem) || !this.CheckLvCap(materiaitem,enchitem))
					 {
						 for(int i1= 0 ; i1< this.ResultSlotNum;i1++)
						 {
							 this.materializeResult.setInventorySlotContents(i1,null);
						 }
						 this.ItemEnchList.clear();
						 this.ItemEnchLvList.clear();
						 this.MateriaEnchList.clear();
						 this.MateriaEnchLvList.clear();
						 return;
					 }
					 if (this.ItemEnchList.size() >0)
					 {
						 boolean flag = false;
						 for(int i2=0;i2 < this.ItemEnchList.size();i2++)
						 {
							 if(!Enchantment.enchantmentsList[this.ItemEnchList.get(i2)].canApplyTogether(enchKind))
							 {
								 this.ItemEnchList.remove(i2);
								 this.ItemEnchLvList.remove(i2);
							 }
						 }
					 }
					 if(! this.MateriaEnchList.contains(enchKind.effectId))
					 {
						 this.MateriaEnchList.add(enchKind.effectId);
						 this.MateriaEnchLvList.add(enchLv);
					 }
				 }
				 for(int i2=0;i2 < this.ItemEnchList.size();i2++)
				 {
					 Result.addEnchantment(Enchantment.enchantmentsList[this.ItemEnchList.get(i2)], this.ItemEnchLvList.get(i2));
				 }
				 for(int i2=0;i2 < this.MateriaEnchList.size();i2++)
				 {
					 Result.addEnchantment(Enchantment.enchantmentsList[this.MateriaEnchList.get(i2)], this.MateriaEnchLvList.get(i2));
				 }
				 this.materializeResult.setInventorySlotContents(0, Result);
				 this.ItemEnchList.clear();
				 this.ItemEnchLvList.clear();
				 this.MateriaEnchList.clear();
				 this.MateriaEnchLvList.clear();

				 for(int i=1;i < ResultSlotNum;i++)
				 {
					 this.materializeResult.setInventorySlotContents(i, null);
				 }
			 }
			 else if(enchOnItem != null)//extract enchantment from Item
			 {
				 int var1Int = 0;
				 for(int i=0;i<8;i++)
				 {
					 if(i < this.ItemEnchList.size())
					 {
						 int declv = (!materiadecLv)?0:(dmgratio > 0.5F)?0:(dmgratio > 0.25F)?1:2;
						 int decreasedLv=(this.ItemEnchLvList.get(i)-declv <0)?0:this.ItemEnchLvList.get(i)-declv;
						 var1Int = MateriaKindFromEnch(this.ItemEnchList.get(i))*maxlv + decreasedLv;
						 this.materializeResult.setInventorySlotContents(i+1, new ItemStack(EnchantChanger.MateriaID, 1, var1Int));
					 }
					 else
						 break;
				 }
				 this.materializeResult.setInventorySlotContents(0, Result);
				 this.ItemEnchList.clear();
				 this.ItemEnchLvList.clear();
				 this.MateriaEnchList.clear();
				 this.MateriaEnchLvList.clear();
			 }
			 else
			 {
				 for(int i=0;i < ResultSlotNum;i++)
				 {
					 this.materializeResult.setInventorySlotContents(i, null);
				 }
				 this.ItemEnchList.clear();
				 this.ItemEnchLvList.clear();
				 this.MateriaEnchList.clear();
				 this.MateriaEnchLvList.clear();
			 }
		 }
    }
    /**
     * Callback for when the crafting gui is closed.
     */
    @Override
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);

        if (!this.worldPointer.isRemote)
        {
        	//System.out.println("client");
        	if (!this.ItemSourceLeft())
        	{
        		for (int var4 = 0; var4 < ResultSlotNum; ++var4)
                {
            		//System.out.println(var2);
            		ItemStack var5 = this.materializeResult.getStackInSlotOnClosing(var4);
                    if (var5 != null)
                    {
                        par1EntityPlayer.dropPlayerItem(var5);
                    }
                }
        	}
        	for (int var2 = 0; var2 < SourceSlotNum; ++var2)
            {
        		//System.out.println(var2);
        		ItemStack var3 = this.materializeSource.getStackInSlotOnClosing(var2);
                if (var3 != null)
                {
                    par1EntityPlayer.dropPlayerItem(var3);
                }
            }

        }
    }

    public boolean ItemResultLeft()
    {
    	if((this.materializeResult.getStackInSlot(0) !=null)||(this.materializeResult.getStackInSlot(1) !=null)||(this.materializeResult.getStackInSlot(2) !=null)||(this.materializeResult.getStackInSlot(3) !=null)||(this.materializeResult.getStackInSlot(4) !=null))
    	{
    		return true;
    	}
    	else
    		return false;
    }
    public boolean ItemSourceLeft()
    {
    	if(this.materializeSource.getStackInSlot(0) != null)
    		return true;
		else
			return false;
    }
    public Enchantment EnchKind(int materiaDamage)
	 {
		 int materiaKind = (materiaDamage-1) / maxlv;
		 if(materiaDamage ==0)
		 {
			 return null;
		 }
		 else if(materiaDamage <= materiamax)
		 {
			 return Enchantment.enchantmentsList[this.vanillaEnch[materiaKind]];
		 }
		 else if(materiaDamage <= materiamax + EnchantChanger.MagicMateriaNumMax)
		 {
			 int magic = (materiaDamage - materiamax - 1) / maxlv;
			 switch(magic)
			 {
			 case 0:return Enchantment.enchantmentsList[EnchantChanger.EnchantmentMeteoId];
			 case 1:return Enchantment.enchantmentsList[EnchantChanger.EndhantmentHolyId];
			 case 2:return Enchantment.enchantmentsList[EnchantChanger.EnchantmentTelepoId];
			 case 3:return Enchantment.enchantmentsList[EnchantChanger.EnchantmentFloatId];
			 case 4:return Enchantment.enchantmentsList[EnchantChanger.EnchantmentThunderId];
			 default :return Enchantment.enchantmentsList[0];
			 }
		 }
		 else if(EnchantChanger.ExtraEnchantIdArray.size() > 0)
		 {
			 int ExtraKind = (materiaDamage - (materiamax + EnchantChanger.MagicMateriaNumMax) - 1) / maxlv;
			 return Enchantment.enchantmentsList[EnchantChanger.ExtraEnchantIdArray.get(ExtraKind)];
		 }
		 else
		 {
			 return Enchantment.enchantmentsList[0];
		 }
	 }
	 public int EnchLv(int materiaDamage)
	 {
		 if(materiaDamage ==0)
		 {
			 return 0;
		 }
		 else if(materiaDamage <= materiamax)
		 {
			 return (materiaDamage % maxlv == 0)?maxlv:materiaDamage % maxlv;
		 }
		 else if(materiaDamage <= materiamax + EnchantChanger.MagicMateriaNumMax)
		 {
			 return 1;
		 }
		 else if(EnchantChanger.ExtraEnchantIdArray.size() > 0)
		 {
			 return ((materiaDamage - (materiamax + EnchantChanger.MagicMateriaNumMax)) % maxlv == 0)?maxlv:(materiaDamage - (materiamax + EnchantChanger.MagicMateriaNumMax)) % maxlv;
		 }
		 else
		 {
			 return  0;
		 }
	 }
	 public boolean checkMateriafromSlot(IInventory Source)
	 {
		 boolean ret=false;
		 for (int i=0;i<Source.getSizeInventory();i++)
		 {
			 if(Source.getStackInSlot(i) !=null && Source.getStackInSlot(i).getItem() instanceof EcItemMateria)
				ret = true;
		 }
		 return ret;
	 }
    public int MateriaKindFromEnch(int par1)
	 {
		 switch(par1)
		 {
		 case 0:return 0;
		 case 1:return 1;
		 case 2:return 2;
		 case 3:return 3;
		 case 4:return 4;
		 case 5:return 5;
		 case 6:return 6;
		 case 7:return 7;
		 case 16:return 8;
		 case 17:return 9;
		 case 18:return 10;
		 case 19:return 11;
		 case 20:return 12;
		 case 21:return 13;
		 case 32:return 14;
		 case 33:return 15;
		 case 34:return 16;
		 case 35:return 17;
		 case 48:return 18;
		 case 49:return 19;
		 case 50:return 20;
		 case 51:return 21;
		 default:;
		 }
		 if(par1 == EnchantChanger.EnchantmentMeteoId)
		 {
			 return 22;
		 }
		 else if(par1 == EnchantChanger.EndhantmentHolyId)
		 {
			 return 23;
		 }
		 else if(par1 == EnchantChanger.EnchantmentTelepoId)
		 {
			 return 24;
		 }
		 else if(par1 == EnchantChanger.EnchantmentFloatId)
		 {
			 return 25;
		 }
		 else if(par1 == EnchantChanger.EnchantmentThunderId)
		 {
			 return 26;
		 }
		 else
		 {
			 for(int i=0;i < EnchantChanger.ExtraEnchantIdArray.size();i++)
			 {
				 if(par1 == EnchantChanger.ExtraEnchantIdArray.get(i))
				 {
					 return 30 + i;
				 }
			 }
			 return 1000;
		 }
	 }

    public static boolean func_92037_a(Enchantment ench, ItemStack par1ItemStack)
    {
    	if(ench == null)
    	{
    		return false;
    	}else if(ench instanceof EnchantmentDurability)
    	{
    		return par1ItemStack.isItemStackDamageable() ? true : ench.type.canEnchantItem(par1ItemStack.getItem());
    	}
    	else if(ench instanceof EnchantmentDigging)
    	{
    		return par1ItemStack.getItem().itemID == Item.shears.itemID ? true : ench.type.canEnchantItem(par1ItemStack.getItem());
    	}
    	else if(ench instanceof EnchantmentDamage || ench instanceof EnchantmentLootBonus || ench instanceof EnchantmentFireAspect)
    	{
    		return par1ItemStack.getItem() instanceof ItemTool ? true : ench.type.canEnchantItem(par1ItemStack.getItem());
    	}
    	else if(ench instanceof EnchantmentThorns)
    	{
    		return par1ItemStack.getItem() instanceof ItemArmor ? true : ench.type.canEnchantItem(par1ItemStack.getItem());
    	}
    	else if(ench instanceof EnchantmentUntouching)
    	{
    		return par1ItemStack.getItem().itemID == Item.shears.itemID ? true : ench.type.canEnchantItem(par1ItemStack.getItem());
    	}
    	else if(ench instanceof EcEnchantmentMeteo || ench instanceof EcEnchantmentHoly || ench instanceof EcEnchantmentTeleport || ench instanceof EcEnchantmentFloat || ench instanceof EcEnchantmentThunder)
    	{
    		return par1ItemStack.getItem() instanceof EcItemSword;
    	}
    	else
    	{
    		return ench.type.canEnchantItem(par1ItemStack.getItem());
    	}
    }
    public static int getItemInstToInt(ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof ItemSword)
		{
			return 1;
		}
		else if(itemstack.getItem() instanceof ItemBow)
		{
			return 2;
		}
		else if(itemstack.getItem() instanceof ItemTool)
		{
			return 3;
		}
		else if(itemstack.getItem() instanceof ItemArmor)
		{
			return ((ItemArmor)itemstack.getItem()).armorType + 4;
		}
		else
		{
			return ExtraItemCheck(itemstack);
		}
	}
    public static int ExtraItemCheck(ItemStack par1ItemStack)
	{
		int var1 = par1ItemStack.itemID;
		System.out.println(var1);
		for(int i=0;i<EnchantChanger.SwordIdArray.size();i++)
		{
			if(var1 == EnchantChanger.SwordIdArray.get(i))
					return 1;
		}
		for(int i=0;i<EnchantChanger.BowIdArray.size();i++)
		{
			if(var1 == EnchantChanger.BowIdArray.get(i))
					return 2;
		}
		for(int i=0;i<EnchantChanger.ToolIdArray.size();i++)
		{
			if(var1 == EnchantChanger.ToolIdArray.get(i))
					return 3;
		}
		for(int i=0;i<EnchantChanger.ArmorIdArray.size();i++)
		{
			if(var1 == EnchantChanger.ArmorIdArray.get(i))
					return 6;
		}
		return 0;
	}
    public static boolean canMaterializeItemfromtype(ItemStack itemstack, EnumEnchantmentType type)
    {
        Item par1Item = itemstack.getItem();
    	if (type == EnumEnchantmentType.all)
        {
            return true;
        }
        else if (par1Item instanceof ItemArmor)
        {
            if (type == EnumEnchantmentType.armor)
            {
                return true;
            }
            else
            {
                ItemArmor var2 = (ItemArmor)par1Item;
                return var2.armorType == 0 ? type == EnumEnchantmentType.armor_head : (var2.armorType == 2 ? type == EnumEnchantmentType.armor_legs : (var2.armorType == 1 ? type == EnumEnchantmentType.armor_torso : (var2.armorType == 3 ? type == EnumEnchantmentType.armor_feet : false)));
            }
        }
        else
        {
            return par1Item instanceof ItemSword ? type == EnumEnchantmentType.weapon : (par1Item instanceof ItemTool ? type == EnumEnchantmentType.digger : (par1Item instanceof ItemBow ? type == EnumEnchantmentType.bow : false));
        }
    }
    public boolean isValidItem(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		if(par2ItemStack !=null && par2ItemStack.getItem() instanceof EcItemMateria)
		{
			int materiaNum = (par2ItemStack.getItemDamage()< EnchantChanger.materiamax)? par2ItemStack.getItemDamage() % 16 : 16;
			int materiaLv= (par2ItemStack.getItemDamage()< EnchantChanger.materiamax)?MathHelper.floor_float(par2ItemStack.getItemDamage() / 16):(par2ItemStack.getItemDamage()< EnchantChanger.materiamax+EcItemMateria.MagicMateriaNum)? 1: ((par2ItemStack.getItemDamage() -( EnchantChanger.materiamax+EcItemMateria.MagicMateriaNum )) % 10) + 1 ;
			int ItemInst = EcContainerMaterializer.getItemInstToInt(par1ItemStack);
			if(materiaLv ==0)
			{
				return false;
			}
			switch(materiaNum)
			{
			case 0:return false;
			case 1:
				if((ItemInst > 0)&&(ItemInst < 8)&&(ItemInst !=3))
					return true;
				else
					return false;
			case 2:
				if(ItemInst == 1)
					return true;
				else
					return false;
			case 3:
				if(ItemInst == 3)
					return true;
				else
					return false;
			case 4:
				if(ItemInst ==4)
					return true;
				else
					return false;
			case 5:
				if(ItemInst == 3)
					return true;
				else
					return false;
			case 6:
				if ((ItemInst>3)&&(ItemInst < 8))
					return true;
				else
					return false;
			case 7:
				if(ItemInst == 3)
					return true;
				else
					return false;
			case 8:
				if ((ItemInst>3)&&(ItemInst < 8))
					return true;
				else
					return false;
			case 9:
				if((ItemInst == 1)||(ItemInst == 3))
					return true;
				else
					return false;
			case 10:
				if(ItemInst == 4)
					return true;
				else
					return false;
			case 11:
				if(ItemInst == 7)
					return true;
				else
					return false;
			case 12:
				if(ItemInst == 2)
					return true;
				else
					return false;
			case 13:
				if((ItemInst > 0)&&(ItemInst < 8)&&(ItemInst !=3))
					return true;
				else
					return false;
			case 14:
				if((ItemInst == 1)||(ItemInst == 2))
					return true;
				else
					return false;
			case 15:
				if(ItemInst == 1)
					return true;
				else
					return false;
			default:
				if(par2ItemStack.getItemDamage() < EnchantChanger.materiamax + 5 && par1ItemStack.getItem() instanceof EcItemSword)
				{
					return true;
				}
				else if(par2ItemStack.getItemDamage() >= EnchantChanger.materiamax + EcItemMateria.MagicMateriaNum && EnchantChanger.ExtraEnchantIdArray.size() > 0)
				{
					int var2 = par2ItemStack.getItemDamage() - (EnchantChanger.materiamax + EcItemMateria.MagicMateriaNum);
					int var3 = MathHelper.floor_float(var2 / 10);
					EnumEnchantmentType type = Enchantment.enchantmentsList[EnchantChanger.ExtraEnchantIdArray.get(var3)].type;
					if(EcContainerMaterializer.canMaterializeItemfromtype(par1ItemStack, type))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
		}
		else return false;
	}
    public boolean isValidMateria(ItemStack par1ItemStack)
    {
    	ItemStack var1 = this.materializeSource.getStackInSlot(0);
    	if(par1ItemStack.getItemDamage() < EnchantChanger.materiamax)
    	{
	    	int materiaNum = par1ItemStack.getItemDamage() % 16;
	    	int materiaLv = MathHelper.floor_float(par1ItemStack.getItemDamage() /16);
	    	if(materiaLv > 0)
	    	{
	    		if(var1 != null)
	        	{
	        		switch (EcContainerMaterializer.getItemInstToInt(var1))
	        		{
	        		case 0 :return false;
	        		case 1 :
	        			if((materiaNum == 1)||(materiaNum == 2)||(materiaNum == 9)||(materiaNum == 13)||(materiaNum == 14)||(materiaNum == 15))
	        			{
	        				return true;
	        			}
	        			else
	        			{
	        				return false;
	        			}
	        		case 2 :
	        			if((materiaNum == 1)||(materiaNum == 12)||(materiaNum == 13)||(materiaNum == 14))
	        			{
	        				return true;
	        			}
	        			else
	        			{
	        				return false;
	        			}
	        		case 3 :
	        			if((materiaNum == 3)||(materiaNum == 5)||(materiaNum == 7)||(materiaNum == 9))
	        			{
	        				return true;
	        			}
	        			else
	        			{
	        				return false;
	        			}
	        		case 4 :
	        			if((materiaNum == 1)||(materiaNum == 4)||(materiaNum == 6)||(materiaNum == 8)||(materiaNum == 10)||(materiaNum == 13))
	        			{
	        				return true;
	        			}
	        			else
	        			{
	        				return false;
	        			}
	        		case 5 :
	        			if((materiaNum == 1)||(materiaNum == 6)||(materiaNum == 8)||(materiaNum == 13))
	        			{
	        				return true;
	        			}
	        			else
	        			{
	        				return false;
	        			}
	        		case 6 :
	        			if((materiaNum == 1)||(materiaNum == 6)||(materiaNum == 8)||(materiaNum == 13))
	        			{
	        				return true;
	        			}
	        			else
	        			{
	        				return false;
	        			}
	        		case 7 :
	        			if((materiaNum == 1)||(materiaNum == 6)||(materiaNum == 8)||(materiaNum == 11)||(materiaNum == 13))
	        			{
	        				return true;
	        			}
	        			else
	        			{
	        				return false;
	        			}
	        		default : return false;
	        		}
	        	}
	        	else
	        		return true;
	    	}
	    	else return false;
    	}
    	else if(par1ItemStack.getItemDamage() < EnchantChanger.materiamax + 5)
    	{
    		if(var1 == null)
    		{
    			return true;
    		}
    		else if(var1.getItem() instanceof EcItemSword)
    		{
    			return true;
    		}
    		else
    		{
    			return false;
    		}
    	}
    	else if(par1ItemStack.getItemDamage() >= EnchantChanger.materiamax + EcItemMateria.MagicMateriaNum && EnchantChanger.ExtraEnchantIdArray.size() > 0)
    	{
    		if(var1 == null)
    		{
    			return true;
    		}
    		else
    		{
	    		int var2 = par1ItemStack.getItemDamage() -(EnchantChanger.materiamax + EcItemMateria.MagicMateriaNum);
	    		int var3 = MathHelper.floor_float(var2 / 10);
	    		EnumEnchantmentType type = Enchantment.enchantmentsList[EnchantChanger.ExtraEnchantIdArray.get(var3)].type;
				if(EcContainerMaterializer.canMaterializeItemfromtype(var1, type))
				{
					return true;
				}
				else
				{
					return false;
				}
    		}
    	}
    	else
    	{
    		return false;
    	}

    }
 	public boolean CheckLvCap(ItemStack par1ItemStack, ItemStack par2ItemStack)
	 {
		 if(EnchantChanger.LevelCap)
		 {
			 if ( par2ItemStack == null || par1ItemStack.getItemDamage() ==0)
			 {
				 return true;
			 }
			 else if(par1ItemStack.getItemDamage() <= materiamax)
			 {
				 int MateriaKind = (par1ItemStack.getItemDamage()-1) / maxlv;
				 int enchLv = par1ItemStack.getItemDamage() % maxlv;
				 int CapLv = Enchantment.enchantmentsList[this.vanillaEnch[MateriaKind]].getMaxLevel();
				 return (enchLv <= CapLv)? true:false;
			 }
			 else if(par1ItemStack.getItemDamage() > materiamax + EnchantChanger.MagicMateriaNumMax && EnchantChanger.ExtraEnchantIdArray.size() > 0)
			 {
				 int var1=(par1ItemStack.getItemDamage() -(materiamax + EnchantChanger.MagicMateriaNumMax)-1) / maxlv;
				 int var2 = par1ItemStack.getItemDamage() % maxlv;
				 return (var2 <= Enchantment.enchantmentsList[EnchantChanger.ExtraEnchantIdArray.get(var1)].getMaxLevel())?true:false;
			 }
			 else
			 {
				 return true;
			 }
		 }
		 else return true;
	 }
}