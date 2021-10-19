package idealindustrial.teststuff.testmulti;

import idealindustrial.textures.ITexture;
import idealindustrial.textures.RenderedTexture;
import idealindustrial.autogen.blocks.II_Blocks;
import idealindustrial.recipe.BasicMachineRecipe;
import idealindustrial.recipe.RecipeMaps;
import idealindustrial.textures.TextureManager;
import idealindustrial.tile.impl.multi.RecipedMultiMachineBase;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.tile.impl.multi.struct.MachineStructureBuilder;
import idealindustrial.tile.impl.multi.struct.MultiMachineShape;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.parameter.RecipedMachineStats;
import net.minecraft.init.Blocks;

import java.util.stream.Stream;

public class TestMultiMachine1 extends RecipedMultiMachineBase<HostMachineTile, BasicMachineRecipe> {

    public TestMultiMachine1(HostMachineTile baseTile) {
        super(baseTile, "Test MultiMachine",
                II_StreamUtil.repeated("test/fus", 10)
                        .map(s -> s == null ? null : TextureManager.INSTANCE.blockTexture(s))
                        .map(RenderedTexture::new).toArray(ITexture[]::new),
                Stream.of(null, null, null, "test/fusg", null,
                        null, null, null, "test/fusgy", null)
                        .map(s -> s == null ? null : TextureManager.INSTANCE.blockTexture(s))
                        .map(i -> i == null ? null : new RenderedTexture(i)).toArray(ITexture[]::new),
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
