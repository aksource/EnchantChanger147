package ak.EnchantChanger;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.Item;

public class EcWorldGenerator implements IWorldGenerator
{
	private static Item ItemMat = (Item) EnchantChanger.ItemMat;
	private static int materiamax = EnchantChanger.materiamax;

	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		generateSurface(world, random, chunkX,chunkZ);
	}
	public void generateSurface(World world, Random random, int chunkX, int chunkZ)
    {
    	if(!GenerateTreasure(world, random, chunkX,chunkZ))
    	{
    		GenerateTreasure(world, random, chunkX,chunkZ);
    	}
    }
    public static boolean GenerateTreasure(World var0, Random var1, int var2, int var3)
    {
        HashMap var4 = new HashMap();
        int HashMapKey=0;
        boolean Ret = false;
        var4.put(Integer.valueOf(HashMapKey), new ItemStack((Block) ItemMat, 1, materiamax));
        ++HashMapKey;
        var4.put(Integer.valueOf(HashMapKey), new ItemStack((Block) ItemMat, 1, materiamax+1));
        ++HashMapKey;
        var4.put(Integer.valueOf(HashMapKey), new ItemStack((Block) ItemMat, 1, materiamax+2));
        ++HashMapKey;
        var4.put(Integer.valueOf(HashMapKey), new ItemStack((Block) ItemMat, 1, materiamax+3));
        ++HashMapKey;
        var4.put(Integer.valueOf(HashMapKey), new ItemStack((Block) ItemMat, 1, materiamax+4));
        ++HashMapKey;
        Chunk chunk = var0.getChunkFromBlockCoords(var2, var3);
        Iterator TileEntityIt = chunk.chunkTileEntityMap.values().iterator();

        while (TileEntityIt.hasNext())
        {
            TileEntity tileentity = (TileEntity)TileEntityIt.next();

            if (tileentity != null && tileentity instanceof TileEntityChest)
            {
                TileEntityChest chest = (TileEntityChest)tileentity;

                for (int var11 = 0; var11 < chest.getSizeInventory(); ++var11)
                {
                    if (chest.getStackInSlot(var11) == null)
                    {
                        int var12 = var1.nextInt(HashMapKey * 3);
                        chest.setInventorySlotContents(var11, (ItemStack)var4.get(Integer.valueOf(var12)));
                        Ret = true;
                    }
                }
            }
        }

        return Ret;
    }
}