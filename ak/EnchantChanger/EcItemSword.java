package ak.EnchantChanger;

import java.util.ArrayList;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.src.*;
import net.minecraft.world.World;
import net.minecraftforge.client.*;
public class EcItemSword extends ItemSword
{
    public EcItemSword(int par1 , EnumToolMaterial toolMaterial)
    {
        super(par1, toolMaterial);
        this.setTextureFile(EnchantChanger.EcSprites);
    }
    public static void doMagic(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	if(EnchantmentHelper.getEnchantmentLevel(EnchantChanger.EnchantmentMeteoId, par1ItemStack) > 0)
    	{
    		EcItemMateria.Meteo(par2World, par3EntityPlayer);
    	}
    	if(EnchantmentHelper.getEnchantmentLevel(EnchantChanger.EndhantmentHolyId, par1ItemStack) > 0)
    	{
    		EcItemMateria.Holy(par2World, par3EntityPlayer);
    	}
    	if(EnchantmentHelper.getEnchantmentLevel(EnchantChanger.EnchantmentTelepoId, par1ItemStack) > 0)
    	{
    		EcItemMateria.Teleport(par1ItemStack, par2World, par3EntityPlayer);
    	}
    	if(EnchantmentHelper.getEnchantmentLevel(EnchantChanger.EnchantmentThunderId, par1ItemStack) > 0)
    	{
    		EcItemMateria.Thunder(par2World, par3EntityPlayer);
    	}
    }
    public static boolean hasFloat(ItemStack itemstack)
    {
    	if(EnchantmentHelper.getEnchantmentLevel(EnchantChanger.EnchantmentFloatId, itemstack) > 0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
}