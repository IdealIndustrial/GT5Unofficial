package idealindustrial.autogen.material.submaterial;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public enum BlockType {
    METALLIC(Material.iron, Block.soundTypeMetal, 8);

    Material material;
    Block.SoundType soundType;
    int classCount;

     BlockType(Material material, Block.SoundType soundType, int classCount) {
        this.material = material;
        this.soundType = soundType;
        this.classCount = classCount;
    }

    public Material getMaterial() {
        return material;
    }

    public Block.SoundType getSoundType() {
        return soundType;
    }

    public int getClassCount() {
        return classCount;
    }
}
