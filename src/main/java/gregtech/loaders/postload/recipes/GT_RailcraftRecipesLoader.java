package gregtech.loaders.postload.recipes;

import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GT_RailcraftRecipesLoader implements Runnable {

    private final static String aTextIron1 = "X X";
    private final static String aTextIron2 = "XXX";
    private final static String aTextRailcraft = "Railcraft";
    private final static String aTextMachineBeta = "machine.beta";
    private final static String aTextMachineAlpha = "machine.alpha";

    @Override
    public void run() {
        
	GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "part.circuit", 1L, 0));
	GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "part.circuit", 1L, 1));
	GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "part.circuit", 1L, 2));
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4L),ItemList.Circuit_Board_Coated.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 3L, new Object[0])},Materials.SolderingAlloy.getMolten(144L), GT_ModHandler.getModItem("Railcraft", "part.circuit", 1L, 0), 120, 5);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L),ItemList.Circuit_Board_Coated.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 3L, new Object[0])},Materials.SolderingAlloy.getMolten(144L), GT_ModHandler.getModItem("Railcraft", "part.circuit", 1L, 1), 120, 5);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 4L),ItemList.Circuit_Board_Coated.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 3L, new Object[0])},Materials.SolderingAlloy.getMolten(144L), GT_ModHandler.getModItem("Railcraft", "part.circuit", 1L, 2), 120, 5);

        GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("Railcraft", "machine.beta", 1L, 0), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Iron, 4L)}, new int[]{10000}, 300, 2);
        GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("Railcraft", "machine.beta", 1L, 13), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Steel, 4L)}, new int[]{10000}, 300, 2);
        GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("Railcraft", "machine.beta", 1L, 1), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Iron, 4L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Glass, 3L)}, new int[]{10000, 10000}, 300, 2);
        GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("Railcraft", "machine.beta", 1L, 14), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Steel, 4L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Glass, 3L)}, new int[]{10000, 10000}, 300, 2);
    
        GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem("Railcraft", "post.metal.light.blue", 8L), new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefixes.ingot.get(Materials.Aluminium).toString()});
        GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem("Railcraft", "post.metal.purple", 64L), new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefixes.ingot.get(Materials.Titanium).toString()});
        GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem("Railcraft", "post.metal.black", 64L), new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefixes.ingot.get(Materials.Tungsten).toString()});

        GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem("Railcraft", "post.metal.light.blue", 8L), new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Aluminium).toString()});
        GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem("Railcraft", "post.metal.purple", 64L), new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Titanium).toString()});
        GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem("Railcraft", "post.metal.black", 64L), new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Tungsten).toString()});


        GT_Log.out.println("GT_Mod: Adding Rolling Machine Recipes.");
        GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Standard.get(4L), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Aluminium).toString()});
        GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Standard.get(32L), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Titanium).toString()});
        GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Standard.get(32L), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Tungsten).toString()});

        GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Reinforced.get(32L), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.TungstenSteel).toString()});

        GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(2L), new Object[]{"  X", " X ", "X  ", 'X', OrePrefixes.ingot.get(Materials.Aluminium).toString()});
        GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(16L), new Object[]{"  X", " X ", "X  ", 'X', OrePrefixes.ingot.get(Materials.Titanium).toString()});
        GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(16L), new Object[]{"  X", " X ", "X  ", 'X', OrePrefixes.ingot.get(Materials.Tungsten).toString()});
        GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(48L), new Object[]{"  X", " X ", "X  ", 'X', OrePrefixes.ingot.get(Materials.TungstenSteel).toString()});


        GT_Log.out.println("GT_Mod: Replacing Railcraft Recipes with slightly more OreDicted Variants");
        long tBitMask = GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES_IF_SAME_NBT | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_NATIVE_RECIPES | GT_ModHandler.RecipeBits.ONLY_ADD_IF_THERE_IS_ANOTHER_RECIPE_FOR_IT;
        char tHammer = ' ';
        char tFile = ' ';
        char tWrench = ' ';
        OrePrefixes tIngot = OrePrefixes.ingot;
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "railcraft_stuff_use_tools", true)) {
            tHammer = 'h';
            tFile = 'f';
            tWrench = 'w';
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "railcraft_stuff_use_plates", true)) {
            tIngot = OrePrefixes.plate;
        }
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 2L, 3), tBitMask | GT_ModHandler.RecipeBits.MIRRORED, new Object[]{tHammer + "" + tFile, "XX", "XX", 'X', tIngot.get(Materials.Tin)});

        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1L, 0), tBitMask, new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', OrePrefixes.nugget.get(Materials.Gold), 'G', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1L, 3)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1L, 1), tBitMask, new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', tIngot.get(Materials.Iron), 'G', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1L, 3)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1L, 2), tBitMask, new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', tIngot.get(Materials.Steel), 'G', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1L, 3)});

        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 0), tBitMask, new Object[]{tWrench + "PP", tHammer + "PP", 'P', OrePrefixes.plate.get(Materials.Iron)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 1), tBitMask, new Object[]{"GPG", "PGP", "GPG", 'P', OrePrefixes.plate.get(Materials.Iron), 'G', new ItemStack(Blocks.glass_pane, 1, 32767)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 2), tBitMask, new Object[]{"BPB", "PLP", "BPB", 'P', OrePrefixes.plate.get(Materials.Iron), 'B', new ItemStack(Blocks.iron_bars, 1, 32767), 'L', new ItemStack(Blocks.lever, 1, 32767)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 3), tBitMask, new Object[]{tWrench + "P", tHammer + "P", 'P', OrePrefixes.plate.get(Materials.Iron)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 4), tBitMask, new Object[]{tWrench + "P", tHammer + "P", 'P', OrePrefixes.plate.get(Materials.Steel)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 5), tBitMask, new Object[]{"BBB", "BFB", "BOB", 'B', OrePrefixes.ingot.get(Materials.Brick), 'F', new ItemStack(Items.fire_charge, 1, 32767), 'O', OreDictNames.craftingFurnace});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 6), tBitMask, new Object[]{"PUP", "BFB", "POP", 'P', OrePrefixes.plate.get(Materials.Steel), 'B', new ItemStack(Blocks.iron_bars, 1, 32767), 'F', new ItemStack(Items.fire_charge, 1, 32767), 'U', OrePrefixes.bucket.get(Materials.Empty), 'O', OreDictNames.craftingFurnace});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 7), tBitMask | GT_ModHandler.RecipeBits.MIRRORED, new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefixes.nugget.get(Materials.Gold), 'O', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1L, 0), 'G', new ItemStack(Blocks.glass, 1, 32767), 'T', OreDictNames.craftingPiston});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 8), tBitMask | GT_ModHandler.RecipeBits.MIRRORED, new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefixes.plate.get(Materials.Iron), 'O', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1L, 1), 'G', new ItemStack(Blocks.glass, 1, 32767), 'T', OreDictNames.craftingPiston});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 9), tBitMask | GT_ModHandler.RecipeBits.MIRRORED, new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefixes.plate.get(Materials.Steel), 'O', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1L, 2), 'G', new ItemStack(Blocks.glass, 1, 32767), 'T', OreDictNames.craftingPiston});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 10), tBitMask, new Object[]{" E ", " O ", "OIO", 'I', tIngot.get(Materials.Gold), 'E', OrePrefixes.gem.get(Materials.EnderPearl), 'O', OrePrefixes.stone.get(Materials.Obsidian)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 11), tBitMask, new Object[]{"OOO", "OEO", "OOO", 'E', OrePrefixes.gem.get(Materials.EnderPearl), 'O', OrePrefixes.stone.get(Materials.Obsidian)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 12), tBitMask, new Object[]{"GPG", "PAP", "GPG", 'P', OreDictNames.craftingPiston, 'A', OreDictNames.craftingAnvil, 'G', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1L, 2)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 13), tBitMask, new Object[]{tWrench + "PP", tHammer + "PP", 'P', OrePrefixes.plate.get(Materials.Steel)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 14), tBitMask, new Object[]{"GPG", "PGP", "GPG", 'P', OrePrefixes.plate.get(Materials.Steel), 'G', new ItemStack(Blocks.glass_pane, 1, 32767)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 15), tBitMask, new Object[]{"BPB", "PLP", "BPB", 'P', OrePrefixes.plate.get(Materials.Steel), 'B', new ItemStack(Blocks.iron_bars, 1, 32767), 'L', new ItemStack(Blocks.lever, 1, 32767)});

        GT_ModHandler.addCraftingRecipe(ItemList.RC_ShuntingWireFrame.get(6L), tBitMask, new Object[]{"PPP", "R" + tWrench + "R", "RRR", 'P', OrePrefixes.plate.get(Materials.Iron), 'R', ItemList.RC_Rebar.get(1L)});

        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1L, 0), tBitMask, new Object[]{"IOI", "GEG", "IOI", 'I', tIngot.get(Materials.Gold), 'G', OrePrefixes.gem.get(Materials.Diamond), 'E', OrePrefixes.gem.get(Materials.EnderPearl), 'O', OrePrefixes.stone.get(Materials.Obsidian)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 3L, 1), tBitMask, new Object[]{"BPB", "P" + tWrench + "P", "BPB", 'P', OrePrefixes.plate.get(Materials.Steel), 'B', OrePrefixes.block.get(Materials.Steel)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1L, 2), tBitMask, new Object[]{"IOI", "GEG", "IOI", 'I', tIngot.get(Materials.Gold), 'G', OrePrefixes.gem.get(Materials.Emerald), 'E', OrePrefixes.gem.get(Materials.EnderPearl), 'O', OrePrefixes.stone.get(Materials.Obsidian)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4L, 3), tBitMask, new Object[]{"PPP", "PFP", "PPP", 'P', OrePrefixes.plate.get(Materials.Steel), 'F', OreDictNames.craftingFurnace});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1L, 5), tBitMask, new Object[]{" N ", "RCR", 'R', OrePrefixes.dust.get(Materials.Redstone), 'N', OrePrefixes.stone.get(Materials.Netherrack), 'C', new ItemStack(Items.cauldron, 1, 0)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1L, 6), tBitMask, new Object[]{"SGS", "EDE", "SGS", 'E', OrePrefixes.gem.get(Materials.Emerald), 'S', OrePrefixes.plate.get(Materials.Steel), 'G', new ItemStack(Blocks.glass_pane, 1, 32767), 'D', new ItemStack(Blocks.dispenser, 1, 32767)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1L, 8), tBitMask, new Object[]{"IPI", "PCP", "IPI", 'P', OreDictNames.craftingPiston, 'I', tIngot.get(Materials.Iron), 'C', new ItemStack(Blocks.crafting_table, 1, 32767)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1L, 9), tBitMask, new Object[]{" I ", " T ", " D ", 'I', new ItemStack(Blocks.iron_bars, 1, 32767), 'T', GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 4), 'D', new ItemStack(Blocks.dispenser, 1, 32767)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1L, 10), tBitMask, new Object[]{" I ", "RTR", " D ", 'I', new ItemStack(Blocks.iron_bars, 1, 32767), 'T', GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 4), 'D', new ItemStack(Blocks.dispenser, 1, 32767), 'R', OrePrefixes.dust.get(Materials.Redstone)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1L, 10), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RTR", 'T', GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1L, 9), 'R', OrePrefixes.dust.get(Materials.Redstone)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1L, 11), tBitMask, new Object[]{"PCP", "CSC", "PCP", 'P', OrePrefixes.plank.get(Materials.Wood), 'S', OrePrefixes.plate.get(Materials.Steel), 'C', new ItemStack(Items.golden_carrot, 1, 0)});
        if (GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "DisableRCBlastFurnace", false)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4L, 12));
        }
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1L, 13), tBitMask, new Object[]{"TSB", "SCS", "PSP", 'P', OreDictNames.craftingPiston, 'S', OrePrefixes.plate.get(Materials.Steel), 'B', OreDictNames.craftingBook, 'C', new ItemStack(Blocks.crafting_table, 1, 32767), 'T', new ItemStack(Items.diamond_pickaxe, 1, 0)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 6L, 14), tBitMask, new Object[]{"PPP", "ISI", "PPP", 'P', OrePrefixes.plank.get(Materials.Wood), 'I', tIngot.get(Materials.Iron), 'S', "slimeball"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4L, 15), tBitMask, new Object[]{"PDP", "DBD", "PDP", 'P', OreDictNames.craftingPiston, 'B', OrePrefixes.block.get(Materials.Steel), 'D', OrePrefixes.gem.get(Materials.Diamond)});

        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "machine.epsilon", 1L, 0), tBitMask, new Object[]{"PWP", "WWW", "PWP", 'P', OrePrefixes.plate.get(Materials.Iron), 'W', OrePrefixes.wireGt02.get(Materials.Copper)});

        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "tool.crowbar", 1L, 0), tBitMask, new Object[]{tHammer + "DS", "DSD", "SD" + tFile, 'S', OrePrefixes.ingot.get(Materials.Iron), 'D', Dyes.dyeRed});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "tool.crowbar.reinforced", 1L, 0), tBitMask, new Object[]{tHammer + "DS", "DSD", "SD" + tFile, 'S', OrePrefixes.ingot.get(Materials.Steel), 'D', Dyes.dyeRed});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "tool.whistle.tuner", 1L, 0), tBitMask | GT_ModHandler.RecipeBits.MIRRORED, new Object[]{"S" + tHammer + "S", "SSS", " S" + tFile, 'S', OrePrefixes.nugget.get(Materials.Iron)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.blade", 1L, 0), tBitMask, new Object[]{"S" + tFile, "S ", "S" + tHammer, 'S', tIngot.get(Materials.Steel)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.disk", 1L, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefixes.block.get(Materials.Steel), 'S', GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.blade", 1L, 0)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.rotor", 1L, 0), tBitMask, new Object[]{"SSS", " " + tWrench + " ", 'S', GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.disk", 1L, 0)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "borehead.iron", 1L, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefixes.block.get(Materials.Iron), 'S', tIngot.get(Materials.Steel)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "borehead.steel", 1L, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefixes.block.get(Materials.Steel), 'S', tIngot.get(Materials.Steel)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "borehead.diamond", 1L, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefixes.block.get(Materials.Diamond), 'S', tIngot.get(Materials.Steel)});

        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "cart.loco.steam.solid", 1L, 0), tBitMask, new Object[]{"TTF", "TTF", "BCC", 'C', new ItemStack(Items.minecart, 1), 'T', GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 4), 'F', GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1L, 5), 'B', new ItemStack(Blocks.iron_bars, 1, 32767)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "cart.loco.electric", 1L, 0), tBitMask, new Object[]{"LP" + tWrench, "PEP", "GCG", 'C', new ItemStack(Items.minecart, 1), 'E', GT_ModHandler.getModItem(aTextRailcraft, "machine.epsilon", 1L, 0), 'G', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1L, 2), 'L', new ItemStack(Blocks.redstone_lamp, 1, 32767), 'P', OrePrefixes.plate.get(Materials.Steel)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "cart.bore", 1L, 0), tBitMask, new Object[]{"BCB", "FCF", tHammer + "A" + tWrench, 'C', new ItemStack(Items.minecart, 1), 'A', new ItemStack(Items.chest_minecart, 1), 'F', OreDictNames.craftingFurnace, 'B', OrePrefixes.block.get(Materials.Steel)});

    }
}
