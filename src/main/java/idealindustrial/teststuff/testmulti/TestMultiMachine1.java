package idealindustrial.teststuff.testmulti;

import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.tile.interfaces.meta.MetaTile;
import idealindustrial.tile.meta.multi.BaseMultiMachine;
import idealindustrial.tile.meta.multi.struct.MachineStructureBuilder;
import idealindustrial.tile.meta.multi.struct.MultiMachineShape;
import idealindustrial.util.misc.II_StreamUtil;
import net.minecraft.init.Blocks;

import java.util.stream.Stream;

import static gregtech.api.enums.Textures.BlockIcons.*;
import static idealindustrial.tile.meta.multi.struct.MachineStructureBuilder.blockPredicate;

public class TestMultiMachine1 extends BaseMultiMachine<BaseMachineTile> {

    public TestMultiMachine1(BaseMachineTile baseTile) {
        super(baseTile, "Test MultiMachine",
                II_StreamUtil.repeated(MACHINE_CASING_CLEAN_STAINLESSSTEEL, 10)
                        .map(GT_RenderedTexture::new).toArray(ITexture[]::new),
                Stream.of(null, null, null, MACHINE_CASING_FUSION_GLASS, OVERLAY_FUSION1,
                        null, null, null, MACHINE_CASING_FUSION_GLASS_YELLOW, OVERLAY_FUSION2)
                        .map(i -> i == null ? null : new GT_RenderedTexture(i)).toArray(ITexture[]::new));
    }

    public TestMultiMachine1(BaseMachineTile baseTile, TestMultiMachine1 copyFrom) {
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
                .define('X', blockPredicate(Blocks.bedrock, 0).or(blockPredicate(Blocks.coal_block, 0)))
                .define('B', blockPredicate(Blocks.brick_block, 0)).added().create();
    }

    @Override
    public MetaTile<BaseMachineTile> newMetaTile(BaseMachineTile baseTile) {
        return new TestMultiMachine1(baseTile, this);
    }
}
