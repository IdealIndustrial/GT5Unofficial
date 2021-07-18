package idealindustrial.itemgen.blocks;

import idealindustrial.itemgen.material.II_Material;
import idealindustrial.itemgen.material.II_Materials;
import idealindustrial.itemgen.material.Prefixes;
import idealindustrial.itemgen.material.submaterial.BlockInfo;
import idealindustrial.itemgen.material.submaterial.BlockType;
import idealindustrial.reflection.events.II_EventListener;
import idealindustrial.util.lang.II_Lang;
import idealindustrial.util.lang.LocalizeEvent;
import idealindustrial.util.lang.materials.EngLocalizer;
import idealindustrial.util.lang.materials.MaterialLocalizer;

import java.util.ArrayList;
import java.util.List;

@II_EventListener
public class II_Blocks {

    public static II_Blocks INSTANCE = new II_Blocks();

    public final II_MetaGeneratedBlock[][] materialBlocks = new II_MetaGeneratedBlock[BlockType.values().length][];
    public final List<II_MetaGeneratedBlock> metaGeneratedBlocks = new ArrayList<>();

    protected II_Blocks() {
        for (int i = 0; i < materialBlocks.length; i++) {
            materialBlocks[i] = new II_MetaGeneratedBlock[BlockType.values()[i].getClassCount()];
        }
    }

    public void init() {
        for (II_Material material : II_Materials.materialsK1) {
            if (material == null || material.getBlockInfo() == null) {
                continue;
            }
            BlockInfo blockInfo = material.getBlockInfo();
            II_MetaGeneratedBlock[] blocks = materialBlocks[blockInfo.getType().ordinal()];
            int id = blockInfo.getId();
            int subID = id / 16;
            assert blocks[subID] == null || !blocks[subID].isEnabled(id % 16) :
                    "blocks of " + (blocks[subID].getMaterials()[id % 16].name()) + " and " + material.name() +
                            " have same type and ID";
            if (blocks[id] == null) {
                blocks[id] = new II_MetaGeneratedBlock(material.getBlockInfo().getType().name().toLowerCase(), subID,
                        blockInfo.getType().getMaterial(), blockInfo.getType().getSoundType());
                metaGeneratedBlocks.add(blocks[id]);
            }
            blocks[id].addBlock(id % 16, Prefixes.block, material);//todo think about other block prefixes
        }
    }

    @LocalizeEvent
    public static void localize() {
        MaterialLocalizer localizer = EngLocalizer.getInstance();
        for (II_MetaGeneratedBlock block : INSTANCE.metaGeneratedBlocks) {
            for (int i = 0; i < 16; i++) {
                if (!block.isEnabled(i)) {
                    continue;
                }
                String unlocalized = block.getUnlocalizedName() + "." + i;
                II_Lang.add(unlocalized + ".name", localizer.get(block.getMaterials()[i], Prefixes.block));
            }
        }
    }
}
