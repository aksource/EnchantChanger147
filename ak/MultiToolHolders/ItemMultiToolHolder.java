package ak.MultiToolHolders;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;

import java.io.DataOutputStream;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ak.MultiToolHolders.Client.MTHKeyHandler;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMultiToolHolder extends Item implements IItemRenderer
{
	public int SlotNum;
	public ToolHolderData tools;
	private Random rand = new Random();
	private int Slotsize;
	private boolean OpenKeydown;
	private boolean NextKeydown;
	private boolean PrevKeydown;
	private Minecraft mc;
	public ItemMultiToolHolder(int par1, int slot)
	{
		super(par1);
		this.hasSubtypes = true;
		this.setMaxStackSize(1);
		this.Slotsize = slot;
		this.SlotNum = 0;
		this.setTextureFile(MultiToolHolders.itemTexture);
	}
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		String ToolName;
		for(int i = 0;i<Slotsize;i++)
		{
			if(this.tools !=null && this.tools.getStackInSlot(i) != null)
			{
				ToolName =  this.tools.getStackInSlot(i).getDisplayName();
				par3List.add(ToolName);
			}
		}
	}
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
			return this.tools.getStackInSlot(SlotNum).getItem().isFull3D();
		else
			return false;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		if(this.tools != null && this.tools.getStackInSlot(SlotNum) != null)
		{
			IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(this.tools.getStackInSlot(SlotNum), EQUIPPED);
			if(customRenderer != null)
				return customRenderer.shouldUseRenderHelper(type, this.tools.getStackInSlot(SlotNum), helper);
			else
				return helper == ItemRendererHelper.EQUIPPED_BLOCK;
		}
		else
			return helper == ItemRendererHelper.EQUIPPED_BLOCK;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
