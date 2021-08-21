package idealindustrial.recipe;

public class II_GuiArrowDefinition extends II_GuiObjectDefinition {
    public int direction;

    public II_GuiArrowDefinition(int x, int y, int textureID, int direction) {
        super(x, y, textureID);
        this.direction = direction;
    }
}
