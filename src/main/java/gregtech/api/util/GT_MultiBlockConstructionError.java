package gregtech.api.util;


import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.net.GT_Packet;
import gregtech.api.net.GT_Packet_MultiBlockError;
import gregtech.common.GT_Network;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

//a bit procedural approach for easier serialization.
//may be one day someone will write more generalized version
public interface GT_MultiBlockConstructionError {

    String toLocalString();

    void save(ByteArrayDataOutput to);

    void load(ByteArrayDataInput from);

    static void registerErrors() {
        GT_Packet_MultiBlockError.registerNewErrorType(WrongBlock.class);
        GT_Packet_MultiBlockError.registerNewErrorType(NotEnoughCasings.class);
        GT_Packet_MultiBlockError.registerNewErrorType(LangString.class);
    }

    static void sendToClients(GT_MultiBlockConstructionError error, IGregTechTileEntity te) {
        GT_Packet packet = new GT_Packet_MultiBlockError(te.getXCoord(), te.getYCoord(), te.getZCoord(), error);
        GT_Values.NW.sendPacketToAllPlayersInRange(te.getWorld(), packet, te.getXCoord(), te.getZCoord());
    }

    class LangString implements GT_MultiBlockConstructionError {

        private String unlocal;

        public LangString(String unlocal) {
            this.unlocal = unlocal;
        }

        public LangString() {

        }

        @Override
        public String toLocalString() {
            return StatCollector.translateToLocal(unlocal);
        }

        @Override
        public void save(ByteArrayDataOutput to) {
            to.writeUTF(unlocal);
        }

        @Override
        public void load(ByteArrayDataInput from) {
            unlocal = from.readUTF();
        }
    }

    class NotEnoughCasings implements GT_MultiBlockConstructionError {

        private int atLeastCasings;
        private int casingsGot;

        public NotEnoughCasings() {

        }

        public NotEnoughCasings(int atLeastCasings, int casingsGot) {
            this.atLeastCasings = atLeastCasings;
            this.casingsGot = casingsGot;
        }

        @Override
        public String toLocalString() {
            return StatCollector.translateToLocal("multiblock.error.casingsatleast")
                    + " " + atLeastCasings
                    + StatCollector.translateToLocal("multiblock.error.casingsgot") + " " + casingsGot;

        }

        @Override
        public void save(ByteArrayDataOutput to) {
            to.writeInt(atLeastCasings);
            to.writeInt(casingsGot);

        }

        @Override
        public void load(ByteArrayDataInput from) {
            atLeastCasings = from.readInt();
            casingsGot = from.readInt();
        }
    }

    class WrongBlock implements GT_MultiBlockConstructionError {
        private String expectedUnlocalName;
        private ItemStack expectedAsStack;
        private int x;
        private int y;
        private int z;
        private boolean canBeHatch;

        public WrongBlock() {

        }

        public WrongBlock(ItemStack expectedAsStack, int x, int y, int z, boolean canBeHatch) {
            this.expectedAsStack = expectedAsStack;
            this.expectedUnlocalName = "Stack";
            this.x = x;
            this.y = y;
            this.z = z;
            this.canBeHatch = canBeHatch;
        }

        public WrongBlock(String expectedUnlocalName, int x, int y, int z, boolean canBeHatch) {
            this.expectedUnlocalName = expectedUnlocalName;
            this.x = x;
            this.y = y;
            this.z = z;
            this.canBeHatch = canBeHatch;
        }

        private String at() {
            return StatCollector.translateToLocal("multiblock.error.at") + " [" + x + ", " + y + ", " + z + "]";
        }

        @Override
        public String toLocalString() {
            if (expectedUnlocalName.equals("")) {
                return StatCollector.translateToLocal("multiblock.error.expected.hatch") + " " + at();
            }
            if (expectedUnlocalName.equals("invalidHatch")) {
                return StatCollector.translateToLocal("multiblock.error.invalid.hatch") + " " + at();
            }
            String orHatch = canBeHatch ?
                    StatCollector.translateToLocal("multiblock.error.orHatch") + " "
                    : "";
            return StatCollector.translateToLocal("multiblock.error.expected") +
                    " " +
                    getExpected() +
                    orHatch +
                    " " +
                    at();

        }

        private String getExpected() {
            if (expectedAsStack == null) {
                return StatCollector.translateToLocal(expectedUnlocalName);
            }
            return expectedAsStack.getDisplayName();
        }

        @Override
        public void save(ByteArrayDataOutput to) {
            to.writeUTF(expectedUnlocalName);
            if (expectedAsStack == null) {
                to.writeInt(-1);
            } else {
                to.writeInt(Item.getIdFromItem(expectedAsStack.getItem()));
                to.writeInt(expectedAsStack.getItemDamage());
            }
            to.writeInt(x);
            to.writeInt(y);
            to.writeInt(z);
            to.writeBoolean(canBeHatch);

        }

        @Override
        public void load(ByteArrayDataInput from) {
            expectedUnlocalName = from.readUTF();
            int id = from.readInt();
            if (id >= 0) {
                Item item = Item.getItemById(id);
                expectedAsStack = new ItemStack(item, 1, from.readInt());
            }
            x = from.readInt();
            y = from.readInt();
            z = from.readInt();
            canBeHatch = from.readBoolean();
        }
    }

}
