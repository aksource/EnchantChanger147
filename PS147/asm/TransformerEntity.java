package PS147.asm;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cpw.mods.fml.relauncher.FMLRelauncher;
import cpw.mods.fml.relauncher.IClassTransformer;

// Opcodes : インプリメントすると、ASMによるバイトコード定数にアクセスするのに便利です。
// 必須ではありません。不用な場合は implements から削除してください。

public class TransformerEntity implements IClassTransformer
{
	// 改変対象のクラスの完全修飾名です。
	// 後述でMinecraft.jar内の難読化されるファイルを対象とする場合の簡易な取得方法を紹介します。
	private static final String TARGET_CLASS_NAME = "lq";

	// クラスがロードされる際に呼び出されるメソッドです。
	@Override
	public byte[] transform(String name, byte[] bytes)
	{
		// FMLRelauncher.side() : Client/Server どちらか一方のを対象とする場合や、
		// 一つのMODで Client/Sever 両方に対応したMODで、この値を判定して処理を変える事ができます。
		// 今回は"CLIENT"と比較し、Client側のファイルを対象としている例です。
		// Client側専用のMODとして公開するのであれば、判定は必須ではありません。

		// name : 現在ロードされようとしているクラス名が格納されています。

		if (!FMLRelauncher.side().equals("CLIENT") || !name.equals(TARGET_CLASS_NAME))
		{
			// 処理対象外なので何もしない
			return bytes;
		}
		try
		{
			return replaceClass(bytes);
		}
		catch (Exception e)
		{
			throw new RuntimeException("failed : ps147core loading", e);
		}
	}

	// 下記の想定で実装されています。
	// 対象クラスの bytes を ModifiedTargetClass.class ファイルに置き換える
	private byte[] replaceClass(byte[] bytes) throws IOException
	{
		ZipFile zf = null;
		InputStream zi = null;

		try
		{
			zf = new ZipFile(ps147core.location);

			// 差し替え後のファイルです。coremodのjar内のパスを指定します。
			ZipEntry ze = zf.getEntry("lq.class");

			if (ze != null)
			{
				zi = zf.getInputStream(ze);
				bytes = new byte[(int) ze.getSize()];
				zi.read(bytes);
			}

			return bytes;
		}
		finally
		{
			if (zi != null)
			{
				zi.close();
			}

			if (zf != null)
			{
				zf.close();
			}
		}
	}

}