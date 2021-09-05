package idealindustrial.autogen.blocks;

import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.autogen.material.submaterial.BlockInfo;
import idealindustrial.autogen.material.submaterial.BlockType;
import idealindustrial.reflection.events.II_EventListener;
import idealindustrial.tile.BlockMachines;
import idealindustrial.util.lang.LangHandler;
import idealindustrial.util.lang.LocalizeEvent;
import idealindustrial.util.lang.materials.EngLocalizer;
import idealindustrial.util.lang.materials.MaterialLocalizer;

import java.util.ArrayList;
import java.util.List;

@II_EventListener
public class II_Blocks {

    public static II_Blocks INSTANCE = new II_Blocks();

    public final MetaGeneratedBlock[][] materialBlocks = new MetaGeneratedBlock[BlockType.values().length][];
    public final List<MetaGeneratedBlock> metaGeneratedBlocks = new ArrayList<>();
    public BlockMachines blockMachines;

    protected II_Blocks() {
        for (int i = 0; i < materialBlocks.length; i++) {
            materialBlocks[i] = new MetaGeneratedBlock[BlockType.values()[i].getClassCount()];
        }
    }

    public void init() {
        for (II_Material material : II_Materials.materialsK1) {
            if (material == null || material.getBlockInfo() == null) {
                continue;
            }
            BlockInfo blockInfo = material.getBlockInfo();
            MetaGeneratedBlock[] blocks = materialBlocks[blockInfo.getType().ordinal()];
            int id = blockInfo.getId();
            int subID = id / 16;
            assert blocks[subID] == null || !blocks[subID].isEnabled(id % 16) :
                    "blocks of " + (blocks[subID].getMaterials()[id % 16].name()) + " and " + material.name() +
                            " have same type and ID";
            if (blocks[id] == null) {
                blocks[id] = new MetaGeneratedBlock(material.getBlockInfo().getType().name().toLowerCase(), subID,
                        blockInfo.getType().getMaterial(), blockInfo.getType().getSoundType());
                metaGeneratedBlocks.add(blocks[id]);
            }
            blocks[id].addBlock(id % 16, Prefixes.block, material);//todo think about other block prefixes
        }

        blockMachines = new BlockMachines();
    }

    @LocalizeEvent
    public static void localize() {
        MaterialLocalizer localizer = EngLocalizer.getInstance();
        for (MetaGeneratedBlock block : INSTANCE.metaGeneratedBlocks) {
            for (int i = 0; i < 16; i++) {
                if (!block.isEnabled(i)) {
                    continue;
                }
                String unlocalized = block.getUnlocalizedName() + "." + i;
                LangHandler.add(unlocalized + ".name", localizer.get(block.getMaterials()[i], Prefixes.block));
            }
        }
    }
}
