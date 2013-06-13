package Nanashi.AdvancedTools.client;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import Nanashi.AdvancedTools.AdvancedTools;
import Nanashi.AdvancedTools.CommonProxy;
import Nanashi.AdvancedTools.CommonTickHandler;
import Nanashi.AdvancedTools.Entity_BBFireBall;
import Nanashi.AdvancedTools.Entity_FireZombie;
import Nanashi.AdvancedTools.Entity_GoldCreeper;
import Nanashi.AdvancedTools.Entity_HighSkeleton;
import Nanashi.AdvancedTools.Entity_HighSpeedCreeper;
import Nanashi.AdvancedTools.Entity_IHFrozenMob;
import Nanashi.AdvancedTools.Entity_PGPowerBomb;
import Nanashi.AdvancedTools.Entity_SBWindEdge;
import Nanashi.AdvancedTools.Entity_SkeletonSniper;
import Nanashi.AdvancedTools.Entity_ThrowingKnife;
import Nanashi.AdvancedTools.Entity_ZombieWarrior;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderInformation()
	{
		MinecraftForgeClient.preloadTexture(AdvancedTools.itemTexture);
	    RenderingRegistry.registerEntityRenderingHandler(Entity_BBFireBall.class, new Render_UQMagics());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_IHFrozenMob.class, new Render_UQFreezer());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_PGPowerBomb.class, new Render_UQMagics());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_SBWindEdge.class, new Render_UQMagics());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_ThrowingKnife.class, new RenderThrowingKnife());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_HighSkeleton.class, new RenderBiped(new ModelSkeleton(), 0.5F));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_SkeletonSniper.class, new RenderBiped(new ModelSkeleton(), 0.5F));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_ZombieWarrior.class, new RenderBiped(new ModelZombie(), 0.5F));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_FireZombie.class, new RenderBiped(new ModelZombie(), 0.5F));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_HighSpeedCreeper.class, new RenderCreeper());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_GoldCreeper.class, new RenderGCreeper());
	    TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
	    TickRegistry.registerTickHandler(new CommonTickHandler(), Side.SERVER);
	}

	@Override
	public void registerTileEntitySpecialRenderer()
	{

	}

	@Override
	public World getClientWorld()
	{
	return FMLClientHandler.instance().getClient().theWorld;
	}
}