package idealindustrial.impl.blocks.base;

import idealindustrial.api.textures.ITexture;
import idealindustrial.api.textures.IconContainer;
import idealindustrial.impl.item.stack.HashedBlock;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.textures.TextureManager;
import idealindustrial.util.lang.LangHandler;
import idealindustrial.util.misc.II_Util;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Stream;

public class SpecialBlock extends MetaBlock {

    int[] harvestLevels;
    String[] harvestTools;
    float[] hardness;
    DropsFunction[] drops;
    IconContainer[] textures;
    Class<? extends ItemBlock> itemClass;

    public SpecialBlock(int amount, Class<? extends MetaBlock_Item> itemClass, String unlocalizedName, Material material) {
        super(itemClass, unlocalizedName, material);
        harvestLevels = new int[amount];
        hardness = new float[amount];
        harvestTools = new String[amount];
        drops = new DropsFunction[amount];
        textures = new IconContainer[amount];
        this.itemClass = itemClass;
    }

    public SpecialBlock(int amount, String unlocalizedName, Material material) {
        this(amount, MetaBlock_Item.class, unlocalizedName, material);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (isEnabled(meta)) {
            return hardness[meta];
        }
        return super.getBlockHardness(world, x, y, z);
    }

    @Override
    public int getHarvestLevel(int meta) {
        if (isEnabled(meta)) {
            return harvestLevels[meta];
        }
        return super.getHarvestLevel(meta);
    }

    @Override
    public String getHarvestTool(int meta) {
        if (isEnabled(meta)) {
            return harvestTools[meta];
        }
        return super.getHarvestTool(meta);
    }

    interface DropsFunction {
        ArrayList<ItemStack> get(World world, int x, int y, int z);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
        if (isEnabled(meta)) {
            return drops[meta].get(world, x, y, z);
        }
        return super.getDrops(world, x, y, z, meta, fortune);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (isEnabled(meta)) {
            return textures[meta].getIcon();
        }
        return super.getIcon(side, meta);
    }

    public BlockBuilder builder(int meta, IconContainer texture) {
        assert !isEnabled(meta);
        enable(meta);
        return new BlockBuilder(meta, texture);
    }

    public BlockBuilder builder(int meta, String name) {
        return builder(meta, TextureManager.INSTANCE.blockTexture("special/" + name));
    }

    public class BlockBuilder {
        int meta;

        public BlockBuilder(int meta, IconContainer texture) {
            this.meta = meta;
            textures[meta] = texture;
        }

        public BlockBuilder setHarvestValues(float hardness, int level, String tool) {
            SpecialBlock.this.hardness[meta] = hardness;
            harvestLevels[meta] = level;
            harvestTools[meta] = tool;
            return this;
        }

        public BlockBuilder setDrops(DropsFunction function) {
            drops[meta] = function;
            return this;
        }

        @SuppressWarnings("unchecked")
        public BlockBuilder setDrops(ItemStack... is) {
            return setDrops(((world, x, y, z) -> new ArrayList<ItemStack>(Arrays.asList(is))));
        }

        public BlockBuilder setDrops(II_ItemStack... is) {
            return setDrops(Stream.of(is).map(II_ItemStack::toMCStack).toArray(ItemStack[]::new));
        }

        public BlockBuilder setDrops(ItemStack is, int min, int max) {
            Random random = new Random();
            return setDrops((world, x, y, z) -> new ArrayList<>(Collections.singleton(II_Util.copyAmount(min + random.nextInt(max - min), is))));
        }

        public BlockBuilder setDrops(II_ItemStack is, int min, int max) {
            return setDrops(is.toMCStack(), min, max);
        }

        public BlockBuilder setEngLocale(String name) {
            LangHandler.add(MetaBlock_Item.getUnlocalizedName(getUnlocalizedName(), meta), name);
            return this;
        }

        public HashedBlock asHashed() {
            return new HashedBlock(SpecialBlock.this, meta);
        }

    }
}
