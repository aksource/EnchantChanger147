package Booster;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IArmorTextureProvider;

public class ItemBooster extends ItemArmor implements IArmorTextureProvider{

	private int id;
	public ItemBooster(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
		id = par1;
		this.setTextureFile(Booster.Tex);
	}
	@Override
	public String getArmorTextureFile(ItemStack itemstack) {
		if(itemstack.getItem().itemID == Booster.BoosterID)
			return Booster.Armor08_1;
		else
			return Booster.Armor20_1;
	}
}