//		this.tools = this.getData(item, ((EntityLiving) data[1]).worldObj);
		if(this.tools != null && this.tools.getStackInSlot(SlotNum) != null)
		{
			IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(this.tools.getStackInSlot(SlotNum), EQUIPPED);
			if(customRenderer !=null)
				customRenderer.renderItem(type, this.tools.getStackInSlot(SlotNum), data);
			else
				renderToolHolder((EntityLiving) data[1], this.tools.getStackInSlot(SlotNum));
		}
		else
		{
			renderToolHolder((EntityLiving) data[1], item);
		}
	}
	@SideOnly(Side.CLIENT)
	public void renderToolHolder(EntityLiving entity, ItemStack stack)
	{
		mc = Minecraft.getMinecraft();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture(stack.getItem().getTextureFile()));

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        Tessellator var5 = Tessellator.instance;
        int var6 = entity.getItemIcon(stack, 0);
        float var7 = ((float)(var6 % 16 * 16) + 0.0F) / 256.0F;
        float var8 = ((float)(var6 % 16 * 16) + 15.99F) / 256.0F;
        float var9 = ((float)(var6 / 16 * 16) + 0.0F) / 256.0F;
        float var10 = ((float)(var6 / 16 * 16) + 15.99F) / 256.0F;
        float var11 = 0.0F;
        float var12 = 0.3F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-var11, -var12, 0.0F);
        float var13 = 1.5F;
        GL11.glScalef(var13, var13, var13);
        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
        renderItemIn2D(var5, var8, var9, var7, var10, 0.0625F);

        if (stack != null && stack.hasEffect()/* && par3 == 0*/)
        {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("%blur%/misc/glint.png"));
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            float var14 = 0.76F;
            GL11.glColor4f(0.5F * var14, 0.25F * var14, 0.8F * var14, 1.0F);
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            float var15 = 0.125F;
            GL11.glScalef(var15, var15, var15);
            float var16 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
            GL11.glTranslatef(var16, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            renderItemIn2D(var5, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(var15, var15, var15);
            var16 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
            GL11.glTranslatef(-var16, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            renderItemIn2D(var5, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
    public static void renderItemIn2D(Tessellator par0Tessellator, float par1, float par2, float par3, float par4, float par5)
    {
        float var6 = 1.0F;
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 0.0F, 1.0F);
        par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)par1, (double)par4);
        par0Tessellator.addVertexWithUV((double)var6, 0.0D, 0.0D, (double)par3, (double)par4);
        par0Tessellator.addVertexWithUV((double)var6, 1.0D, 0.0D, (double)par3, (double)par2);
        par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)par1, (double)par2);
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 0.0F, -1.0F);
        par0Tessellator.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - par5), (double)par1, (double)par2);
        par0Tessellator.addVertexWithUV((double)var6, 1.0D, (double)(0.0F - par5), (double)par3, (double)par2);
        par0Tessellator.addVertexWithUV((double)var6, 0.0D, (double)(0.0F - par5), (double)par3, (double)par4);
        par0Tessellator.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - par5), (double)par1, (double)par4);
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        int var7;
        float var8;
        float var9;
        float var10;

        /* Gets the width/16 of the currently bound texture, used
         * to fix the side rendering issues on textures != 16 */
        int tileSize = TextureFXManager.instance().getTextureDimensions(GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D)).width / 16;

        float tx = 1.0f / (32 * tileSize);
        float tz = 1.0f /  tileSize;

        for (var7 = 0; var7 < tileSize; ++var7)
        {
            var8 = (float)var7 / tileSize;
            var9 = par1 + (par3 - par1) * var8 - tx;
            var10 = var6 * var8;
            par0Tessellator.addVertexWithUV((double)var10, 0.0D, (double)(0.0F - par5), (double)var9, (double)par4);
            par0Tessellator.addVertexWithUV((double)var10, 0.0D, 0.0D, (double)var9, (double)par4);
            par0Tessellator.addVertexWithUV((double)var10, 1.0D, 0.0D, (double)var9, (double)par2);
            par0Tessellator.addVertexWithUV((double)var10, 1.0D, (double)(0.0F - par5), (double)var9, (double)par2);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(1.0F, 0.0F, 0.0F);

        for (var7 = 0; var7 < tileSize; ++var7)
        {
            var8 = (float)var7 / tileSize;
            var9 = par1 + (par3 - par1) * var8 - tx;
            var10 = var6 * var8 + tz;
            par0Tessellator.addVertexWithUV((double)var10, 1.0D, (double)(0.0F - par5), (double)var9, (double)par2);
            par0Tessellator.addVertexWithUV((double)var10, 1.0D, 0.0D, (double)var9, (double)par2);
            par0Tessellator.addVertexWithUV((double)var10, 0.0D, 0.0D, (double)var9, (double)par4);
            par0Tessellator.addVertexWithUV((double)var10, 0.0D, (double)(0.0F - par5), (double)var9, (double)par4);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 1.0F, 0.0F);

        for (var7 = 0; var7 < tileSize; ++var7)
        {
            var8 = (float)var7 / tileSize;
            var9 = par4 + (par2 - par4) * var8 - tx;
            var10 = var6 * var8 + tz;
            par0Tessellator.addVertexWithUV(0.0D, (double)var10, 0.0D, (double)par1, (double)var9);
            par0Tessellator.addVertexWithUV((double)var6, (double)var10, 0.0D, (double)par3, (double)var9);
            par0Tessellator.addVertexWithUV((double)var6, (double)var10, (double)(0.0F - par5), (double)par3, (double)var9);
            par0Tessellator.addVertexWithUV(0.0D, (double)var10, (double)(0.0F - par5), (double)par1, (double)var9);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, -1.0F, 0.0F);

        for (var7 = 0; var7 < tileSize; ++var7)
        {
            var8 = (float)var7 / tileSize;
            var9 = par4 + (par2 - par4) * var8 - tx;
            var10 = var6 * var8;
            par0Tessellator.addVertexWithUV((double)var6, (double)var10, 0.0D, (double)par3, (double)var9);
            par0Tessellator.addVertexWithUV(0.0D, (double)var10, 0.0D, (double)par1, (double)var9);
            par0Tessellator.addVertexWithUV(0.0D, (double)var10, (double)(0.0F - par5), (double)par1, (double)var9);
            par0Tessellator.addVertexWithUV((double)var6, (double)var10, (double)(0.0F - par5), (double)par3, (double)var9);
        }

        par0Tessellator.draw();
    }
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (par3Entity instanceof EntityPlayer && par5)
		{
			EntityPlayer entityPlayer = (EntityPlayer) par3Entity;
			Side side = FMLCommonHandler.instance().getEffectiveSide();
			if(side.isServer())
			{
				this.tools = this.getData(par1ItemStack, par2World);
				this.tools.onUpdate(par2World, entityPlayer);
				this.tools.onInventoryChanged();
			}
			if (par1ItemStack.hasTagCompound())
	        {
				par1ItemStack.getTagCompound().removeTag("ench");
	        }
			if(this.tools != null && this.tools.getStackInSlot(SlotNum) != null)
			{
				this.tools.getStackInSlot(SlotNum).getItem().onUpdate(this.tools.getStackInSlot(SlotNum), par2World, par3Entity, par4, par5);
				this.setEnchantments(par1ItemStack, this.tools.getStackInSlot(SlotNum));
			}
			if(entityPlayer.openContainer == null || !(entityPlayer.openContainer instanceof ContainerToolHolder))
			{
				if(side.isClient())
				{
					this.NextKeydown = MTHKeyHandler.nextKeydown && !this.NextKeydown;
					this.PrevKeydown = MTHKeyHandler.prevKeydown && !this.PrevKeydown;
					if(this.NextKeydown)
					{
						this.SlotNum++;
						if(this.SlotNum == this.Slotsize)
							this.SlotNum = 0;
					}
					else if(this.PrevKeydown)
					{
						this.SlotNum--;
						if(this.SlotNum == -1)
							this.SlotNum = this.Slotsize - 1;
					}
					this.OpenKeydown = MTHKeyHandler.openKeydown && !this.OpenKeydown;
					PacketDispatcher.sendPacketToServer(PacketHandler.getPacketTool(this));
				}
				if(this.OpenKeydown)
				{

					int GuiID = (this.Slotsize == 3)? MultiToolHolders.guiIdHolder3:(this.Slotsize == 5)? MultiToolHolders.guiIdHolder5:(this.Slotsize == 7)? MultiToolHolders.guiIdHolder7:MultiToolHolders.guiIdHolder9;
					entityPlayer.openGui(MultiToolHolders.instance, GuiID, par2World, 0, 0, 0);
				}
			}
		}
	}
	public ToolHolderData getData(ItemStack var1, World var2)
	{
		String itemName = "Holder" + this.Slotsize;
		int itemDamage = var1.getItemDamage();
		String var3 = String.format("%s_%s", itemName, itemDamage);;
		ToolHolderData var4 = (ToolHolderData)var2.loadItemData(ToolHolderData.class, var3);

		if (var4 == null)
		{
			var4 = new ToolHolderData(var3);
			var4.markDirty();
			var2.setItemData(var3, var4);
		}

		return var4;
	}
	private void makeData(ItemStack var1, World var2)
	{
		String itemName = "Holder" + this.Slotsize;
		var1.setItemDamage(var2.getUniqueDataId(itemName));
		int itemDamage = var1.getItemDamage();
		String var3 = String.format("%s_%s", itemName, itemDamage);;
		ToolHolderData var4 = new ToolHolderData(var3);
		var4.markDirty();
		var2.setItemData(var3, var4);
	}
	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (par1ItemStack.getItem() instanceof ItemMultiToolHolder)
		{
			this.makeData(par1ItemStack, par2World);
		}
	}
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(this.tools.getStackInSlot(SlotNum) != null)
		{
			this.attackTargetEntityWithTheItem(entity, player, this.tools.getStackInSlot(SlotNum));
			return true;
		}
		else
			return false;
	}
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
		{
		boolean ret = this.tools.getStackInSlot(SlotNum).getItem().onItemUseFirst(this.tools.getStackInSlot(SlotNum), player, world, x, y, z, side, hitX, hitY, hitZ);
			if (this.tools.getStackInSlot(SlotNum).stackSize <= 0)
			{
				this.destroyTheItem(player, this.tools.getStackInSlot(SlotNum));
			}
			return ret;
		}
		else
			return false;
	}
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
		{
			boolean ret = this.tools.getStackInSlot(SlotNum).getItem().onItemUse(this.tools.getStackInSlot(SlotNum), par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
			if (this.tools.getStackInSlot(SlotNum).stackSize <= 0)
			{
				this.destroyTheItem(par2EntityPlayer, this.tools.getStackInSlot(SlotNum));
			}
			return ret;
		}
		else
			return false;
	}
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
		{
			this.tools.getStackInSlot(SlotNum).getItem().onPlayerStoppedUsing(this.tools.getStackInSlot(SlotNum), par2World, par3EntityPlayer, par4);
			if (this.tools.getStackInSlot(SlotNum).stackSize <= 0)
			{
				this.destroyTheItem( par3EntityPlayer, this.tools.getStackInSlot(SlotNum));
			}
		}
	}
	@Override
	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
		{
			this.tools.getStackInSlot(SlotNum).getItem().onFoodEaten(this.tools.getStackInSlot(SlotNum), par2World, par3EntityPlayer);
		}
		return par1ItemStack;
	}
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
		{
			this.tools.setInventorySlotContents(SlotNum, this.tools.getStackInSlot(SlotNum).getItem().onItemRightClick(this.tools.getStackInSlot(SlotNum), par2World, par3EntityPlayer));
		}
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
	@Override
    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving)
    {
		if(this.tools != null && this.tools.getStackInSlot(SlotNum) != null)
			return this.tools.getStackInSlot(SlotNum).getItem().itemInteractionForEntity(this.tools.getStackInSlot(SlotNum), par2EntityLiving);
		else
			return false;
    }
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
		{
			return this.tools.getStackInSlot(SlotNum).getItemUseAction();
		}
		else
			return super.getItemUseAction(par1ItemStack);
	}
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
		{
			return this.tools.getStackInSlot(SlotNum).getMaxItemUseDuration();
		}
		else
			return super.getMaxItemUseDuration(par1ItemStack);
	}
