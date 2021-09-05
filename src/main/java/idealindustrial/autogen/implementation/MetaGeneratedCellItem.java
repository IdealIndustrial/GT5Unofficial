package idealindustrial.autogen.implementation;

import gregtech.api.enums.ItemList;
import idealindustrial.autogen.items.MetaGeneratedItem;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.Prefixes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;

import static idealindustrial.util.misc.MiscValues.cellToStateMap;

public class MetaGeneratedCellItem extends MetaGeneratedItem {

    public static final Prefixes[] prefixes = new Prefixes[]{Prefixes.cell, Prefixes.gasCell, Prefixes.plasmaCell};
    public static ItemStack cell =  ItemList.Cell_Empty.get(1L);
    public MetaGeneratedCellItem() {
        super("metagenerated.cells", II_Materials.materialsK1, prefixes);
        foreachEnabled(i -> {
            Fluid fluid = material(i).getLiquidInfo().get(cellToStateMap.get(prefix(i))).getFluid();
            assert fluid != null;
            FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(this, 1, i), cell);
        });
    }


}
