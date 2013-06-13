package ak.EnchantChanger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;


public class Packet_EnchantChanger implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player player)
	{
		// @NetworkMod, ならびにgetPacketで設定したチャンネル
		if (packet.channel.equals("EC|CSC"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			EntityPlayer entityPlayer = (EntityPlayer) player;
			if(entityPlayer.inventory.getCurrentItem().getItem() instanceof EcItemCloudSwordCore)
			{
				EcItemCloudSwordCore sword = (EcItemCloudSwordCore) entityPlayer.getCurrentEquippedItem().getItem();
				sword.readPacketData(data);
			}
		}
		else if (packet.channel.equals("EC|CS"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			EntityPlayer entityPlayer = (EntityPlayer) player;
			if(entityPlayer.inventory.getCurrentItem().getItem() instanceof EcItemCloudSword)
			{
				EcItemCloudSword sword = (EcItemCloudSword) entityPlayer.getCurrentEquippedItem().getItem();
				sword.readPacketData(data);
			}
		}
		else if (packet.channel.equals("EC|Levi"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			EnchantChanger.livingeventhooks.readPacketData(data);
		}
	}
	public static Packet getPacketCSC(EcItemCloudSwordCore ItemCloudSwordCore)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		ItemCloudSwordCore.writePacketData(dos);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "EC|CSC"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();

		return packet;
	}
	public static Packet getPacketCS(EcItemCloudSword ItemCloudSword)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		ItemCloudSword.writePacketData(dos);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "EC|CS"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();

		return packet;
	}
	public static Packet getPacketLevi(LivingEventHooks LivingEventhooks)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		LivingEventhooks.writePacketData(dos);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "EC|Levi"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();

		return packet;
	}
}