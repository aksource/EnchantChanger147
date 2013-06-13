package ak.EnchantChanger;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import ak.EnchantChanger.Client.EcKeyHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;


public class CommonTickHandler implements ITickHandler
{
    public int[] Count= new int[]{0,0,0,0,0,0,0,0,0,0};
    public static int[] LimitBreakCount = new int[]{0,0,0};
    public static int[] LimitBreakCoolDownCount = new int[]{0,0,0};
    public static boolean[] LimitBreakFlag = new boolean[]{false,false,false};
    public boolean MagicKeyActive = false;
	private Entity entity;
	private boolean CSCmode;
	@Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
    	if (type.equals(EnumSet.of(TickType.WORLD)))
        {
            //this.worldTick((World)var2[0]);
    		//this.playerTick(((EntityPlayer)tickData[0]).worldObj, (EntityPlayerMP)tickData[0]);
        }
        else if (type.equals(EnumSet.of(TickType.PLAYER)))
        {
            this.playerTick(((EntityPlayer)tickData[0]).worldObj, (EntityPlayerMP)tickData[0]);
        }
        else if(type.equals(EnumSet.of(TickType.SERVER)))
        {
        	onTickInGame();
        }
    }


    public EnumSet ticks()
    {
    	return EnumSet.of(TickType.WORLD, TickType.PLAYER, TickType.SERVER);
    }

    public String getLabel()
    {
        return null;
    }
    private void playerTick(World world, EntityPlayer player)
    {
        //put here any code that needs to be done server side, like set WorldGeneration, and let entities spawn on the first server tick.
        //also put all the code in a if(world != null && !world.isRemote) statement, makes it so that you don't get NPEs from that.

    	if(player != null)
    	{
//    		GreatGospel(player);
//    		Absorption(world,player);
    		this.LimitBreak(player);
    		this.LimitBreakCoolDown();
//    		this.KeyControl();
//    		if(this.MagicKeyActive)
//    		{
//    			this.checkMagic(world, player);
//    		}

    	}
    }

    private void onTickInGame()
    {
        //put here any code that needs to be done server side, like set WorldGeneration, and let entities spawn on the first server tick.
        //also put all the code in a if(world != null && !world.isRemote) statement, makes it so that you don't get NPEs from that.

    }
    public void GreatGospel(EntityPlayer player)
    {
    	//System.out.println("GG");
    	if(player.capabilities.isCreativeMode)
    	{
    		return;
    	}
    	if((player.getFoodStats().getFoodLevel() < 0 && !EnchantChanger.YouAreTera) || !EcItemMateria.GGEnable)
    	{
    		player.capabilities.disableDamage = false;
    		return;
    	}
    	ItemStack playerItem = player.getCurrentEquippedItem();
    	if(playerItem !=null && playerItem.getItem() instanceof EcItemMateria && playerItem.getItemDamage() == EnchantChanger.materiamax + 1 +  1 * EnchantChanger.MaxLv)
    	{
    		player.capabilities.disableDamage = true;
    		if(MpCount(1,EnchantChanger.GGMptime))
    			player.getFoodStats().addStats(-1, 1.0f);
    	}
    	else
    	{
    		player.capabilities.disableDamage = false;
    	}
    }
    public void Absorption(World world,EntityPlayer player)
    {
    	if(player.getFoodStats().getFoodLevel() < 20)
    	{
    		if(!MpCount(3,EnchantChanger.AbsorpMptime))
    		{
    			return;
    		}
    		ItemStack playerItem = player.getCurrentEquippedItem();
        	if(playerItem !=null && playerItem.getItem() instanceof EcItemMateria && playerItem.getItemDamage() == EnchantChanger.materiamax + 1 +  7 * EnchantChanger.MaxLv)
        	{
	    		List EntityList = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(EnchantChanger.AbsorpBoxSize, EnchantChanger.AbsorpBoxSize, EnchantChanger.AbsorpBoxSize));
	    		for (int i=0; i < EntityList.size();i++)
	        	{
	        		Entity entity=(Entity) EntityList.get(i);
	        		if(entity instanceof EntityLiving)
	        		{
	        			((EntityLiving) entity).attackEntityFrom(DamageSource.generic, 1);
	        			player.getFoodStats().addStats(1, 1.0f);
//	        			System.out.println("Absorp!");
	        		}
	        		else
	        		{
	        			//entity.setDead();
	        		}
	        	}
        	}
    	}
    }
    public void KeyControl()
    {
    	this.MagicKeyActive = EcKeyHandler.MagicKeyDown && !this.MagicKeyActive;
    }
	public void checkMagic(World world, EntityPlayer player)
	{
		ItemStack itemstack = player.getHeldItem();
		if(itemstack != null && itemstack.getItem() instanceof EcItemSword)
		{
			EcItemSword.doMagic(itemstack, world, player);
		}
	}
    public void LimitBreak(EntityPlayer player)
    {
    	for(int i=0;i < this.LimitBreakFlag.length;i++)
    	{
	    	if(this.LimitBreakCount[i] <= 0)
	    	{
	    		if(this.LimitBreakFlag[i])
	    		{
	    			player.addChatMessage("LIMIT BREAK FINISH.");
	    			player.clearActivePotions();
	    			for (int k=0;k<33;k++)
	        		{
	    				player.removePotionEffect(k);
	        		}
	    		}
	    		this.LimitBreakFlag[i]=false;
	    		this.LimitBreakCount[i] = 0;
	    	}
	    	else
	    	{
	    		this.LimitBreakCount[i]--;
	    	}
    	}
    }
    public void LimitBreakCoolDown()
    {
    	for(int i=0;i < this.LimitBreakFlag.length;i++)
    	{
    		if(this.LimitBreakCoolDownCount[i]>0)
    		{
    			this.LimitBreakCoolDownCount[i]--;
    		}
    	}
    }
    public boolean MpCount(int par1, int par2)
    {
    	Count[par1]++;
    	if(Count[par1] > par2)
    	{
    		Count[par1] =0;
    		//System.out.println("MPDecrease");
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
}