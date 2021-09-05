package idealindustrial.tile.meta.multi.struct;

import net.minecraft.client.particle.EntityFX;

public interface IGuideRenderer {

    void registerNewParticle(EntityFX particle);

    int getMaxAge();
}
