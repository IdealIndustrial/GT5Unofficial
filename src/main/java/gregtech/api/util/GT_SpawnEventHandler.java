package gregtech.api.util;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_MonsterRepellent;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

public class GT_SpawnEventHandler {

    public static volatile List<int[]> mobReps = new ArrayList();

    public GT_SpawnEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void denyMobSpawn(CheckSpawn event) {
        if (event.getResult() == Event.Result.ALLOW || (event.entityLiving instanceof EntityPlayer)
         || (event.entityLiving instanceof EntityTameable && (((EntityTameable) event.entityLiving).isTamed()))) {
            return;
        }

        boolean spawnIsNeutral = (
                event.entityLiving instanceof EntityPigZombie
                        || event.entityLiving instanceof EntityOcelot
                        || event.entityLiving instanceof EntityBat
                        || event.entityLiving instanceof EntitySquid
                        || event.entityLiving instanceof EntityWolf);

        boolean spawnIsFriendly = event.entityLiving.isCreatureType(EnumCreatureType.creature, false);

        for (int[] rep : mobReps) {
            if (rep[3] == event.entity.worldObj.provider.dimensionId) {
                TileEntity tTile = event.entity.worldObj.getTileEntity(rep[0], rep[1], rep[2]);
                if (tTile instanceof BaseMetaTileEntity && ((BaseMetaTileEntity) tTile).getMetaTileEntity() instanceof GT_MetaTileEntity_MonsterRepellent) {
                    int r = ((GT_MetaTileEntity_MonsterRepellent) ((BaseMetaTileEntity) tTile).getMetaTileEntity()).mRange;
                    boolean neutralsAllowed =
                            ((GT_MetaTileEntity_MonsterRepellent) ((BaseMetaTileEntity) tTile).getMetaTileEntity()).repMode == GT_MetaTileEntity_MonsterRepellent.repellationMode.HOSTILES;

                    boolean anythingAllowed = ((GT_MetaTileEntity_MonsterRepellent) ((BaseMetaTileEntity) tTile).getMetaTileEntity()).repMode != GT_MetaTileEntity_MonsterRepellent.repellationMode.EVERYONE;

                    double dx = rep[0] + 0.5F - event.entity.posX;
                    double dy = rep[1] + 0.5F - event.entity.posY;
                    double dz = rep[2] + 0.5F - event.entity.posZ;
                    //Original - sphere: (dx * dx + dz * dz + dy * dy) <= Math.pow(r, 2)
                    //New - cube: (Math.abs(dx) <= r && Math.abs(dz) <= r && Math.abs(dy) <= r)
                    if (Math.abs(dx) <= r && Math.abs(dz) <= r && Math.abs(dy) <= r) {
                        if ((!neutralsAllowed && spawnIsNeutral) || (!anythingAllowed) || (!spawnIsFriendly && !spawnIsNeutral)) {
                            event.setResult(Event.Result.DENY);
                            break;
                        }
                    }
                }
            }
        }
    }
}
