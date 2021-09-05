package idealindustrial.recipe;

public class GuiArrowDefinition extends GuiObjectDefinition {
    public int direction;

    public GuiArrowDefinition(int x, int y, int textureID, int direction) {
        super(x, y, textureID);
        this.direction = direction;
    }
}
