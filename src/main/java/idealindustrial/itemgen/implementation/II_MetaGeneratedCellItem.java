package idealindustrial.itemgen.implementation;

import gregtech.api.enums.ItemList;
import idealindustrial.itemgen.items.II_MetaGeneratedItem;
import idealindustrial.itemgen.material.II_Materials;
import idealindustrial.itemgen.material.Prefixes;
import idealindustrial.itemgen.material.submaterial.render.RenderInfo;
import idealindustrial.util.misc.MiscValues;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;

import static idealindustrial.util.misc.MiscValues.cellToStateMap;

public class II_MetaGeneratedCellItem extends II_MetaGeneratedItem {

    public static final Prefixes[] prefixes = new Prefixes[]{Prefixes.cell, Prefixes.gasCell, Prefixes.plasmaCell};
    public static ItemStack cell =  ItemList.Cell_Empty.get(1L);
    public II_MetaGeneratedCellItem() {
        super("metagenerated.cells", II_Materials.materialsK1, prefixes);
        foreachEnabled(i -> {
            Fluid fluid = material(i).getLiquidInfo().get(cellToStateMap.get(prefix(i))).getFluid();
            assert fluid != null;
            FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(this, 1, i), cell);
        });
    }


}
