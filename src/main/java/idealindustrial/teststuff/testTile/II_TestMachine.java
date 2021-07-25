package idealindustrial.teststuff.testTile;

import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.tile.gui.base.II_GenericContainer;
import idealindustrial.tile.gui.base.II_GenericGuiContainer;
import idealindustrial.tile.meta.II_BaseMetaTile_Facing1;
import idealindustrial.tile.meta.II_MetaTile;
import idealindustrial.util.inventory.II_ArrayRecipedInventory;
import idealindustrial.util.inventory.II_EmptyInventory;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import java.util.stream.Stream;

import static gregtech.api.enums.Textures.BlockIcons.*;

public class II_TestMachine extends II_BaseMetaTile_Facing1 {

    public II_TestMachine(II_BaseTile baseTile) {
        super(baseTile, "test",
                Stream.of(MACHINE_BRONZE_BOTTOM, MACHINE_BRONZE_TOP, MACHINE_BRONZE_SIDE, TURBINE[4],
                        MACHINE_8V_BOTTOM, MACHINE_8V_TOP, MACHINE_8V_SIDE, MACHINE_CASING_TURBINE)
                        .map(GT_RenderedTexture::new).toArray(ITexture[]::new),
                Stream.of(MACHINE_BRONZE_BOTTOM, MACHINE_BRONZE_TOP, MACHINE_BRONZE_SIDE, TURBINE_ACTIVE[4],
                        MACHINE_8V_BOTTOM, MACHINE_8V_TOP, MACHINE_8V_SIDE, MACHINE_CASING_TURBINE)
                        .map(GT_RenderedTexture::new).toArray(ITexture[]::new)
                );
        inventoryIn = new II_ArrayRecipedInventory(1, 64);
        inventoryOut = new II_ArrayRecipedInventory(1, 64);
        inventorySpecial = II_EmptyInventory.INSTANCE;
        hasInventory = true;
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return new II_GenericGuiContainer(new II_TestContainer(getBase(), player), II_Paths.PATH_GUI + "BasicGui.png");
    }

    @Override
    public Container getServerGUI(EntityPlayer player, int internalID) {
        return new II_TestContainer(getBase(), player);
    }

    @Override
    public II_MetaTile newMetaTile(II_BaseTile baseTile) {
        II_TestMachine testMachine = new II_TestMachine(baseTile);
        testMachine.name = name;
        testMachine.baseTextures = baseTextures;
        testMachine.overlays = overlays;
        return testMachine;
    }
}
