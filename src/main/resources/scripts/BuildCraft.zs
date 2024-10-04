// --- Created by Jason McRay ---
// --- Edited by Sapient ---

import mods.nei.NEI;
import mods.gregtech.FormingPress;
import mods.gregtech.PrecisionLaser;
import mods.buildcraft.AssemblyTable;
import mods.gregtech.Lathe;
import minetweaker.item.IItemStack;

# Aliases
var barsIron = <minecraft:iron_bars>;
var BCGearDiamond = <BuildCraft|Core:diamondGearItem>;
var BCGearGold = <BuildCraft|Core:goldGearItem>;
var BCGearIron = <BuildCraft|Core:ironGearItem>;
var BCGearStone = <BuildCraft|Core:stoneGearItem>;
var BCGearWood = <BuildCraft|Core:woodenGearItem>;
var blockGlass = <ore:blockGlass>;
var blockRedstone = <minecraft:redstone_block>;
var bucket = <minecraft:bucket>;
var crystalRedstone = <BuildCraft|Silicon:redstoneCrystal>;
var dustRedstone = <ore:dustRedstone>;
var dustSmallGlass = <gregtech:gt.metaitem.01:1890>;
var casingMV = <gregtech:gt.blockcasings:2>;
var conveyorMV = <gregtech:gt.metaitem.01:32631>;
var epistonMV = <gregtech:gt.metaitem.01:32641>;
var robotarmMV = <gregtech:gt.metaitem.01:32651>;
var robotarmHV = <gregtech:gt.metaitem.01:32652>;
var motorMV = <gregtech:gt.metaitem.01:32601>;
var motorHV = <gregtech:gt.metaitem.01:32602>;
var engineCombustion = <BuildCraft|Core:engineBlock:2>;
var engineRedstone = <BuildCraft|Core:engineBlock>;
var engineStirling = <BuildCraft|Core:engineBlock:1>;
var filterBlack = <BuildCraft|Transport:pipeLens:16>;
var filterBlue = <BuildCraft|Transport:pipeLens:20>;
var filterBrown = <BuildCraft|Transport:pipeLens:19>;
var filterCyan = <BuildCraft|Transport:pipeLens:22>;
var filterGray = <BuildCraft|Transport:pipeLens:24>;
var filterGreen = <BuildCraft|Transport:pipeLens:18>;
var filterLightBlue = <BuildCraft|Transport:pipeLens:28>;
var filterLightGray = <BuildCraft|Transport:pipeLens:23>;
var filterLime = <BuildCraft|Transport:pipeLens:26>;
var filterMagenta = <BuildCraft|Transport:pipeLens:29>;
var filterOrange = <BuildCraft|Transport:pipeLens:30>;
var filterPink = <BuildCraft|Transport:pipeLens:25>;
var filterPurple = <BuildCraft|Transport:pipeLens:21>;
var filterRed = <BuildCraft|Transport:pipeLens:17>;
var filterWhite = <BuildCraft|Transport:pipeLens:31>;
var filterYellow = <BuildCraft|Transport:pipeLens:27>;
var gearIron = <ore:gearIron>;
var gearStone = <ore:gearStone>;
var gearWIron = <ore:gearWroughtIron>;
var gearWood = <ore:gearWood>;
var glass = <minecraft:glass>;
var glassBlack = <minecraft:stained_glass:15>;
var glassBlue = <minecraft:stained_glass:11>;
var glassBrown = <minecraft:stained_glass:12>;
var glassCyan = <minecraft:stained_glass:9>;
var glassGray = <minecraft:stained_glass:7>;
var glassGreen = <minecraft:stained_glass:13>;
var glassLightBlue = <minecraft:stained_glass:3>;
var glassLightGray = <minecraft:stained_glass:8>;
var glassLime = <minecraft:stained_glass:5>;
var glassMagenta = <minecraft:stained_glass:2>;
var glassOrange = <minecraft:stained_glass:1>;
var glassPink = <minecraft:stained_glass:6>;
var glassPurple = <minecraft:stained_glass:10>;
var glassRed = <minecraft:stained_glass:14>;
var glassWhite = <minecraft:stained_glass>;
var glassYellow = <minecraft:stained_glass:4>;
var HHammer = <ore:craftingToolHardHammer>;
var hopper = <minecraft:hopper>;
var chest = <minecraft:chest>;
var chipsetDiamond = <BuildCraft|Silicon:redstoneChipset:3>;
var chipsetEmerald = <BuildCraft|Silicon:redstoneChipset:7>;
var chipsetGold = <BuildCraft|Silicon:redstoneChipset:2>;
var chipsetIron = <BuildCraft|Silicon:redstoneChipset:1>;
var chipsetPulsating = <BuildCraft|Silicon:redstoneChipset:4>;
var chipsetQuartz = <BuildCraft|Silicon:redstoneChipset:5>;
var chipsetRedstone = <BuildCraft|Silicon:redstoneChipset>;
var chipsetRedstoneComp = <BuildCraft|Silicon:redstoneChipset:6>;
var lensBlack = <BuildCraft|Transport:pipeLens>;
var lensBlue = <BuildCraft|Transport:pipeLens:4>;
var lensBrown = <BuildCraft|Transport:pipeLens:3>;
var lensCyan = <BuildCraft|Transport:pipeLens:6>;
var lenseIgnis = <gregtech:gt.metaitem.01:24541>;
var lenseJasper = <gregtech:gt.metaitem.01:24511>;
var lenseRedGarnet = <gregtech:gt.metaitem.01:24527>;
var lenseRuby = <gregtech:gt.metaitem.01:24502>;
var lenseRuby2 = <gregtech:gt.metaitem.01:24512>;
var lensGray = <BuildCraft|Transport:pipeLens:8>;
var lensGreen = <BuildCraft|Transport:pipeLens:2>;
var lensLightBlue = <BuildCraft|Transport:pipeLens:12>;
var lensLightGray = <BuildCraft|Transport:pipeLens:7>;
var lensLime = <BuildCraft|Transport:pipeLens:10>;
var lensMagenta = <BuildCraft|Transport:pipeLens:13>;
var lensOrange = <BuildCraft|Transport:pipeLens:14>;
var lensPink = <BuildCraft|Transport:pipeLens:9>;
var lensPurple = <BuildCraft|Transport:pipeLens:5>;
var lensRed = <BuildCraft|Transport:pipeLens:1>;
var lensWhite = <BuildCraft|Transport:pipeLens:15>;
var lensYellow = <BuildCraft|Transport:pipeLens:11>;
var pickaxeSteel = <Railcraft:tool.steel.pickaxe>;
var pipePlug = <BuildCraft|Transport:pipePlug>;
var pipeWireBlue = <BuildCraft|Transport:pipeWire:1>;
var pipeWireGreen = <BuildCraft|Transport:pipeWire:2>;
var pipeWireRed = <BuildCraft|Transport:pipeWire>;
var pipeWireYellow = <BuildCraft|Transport:pipeWire:3>;
var piston = <minecraft:piston>;
var plateEmerald = <gregtech:gt.metaitem.01:17501>;
var plateIron = <ore:plateIron>;
var plateSteel = <ore:plateSteel>;
var plateWIron = <ore:plateWroughtIron>;
var plateWood = <ore:plateWood>;
var quarry = <BuildCraft|Builders:machineBlock>;
var saw = <ore:craftingToolSaw>;
var SHammer = <ore:craftingToolSoftHammer>;
var stoneCobble = <ore:stoneCobble>;
var wrench = <ore:craftingToolWrench>;
var pipeApiarist = <BuildCraft|Compat:item.buildcraftPipe.pipeitemspropolis>;
var filler = <BuildCraft|Builders:fillerBlock>;
var builder = <BuildCraft|Builders:builderBlock>;
var architect = <BuildCraft|Builders:architectBlock>;
var library = <BuildCraft|Builders:libraryBlock>;
var marker = <BuildCraft|Core:markerBlock>;
var cmarker = <BuildCraft|Builders:constructionMarkerBlock>;
var chestIron = <IronChest:BlockIronChest:0>;
var gearGtIron = <ore:gearIron>;
var gearGtGold = <ore:gearGtGold>;
var gearGtDiamond = <ore:gearGtDiamond>;
var monitor = <gregtech:gt.metaitem.01:32740>;
var BasicCircuit = <ore:circuitBasic>;
var laser = <BuildCraft|Silicon:laserBlock>;
var assembly = <BuildCraft|Silicon:laserTableBlock>;
var advancedcrafting = <BuildCraft|Silicon:laserTableBlock:1>;
var programming = <BuildCraft|Silicon:laserTableBlock:4>;
var stamping = <BuildCraft|Silicon:laserTableBlock:5>;
var integration = <BuildCraft|Silicon:laserTableBlock:2>;
var charging = <BuildCraft|Silicon:laserTableBlock:3>;
var packager = <BuildCraft|Silicon:packagerBlock>;


