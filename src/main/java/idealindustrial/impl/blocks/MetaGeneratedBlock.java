package idealindustrial.impl.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import idealindustrial.II_Core;
import idealindustrial.api.reflection.II_EventListener;
import idealindustrial.impl.blocks.base.MetaBlock;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.autogen.material.submaterial.MatterState;
import idealindustrial.api.textures.IconContainer;
import idealindustrial.impl.blocks.base.MetaBlock_Item;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class MetaGeneratedBlock extends MetaBlock {

    private final IconContainer[] icons;
    protected final II_Material[] materials;
    protected final int[] colors;
    protected int subID;
    public MetaGeneratedBlock(String type, int subID, Material material, SoundType soundType) {
        super(MetaBlock_Item.class, "metagenerated." + type + "." + subID, material);
        this.icons = new IconContainer[16];
        this.materials = new II_Material[16];
        this.colors = new int[16];
        this.subID = subID;
        setStepSound(soundType);
        setCreativeTab(II_Core.II_MATERIAL_TAB);
    }

    public void addBlock(int i, Prefixes prefix, II_Material material) {
        assert !isEnabled(i);
        enable(i);
        materials[i] = material;
        icons[i] = material.getRenderInfo().getTextureSet().forPrefix(prefix);
        colors[i] = material.getRenderInfo().getColorAsInt(MatterState.Solid);
    }

    public Prefixes prefix(int id) {
        return Prefixes.block;
    }

    public II_Material material(int id) {
        return materials[id];
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        assert isEnabled(meta);
        return icons[meta].getIcon();
    }

    @Override
    public int getRenderColor(int meta) {
        return colors[meta];
    }

    @Override
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return colors[world.getBlockMetadata(x,y,z)];
    }

    public String getHarvestTool(int aMeta) {
        return "pickaxe";
    }

    public int getHarvestLevel(int aMeta) {
        return 1;
    }

    public float getBlockHardness(World aWorld, int aX, int aY, int aZ) {
        return Blocks.iron_block.getBlockHardness(aWorld, aX, aY, aZ);
    }

    public float getExplosionResistance(Entity aTNT) {
        return Blocks.iron_block.getExplosionResistance(aTNT);
    }

    public String getUnlocalizedName() {
        return this.mUnlocalizedName;
    }

    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.mUnlocalizedName + ".name");
    }

    public boolean canBeReplacedByLeaves(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return false;
    }

    public boolean isNormalCube(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return true;
    }

    public boolean renderAsNormalBlock() {
        return true;
    }

    public boolean isOpaqueCube() {
        return true;
    }

    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public int damageDropped(int par1) {
        return par1;
    }

    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        return par1World.getBlockMetadata(par2, par3, par4);
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock(this);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister aIconRegister) {
        //already registered
    }

    public II_Material[] getMaterials() {
        return materials;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 16; i++) {
            if (isEnabled(i)) {
                list.add(new ItemStack(item, 1, i));
            }
        }
//        super.getSubItems(item, tab, list);
    }

}
