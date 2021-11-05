package idealindustrial.teststuff.testTile2;

import idealindustrial.api.textures.ITexture;
import idealindustrial.impl.textures.RenderedTexture;
import idealindustrial.impl.textures.TextureManager;
import idealindustrial.impl.tile.IOType;
import idealindustrial.impl.tile.gui.base.GenericGuiContainer;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.tile.impl.TileFacing2Main;
import idealindustrial.impl.tile.energy.electric.InputEnergyHandler;
import idealindustrial.impl.tile.fluid.MultiFluidHandler;
import idealindustrial.impl.tile.inventory.ArrayRecipedInventory;
import idealindustrial.impl.tile.inventory.EmptyInventory;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import java.util.stream.Stream;


public class TestMachine2 extends TileFacing2Main<HostMachineTile> {

    public TestMachine2(HostMachineTile baseTile) {
        super(baseTile, "test2",
                Stream.of("test/fus", "test/fus", "test/fus", "test/fus", "test/fus",
                        "test/fus", "test/fus", "test/fus", "test/fus", "test/fus")
                        .map(s -> TextureManager.INSTANCE.blockTexture(s))
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
        return new GenericGuiContainer<>(new Test2ZVontainer(getHost(), player), II_Paths.PATH_GUI + "BasicGui.png");
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
