package idealindustrial.tile.impl.multi.parts;

import idealindustrial.textures.ITexture;
import idealindustrial.textures.Textures;
import idealindustrial.tile.gui.base.ContainerNxN;
import idealindustrial.tile.gui.base.GuiContainerNxN;
import idealindustrial.tile.gui.base.component.GuiTextures;
import idealindustrial.tile.impl.multi.MultiMachineBase;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.util.inventory.ArrayRecipedInventory;
import idealindustrial.util.inventory.EmptyInventory;
import idealindustrial.util.inventory.interfaces.InternalInventory;
import idealindustrial.util.misc.II_StreamUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;

public abstract class Hatch_Item extends TileHatch<HostMachineTile, MultiMachineBase<?>> {
    int tier;
    protected Hatch_Item(HostMachineTile baseTile, String name, ITexture outFace, int tier) {
        super(baseTile, name,
                II_StreamUtil.arrayOf(Textures.baseTiredTextures[tier], new ITexture[8]),
                II_StreamUtil.setInNullAr(outFace, new ITexture[8], 3, 7)
        );
        hasInventory = true;
        this.tier = tier;

    }

    protected Hatch_Item(HostMachineTile baseTile, Hatch_Item copyFrom) {
        super(baseTile, copyFrom);
        hasInventory = true;
        this.tier = copyFrom.tier;
    }

    protected void set(InternalInventory inv, boolean input) {
        if (input) {
            inventoryIn = inv;
            inventoryOut = EmptyInventory.INSTANCE;
        } else {
            inventoryOut = inv;
            inventoryIn = EmptyInventory.INSTANCE;
        }
        inventorySpecial = EmptyInventory.INSTANCE;
    }

    @Override
    public ContainerNxN getServerGUI(EntityPlayer player, int internalID) {
        return new ContainerNxN(getHost(), player, tier + 1, GuiTextures.SlotTextures.SLOT_DEFAULT);
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return new GuiContainerNxN(getServerGUI(player, internalID));
    }

    @Override
    public void onInInventoryModified(int inventory) {
        if (multiBlock != null) {
            multiBlock.onInInventoryModified(inventory);
        }
    }

    public static class OutputBus extends Hatch_Item {

        public OutputBus(HostMachineTile baseTile, String name, int tier) {
            super(baseTile, name, Textures.output, tier);
           set(new ArrayRecipedInventory((tier + 1) * (tier + 1), 64), false);
        }

        protected OutputBus(HostMachineTile baseTile, OutputBus copyFrom) {
            super(baseTile, copyFrom);
            set(new ArrayRecipedInventory(copyFrom.inventoryOut.size(), 64), false);
        }

        @Override
        public MultiMachineBase.HatchType getType() {
            return MultiMachineBase.HatchType.ItemOut;
        }

        @Override
        public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
            return new OutputBus(baseTile, this);
        }
    }

    public static class InputBus extends Hatch_Item {

        public InputBus(HostMachineTile baseTile, String name, int tier) {
            super(baseTile, name, Textures.input, tier);
            set(new ArrayRecipedInventory((tier + 1) * (tier + 1), 64), true);
        }

        protected InputBus(HostMachineTile baseTile, InputBus copyFrom) {
            super(baseTile, copyFrom);
            set(new ArrayRecipedInventory(copyFrom.inventoryIn.size(), 64), true);
        }

        @Override
        public MultiMachineBase.HatchType getType() {
            return MultiMachineBase.HatchType.ItemIn;
        }

        @Override
        public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
            return new InputBus(baseTile, this);
        }
    }


}
