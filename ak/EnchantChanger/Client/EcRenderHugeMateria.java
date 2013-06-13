package ak.EnchantChanger.Client;

import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ak.EnchantChanger.EcTileEntityHugeMateria;
import ak.EnchantChanger.EnchantChanger;
@SideOnly(Side.CLIENT)
public class EcRenderHugeMateria extends TileEntitySpecialRenderer
{
	private EcModelHMateria Hmateria = new EcModelHMateria();
	private String texture = EnchantChanger.EcHugetex;
	private float angle;
	private float height;

	protected void bindTextureByName(String par1Str)
    {
        RenderEngine var2 = this.tileEntityRenderer.renderEngine;

        if (var2 != null)
        {
            var2.bindTexture(var2.getTexture(par1Str));
        }
    }
	public void doRender(EcTileEntityHugeMateria par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.angle = par1TileEntity.angle;
		this.height = MathHelper.sin(angle);
		this.bindTextureByName(texture);
		GL11.glPushMatrix();
//		GL11.glEnable(GL11.GL_BLEND);
//		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//		GL11.glTranslated(par2 + 0.45, par4-0.1, par6 + 0.45);
		GL11.glTranslated(par2+1.2, par4+1, par6-0.25);
//		Tessellator var9 = Tessellator.instance;
//		var9.setBrightness(220);
//		GL11.glPushMatrix();
//		GL11.glScalef(1.0F,2.0F,1.0F);
		this.Hmateria.render((Entity)null, this.height, 0.0F, 0.0F, 0.0F, 0.0F, 0.038F);
//		GL11.glScalef(1.0F, 1.0F, 1.0F);
//		GL11.glPopMatrix();
//		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		if(par1TileEntity !=null)
			this.doRender((EcTileEntityHugeMateria)par1TileEntity, par2, par4, par6, par8);
	}
}
