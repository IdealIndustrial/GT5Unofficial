package gregtech.api.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_MultiBlockConstructionError;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Used to transfer Block Events in a much better fashion
 */
public class GT_Packet_MultiBlockError extends GT_Packet {

    private static List<Supplier<GT_MultiBlockConstructionError>> sErrorConstructors = new ArrayList<>();
    private static Map<Class<? extends GT_MultiBlockConstructionError>, Integer> sIdMap = new HashMap<>();

    public static void registerNewErrorType(Class<? extends GT_MultiBlockConstructionError> type) {
        if (sIdMap.containsKey(type)) {
            throw new IllegalArgumentException("Already registered type " + type.getSimpleName());
        }
        sIdMap.put(type, sErrorConstructors.size());
        try {
            Constructor<? extends GT_MultiBlockConstructionError> constructor = type.getConstructor();
            sErrorConstructors.add(() -> {
                try {
                    return constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Unable to construct error", e);
                }
            });
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Expected public constructor", e);
        }

    }

    private int mX, mZ;
    private short mY;
    private GT_MultiBlockConstructionError mError;

    public GT_Packet_MultiBlockError() {
        super(true);
    }

    /**
     *
     * @param aError is error or null to reset last error
     */
    public GT_Packet_MultiBlockError(int aX, short aY, int aZ, GT_MultiBlockConstructionError aError) {
        super(false);
        mX = aX;
        mY = aY;
        mZ = aZ;
        mError = aError;
        if (aError != null && !sIdMap.containsKey(aError.getClass())) {
            throw new IllegalArgumentException("Unregistered Error Type " + aError.getClass().getSimpleName());
        }
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeInt(mX);
        tOut.writeShort(mY);
        tOut.writeInt(mZ);
        if (mError != null) {
            tOut.writeInt(sIdMap.get(mError.getClass()));
        }else  {
            tOut.writeInt(-1);
        }
        mError.save(tOut);
        return tOut.toByteArray();
    }

    @Override
    public GT_Packet decode(ByteArrayDataInput aData) {
        return new GT_Packet_MultiBlockError(aData.readInt(), aData.readShort(), aData.readInt(), loadError(aData));
    }

    private static GT_MultiBlockConstructionError loadError(ByteArrayDataInput aData) {
        int id = aData.readInt();
        if (id < 0) {
            return null;
        }
        GT_MultiBlockConstructionError error =  sErrorConstructors.get(id).get();
        error.load(aData);
        return error;
    }

    @Override
    public void process(IBlockAccess aWorld) {
        if (aWorld != null) {
            TileEntity tTileEntity = aWorld.getTileEntity(mX, mY, mZ);
            if (tTileEntity instanceof IGregTechTileEntity) {
                if (((IGregTechTileEntity) tTileEntity).getMetaTileEntity() instanceof GT_MetaTileEntity_MultiBlockBase) {
                    ((GT_MetaTileEntity_MultiBlockBase) ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()).mLastError = mError;
                }
            }
        }
    }

    @Override
    public byte getPacketID() {
        return 10;
    }
}