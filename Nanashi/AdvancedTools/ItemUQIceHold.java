package Nanashi.AdvancedTools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUQIceHold extends ItemUniqueArms
{
	private int coolTime;

	protected ItemUQIceHold(int var1, EnumToolMaterial var2)
	{
		super(var1, var2);
	}

	protected ItemUQIceHold(int var1, EnumToolMaterial var2, int var3)
	{
		super(var1, var2);
		this.weaponStrength = var3;
	}

	public int getDamageVsEntity(Entity var1)
	{
		return var1 instanceof EntityEnderman ? super.getDamageVsEntity(var1) * 3 : super.getDamageVsEntity(var1);
	}

	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
	{
		super.onUpdate(var1, var2, var3, var4, var5);

		if (this.coolTime > 0){
			--this.coolTime;
		}
	}

	public void onPlayerStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4)
	{
		int var5 = var3.getFoodStats().getFoodLevel();

		if (var5 > 6){
			int var6 = this.getMaxItemUseDuration(var1) - var4;
			float var7 = (float)var6 / 20.0F;
			var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
			int var10;
			int var11;
			int var12;
			int var13;

			if (var7 >= 1.0F){
				int var8 = (int)var3.posX;
				int var9 = (int)var3.posY - 2;
				var10 = (int)var3.posZ;

				if (!var2.canMineBlock(var3, var8, var9, var10)){
					return;
				}

				for (var11 = -1; var11 <= 1; ++var11){
					for (var12 = -6; var12 <= 6; ++var12){
						for (var13 = -6; var13 <= 6; ++var13){
							if (Math.sqrt((double)(var12 * var12 + var13 * var13)) <= 5.8D && var2.isAirBlock(var8 + var12, var9 + 1 + var11, var10 + var13)){
								var2.spawnParticle("explode", (double)(var8 + var12), (double)(var9 + 1 + var11), (double)(var10 + var13), 0.0D, 0.0D, 0.0D);
								Material var14 = var2.getBlockMaterial(var8 + var12, var9 + var11, var10 + var13);

								if (var14 == Material.water && var2.getBlockMetadata(var8, var9 + var11, var10) == 0){
									var2.setBlockWithNotify(var8 + var12, var9 + var11, var10 + var13, Block.ice.blockID);
								}else if (var2.getBlockId(var8 + var12, var9 + 1 + var11, var10 + var13) == 0 && Block.snow.canPlaceBlockAt(var2, var8 + var12, var9 + 1 + var11, var10 + var13)){
									var2.setBlockWithNotify(var8 + var12, var9 + 1 + var11, var10 + var13, Block.snow.blockID);
								}
							}
						}
					}
				}

				List var19 = var2.getEntitiesWithinAABB(EntityLiving.class, var3.boundingBox.expand(5.0D, 1.0D, 5.0D));

				for (var12 = 0; var12 < var19.size(); ++var12){
					EntityLiving var21 = (EntityLiving)var19.get(var12);
					DamageSource var24 = DamageSource.causePlayerDamage(var3);

					if (var21 instanceof EntityEnderman){
						var21.attackEntityFrom(var24, this.weaponStrength * 3);
					}else if (var21 != var3){
						var21.attackEntityFrom(var24, 0);
						Entity_IHFrozenMob var15 = new Entity_IHFrozenMob(var2, var21, var3);

						if (!var2.isRemote){
							var2.spawnEntityInWorld(var15);
						}
					}
				}
			}else{
				MovingObjectPosition var16 = AdvancedTools.setMousePoint(var2, var3);
				boolean var17 = false;

				if (var16 != null && var16.typeOfHit == EnumMovingObjectType.ENTITY){
					Entity var18 = var16.entityHit;

					if (var18 instanceof EntityLiving){
						DamageSource var20 = DamageSource.causePlayerDamage(var3);

						if (var18 instanceof EntityEnderman){
							var18.attackEntityFrom(var20, this.weaponStrength * 3);
						}else{
							var18.attackEntityFrom(var20, 0);
							Entity_IHFrozenMob var22 = new Entity_IHFrozenMob(var2, (EntityLiving)var18, var3);

							if (!var2.isRemote){
								var2.spawnEntityInWorld(var22);
							}
						}
						var17 = true;
					}
				}

				if (!var17){
					var16 = this.getMovingObjectPositionFromPlayer(var2, var3, true);

					if (var16 == null){
						return;
					}

					if (var16.typeOfHit == EnumMovingObjectType.TILE){
						var10 = var16.blockX;
						var11 = var16.blockY;
						var12 = var16.blockZ;

						for (var13 = -3; var13 <= 3; ++var13){
							for (int var25 = -3; var25 <= 3; ++var25){
								if (Math.sqrt((double)(var13 * var13 + var25 * var25)) <= 2.8D && var2.isAirBlock(var10 + var13, var11 + 1, var12 + var25)){
									var2.spawnParticle("explode", (double)(var10 + var13), (double)(var11 + 1), (double)(var12 + var25), 0.0D, 0.0D, 0.0D);
									Material var23 = var2.getBlockMaterial(var10 + var13, var11, var12 + var25);

									if (var23 == Material.water && var2.getBlockMetadata(var10, var11, var12) == 0){
										var2.setBlockWithNotify(var10 + var13, var11, var12 + var25, Block.ice.blockID);
									}else if (var2.getBlockId(var10 + var13, var11 + 1, var12 + var25) == 0 && Block.snow.canPlaceBlockAt(var2, var10 + var13, var11 + 1, var12 + var25)){
										var2.setBlockWithNotify(var10 + var13, var11 + 1, var12 + var25, Block.snow.blockID);
									}
								}
							}
						}
					}
				}
			}

			var1.damageItem(1, var3);

			if (!var3.capabilities.isCreativeMode){
				var3.getFoodStats().addStats(-1, 1.0f);
			}

			var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
			var3.swingItem();
		}
	}

	public EnumAction getItemUseAction(ItemStack var1)
	{
		return EnumAction.bow;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("Ability : Ice Coffin");
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		int var4 = var3.getFoodStats().getFoodLevel();

		if (var4 > 6){
			var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
		}

		return var1;
	}
}
