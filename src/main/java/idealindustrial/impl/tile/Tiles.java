package idealindustrial.impl.tile;

import idealindustrial.api.recipe.IRecipeGuiParams;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.recipe.*;
import idealindustrial.impl.textures.TextureUtil;
import idealindustrial.impl.textures.Textures;
import idealindustrial.impl.tile.gui.base.component.SlotHoloEvent;
import idealindustrial.impl.tile.impl.recipe.TileMachineBasicRecipe;
import idealindustrial.impl.tile.impl.recipe.TileMachineReciped;
import idealindustrial.impl.tile.module.EventRecipedModule;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.parameter.RecipedMachineStats;

import static idealindustrial.impl.recipe.RecipeGuiParamsBuilder.SlotType.Holo;
import static idealindustrial.impl.recipe.RecipeGuiParamsBuilder.SlotType.ItemsSpecial;
import static idealindustrial.impl.tile.TileEvents.MODULE_START_PROCESSING;
import static idealindustrial.impl.tile.gui.base.component.GuiTextures.SlotTextures.SLOT_HOLO_HAMMER;
import static idealindustrial.util.misc.II_TileUtil.makeBaseMachineTile;

/**
 * just an utility class that contains all tiles that don't have their own class
 * for example Primitive forge
 * this class is necessary to keep all information related to the specific tile consistent
 * keep in mind that this class is just a container, it doesn't register anything by itself
 */
public class Tiles {


    public static Tile<?> makePrimitiveForge() {
        String name = "Primitive Forge";
        String mapName = name + " Recipes";
        //using getMap just to be sure that map is constructed only once
        RecipeMap<BasicMachineRecipe> recipes = RecipeMaps.getMap(mapName, () -> {
            IRecipeGuiParams guiParams = new RecipeGuiParamsBuilder(2, 2, 1, 0, 0)
                    .setMachineRecipeSlotCoords(43, 107, 25, 63)
                    .moveSlot(ItemsSpecial, 0, 30, 25).construct();
            return new BasicRecipeMap<>(mapName, true, false, guiParams, BasicMachineRecipe.class);
        });

        return new TileMachineBasicRecipe(makeBaseMachineTile(), name,
                II_StreamUtil.arrayOf(Textures.baseTiredTextures[1], new ITexture[10]),
                TextureUtil.loadRecipedMachineTextures(TextureUtil.facing2Configuration, name),
                recipes,
                new RecipedMachineStats(0, 2, 2, 1, 64, 0, 0, 0, 0, 0));
    }

    public static Tile<?> makePrimitiveAnvil() {
        String name = "Primitive Anvil";
        String mapName = name + " Recipes";
        RecipeMap<ShapedMachineRecipe> recipes = RecipeMaps.getMap(mapName, () -> {
            IRecipeGuiParams params = new RecipeGuiParamsBuilder(9, 1, 0, 0, 0, 1)
                    .setSlot(Holo, 0, 120, 5, SLOT_HOLO_HAMMER, (inv, id, x, y, t) -> new SlotHoloEvent(inv, id, x, y, t, MODULE_START_PROCESSING))
                    .construct();
            return new BasicRecipeMap<>(mapName, true, true, params, ShapedMachineRecipe.class);
        });
        RecipedMachineStats stats = new RecipedMachineStats(0, 9, 1, 0, 64, 0, 0, 0, 0, 0);
        return new TileMachineReciped<>(makeBaseMachineTile(), name,
                II_StreamUtil.arrayOf(Textures.baseTiredTextures[1], new ITexture[10]),
                TextureUtil.loadRecipedMachineTextures(TextureUtil.facing2Configuration, name),
                recipes,
                stats,
                (th) -> new EventRecipedModule<>(th, stats, recipes));
    }

}
