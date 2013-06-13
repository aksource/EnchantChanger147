package ak.EnchantChanger;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import ak.EnchantChanger.Client.ClientProxy;
//public class BlockMaterialize extends Block implements ITextureProvider
public class EcBlockMaterialize extends BlockContainer
{
	public EcBlockMaterialize(int par1)
	{
		super(par1, 0, Material.rock);
		setHardness(5F);
		setResistance(2000.0F);
		setBlockName("EnchantChanger");
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        this.setLightOpacity(0);
	}
	public int idDropped(int i, Random random, int j)
    {
        return this.blockID;
    }
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	public String getTextureFile()
    {
            return EnchantChanger.EcTerrain;
    }
	public boolean isOpaqueCube()
    {
        return false;
    }

	public int quantityDropped(Random random)
    {
        return 1;
    }

	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return this.getBlockTextureFromSide(par1);
    }
	public int getBlockTextureFromSide(int par1)
    {
        return par1 == 0 ? this.blockIndexInTexture + 17 : (par1 == 1 ? this.blockIndexInTexture : this.blockIndexInTexture + 16);
    }
	public void addCreativeItems(ArrayList itemList)
    {
            itemList.add(new ItemStack(this, 1, 0));
    }

	public TileEntity getBlockEntity()
    {
        return new EcTileEntityMaterializer();
    }

	/**
    * Called upon block activation (right click on the block.)
    */
	//@SideOnly(Side.CLIENT)
   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
   {
		par5EntityPlayer.openGui(EnchantChanger.instance, 0, par1World, par2, par3, par4);
		return true;
   }

	/**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
    }
	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new EcTileEntityMaterializer();
	}

}