package ak.EnchantChanger;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import ak.EnchantChanger.Client.ClientProxy;

public class EcItemExExpBottle extends Item
{

	public EcItemExExpBottle(int par1) {
		super(par1);

	}
	public String getTextureFile()
    {
            return EnchantChanger.EcSprites;
    }
	public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(new EcEntityExExpBottle(par2World, par3EntityPlayer));
        }

        return par1ItemStack;
    }
    @Override
    public Item setIconIndex(int par1)
    {
        this.iconIndex = par1;
        return this;
    }
}