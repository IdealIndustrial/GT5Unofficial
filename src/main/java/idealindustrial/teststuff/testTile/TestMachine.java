package idealindustrial.teststuff.testTile;

import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.tile.IOType;
import idealindustrial.tile.gui.base.GenericGuiContainer;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.tile.meta.BaseMetaTile_Facing1Output;
import idealindustrial.util.energy.OutputFacingEnergyHandler;
import idealindustrial.util.inventory.EmptyInventory;
import idealindustrial.util.inventory.ArrayRecipedInventory;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import java.util.stream.Stream;

import static gregtech.api.enums.Textures.BlockIcons.*;

public class TestMachine extends BaseMetaTile_Facing1Output<BaseMachineTile> {

    public TestMachine(BaseMachineTile baseTile) {
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
        energyHandler = new OutputFacingEnergyHandler(this, 0, 10_000L, 32, 1, outputFacing);
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return new GenericGuiContainer(new TestContainer(getBase(), player), II_Paths.PATH_GUI + "BasicGui.png");
    }

    @Override
    public Container getServerGUI(EntityPlayer player, int internalID) {
        return new TestContainer(getBase(), player);
    }

    @Override
    protected void onOutputFacingChanged() {
        if (energyHandler != null) {
            energyHandler.onConfigurationChanged();
            baseTile.notifyOnIOConfigChange(IOType.ENERGY);
        }
    }

    @Override
    public void onRemoval() {
        if (energyHandler != null) {
            energyHandler.onRemoval();
        }
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        super.onPostTick(timer, serverSide);
        if (serverSide && baseTile.isAllowedToWork()) {
            energyHandler.stored += 32;
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
        energyHandler.onConfigurationChanged();
    }

    @Override
    public boolean onSoftHammerClick(EntityPlayer player, ItemStack item, int side) {
        super.onSoftHammerClick(player, item, side);
        if (baseTile.isServerSide()) {
            baseTile.setActive(baseTile.isAllowedToWork());
        }
        return true;
    }

    @Override
    public TestMachine newMetaTile(BaseMachineTile baseTile) {
        TestMachine testMachine = new TestMachine(baseTile);
        testMachine.name = name;
        testMachine.baseTextures = baseTextures;
        testMachine.overlays = overlays;
        return testMachine;
    }
}
