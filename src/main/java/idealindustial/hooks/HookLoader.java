package idealindustial.hooks;

import gloomyfolken.hooklib.minecraft.PrimaryClassTransformer;


public class HookLoader extends gloomyfolken.hooklib.minecraft.HookLoader {


    @Override
    public String[] getASMTransformerClass() {
        return new String[]{PrimaryClassTransformer.class.getName()};
    }

    protected static boolean neiGui, ec2Faces, gtMats;

    @Override
    protected void registerHooks() {
        II_PatchConfig.load();
        if (neiGui)
            registerHookContainer("idealindustrial.hooks.II_AE2NeiPatch");
        if (ec2Faces)
            registerHookContainer("idealindustrial.hooks.II_FluidInterfacePatch");
        if (gtMats)
            registerHookContainer("idealindustrial.hooks.II_RemapGTMaterialsPatch");

    }
}
