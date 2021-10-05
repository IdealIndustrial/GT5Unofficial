package idealindustrial.tile.impl.multi.struct;

import net.minecraft.client.particle.EntityFX;

public interface IGuideRenderer {

    void registerNewParticle(EntityFX particle);

    int getMaxAge();
}
