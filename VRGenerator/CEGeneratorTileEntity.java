package VRGenerator;

import net.minecraft.nbt.NBTTagCompound;

public class CEGeneratorTileEntity extends CETileEntityGenerator
{
	//永久光発電機
	//発電レベル（メタデータ-4）
	public int level;
	//総出力EU/t
	public static int[] power = { 2, 8, 32, 128, 512, 2048, 8192, 32768, 131072, 532480};//524288 8192 532480
	//1tickに送電するパケット数
	public static int[] loops = { 1, 1,  1,   1,   1,    4,   16,    64,    256,   1040};
	//電圧（総出力÷パケット数）
	public short output = 1;
	public int packet;

	public CEGeneratorTileEntity(int lv)
	{
		super();
		level = lv;
		init();
	}

	public CEGeneratorTileEntity()
	{
		this(0);
	}

	//発電機性能初期化
	public void init()
	{
		production = power[level];
		packet = loops[level];
		output = (short)(production / packet);
	}

	//行う処理は送電のみの超軽量設計
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		for(int i = 0; i < packet; i++)
		{
			sendEnergy(output);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		level = nbttagcompound.getInteger("level");
		init();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("level", level);
	}
	@Override
	public int getMaxEnergyOutput()
	{
		return output;
	}

	//レンチで100％アイテム化（製造コストが高すぎるための処置）
	@Override
	public float getWrenchDropRate()
	{
		return 1.0F;
	}
}
