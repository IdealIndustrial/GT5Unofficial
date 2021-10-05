package idealindustrial.tile.meta.multi.parts;

import gregtech.api.interfaces.ITexture;
import idealindustrial.textures.Textures;
import idealindustrial.tile.gui.base.ContainerNxN;
import idealindustrial.tile.gui.base.GuiContainerNxN;
import idealindustrial.tile.gui.base.component.GuiTextures;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.tile.interfaces.meta.MetaTile;
import idealindustrial.tile.meta.multi.BaseMultiMachine;
import idealindustrial.util.inventory.ArrayRecipedInventory;
import idealindustrial.util.inventory.EmptyInventory;
import idealindustrial.util.inventory.interfaces.InternalInventory;
import idealindustrial.util.misc.II_StreamUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public abstract class Hatch_Item extends BaseMetaTile_Hatch<BaseMachineTile, BaseMultiMachine<?>> {
    int tier;
    protected Hatch_Item(BaseMachineTile baseTile, String name, ITexture outFace, int tier) {
        super(baseTile, name,
                II_StreamUtil.arrayOf(Textures.baseTiredTextures[tier], new ITexture[8]),
                II_StreamUtil.setInNullAr(outFace, new ITexture[8], 3, 7)
        );
        hasInventory = true;
        this.tier = tier;

    }

    protected Hatch_Item(BaseMachineTile baseTile, Hatch_Item copyFrom) {
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
        return new ContainerNxN(getBase(), player, tier + 1, GuiTextures.SlotTextures.SLOT_DEFAULT);
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

        public OutputBus(BaseMachineTile baseTile, String name, int tier) {
            super(baseTile, name, Textures.output, tier);
           set(new ArrayRecipedInventory((tier + 1) * (tier + 1), 64), false);
        }

        protected OutputBus(BaseMachineTile baseTile, OutputBus copyFrom) {
            super(baseTile, copyFrom);
            set(new ArrayRecipedInventory(copyFrom.inventoryOut.size(), 64), false);
        }

        @Override
        public BaseMultiMachine.HatchType getType() {
            return BaseMultiMachine.HatchType.ItemOut;
        }

        @Override
        public MetaTile<BaseMachineTile> newMetaTile(BaseMachineTile baseTile) {
            return new OutputBus(baseTile, this);
        }
    }

    public static class InputBus extends Hatch_Item {

        public InputBus(BaseMachineTile baseTile, String name, int tier) {
            super(baseTile, name, Textures.input, tier);
            set(new ArrayRecipedInventory((tier + 1) * (tier + 1), 64), true);
        }

        protected InputBus(BaseMachineTile baseTile, InputBus copyFrom) {
            super(baseTile, copyFrom);
            set(new ArrayRecipedInventory(copyFrom.inventoryIn.size(), 64), true);
        }

        @Override
        public BaseMultiMachine.HatchType getType() {
            return BaseMultiMachine.HatchType.ItemIn;
        }

        @Override
        public MetaTile<BaseMachineTile> newMetaTile(BaseMachineTile baseTile) {
            return new InputBus(baseTile, this);
        }
    }


}
