package gregtech.common.covers;

import gregtech.api.enums.GT_Values;
import gregtech.api.gui.GT_GUICover;
import gregtech.api.gui.widgets.GT_GuiIcon;
import gregtech.api.gui.widgets.GT_GuiIconCheckButton;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.net.GT_Packet_TileEntityCover;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fluids.Fluid;

public class GT_Cover_NeedMaintainance extends GT_CoverBehavior {

    private boolean isRotor(ItemStack aRotor) {
        return !(aRotor == null || !(aRotor.getItem() instanceof GT_MetaGenerated_Tool) || aRotor.getItemDamage() < 170 || aRotor.getItemDamage() > 176);
    }

    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        int needsRepair = 0;

        if (aTileEntity instanceof IGregTechTileEntity) {
            IGregTechTileEntity tTileEntity = (IGregTechTileEntity) aTileEntity;
            IMetaTileEntity mTileEntity = tTileEntity.getMetaTileEntity();
            if (mTileEntity instanceof GT_MetaTileEntity_MultiBlockBase) {
                GT_MetaTileEntity_MultiBlockBase multi = (GT_MetaTileEntity_MultiBlockBase) mTileEntity;
                int ideal = multi.getIdealStatus();
                int real = multi.getRepairStatus();
                ItemStack tRotor = multi.getRealInventory()[1];
                int coverVar = aCoverVariable >>> 1;
                if (coverVar < 5) {
                    if (ideal - real > coverVar)
                        needsRepair = 15;
                } else if (coverVar == 5 || coverVar == 6 || coverVar == 7) {
                    if (isRotor(tRotor)) {
                        long tMax = GT_MetaGenerated_Tool.getToolMaxDamage(tRotor);
                        long tCur = GT_MetaGenerated_Tool.getToolDamage(tRotor);

                        if (coverVar == 7) {
                            needsRepair = (int) (15 * tCur / tMax);
                        } else if (coverVar == 5) {
                            needsRepair = (tCur >= tMax * 8 / 10) ? 15 : 0;
                        } else {
                            long mExpectedDamage = Math.round(Math.min(multi.mEUt / multi.damageFactorLow, Math.pow(multi.mEUt, multi.damageFactorHigh)));
                            needsRepair = (tCur + mExpectedDamage * 2 >= tMax)  ? 15 : 0;
                        }
                    } else {
                        needsRepair = 15;
                    }
                }
            }
        }
        if (aCoverVariable % 2 == 1) {
            needsRepair = 15 - needsRepair;
        }

