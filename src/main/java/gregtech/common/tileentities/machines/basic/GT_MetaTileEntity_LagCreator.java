package gregtech.common.tileentities.machines.basic;


import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_1by1;
import gregtech.api.gui.GT_GUIContainer_1by1;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public class GT_MetaTileEntity_LagCreator
        extends GT_MetaTileEntity_BasicMachine {

    int lagTime = 0;

    public GT_MetaTileEntity_LagCreator(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "Creates lags, use screwdriver", 1, 1, "E_Oven.png", "", new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/electric_oven/OVERLAY_SIDE_ACTIVE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/electric_oven/OVERLAY_SIDE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/electric_oven/OVERLAY_FRONT_ACTIVE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/electric_oven/OVERLAY_FRONT")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/electric_oven/OVERLAY_TOP_ACTIVE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/electric_oven/OVERLAY_TOP")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/electric_oven/OVERLAY_BOTTOM_ACTIVE")),
                new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/electric_oven/OVERLAY_BOTTOM")));
    }

    public GT_MetaTileEntity_LagCreator(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
    }

    public GT_MetaTileEntity_LagCreator(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_LagCreator(this.mName, this.mTier, this.mDescriptionArray, this.mTextures, this.mGUIName, this.mNEIName);
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (ItemList.Cell_Empty.isStackEqual(aStack));
    }

    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return false;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_1by1(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_1by1(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            if (aPlayer.isSneaking()) {
                lagTime--;
            } else {
                lagTime+=5;
            }
            if (lagTime < 0) {
                lagTime = 0;
            }
            GT_Utility.sendChatToPlayer(aPlayer, "now lags " + lagTime + " ms each tick");
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide()) {
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < lagTime) {
                heatUpAir(100);
            }
        }
    }

    private static void heatUpAir(int complexity) {
        for (int i = 0; i < complexity; i++) {
            int a = 1, b = 2, c = 3, d = 4;
            for (int k = 0; k < 100; k++) {
                a++;
                b*=2;
                c--;
                d= a*b*c*d;
                Random r = new Random(d);
                for (int q = 0; q < 10; q++) {
                    r.nextInt();
                }
            }
        }
    }

}
