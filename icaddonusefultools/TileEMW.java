package icaddonusefultools;

import ic2.api.Direction;
import ic2.api.IWrenchable;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEMW extends TileEntity  implements IEnergySource, IEnergySink, IWrenchable{

	private short face;
	private int[] energyInOut = new int[]{32,128,512,2048,Integer.MAX_VALUE};
	private byte kind;
	
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		return false;
	}
	public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		
	}
	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean isAddedToEnergyNet() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public int demandsEnergy() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int getMaxSafeInput() {
		return 0;
	}

	@Override
	public int getMaxEnergyOutput() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);
		kind = data.getByte("kind");
		face = data.getShort("face");
	}
	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);
		data.setByte("kind", kind);
		data.setShort("face", face);
	}

    @Override
	public Packet getDescriptionPacket()
    {
    	return PacketHandler.getPacketTileEMW(this);
    }
	public byte getKind() {
		return kind;
	}

	public void setKind(byte kind) {
		this.kind = kind;
	}
	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
		return getFacing() != side;
	}
	@Override
	public short getFacing() {
		return face;
	}
	@Override
	public void setFacing(short facing) {
		face = facing;
	}
	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
		return false;
	}
	@Override
	public float getWrenchDropRate() {
		return 0.5f;
	}
	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return null;
	}
}