//	public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player)
//	{
//		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
//		{
//			this.onPlayerDestroyBlock(X, Y, Z, this.tools.getStackInSlot(SlotNum), player, player.worldObj);
//			return true;
//		}
//		else
//			return false;
//	}
	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block){
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
			return this.tools.getStackInSlot(SlotNum).getStrVsBlock(par2Block);
		else
			return super.getStrVsBlock(par1ItemStack, par2Block);
	}
	@Override
	public float getStrVsBlock(ItemStack stack, Block block, int meta)
	{
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
		{
			return this.tools.getStackInSlot(SlotNum).getItem().getStrVsBlock(this.tools.getStackInSlot(SlotNum), block, meta);
		}
		else
			return super.getStrVsBlock(stack, block, meta);
	}
	@Override
	public int getDamageVsEntity(Entity par1Entity)
	{
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
		{
			return this.tools.getStackInSlot(SlotNum).getItem().getDamageVsEntity(par1Entity);
		}
		else
			return super.getDamageVsEntity(par1Entity);
	}
//	@Override
//	public int getDamageVsEntity(Entity par1Entity, ItemStack itemStack)
//	{
//		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
//		{
//			return this.tools.getStackInSlot(SlotNum).getItem().getDamageVsEntity(par1Entity, this.tools.getStackInSlot(SlotNum));
//		}
//		else
//			return super.getDamageVsEntity(par1Entity, itemStack);
//	}
	public boolean canHarvestBlock(Block par1Block)
	{
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
		{
			return this.tools.getStackInSlot(SlotNum).canHarvestBlock(par1Block);
		}
		else
			return super.canHarvestBlock(par1Block);
	}
	@Override
    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
		if(this.tools !=null && this.tools.getStackInSlot(SlotNum) != null)
		{
			boolean ret = this.tools.getStackInSlot(SlotNum).getItem().onBlockDestroyed(this.tools.getStackInSlot(SlotNum), par2World, par3, par4, par5, par6, par7EntityLiving);
			if (this.tools.getStackInSlot(SlotNum).stackSize <= 0)
			{
				this.destroyTheItem((EntityPlayer) par7EntityLiving, this.tools.getStackInSlot(SlotNum));
			}
			this.tools.onInventoryChanged();
			return ret;
		}
		else
			return super.onBlockDestroyed(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving);
    }
