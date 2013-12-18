package icaddonusefultools;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler{

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("EMW"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			int x, y, z;
			short face;
			byte kind;
			try
			{
				x = data.readInt();
				y = data.readInt();
				z = data.readInt();
 
				face = data.readShort();
				kind = data.readByte();
 
				World world = ICUsefulTools.proxy.getClientWorld();
				if(world != null)
				{
					TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

					if (tileEntity instanceof TileEMW)
					{
						TileEMW tileEMW = (TileEMW)tileEntity;
						tileEMW.setFacing(face);
						tileEMW.setKind(kind);
					}
				}
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static Packet getPacketTileEMW(TileEMW tile)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
	 
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		short face = tile.getFacing();
		byte kind = tile.getKind();
	 
		try
		{
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeShort(face);
			dos.writeByte(kind);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	 
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "EMW";
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = true;
	 
		return packet;
	}
}