# Items/Blocks Removal
<ore:gearWood>.remove(BCGearWood);
<ore:gearStone>.remove(BCGearStone);
<ore:gearIron>.remove(BCGearIron);
<ore:gearGold>.remove(BCGearGold);
<ore:gearDiamond>.remove(BCGearDiamond);
recipes.remove(BCGearWood);
NEI.hide(BCGearWood);
recipes.remove(BCGearStone);
NEI.hide(BCGearStone);
recipes.remove(BCGearIron);
NEI.hide(BCGearIron);
recipes.remove(BCGearGold);
NEI.hide(BCGearGold);
recipes.remove(BCGearDiamond);
NEI.hide(BCGearDiamond);
recipes.remove(advancedcrafting);
NEI.hide(advancedcrafting);
recipes.remove(programming);
NEI.hide(programming);
recipes.remove(BCGearDiamond);
NEI.hide(BCGearDiamond);
recipes.remove(stamping);
NEI.hide(stamping);

# Recipe Changes
recipes.remove(engineRedstone);
recipes.remove(engineStirling);
recipes.remove(engineCombustion);
recipes.remove(filler);
recipes.addShaped(filler, [
	[epistonMV, marker, epistonMV],
	[conveyorMV, casingMV, conveyorMV],
	[gearGtGold, chestIron, gearGtGold]]);	
