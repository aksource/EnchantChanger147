package ak.EnchantChanger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EcItemZackSword extends EcItemSword
{
	public EcItemZackSword(int par1)
	{
		super(par1, EnumToolMaterial.IRON);
	}
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
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
}
