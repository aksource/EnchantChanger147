package ak.EnchantChanger;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import ak.EnchantChanger.Client.ClientProxy;

public class EcItemMaterializer extends Item
{

	public EcItemMaterializer(int par1) {
		super(par1);
		maxStackSize = 1;
        setMaxDamage(0);
	}
	public String getTextureFile()
    {
            return EnchantChanger.EcSprites;
    }
	public boolean hasEffect(ItemStack par1ItemStack)
    {
        return false;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	par3EntityPlayer.openGui(EnchantChanger.instance, EnchantChanger.guiIdMaterializer,par2World,0,0,0);

        return par1ItemStack;
    }
    @Override
    public Item setIconIndex(int par1)
    {
        this.iconIndex = par1;
        return this;
    }
}