recipes.remove(builder);
recipes.addShaped(builder, [
	[robotarmMV, marker, robotarmMV],
	[conveyorMV, casingMV, conveyorMV],
	[gearGtDiamond, chestIron, gearGtDiamond]]);	
recipes.remove(architect);
recipes.addShaped(architect, [
	[robotarmMV, marker, robotarmMV],
	[conveyorMV, casingMV, conveyorMV],
	[gearGtDiamond, monitor, gearGtDiamond]]);	
recipes.remove(library);
recipes.addShaped(library, [
	[plateIron, <ore:gearGtIron>, plateIron],
	[chipsetRedstone, monitor, chipsetRedstone],
	[plateIron, BasicCircuit, plateIron]]);	
recipes.remove(laser);	
recipes.addShaped(laser, [
	[null, <ore:lensRuby>, null],
	[chipsetRedstone, <ore:crystalRedstone>, chipsetRedstone],
	[<minecraft:obsidian>, <gregtech:gt.metaitem.01:32681>, <minecraft:obsidian>]]);
recipes.remove(assembly);
recipes.addShaped(assembly, [
	[null, null, null],
	[<minecraft:obsidian>, robotarmMV, <minecraft:obsidian>],
	[<minecraft:obsidian>, motorMV, <minecraft:obsidian>]]);
recipes.remove(integration);	
recipes.addShaped(integration, [
	[null, null, null],
	[<minecraft:obsidian>, robotarmHV, <minecraft:obsidian>],
	[<minecraft:obsidian>, motorHV, <minecraft:obsidian>]]);
recipes.remove(charging);	
NEI.hide(charging);
recipes.remove(packager);	
NEI.hide(packager);

# Specials
//recipes.remove(quarry);
NEI.hide(quarry);

