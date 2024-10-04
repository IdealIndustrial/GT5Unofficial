package idealindustrial.hooks;

import cofh.api.energy.IEnergyContainerItem;
import crazypants.enderio.conduit.IConduitBundle;
import crazypants.enderio.conduit.power.PowerConduit;
import crazypants.enderio.power.IPowerInterface;
import crazypants.enderio.power.PowerHandlerUtil;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import ic2.core.Ic2Items;
import ic2.core.block.wiring.TileEntityChargepadBlock;
import ic2.core.item.GatewayElectricItemManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class II_EUtoRFPatch {


    @Hook(injectOnExit = false, returnCondition = ReturnCondition.ALWAYS)
    public static IElectricItemManager getManager(GatewayElectricItemManager manager, ItemStack stack) {
        Item item = stack.getItem();
        if (item == null) {
            return null;
        } else if (item instanceof ISpecialElectricItem) {
            return ((ISpecialElectricItem) item).getManager(stack);
        } else if (item instanceof IEnergyContainerItem) {
            return new DummyEUtoRFCompatManager((IEnergyContainerItem) item);//really shitty, todo: think a way without new
        } else {
            return (IElectricItemManager) (item instanceof IElectricItem ? ElectricItem.rawManager : ElectricItem.getBackupManager(stack));
        }
    }

    @Hook(injectOnExit = false, returnCondition = ReturnCondition.ALWAYS)
    public static void chargeitems(TileEntityChargepadBlock block, ItemStack itemstack, int chargefactor) {
        if (itemstack.getItem() instanceof IElectricItem || itemstack.getItem() instanceof IEnergyContainerItem) {
            if (itemstack.getItem() != Ic2Items.debug.getItem()) {
                double freeamount = ElectricItem.manager.charge(itemstack, 1.0D / 0.0, block.tier, true, true);
                double charge = 0.0D;
                if (freeamount >= 0.0D) {
                    if (block.energy >= freeamount) {
                        if (freeamount >= (double) (chargefactor * 2)) {
                            charge = (double) (chargefactor * 2);
                        } else {
                            charge = freeamount;
                        }
                    } else {
                        charge = block.energy;
                    }

                    block.energy -= ElectricItem.manager.charge(itemstack, charge, block.tier, true, false);
                }

            }
        }
    }


    private static class DummyEUtoRFCompatManager implements IElectricItemManager {

        private final IEnergyContainerItem item;

        public DummyEUtoRFCompatManager(IEnergyContainerItem item) {
            this.item = item;
        }

        @Override
        public double charge(ItemStack is, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
            amount = Math.min(amount, GT_Values.V[tier]);
            int max = item.getMaxEnergyStored(is);
            int cur = item.getEnergyStored(is);
            double canUse = Math.min(amount, max - cur);
            amount = toRF(canUse);
            item.receiveEnergy(is, (int) amount, simulate);
            return amount;
        }

        @Override
        public double discharge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
            amount = Math.min(amount, GT_Values.V[tier]);
            amount = toRF(amount);
            return item.extractEnergy(itemStack, (int) amount, simulate);
        }

        @Override
        public double getCharge(ItemStack is) {
            return toEU(item.getEnergyStored(is));
        }

        @Override
        public boolean canUse(ItemStack is, double amount) {
            return this.getCharge(is) > amount;
        }

        @Override
        public boolean use(ItemStack is, double amount, EntityLivingBase entity) {
            if (this.canUse(is, amount)) {
                item.extractEnergy(is, (int) amount, false);
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity) {

        }

        @Override
        public String getToolTip(ItemStack itemStack) {
            return null;
        }

        private static double toRF(double eu) {
            return eu * GregTech_API.mEUtoRF / 100d;
        }

        private static double toEU(double rf) {
            return rf / GregTech_API.mEUtoRF * 100d;
        }

    }


    @Hook(injectOnExit = false, returnCondition = ReturnCondition.ALWAYS)
    public static IPowerInterface getExternalPowerReceptor(PowerConduit conduit, ForgeDirection direction) {
        TileEntity te = conduit.getBundle().getEntity();
        World world = te.getWorldObj();
        if (world == null) {
            return null;
        } else {
            TileEntity test = world.getTileEntity(te.xCoord + direction.offsetX, te.yCoord + direction.offsetY, te.zCoord + direction.offsetZ);
            if (test instanceof BaseMetaTileEntity && ((BaseMetaTileEntity) test).outputsEnergyTo((byte) direction.getOpposite().ordinal())) {
                return new DummyIPowerInterfaceImpl(((BaseMetaTileEntity) test));
            }
            return test instanceof IConduitBundle ? null : PowerHandlerUtil.create(test);
        }
    }

    private static class DummyIPowerInterfaceImpl implements IPowerInterface {
        BaseMetaTileEntity tile;

        public DummyIPowerInterfaceImpl(BaseMetaTileEntity test) {
            this.tile = test;
        }

        @Override
        public Object getDelegate() {
            return null;
        }

        @Override
        public boolean canConduitConnect(ForgeDirection forgeDirection) {
            return tile.outputsEnergyTo((byte) forgeDirection.getOpposite().ordinal());
        }

        @Override
        public int getEnergyStored(ForgeDirection forgeDirection) {
            return 0;
        }

        @Override
        public int getMaxEnergyStored(ForgeDirection forgeDirection) {
            return 0;
        }

        @Override
        public int getPowerRequest(ForgeDirection forgeDirection) {
            return 0;
        }

        @Override
        public int getMinEnergyReceived(ForgeDirection forgeDirection) {
            return 0;
        }

        @Override
        public int recieveEnergy(ForgeDirection forgeDirection, int i) {
            return 0;
        }

        @Override
        public boolean isOutputOnly() {
            return true;
        }

        @Override
        public boolean isInputOnly() {
            return false;
        }
    }
}
