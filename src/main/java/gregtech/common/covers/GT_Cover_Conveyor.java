package gregtech.common.covers;

import gregtech.api.enums.GT_Values;
import gregtech.api.gui.GT_GUICover;
import gregtech.api.gui.widgets.GT_GuiIcon;
import gregtech.api.gui.widgets.GT_GuiIconButton;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.net.GT_Packet_TileEntityCover;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_Conveyor extends GT_CoverBehavior {

    public final int mTickRate;
    private final int mMaxStacks;

    public GT_Cover_Conveyor(int aTickRate) {
        this.mTickRate = aTickRate;
        this.mMaxStacks = 1;
    }

    public GT_Cover_Conveyor(int aTickRate, int maxStacks) {
        this.mTickRate = aTickRate;
        this.mMaxStacks = maxStacks;
    }

    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        if ((aCoverVariable % 6 > 1) && ((aTileEntity instanceof IMachineProgress))) {
            if (((IMachineProgress) aTileEntity).isAllowedToWork() != aCoverVariable % 6 < 4) {
                return aCoverVariable;
            }
        }
        TileEntity tTileEntity = aTileEntity.getTileEntityAtSide(aSide);
        Object fromEntity = aCoverVariable % 2 == 0 ? aTileEntity : tTileEntity,
                toEntity = aCoverVariable % 2 != 0 ? aTileEntity : tTileEntity;
        byte fromSide = aCoverVariable % 2 != 0 ? GT_Utility.getOppositeSide(aSide) : aSide,
                toSide = aCoverVariable % 2 == 0 ? GT_Utility.getOppositeSide(aSide) : aSide;
        boolean costsEnergy = ((aCoverVariable % 2 == 0) || (aSide != 1)) && ((aCoverVariable % 2 != 0) || (aSide != 0)) && (aTileEntity.getUniversalEnergyCapacity() >= 128L);
        byte moved;

        for(int i=0 ; i < this.mMaxStacks ; i++) {
            // Costs energy but we don't have enough, bail
            if ((costsEnergy && !aTileEntity.isUniversalEnergyStored(256L)))
                break;

            moved = GT_Utility.moveOneItemStack(fromEntity, toEntity, fromSide , toSide, null, false, (byte) 64, (byte) 1, (byte) 64, (byte) 1);

            if(moved == 0)
                break;

            if (costsEnergy)
                aTileEntity.decreaseStoredEnergyUnits(4 * moved, true);
        }

        return aCoverVariable;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        aCoverVariable = (aCoverVariable + (aPlayer.isSneaking()? -1 : 1)) % 12;
        if(aCoverVariable <0){aCoverVariable = 11;}
        switch(aCoverVariable) {
            case 0: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_006")); break;
            case 1: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_007")); break;
            case 2: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_008")); break;
            case 3: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_009")); break;
            case 4: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_010")); break;
            case 5: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_011")); break;
            case 6: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_012")); break;
            case 7: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_013")); break;
            case 8: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_014")); break;
            case 9: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_015")); break;
            case 10: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_016")); break;
            case 11: aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_017")); break;
        }
        return aCoverVariable;
    }

    public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return true;
    }

    public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return (aCoverVariable >= 6) || (aCoverVariable % 2 != 0);
    }

    public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return (aCoverVariable >= 6) || (aCoverVariable % 2 == 0);
    }

    public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return this.mTickRate;
    }

    /**
     * GUI Stuff
     */

    @Override
    public boolean hasCoverGUI() {
        return true;
    }

    @Override
    public Object getClientGUI(byte aSide, int aCoverID, int coverData, ICoverable aTileEntity)  {
        return new GT_Cover_Conveyor.GUI(aSide, aCoverID, coverData, aTileEntity);
    }

    private class GUI extends GT_GUICover {
        private final byte side;
        private final int coverID;
        private int coverVariable;

        private final static int startX = 10;
        private final static int startY = 25;
        private final static int spaceX = 18;
        private final static int spaceY = 18;

        public GUI(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
            super(aTileEntity, 176, 107, GT_Utility.intToStack(aCoverID));
            this.side = aSide;
            this.coverID = aCoverID;
            this.coverVariable = aCoverVariable;

            GT_GuiIconButton b;
            b = new GT_GuiIconButton(this, 0, startX + spaceX*0, startY+spaceY*0, GT_GuiIcon.EXPORT).setTooltipText(trans("006","Export"));
            b = new GT_GuiIconButton(this, 1, startX + spaceX*1, startY+spaceY*0, GT_GuiIcon.IMPORT).setTooltipText(trans("007","Import"));
            b = new GT_GuiIconButton(this, 2, startX + spaceX*0, startY+spaceY*1, GT_GuiIcon.CHECKMARK).setTooltipText(trans("224","Ignore"));
            b = new GT_GuiIconButton(this, 3, startX + spaceX*1, startY+spaceY*1, GT_GuiIcon.REDSTONE_ON).setTooltipText(trans("225","Conditional"));
            b = new GT_GuiIconButton(this, 4, startX + spaceX*2, startY+spaceY*1, GT_GuiIcon.REDSTONE_OFF).setTooltipText(trans("226","Invert Condition"));
            b = new GT_GuiIconButton(this, 5, startX + spaceX*0, startY+spaceY*2, GT_GuiIcon.ALLOW_INPUT).setTooltipText(trans("227","Allow Input"));
            b = new GT_GuiIconButton(this, 6, startX + spaceX*1, startY+spaceY*2, GT_GuiIcon.BLOCK_INPUT).setTooltipText(trans("228","Block Input"));
        }

        @Override
        public void drawExtras(int mouseX, int mouseY, float parTicks) {
            super.drawExtras(mouseX, mouseY, parTicks);
            this.fontRendererObj.drawString(trans("229","Import/Export" ),  startX + spaceX*3, 3+startY+spaceY*0, 0xFF555555);
            this.fontRendererObj.drawString(trans("230","Conditional"),     startX + spaceX*3, 3+startY+spaceY*1, 0xFF555555);
            this.fontRendererObj.drawString(trans("231", "Enable Input"),   startX + spaceX*3, 3+startY+spaceY*2, 0xFF555555);
        }

        @Override
        protected void onInitGui(int guiLeft, int guiTop, int gui_width, int gui_height) {
            updateButtons();
        }

        public void buttonClicked(GuiButton btn){
            if (getClickable(btn.id)){
                coverVariable = getNewCoverVariable(btn.id);
                GT_Values.NW.sendToServer(new GT_Packet_TileEntityCover(side, coverID, coverVariable, tile));
            }
            updateButtons();
        }

        private void updateButtons(){
            GuiButton b;
            for (Object o : buttonList) {
                b = (GuiButton) o;
                b.enabled = getClickable(b.id);
            }
        }

        private int getNewCoverVariable(int id) {
            switch (id) {
                case 0:
                    return coverVariable & ~0x1;
                case 1:
                    return coverVariable | 0x1;
                case 2:
                    if (coverVariable > 5)
                        return 0x6 | (coverVariable & ~0xE);
                    return (coverVariable & ~0xE);
                case 3:
                    if (coverVariable > 5)
                        return 0x8 | (coverVariable & ~0xE);
                    return 0x2 | (coverVariable & ~0xE);
                case 4:
                    if (coverVariable > 5)
                        return 0xA | (coverVariable & ~0xE);
                    return (0x4 | (coverVariable & ~0xE));
                case 5:
                    if (coverVariable <= 5)
                        return coverVariable + 6;
                    break;
                case 6:
                    if (coverVariable > 5)
                        return coverVariable - 6;
            }
            return coverVariable;
        }

        private boolean getClickable(int id) {
            if (coverVariable < 0 | 11 < coverVariable)
                return false;

            switch (id) {
                case 0: case 1:
                    return (0x1 & coverVariable) != id;
                case 2:
                    return (coverVariable % 6) >= 2;
                case 3:
                    return (coverVariable % 6) < 2 | 4 <= (coverVariable % 6);
                case 4:
                    return (coverVariable % 6) < 4;
                case 5:
                    return coverVariable < 6;
                case 6:
                    return coverVariable >= 6;
            }
            return false;
        }
    }
}