# GT Integration
//FormingPress.addRecipe(output, input1, input2, durationTicks, euPerTick);
//PrecisionLaser.addRecipe(output, lense, input, durationTicks, euPerTick);
//Lathe.addRecipe([output1, output2], input, durationTicks, euPerTick);
AssemblyTable.remove(crystalRedstone);
AssemblyTable.remove(chipsetEmerald);
AssemblyTable.remove(chipsetRedstone);
AssemblyTable.remove(chipsetGold);
AssemblyTable.remove(chipsetDiamond);
AssemblyTable.remove(chipsetPulsating);
AssemblyTable.remove(chipsetQuartz);
AssemblyTable.remove(chipsetRedstoneComp);
AssemblyTable.remove(chipsetIron);
AssemblyTable.remove(pipeWireRed);
AssemblyTable.remove(pipeWireBlue);
AssemblyTable.remove(pipeWireGreen);
AssemblyTable.remove(pipeWireYellow);
AssemblyTable.remove(pipePlug);
AssemblyTable.remove(lensBlack);
AssemblyTable.remove(lensRed);
AssemblyTable.remove(lensGreen);
AssemblyTable.remove(lensBrown);
AssemblyTable.remove(lensBlue);
AssemblyTable.remove(lensPurple);
AssemblyTable.remove(lensCyan);
AssemblyTable.remove(lensLightGray);
AssemblyTable.remove(lensGray);
AssemblyTable.remove(lensPink);
AssemblyTable.remove(lensLime);
AssemblyTable.remove(lensYellow);
AssemblyTable.remove(lensLightBlue);
AssemblyTable.remove(lensMagenta);
AssemblyTable.remove(lensOrange);
AssemblyTable.remove(lensWhite);
AssemblyTable.remove(filterBlack);
AssemblyTable.remove(filterRed);
AssemblyTable.remove(filterGreen);
AssemblyTable.remove(filterBrown);
AssemblyTable.remove(filterBlue);
AssemblyTable.remove(filterPurple);
AssemblyTable.remove(filterCyan);
AssemblyTable.remove(filterLightGray);
AssemblyTable.remove(filterGray);
AssemblyTable.remove(filterPink);
AssemblyTable.remove(filterLime);
AssemblyTable.remove(filterYellow);
AssemblyTable.remove(filterLightBlue);
AssemblyTable.remove(filterMagenta);
AssemblyTable.remove(filterOrange);
AssemblyTable.remove(filterWhite);
FormingPress.addRecipe(chipsetEmerald, chipsetRedstone, plateEmerald, 150, 480);
PrecisionLaser.addRecipe(crystalRedstone, lenseRedGarnet * 0, blockRedstone, 125, 480);
PrecisionLaser.addRecipe(crystalRedstone, lenseRuby * 0, blockRedstone, 125, 480);
PrecisionLaser.addRecipe(crystalRedstone, lenseRuby2 * 0, blockRedstone, 125, 480);
PrecisionLaser.addRecipe(crystalRedstone, lenseIgnis * 0, blockRedstone, 125, 480);
PrecisionLaser.addRecipe(crystalRedstone, lenseJasper * 0, blockRedstone, 125, 480);
Lathe.addRecipe([lensBlack * 2, dustSmallGlass], glassBlack, 100, 32);
Lathe.addRecipe([lensRed * 2, dustSmallGlass], glassRed, 100, 32);
Lathe.addRecipe([lensGreen * 2, dustSmallGlass], glassGreen, 100, 32);
Lathe.addRecipe([lensBrown * 2, dustSmallGlass], glassBrown, 100, 32);
Lathe.addRecipe([lensBlue * 2, dustSmallGlass], glassBlue, 100, 32);
Lathe.addRecipe([lensPurple * 2, dustSmallGlass], glassPurple, 100, 32);
Lathe.addRecipe([lensCyan * 2, dustSmallGlass], glassCyan, 100, 32);
Lathe.addRecipe([lensLightGray * 2, dustSmallGlass], glassLightGray, 100, 32);
Lathe.addRecipe([lensGray * 2, dustSmallGlass], glassGray, 100, 32);
Lathe.addRecipe([lensPink * 2, dustSmallGlass], glassPink, 100, 32);
Lathe.addRecipe([lensLime * 2, dustSmallGlass], glassLime, 100, 32);
Lathe.addRecipe([lensYellow * 2, dustSmallGlass], glassYellow, 100, 32);
Lathe.addRecipe([lensLightBlue * 2, dustSmallGlass], glassLightBlue, 100, 32);
Lathe.addRecipe([lensMagenta * 2, dustSmallGlass], glassMagenta, 100, 32);
Lathe.addRecipe([lensOrange * 2, dustSmallGlass], glassOrange, 100, 32);
Lathe.addRecipe([lensWhite * 2, dustSmallGlass], glassWhite, 100, 32);
FormingPress.addRecipe(filterBlack * 2, glassBlack, barsIron, 100, 32);
FormingPress.addRecipe(filterRed * 2, glassRed, barsIron, 100, 32);
FormingPress.addRecipe(filterGreen * 2, glassGreen, barsIron, 100, 32);
FormingPress.addRecipe(filterBrown * 2, glassBrown, barsIron, 100, 32);
FormingPress.addRecipe(filterBlue * 2, glassBlue, barsIron, 100, 32);
FormingPress.addRecipe(filterPurple * 2, glassPurple, barsIron, 100, 32);
FormingPress.addRecipe(filterCyan * 2, glassCyan, barsIron, 100, 32);
FormingPress.addRecipe(filterLightGray * 2, glassLightGray, barsIron, 100, 32);
FormingPress.addRecipe(filterGray * 2, glassGray, barsIron, 100, 32);
FormingPress.addRecipe(filterPink * 2, glassPink, barsIron, 100, 32);
FormingPress.addRecipe(filterLime * 2, glassLime, barsIron, 100, 32);
FormingPress.addRecipe(filterYellow * 2, glassYellow, barsIron, 100, 32);
FormingPress.addRecipe(filterLightBlue * 2, glassLightBlue, barsIron, 100, 32);
FormingPress.addRecipe(filterMagenta * 2, glassMagenta, barsIron, 100, 32);
FormingPress.addRecipe(filterOrange * 2, glassOrange, barsIron, 100, 32);
FormingPress.addRecipe(filterWhite * 2, glassWhite, barsIron, 100, 32);

