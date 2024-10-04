package gregtech.common.tileentities.machines.basic;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_SpawnEventHandler;
import gregtech.api.util.GT_Utility;
import java.util.Arrays;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static gregtech.api.enums.GT_Values.V;

public class GT_MetaTileEntity_MonsterRepellent extends GT_MetaTileEntity_TieredMachineBlock {

    public enum repellationMode {
        HOSTILES, HOSTILES_NEUTRALS, EVERYONE
    }

    public int mRange = 16;

    public repellationMode repMode = repellationMode.HOSTILES;

    public GT_MetaTileEntity_MonsterRepellent(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 0,
                new String[] {"Repels nasty Creatures. Range: " + (4 + (12 * aTier)) + " unpowered / " + (16 + (48 * aTier)) + " powered.",
                        "Use a screwdriver to switch repellation mode."});
    }

    public GT_MetaTileEntity_MonsterRepellent(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }

    public GT_MetaTileEntity_MonsterRepellent(String aName, int aTier, int aInvSlotCount, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_MonsterRepellent(this.mName, this.mTier, this.mInventory.length, this.mDescriptionArray, this.mTextures);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], (aSide != 1) ? null : aActive ? new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TELEPORTER_ACTIVE) : new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TELEPORTER)};
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTimer) {
        if (aBaseMetaTileEntity.isAllowedToWork() && aBaseMetaTileEntity.isServerSide()) {
            int[] tCoords = new int[]{aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord(), aBaseMetaTileEntity.getWorld().provider.dimensionId};
            if ((aTimer % 600 == 0) && !GT_SpawnEventHandler.mobReps.stream().anyMatch(c -> Arrays.equals(c, tCoords)))
            {
                GT_SpawnEventHandler.mobReps.add(tCoords);
            }
            if (aBaseMetaTileEntity.isUniversalEnergyStored(getMinimumStoredEU()) && aBaseMetaTileEntity.decreaseStoredEnergyUnits(1 << (this.mTier * 2), false)) {
                mRange = 16 + (48 * mTier);
            } else {
                mRange = 4 + (12 * mTier);
            }
        }
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        int[] tCoords = new int[]{aBaseMetaTileEntity.getXCoord(), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getZCoord(), aBaseMetaTileEntity.getWorld().provider.dimensionId};
        if (GT_SpawnEventHandler.mobReps != null && !GT_SpawnEventHandler.mobReps.stream().anyMatch(c -> Arrays.equals(c, tCoords))){
            GT_SpawnEventHandler.mobReps.add(tCoords);
        }
    }

    @Override
    public void onRemoval() {
        int[] tCoords = new int[]{this.getBaseMetaTileEntity().getXCoord(), this.getBaseMetaTileEntity().getYCoord(), this.getBaseMetaTileEntity().getZCoord(), this.getBaseMetaTileEntity().getWorld().provider.dimensionId};
        GT_SpawnEventHandler.mobReps.removeIf(seh -> Arrays.equals(seh,tCoords));
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {

            int newRepMode;

            if (aPlayer.isSneaking())
            {
                newRepMode = repMode.ordinal() - 1;
            }
            else {
                newRepMode = repMode.ordinal() + 1;
            }

            if (newRepMode > repellationMode.values().length - 1)
                newRepMode = 0;

            if (newRepMode < 0)
                newRepMode = repellationMode.values().length - 1;

            repMode = repellationMode.values()[newRepMode];

            String localizationIndex = "240";
            String unlocalizedMessage = "Prevents spawn of hostile creatures";

            switch (repMode){
                case HOSTILES:
                {
                    break;
                }
                case HOSTILES_NEUTRALS:
                {
                    localizationIndex = "241";
                    unlocalizedMessage = "Prevents spawn of hostile and neutral creatures(except animals and pets)";
                    break;
                }
                case EVERYONE:
                {
                    localizationIndex = "242";
                    unlocalizedMessage = "Prevents spawn of any living creature(except players and their pets)";
                    break;
                }
            }

            GT_Utility.sendChatToPlayer(aPlayer, trans(localizationIndex, unlocalizedMessage));
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public boolean isSimpleMachine() {
        return false;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return true;
    }

    @Override
    public boolean isEnetInput() {
        return true;
    }

    @Override
    public boolean isInputFacing(byte aSide) {
        return true;
    }

    @Override
    public boolean isTeleporterCompatible() {
        return false;
    }

    @Override
    public long getMinimumStoredEU() {
        return 512;
    }

    @Override
    public long maxEUStore() {
        return 512 + V[mTier] * 50;
    }

    @Override
    public long maxEUInput() {
        return V[mTier];
    }

    @Override
    public long maxAmperesIn() {
        return 2;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        return null;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("repellationMode", repMode.ordinal());
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        repMode = repellationMode.values()[aNBT.getInteger("repellationMode")];
        if (aNBT.hasKey("neutralsAllowed"))
            aNBT.removeTag("neutralsAllowed");
    }
}
