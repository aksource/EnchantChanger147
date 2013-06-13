package PS147.asm;

import java.util.Arrays;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.ModMetadata;

// 必ずしも DummyModContainer を継承している必要はありません。
// cpw.mods.fml.common.ModContainer さえ実装していれば、どんなクラスでも構いません。

public class ps147ModContainer extends DummyModContainer
{
    public ps147ModContainer()
    {
        super(new ModMetadata());

        // 他のModと区別するための一意なIDやmodの名前など、MODのメタデータを設定します。
        ModMetadata meta = getMetadata();

        meta.modId       = "transformerBeacon";
        meta.name        = "TransformerBeacon";
        meta.version     = "1.0.0";
        meta.authorList  = Arrays.asList("A.K.");
        meta.description = "";
        meta.url         = "";
        meta.credits     = "";
    }
}