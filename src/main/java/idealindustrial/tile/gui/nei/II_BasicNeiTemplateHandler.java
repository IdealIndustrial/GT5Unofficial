package idealindustrial.tile.gui.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import idealindustrial.autogen.oredict.OreDict;
import idealindustrial.autogen.oredict.OreInfo;
import idealindustrial.recipe.*;
import idealindustrial.util.fluid.II_FluidHelper;
import idealindustrial.util.item.CheckType;
import idealindustrial.util.item.HashedStack;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import idealindustrial.util.misc.Function2;
import idealindustrial.util.misc.II_Paths;
import idealindustrial.util.misc.II_Util;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.util.*;
import java.util.function.Function;

import static idealindustrial.tile.gui.base.component.GuiTextures.PROCESSING_ARROWS;
import static idealindustrial.tile.gui.base.component.GuiTextures.SLOTS;

public class II_BasicNeiTemplateHandler extends TemplateRecipeHandler {

    RecipeMap<?> map;
    IRecipeGuiParams params;

    public II_BasicNeiTemplateHandler(RecipeMap<?> map) {
        this.map = map;
        this.params = map.getGuiParams();
    }

    @Override
    public String getGuiTexture() {
        return II_Paths.PATH_GUI + "BasicGuiNoInventory.png";
    }

    @Override
    public String getRecipeName() {
        return map.getName();
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(-4, -8, 1, 3, 174, 78);

        int x = -4, y = -8;
        GuiDraw.changeTexture(SLOTS.location());
        for (GuiSlotDefinition slot : params) {
            int id = slot.textureID;
            int textureX = SLOTS.idToTextureX(id), textureY = SLOTS.idToTextureY(id);
            GuiDraw.drawTexturedModalRect(slot.x + x - 1, slot.y + y - 1, textureX, textureY, 18, 18);
        }

        GuiDraw.changeTexture(PROCESSING_ARROWS.location());
        GuiArrowDefinition arrow = params.getArrow();
        int arrowX = x + arrow.x, arrowY = y + arrow.y;
        int textureX = PROCESSING_ARROWS.idToTextureX(arrow.textureID), textureY = PROCESSING_ARROWS.idToTextureY(arrow.textureID);
        GuiDraw.drawTexturedModalRect(arrowX, arrowY, textureX, textureY, 20, 17);
        GuiDraw.drawTexturedModalRect(arrowX, arrowY, textureX + 20, textureY, (cycleticks >> 1) % 20, 17);
    }


    @Override
    public void drawExtras(int recipeID) {
        IMachineRecipe recipe = arecipes.get(recipeID) instanceof II_CachedRecipe ? ((II_CachedRecipe) arecipes.get(recipeID)).recipe : null;
        if (recipe == null) {
            return;
        }
        RecipeEnergyParams params = recipe.recipeParams();
        String[] ar = new String[]{
                "Total: " + params.total() + " EU",
                "Usage: " + params.voltage * params.amperage + " EU/t",
                "Voltage: " + params.voltage + " EU",
                "Amperage: " + params.amperage
        };
        drawArray(10, 73, -16777216, ar);

    }

    public static void drawArray(int x, int y, int color, String... text) {
        for (String s : text) {
            drawText(x, y += 10, s, color);
        }
    }

    public static void drawText(int x, int y, String str, int color) {
        Minecraft.getMinecraft().fontRenderer.drawString(str, x, y, color);
    }

    @Override
    public void drawProgressBar(int x, int y, int tx, int ty, int w, int h, float completion, int direction) {
        super.drawProgressBar(x, y, tx, ty, w, h, completion, direction);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result == null) {
            return;
        }
        Set<IMachineRecipe> out = loadRecipes(result, map::getCraftingRecipes);
        out.forEach(recipe -> arecipes.add(new II_CachedRecipe(recipe)));

    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return new II_BasicNeiTemplateHandler(map);
    }

    protected Set<IMachineRecipe> loadRecipes(ItemStack result, Function<II_StackSignature, Set<? extends IMachineRecipe>> recipeFunction) {
        Set<IMachineRecipe> out = new HashSet<>();
        Collection<OreInfo> infoSet = OreDict.getInfo(new HashedStack(result));
        if (infoSet != null) {
            for (OreInfo info : infoSet) {
                Set<? extends IMachineRecipe> recipes = recipeFunction.apply(new II_StackSignature(info, 1));
                if (recipes != null) {
                    out.addAll(recipes);
                }
            }
        } else {
            Set<? extends IMachineRecipe> recipes = recipeFunction.apply(new II_StackSignature(result, CheckType.DAMAGE));
            if (recipes != null) {
                out.addAll(recipes);
            }
        }
        return out;
    }


    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient == null) {
            return;
        }
        Set<IMachineRecipe> out = loadRecipes(ingredient, map::getUsageRecipes);
        out.forEach(recipe -> arecipes.add(new II_CachedRecipe(recipe)));
    }

    protected static class II_PositionedStack extends PositionedStack {

        public II_PositionedStack(II_StackSignature signature, GuiSlotDefinition definition) {
            this(signature.correspondingStacks().stream().map(hs -> hs.toItemStack(signature.amount)).toArray(ItemStack[]::new), definition.x, definition.y);
        }

        public II_PositionedStack(Object stack, int x, int y) {
            super(stack, x - 4, y - 8, false);
        }

        public II_PositionedStack(II_ItemStack signature, GuiSlotDefinition definition) {
            this(signature.toMCStack(), definition.x, definition.y);
        }

        public II_PositionedStack(FluidStack signature, GuiSlotDefinition definition) {
            this(II_FluidHelper.getFluidDisplayStack(signature), definition.x, definition.y);
        }


    }

    public class II_CachedRecipe extends CachedRecipe {

        IMachineRecipe recipe;
        List<PositionedStack> inputs;
        List<PositionedStack> outputs;

        public II_CachedRecipe(IMachineRecipe recipe) {
            this.recipe = recipe;
            this.inputs = new ArrayList<>(recipe.getInputs().length + recipe.getFluidInputs().length);
            this.outputs = new ArrayList<>(recipe.getOutputs().length + recipe.getFluidOutputs().length);

            addTo(inputs, recipe.getInputs(), params.getItemsIn(), II_PositionedStack::new);
            addTo(inputs, recipe.getFluidInputs(), params.getFluidsIn(), II_PositionedStack::new);

            addTo(outputs, recipe.getOutputs(), params.getItemsOut(), II_PositionedStack::new);
            addTo(outputs, recipe.getFluidOutputs(), params.getFluidsOut(), II_PositionedStack::new);

        }

        protected <T> void addTo(List<PositionedStack> list, T[] objects, GuiSlotDefinition[] definitions, Function2<T, GuiSlotDefinition, PositionedStack> converter) {
            for (int i = 0; i < objects.length; i++) {
                list.add(converter.apply(objects[i], definitions[i]));
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(II_BasicNeiTemplateHandler.this.cycleticks / 10, this.inputs);
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            return outputs;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }
    }
}
