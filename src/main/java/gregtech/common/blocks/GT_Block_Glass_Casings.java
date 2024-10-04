package gregtech.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GT_Block_Glass_Casings extends GT_Block_Casings_Abstract {

    private static final int maxAllowedMeta = 0; // set here max meta that may by used for this class

    public GT_Block_Glass_Casings(String aName) {
        super(GT_Item_Glass_Casings.class, aName, GT_Material_Casings.INSTANCE);
        GT_Utility.addTexturePage((byte) 1);
        for (byte i = 16; i < 32; i = (byte) (i + 1)) {
            Textures.BlockIcons.casingTexturePages[1][i] = new GT_CopiedBlockTexture(this, 6, i);
        }
        setCreativeTab(GregTech_API.TAB_GREGTECH.get());
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Plascrete Window"); // for Cleanroom
        ItemList.Block_Plascrete_Window.set(new ItemStack(this.setHardness(40.0f).setResistance(100.0f), 1, 0));
    }

    public String getHarvestTool(int aMeta) {
        if (aMeta == 0) return "wrench";
        assert aMeta >= 0 && aMeta <= maxAllowedMeta : "GT_Block_Glass_Casings - Invalid Metadata: " + aMeta;
        return "wrench";
    }

    public int getHarvestLevel(int aMeta) {
        if (aMeta == 0) return 2;
        assert aMeta >= 0 && aMeta <= maxAllowedMeta : "GT_Block_Glass_Casings - Invalid Metadata: " + aMeta;
        return 2;
    }

    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 0:
                return Textures.BlockIcons.BLOCK_PLASCRETE_WINDOW.getIcon();
        }
        assert aMeta >= 0 && aMeta <= maxAllowedMeta : "GT_Block_Glass_Casings - Invalid Metadata: " + aMeta;
        return Textures.BlockIcons.BLOCK_PLASCRETE_WINDOW.getIcon();
    }

    public float getBlockHardness(World aWorld, int aX, int aY, int aZ) {
        int tMeta = aWorld.getBlockMetadata(aX, aY, aZ);
        if (tMeta == 0) {
            return 10.0F;
        }
        assert tMeta >= 0 && tMeta <= maxAllowedMeta : "GT_Block_Glass_Casings - Invalid Metadata: " + tMeta;
        return 1.0F;
    }

    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        int tMeta = world.getBlockMetadata(x, y, z);
        if (tMeta == 0) {
            return 100.0F;
        }
        assert tMeta >=0 && tMeta <= maxAllowedMeta : "GT_Block_Glass_Casings - Invalid Metadata: " + tMeta;
        return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderBlockPass() {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess aWorld, int aX, int aY, int aZ, int aSide)
    {
        return aWorld.getBlock(aX, aY, aZ) != this && super.shouldSideBeRendered(aWorld, aX, aY, aZ, aSide);
    }

}
