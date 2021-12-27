package idealindustrial.impl.hooks;

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
            registerHookContainer("idealindustrial.impl.hooks.II_AE2NeiPatch");
        if (ec2Faces)
            registerHookContainer("idealindustrial.impl.hooks.II_FluidInterfacePatch");
        if (gtMats)
            registerHookContainer("idealindustrial.impl.hooks.II_RemapGTMaterialsPatch");
        if (ae2SpatialFix)
            registerHookContainer("idealindustrial.impl.hooks.II_AE2SpatialPatch");
        if (mineIcon)
            registerHookContainer("idealindustrial.impl.hooks.II_GameTitlePatch");
        if (opis)
            registerHookContainer("idealindustrial.impl.hooks.II_OpisPatch");
        if (worldMultiThread)
            registerHookContainer("idealindustrial.impl.hooks.II_WorldMultithreadingPatch");
        if (true)
            registerHookContainer("idealindustrial.impl.hooks.II_NeiIdePatch");
        registerHookContainer("idealindustrial.impl.hooks.II_GUI_Chat_Patch");
        registerHookContainer("idealindustrial.impl.hooks.II_DrillBreakAnimationHook");

    }
}
