package compactengine.Client;

import net.minecraft.world.World;
import buildcraft.core.render.RenderingEntityBlocks;
import buildcraft.core.render.RenderingEntityBlocks.EntityRenderIndex;
import buildcraft.energy.TileEngine;
import buildcraft.energy.render.RenderEngine;

import compactengine.CommonProxy;
import compactengine.CompactEngine;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderInformation(){}


	@Override
	public void registerTileEntitySpecialRenderer()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEngine.class, new RenderEngine());
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 0), new RenderEngine(CompactEngine.TEX + "base_wood1.png"));
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 1), new RenderEngine(CompactEngine.TEX + "base_wood2.png"));
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 2), new RenderEngine(CompactEngine.TEX + "base_wood3.png"));
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 3), new RenderEngine(CompactEngine.TEX + "base_wood4.png"));
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 4), new RenderEngine(CompactEngine.TEX + "base_wood5.png"));
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}