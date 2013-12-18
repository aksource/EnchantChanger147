package VRGenerator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class CESolarGui extends GuiContainer
{
	public CESolarTileEntity tileentity;
	public String name;
	public String inv;

	public CESolarGui(EntityPlayer player, CESolarTileEntity tile)
	{
		super(new CESolarContainer(player, tile));
		tileentity = tile;

	}
//	public CESolarGui(CESolarContainer containersolargenerator)
//	{
//		super(containersolargenerator);
//		container = containersolargenerator;
//		container.gui = this;
//
//	}

	/**
	 * Draw the foreground layer for the GuiContainer (everythin in front of the items)
	 */
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		tileentity.updateSunVisibility();
		//現在の発電量をGuiに表示
		name = StatCollector.translateToLocal("tile.CESolar.name") + String.format(
				" %d EU/t", ((this.tileentity.sunIsVisible) ? this.tileentity.production: 0));
		inv = StatCollector.translateToLocal("container.inventory");
		fontRenderer.drawString(name, (xSize - fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
		fontRenderer.drawString(inv, 8, (ySize - 96) + 2, 0x404040);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	public void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int k = mc.renderEngine.getTexture("/ic2/sprites/GUISolarGenerator.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(k);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

		if (this.tileentity.sunIsVisible)
		{
			drawTexturedModalRect(l + 80, i1 + 45, 176, 0, 14, 14);
		}
	}
}
