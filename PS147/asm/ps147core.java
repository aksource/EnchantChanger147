package PS147.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;


@TransformerExclusions({"PS147.asm"})
public class ps147core implements IFMLLoadingPlugin
{

    static File location;


    @Override
    public String[] getLibraryRequestClass()
    {
        return null;
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[]{"PS147.asm.TransformerBeacon"};
    }

    @Override
    public String getModContainerClass()
    {
        return "PS147.asm.ps147ModContainer";
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
        if (data.containsKey("coremodLocation"))
        {
            location = (File) data.get("coremodLocation");
        }
    }
}