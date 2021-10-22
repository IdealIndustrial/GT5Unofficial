package gregtech.loaders.postload.recipes;

import cpw.mods.fml.common.Loader;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import ic2.core.Ic2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GT_IndustialCraftRecipesLoader implements Runnable {


    private final static String aTextIron2 = "XXX";

    @Override
    public void run() {

        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("carbonFiber", 1L));

        if (GT_Mod.gregtechproxy.mDisableIC2Cables) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("copperCableItem", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("goldCableItem", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("insulatedGoldCableItem", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("insulatedIronCableItem", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("glassFiberCableItem", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("tinCableItem", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("ironCableItem", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("insulatedTinCableItem", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("detectorCableItem", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("splitterCableItem", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("electrolyzer", 1L));

            if (Loader.isModLoaded("NotEnoughItems")) {
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("copperCableItem", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("goldCableItem", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("insulatedGoldCableItem", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("insulatedIronCableItem", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("glassFiberCableItem", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("tinCableItem", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("ironCableItem", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("insulatedTinCableItem", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("detectorCableItem", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("splitterCableItem", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("electrolyzer", 1L));
                codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("cutter", 1L));
            }
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("batBox", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("batBox", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "BBB", "PPP", 'C', OrePrefixes.cableGt01.get(Materials.Tin), 'P', OrePrefixes.plank.get(Materials.Wood), 'B', OrePrefixes.battery.get(Materials.Basic)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("mfeUnit", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mfeUnit", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CEC", "EME", "CEC", 'C', OrePrefixes.cableGt01.get(Materials.Gold), 'E', OrePrefixes.battery.get(Materials.Elite), 'M', GT_ModHandler.getIC2Item("machine", 1L)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("lvTransformer", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("lvTransformer", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "POP", "PCP", 'C', OrePrefixes.cableGt01.get(Materials.Tin), 'O', GT_ModHandler.getIC2Item("coil", 1L), 'P', OrePrefixes.plank.get(Materials.Wood)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("mvTransformer", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mvTransformer", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CMC", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'M', GT_ModHandler.getIC2Item("machine", 1L)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("hvTransformer", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("hvTransformer", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" C ", "IMB", " C ", 'C', OrePrefixes.cableGt01.get(Materials.Gold), 'M', GT_ModHandler.getIC2Item("mvTransformer", 1L), 'I', OrePrefixes.circuit.get(Materials.Basic), 'B', OrePrefixes.battery.get(Materials.Advanced)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("evTransformer", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("evTransformer", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" C ", "IMB", " C ", 'C', OrePrefixes.cableGt01.get(Materials.Aluminium), 'M', GT_ModHandler.getIC2Item("hvTransformer", 1L), 'I', OrePrefixes.circuit.get(Materials.Advanced), 'B', OrePrefixes.battery.get(Materials.Master)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("cesuUnit", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("cesuUnit", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "BBB", "PPP", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'P', OrePrefixes.plate.get(Materials.Bronze), 'B', OrePrefixes.battery.get(Materials.Advanced)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("luminator", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("teleporter", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("teleporter", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GFG", "CMC", "GDG", 'C', OrePrefixes.cableGt01.get(Materials.Platinum), 'G', OrePrefixes.circuit.get(Materials.Advanced), 'D', OrePrefixes.gem.get(Materials.Diamond), 'M', GT_ModHandler.getIC2Item("machine", 1L), 'F', GT_ModHandler.getIC2Item("frequencyTransmitter", 1L)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("energyOMat", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("energyOMat", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RBR", "CMC", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.battery.get(Materials.Basic), 'M', GT_ModHandler.getIC2Item("machine", 1L)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("advBattery", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("advBattery", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CTC", "TST", "TLT", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'S', OrePrefixes.dust.get(Materials.Sulfur), 'L', OrePrefixes.dust.get(Materials.Lead), 'T', GT_ModHandler.getIC2Item("casingbronze", 1L)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("boatElectric", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("boatElectric", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CCC", "XWX", aTextIron2, 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'X', OrePrefixes.plate.get(Materials.Iron), 'W', GT_ModHandler.getIC2Item("waterMill", 1L)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("cropnalyzer", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("cropnalyzer", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CC ", "RGR", "RIR", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'R', OrePrefixes.dust.get(Materials.Redstone), 'G', OrePrefixes.block.get(Materials.Glass), 'I', OrePrefixes.circuit.get(Materials.Basic)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("coil", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("coil", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CCC", "CXC", "CCC", 'C', OrePrefixes.wireGt01.get(Materials.Copper), 'X', OrePrefixes.ingot.get(Materials.Iron)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("powerunit", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("powerunit", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"BCA", "BIM", "BCA", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'B', OrePrefixes.battery.get(Materials.Basic), 'A', GT_ModHandler.getIC2Item("casingiron", 1L), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getIC2Item("elemotor", 1L)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("powerunitsmall", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("powerunitsmall", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" CA", "BIM", " CA", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'B', OrePrefixes.battery.get(Materials.Basic), 'A', GT_ModHandler.getIC2Item("casingiron", 1L), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', GT_ModHandler.getIC2Item("elemotor", 1L)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("remote", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("remote", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" C ", "TLT", " F ", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'L', OrePrefixes.dust.get(Materials.Lapis), 'T', GT_ModHandler.getIC2Item("casingtin", 1L), 'F', GT_ModHandler.getIC2Item("frequencyTransmitter", 1L)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("odScanner", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("odScanner", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PGP", "CBC", "WWW", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'G', OrePrefixes.dust.get(Materials.Glowstone), 'B', OrePrefixes.battery.get(Materials.Advanced), 'C', OrePrefixes.circuit.get(Materials.Advanced), 'P', GT_ModHandler.getIC2Item("casinggold", 1L)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("ovScanner", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("ovScanner", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PDP", "GCG", "WSW", 'W', OrePrefixes.cableGt01.get(Materials.Gold), 'G', OrePrefixes.dust.get(Materials.Glowstone), 'D', OrePrefixes.battery.get(Materials.Elite), 'C', OrePrefixes.circuit.get(Materials.Advanced), 'P', GT_ModHandler.getIC2Item("casinggold", 1L), 'S', GT_ModHandler.getIC2Item("odScanner", 1L)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("solarHelmet", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("staticBoots", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("staticBoots", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"I I", "IWI", "CCC", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'I', OrePrefixes.ingot.get(Materials.Iron), 'W', new ItemStack(Blocks.wool)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("ecMeter", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("ecMeter", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" G ", "CIC", "C C", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'G', OrePrefixes.dust.get(Materials.Glowstone), 'I', OrePrefixes.circuit.get(Materials.Basic)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("obscurator", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("obscurator", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RER", "CAC", "RRR", 'C', OrePrefixes.cableGt01.get(Materials.Gold), 'R', OrePrefixes.dust.get(Materials.Redstone), 'E', OrePrefixes.battery.get(Materials.Advanced), 'A', OrePrefixes.circuit.get(Materials.Advanced)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("overclockerUpgrade", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("overclockerUpgrade", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CCC", "WEW", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'C', GT_ModHandler.getIC2Item("reactorCoolantSimple", 1L, 1), 'E', OrePrefixes.circuit.get(Materials.Basic)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("transformerUpgrade", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("transformerUpgrade", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GGG", "WTW", "GEG", 'W', OrePrefixes.cableGt01.get(Materials.Gold), 'T', GT_ModHandler.getIC2Item("mvTransformer", 1L), 'E', OrePrefixes.circuit.get(Materials.Basic), 'G', OrePrefixes.block.get(Materials.Glass)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("energyStorageUpgrade", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("energyStorageUpgrade", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PPP", "WBW", "PEP", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'E', OrePrefixes.circuit.get(Materials.Basic), 'P', OrePrefixes.plank.get(Materials.Wood), 'B', OrePrefixes.battery.get(Materials.Basic)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("ejectorUpgrade", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("ejectorUpgrade", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PHP", "WEW", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'E', OrePrefixes.circuit.get(Materials.Basic), 'P', new ItemStack(Blocks.piston), 'H', new ItemStack(Blocks.hopper)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("suBattery", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("suBattery", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"W", "C", "R", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.HydratedCoal), 'R', OrePrefixes.dust.get(Materials.Redstone)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("frequencyTransmitter", 1L));		
            GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Lead, 1), OrePrefixes.circuit.get(Materials.Basic), 1,GT_Values.NF, GT_ModHandler.getIC2Item("frequencyTransmitter", 1L), 100, 8);
            GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Zinc, 1), OrePrefixes.circuit.get(Materials.Basic), 1,GT_Values.NF, GT_ModHandler.getIC2Item("frequencyTransmitter", 1L), 100, 8);		
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("pullingUpgrade", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("pullingUpgrade", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PHP", "WEW", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'P', new ItemStack(Blocks.sticky_piston), 'R', new ItemStack(Blocks.hopper), 'E', OrePrefixes.circuit.get(Materials.Basic)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("cutter", 1L));



        } else {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("glassFiberCableItem", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GGG", "EDE", "GGG", 'G', new ItemStack(Blocks.glass, 1, 32767), 'D', OrePrefixes.dust.get(Materials.Silver), 'E', ItemList.IC2_Energium_Dust.get(1L)});
        }

        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("UranFuel", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"UUU", "NNN", "UUU", 'U', OrePrefixes.ingot.get(Materials.Uranium), 'N', OrePrefixes.nugget.get(Materials.Uranium235)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("MOXFuel", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"UUU", "NNN", "UUU", 'U', OrePrefixes.ingot.get(Materials.Uranium), 'N', OrePrefixes.ingot.get(Materials.Plutonium)});

        if (!GregTech_API.mIC2Classic) {
            GT_ModHandler.addCraftingRecipe(ItemList.Uraniumcell_2.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "   ", "   ", 'R', ItemList.Uraniumcell_1, 'P', OrePrefixes.plate.get(Materials.Iron)});
            GT_ModHandler.addCraftingRecipe(ItemList.Uraniumcell_4.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "CPC", "RPR", 'R', ItemList.Uraniumcell_1, 'P', OrePrefixes.plate.get(Materials.Iron), 'C', OrePrefixes.plate.get(Materials.Copper)});
            GT_ModHandler.addCraftingRecipe(ItemList.Moxcell_2.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "   ", "   ", 'R', ItemList.Moxcell_1, 'P', OrePrefixes.plate.get(Materials.Iron)});
            GT_ModHandler.addCraftingRecipe(ItemList.Moxcell_4.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "CPC", "RPR", 'R', ItemList.Moxcell_1, 'P', OrePrefixes.plate.get(Materials.Iron), 'C', OrePrefixes.plate.get(Materials.Copper)});

            GT_ModHandler.removeRecipeByOutput(Ic2Items.miningLaser.copy());
            GT_ModHandler.addCraftingRecipe(Ic2Items.miningLaser.copy(), new Object[]{"PPP", "GEC", "SBd", 'P', OrePrefixes.plate.get(Materials.Titanium), 'G', OrePrefixes.gemExquisite.get(Materials.Diamond), 'E', ItemList.Emitter_HV, 'C', OrePrefixes.circuit.get(Materials.Elite), 'S', OrePrefixes.screw.get(Materials.Titanium), 'B', new ItemStack(Ic2Items.chargingEnergyCrystal.copy().getItem(), 1, GT_Values.W)});
            GT_ModHandler.addCraftingRecipe(Ic2Items.miningLaser.copy(), new Object[]{"PPP", "GEC", "SBd", 'P', OrePrefixes.plate.get(Materials.Titanium), 'G', OrePrefixes.gemExquisite.get(Materials.Ruby), 'E', ItemList.Emitter_HV, 'C', OrePrefixes.circuit.get(Materials.Elite), 'S', OrePrefixes.screw.get(Materials.Titanium), 'B', new ItemStack(Ic2Items.chargingEnergyCrystal.copy().getItem(), 1, GT_Values.W)});
            GT_ModHandler.addCraftingRecipe(Ic2Items.miningLaser.copy(), new Object[]{"PPP", "GEC", "SBd", 'P', OrePrefixes.plate.get(Materials.Titanium), 'G', OrePrefixes.gemExquisite.get(Materials.Jasper), 'E', ItemList.Emitter_HV, 'C', OrePrefixes.circuit.get(Materials.Elite), 'S', OrePrefixes.screw.get(Materials.Titanium), 'B', new ItemStack(Ic2Items.chargingEnergyCrystal.copy().getItem(), 1, GT_Values.W)});
            GT_ModHandler.addCraftingRecipe(Ic2Items.miningLaser.copy(), new Object[]{"PPP", "GEC", "SBd", 'P', OrePrefixes.plate.get(Materials.Titanium), 'G', OrePrefixes.gemExquisite.get(Materials.GarnetRed), 'E', ItemList.Emitter_HV, 'C', OrePrefixes.circuit.get(Materials.Elite), 'S', OrePrefixes.screw.get(Materials.Titanium), 'B', new ItemStack(Ic2Items.chargingEnergyCrystal.copy().getItem(), 1, GT_Values.W)});
        }

        GT_ModHandler.removeRecipeByOutput(ItemList.IC2_Energium_Dust.get(1L));
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.gregtechrecipes, "energycrystalruby", true)) {
            GT_ModHandler.addCraftingRecipe(ItemList.IC2_Energium_Dust.get(9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RDR", "DRD", "RDR", 'R', OrePrefixes.dust.get(Materials.Redstone), 'D', OrePrefixes.dust.get(Materials.Ruby)});
        } else {
            GT_ModHandler.addCraftingRecipe(ItemList.IC2_Energium_Dust.get(9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RDR", "DRD", "RDR", 'R', OrePrefixes.dust.get(Materials.Redstone), 'D', OrePrefixes.dust.get(Materials.Diamond)});
        }
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("lapotronCrystal", 1L));
        for (Materials tCMat : new Materials[]{Materials.Lapis, Materials.Lazurite, Materials.Sodalite}) {
            GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("lapotronCrystal", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.gemExquisite.get(Materials.Sapphire), OrePrefixes.stick.get(tCMat), ItemList.Circuit_Parts_Wiring_Elite});
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("lapotronCrystal", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"LCL", "RSR", "LCL", 'C', OrePrefixes.circuit.get(Materials.Data), 'S', GT_ModHandler.getIC2Item("energyCrystal", 1L, 32767), 'L', OrePrefixes.plate.get(tCMat), 'R', OrePrefixes.stick.get(tCMat)});
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("lapotronCrystal", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"LCL", "RSR", "LCL", 'C', OrePrefixes.circuit.get(Materials.Advanced), 'S', OrePrefixes.gemFlawless.get(Materials.Sapphire), 'L', OrePrefixes.plate.get(tCMat), 'R', OrePrefixes.stick.get(tCMat)});
        }
        GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("miningPipe", 8));
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("miningPipe", 1), new Object[]{"hPf", 'P', OrePrefixes.pipeSmall.get(Materials.Steel)});
        GT_Values.RA.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeTiny, Materials.Steel, 1), GT_ModHandler.getIC2Item("miningPipe", 1), 200, 16);
        
        //More recipes for mining pipe
		GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeTiny, Materials.Steel, 1L), ItemList.Shape_Extruder_Pipe_Tiny.get(0L), GT_ModHandler.getIC2Item("miningPipe", 1), 1, 64);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeSmall, Materials.Steel, 1L), ItemList.Shape_Extruder_Pipe_Tiny.get(0L), GT_ModHandler.getIC2Item("miningPipe", 2), 4, 64);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1L), ItemList.Shape_Extruder_Pipe_Tiny.get(0L), GT_ModHandler.getIC2Item("miningPipe", 4), 8, 64);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Steel, 1L), ItemList.Shape_Extruder_Pipe_Tiny.get(0L), GT_ModHandler.getIC2Item("miningPipe", 8), 16, 64);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeHuge, Materials.Steel, 1L), ItemList.Shape_Extruder_Pipe_Tiny.get(0L), GT_ModHandler.getIC2Item("miningPipe", 16), 32, 64);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeTiny, Materials.StainlessSteel, 1L), ItemList.Shape_Extruder_Pipe_Tiny.get(0L), GT_ModHandler.getIC2Item("miningPipe", 8), 4, 120);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeSmall, Materials.StainlessSteel, 1L), ItemList.Shape_Extruder_Pipe_Tiny.get(0L), GT_ModHandler.getIC2Item("miningPipe", 16), 8, 120);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1L), ItemList.Shape_Extruder_Pipe_Tiny.get(0L), GT_ModHandler.getIC2Item("miningPipe", 32), 16, 120);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.StainlessSteel, 1L), ItemList.Shape_Extruder_Pipe_Tiny.get(0L), GT_ModHandler.getIC2Item("miningPipe", 64), 32, 120);

        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("luminator", 16L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RTR", "GHG", "GGG", 'H', OrePrefixes.cell.get(Materials.Helium), 'T', OrePrefixes.ingot.get(Materials.Tin), 'R', OrePrefixes.ingot.get(Materials.Iron), 'G', new ItemStack(Blocks.glass, 1)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("luminator", 16L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RTR", "GHG", "GGG", 'H', OrePrefixes.cell.get(Materials.Mercury), 'T', OrePrefixes.ingot.get(Materials.Tin), 'R', OrePrefixes.ingot.get(Materials.Iron), 'G', new ItemStack(Blocks.glass, 1)});
        if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "beryliumreflector", true)) &&
                (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reactorReflectorThick", 1L, 1)))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("reactorReflectorThick", 1L, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" N ", "NBN", " N ", 'B', OrePrefixes.plateDouble.get(Materials.Beryllium), 'N', GT_ModHandler.getIC2Item("reactorReflector", 1L, 1)});
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("reactorReflectorThick", 1L, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" B ", "NCN", " B ", 'B', OrePrefixes.plate.get(Materials.Beryllium), 'N', GT_ModHandler.getIC2Item("reactorReflector", 1L, 1), 'C', OrePrefixes.plate.get(Materials.TungstenCarbide)});
        }
        if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "reflector", true)) &&
                (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reactorReflector", 1L, 1)))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("reactorReflector", 1L, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"TGT", "GSG", "TGT", 'T', OrePrefixes.plate.get(Materials.Tin), 'G', OrePrefixes.dust.get(Materials.Graphite), 'S', OrePrefixes.plateDouble.get(Materials.Steel)});
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("reactorReflector", 1L, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"TTT", "GSG", "TTT", 'T', OrePrefixes.plate.get(Materials.TinAlloy), 'G', OrePrefixes.dust.get(Materials.Graphite), 'S', OrePrefixes.plate.get(Materials.Beryllium)});
        }
        if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "cropharvester", true)) &&
                (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("crophavester", 1L)))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("crophavester", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"ACA", "PMS", "WOW", 'M', ItemList.Hull_HV, 'C', OrePrefixes.circuit.get(Materials.Advanced), 'A', ItemList.Robot_Arm_HV, 'P', ItemList.Electric_Piston_HV, 'S', ItemList.Sensor_HV, 'W', OrePrefixes.toolHeadSense.get(Materials.StainlessSteel), 'O', ItemList.Conveyor_Module_HV});
        }
        if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "nuclearReactor", true)) &&
                (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("nuclearReactor", 1L)))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("nuclearReactor", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "PMP", "PAP", 'P', OrePrefixes.plateDense.get(Materials.Lead), 'C', OrePrefixes.circuit.get(Materials.Elite), 'M', GT_ModHandler.getIC2Item("reactorChamber", 1), 'A', ItemList.Robot_Arm_EV});

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reactorChamber", 1L));
            GT_Values.RA.addAssemblerRecipe(ItemList.Hull_EV.get(1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lead, 4), GT_ModHandler.getIC2Item("reactorChamber", 1), 200, 256);

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reactorvessel", 1L));
            GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Lead, 1), Materials.Concrete.getMolten(144), GT_ModHandler.getIC2Item("reactorvessel", 1), GT_Values.NI, GT_Values.NI, null, 400, 80);

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reactorAccessHatch", 1L));
            GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item("reactorvessel", 1L), ItemList.Conveyor_Module_EV.get(1), GT_ModHandler.getIC2Item("reactorAccessHatch", 1L), 200, 80);

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reactorFluidPort", 1L));
            GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item("reactorvessel", 1L), ItemList.Electric_Pump_EV.get(1), GT_ModHandler.getIC2Item("reactorFluidPort", 1L), 200, 80);

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reactorRedstonePort", 1L));
            GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item("reactorvessel", 1L), OrePrefixes.circuit.get(Materials.Elite), 1,GT_Values.NF, GT_ModHandler.getIC2Item("reactorRedstonePort", 1L), 200, 80);
        }
        if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "rtg", true)) &&
                (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("RTGenerator", 1L)))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("RTGenerator", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"III", "IMI", "ICI", 'I', ItemList.IC2_Item_Casing_Steel, 'C', OrePrefixes.circuit.get(Materials.Master), 'M', ItemList.Hull_IV});

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("RTHeatGenerator", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("RTHeatGenerator", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"III", "IMB", "ICI", 'I', ItemList.IC2_Item_Casing_Steel, 'C', OrePrefixes.circuit.get(Materials.Master), 'M', ItemList.Hull_IV, 'B', GT_OreDictUnificator.get(OrePrefixes.block, Materials.Copper, 1)});
        }
        if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "windRotor", true)) &&
                (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("carbonrotor", 1L)))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("carbonrotor", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', GT_ModHandler.getIC2Item("carbonrotorblade", 1), 'S', OrePrefixes.screw.get(Materials.Iridium), 'T', GT_ModHandler.getIC2Item("steelshaft", 1)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("steelrotor", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("steelrotor", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', GT_ModHandler.getIC2Item("steelrotorblade", 1), 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'T', GT_ModHandler.getIC2Item("ironshaft", 1)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("ironrotor", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("ironrotor", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', GT_ModHandler.getIC2Item("ironrotorblade", 1), 'S', OrePrefixes.screw.get(Materials.WroughtIron), 'T', GT_ModHandler.getIC2Item("ironshaft", 1)});
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("woodrotor", 1L));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("woodrotor", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', GT_ModHandler.getIC2Item("woodrotorblade", 1), 'S', OrePrefixes.screw.get(Materials.WroughtIron), 'T', OrePrefixes.stickLong.get(Materials.WroughtIron)});
        }
        GT_Log.out.println("GT_Mod: Applying Recipes for Tools");
        if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "nanosaber", true)) &&
                (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoSaber", 1L)))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("nanoSaber", 1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PI ", "PI ", "CLC", 'L', OrePrefixes.battery.get(Materials.Master), 'I', OrePrefixes.plateAlloy.get("Iridium"), 'P', OrePrefixes.plate.get(Materials.Platinum), 'C', OrePrefixes.circuit.get(Materials.Elite)});
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "namefix", true)) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.removeRecipeByOutput(new ItemStack(Items.flint_and_steel, 1)) ? new ItemStack(Items.flint_and_steel, 1) : null, GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"S ", " F", 'F', new ItemStack(Items.flint, 1), 'S', "nuggetSteel"});
        }
        if (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("diamondDrill", 1L))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("diamondDrill", 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" D ", "DMD", "TAT", 'M', GT_ModHandler.getIC2Item("miningDrill", 1L, 32767), 'D', OreDictNames.craftingIndustrialDiamond, 'T', OrePrefixes.plate.get(Materials.Titanium), 'A', OrePrefixes.circuit.get(Materials.Advanced)});
        }
        if (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("miningDrill", 1L))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("miningDrill", 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" S ", "SCS", "SBS", 'C', OrePrefixes.circuit.get(Materials.Basic), 'B', OrePrefixes.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefixes.plate.get(Materials.StainlessSteel) : OrePrefixes.plate.get(Materials.Iron)});
        }
        if (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("chainsaw", 1L))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("chainsaw", 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"BS ", "SCS", " SS", 'C', OrePrefixes.circuit.get(Materials.Basic), 'B', OrePrefixes.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefixes.plate.get(Materials.StainlessSteel) : OrePrefixes.plate.get(Materials.Iron)});
        }
        if (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("electricHoe", 1L))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("electricHoe", 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"SS ", " C ", " B ", 'C', OrePrefixes.circuit.get(Materials.Basic), 'B', OrePrefixes.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefixes.plate.get(Materials.StainlessSteel) : OrePrefixes.plate.get(Materials.Iron)});
        }
        if (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("electricTreetap", 1L))) {
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("electricTreetap", 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" B ", "SCS", "S  ", 'C', OrePrefixes.circuit.get(Materials.Basic), 'B', OrePrefixes.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefixes.plate.get(Materials.StainlessSteel) : OrePrefixes.plate.get(Materials.Iron)});
        }
        GT_Log.out.println("GT_Mod: Removing Q-Armor Recipes if configured.");
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QHelmet", false)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumHelmet", 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QPlate", false)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumBodyarmor", 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QLegs", false)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumLeggings", 1L));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QBoots", false)) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumBoots", 1L));
        }
        //Ic2 ArmorBatpack/Energypack/NightvisionGoggles
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemArmorBatpack", 1L));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemArmorAdvBatpack", 1L));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemArmorEnergypack", 1L));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemAdvBat", 1L));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemNightvisionGoggles", 1L));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemArmorJetpackElectric", 1, GT_Values.W));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemArmorJetpack", 1L));
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "itemArmorBatpack", 1, GT_Values.W), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] {"RCR", "RAR", "RTR", 'R', GT_ModHandler.getModItem("IC2", "itemBatREDischarged", 1, 0), 'C', OrePrefixes.circuit.get(Materials.Basic), 'A',  ItemList.IC2_Item_Casing_Steel,  'T', OrePrefixes.wireGt02.get(Materials.Tin)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "itemArmorAdvBatpack", 1, GT_Values.W), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] {"RCR", "RAR", "RTR", 'R', GT_ModHandler.getModItem("IC2", "itemAdvBat", 1, GT_Values.W), 'C', OrePrefixes.circuit.get(Materials.Good), 'A',  GT_ModHandler.getModItem("IC2", "itemArmorBatpack", 1, GT_Values.W),  'T', OrePrefixes.wireGt04.get(Materials.AnnealedCopper)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "itemArmorEnergypack", 1, GT_Values.W), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] {"CSC", "EXE", "STS", 'E', GT_ModHandler.getModItem("IC2", "itemBatCrystal", 1, GT_Values.W), 'C', OrePrefixes.circuit.get(Materials.Advanced), 'X',  GT_ModHandler.getModItem("IC2", "itemArmorAdvBatpack", 1, GT_Values.W),  'T', OrePrefixes.wireGt08.get(Materials.Gold), 'S', ItemList.IC2_Item_Casing_Steel});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "itemArmorJetpackElectric", 1, GT_Values.W), new Object[] {"SCS", "MBM", "EWE", 'S', ItemList.IC2_Item_Casing_Steel,  'C', OrePrefixes.circuit.get(Materials.Advanced),  'M', ItemList.Electric_Motor_HV,  'B', GT_ModHandler.getModItem("IC2", "itemArmorBatpack", 1, GT_Values.W), 'W', OrePrefixes.wireGt04.get(Materials.AnnealedCopper), 'E', OrePrefixes.rotor.get(Materials.StainlessSteel)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "itemArmorJetpack", 1, GT_Values.W), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] {"SXS", "TCT", "EZE", 'S', ItemList.IC2_Item_Casing_Steel,  'X', OrePrefixes.circuit.get(Materials.Advanced),  'T', ItemList.Large_Fluid_Cell_Steel.get(1), 'C', GT_ModHandler.getModItem("IC2", "reactorCoolantSix", 1, 1), 'Z', OrePrefixes.pipeMedium.get(Materials.Steel), 'E', OrePrefixes.rotor.get(Materials.StainlessSteel)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("IC2", "itemNightvisionGoggles", 1, GT_Values.W), new Object[] {"AXA", "RBR", "SdS", 'A', GT_ModHandler.getModItem("IC2", "reactorHeatSwitchDiamond", 1, 1),  'X', OrePrefixes.screw.get(Materials.StainlessSteel),  'B', OrePrefixes.bolt.get(Materials.StainlessSteel), 'R', OrePrefixes.ring.get(Materials.StainlessSteel), 'S', OrePrefixes.lens.get(Materials.Olivine)});
        //Ic2 ChargeCrystal
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2","itemBatChargeRE", 1L, GT_Values.W));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2","itemBatChargeAdv", 1L, GT_Values.W));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2","itemBatChargeCrystal", 1L, GT_Values.W));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("IC2","itemBatChargeLamaCrystal", 1L, GT_Values.W));

		GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("IC2", "blockElectric", 1L, 0), 
		ItemList.Sensor_LV.get(1, new Object(){}),
		GT_ModHandler.getModItem("IC2", "reactorHeatSwitch", 1L, 1),
		GT_Utility.getIntegratedCircuit(3)}, 
		Materials.SolderingAlloy.getMolten(144L), 
		GT_ModHandler.getModItem("IC2", "itemBatChargeRE", 1L, 0), 200, 30);
		
		GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("IC2", "blockElectric", 1L, 7),
		ItemList.Sensor_MV.get(1, new Object(){}),
		GT_ModHandler.getModItem("IC2", "reactorHeatSwitch", 1L, 1),
		GT_Utility.getIntegratedCircuit(3)},
		Materials.SolderingAlloy.getMolten(288L), 
		GT_ModHandler.getModItem("IC2", "itemBatChargeAdv", 1L, 0), 200, 120);
		
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("IC2", "blockElectric", 1L, 1),
		ItemList.Sensor_HV.get(1, new Object(){}),
		GT_ModHandler.getModItem("IC2", "reactorHeatSwitchSpread", 1L, 1),
		GT_Utility.getIntegratedCircuit(3)},
		Materials.SolderingAlloy.getMolten(576L), 
		GT_ModHandler.getModItem("IC2", "itemBatChargeCrystal", 1L, 0), 200, 480);
		
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("IC2", "blockElectric", 1L, 2),
		ItemList.Sensor_EV.get(1, new Object(){}),
		GT_ModHandler.getModItem("IC2", "reactorHeatSwitchDiamond", 1L, 1),
		GT_Utility.getIntegratedCircuit(3)},
		Materials.SolderingAlloy.getMolten(1440L), 
		GT_ModHandler.getModItem("IC2", "itemBatChargeLamaCrystal", 1L, 0), 200, 1920);

        GT_ModHandler.addCraftingRecipe(Ic2Items.toolbox.copy(), new Object[]{"SPS", "PdP", "SPS", 'S', OrePrefixes.screw.get(Materials.Bronze), 'P', GT_ModHandler.getIC2Item("casingbronze", 1L)});
    }
}
