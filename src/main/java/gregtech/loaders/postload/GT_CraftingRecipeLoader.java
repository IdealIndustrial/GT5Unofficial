package gregtech.loaders.postload;

import cpw.mods.fml.common.Loader;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.loaders.postload.recipes.GT_ForestryRecipesLoader;
import gregtech.loaders.postload.recipes.GT_GraviSuiteRecipesLoader;
import gregtech.loaders.postload.recipes.GT_IndustialCraftRecipesLoader;
import gregtech.loaders.postload.recipes.GT_RailcraftRecipesLoader;
import ic2.core.Ic2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GT_CraftingRecipeLoader implements Runnable {
    private final static String aTextIron1 = "X X";
    private final static String aTextIron2 = "XXX";
    private final static String aTextRailcraft = "Railcraft";

    public void run() {

        new GT_IndustialCraftRecipesLoader().run();
        if(Loader.isModLoaded(aTextRailcraft))
            new GT_RailcraftRecipesLoader().run();
        if (Loader.isModLoaded("GraviSuite"))
            new GT_GraviSuiteRecipesLoader().run();
        if (Loader.isModLoaded(GT_Values.MOD_ID_FR))
            new GT_ForestryRecipesLoader().run();



        GT_Log.out.println("GT_Mod: Adding nerfed Vanilla Recipes.");
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.bucket, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"XhX", " X ", 'X', OrePrefixes.plate.get(Materials.Iron)});
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Bucket", true)) {
            GT_ModHandler.addCraftingRecipe(new ItemStack(Items.bucket, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{aTextIron1, " X ", 'X', OrePrefixes.ingot.get(Materials.Iron)});
        }
        ItemStack tMat = new ItemStack(Items.iron_ingot);
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.PressurePlate", true)) {
            ItemStack tStack;
            if (null != (tStack = GT_ModHandler.removeRecipe(tMat, tMat, null, null, null, null, null, null, null))) {
                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XXh", 'X', OrePrefixes.plate.get(Materials.Iron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.Iron)});
            }
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Door", true)) {
            ItemStack tStack;
            if (null != (tStack = GT_ModHandler.removeRecipe(tMat, tMat, null, tMat, tMat, null, tMat, tMat, null))) {
                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XX ", "XXh", "XX ", 'X', OrePrefixes.plate.get(Materials.Iron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.Iron)});
            }
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Cauldron", true)) {
            ItemStack tStack;
            if (null != (tStack = GT_ModHandler.removeRecipe(tMat, null, tMat, tMat, null, tMat, tMat, tMat, tMat))) {
                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{aTextIron1, "XhX", aTextIron2, 'X', OrePrefixes.plate.get(Materials.Iron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.Iron)});
            }
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Hopper", true)) {
            ItemStack tStack;
            if (null != (tStack = GT_ModHandler.removeRecipe(tMat, null, tMat, tMat, new ItemStack(Blocks.chest, 1, 0), tMat, null, tMat, null))) {
                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XwX", "XCX", " X ", 'X', OrePrefixes.plate.get(Materials.Iron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.Iron), 'C', "craftingChest"});
            }
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Bars", true)) {
            ItemStack tStack;
            if (null != (tStack = GT_ModHandler.removeRecipe(tMat, tMat, tMat, tMat, tMat, tMat, null, null, null))) {
                tStack.stackSize /= 2;
                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{" w ", aTextIron2, aTextIron2, 'X', OrePrefixes.stick.get(Materials.Iron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.Iron)});
            }
        }
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("ironFence", 6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextIron2, aTextIron2, " w ", 'X', OrePrefixes.stick.get(Materials.Iron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.Iron)});

        tMat = new ItemStack(Items.gold_ingot);
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Gold.PressurePlate", true)) {
            ItemStack tStack;
            if (null != (tStack = GT_ModHandler.removeRecipe(tMat, tMat, null, null, null, null, null, null, null))) {
                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XXh", 'X', OrePrefixes.plate.get(Materials.Gold), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.Gold)});
            }
        }
        tMat = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Rubber, 1L);
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Rubber.Sheet", true)) {
            ItemStack tStack;
            if (null != (tStack = GT_ModHandler.removeRecipe(tMat, tMat, tMat, tMat, tMat, tMat, null, null, null))) {
                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{aTextIron2, aTextIron2, 'X', OrePrefixes.plate.get(Materials.Rubber)});
            }
        }
        GT_ModHandler.removeRecipeByOutput(ItemList.Bottle_Empty.get(1L));
        GT_ModHandler.removeRecipeByOutput(ItemList.IC2_Spray_WeedEx.get(1L));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reBattery", 1L));
        GT_ModHandler.removeRecipeByOutput(new ItemStack(Blocks.tnt));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("dynamite", 1L));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("industrialTnt", 1L));

        ItemStack tStack = GT_ModHandler.removeRecipe(new ItemStack(Blocks.planks, 1, 0), null, null, new ItemStack(Blocks.planks, 1, 0));
        if (tStack != null) {
            GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, tStack), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"s", "P", "P", 'P', OrePrefixes.plank.get(Materials.Wood)});
            GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize / 2 : tStack.stackSize, tStack), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"P", "P", 'P', OrePrefixes.plank.get(Materials.Wood)});
        }
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.wooden_pressure_plate, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PP", 'P', OrePrefixes.plank.get(Materials.Wood)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.stone_button, 2, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"S", "S", 'S', OrePrefixes.stone});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.stone_pressure_plate, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"SS", 'S', OrePrefixes.stone});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.stone_button, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.stone});

        GT_Log.out.println("GT_Mod: Adding Vanilla Convenience Recipes.");

        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.stonebrick, 1, 3), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"f", "X", 'X', new ItemStack(Blocks.double_stone_slab, 1, 8)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.gravel, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(Blocks.cobblestone, 1, 0)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.sand, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(Blocks.gravel, 1, 0)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.cobblestone, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(Blocks.stone, 1, 0)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.stonebrick, 1, 2), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(Blocks.stonebrick, 1, 0)});

        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.double_stone_slab, 1, 8), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.double_stone_slab, 1, 0)});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.double_stone_slab, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.double_stone_slab, 1, 8)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.double_stone_slab, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.stone_slab, 1, 0)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.cobblestone, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.stone_slab, 1, 3)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.brick_block, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.stone_slab, 1, 4)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.stonebrick, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.stone_slab, 1, 5)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.nether_brick, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.stone_slab, 1, 6)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.quartz_block, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.stone_slab, 1, 7)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.double_stone_slab, 1, 8), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.stone_slab, 1, 8)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.planks, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.wooden_slab, 1, 0)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.planks, 1, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.wooden_slab, 1, 1)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.planks, 1, 2), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.wooden_slab, 1, 2)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.planks, 1, 3), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.wooden_slab, 1, 3)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.planks, 1, 4), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.wooden_slab, 1, 4)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.planks, 1, 5), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.wooden_slab, 1, 5)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.planks, 1, 6), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.wooden_slab, 1, 6)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.planks, 1, 7), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.wooden_slab, 1, 7)});

        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.stick, 2, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"s", "X", 'X', new ItemStack(Blocks.deadbush, 1, 32767)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.stick, 2, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"s", "X", 'X', new ItemStack(Blocks.tallgrass, 1, 0)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.stick, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"s", "X", 'X', OrePrefixes.treeSapling});

        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.comparator, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" T ", "TQT", "SSS", 'Q', OreDictNames.craftingQuartz, 'S', OrePrefixes.stoneSmooth, 'T', OreDictNames.craftingRedstoneTorch});

        GT_Log.out.println("GT_Mod: Adding Tool Recipes.");
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.minecart, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{" h ", "PwP", "WPW", 'P', OrePrefixes.plate.get(Materials.Iron), 'W', ItemList.Component_Minecart_Wheels_Iron});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.minecart, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" h ", "PwP", "WPW", 'P', OrePrefixes.plate.get(Materials.Steel), 'W', ItemList.Component_Minecart_Wheels_Steel});

        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.chest_minecart, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.minecart, 1), 'X', OreDictNames.craftingChest});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.furnace_minecart, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.minecart, 1), 'X', OreDictNames.craftingFurnace});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.hopper_minecart, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.minecart, 1), 'X', new ItemStack(Blocks.hopper, 1, 32767)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.tnt_minecart, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.minecart, 1), 'X', new ItemStack(Blocks.tnt, 1, 32767)});

        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.chainmail_helmet, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"RRR", "RhR", 'R', OrePrefixes.ring.get(Materials.Steel)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.chainmail_chestplate, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"RhR", "RRR", "RRR", 'R', OrePrefixes.ring.get(Materials.Steel)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.chainmail_leggings, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"RRR", "RhR", "R R", 'R', OrePrefixes.ring.get(Materials.Steel)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.chainmail_boots, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"R R", "RhR", 'R', OrePrefixes.ring.get(Materials.Steel)});

        GT_Log.out.println("GT_Mod: Adding Wool and Color releated Recipes.");
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeOrange});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 2), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeMagenta});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 3), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeLightBlue});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 4), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeYellow});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 5), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeLime});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyePink});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 7), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeGray});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 8), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeLightGray});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 9), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeCyan});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 10), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyePurple});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 11), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeBlue});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 12), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeBrown});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 13), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeGreen});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 14), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeRed});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.wool, 1, 15), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.wool, 1, 0), Dyes.dyeBlack});

        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.stained_glass, 8, 0), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GGG", "GDG", "GGG", 'G', new ItemStack(Blocks.glass, 1), 'D', Dyes.dyeWhite});

        GT_Log.out.println("GT_Mod: Putting a Potato on a Stick.");
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Packaged_PotatoChips.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.foil.get(Materials.Aluminium), ItemList.Food_PotatoChips});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Packaged_ChiliChips.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.foil.get(Materials.Aluminium), ItemList.Food_ChiliChips});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Packaged_Fries.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.plateDouble.get(Materials.Paper), ItemList.Food_Fries});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Chum_On_Stick.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.stick.get(Materials.Wood), ItemList.Food_Chum});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Potato_On_Stick.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.stick.get(Materials.Wood), ItemList.Food_Raw_Potato});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Potato_On_Stick_Roasted.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.stick.get(Materials.Wood), ItemList.Food_Baked_Potato});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.bucket.get(Materials.Water), OrePrefixes.dust.get(Materials.Wheat)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough_Sugar.get(2L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", OrePrefixes.dust.get(Materials.Sugar)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough_Chocolate.get(2L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", OrePrefixes.dust.get(Materials.Cocoa)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough_Chocolate.get(2L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", OrePrefixes.dust.get(Materials.Chocolate)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Flat_Dough.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", ToolDictNames.craftingToolRollingPin});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Bun.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough"});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Bread.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", "foodDough"});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Baguette.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", "foodDough", "foodDough"});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Cake.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Dough_Sugar, ItemList.Food_Dough_Sugar, ItemList.Food_Dough_Sugar, ItemList.Food_Dough_Sugar});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_ChiliChips.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_PotatoChips, OrePrefixes.dust.get(Materials.Chili)});

        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Buns.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Breads.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Baguettes.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Bun.get(2L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Bread.get(2L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Baguette.get(2L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes});

        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Veggie.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Cheese.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Meat.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, OrePrefixes.dust.get(Materials.MeatCooked)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Chum.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, ItemList.Food_Chum});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Veggie.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Cheese.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Meat.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, OrePrefixes.dust.get(Materials.MeatCooked)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Chum.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, ItemList.Food_Chum});

        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Veggie.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Cheese.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Bacon.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, new ItemStack(Items.cooked_porkchop, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Steak.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, new ItemStack(Items.cooked_beef, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Veggie.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Cheese.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Bacon.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, new ItemStack(Items.cooked_porkchop, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Steak.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, new ItemStack(Items.cooked_beef, 1)});

        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Veggie.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Cheese.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Bacon.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, new ItemStack(Items.cooked_porkchop, 1), new ItemStack(Items.cooked_porkchop, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Steak.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, new ItemStack(Items.cooked_beef, 1), new ItemStack(Items.cooked_beef, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Veggie.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Cheese.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Bacon.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, new ItemStack(Items.cooked_porkchop, 1), new ItemStack(Items.cooked_porkchop, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Steak.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, new ItemStack(Items.cooked_beef, 1), new ItemStack(Items.cooked_beef, 1)});

        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Pizza_Veggie.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Flat_Dough, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Pizza_Cheese.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Flat_Dough, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Pizza_Meat.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Flat_Dough, OrePrefixes.dust.get(Materials.MeatCooked)});

        GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Cheese.get(4L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "foodCheese"});
        GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Lemon.get(4L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropLemon"});
        GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Tomato.get(4L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropTomato"});
        GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Onion.get(4L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropOnion"});
        GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Cucumber.get(4L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropCucumber"});
        GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Bun.get(2L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Baked_Bun});
        GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Bread.get(2L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Baked_Bread});
        GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Baguette.get(2L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Baked_Baguette});
        GT_ModHandler.addCraftingRecipe(ItemList.Food_Raw_PotatoChips.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropPotato"});
        GT_ModHandler.addCraftingRecipe(ItemList.Food_Raw_Cookie.get(4L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Dough_Chocolate});

        GT_ModHandler.addCraftingRecipe(ItemList.Food_Raw_Fries.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"k", "X", 'X', "cropPotato"});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.bowl, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"k", "X", 'X', OrePrefixes.plank.get(Materials.Wood)});
        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"k", "X", 'X', OrePrefixes.plate.get(Materials.Rubber)});
        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadArrow, Materials.Flint, 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"f", "X", 'X', new ItemStack(Items.flint, 1, 32767)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.feather, 2), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"P k", 'P', OrePrefixes.plate.get(Materials.Paper)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.arrow, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"  H", " S ", "F  ", 'H', new ItemStack(Items.flint, 1, 32767), 'S', OrePrefixes.stick.get(Materials.Wood), 'F', OreDictNames.craftingFeather});

        GT_ModHandler.removeRecipe(new ItemStack(Blocks.planks), null, new ItemStack(Blocks.planks), null, new ItemStack(Blocks.planks));
        GT_ModHandler.removeRecipeByOutput(ItemList.Food_Baked_Bread.get(1L));
        GT_ModHandler.removeRecipeByOutput(new ItemStack(Items.cookie, 1));
        GT_ModHandler.removeRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1L));
        if (null != GT_Utility.setStack(GT_ModHandler.getRecipeOutput(true, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1L), null, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L)), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "bronzeingotcrafting", true) ? 1L : 2L))) {
            GT_Log.out.println("GT_Mod: Changed Forestrys Bronze Recipe");
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "enchantmenttable", false)) {
            GT_Log.out.println("GT_Mod: Removing the Recipe of the Enchantment Table, to have more Fun at enchanting with the Anvil and Books from Dungeons.");
            GT_ModHandler.removeRecipeByOutput(new ItemStack(Blocks.enchanting_table, 1));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "enderchest", false)) {
            GT_ModHandler.removeRecipeByOutput(new ItemStack(Blocks.ender_chest, 1));
        }
        tStack = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 1L);
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getRecipeOutput(null, new ItemStack(Blocks.sand, 1, 0), null, null, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Apatite, 1L), null, null, new ItemStack(Blocks.sand, 1, 0), null), new Object[]{"S", "A", "S", 'A', OrePrefixes.dust.get(Materials.Apatite), 'S', new ItemStack(Blocks.sand, 1, 32767)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getRecipeOutput(tStack, tStack, tStack, tStack, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Apatite, 1L), tStack, tStack, tStack, tStack), new Object[]{"SSS", "SAS", "SSS", 'A', OrePrefixes.dust.get(Materials.Apatite), 'S', OrePrefixes.dust.get(Materials.Ash)});

        GT_Log.out.println("GT_Mod: Adding Mixed Metal Ingot Recipes.");
        GT_ModHandler.removeRecipeByOutput(ItemList.IC2_Mixed_Metal_Ingot.get(1L));

        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Iron), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Iron), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Iron), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Iron), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Iron), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Iron), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});




        GT_Log.out.println("GT_Mod: Beginning to add regular Crafting Recipes.");
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("scaffold", 4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", " S ", "S S", 'W', OrePrefixes.plank.get(Materials.Wood), 'S', OrePrefixes.stick.get(Materials.Wood)});

        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "HPT", 'H', OrePrefixes.cell.get(Materials.Helium), 'N', OrePrefixes.cell.get(Materials.Nitrogen), 'T', OrePrefixes.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefixes.wireGt01.get(Materials.NiobiumTitanium)});
        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "HPT", 'H', OrePrefixes.cell.get(Materials.Helium), 'N', OrePrefixes.cell.get(Materials.Nitrogen), 'T', OrePrefixes.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefixes.wireGt01.get(Materials.VanadiumGallium)});
        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "NPT", 'N', OrePrefixes.cell.get(Materials.Nitrogen), 'T', OrePrefixes.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefixes.wireGt01.get(Materials.YttriumBariumCuprate)});
        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "NPT", 'N', OrePrefixes.cell.get(Materials.Nitrogen), 'T', OrePrefixes.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefixes.wireGt01.get(Materials.Naquadah)});

        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.stick.get(Materials.Iron), OrePrefixes.dust.get(Materials.Redstone), OrePrefixes.dust.get(Materials.Redstone), OrePrefixes.dust.get(Materials.Redstone), OrePrefixes.dust.get(Materials.Redstone)});
        GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Paper, 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PPk", 'P', OrePrefixes.plate.get(Materials.Paper)});
        GT_ModHandler.addCraftingRecipe(ItemList.Paper_Punch_Card_Empty.get(2L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"Pk", 'P', GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Paper, 1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Small_Gunpowder_Bundle.get(1L), new Object[]{" P ", "PWP", " B ", 'P',  new ItemStack(Items.paper),'W', GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Gunpowder, 1L), 'B', new ItemStack(Items.bowl)});

        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Gold.get(1L), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Gold)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Iron.get(1L), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Iron)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Bronze.get(1L), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Bronze)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Copper.get(1L), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Copper)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Tin.get(1L), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Lead.get(1L), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Lead)});
        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Steel.get(1L), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Steel)});

        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.torch, 2), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"C", "S", 'C', OrePrefixes.dust.get(Materials.Sulfur), 'S', OrePrefixes.stick.get(Materials.Wood)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.torch, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"C", "S", 'C', OrePrefixes.dust.get(Materials.TricalciumPhosphate), 'S', OrePrefixes.stick.get(Materials.Wood)});

        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.piston, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.stoneCobble, 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.ingot.get(Materials.Iron)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.piston, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.stoneCobble, 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.ingot.get(Materials.Bronze)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.piston, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.stoneCobble, 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.ingot.get(Materials.Aluminium)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.piston, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.stoneCobble, 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.ingot.get(Materials.Steel)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.piston, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.stoneCobble, 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.ingot.get(Materials.Titanium)});

        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("reactorVent", 1L, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"AIA", "I I", "AIA", 'I', new ItemStack(Blocks.iron_bars, 1), 'A', OrePrefixes.plate.get(Materials.Aluminium)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("reactorPlatingExplosive", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{GT_ModHandler.getIC2Item("reactorPlating", 1L), OrePrefixes.plate.get(Materials.Lead)});
        if (!Materials.Steel.mBlastFurnaceRequired) {
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Steel, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Coal), OrePrefixes.dust.get(Materials.Coal)});
        }
        if (GT_Mod.gregtechproxy.mNerfDustCrafting) {
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Electrum, 6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Gold)});
            GT_ModHandler.removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Brass, 1L));
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Brass, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Zinc)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Brass, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Zinc)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bronze, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Tin)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Bronze, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tin)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Invar, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Nickel)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Cupronickel, 6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Copper)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Nichrome, 15L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Chrome)});
        } else {
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Electrum, 2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Gold)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Brass, 4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Zinc)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Brass, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Zinc)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bronze, 4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Tin)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bronze, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tin)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Invar, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Nickel)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cupronickel, 2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Copper)});
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nichrome, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Chrome)});
        }
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.RoseGold, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Gold), OrePrefixes.dust.get(Materials.Gold), OrePrefixes.dust.get(Materials.Gold), OrePrefixes.dust.get(Materials.Gold), OrePrefixes.dust.get(Materials.Copper)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.SterlingSilver, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Copper)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.BlackBronze, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Gold), OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.BismuthBronze, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Bismuth), OrePrefixes.dust.get(Materials.Zinc), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.BlackSteel, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.BlackBronze), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.RedSteel, 8L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.SterlingSilver), OrePrefixes.dust.get(Materials.BismuthBronze), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.BlueSteel, 8L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.RoseGold), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel)});

        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ultimet, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Chrome), OrePrefixes.dust.get(Materials.Chrome), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Molybdenum)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CobaltBrass, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Aluminium), OrePrefixes.dust.get(Materials.Cobalt)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.StainlessSteel, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Manganese), OrePrefixes.dust.get(Materials.Chrome)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.YttriumBariumCuprate, 6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Yttrium), OrePrefixes.dust.get(Materials.Barium), OrePrefixes.dust.get(Materials.Barium), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper), OrePrefixes.dust.get(Materials.Copper)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Kanthal, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Aluminium), OrePrefixes.dust.get(Materials.Chrome)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.FerriteMixture, 6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Zinc), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.BorosilicateGlass, 8L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Boron), OrePrefixes.dust.get(Materials.Glass), OrePrefixes.dust.get(Materials.Glass), OrePrefixes.dust.get(Materials.Glass), OrePrefixes.dust.get(Materials.Glass), OrePrefixes.dust.get(Materials.Glass), OrePrefixes.dust.get(Materials.Glass), OrePrefixes.dust.get(Materials.Glass)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.EpoxidFiberReinforced, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Epoxid), ItemList.Circuit_Parts_GlassFiber.get(1, new Object[0])});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.EpoxidFiberReinforced, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Epoxid), GT_ModHandler.getIC2Item("carbonFiber", 1)});

        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ultimet, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dustTiny.get(Materials.Cobalt), OrePrefixes.dustTiny.get(Materials.Cobalt), OrePrefixes.dustTiny.get(Materials.Cobalt), OrePrefixes.dustTiny.get(Materials.Cobalt), OrePrefixes.dustTiny.get(Materials.Cobalt), OrePrefixes.dustTiny.get(Materials.Chrome), OrePrefixes.dustTiny.get(Materials.Chrome), OrePrefixes.dustTiny.get(Materials.Nickel), OrePrefixes.dustTiny.get(Materials.Molybdenum)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CobaltBrass, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Aluminium), OrePrefixes.dustTiny.get(Materials.Cobalt)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.StainlessSteel, 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Nickel), OrePrefixes.dustTiny.get(Materials.Manganese), OrePrefixes.dustTiny.get(Materials.Chrome)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.YttriumBariumCuprate, 6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dustTiny.get(Materials.Yttrium), OrePrefixes.dustTiny.get(Materials.Barium), OrePrefixes.dustTiny.get(Materials.Barium), OrePrefixes.dustTiny.get(Materials.Copper), OrePrefixes.dustTiny.get(Materials.Copper), OrePrefixes.dustTiny.get(Materials.Copper)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Kanthal, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Aluminium), OrePrefixes.dustTiny.get(Materials.Chrome)});

        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.VanadiumSteel, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Vanadium), OrePrefixes.dust.get(Materials.Chrome)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.HSSG, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.TungstenSteel), OrePrefixes.dust.get(Materials.TungstenSteel), OrePrefixes.dust.get(Materials.TungstenSteel), OrePrefixes.dust.get(Materials.TungstenSteel), OrePrefixes.dust.get(Materials.TungstenSteel), OrePrefixes.dust.get(Materials.Chrome), OrePrefixes.dust.get(Materials.Molybdenum), OrePrefixes.dust.get(Materials.Molybdenum), OrePrefixes.dust.get(Materials.Vanadium)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.HSSE, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Manganese), OrePrefixes.dust.get(Materials.Silicon)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.HSSS, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.Iridium), OrePrefixes.dust.get(Materials.Iridium), OrePrefixes.dust.get(Materials.Osmium)});


        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.IronWood, 2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.LiveRoot), OrePrefixes.dustTiny.get(Materials.Gold)});

        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.gunpowder, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Coal), 		OrePrefixes.dust.get(Materials.Coal), 		OrePrefixes.dust.get(Materials.Coal), 		OrePrefixes.dust.get(Materials.Sulfur), OrePrefixes.dust.get(Materials.Saltpeter), OrePrefixes.dust.get(Materials.Saltpeter)});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.gunpowder, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Charcoal), 	OrePrefixes.dust.get(Materials.Charcoal), 	OrePrefixes.dust.get(Materials.Charcoal), 	OrePrefixes.dust.get(Materials.Sulfur), OrePrefixes.dust.get(Materials.Saltpeter), OrePrefixes.dust.get(Materials.Saltpeter)});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.gunpowder, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Carbon), 	OrePrefixes.dust.get(Materials.Carbon), 	OrePrefixes.dust.get(Materials.Carbon), 	OrePrefixes.dust.get(Materials.Sulfur), OrePrefixes.dust.get(Materials.Saltpeter), OrePrefixes.dust.get(Materials.Saltpeter)});

        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.IndiumGalliumPhosphide, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Indium), OrePrefixes.dust.get(Materials.Gallium), OrePrefixes.dust.get(Materials.Phosphorus)});

        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Potassium), OrePrefixes.cell.get(Materials.Nitrogen), OrePrefixes.cell.get(Materials.Oxygen), OrePrefixes.cell.get(Materials.Oxygen), OrePrefixes.cell.get(Materials.Oxygen)});


        if (Loader.isModLoaded("ImmersiveEngineering")) {
            GT_ModHandler.removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 4));
            GT_ModHandler.removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4));
            GT_ModHandler.removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 4));
        }

        if (Loader.isModLoaded("NotEnoughItems")) {
            codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("reactorUraniumSimple", 1L, 1));
            codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("reactorUraniumDual", 1L, 1));
            codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("reactorUraniumQuad", 1L, 1));
            codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("reactorMOXSimple", 1L, 1));
            codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("reactorMOXDual", 1L, 1));
            codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("reactorMOXQuad", 1L, 1));
        }


        GT_ModHandler.removeRecipe(tStack = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1L), tStack, tStack, tStack, new ItemStack(Items.coal, 1, 0), tStack, tStack, tStack, tStack);
        GT_ModHandler.removeRecipe(tStack = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1L), tStack, tStack, tStack, new ItemStack(Items.coal, 1, 1), tStack, tStack, tStack, tStack);
        GT_ModHandler.removeRecipe(null, tStack = new ItemStack(Items.coal, 1), null, tStack, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iron, 1L), tStack, null, tStack, null);

        GT_ModHandler.removeFurnaceSmelting(new ItemStack(Blocks.hopper));

        GT_Log.out.println("GT_Mod: Applying harder Recipes for several Blocks.");
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "blockbreaker", false)) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.removeRecipe(new ItemStack(Blocks.cobblestone, 1), new ItemStack(Items.iron_pickaxe, 1), new ItemStack(Blocks.cobblestone, 1), new ItemStack(Blocks.cobblestone, 1), new ItemStack(Blocks.piston, 1), new ItemStack(Blocks.cobblestone, 1), new ItemStack(Blocks.cobblestone, 1), new ItemStack(Items.redstone, 1), new ItemStack(Blocks.cobblestone, 1)), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RGR", "RPR", "RCR", 'G', OreDictNames.craftingGrinder, 'C', OrePrefixes.circuit.get(Materials.Advanced), 'R', OrePrefixes.plate.get(Materials.Steel), 'P', OreDictNames.craftingPiston});
        }

        if (GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Diamond, 1L) != null) {
            tStack = GT_ModHandler.getRecipeOutput(GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Iron, 1L), new ItemStack(Items.redstone, 1), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Gold, 1L), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Gold, 1L), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Diamond, 1L), new ItemStack(Items.diamond_pickaxe, 1), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Diamond, 1L));
            if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "quarry", true)) {
                GT_ModHandler.removeRecipeByOutput(tStack);
                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"ICI", "GIG", "DPD", 'C', OrePrefixes.circuit.get(Materials.Advanced), 'D', OrePrefixes.gear.get(Materials.Diamond), 'G', OrePrefixes.gear.get(Materials.Gold), 'I', OrePrefixes.gear.get(Materials.Steel), 'P', GT_ModHandler.getIC2Item("diamondDrill", 1L, 32767)});
            }
            if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "quarry", false)) {
                GT_ModHandler.removeRecipeByOutput(tStack);
            }
        }
        if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "sugarpaper", true))) {
            GT_ModHandler.removeRecipeByOutput(new ItemStack(Items.paper));
            GT_ModHandler.removeRecipeByOutput(new ItemStack(Items.sugar));
            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Paper, 2), new Object[]{"SSS", " m ", 'S', new ItemStack(Items.reeds)});
            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sugar, 1), new Object[]{"Sm ", 'S', new ItemStack(Items.reeds)});
