package ak.EnchantChanger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IThrowableEntity;

public class EcEntityExExpBottle extends EntityThrowable implements IThrowableEntity
{
    public EcEntityExExpBottle(World par1World)
    {
        super(par1World);
    }

    public EcEntityExExpBottle(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }

    public EcEntityExExpBottle(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.07F;
    }

    protected float func_40077_c()
    {
        return 0.7F;
    }

    protected float func_40074_d()
    {
        return -20.0F;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (!this.worldObj.isRemote)
        {
            this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
            int var2 = 30 + this.worldObj.rand.nextInt(5) + this.worldObj.rand.nextInt(5);

            while (var2 > 0)
            {
                int var3 = EntityXPOrb.getXPSplit(var2);
                var2 -= var3;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var3));
            }

            this.setDead();
        }
    }

	@Override
	public void setThrower(Entity entity)
	{
		// TODO 自動生成されたメソッド・スタブ

	}
}