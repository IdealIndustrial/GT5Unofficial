package gregtech.api.metatileentity.reimplement.basetile;

import net.minecraft.entity.player.EntityPlayer;

public interface IClickableTileEntity {

    /**
     * Called when leftclicking the TileEntity
     */
    public void onLeftclick(EntityPlayer aPlayer);

    /**
     * Called when rightclicking the TileEntity
     */
    public boolean onRightclick(EntityPlayer aPlayer, byte aSide, float par1, float par2, float par3);
}