        aTileEntity.setOutputRedstoneSignal(aSide, (byte) needsRepair);
        aTileEntity.setOutputRedstoneSignal(GT_Utility.getOppositeSide(aSide), (byte) needsRepair);
        return aCoverVariable;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        aCoverVariable = (aCoverVariable + (aPlayer.isSneaking() ? -1 : 1)) % 16;
        if (aCoverVariable < 0) {
            aCoverVariable = 15;
        }
        switch (aCoverVariable) {
            case 0:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_056"));
                break;
            case 1:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_057"));
                break;
            case 2:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_058"));
                break;
            case 3:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_059"));
                break;
            case 4:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_060"));
                break;
            case 5:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_061"));
                break;
            case 6:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_062"));
                break;
            case 7:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_063"));
                break;
            case 8:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_064"));
                break;
            case 9:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_065"));
                break;
            case 10:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_066"));
                break;
            case 11:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_067"));
                break;
            case 12:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_068"));
                break;
            case 13:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_069"));
                break;
            case 14:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_070"));
                break;
            case 15:
                aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_071"));
                break;
        }
        return aCoverVariable;
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
        return true;
    }

    public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
        return true;
    }

    public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return 60;
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
        return new GUI(aSide, aCoverID, coverData, aTileEntity);
    }

    protected class GUI extends GT_GUICover {
        protected final byte side;
        protected final int coverID;
        protected int coverVariable;

        private final String[] tooltiptext = {
                trans("056", "Emit if 1 Maintenance Needed"),
                trans("058", "Emit if 2 Maintenance Needed"),
                trans("060", "Emit if 3 Maintenance Needed"),
                trans("062", "Emit if 4 Maintenance Needed"),
                trans("064", "Emit if 5 Maintenance Needed"),
                trans("066", "Emit if rotor needs maintenance low accuracy mod"),
                trans("068", "Emit if rotor needs maintenance high accuracy mod"),
                trans("070", "Emit rotor damage"),
        };

        private final String[] buttontext = {
                trans("247", "1 Issue"),
                trans("248", "2 Issues"),
                trans("249", "3 Issues"),
                trans("250", "4 Issues"),
                trans("251", "5 Issues"),
                trans("252", "Rotor < 80%"),
                trans("253", "Rotor < 100%"),
                trans("254", "Rotor damage"),
                trans("255", "Rotor tough"),

                trans("INVERTED","Inverted"),
                trans("NORMAL","Normal"),
        };

        protected final static int startX = 10;
        protected final static int startY = 22;
        protected final static int spaceX = 18;
        protected final static int spaceY = 16;

        protected final static int invertButton = 8;

        public GUI(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
            super(aTileEntity, 176, 107, GT_Utility.intToStack(aCoverID));
            this.side = aSide;
            this.coverID = aCoverID;
            this.coverVariable = aCoverVariable;

            GuiButton b;
            b = new GT_GuiIconCheckButton(this, 0, startX + spaceX*0, startY+spaceY*0, GT_GuiIcon.CHECKMARK, null).setTooltipText(tooltiptext[0]);
            b = new GT_GuiIconCheckButton(this, 1, startX + spaceX*0, startY+spaceY*1, GT_GuiIcon.CHECKMARK, null).setTooltipText(tooltiptext[1]);
            b = new GT_GuiIconCheckButton(this, 2, startX + spaceX*0, startY+spaceY*2, GT_GuiIcon.CHECKMARK, null).setTooltipText(tooltiptext[2]);
            b = new GT_GuiIconCheckButton(this, 3, startX + spaceX*0, startY+spaceY*3, GT_GuiIcon.CHECKMARK, null).setTooltipText(tooltiptext[3]);
            b = new GT_GuiIconCheckButton(this, 4, startX + spaceX*0, startY+spaceY*4, GT_GuiIcon.CHECKMARK, null).setTooltipText(tooltiptext[4]);

            b = new GT_GuiIconCheckButton(this, 5, startX + spaceX*4 + 4, startY+spaceY*0, GT_GuiIcon.CHECKMARK, null).setTooltipText(tooltiptext[5]);
            b = new GT_GuiIconCheckButton(this, 6, startX + spaceX*4 + 4, startY+spaceY*1, GT_GuiIcon.CHECKMARK, null).setTooltipText(tooltiptext[6]);
            b = new GT_GuiIconCheckButton(this, 7, startX + spaceX*4 + 4, startY+spaceY*2, GT_GuiIcon.CHECKMARK, null).setTooltipText(tooltiptext[7]);
            b = new GT_GuiIconCheckButton(this, invertButton, startX + spaceX*4 + 4, startY+spaceY*4, GT_GuiIcon.REDSTONE_ON, GT_GuiIcon.REDSTONE_OFF);
        }


        @Override
        public void drawExtras(int mouseX, int mouseY, float parTicks) {
            super.drawExtras(mouseX, mouseY, parTicks);

            this.fontRendererObj.drawString(buttontext[0],startX + spaceX*1, 4+startY+spaceY*0, 0xFF555555);
            this.fontRendererObj.drawString(buttontext[1],startX + spaceX*1, 4+startY+spaceY*1, 0xFF555555);
            this.fontRendererObj.drawString(buttontext[2],startX + spaceX*1, 4+startY+spaceY*2, 0xFF555555);
            this.fontRendererObj.drawString(buttontext[3],startX + spaceX*1, 4+startY+spaceY*3, 0xFF555555);
            this.fontRendererObj.drawString(buttontext[4],startX + spaceX*1, 4+startY+spaceY*4, 0xFF555555);
            this.fontRendererObj.drawString(buttontext[5],startX + spaceX*5 + 4, 4+startY+spaceY*0, 0xFF555555);
            this.fontRendererObj.drawString(buttontext[6],startX + spaceX*5 + 4, 4+startY+spaceY*1, 0xFF555555);
            //                                          inverted        normal
            String rotorText = ((coverVariable & 0x1) > 0) ? buttontext[8] : buttontext[7];
            String invertText = ((coverVariable & 0x1) > 0) ? buttontext[9] : buttontext[10];
            this.fontRendererObj.drawString(rotorText,  startX + spaceX*5 + 4, 4+startY+spaceY*2, 0xFF555555);
            this.fontRendererObj.drawString(invertText,  startX + spaceX*5 + 4, 4+startY+spaceY*4, 0xFF555555);
        }

        @Override
        protected void onInitGui(int guiLeft, int guiTop, int gui_width, int gui_height) {
            updateButtons();
        }

        public void buttonClicked(GuiButton btn){
            if (btn.id == invertButton || !isEnabled(btn.id)){
                coverVariable = getNewCoverVariable(btn.id, ((GT_GuiIconCheckButton) btn).isChecked());
                GT_Values.NW.sendToServer(new GT_Packet_TileEntityCover(side, coverID, coverVariable, tile));
            }
            updateButtons();
        }

        protected void updateButtons(){
            for (Object o : buttonList)
                ((GT_GuiIconCheckButton) o).setChecked(isEnabled(((GT_GuiIconCheckButton) o).id));
        }

        protected int getNewCoverVariable(int id, boolean checked) {
            if (id == invertButton) {
                if (checked)
                    return coverVariable & ~0x1;
                else
                    return coverVariable | 0x1;
            }
            return (coverVariable & 0x1) | (id << 1) ;
        }

        protected boolean isEnabled(int id) {
            if (id == invertButton)
                return (coverVariable & 0x1) > 0;
            return (coverVariable >>> 1) == id;
        }
    }
}