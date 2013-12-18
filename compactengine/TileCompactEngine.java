package compactengine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.core.network.PacketUpdate;
import buildcraft.core.proxy.CoreProxy;
import buildcraft.energy.Engine;
import buildcraft.energy.TileEngine;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.Pipe;
import buildcraft.transport.pipes.PipePowerWood;

public class TileCompactEngine extends TileEngine
{
    public Engine newEngine(int i)
    {
        if (4 >= i && i >= 0)
        {
            return new CompactEngineWood(this, i);
        }
        return null;
    }

	@Override
    public void initialize()
    {
		if (!CoreProxy.proxy.isRenderWorld(worldObj))
        {
            if (engine == null)
            {
                createEngineIfNeeded();
            }
			super.initialize();
        }
    }

	//接続された木のエネルギーパイプの性能を、2048倍5台に耐えられるように拡張
	private int initFace = -1;
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(initFace == orientation) return;

		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		if (orientation == 0) --y;
		if (orientation == 1) ++y;
		if (orientation == 2) --z;
		if (orientation == 3) ++z;
		if (orientation == 4) --x;
		if (orientation == 5) ++x;
		Pipe pipe = BlockGenericPipe.getPipe(worldObj, x, y, z);
		if(pipe != null && pipe instanceof PipePowerWood)
		{
			((PipePowerWood)pipe).getPowerProvider().configure(50, 1, 10000, 1, 10000);
			initFace = orientation;
		}else{
			initFace = -1;
		}
    }

	//Engineのインスタンス生成を再定義
	private void createEngineIfNeeded()
	{
		if (engine == null)
		{
			int i = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			engine = newEngine(i);
			engine.orientation = ForgeDirection.VALID_DIRECTIONS[orientation];
			worldObj.notifyBlockChange(xCoord, yCoord, zCoord, CompactEngine.blockID_CompactEngine);
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		createEngineIfNeeded();
		return super.getDescriptionPacket();
	}

	@Override
	public void handleDescriptionPacket(PacketUpdate packet) {
		createEngineIfNeeded();
		super.handleDescriptionPacket(packet);
	}

	@Override
	public void handleUpdatePacket(PacketUpdate packet) {
		createEngineIfNeeded();
		super.handleUpdatePacket(packet);
	}


	//リログ時の動きを揃えるために、progressをリセット
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		engine.progress = 0;
	}

	//木エネルギーパイプに接続できるように、判定をオーバーライド
	@Override
	public boolean isPipeConnected(ForgeDirection with) {
		return with.ordinal() != orientation;
	}

	@Override
	public boolean isBurning()
	{
		return false;
	}

	@Override
	public int getScaledBurnTime(int i)
	{
		return 0;
	}

	@Override
	public int getSizeInventory()
	{
		return 0;
	}

}
