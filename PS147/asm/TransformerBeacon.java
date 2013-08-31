package PS147.asm;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cpw.mods.fml.relauncher.FMLRelauncher;
import cpw.mods.fml.relauncher.IClassTransformer;

public class TransformerBeacon implements IClassTransformer
{
	private static final String TARGET_CLASS_NAME = "ank";

	@Override
	public byte[] transform(String name, byte[] bytes)
	{

		if (!FMLRelauncher.side().equals("CLIENT") || !name.equals(TARGET_CLASS_NAME))
		{
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

	private byte[] replaceClass(byte[] bytes) throws IOException
	{
		ZipFile zf = null;
		InputStream zi = null;

		try
		{
			zf = new ZipFile(ps147core.location);

			ZipEntry ze = zf.getEntry("ank.class");

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