package idealindustrial.impl.autogen.material.submaterial;


public class BlockInfo {

    protected int id;
    protected BlockType type;


    public BlockInfo(int id, BlockType type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public BlockType getType() {
        return type;
    }
}
