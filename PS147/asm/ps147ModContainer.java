package PS147.asm;

import java.util.Arrays;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.ModMetadata;



public class ps147ModContainer extends DummyModContainer
{
    public ps147ModContainer()
    {
        super(new ModMetadata());

        ModMetadata meta = getMetadata();

        meta.modId       = "ps147Core";
        meta.name        = "ps147COre";
        meta.version     = "1.0.1";
        meta.authorList  = Arrays.asList("A.K.");
        meta.description = "";
        meta.url         = "";
        meta.credits     = "";
    }
}