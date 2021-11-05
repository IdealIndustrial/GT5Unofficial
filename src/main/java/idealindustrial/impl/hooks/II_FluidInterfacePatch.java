package idealindustrial.impl.hooks;

import extracells.tileentity.TileEntityFluidInterface;
import gloomyfolken.hooklib.asm.Hook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class II_FluidInterfacePatch {

    @Hook(injectOnExit = true, isMandatory = true)
    public static void readFromNBT(TileEntityFluidInterface face, NBTTagCompound tag) {
        for (int i = 0; i < face.tanks.length; i++) {
            if (!tag.hasKey("notShittyFluidID" + i))
                continue;
            Fluid fluid = FluidRegistry.getFluid(tag.getString("notShittyFluidID" + i));
            if (fluid == null)
                continue;
            int id = FluidRegistry.getFluidID(fluid);
            face.fluidFilter[i] = id;

        }
    }

    @Hook(injectOnExit = true, isMandatory = true)
    public static void writeToNBTWithoutExport(TileEntityFluidInterface face, NBTTagCompound tag) {
        for (int i = 0; i < face.tanks.length; i++) {
            int id = face.fluidFilter[i];
            Fluid fluid = FluidRegistry.getFluid(id);
            if (fluid == null)
                continue;
            tag.setString("notShittyFluidID" + i, FluidRegistry.getFluidName(fluid));
        }
    }

}
