package idealindustrial.recipe.editor;

import cpw.mods.fml.client.FMLClientHandler;
import idealindustrial.recipe.*;
import idealindustrial.tile.gui.base.GenericGuiContainer;
import idealindustrial.tile.gui.base.component.GuiButtonCallback;
import idealindustrial.tile.gui.base.component.GuiIntegerField;
import idealindustrial.tile.interfaces.host.NetworkedInventory;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import idealindustrial.util.json.ArrayObject;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidStack;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static idealindustrial.tile.gui.base.component.GuiTextures.PROCESSING_ARROWS;

public class RecipeEditorGuiContainer extends GenericGuiContainer<RecipeEditorContainer> {

    RecipeMap<?> map;
    RecipeEditorContainer container;
    GuiButton add;
    GuiIntegerField vol, amp, duration;
    List<GuiTextField> boxes = new ArrayList<>();

    public RecipeEditorGuiContainer(RecipeMap<?> map, EntityPlayer player) {
        super(new RecipeEditorContainer(map, player), II_Paths.PATH_GUI + "BasicGuiNoInventory.png");
        container = (RecipeEditorContainer) super.inventorySlots;
        this.map = map;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initGui() {
        super.initGui();
        buttonList.add(add = new GuiButtonCallback(0, guiLeft + 135, guiTop + 85, 30, 15, "Add", this::addRecipe));
        boxes.add(vol = new GuiIntegerField(fontRendererObj,guiLeft + 30, guiTop + 85, 100, 10));
        boxes.add(amp = new GuiIntegerField(fontRendererObj,guiLeft + 30, guiTop + 100, 100, 10));
        boxes.add(duration = new GuiIntegerField(fontRendererObj,guiLeft + 30, guiTop + 115, 100, 10));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void addRecipe() {
        try {
            NetworkedInventory inv = container.tile;
            II_StackSignature[] inItems = parseSignatures(inv.getIn().size());
            II_ItemStack[] outItems = parseOutputs(inv.getIn().size(), inv.getOut().size());
            FluidStack[] inFluids = container.representation.parseInputsClient();
            FluidStack[] outFluids = container.representation.parseOutputsClient();
            inItems = Arrays.stream(inItems).filter(Objects::nonNull).toArray(II_StackSignature[]::new);
            outItems = Arrays.stream(outItems).filter(Objects::nonNull).toArray(II_ItemStack[]::new);
            inFluids = Arrays.stream(inFluids).filter(Objects::nonNull).toArray(FluidStack[]::new);
            outFluids = Arrays.stream(outFluids).filter(Objects::nonNull).toArray(FluidStack[]::new);
            RecipeEnergyParams params = new RecipeEnergyParams(vol.getValue(), amp.getValue(), duration.getValue());
            if ((inItems.length > 0 || inFluids.length > 0) && (outItems.length > 0 || outFluids.length > 0)) {
                IMachineRecipe recipe = new BasicMachineRecipe(inItems, outItems, inFluids, outFluids, params);
                ((BasicRecipeMap) map).addRecipe(recipe);
                putRecipe(recipe);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void putRecipe(IMachineRecipe recipe) throws IOException {
        RecipeMapStorage<?> storage = map.getJsonReflection();
        File dir = new File(FMLClientHandler.instance().getClient().mcDataDir, "recipes");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, storage.getFileName());
        RecipeMap tmp = map.newEmpty();
        if (!file.exists()) {
            file.createNewFile();
        } else {
            ArrayObject<IMachineRecipe> recipes = storage.getGson().fromJson(new FileReader(file), ArrayObject.getType(storage.getRecipeType()));
            if (recipes != null && recipes.contents != null) {
                for (IMachineRecipe rec : recipes.contents) {
                    tmp.addRecipe(rec);
                }
            }
        }
        tmp.addRecipe(recipe);
        String str = tmp.getJsonReflection().getGson().toJson(tmp.getJsonReflection().toArrayObject(), ArrayObject.getType(storage.getRecipeType()));
        FileWriter w = new FileWriter(file);
        w.write(str);
        w.close();
    }

    private II_StackSignature[] parseSignatures(int total) {
        II_StackSignature[] out = new II_StackSignature[total];
        for (int i = 0; i < out.length; i++) {
            II_SlotWithCheckType slot = (II_SlotWithCheckType) inventorySlots.getSlot(i);
            if (slot.getStack() == null) {
                continue;
            }
            out[i] = new II_StackSignature(slot.getStack(), slot.checkType);
        }
        return out;
    }

    private II_ItemStack[] parseOutputs(int start, int total) {
        II_ItemStack[] out = new II_ItemStack[total];
        for (int i = 0; i < out.length; i++) {
            Slot slot = inventorySlots.getSlot(start + i);
            if (slot.getStack() == null) {
                continue;
            }
            out[i] = new II_ItemStack(slot.getStack());
        }
        return out;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
        fontRendererObj.drawString(container.tile.getInventoryName(), 10, 10, 4210752, false);
        fontRendererObj.drawString("V:", 4, 85, 4210752, false);
        fontRendererObj.drawString("A:", 4, 100, 4210752, false);
        fontRendererObj.drawString("Time:", 4, 115, 4210752, false);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);
        int guiX = (width - xSize) / 2 - 1;
        int guiY = (height - ySize) / 2 - 1;
        GuiArrowDefinition arrow = container.params.getArrow();
        drawProgressBar(guiX + arrow.x, guiY + arrow.y, arrow);
        for (GuiTextField f : boxes) {
            f.drawTextBox();
        }


    }

    protected void drawProgressBar(int x, int y, GuiArrowDefinition arrow) {
        Minecraft.getMinecraft().renderEngine.bindTexture(PROCESSING_ARROWS.location());
        int textureX = PROCESSING_ARROWS.idToTextureX(arrow.textureID), textureY = PROCESSING_ARROWS.idToTextureY(arrow.textureID);
        drawTexturedModalRect(x, y, textureX, textureY, 20, 17);
        drawTexturedModalRect(x, y, textureX + 20, textureY, container.getProgress(), 17);
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        for (GuiTextField f : boxes) {
            f.mouseClicked(par1, par2, par3);
        }
    }

    @Override
    protected void keyTyped(char ch, int id) {
        super.keyTyped(ch, id);
        for (GuiTextField f : boxes) {
            f.textboxKeyTyped(ch, id);
        }
    }
}
