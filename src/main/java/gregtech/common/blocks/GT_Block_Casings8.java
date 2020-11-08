package gregtech.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GT_Block_Casings8
        extends GT_Block_Casings_Abstract {
    public GT_Block_Casings8() {
        super(GT_Item_Casings8.class, "gt.blockcasings8", GT_Material_Casings.INSTANCE);
        for (int i = 0; i < 3; i = (i + 1)) {
            Textures.BlockIcons.casingTexturePages[1][i+48] = new GT_CopiedBlockTexture(this, 6, i);
        }
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Chemically Inert Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "PTFE Pipe Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Robust HSS-G Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Primitive Pump Intake");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Simple Pump Intake");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Advanced Pump Intake");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Advanced Pump Intake II");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Advanced Pump Intake III");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Advanced Pump Intake IV");


        ItemList.Casing_Chemically_Inert.set(new ItemStack(this, 1, 0));
        ItemList.Casing_Pipe_Polytetrafluoroethylene.set(new ItemStack(this, 1, 1));
        ItemList.Casing_RobustHSSG.set(new ItemStack(this,1,2));

        ItemList.Casing_Filter_ULV.set(new ItemStack(this, 1, 10));
        ItemList.Casing_Filter_LV.set(new ItemStack(this, 1, 11));
        ItemList.Casing_Filter_MV.set(new ItemStack(this, 1, 12));
        ItemList.Casing_Filter_HV.set(new ItemStack(this, 1, 13));
        ItemList.Casing_Filter_EV.set(new ItemStack(this, 1, 14));
        ItemList.Casing_Filter_IV.set(new ItemStack(this, 1, 15));

        //ids 3 - 9 are used for pumps overlays
        Textures.BlockIcons.casingTexturePages[1][51] = new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_PUMP_ULV);
        Textures.BlockIcons.casingTexturePages[1][52] = new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_PUMP_LV);
        Textures.BlockIcons.casingTexturePages[1][53] = new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_PUMP_MV);
        Textures.BlockIcons.casingTexturePages[1][54] = new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_PUMP_HV);
        Textures.BlockIcons.casingTexturePages[1][55] = new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_PUMP_EV);
        Textures.BlockIcons.casingTexturePages[1][56] = new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_PUMP_IV);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 0:
                return Textures.BlockIcons.MACHINE_CASING_CHEMICALLY_INERT.getIcon();
            case 1:
                return Textures.BlockIcons.MACHINE_CASING_PIPE_POLYTETRAFLUOROETHYLENE.getIcon();
            case 2:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_HSSG.getIcon();
            case 10:
                if (aSide == 1)
                    return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_ULV_UP.getIcon();
                return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_ULV.getIcon();
            case 11:
                if (aSide == 1)
                    return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_LV_UP.getIcon();
                return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_LV.getIcon();
            case 12:
                if (aSide == 1)
                    return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_MV_UP.getIcon();
                return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_MV.getIcon();
            case 13:
                if (aSide == 1)
                    return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_HV_UP.getIcon();
                return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_HV.getIcon();
            case 14:
                if (aSide == 1)
                    return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_EV_UP.getIcon();
                return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_EV.getIcon();
            case 15:
                if (aSide == 1)
                    return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_IV_UP.getIcon();
                return Textures.BlockIcons.MACHINE_CASING_PUMP_FILTER_IV.getIcon();

        }
        return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
    }
}
