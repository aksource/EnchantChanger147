package compactengine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringTranslate;
import buildcraft.energy.Engine;
import buildcraft.energy.EngineWood;
import buildcraft.energy.TileEngine;

/**
 * エンジン処理はほぼ全て書き直し
 */
public class CompactEngineWood extends EngineWood
{
	private float power;		//1tickごとのエネルギー生産量、圧縮レベル÷20ｘ1.25（赤ピストンで釣り合うように）
	private int no;				//テクスチャ番号
	private int level;			//圧縮レベル8～2048
	private int limitTime;		//爆発までの猶予tick
	private int time = 0;		//爆発破カウンター
	private int alertTime = 0;	//爆発警告タイマー設定
	private double stageRed;	//赤ピストンの閾値
	public static final int explosionPower = CompactEngine.CompactEngineExplosionPowerLevel;
	public static final int explosionTime = CompactEngine.CompactEngineExplosionTimeLevel;
	public static final int alert = CompactEngine.CompactEngineExplosionAlertMinute;
	public static final int[] powerLevel = { 8, 32, 128, 512, 2048 };
	public static final int[][] explosionRanges = {		//爆発力定数、x=Level、y＝爆発力設定レベル
		{ 2, 4,  6, 10,  16 },		//低い
		{ 3, 6, 10, 16,  32 },		//標準
		{ 4, 8, 16, 32, 128 },		//災害
		{ 8,32,128,512,2048 }	};	//崩壊（ワールドに入れなくなる危険あり）
	public static final int[][] explosionTimes = {		//爆発時間定数、x=Level、y＝爆発時間設定レベル
		{120, 90, 60, 30,15 },		//長い
		{ 30, 30, 30, 10, 5 },		//標準
		{ 15, 15, 15,  5, 3 },		//短い
		{  5,  5,  5,  3, 1 }	};	//無理

	public CompactEngineWood(TileEngine tileengine, int meta)
	{
		super(tileengine);
		no = meta + 1;
		this.initCompactEngine(meta);
	}

	private void initCompactEngine(int meta)
	{
		level = powerLevel[meta];
		this.limitTime = (explosionTimes[explosionTime][meta] * 20 * 60);
		this.time = this.limitTime;
		alertTime = alert * 60 * 20;

		power = level / 20f * 1.25f;
		maxEnergy = 1000 * level;			//最大蓄熱量
		maxEnergyExtracted = level / 2;		//ピストン1回当たりの出力MJ
		this.stageRed = (250.0D * this.level);
	}

	//爆発力
	@Override
	public int explosionRange()
	{
		return explosionRanges[explosionPower][no-1];
	}

	@Override
	public void update()
	{
		//スイッチがオフの時、20倍の速さでエンジンが冷える
		if (!tile.isRedstonePowered && energy > power * 20)
		{
			energy -= power * 20;
			this.time += this.time * 20;
			if (this.time > this.limitTime) this.time = this.limitTime;
		}

		//スイッチがオンの時
		if (tile.isRedstonePowered)
		{
			energy += power;

			//赤ピストン時の処理
			if(this.energy >= this.maxEnergy - this.stageRed && !CompactEngine.neverExplosion)
			{
				//爆発カウントダウン
				time--;
				if(alert != 0 && time == alertTime)
				{
					CompactEngine.addChat(StringTranslate.getInstance().translateKey("engine.alert")
							, level, alert, tile.xCoord, tile.yCoord, tile.zCoord);
				}

				if(time <= 0 || energy > maxEnergy + power)
				{
					//エネルギーステージ判定
					computeEnergyStage();
					//爆発メッセージ表示
					CompactEngine.addChat(StringTranslate.getInstance().translateKey("engine.explode")
							, explosionRange(), tile.xCoord, tile.yCoord, tile.zCoord);
					//エネルギー加算メソッド経由で、BCの爆発処理を呼び出す
					addEnergy(0);
				}else{
				}
			}else{
				this.time = this.limitTime;
			}
		}
	}

	//将来的に機能拡張する際の予備、現在は未使用
	public int getTime()
	{
		return this.time;
	}

	//energyStage判定、タイマーが0以下の時、爆発ステージに設定
	@Override
	protected void computeEnergyStage()
	{
		super.computeEnergyStage();
		if (this.time <= 0)
		{
			this.energyStage = Engine.EnergyStage.Explosion;
		}
	}

	//受入限界
	@Override
	public int maxEnergyReceived()
	{
		return 50 * level;
	}

	//1.4.7から追加された温度取得メソッド。普段は呼ばれないので、温度は熱量とタイマーから算出
	@Override
	public int getHeat()
	{
		return this.energy >= this.maxEnergy - this.stageRed ?
			(int)(this.maxEnergy - this.stageRed * ((double)this.time / this.limitTime)) : (int)this.energy;
	}

	//爆発タイマーをNBTに保存／呼び出し
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		this.time = nbttagcompound.getInteger("time");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setInteger("time", this.time);
	}

	//木のエンジンの2倍の速さで動く
	@Override
	public float getPistonSpeed()
	{
		switch (getEnergyStage().ordinal())
		{
			case 0:
				return 0.02F;
			case 1:
				return 0.04F;
			case 2:
				return 0.08F;
			case 3:
				return 0.16F;
			default:
				return 0;
		}
	}

	@Override
	public String getTextureFile()
	{
		return "/texture/base_wood"+no+".png";
	}

}