# --- Buildcraft ---
# RF Cables
var kinesisWood = <BuildCraft|Transport:item.buildcraftPipe.pipepowerwood:*>;
var kinesisCobble = <BuildCraft|Transport:item.buildcraftPipe.pipepowercobblestone:*>;
var kinesisStone = <BuildCraft|Transport:item.buildcraftPipe.pipepowerstone:*>;
var kinesisQuartz = <BuildCraft|Transport:item.buildcraftPipe.pipepowerquartz:*>;
var kinesisIron = <BuildCraft|Transport:item.buildcraftPipe.pipepoweriron:*>;
var kinesisGold = <BuildCraft|Transport:item.buildcraftPipe.pipepowergold:*>;
var kinesisDiamond = <BuildCraft|Transport:item.buildcraftPipe.pipepowerdiamond:*>;
var kinesisEmerald = <BuildCraft|Transport:item.buildcraftPipe.pipepoweremerald:*>;
var kinesisSandstone = <BuildCraft|Transport:item.buildcraftPipe.pipepowersandstone:*>;

recipes.remove(kinesisWood);
NEI.hide(kinesisWood);
recipes.remove(kinesisCobble);
NEI.hide(kinesisCobble);
recipes.remove(kinesisStone);
NEI.hide(kinesisStone);
recipes.remove(kinesisQuartz);
NEI.hide(kinesisQuartz);
recipes.remove(kinesisIron);
NEI.hide(kinesisIron);
recipes.remove(kinesisGold);
NEI.hide(kinesisGold);
recipes.remove(kinesisDiamond);
NEI.hide(kinesisDiamond);
recipes.remove(kinesisEmerald);
NEI.hide(kinesisEmerald);
recipes.remove(kinesisSandstone);
NEI.hide(kinesisSandstone);


//Buildcraft
var PipeSealant = <BuildCraft|Transport:pipeWaterproof>;
var RedstoneEng = <BuildCraft|Core:engineBlock:0>;
var Pipes = [	
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemswood:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsiron:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsgold:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsquartz:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemssandstone:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemscobblestone:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsstone:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsdiamond:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsemerald:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsobsidian:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsstripes:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsclay:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemslapis:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsvoid:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsdaizuli:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipeitemsemzuli:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipefluidswood:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipefluidsiron:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipefluidsgold:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipefluidsquartz:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipefluidssandstone:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipefluidscobblestone:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipefluidsstone:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipefluidsdiamond:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipefluidsemerald:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipefluidsclay:*>,
	<BuildCraft|Transport:item.buildcraftPipe.pipefluidsvoid:*> 
] as IItemStack[];

for i, X in Pipes {
	recipes.remove(X);
	NEI.hide(X); 
}  

recipes.remove(PipeSealant);
recipes.remove(RedstoneEng);
NEI.hide(PipeSealant);
NEI.hide(RedstoneEng);
NEI.hide(<BuildCraft|Core:buildToolBlock>);
NEI.hide(<BuildCraft|Transport:pipeBlock>);

recipes.remove(pipeApiarist);
recipes.addShaped(pipeApiarist, [[<Forestry:propolis>, <EnderIO:itemItemConduit:0>, <Forestry:propolis>]]);

//Water Tanks
recipes.addShaped(<Railcraft:machine.alpha:14> * 6, [
 [<ore:plankWood>, <ore:plankWood>, <ore:plankWood>],
 [<ore:plateIron>, <IC2:itemHarz>, <ore:plateIron>],
 [<ore:plankWood>, <ore:plankWood>, <ore:plankWood>]]);

// Paintbrush
recipes.remove(<BuildCraft|Core:paintbrush>);

// Wrench
recipes.addShaped(<BuildCraft|Core:wrenchItem>, [
 [<ore:ingotSteel>, <ore:craftingToolHardHammer>, <ore:ingotSteel>],
 [null, <ore:gearGtSteel>, null],
 [null, <ore:ingotSteel>, null]]);
