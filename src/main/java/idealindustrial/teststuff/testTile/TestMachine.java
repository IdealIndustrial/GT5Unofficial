package idealindustrial.teststuff.testTile;

import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.tile.IOType;
import idealindustrial.tile.gui.base.GenericGuiContainer;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.impl.TileFacing1Output;
import idealindustrial.util.energy.electric.OutputEnergyHandler;
import idealindustrial.util.energy.electric.OutputFacingEnergyHandler;
import idealindustrial.util.inventory.EmptyInventory;
import idealindustrial.util.inventory.ArrayRecipedInventory;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.stream.Stream;

import static gregtech.api.enums.Textures.BlockIcons.*;

public class TestMachine extends TileFacing1Output<HostMachineTile> {

    public TestMachine(HostMachineTile baseTile) {
        super(baseTile, "test",
                Stream.of(MACHINE_BRONZE_BOTTOM, MACHINE_BRONZE_TOP, MACHINE_BRONZE_SIDE, TURBINE[4],
                        MACHINE_8V_BOTTOM, MACHINE_8V_TOP, MACHINE_8V_SIDE, TURBINE_ACTIVE[4])
                        .map(GT_RenderedTexture::new).toArray(ITexture[]::new),
                Stream.of(MACHINE_BRONZE_BOTTOM, MACHINE_BRONZE_TOP, MACHINE_BRONZE_SIDE, TURBINE[4],
                        MACHINE_8V_BOTTOM, MACHINE_8V_TOP, MACHINE_8V_SIDE, TURBINE_ACTIVE[4])
                        .map(GT_RenderedTexture::new).toArray(ITexture[]::new)
        );
        inventoryIn = new ArrayRecipedInventory(1, 64);
        inventoryOut = new ArrayRecipedInventory(1, 64);
        inventorySpecial = EmptyInventory.INSTANCE;
        hasInventory = true;

        hasEnergy = true;
        energyHandler = new OutputEnergyHandler(baseTile, 0, 10_000L, 32, 1, outputFacing);
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return new GenericGuiContainer(new TestContainer(getHost(), player), II_Paths.PATH_GUI + "BasicGui.png");
    }

    @Override
    public Container getServerGUI(EntityPlayer player, int internalID) {
        return new TestContainer(getHost(), player);
    }

    @Override
    protected IOType getOutputIOType() {
        return IOType.ENERGY;
    }

    @Override
    public boolean getIOatSide(int side, IOType type, boolean input) {
        return type == IOType.ENERGY && side == outputFacing && !input;
    }

//    @Override
//    public void onRemoval() {
//        if (energyHandler != null) {
//            energyHandler.onRemoval();
//        }
//    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        super.onPostTick(timer, serverSide);
        if (serverSide && hostTile.isAllowedToWork()) {
            energyHandler.fill(32, true);
        }
//        try {
//            II_StackSignature stack = Test.test("{material: \"iron\", prefix: \"plate\", amount: 13}");
//            II_StackSignature stack2 = Test.test("{mod: \"minecraft\", name:\"apple\", damage: 1,  amount: 13}");
//            II_StackSignature stack3 = Test.test("{ore: \"plankWood\",  amount: 13}");
//
//            Gson gson = NbtToJson.registerSmartNBTSerializer(new GsonBuilder())
//                    .registerTypeAdapter(II_StackSignature.class, new JsonStackSignatureSerializer())
//                    .create();
//            if (inventoryIn.get(0) != null) {
//                II_ItemStack is = inventoryIn.iterator().next();
//                II_StackSignature ss = new II_StackSignature(is, CheckType.DIRECT);
//                String str = gson.toJson(ss, II_StackSignature.class);
//                int n= 0;
//            }
//            int a = 0;
//        }
//        catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }

    @Override
    public void onBlockChange() {
        super.onBlockChange();
//        energyHandler.onConfigurationChanged();
    }

    @Override
    public boolean onSoftHammerClick(EntityPlayer player, ItemStack item, int side) {
        super.onSoftHammerClick(player, item, side);
        if (hostTile.isServerSide()) {
            hostTile.setActive(hostTile.isAllowedToWork());
        }
        return true;
    }

    @Override
    public TestMachine newMetaTile(HostMachineTile baseTile) {
        TestMachine testMachine = new TestMachine(baseTile);
        testMachine.name = name;
        testMachine.baseTextures = baseTextures;
        testMachine.overlays = overlays;
        return testMachine;
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {
        super.loadFromNBT(nbt);
//        if (energyHandler != null) {
//            energyHandler.onConfigurationChanged();
//        }
    }
}
