package compactengine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.core.CreativeTabBuildCraft;
import buildcraft.energy.Engine;
import buildcraft.energy.TileEngine;
import buildcraft.transport.PipeTransport;
import buildcraft.transport.PipeTransportPower;
import buildcraft.transport.TileGenericPipe;
import buildcraft.transport.pipes.PipePowerWood;

public class ItemEnergyChecker extends Item
{
	private static StringTranslate trans = StringTranslate.getInstance();
	public ItemEnergyChecker(int par1)
	{
		super(par1);
		setIconIndex(5);
		setCreativeTab(CreativeTabBuildCraft.tabBuildCraft);
	}

	//エンジンのGUIを出さないように、Firstで設定
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			int id = world.getBlockId(x, y, z);
			TileEntity tile = world.getBlockTileEntity(x, y, z);

			//対象がエンジンだった場合
			if(tile instanceof TileEngine)
			{
				Engine engine = ((TileEngine)tile).engine;
				//最大出力とピストン速度からMJ/tを算出
				float mjt = engine.maxEnergyExtracted / (1.28f / engine.getPistonSpeed());
				//温度を取得。温度設定がないエンジンは、蓄熱量から温度を算出
				int heat = engine.getHeat();
				if(heat == 0){
					heat = (int)(engine.energy / engine.maxEnergy * 100);
				}else{
					heat = (int)(heat / 10);
				}
				if(world.isRemote)
				{
					CompactEngine.addChat(
							trans.translateKey("energyChecker.maxPower") + ": %1.2f MJ/t  " +
									trans.translateKey("energyChecker.energy") + ": %1.0f / %d MJ  " +
									trans.translateKey("energyChecker.heat") + ": %d\u00B0C / %d\u00B0C"
									, (float)mjt, engine.energy, engine.maxEnergy, heat, engine.maxEnergy / 10);
				}
				return true;
			}
			//対象がエネルギーを受け取れるパイプやマシンだった場合
			else if(tile instanceof IPowerReceptor && ((IPowerReceptor)tile).getPowerProvider() != null)
			{
				IPowerProvider provider = ((IPowerReceptor)tile).getPowerProvider();
				CompactEngine.addChat(
					trans.translateKey("energyChecker.energy") + ": %1.2f / %d MJ  " +
					trans.translateKey("energyChecker.workEnergy") + ": %d MJ"
					, provider.getEnergyStored(), provider.getMaxEnergyStored(), provider.getMaxEnergyReceived());
				return true;
			}
			//対象がエネルギーパイプパイプだった場合
			else if(tile instanceof TileGenericPipe)
			{
				//停止した木のエネルギーパイプの処理。動作中の木エネルギーパイプはマシン扱いになる
				if(((TileGenericPipe)tile).pipe instanceof PipePowerWood)
				{
					CompactEngine.addChat(trans.translateKey("energyChecker.energy") + ": 10000 / 10000 MJ");
					return true;
				}

				//エネルギーパイプだった場合の処理
				PipeTransport transport = ((TileGenericPipe)tile).pipe.transport;
				if(transport instanceof PipeTransportPower)
				{
					//パイプの全方向のエネルギー量を調べて最大値を求める
					double PowerMax = 0;
					double[] internalPower = ((PipeTransportPower)transport).internalNextPower;
					for(int i = 0; i < 6; i++){if(PowerMax < internalPower[i]) PowerMax = internalPower[i];}
					internalPower = ((PipeTransportPower)transport).internalPower;
					for(int i = 0; i < 6; i++){if(PowerMax < internalPower[i]) PowerMax = internalPower[i];}
					CompactEngine.addChat(
						trans.translateKey("energyChecker.pipeEnergy") + ": %1.4f / 10000 MJ", PowerMax);
					return true;
				}
			}
			return false;
		}
		return false;
	}

	@Override
	public String getTextureFile()
	{
		return CompactEngine.texture;
	}

}
