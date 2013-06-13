package ak.EnchantChanger;

import java.util.ArrayList;

import ak.EnchantChanger.Client.*;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.src.*;
import net.minecraft.world.World;
import net.minecraftforge.client.*;

public class EcItemZackSword extends EcItemSword implements IItemRenderer
{
	//public static final EcModelZackSword ZModel = new EcModelZackSword();
	//public static final EcModelZackSword2 ZModel = new EcModelZackSword2();

    public EcItemZackSword(int par1)
    {
        super(par1, EnumToolMaterial.IRON);
        this.setMaxDamage(-1);
    }

    @Override
   	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
   		return type == ItemRenderType.EQUIPPED;
   	}
   	@Override
   	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
   		return false;
   	}

   	@Override
   	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
   	{
   		EcModelZackSword2 ZModel = new EcModelZackSword2();
   		ZModel.renderItem(item, (EntityLiving)data[1]);
   	}

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	if(par3EntityPlayer.isSneaking() && CommonTickHandler.LimitBreakCoolDownCount[0] == 0&&(par3EntityPlayer.getHealth() < 3 || par3EntityPlayer.capabilities.isCreativeMode))
        {
        	CommonTickHandler.LimitBreakFlag[0]=true;
        	CommonTickHandler.LimitBreakCount[0]=20*15;
        	CommonTickHandler.LimitBreakCoolDownCount[0]=20*60*3;
        	par3EntityPlayer.addPotionEffect(new PotionEffect(3,CommonTickHandler.LimitBreakCount[0],3));
        	par3EntityPlayer.addChatMessage("LIMIT BREAK!!");
        	return par1ItemStack;
        }
        else
        {
        	super.doMagic(par1ItemStack, par2World, par3EntityPlayer);
        }
    	par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
}
