package idealindustrial.teststuff.testmulti;

import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.autogen.blocks.II_Blocks;
import idealindustrial.recipe.BasicMachineRecipe;
import idealindustrial.recipe.RecipeMaps;
import idealindustrial.tile.impl.multi.RecipedMultiMachineBase;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.tile.impl.multi.struct.MachineStructureBuilder;
import idealindustrial.tile.impl.multi.struct.MultiMachineShape;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.init.Blocks;

import java.util.stream.Stream;

import static gregtech.api.enums.Textures.BlockIcons.*;

public class TestMultiMachine1 extends RecipedMultiMachineBase<HostMachineTile, BasicMachineRecipe> {

    public TestMultiMachine1(HostMachineTile baseTile) {
        super(baseTile, "Test MultiMachine",
                II_StreamUtil.repeated(MACHINE_CASING_CLEAN_STAINLESSSTEEL, 10)
                        .map(GT_RenderedTexture::new).toArray(ITexture[]::new),
                Stream.of(null, null, null, MACHINE_CASING_FUSION_GLASS, OVERLAY_FUSION1,
                        null, null, null, MACHINE_CASING_FUSION_GLASS_YELLOW, OVERLAY_FUSION2)
                        .map(i -> i == null ? null : new GT_RenderedTexture(i)).toArray(ITexture[]::new),
                RecipeMaps.benderRecipes, new RecipedMachineStats(1, 0, 0, 64, 0, 0, 0, 0, 0));
    }

    public TestMultiMachine1(HostMachineTile baseTile, TestMultiMachine1 copyFrom) {
        super(baseTile, copyFrom);
    }

    @Override
    protected MultiMachineShape getStructure() {
        return MachineStructureBuilder.start().addShape(
                new String[][]{
                        {
                                "XXX",
                                "XXX",
                                "XXX"
                        },
                        {
                                "BBB",
                                "BBB",
                                "BcB"
                        },
                        {
                                "XXX",
                                "XXX",
                                "XXX"
                        }
                })
                .define('X', blockPredicate(II_Blocks.INSTANCE.casing1, 0).orHatch(HatchType.ItemIn, HatchType.ItemOut))
                .define('B', blockPredicate(Blocks.brick_block, 0).orHatch(HatchType.EnergyIn))
                .added()
                .create();
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new TestMultiMachine1(baseTile, this);
    }
}
