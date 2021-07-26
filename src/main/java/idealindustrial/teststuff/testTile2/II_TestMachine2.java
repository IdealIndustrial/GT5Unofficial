package idealindustrial.teststuff.testTile2;

import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.tile.gui.base.II_GenericGuiContainer;
import idealindustrial.tile.meta.II_BaseMetaTile_Facing2Main;
import idealindustrial.tile.meta.II_MetaTile;
import idealindustrial.util.fluid.II_MultiFluidHandler;
import idealindustrial.util.inventory.II_ArrayRecipedInventory;
import idealindustrial.util.inventory.II_EmptyInventory;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.stream.Stream;

import static gregtech.api.enums.Textures.BlockIcons.*;

public class II_TestMachine2 extends II_BaseMetaTile_Facing2Main {

    public II_TestMachine2(II_BaseTile baseTile) {
        super(baseTile, "test2",
                Stream.of(MACHINE_CASING_FUSION, MACHINE_CASING_FUSION, MACHINE_CASING_FUSION, MACHINE_CASING_FUSION, MACHINE_CASING_FUSION,
                        MACHINE_CASING_FUSION, MACHINE_CASING_FUSION, MACHINE_CASING_FUSION, MACHINE_CASING_FUSION, MACHINE_CASING_FUSION)
                        .map(GT_RenderedTexture::new).toArray(ITexture[]::new),
                Stream.of(null, null, null, MACHINE_CASING_FUSION_GLASS, OVERLAY_FUSION1,
                        null, null, null, MACHINE_CASING_FUSION_GLASS_YELLOW,  OVERLAY_FUSION2)
                        .map(i -> i == null ? null : new GT_RenderedTexture(i)).toArray(ITexture[]::new)
        );
        inventoryIn = new II_ArrayRecipedInventory(2, 64);
        inventoryOut = new II_ArrayRecipedInventory(2, 64);
        inventorySpecial = II_EmptyInventory.INSTANCE;
        hasInventory = true;

        tankIn = new II_MultiFluidHandler(2, 3000);
        tankOut = new II_MultiFluidHandler(2, 3000);
        hasTank = true;
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return new II_GenericGuiContainer(new II_Test2ZVontainer(getBase(), player), II_Paths.PATH_GUI + "BasicGui.png");
    }

    @Override
    public Container getServerGUI(EntityPlayer player, int internalID) {
        return new II_Test2ZVontainer(getBase(), player);
    }

    @Override
    public II_MetaTile newMetaTile(II_BaseTile baseTile) {
        II_TestMachine2 testMachine = new II_TestMachine2(baseTile);
        testMachine.name = name;
        testMachine.baseTextures = baseTextures;
        testMachine.overlays = overlays;
        return testMachine;
    }

    @Override
    public void onTick(long timer, boolean serverSide) {
        if (baseTile.isActive() && timer % 10 == 6) {
            tankIn.fill(ForgeDirection.UNKNOWN, GT_ModHandler.getWater(1L), true);
        }
    }
}
