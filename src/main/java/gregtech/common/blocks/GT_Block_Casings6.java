package gregtech.common.blocks;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GT_Block_Casings6
        extends GT_Block_Casings_Abstract {
    public GT_Block_Casings6() {
        super(GT_Item_Casings6.class, "gt.blockcasings6", GT_Material_Casings.INSTANCE);
        for (int i = 0; i < 1; i++) {
            Textures.BlockIcons.casingTexturePages[1][i + 64] = new GT_CopiedBlockTexture(this, 6, i);
        }
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Pyrolyze Casing");

        ItemList.Casing_Pyrolyze.set(new ItemStack(this, 1, 0));

    }

    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 0:
                return Textures.BlockIcons.MACHINE_CASING_PYROLYSE.getIcon();
        }
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
    }
}
