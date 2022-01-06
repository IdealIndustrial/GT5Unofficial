package idealindustrial.impl.blocks;

import idealindustrial.api.reflection.II_EventListener;
import idealindustrial.api.reflection.events.RegisterOresEvent;
import idealindustrial.api.textures.ITexture;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.autogen.material.submaterial.BlockInfo;
import idealindustrial.impl.autogen.material.submaterial.BlockType;
import idealindustrial.impl.blocks.base.CasingBlock;
import idealindustrial.impl.blocks.base.MetaBlock_Item;
import idealindustrial.impl.blocks.base.SpecialBlock;
import idealindustrial.impl.blocks.ores.BlockOres;
import idealindustrial.impl.blocks.ores.TileOres;
import idealindustrial.impl.blocks.plants.BlockPlants;
import idealindustrial.impl.blocks.plants.Plants;
import idealindustrial.impl.item.stack.HashedBlock;
import idealindustrial.impl.item.stack.HashedBlockContainer;
import idealindustrial.impl.oredict.OredictHandler;
import idealindustrial.impl.tile.BlockMachines;
import idealindustrial.util.lang.LangHandler;
import idealindustrial.util.lang.LocalizeEvent;
import idealindustrial.util.lang.materials.EngLocalizer;
import idealindustrial.util.lang.materials.MaterialLocalizer;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@II_EventListener
public class II_Blocks {

    public static II_Blocks INSTANCE = new II_Blocks();

    public final MetaGeneratedBlock[][] materialBlocks = new MetaGeneratedBlock[BlockType.values().length][];
    public final List<MetaGeneratedBlock> metaGeneratedBlocks = new ArrayList<>();
    public BlockMachines blockMachines;
    public BlockOres blockOres;
    public BlockPlants blockPlants;
    public SpecialBlock block1 = new SpecialBlock(1, "specialBlock1", Material.rock);

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
            if (blocks[subID] == null) {
                blocks[subID] = new MetaGeneratedBlock(material.getBlockInfo().getType().name().toLowerCase(), subID,
                        blockInfo.getType().getMaterial(), blockInfo.getType().getSoundType());
                metaGeneratedBlocks.add(blocks[id]);
            }
            blocks[subID].addBlock(id % 16, Prefixes.block, material);//todo think about other block prefixes
        }

        blockMachines = new BlockMachines();
        blockOres = new BlockOres();
        blockPlants = new BlockPlants();
        Plants.init();
        SpecialBlocks.init(this);
        CasingBlocks.init();
    }

    @RegisterOresEvent
    public static void registerOres(OredictHandler handler) {
        Arrays.stream(INSTANCE.materialBlocks)
                .filter(Objects::nonNull).flatMap(Arrays::stream)
                .filter(Objects::nonNull).forEach(block ->
                        block.foreachEnabled(i -> handler.registerOre(block.prefix(i), block.material(i), new ItemStack(block, 1, i)))
                );
    }

    @LocalizeEvent
    public static void localize() {
        MaterialLocalizer localizer = EngLocalizer.getInstance();
        for (MetaGeneratedBlock block : INSTANCE.metaGeneratedBlocks) {
            for (int i = 0; i < 16; i++) {
                if (!block.isEnabled(i)) {
                    continue;
                }
                LangHandler.add(MetaBlock_Item.getUnlocalizedName(block.getUnlocalizedName(), i), localizer.get(block.getMaterials()[i], Prefixes.block));
            }
        }
        Prefixes[] prefixes = new Prefixes[]{Prefixes.ore, Prefixes.oreSmall};
        for (II_Material material : II_Materials.allMaterials) {
            for (Prefixes prefix : prefixes) {
                if (!material.isEnabled(prefix)) {
                    continue;
                }
                int i = TileOres.getMeta(material, prefix);
                String unlocalized = II_Blocks.INSTANCE.blockOres.getUnlocalizedName() + "." + i;
                LangHandler.add(unlocalized + ".name", localizer.get(material, prefix));
            }
        }
    }


    public enum SpecialBlocks implements HashedBlockContainer {
        CoalChunks((b) -> b.block1.builder(0, "1/charcoal").setHarvestValues(2, 0, "axe")
                .setDrops(new ItemStack(Items.coal, 1, 0), 2, 4)
                .setEngLocale("Coal Chunk").asHashed());

        SpecialBlocks(Function<II_Blocks, HashedBlock> supplier) {
            this.supplier = supplier;
        }

        final Function<II_Blocks, HashedBlock> supplier;

        static void init(II_Blocks blocks) {
            for (SpecialBlocks block : values()) {
                block.block = block.supplier.apply(blocks);
            }
        }

        HashedBlock block;

        public HashedBlock getBlock() {
            return block;
        }

        @Override
        public HashedBlock get() {
            return block;
        }
    }

    //  casing1 = new CasingBlock("testcasing", Material.iron, "test casing", "Bricked Casing");

    static List<CasingBlock> casings = new ArrayList<>();

    public enum CasingBlocks implements HashedBlockContainer {
        TestCasing("Test Casing"),
        PrimitiveFurnaceCasing("Bricked Casing"),
        PrimitiveBlastFurnaceCasing("Bricked Blast Casing")

        ;

       final String name;

        CasingBlocks(String name) {
            this.name = name;
        }

        HashedBlock block;

        static void init() {
            CasingBlock.Builder builder = new CasingBlock.Builder("casing." + 0, Material.iron);
            for (CasingBlocks block : values()) {
                builder.addBlock(block.name).onPostCreated(block::setBlock);
                if (block.ordinal() % 16 == 15) {
                    casings.add(builder.construct());
                    builder = new CasingBlock.Builder("casing." + block.ordinal() / 16, Material.iron);
                }
            }
            casings.add(builder.construct());
        }

        void setBlock(HashedBlock block) {
            this.block = block;
        }

        public ITexture getTexture() {
            return ((CasingBlock) get().getBlock()).getTexture(ordinal() % 16);
        }


        @Override
        public HashedBlock get() {
            assert block != null;
            return block;
        }
    }
}
