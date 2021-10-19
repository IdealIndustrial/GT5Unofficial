package idealindustrial.teststuff.testTile2;

import idealindustrial.textures.ITexture;
import idealindustrial.textures.RenderedTexture;
import idealindustrial.textures.TextureManager;
import idealindustrial.tile.IOType;
import idealindustrial.tile.gui.base.GenericGuiContainer;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.tile.impl.TileFacing2Main;
import idealindustrial.util.energy.electric.InputEnergyHandler;
import idealindustrial.util.fluid.MultiFluidHandler;
import idealindustrial.util.inventory.ArrayRecipedInventory;
import idealindustrial.util.inventory.EmptyInventory;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.stream.Stream;


public class TestMachine2 extends TileFacing2Main<HostMachineTile> {

    public TestMachine2(HostMachineTile baseTile) {
        super(baseTile, "test2",
                Stream.of("test/fus", "test/fus", "test/fus", "test/fus", "test/fus",
                        "test/fus", "test/fus", "test/fus", "test/fus", "test/fus")
                        .map(s -> s == null ? null : TextureManager.INSTANCE.blockTexture(s))
                        .map(RenderedTexture::new).toArray(ITexture[]::new),
                Stream.of(null, null, null, "test/fusg", null,
                        null, null, null, "test/fusgy", null)
                        .map(s -> s == null ? null : TextureManager.INSTANCE.blockTexture(s))
                        .map(i -> i == null ? null : new RenderedTexture(i)).toArray(ITexture[]::new)
        );
        inventoryIn = new ArrayRecipedInventory(2, 64);
        inventoryOut = new ArrayRecipedInventory(2, 64);
        inventorySpecial = EmptyInventory.INSTANCE;
        hasInventory = true;

        tankIn = new MultiFluidHandler(2, 3000);
        tankOut = new MultiFluidHandler(2, 3000);
        hasTank = true;

        energyHandler = new InputEnergyHandler(baseTile, 0, 500, 32, 1);
        hasEnergy = true;
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return new GenericGuiContainer(new Test2ZVontainer(getHost(), player), II_Paths.PATH_GUI + "BasicGui.png");
    }

    @Override
    public Container getServerGUI(EntityPlayer player, int internalID) {
        return new Test2ZVontainer(getHost(), player);
    }

    @Override
    public void onTick(long timer, boolean serverSide) {
        if (hostTile.isAllowedToWork() && timer % 10 == 6 && energyHandler.getStored() > 60) {
            energyHandler.drain(60, true);
//            tankIn.fill(ForgeDirection.UNKNOWN, GT_ModHandler.getWater(1L), true);
        }
    }

    @Override
    protected IOType getOutputIOType() {
        return IOType.ENERGY;
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        TestMachine2 testMachine = new TestMachine2(baseTile);
        testMachine.name = name;
        testMachine.baseTextures = baseTextures;
        testMachine.overlays = overlays;
        return testMachine;
    }
}
