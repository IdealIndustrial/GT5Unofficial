package gregtech.api.threads;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.IMachineBlockUpdateable;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

public class GT_Runnable_MachineBlockUpdate implements Runnable {
    private final int mX, mY, mZ;
    private final World mWorld;
    protected int mTime;
    protected int mTimer;
    protected boolean mShouldRun = true;

    static HashSet<GT_Runnable_MachineBlockUpdate> mWaitingThreads = new HashSet<>();

    public GT_Runnable_MachineBlockUpdate(World aWorld, int aX, int aY, int aZ, int aDelay, int aRadius) {
        mWorld = aWorld;
        mX = aX;
        mY = aY;
        mZ = aZ;
        mTimer = mTime = aDelay;
        if (!mWaitingThreads.contains(this)) {
            mWaitingThreads.add(this);
            new PathFinder(aWorld, aX, aY, aZ, aRadius, this).run();
        }
    }

    public GT_Runnable_MachineBlockUpdate(World aWorld, int aX, int aY, int aZ) {
        this(aWorld, aX, aY, aZ, 50, 2);
    }

    private static void stepToUpdateMachine(World aWorld, int aX, int aY, int aZ, HashSet<ChunkPosition> aList) {
        aList.add(new ChunkPosition(aX, aY, aZ));
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if (tTileEntity instanceof IMachineBlockUpdateable)
            ((IMachineBlockUpdateable) tTileEntity).onMachineBlockUpdate();
        Block b = aWorld.getBlock(aX, aY, aZ);
        int meta = aWorld.getBlockMetadata(aX, aY, aZ);
        if (b == GregTech_API.sBlockMachines && meta / 4 == 2)
            return;
        if (aList.size() < 5 || (tTileEntity instanceof IMachineBlockUpdateable) || GregTech_API.isMachineBlock(b, meta)) {
            if (!aList.contains(new ChunkPosition(aX + 1, aY, aZ))) stepToUpdateMachine(aWorld, aX + 1, aY, aZ, aList);
            if (!aList.contains(new ChunkPosition(aX - 1, aY, aZ))) stepToUpdateMachine(aWorld, aX - 1, aY, aZ, aList);
            if (!aList.contains(new ChunkPosition(aX, aY + 1, aZ))) stepToUpdateMachine(aWorld, aX, aY + 1, aZ, aList);
            if (!aList.contains(new ChunkPosition(aX, aY - 1, aZ))) stepToUpdateMachine(aWorld, aX, aY - 1, aZ, aList);
            if (!aList.contains(new ChunkPosition(aX, aY, aZ + 1))) stepToUpdateMachine(aWorld, aX, aY, aZ + 1, aList);
            if (!aList.contains(new ChunkPosition(aX, aY, aZ - 1))) stepToUpdateMachine(aWorld, aX, aY, aZ - 1, aList);
        }
    }

    public static void onTick() {
        Iterator<GT_Runnable_MachineBlockUpdate> iterator = mWaitingThreads.iterator();
        while (iterator.hasNext()) {
            GT_Runnable_MachineBlockUpdate tThread = iterator.next();
            if (tThread.mTimer-- < 0) {
                if (tThread.mShouldRun)
                    new Thread(tThread).start();
                iterator.remove();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GT_Runnable_MachineBlockUpdate that = (GT_Runnable_MachineBlockUpdate) o;
        return mX == that.mX &&
                mY == that.mY &&
                mZ == that.mZ &&
                mWorld.provider.dimensionId ==  that.mWorld.provider.dimensionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mX, mY, mZ, mWorld.provider.dimensionId);
    }

    @Override
    public void run() {
        try {
            stepToUpdateMachine(mWorld, mX, mY, mZ, new HashSet<>());
        } catch (Throwable e) {/**/}
    }

    private static class PathFinder implements Runnable {
        World mWorld;
        int mX, mY, mZ;
        HashSet<ChunkPosition> mNextQueue;
        HashSet<ChunkPosition> mRNextQueue;
        HashSet<ChunkPosition> mAlreadyPassedSet;
        int mDepth;
        GT_Runnable_MachineBlockUpdate mUpdater;

        public PathFinder(World aWorld, int aX, int aY, int aZ, int aDepth, GT_Runnable_MachineBlockUpdate aUpdater) {
            this.mWorld = aWorld;
            this.mX = aX;
            this.mY = aY;
            this.mZ = aZ;
            mNextQueue = new HashSet<>();
            mRNextQueue = new HashSet<>();
            mAlreadyPassedSet = new HashSet<>();
            mNextQueue.add(new ChunkPosition(aX,aY,aZ));
            mUpdater =aUpdater;
            mDepth = aDepth;
        }

        @Override
        public void run() {
            if (find(mDepth)) {
                mUpdater.mShouldRun = false;

            }
        }

        public boolean find(int aDepth) {
            if (aDepth < 0)
                return false;
            for (ChunkPosition p : mNextQueue) {
                if (processNode(mWorld, p, mAlreadyPassedSet))
                    return true;
            }
            mNextQueue = mRNextQueue;
            mRNextQueue = new HashSet<>();
            return find(--aDepth);

        }

        public boolean processNode(World aWorld, ChunkPosition aPos, HashSet<ChunkPosition> aList) {

            int aZ = aPos.chunkPosZ;
            int aY = aPos.chunkPosY;
            int aX = aPos.chunkPosX;

            for (GT_Runnable_MachineBlockUpdate r : mWaitingThreads) {
                if (r.mX == aX && r.mY == aY && r.mZ == aZ &&
                aWorld.provider.dimensionId == r.mWorld.provider.dimensionId &&
                !mUpdater.equals(r)) {
                    r.mTimer = r.mTime;
                    return true;
                }
            }

            aList.add(new ChunkPosition(aX, aY, aZ));
            TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
            if (aList.size() < 5 || (tTileEntity instanceof IMachineBlockUpdateable) || GregTech_API.isMachineBlock(aWorld.getBlock(aX, aY, aZ), aWorld.getBlockMetadata(aX, aY, aZ))) {
                ChunkPosition pos;
                if (!aList.contains(pos = new ChunkPosition(aX + 1, aY, aZ)))
                    mRNextQueue.add(pos);
                if (!aList.contains(pos = new ChunkPosition(aX, aY, aZ + 1)))
                    mRNextQueue.add(pos);
                if (!aList.contains(pos = new ChunkPosition(aX, aY + 1, aZ)))
                    mRNextQueue.add(pos);
                if (!aList.contains(pos = new ChunkPosition(aX - 1, aY, aZ)))
                    mRNextQueue.add(pos);
                if (!aList.contains(pos = new ChunkPosition(aX, aY - 1, aZ)))
                    mRNextQueue.add(pos);
                if (!aList.contains(pos = new ChunkPosition(aX, aY, aZ - 1)))
                    mRNextQueue.add(pos);
            }
            return false;
        }
    }
}