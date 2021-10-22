package idealindustrial.hooks;

import gloomyfolken.hooklib.minecraft.PrimaryClassTransformer;


public class HookLoader extends gloomyfolken.hooklib.minecraft.HookLoader {


    @Override
    public String[] getASMTransformerClass() {
        return new String[]{PrimaryClassTransformer.class.getName()};
    }

    public static boolean neiGui, ec2Faces, gtMats, mineIcon, ae2SpatialFix, opis, worldMultiThread;

    @Override
    protected void registerHooks() {
        II_PatchConfig.load();
        if (neiGui)
            registerHookContainer("idealindustrial.hooks.II_AE2NeiPatch");
        if (ec2Faces)
            registerHookContainer("idealindustrial.hooks.II_FluidInterfacePatch");
        if (gtMats)
            registerHookContainer("idealindustrial.hooks.II_RemapGTMaterialsPatch");
        if (ae2SpatialFix)
            registerHookContainer("idealindustrial.hooks.II_AE2SpatialPatch");
        if (mineIcon)
            registerHookContainer("idealindustrial.hooks.II_GameTitlePatch");
        if (opis)
            registerHookContainer("idealindustrial.hooks.II_OpisPatch");
        if (worldMultiThread)
            registerHookContainer("idealindustrial.hooks.II_WorldMultithreadingPatch");
        if (true)
            registerHookContainer("idealindustrial.hooks.II_NeiIdePatch");
        registerHookContainer("idealindustrial.hooks.II_GUI_Chat_Patch");

    }
}