//	public boolean onPlayerDestroyBlock(int par1, int par2, int par3, /*int par4,*/ ItemStack stack, EntityPlayer player, World world)
//	{
//		mc = Minecraft.getMinecraft();
//		if (stack != null && stack.getItem() != null && stack.getItem().onBlockStartBreak(stack, par1, par2, par3, player))
//		{
//			return false;
//		}
//		if (!player.capabilities.allowEdit && !this.canCurrentToolHarvestBlock(par1, par2, par3, player, stack))
//		{
//			return false;
//		}
//		else
//		{
//			Block block = Block.blocksList[world.getBlockId(par1, par2, par3)];
//
//			if (block == null)
//			{
//				return false;
//			}
//			else
//			{
//				world.playAuxSFX(2001, par1, par2, par3, block.blockID + (world.getBlockMetadata(par1, par2, par3) << 12));
//				int i1 = world.getBlockMetadata(par1, par2, par3);
//				boolean flag = block.removeBlockByPlayer(world, player, par1, par2, par3);
//
//				if (flag)
//				{
//					System.out.println("0");
//					block.onBlockDestroyedByPlayer(world, par1, par2, par3, i1);
//				}
//
//				System.out.println("1");
//				//				this.currentBlockY = -1;
//
//				if (!player.capabilities.isCreativeMode)
//				{
//					ItemStack itemstack = stack;
//
//					if (itemstack != null)
//					{
//						itemstack.onBlockDestroyed(world, block.blockID, par1, par2, par3, player);
//
//						if (itemstack.stackSize == 0)
//						{
//							this.destroyTheItem(player, stack);
//						}
//					}
//				}
//
//				return flag;
//			}
//		}
//	}
	public void attackTargetEntityWithTheItem(Entity par1Entity, EntityPlayer player,ItemStack stack)
	{
		if (MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(player, par1Entity)))
		{
			return;
		}
		if (stack != null && stack.getItem().onLeftClickEntity(stack, player, par1Entity))
		{
			return;
		}
		if (par1Entity.canAttackWithItem())
		{
			if (!par1Entity.func_85031_j(player))
			{
				int var2 = stack.getDamageVsEntity(par1Entity);

				if (player.isPotionActive(Potion.damageBoost))
				{
					var2 += 3 << player.getActivePotionEffect(Potion.damageBoost).getAmplifier();
				}

				if (player.isPotionActive(Potion.weakness))
				{
					var2 -= 2 << player.getActivePotionEffect(Potion.weakness).getAmplifier();
				}

				int var3 = 0;
				int var4 = 0;

				if (par1Entity instanceof EntityLiving)
				{
					var4 = this.getEnchantmentModifierLiving(stack, player, (EntityLiving)par1Entity);
					var3 += EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
				}

				if (player.isSprinting())
				{
					++var3;
				}

				if (var2 > 0 || var4 > 0)
				{
					boolean var5 = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null && par1Entity instanceof EntityLiving;

					if (var5)
					{
						var2 += this.rand.nextInt(var2 / 2 + 2);
					}

					var2 += var4;
					boolean var6 = false;
					int var7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);

					if (par1Entity instanceof EntityLiving && var7 > 0 && !par1Entity.isBurning())
					{
						var6 = true;
						par1Entity.setFire(1);
					}

					boolean var8 = par1Entity.attackEntityFrom(DamageSource.causePlayerDamage(player), var2);

					if (var8)
					{
						if (var3 > 0)
						{
							par1Entity.addVelocity((double)(-MathHelper.sin(player.rotationYaw * (float)Math.PI / 180.0F) * (float)var3 * 0.5F), 0.1D, (double)(MathHelper.cos(player.rotationYaw * (float)Math.PI / 180.0F) * (float)var3 * 0.5F));
							player.motionX *= 0.6D;
							player.motionZ *= 0.6D;
							player.setSprinting(false);
						}

						if (var5)
						{
							player.onCriticalHit(par1Entity);
						}

						if (var4 > 0)
						{
							player.onEnchantmentCritical(par1Entity);
						}

						if (var2 >= 18)
						{
							player.triggerAchievement(AchievementList.overkill);
						}

						player.setLastAttackingEntity(par1Entity);

						if (par1Entity instanceof EntityLiving)
						{
							EnchantmentThorns.func_92096_a(player, (EntityLiving)par1Entity, this.rand);
						}
					}

					ItemStack var9 = stack;

					if (var9 != null && par1Entity instanceof EntityLiving)
					{
						var9.hitEntity((EntityLiving)par1Entity, player);

						if (var9.stackSize <= 0)
						{
							this.destroyTheItem(player, stack);
						}
					}

					if (par1Entity instanceof EntityLiving)
					{


						player.addStat(StatList.damageDealtStat, var2);

						if (var7 > 0 && var8)
						{
							par1Entity.setFire(var7 * 4);
						}
						else if (var6)
						{
							par1Entity.extinguish();
						}
					}

					player.addExhaustion(0.3F);
				}
			}
		}
	}
	public void destroyTheItem(EntityPlayer player, ItemStack orig)
	{
		this.tools.setInventorySlotContents(this.SlotNum, (ItemStack)null);
		MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, orig));
	}
	public int getEnchantmentModifierLiving(ItemStack stack, EntityLiving attacker, EntityLiving enemy)
	{
		int calc = 0;
		if (stack != null)
		{
			NBTTagList nbttaglist = stack.getEnchantmentTagList();

			if (nbttaglist != null)
			{
				for (int i = 0; i < nbttaglist.tagCount(); ++i)
				{
					short short1 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("id");
					short short2 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("lvl");

					if (Enchantment.enchantmentsList[short1] != null)
					{
						calc += Enchantment.enchantmentsList[short1].calcModifierLiving(short2, enemy);
					}
				}
			}
		}
		return calc > 0 ? 1 + rand.nextInt(calc) : 0;
	}
	public void setEnchantments(ItemStack ToEnchant, ItemStack Enchanted)
	{
		int EnchNum;
		int EnchLv;
		NBTTagList list = Enchanted.getEnchantmentTagList();
		if(list !=null)
		{
			for (int i = 0; i < list.tagCount(); ++i)
			{
				if(((NBTTagCompound)list.tagAt(i)).getShort("lvl") > 0)
				{
					EnchNum = ((NBTTagCompound)list.tagAt(i)).getShort("id");
					EnchLv = ((NBTTagCompound)list.tagAt(i)).getShort("lvl");
					ToEnchant.addEnchantment(Enchantment.enchantmentsList[EnchNum], EnchLv);
				}
			}
		}
	}
//	public boolean canCurrentToolHarvestBlock(int par1, int par2, int par3, EntityPlayer player, ItemStack stack)
//	{
//		if (player.capabilities.allowEdit)
//		{
//			return true;
//		}
//		else
//		{
//			int l = player.worldObj.getBlockId(par1, par2, par3);
//
//			if (l > 0)
//			{
//				Block block = Block.blocksList[l];
//
//				if (block.blockMaterial.isAlwaysHarvested())
//				{
//					return true;
//				}
//
//				if (stack != null)
//				{
//					ItemStack itemstack = stack;
//
//					if (itemstack.canHarvestBlock(block) || itemstack.getStrVsBlock(block) > 1.0F)
//					{
//						return true;
//					}
//				}
//			}
//
//			return false;
//		}
//	}
	// パケットの読み込み(パケットの受け取りはPacketHandlerで行う)
	public void readPacketData(ByteArrayDataInput data)
	{
		try
		{
			this.SlotNum = data.readInt();
			this.OpenKeydown = data.readBoolean();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// パケットの書き込み(パケットの生成自体はPacketHandlerで行う)
	public void writePacketData(DataOutputStream dos)
	{
		try
		{
			dos.writeInt(SlotNum);
			dos.writeBoolean(OpenKeydown);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}