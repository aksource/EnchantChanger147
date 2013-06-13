package ak.EnchantChanger.Client;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import ak.EnchantChanger.CommonTickHandler;
import ak.EnchantChanger.EnchantChanger;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ClientTickHandler implements ITickHandler
{
	private boolean allowLevitatiton = false;
	private boolean isLevitation = false;
	private int flyToggleTimer = 0;
	private boolean jump01 = false;
	private boolean jump02 = false;
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if (type.equals(EnumSet.of(TickType.RENDER)))
		{
			onRenderTick();
		}
		else if (type.equals(EnumSet.of(TickType.CLIENT)))
		{

		}
		else if(type.equals(EnumSet.of(TickType.PLAYER)))
		{
			this.playerTick(((EntityPlayer)tickData[0]).worldObj, (EntityPlayer)tickData[0]);
		}
	}

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.RENDER, TickType.CLIENT, TickType.PLAYER);
        // In my testing only RENDER, CLIENT, & PLAYER did anything on the client side.
        // Read 'cpw.mods.fml.common.TickType.java' for a full list and description of available types
    }

    @Override
    public String getLabel() { return null; }


    public void onRenderTick()
    {

    }

    public void onTickInGUI(GuiScreen guiscreen)
    {

    }
    public void playerTick(World world, EntityPlayer player)
    {

    }
    public void onTickInGame()
    {

    }
}