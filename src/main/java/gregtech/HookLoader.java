package gregtech;

import gloomyfolken.hooklib.minecraft.PrimaryClassTransformer;


public class HookLoader extends gloomyfolken.hooklib.minecraft.HookLoader {


    //TODO:MOVE TO II_CORE
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{PrimaryClassTransformer.class.getName()};
    }

    @Override
    protected void registerHooks() {
        registerHookContainer("gregtech.HookContainer");
    }
}
