package ak.EnchantChanger.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ak.EnchantChanger.EnchantChanger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class EcModelZackSword extends ModelBase
{
  //fields
    ModelRenderer Sword;
    //private Minecraft MC = FMLClientHandler.instance().getClient();

    public EcModelZackSword()
    {
        textureWidth = 64;
        textureHeight = 32;
        setTextureOffset("Sword.edge1", 20, 0);
        setTextureOffset("Sword.edge2", 20, 2);
        setTextureOffset("Sword.edge3", 20, 5);
        setTextureOffset("Sword.edge4", 20, 9);
        setTextureOffset("Sword.edge5", 20, 14);
        setTextureOffset("Sword.edge6", 20, 20);
        setTextureOffset("Sword.edge7", 20, 20);
        setTextureOffset("Sword.edge8", 20, 20);
        setTextureOffset("Sword.tsuba", 8, 14);
        setTextureOffset("Sword.tsuka", 12, 22);

        Sword = new ModelRenderer(this, "Sword");
        Sword.setRotationPoint(6F, 3F, -4F);
        setRotation(Sword, 0F, 0F, 0F);
        Sword.mirror = true;
          Sword.addBox("edge1", 0F, 23F, 0F, 1, 1, 1);
          Sword.addBox("edge2", 0F, 22F, 0F, 1, 1, 2);
          Sword.addBox("edge3", 0F, 21F, 0F, 1, 1, 3);
          Sword.addBox("edge4", 0F, 20F, 0F, 1, 1, 4);
          Sword.addBox("edge5", 0F, 19F, 0F, 1, 1, 5);
          Sword.addBox("edge6", 0F, 13F, 0F, 1, 6, 6);
          Sword.addBox("edge7", 0F, 7F, 0F, 1, 6, 6);
          Sword.addBox("edge8", 0F, 1F, 0F, 1, 6, 6);
          Sword.addBox("tsuba", -0.5F, 0F, -0.5F, 2, 1, 7);
          Sword.addBox("tsuka", 0F, -6F, 2.5F, 1, 6, 1);
      }
/**
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
	  Minecraft mc = mod_EnchantChanger.mc;
	  GL11.glPushMatrix();
      GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/mod_EnchantChanger/SephirothSword.png"));
	  super.render(entity, f, f1, f2, f3, f4, f5);
	  setRotationAngles(f, f1, f2, f3, f4, f5);
      Sword.render(f5);
  }
*/
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
  }

  	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}
  	@SideOnly(Side.CLIENT)
	public void renderItem(ItemStack pitem, EntityLiving pentity) {

  		Minecraft MC = Minecraft.getMinecraft();
  		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, MC.renderEngine.getTexture(EnchantChanger.EcSwordPNG));


	    if (pentity instanceof EntityPlayer && ((EntityPlayer)pentity).isUsingItem()) {
			//Guard
	    	//GL11.glTranslatef(-1.0F, 0.1F, 0.3F);
			GL11.glRotatef(25.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
			//ViewChange
			if (MC.gameSettings.thirdPersonView == 0) {
				GL11.glTranslatef(1.3F, 0.0F, 0.5F);
				GL11.glRotatef(70F, 0F, 0F, 1F);
			}
		} else {
			//ViewChange
			if (MC.gameSettings.thirdPersonView == 0) {
				GL11.glTranslatef(1F, -1F, 0.5F);
				GL11.glRotatef(90F, 0F, 1F, 0F);
				GL11.glRotatef(20F, 0F, 0F, 1F);
			}
		}

	    GL11.glTranslatef(0F, 0F, 0F);
	    GL11.glScalef(0.07F, 0.12F, 0.12F);
	    GL11.glRotatef(0.0F, 1.0F, 1.0F, 0.0F);
	    GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
	    GL11.glTranslatef(0.05F, 0.0F, 0.0F);

	    Sword.render(1.0F);
	    GL11.glPopMatrix();
	    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
}
