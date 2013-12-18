package icaddonusefultools;

import ic2.api.Items;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEMW extends BlockContainer{
	
	public BlockEMW(int ID)
	{
		super(ID, Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEMW();
	}
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Items.getItem("advancedMachine").itemID;
    }
	@Override
    public int damageDropped(int par1)
    {
        return 0;
    }
	@Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
        if(tile != null && tile instanceof TileEMW)
        {
        	return ((TileEMW)tile).onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
        }
        else
        	return false;
    }
	@Override
	public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		if(tile != null && tile instanceof TileEMW)
			((TileEMW)tile).onEntityWalking(par1World, par2, par3, par4, par5Entity);
	}
}