package Nanashi.AdvancedTools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Entity_GoldCreeper extends EntityMob
{
	/**
	 * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
	 * weird)
	 */
	private int lastActiveTime;

	/**
	 * The amount of time since the creeper was close enough to the player to ignite
	 */
	private int timeSinceIgnited;
	private int fuseTime = 30;

	/** Explosion radius for this creeper. */
	private int explosionRadius = 3;
	public Entity_GoldCreeper(World var1)
	{
		super(var1);
		this.texture = AdvancedTools.mobTexture + "gcreeper.png";
		this.moveSpeed = 0.38F;
		this.experienceValue = 40;
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new Entity_AIGCreeperSwell(this));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 0.25F, false));
		this.tasks.addTask(5, new EntityAIWander(this, 0.2F));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	public int func_82143_as()
	{
		return this.getAttackTarget() == null ? 3 : 3 + (this.health - 1);
	}
	protected void fall(float par1)
	{
		super.fall(par1);
		this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + par1 * 1.5F);

		if (this.timeSinceIgnited > this.fuseTime - 5)
		{
			this.timeSinceIgnited = this.fuseTime - 5;
		}
	}
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte) - 1));
		this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
	}
	public int getMaxHealth()
	{
		return 20;
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (this.dataWatcher.getWatchableObjectByte(17) == 1)
		{
			par1NBTTagCompound.setBoolean("powered", true);
		}

		par1NBTTagCompound.setShort("Fuse", (short)this.fuseTime);
		par1NBTTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
	}


	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		this.dataWatcher.updateObject(17, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));

		if (par1NBTTagCompound.hasKey("Fuse"))
		{
			this.fuseTime = par1NBTTagCompound.getShort("Fuse");
		}

		if (par1NBTTagCompound.hasKey("ExplosionRadius"))
		{
			this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
		}
	}

	public void onUpdate()
	{
		if (this.entityToAttack == null && this.getPowered())
		{
			this.dataWatcher.updateObject(17, Byte.valueOf((byte)0));
		}

		if (this.isEntityAlive())
		{
			this.lastActiveTime = this.timeSinceIgnited;
			int var1 = this.getCreeperState();

			if (var1 > 0 && this.timeSinceIgnited == 0)
			{
				this.playSound("random.fuse", 1.0F, 0.5F);
			}

			this.timeSinceIgnited += var1;

			if (this.timeSinceIgnited < 0)
			{
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime)
			{
				this.timeSinceIgnited = this.fuseTime;

				if (!this.worldObj.isRemote)
				{
					boolean var2 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

					if (this.getPowered())
					{
						this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(this.explosionRadius * 2), var2);
					}
					else
					{
						this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, var2);
					}

					this.setDead();
				}
			}
		}
		super.onUpdate();

		if (!this.isPotionActive(Potion.moveSpeed) && this.getPowered())
		{
			this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1));
			this.addPotionEffect(new PotionEffect(Potion.jump.id, 20, 1));
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource var1, int var2)
	{
		if (!this.getPowered())
		{
			this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
		}
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER)
		{
			if (var1.getEntity() instanceof EntityPlayer && var2 > 0)
			{
				boolean var3 = false;
				ItemStack var4 = ((EntityPlayer)var1.getEntity()).getCurrentEquippedItem();

				if (var4 != null)
				{
					var3 = var4.itemID == AdvancedTools.LuckLuck.itemID;
				}

				if (var3 || this.rand.nextFloat() < 0.3F && this.health > 0)
				{
					this.dropItem(Item.goldNugget.itemID, 1);
				}
			}
		}
		return super.attackEntityFrom(var1, var2);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.creeper";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "mob.creeperdeath";
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource var1)
	{
		super.onDeath(var1);

		if (var1.getEntity() instanceof EntityPlayer)
		{
			boolean var2 = false;
			ItemStack var3 = ((EntityPlayer)var1.getEntity()).getHeldItem();

			if (var3 != null)
			{
				var2 = var3.itemID == AdvancedTools.LuckLuck.itemID;
			}

			if (var2 || this.rand.nextFloat() < 0.3F)
			{
				this.dropItem(AdvancedTools.RedEnhancer.itemID + this.rand.nextInt(29), 1);
			}
		}
	}
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		return true;
	}
	public boolean getPowered()
	{
		return this.dataWatcher.getWatchableObjectByte(17) == 1;
	}
	@SideOnly(Side.CLIENT)

	/**
	 * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
	 */
	public float getCreeperFlashIntensity(float par1)
	{
		return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * par1) / (float)(this.fuseTime - 2);
	}
	/**
	 * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
	 */
	public int getCreeperState()
	{
		return this.dataWatcher.getWatchableObjectByte(16);
	}
	/**
	 * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	 */
	public void setCreeperState(int par1)
	{
		this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
	}
	/**
	 * Called when a lightning bolt hits the entity.
	 */
	public void onStruckByLightning(EntityLightningBolt var1)
	{
		super.onStruckByLightning(var1);
	}
}
