package ak.EnchantChanger;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

import ak.EnchantChanger.Client.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.*;
public class EcItemUltimateWeapon extends EcItemSword implements IItemRenderer
{
	private int ultimateWeaponDamage;

    public EcItemUltimateWeapon(int par1)
    {
        super(par1, EnumToolMaterial.EMERALD);
        this.setMaxDamage(-1);
        this.ultimateWeaponDamage =  0;
    }
    @Override
    public int getDamageVsEntity(Entity par1Entity)
    {
        return this.ultimateWeaponDamage;
    }
    /**
     * Called when the player Left Clicks (attacks) an entity.
     * Processed before damage is done, if return value is true further processing is canceled
     * and the entity is not attacked.
     *
     * @param stack The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return True to cancel the rest of the interaction.
     */
    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
    {
    	int playerhealth = player.getHealth();
    	int playermaxhealth =  player.getMaxHealth();
    	float healthratio = playerhealth / playermaxhealth;
    	int mobmaxhealth = 0;
    	if(entity instanceof EntityLiving)
    	{
    		mobmaxhealth = MathHelper.floor_float(((EntityLiving) entity).getMaxHealth()/3)+1;
        	if(player instanceof EntityPlayer && healthratio >= 1 && mobmaxhealth > this.ultimateWeaponDamage+WeaponDamagefromHP(player))
        	{
        		this.ultimateWeaponDamage = mobmaxhealth;
        	}
        	else
        	{
        		this.ultimateWeaponDamage = WeaponDamagefromHP(player);
        	}
    	}
    	else if(entity instanceof EntityDragonPart)
    	{
    		this.ultimateWeaponDamage = 100;
    	}
    	else
    	{
    		this.ultimateWeaponDamage = 10;
    	}
    	return false;
    }

    @Override
   	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
   		return type == ItemRenderType.EQUIPPED;
   	}
   	@Override
   	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
   	{
   		return false;
   	}

   	@Override
   	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
   	{
   		EcModelUltimateWeapon UModel = new EcModelUltimateWeapon();
   		UModel.renderItem(item, (EntityLiving)data[1]);
   	}

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	super.doMagic(par1ItemStack, par2World, par3EntityPlayer);
    	par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
  public int WeaponDamagefromHP(EntityPlayer player)
    {
    	int nowHP = player.getHealth();
    	float damageratio;
    	switch(nowHP)
    	{
    	case 20:
    	case 19:
    	case 18:
    	case 17:
    		damageratio = 1;break;
    	case 16:
    	case 15:
    	case 14:
    	case 13:
    	case 12:
    	case 11:
    		damageratio = 0.7f;break;
    	case 10:
    	case 9:
    	case 8:
    	case 7:
    	case 6:
    	case 5:
    		damageratio = 0.5f;break;
    	case 4:
    	case 3:
    	case 2:
    	case 1:
    		damageratio = 0.3f;break;
		default :damageratio = 0;
    	}
    	int EXPLv = player.experienceLevel;
    	return MathHelper.floor_float((10 + EXPLv/5)*damageratio);

    }
}
