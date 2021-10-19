package idealindustrial.hooks;

import appeng.client.gui.implementations.GuiMEMonitorable;
import codechicken.nei.BookmarkPanel;
import com.google.common.collect.HashBasedTable;
import extracells.gui.GuiFluidTerminal;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import mcp.mobius.mobiuscore.profiler.ProfilerSection;
import mcp.mobius.opis.data.holders.basetypes.CoordinatesBlock;
import mcp.mobius.opis.data.holders.newtypes.DataBlockTileEntityPerClass;
import mcp.mobius.opis.data.managers.TileEntityManager;
import mcp.mobius.opis.data.profilers.ProfilerTileEntityUpdate;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.Iterator;

public class II_OpisPatch {

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static ArrayList<DataBlockTileEntityPerClass> getCumulativeTimingTileEntities(TileEntityManager ignored) { //shitty decompile but it works
        HashBasedTable<Integer, Integer, DataBlockTileEntityPerClass> data = HashBasedTable.create();

        CoordinatesBlock coord;
        int id;
        int meta;
        Block block;
        TileEntity tileEntity;
        for (Iterator<CoordinatesBlock> iterator = ((ProfilerTileEntityUpdate) ProfilerSection.TILEENT_UPDATETIME.getProfiler()).data.keySet().iterator(); iterator.hasNext(); data.get(id, meta).add(((ProfilerTileEntityUpdate) ProfilerSection.TILEENT_UPDATETIME.getProfiler()).data.get(coord).getGeometricMean())) {
            coord = iterator.next();
            World world = DimensionManager.getWorld(coord.dim);
            id = Block.getIdFromBlock(block = world.getBlock(coord.x, coord.y, coord.z));
            meta = world.getBlockMetadata(coord.x, coord.y, coord.z);
            if (!data.contains(id, meta)) {
                data.put(id, meta, new DataBlockTileEntityPerClass(id, meta));
            }
        }

        return new ArrayList(data.values());
    }




}