//            ItemStack brick = new ItemStack(new ItemStack(Blocks.stone_slab).getItem().setContainerItem(new ItemStack(Blocks.stone_slab).getItem()));         
//            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.paper, Materials.Empty, 2), new Object[]{" C ", "SSS", " C ", 'S', GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Paper, 1), 'C', brick});
            GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.paper, Materials.Empty, 2), new Object[]{" C ", "SSS", " C ", 'S', GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Paper, 1), 'C', new ItemStack(Blocks.stone_slab)});
//            GameRegistry.addRecipe(GT_OreDictUnificator.get(OrePrefixes.paper, Materials.Empty, 2), " C ", "SSS", " C ", 'S', GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Paper, 1), 'C', brick);
        }

        long bits = GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE;
        long bitr = GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE;
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularBasicHelmet.getWildcard(1), bits, new Object[]{"AAA", "B B", 'A', new ItemStack(Items.leather, 1, 32767), 'B', OrePrefixes.ring.get(Materials.Iron)});
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularBasicChestplate.getWildcard(1), bits, new Object[]{"A A", "BAB", "AAA", 'A', new ItemStack(Items.leather, 1, 32767), 'B', OrePrefixes.ring.get(Materials.Iron)});
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularBasicLeggings.getWildcard(1), bits, new Object[]{"BAB", "A A", "A A", 'A', new ItemStack(Items.leather, 1, 32767), 'B', OrePrefixes.ring.get(Materials.Iron)});
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularBasicBoots.getWildcard(1), bits, new Object[]{"A A", "B B", "A A", 'A', new ItemStack(Items.leather, 1, 32767), 'B', OrePrefixes.ring.get(Materials.Iron)});
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric1Helmet.getWildcard(1), bits, new Object[]{"ACA", "B B", 'A', OrePrefixes.stick.get(Materials.Aluminium), 'B', OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.battery.get(Materials.Advanced)});
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric1Chestplate.getWildcard(1), bits, new Object[]{"A A", "BCB", "AAA", 'A', OrePrefixes.stick.get(Materials.Aluminium), 'B', OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.battery.get(Materials.Advanced)});
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric1Leggings.getWildcard(1), bits, new Object[]{"BCB", "A A", "A A", 'A', OrePrefixes.stick.get(Materials.Aluminium), 'B', OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.battery.get(Materials.Advanced)});
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric1Boots.getWildcard(1), bits, new Object[]{"A A", "BCB", "A A", 'A', OrePrefixes.stick.get(Materials.Aluminium), 'B', OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.battery.get(Materials.Advanced)});
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric2Helmet.getWildcard(1), bits, new Object[]{"ACA", "B B", 'A', OrePrefixes.stick.get(Materials.TungstenSteel), 'B', OrePrefixes.plateAlloy.get(Materials.Carbon), 'C', OrePrefixes.battery.get(Materials.Master)});
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric2Chestplate.getWildcard(1), bits, new Object[]{"A A", "BCB", "AAA", 'A', OrePrefixes.stick.get(Materials.TungstenSteel), 'B', OrePrefixes.plateAlloy.get(Materials.Carbon), 'C', OrePrefixes.battery.get(Materials.Master)});
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric2Leggings.getWildcard(1), bits, new Object[]{"BCB", "A A", "A A", 'A', OrePrefixes.stick.get(Materials.TungstenSteel), 'B', OrePrefixes.plateAlloy.get(Materials.Carbon), 'C', OrePrefixes.battery.get(Materials.Master)});
//        GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric2Boots.getWildcard(1), bits, new Object[]{"A A", "BCB", "A A", 'A', OrePrefixes.stick.get(Materials.TungstenSteel), 'B', OrePrefixes.plateAlloy.get(Materials.Carbon), 'C', OrePrefixes.battery.get(Materials.Master)});

    	GT_ModHandler.addShapelessCraftingRecipe(Materials.Fireclay.getDust(2), new Object[]{Materials.Brick.getDust(1), Materials.Clay.getDust(1)});

        GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0), new FluidStack(FluidRegistry.getFluid("molten.borosilicateglass"), 144), ItemList.VOLUMETRIC_FLASK.get(1), 44, 24);

        GT_ModHandler.addCraftingRecipe(ItemList.Machine_Well.get(1L), bits, new Object[]{"SPS","WQW","BVB",'S',GT_OreDictUnificator.get(OrePrefixes.gearGt,Materials.Wood,1),'P', new ItemStack(Items.stick,1),'W', OrePrefixes.plank.get(Materials.Wood), 'Q', new ItemStack(Items.string,1), 'B', OrePrefixes.stoneCobble,'V',new ItemStack(Items.bucket,1)});

        GT_ModHandler.addCraftingRecipe(ItemList.PrimitiveFluidFilterItem.get(1L), bits, new Object[]{"PSP","WOW","PSP", 'P', OrePrefixes.plate.get(Materials.Paper), 'S', new ItemStack(Items.string,1), 'W', new ItemStack(Blocks.wool, 1, GT_Values.W), 'O', OrePrefixes.pipeMedium.get(Materials.Wood) });
        GT_ModHandler.addCraftingRecipe(ItemList.Casing_Filter_ULV.get(1L), bits | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RFR","FPF","RFR", 'R', OrePrefixes.ring.get(Materials.Tin), 'F', ItemList.PrimitiveFluidFilterItem.get(1L), 'P', OrePrefixes.pipeMedium.get(Materials.Wood)});
        GT_ModHandler.addCraftingRecipe(ItemList.Tool_DataReader_MV.get(1L), bitr, new Object[]{"PBP","CSC","PWP", 'P', OrePrefixes.plate.get(Materials.Aluminium), 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'B', ItemList.Battery_RE_MV_Lithium,	'S', ItemList.Cover_Screen, 'C', OrePrefixes.circuit.get(Materials.Good)});
        GT_ModHandler.addCraftingRecipe(ItemList.Tool_DataReader_EV.get(1L), bitr, new Object[]{"PBP","CSC","PWP", 'P', OrePrefixes.plate.get(Materials.Titanium), 'W', OrePrefixes.cableGt01.get(Materials.Aluminium), 'B', OrePrefixes.battery.get(Materials.Master),	'S', ItemList.Cover_Screen, 'C', OrePrefixes.circuit.get(Materials.Data)});
    }
}
