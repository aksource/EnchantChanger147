package VRGenerator;

import ic2.api.Direction;
import ic2.api.IWrenchable;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileSourceEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.api.network.NetworkHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class CETileEntityGenerator extends TileEntity implements INetworkDataProvider, INetworkUpdateListener, IWrenchable, IEnergySource
{
    private boolean active;
    private short facing;
    public boolean prevActive;
    public short prevFacing;
    public int production;
    private Random random = new Random();
    public boolean addedToEnergyNet = false;
    public int activityMeter = 0;
    public int ticksSinceLastActiveUpdate;
    public int tier = 1;
    
    public CETileEntityGenerator()
    {
        this.ticksSinceLastActiveUpdate = random.nextInt(256);
    }
    
    public void updateEntity() {
    	if(!addedToEnergyNet)
    	{
    		if(!this.worldObj.isRemote)
    		{
    			EnergyTileLoadEvent event = new EnergyTileLoadEvent(this);
    			MinecraftForge.EVENT_BUS.post(event);
    		}
    		else
    		{
    			NetworkHelper.requestInitialData(this);
    		}
    		this.addedToEnergyNet = true;
    	}
    }
    public int sendEnergy(int send)
    {
      EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, send);
      MinecraftForge.EVENT_BUS.post(event);
      return event.amount;
    }
    public void onChunkUnload()
    {
    	if(this.addedToEnergyNet)
    	{
    		EnergyTileUnloadEvent event = new EnergyTileUnloadEvent(this);
    		MinecraftForge.EVENT_BUS.post(event);
    		this.addedToEnergyNet = false;
    	}
    }
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
    }
	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) {
		return true;
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return this.addedToEnergyNet;
	}

	@Override
	public int getMaxEnergyOutput() {
		return production;
	}

	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
		return false;
	}

	@Override
	public short getFacing() {
		return 0;
	}

	@Override
	public void setFacing(short facing) {

	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
		return true;
	}

	@Override
	public float getWrenchDropRate() {
		return 1.0f;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return new ItemStack(this.getBlockType(), 1, this.getBlockMetadata());
	}

	@Override
	public void onNetworkUpdate(String field) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public List<String> getNetworkedFields() {
		List<String> syncList = new ArrayList();
		syncList.add("active");
		return syncList;
	}
//	@Override
//	public Packet getDescriptionPacket()
//	{
//		return PacketHandler.getPacketTileGen(this);
//	}
}