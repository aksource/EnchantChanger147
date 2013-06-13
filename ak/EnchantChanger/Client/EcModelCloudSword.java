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

import ak.EnchantChanger.EcItemCloudSwordCore;
import ak.EnchantChanger.EnchantChanger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class EcModelCloudSword extends ModelBase
{
	//fields
	ModelRenderer Blade1;
    ModelRenderer Blade2;
    ModelRenderer Core;
    ModelRenderer tsuka;
    ModelRenderer organix;
    ModelRenderer Batterfly1;
    ModelRenderer rune1;
    ModelRenderer Batterfly2;
    ModelRenderer rune2;


    public EcModelCloudSword()
    {
    	textureWidth = 64;
        textureHeight = 64;
        setTextureOffset("Blade1.edge1-1", 0, 0);
        setTextureOffset("Blade1.edge1-2", 0, 2);
        setTextureOffset("Blade1.edge1-3", 0, 0);
        setTextureOffset("Blade1.edge1-4", 0, 0);
        setTextureOffset("Blade1.edge1-5", 0, 0);
        setTextureOffset("Blade1.edge1-6", 0, 0);
        setTextureOffset("Blade1.edge1-7", 0, 0);
        setTextureOffset("Blade1.edge1-8", 0, 0);
        setTextureOffset("Blade1.edge1-9", 0, 0);
        setTextureOffset("Blade1.edge1-10", 0, 0);
        setTextureOffset("Blade1.edge1-11", 0, 0);
        setTextureOffset("Blade2.edge2-1", 0, 0);
        setTextureOffset("Blade2.edge2-2", 0, 0);
        setTextureOffset("Blade2.edge2-3", 0, 0);
        setTextureOffset("Blade2.edge2-4", 0, 0);
        setTextureOffset("Blade2.edge2-5", 0, 0);
        setTextureOffset("Blade2.edge2-6", 0, 0);
        setTextureOffset("Blade2.edge2-7", 0, 0);
        setTextureOffset("Blade2.edge2-8", 0, 0);
        setTextureOffset("Blade2.edge2-9", 0, 0);
        setTextureOffset("Blade2.edge2-10", 0, 0);
        setTextureOffset("Blade2.edge2-11", 0, 0);
        setTextureOffset("Core.core1", 8, 0);
        setTextureOffset("Core.core2", 8, 0);
        setTextureOffset("Core.core3", 8, 0);
        setTextureOffset("Core.core4", 8, 0);
        setTextureOffset("Core.core5", 8, 0);
        setTextureOffset("Core.core6", 8, 0);
        setTextureOffset("Core.core7", 8, 0);
        setTextureOffset("Core.core8", 8, 0);
        setTextureOffset("Core.core9", 8, 0);
        setTextureOffset("Core.core10", 8, 0);
        setTextureOffset("Core.core11", 8, 0);
        setTextureOffset("Core.core12", 8, 0);
        setTextureOffset("Core.core13", 8, 0);
        setTextureOffset("Core.core14", 8, 0);
        setTextureOffset("Core.core15", 8, 0);
        setTextureOffset("Core.core16", 15, 4);
        setTextureOffset("Core.core17", 16, 8);
        setTextureOffset("tsuka.tsuka1", 12, 0);
        setTextureOffset("tsuka.tsuka2", 12, 16);
        setTextureOffset("organix.org1", 0, 33);
        setTextureOffset("organix.org2", 0, 33);
        setTextureOffset("organix.org3", 0, 0);
        setTextureOffset("organix.org4", 0, 0);
        setTextureOffset("organix.org5", 0, 0);
        setTextureOffset("organix.org6", 0, 0);
        setTextureOffset("organix.org7", 0, 0);
        setTextureOffset("organix.edge1", 16, 33);
        setTextureOffset("organix.edge2", 16, 33);
        setTextureOffset("organix.edge3", 16, 33);
        setTextureOffset("organix.edge4", 16, 33);
        setTextureOffset("organix.edge5", 16, 33);
        setTextureOffset("organix.edge6", 16, 33);
        setTextureOffset("organix.tsuka", 20, 17);
        setTextureOffset("organix.org8", 0, 0);
        setTextureOffset("organix.org9", 0, 0);
        setTextureOffset("organix.org10", 0, 33);
        setTextureOffset("Batterfly1.bat1-1", 0, 0);
        setTextureOffset("Batterfly1.bat1-2", 0, 0);
        setTextureOffset("Batterfly1.bat1-3", 0, 0);
        setTextureOffset("Batterfly1.bat1-4", 0, 0);
        setTextureOffset("Batterfly1.bat1-5", 0, 0);
        setTextureOffset("Batterfly1.bat1-6", 0, 0);
        setTextureOffset("Batterfly1.bat1-7", 0, 0);
        setTextureOffset("Batterfly1.bat1-8", 0, 0);
        setTextureOffset("Batterfly1.bat1-9", 0, 0);
        setTextureOffset("Batterfly1.tsuka", 20, 17);
        setTextureOffset("Batterfly1.bat1-10", 24, 33);
        setTextureOffset("Batterfly1.bat1-11", 24, 33);
        setTextureOffset("rune1.rune1-1", 19, 39);
        setTextureOffset("rune1.rune1-2", 17, 38);
        setTextureOffset("rune1.rune1-3", 17, 38);
        setTextureOffset("rune1.rune1-4", 17, 38);
        setTextureOffset("rune1.rune1-5", 0, 0);
        setTextureOffset("rune1.rune1-6", 0, 0);
        setTextureOffset("rune1.rune1-7", 0, 0);
        setTextureOffset("rune1.rune1-8", 8, 1);
        setTextureOffset("rune1.rune1-9", 8, 1);
        setTextureOffset("rune1.rune1-10", 8, 1);
        setTextureOffset("rune1.rune1-11", 8, 1);
        setTextureOffset("rune1.rune1-12", 8, 1);
        setTextureOffset("rune1.rune1-13", 0, 0);
        setTextureOffset("rune1.rune1-14", 0, 0);
        setTextureOffset("rune1.rune1-15", 0, 0);
        setTextureOffset("rune1.rune1-16", 0, 0);
        setTextureOffset("rune1.rune1-17", 19, 39);
        setTextureOffset("rune1.rune1-18", 0, 0);
        setTextureOffset("rune1.rune1-19", 8, 1);
        setTextureOffset("rune1.rune1-20", 0, 0);
        setTextureOffset("rune1.tsuka", 20, 17);
        setTextureOffset("rune1.rune1-21", 0, 0);
        setTextureOffset("Batterfly2.bat2-1", 0, 0);
        setTextureOffset("Batterfly2.bat2-2", 0, 0);
        setTextureOffset("Batterfly2.bat2-3", 0, 0);
        setTextureOffset("Batterfly2.bat2-4", 0, 0);
        setTextureOffset("Batterfly2.bat2-5", 0, 0);
        setTextureOffset("Batterfly2.bat2-6", 0, 0);
        setTextureOffset("Batterfly2.bat2-7", 0, 0);
        setTextureOffset("Batterfly2.bat2-8", 0, 0);
        setTextureOffset("Batterfly2.bat2-9", 0, 0);
        setTextureOffset("Batterfly2.tsuka", 20, 17);
        setTextureOffset("Batterfly2.bat2-10", 24, 33);
        setTextureOffset("Batterfly2.bat2-11", 24, 33);
        setTextureOffset("rune2.rune2-1", 19, 39);
        setTextureOffset("rune2.rune2-2", 17, 38);
        setTextureOffset("rune2.rune2-3", 17, 38);
        setTextureOffset("rune2.rune2-4", 17, 38);
        setTextureOffset("rune2.rune2-5", 0, 0);
        setTextureOffset("rune2.rune2-6", 0, 0);
        setTextureOffset("rune2.rune2-7", 0, 0);
        setTextureOffset("rune2.rune2-8", 8, 1);
        setTextureOffset("rune2.rune2-9", 8, 1);
        setTextureOffset("rune2.rune2-10", 8, 1);
        setTextureOffset("rune2.rune2-11", 8, 1);
        setTextureOffset("rune2.rune2-12", 8, 1);
        setTextureOffset("rune2.rune2-13", 0, 0);
        setTextureOffset("rune2.rune2-14", 0, 0);
        setTextureOffset("rune2.rune2-15", 0, 0);
        setTextureOffset("rune2.rune2-16", 0, 0);
        setTextureOffset("rune2.rune2-17", 19, 39);
        setTextureOffset("rune2.rune2-18", 0, 0);
        setTextureOffset("rune2.rune2-19", 8, 1);
        setTextureOffset("rune2.rune2-20", 0, 0);
        setTextureOffset("rune2.tsuka", 20, 17);
        setTextureOffset("rune2.rune2-21", 0, 0);

        Blade1 = new ModelRenderer(this, "Blade1");
        Blade1.setRotationPoint(6F, 13F, -1.5F);
        setRotation(Blade1, 0F, 0F, 0F);
        Blade1.mirror = true;
          Blade1.addBox("edge1-1", 0F, 23F, -0.5F, 1, 1, 1);
          Blade1.addBox("edge1-2", 0F, 22F, -1.533333F, 1, 1, 2);
          Blade1.addBox("edge1-3", -0.5F, 12F, -0.5F, 2, 10, 1);
          Blade1.addBox("edge1-4", -0.5F, 8F, -1.5F, 2, 14, 1);
          Blade1.addBox("edge1-5", 0F, 7F, -2.5F, 1, 15, 1);
          Blade1.addBox("edge1-6", -1F, 5F, -0.5F, 3, 4, 1);
          Blade1.addBox("edge1-7", -1F, 0F, -0.5F, 3, 2, 1);
          Blade1.addBox("edge1-8", -1F, -2F, -1.5F, 3, 10, 1);
          Blade1.addBox("edge1-9", -0.5F, -7.9F, -2.5F, 2, 15, 1);
          Blade1.addBox("edge1-10", -1F, -2F, -0.5F, 3, 1, 1);
          Blade1.addBox("edge1-11", 0F, -7.9F, -3.5F, 1, 14, 1);
        Blade2 = new ModelRenderer(this, "Blade2");
        Blade2.setRotationPoint(6F, 13F, -1.5F);
        setRotation(Blade2, 0F, 0F, 0F);
        Blade2.mirror = true;
          Blade2.addBox("edge2-1", 0F, 23F, 0.5F, 1, 1, 1);
          Blade2.addBox("edge2-2", 0F, 22F, 0.5F, 1, 1, 2);
          Blade2.addBox("edge2-3", -0.5F, 12F, 0.5F, 2, 10, 1);
          Blade2.addBox("edge2-4", -0.5F, 8F, 1.5F, 2, 14, 1);
          Blade2.addBox("edge2-5", 0F, 7F, 2.5F, 1, 15, 1);
          Blade2.addBox("edge2-6", -1F, 5F, 0.5F, 3, 4, 1);
          Blade2.addBox("edge2-7", -1F, 0F, 0.5F, 3, 2, 1);
          Blade2.addBox("edge2-8", -1F, -2F, 1.5F, 3, 10, 1);
          Blade2.addBox("edge2-9", -0.5F, -7.9F, 2.5F, 2, 15, 1);
          Blade2.addBox("edge2-10", -1F, -2F, 0.5F, 3, 1, 1);
          Blade2.addBox("edge2-11", 0F, -7.9F, 3.5F, 1, 14, 1);
        Core = new ModelRenderer(this, "Core");
        Core.setRotationPoint(6F, 13F, -1.5F);
        setRotation(Core, 0F, 0F, 0F);
        Core.mirror = true;
          Core.addBox("core1", 0F, 20F, 0F, 1, 2, 1);
          Core.addBox("core2", 0F, -7.9F, -1F, 1, 30, 1);
          Core.addBox("core3", 0F, -7.9F, 1F, 1, 30, 1);
          Core.addBox("core4", 0F, 18F, 0F, 1, 1, 1);
          Core.addBox("core5", 0F, 16F, 0F, 1, 1, 1);
          Core.addBox("core6", 0F, 14F, 0F, 1, 1, 1);
          Core.addBox("core7", 0F, 12F, 0F, 1, 1, 1);
          Core.addBox("core8", 0F, 10F, 0F, 1, 1, 1);
          Core.addBox("core9", 0F, 8F, 0F, 1, 1, 1);
          Core.addBox("core10", 0F, 5F, 0F, 1, 2, 1);
          Core.addBox("core11", 0F, 3F, 0F, 1, 1, 1);
          Core.addBox("core12", 0F, 1F, 0F, 1, 1, 1);
          Core.addBox("core13", 0F, -2F, 0F, 1, 2, 1);
          Core.addBox("core14", 0F, -4F, 0F, 1, 1, 1);
          Core.addBox("core15", 0F, -7.9F, 0F, 1, 3, 1);
          Core.addBox("core16", -0.5F, -7F, -1.5F, 2, 5, 1);
          Core.addBox("core17", -0.5F, -7F, 1.5F, 2, 5, 1);
        tsuka = new ModelRenderer(this, "tsuka");
        tsuka.setRotationPoint(6F, 13F, -1.5F);
        setRotation(tsuka, 0F, 0F, 0F);
        tsuka.mirror = true;
          tsuka.addBox("tsuka1", -2.5F, -8F, -6F, 6, 3, 13);
          tsuka.addBox("tsuka2", -0.5F, -22F, -0.5F, 2, 14, 2);
        organix = new ModelRenderer(this, "organix");
        organix.setRotationPoint(6F, 13F, -1.5F);
        setRotation(organix, 0F, 0F, 0F);
        organix.mirror = true;
          organix.addBox("org1", -1.5F, 2F, 0.5F, 4, 20, 2);
          organix.addBox("org2", -1F, 22F, 0.5F, 3, 1, 2);
          organix.addBox("org3", -0.5F, 23F, 0.5F, 2, 1, 1);
          organix.addBox("org4", -1F, 3F, 2.5F, 3, 18, 1);
          organix.addBox("org5", -0.5F, 23F, 1.5F, 2, 1, 1);
          organix.addBox("org6", -0.5F, 21F, 2.5F, 2, 2, 1);
          organix.addBox("org7", -0.5F, 3F, 3.5F, 2, 18, 1);
          organix.addBox("edge1", 0F, 24F, 0.5F, 1, 2, 1);
          organix.addBox("edge2", 0F, 24F, 1.5F, 1, 1, 1);
          organix.addBox("edge3", 0F, 23F, 2.5F, 1, 1, 1);
          organix.addBox("edge4", 0F, 21F, 3.5F, 1, 2, 1);
          organix.addBox("edge5", 0F, -2F, 4.5F, 1, 22, 2);
          organix.addBox("edge6", 0F, 20F, 4.5F, 1, 2, 1);
          organix.addBox("tsuka", -0.5F, -4F, 0.5F, 2, 6, 2);
          organix.addBox("org8", -0.5F, -4F, 4.5F, 2, 2, 1);
          organix.addBox("org9", -0.5F, -5F, 3.5F, 2, 2, 1);
          organix.addBox("org10", -0.5F, -6F, 0.5F, 2, 2, 3);
        Batterfly1 = new ModelRenderer(this, "Batterfly1");
        Batterfly1.setRotationPoint(6F, 13F, -1.5F);
        setRotation(Batterfly1, 0F, 0F, 0F);
        Batterfly1.mirror = true;
          Batterfly1.addBox("bat1-1", 1F, 0F, -1.5F, 1, 27, 1);
          Batterfly1.addBox("bat1-2", 1F, 24.53333F, -0.5F, 1, 2, 1);
          Batterfly1.addBox("bat1-3", 1F, 22F, -0.5F, 1, 2, 1);
          Batterfly1.addBox("bat1-4", 1F, 19F, -0.5F, 1, 2, 1);
          Batterfly1.addBox("bat1-5", 1F, 16F, -0.5F, 1, 2, 1);
          Batterfly1.addBox("bat1-6", 1F, 13F, -0.5F, 1, 2, 1);
          Batterfly1.addBox("bat1-7", 1F, 10F, -0.5F, 1, 2, 1);
          Batterfly1.addBox("bat1-8", 1F, 7F, -0.5F, 1, 2, 1);
          Batterfly1.addBox("bat1-9", 1F, 1F, -0.5F, 1, 1, 1);
          Batterfly1.addBox("tsuka", 0.5F, -6F, -4F, 2, 6, 2);
          Batterfly1.addBox("bat1-10", 0.5F, 0F, -3.5F, 2, 28, 2);
          Batterfly1.addBox("bat1-11", 0.5F, 1F, -5.5F, 2, 28, 2);
        rune1 = new ModelRenderer(this, "rune1");
        rune1.setRotationPoint(6F, 13F, -1.5F);
        setRotation(rune1, 0F, 0F, 0F);
        rune1.mirror = true;
          rune1.addBox("rune1-1", 2F, 8F, 0F, 1, 1, 1);
          rune1.addBox("rune1-2", 2F, 7F, 1F, 1, 1, 1);
          rune1.addBox("rune1-3", 2F, 6F, 2F, 1, 1, 1);
          rune1.addBox("rune1-4", 2F, -4F, 3F, 1, 10, 1);
          rune1.addBox("rune1-5", 2F, -4F, 0F, 1, 12, 1);
          rune1.addBox("rune1-6", 2F, -4F, 1F, 1, 11, 1);
          rune1.addBox("rune1-7", 2F, -4F, 2F, 1, 10, 1);
          rune1.addBox("rune1-8", 2.5F, 0F, 0F, 1, 1, 1);
          rune1.addBox("rune1-9", 2.5F, -0.5F, 1F, 1, 1, 1);
          rune1.addBox("rune1-10", 2.5F, -1F, 2F, 1, 1, 1);
          rune1.addBox("rune1-11", 2.5F, -2F, 3F, 1, 1, 1);
          rune1.addBox("rune1-12", 2.5F, -5F, 4F, 1, 3, 1);
          rune1.addBox("rune1-13", 2.5F, -5F, 0F, 1, 5, 1);
          rune1.addBox("rune1-14", 2.5F, -4.5F, 1F, 1, 4, 1);
          rune1.addBox("rune1-15", 2.5F, -5F, 2F, 1, 4, 1);
          rune1.addBox("rune1-16", 2.5F, -5F, 3F, 1, 3, 1);
          rune1.addBox("rune1-17", 2F, 9F, -1F, 1, 1, 1);
          rune1.addBox("rune1-18", 2F, -5F, -1F, 1, 14, 1);
          rune1.addBox("rune1-19", 2.5F, 1F, -1F, 1, 1, 1);
          rune1.addBox("rune1-20", 2.5F, -5F, -1F, 1, 6, 1);
          rune1.addBox("tsuka", 1F, -5F, -3F, 2, 6, 2);
          rune1.addBox("rune1-21", 2F, 1F, -2F, 1, 6, 1);
        Batterfly2 = new ModelRenderer(this, "Batterfly2");
        Batterfly2.setRotationPoint(6F, 13F, -1.5F);
        setRotation(Batterfly2, 0F, 0F, 0F);
        Batterfly2.mirror = true;
          Batterfly2.addBox("bat2-1", -1F, 0F, -1.5F, 1, 27, 1);
          Batterfly2.addBox("bat2-2", -1F, 24.53333F, -0.5F, 1, 2, 1);
          Batterfly2.addBox("bat2-3", -1F, 22F, -0.5F, 1, 2, 1);
          Batterfly2.addBox("bat2-4", -1F, 19F, -0.5F, 1, 2, 1);
          Batterfly2.addBox("bat2-5", -1F, 16F, -0.5F, 1, 2, 1);
          Batterfly2.addBox("bat2-6", -1F, 13F, -0.5F, 1, 2, 1);
          Batterfly2.addBox("bat2-7", -1F, 10F, -0.5F, 1, 2, 1);
          Batterfly2.addBox("bat2-8", -1F, 7F, -0.5F, 1, 2, 1);
          Batterfly2.addBox("bat2-9", -1F, 1F, -0.5F, 1, 1, 1);
          Batterfly2.addBox("tsuka", -1.5F, -6F, -4F, 2, 6, 2);
          Batterfly2.addBox("bat2-10", -1.5F, 0F, -3.5F, 2, 28, 2);
          Batterfly2.addBox("bat2-11", -1.5F, 1F, -5.5F, 2, 28, 2);
        rune2 = new ModelRenderer(this, "rune2");
        rune2.setRotationPoint(6F, 13F, -1.5F);
        setRotation(rune2, 0F, 0F, 0F);
        rune2.mirror = true;
          rune2.addBox("rune2-1", -2F, 8F, 0F, 1, 1, 1);
          rune2.addBox("rune2-2", -2F, 7F, 1F, 1, 1, 1);
          rune2.addBox("rune2-3", -2F, 6F, 2F, 1, 1, 1);
          rune2.addBox("rune2-4", -2F, -4F, 3F, 1, 10, 1);
          rune2.addBox("rune2-5", -2F, -4F, 0F, 1, 12, 1);
          rune2.addBox("rune2-6", -2F, -4F, 1F, 1, 11, 1);
          rune2.addBox("rune2-7", -2F, -4F, 2F, 1, 10, 1);
          rune2.addBox("rune2-8", -2.5F, 0F, 0F, 1, 1, 1);
          rune2.addBox("rune2-9", -2.5F, -0.5F, 1F, 1, 1, 1);
          rune2.addBox("rune2-10", -2.5F, -1F, 2F, 1, 1, 1);
          rune2.addBox("rune2-11", -2.5F, -2F, 3F, 1, 1, 1);
          rune2.addBox("rune2-12", -2.5F, -5F, 4F, 1, 3, 1);
          rune2.addBox("rune2-13", -2.5F, -5F, 0F, 1, 5, 1);
          rune2.addBox("rune2-14", -2.5F, -4.5F, 1F, 1, 4, 1);
          rune2.addBox("rune2-15", -2.5F, -5F, 2F, 1, 4, 1);
          rune2.addBox("rune2-16", -2.5F, -5F, 3F, 1, 3, 1);
          rune2.addBox("rune2-17", -2F, 9F, -1F, 1, 1, 1);
          rune2.addBox("rune2-18", -2F, -5F, -1F, 1, 14, 1);
          rune2.addBox("rune2-19", -2.5F, 1F, -1F, 1, 1, 1);
          rune2.addBox("rune2-20", -2.5F, -5F, -1F, 1, 6, 1);
          rune2.addBox("tsuka", -1F, -5F, -3F, 2, 6, 2);
          rune2.addBox("rune2-21", -2F, 1F, -2F, 1, 6, 1);

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
  		//Minecraft MC = FMLClientHandler.instance().getClient();
  		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, MC.renderEngine.getTexture(EnchantChanger.EcCloudSwordPNG));


	    if (pentity instanceof EntityPlayer && ((EntityPlayer)pentity).isUsingItem()) {
			//Guard
	    	GL11.glTranslatef(0F, 0.2F, 0F);
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
	    if(EcItemCloudSwordCore.ActiveMode)
	    {
	    	//Blade1.setRotationPoint(6F, 13F, -2.5F);
	    	//Blade2.setRotationPoint(6F, 13F, -1.5F);
	    }
	    else
	    {
	    	//Blade1.setRotationPoint(6F, 13F, -1.5F);
	    	//Blade2.setRotationPoint(6F, 13F, -2.5F);
	    }



	    GL11.glTranslatef(0F, 0F, 0F);
	    GL11.glScalef(0.12F, 0.17F, 0.17F);
	    GL11.glRotatef(0.0F, 1.0F, 1.0F, 0.0F);
	    GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
	    //GL11.glTranslatef(0.05F, 0.0F, 0.0F);

	    Blade1.render(0.5f);
	    Blade2.render(0.5f);
	    Core.render(0.5f);
	    tsuka.render(0.5f);
	    organix.render(0.5f);
	    Batterfly1.render(0.5f);
	    rune1.render(0.5f);
	    Batterfly2.render(0.5f);
	    rune2.render(0.5f);
	    GL11.glPopMatrix();
	    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
}
