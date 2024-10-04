import mods.gregtech.Wiremill;
import minetweaker.oredict.IOreDictEntry;
import minetweaker.item.IItemStack;
import mods.gregtech.Assembler;
import mods.gregtech.ChemicalBath;
import mods.gregtech.FormingPress;
import mods.gregtech.Mixer;
import mods.gregtech.AlloySmelter;
import mods.gregtech.PrecisionLaser;

// Red Iron Compound
val redcomp = <ProjRed|Core:projectred.core.part:40>;

// Red Alloy Wire
val redwire = <ProjRed|Transmission:projectred.transmission.wire:0>;

// Remove Red Iron Compound
recipes.remove(redcomp);
//furnace.remove(<ProjRed|Core:projectred.core.part:10>, redcomp);
//NEI.hide(redcomp);

// Red Alloy wire
recipes.remove(redwire);
for ore in <ore:wireGt02RedAlloy>.items {
	Wiremill.addRecipe(redwire * 4, ore, 400, 2);
}

recipes.addShapeless(<gregtech:gt.blockmachines:2001>, [redwire, redwire, redwire, redwire]);

var dyes = [
	<ore:dyeBlack>,
	<ore:dyeRed>,
	<ore:dyeGreen>,
	<ore:dyeBrown>,
	<ore:dyeBlue>,
	<ore:dyePurple>,
	<ore:dyeCyan>,
	<ore:dyeLightGray>,
	<ore:dyeGray>,
	<ore:dyePink>,
	<ore:dyeLime>,
	<ore:dyeYellow>,
	<ore:dyeLightBlue>,
	<ore:dyeMagenta>,
	<ore:dyeOrange>,
	<ore:dyeWhite>
] as IOreDictEntry[];

var inswires = [
	<ProjRed|Transmission:projectred.transmission.wire:16>,
	<ProjRed|Transmission:projectred.transmission.wire:15>,
	<ProjRed|Transmission:projectred.transmission.wire:14>,
	<ProjRed|Transmission:projectred.transmission.wire:13>,
	<ProjRed|Transmission:projectred.transmission.wire:12>,
	<ProjRed|Transmission:projectred.transmission.wire:11>,
	<ProjRed|Transmission:projectred.transmission.wire:10>,
	<ProjRed|Transmission:projectred.transmission.wire:9>,
	<ProjRed|Transmission:projectred.transmission.wire:8>,
	<ProjRed|Transmission:projectred.transmission.wire:7>,
	<ProjRed|Transmission:projectred.transmission.wire:6>,
	<ProjRed|Transmission:projectred.transmission.wire:5>,
	<ProjRed|Transmission:projectred.transmission.wire:4>,
	<ProjRed|Transmission:projectred.transmission.wire:3>,
	<ProjRed|Transmission:projectred.transmission.wire:2>,
	<ProjRed|Transmission:projectred.transmission.wire:1>
] as IItemStack[];

// Insulated Red Alloy Wires
for i, dye in dyes {
	var iwire = inswires[i];
	recipes.remove(iwire);
	recipes.addShapeless(iwire * 2,[redwire, redwire, <ore:plateRubber>, dye]);
}

///////////////////////////////////////////////////////////// 2006, Alex Main
recipes.remove(<ProjRed|Core:projectred.core.part:1>);
recipes.remove(<ProjRed|Core:projectred.core.part:2>);
recipes.remove(<ProjRed|Core:projectred.core.part:3>);
recipes.remove(<ProjRed|Core:projectred.core.part:4>);
recipes.remove(<ProjRed|Core:projectred.core.part:5>);
recipes.remove(<ProjRed|Core:projectred.core.part:6>);
recipes.remove(<ProjRed|Core:projectred.core.part:7>);
recipes.remove(<ProjRed|Core:projectred.core.part:8>);
recipes.remove(<ProjRed|Core:projectred.core.part:9>);

// dyes
recipes.remove(<ProjRed|Core:projectred.core.part:19>);
recipes.remove(<ProjRed|Core:projectred.core.part:20>);
recipes.remove(<ProjRed|Core:projectred.core.part:21>);
recipes.remove(<ProjRed|Core:projectred.core.part:22>);
recipes.remove(<ProjRed|Core:projectred.core.part:23>);
recipes.remove(<ProjRed|Core:projectred.core.part:24>);
recipes.remove(<ProjRed|Core:projectred.core.part:25>);
recipes.remove(<ProjRed|Core:projectred.core.part:26>);
recipes.remove(<ProjRed|Core:projectred.core.part:27>);
recipes.remove(<ProjRed|Core:projectred.core.part:28>);
recipes.remove(<ProjRed|Core:projectred.core.part:29>);
recipes.remove(<ProjRed|Core:projectred.core.part:30>);
recipes.remove(<ProjRed|Core:projectred.core.part:31>);
recipes.remove(<ProjRed|Core:projectred.core.part:32>);
recipes.remove(<ProjRed|Core:projectred.core.part:33>);
recipes.remove(<ProjRed|Core:projectred.core.part:34>);

recipes.remove(<ProjRed|Core:projectred.core.part:42>);
recipes.remove(<ProjRed|Core:projectred.core.part:43>);
recipes.remove(<ProjRed|Core:projectred.core.part:57>);
recipes.remove(<ProjRed|Core:projectred.core.part:58>);

recipes.remove(<ProjRed|Core:projectred.core.datacard>);

furnace.remove(<ProjRed|Core:projectred.core.part:13>);
furnace.remove(<ProjRed|Core:projectred.core.part:14>);
furnace.remove(<ProjRed|Core:projectred.core.part:59>);

// circuit plate
var circuitPlate = <ProjRed|Core:projectred.core.part>;
furnace.remove(circuitPlate * 2);

// v1 - shapeless: gt empty circuit board -> circuit plate
//recipes.addShapeless(circuitPlate, [<gregtech:gt.metaitem.01:32719>]);
recipes.addShapeless(circuitPlate, [<gregtech:gt.metaitem.01:17947>]);

// v2 - chemical bath (): wet concrete (144 mb) + compressed wood plank = 2 circuit plate
//ChemicalBath.addRecipe([circuitPlate * 2], <gregtech:gt.metaitem.01:17809>, <liquid:molten.concrete> * 144, [10000], 200, 1);

// v3 - assembler: wet concrete (144 mb) + stone slab = 4 circuit plate
//Assembler.addRecipe(circuitPlate * 4, <minecraft:stone_slab>, <gregtech:gt.integrated_circuit:1> * 0, <liquid:molten.concrete> * 144, 400, 1);

// v4 - assembler: wet concrete (144 mb) + integrated circuit#21 = 1 circuit plate
// NOT ALLOWED
// Assembler.addRecipe(circuitPlate, null, <gregtech:gt.integrated_circuit:21> * 0, <liquid:molten.concrete> * 144, 100, 1);

// v5 - FormingPress: pressure plate + mold (plate) = 1 circuit plate
//FormingPress.addRecipe(circuitPlate, <minecraft:stone_pressure_plate>, <gregtech:gt.metaitem.01:32301> * 0, 50, 2);


// Conductive Plate
Assembler.addRecipe(<ProjRed|Core:projectred.core.part:1>, circuitPlate, <gregtech:gt.integrated_circuit:1> * 0, <liquid:molten.redstone> * 144, 200, 16);

// Wired Plate
Assembler.addRecipe(<ProjRed|Core:projectred.core.part:2>, circuitPlate, <gregtech:gt.blockmachines:2000>, 300, 30);

// Bundled Plate - gt machines not accept oredict things
for iWire in <ore:projredBundledCable>.items {
	Assembler.addRecipe(<ProjRed|Core:projectred.core.part:3>, circuitPlate, iWire, 400, 30);
}

// Anode
Assembler.addRecipe(<ProjRed|Core:projectred.core.part:4>, circuitPlate, <minecraft:redstone> * 3, 400, 30);

// Cathode
Assembler.addRecipe(<ProjRed|Core:projectred.core.part:5>, circuitPlate, <minecraft:redstone_torch>, 400, 30);

// Pointer
//Assembler.addRecipe(<ProjRed|Core:projectred.core.part:6> ,<ProjRed|Core:projectred.core.part:5>, <gregtech:gt.metaitem.01:24502>, 500, 30);
//PrecisionLaser.addRecipe(<ProjRed|Core:projectred.core.part:6>, <gregtech:gt.metaitem.01:24502> * 0, <ProjRed|Core:projectred.core.part:5>, 500, 30);

// Silicon Chip
FormingPress.addRecipe(<ProjRed|Core:projectred.core.part:7>, circuitPlate, <ProjRed|Core:projectred.core.part:13>, 600, 30);

// Energized Silicon Chip
FormingPress.addRecipe(<ProjRed|Core:projectred.core.part:8>, circuitPlate, <ProjRed|Core:projectred.core.part:14>, 600, 30);

// Platformed Plate
Assembler.addRecipe(<ProjRed|Core:projectred.core.part:9>, <ProjRed|Core:projectred.core.part:2> * 4, <gregtech:gt.metaitem.01:23874> * 4, 300, 64);

// Infused Silicon - in ball mold
FormingPress.addRecipe(<ProjRed|Core:projectred.core.part:13>, <ProjRed|Core:projectred.core.part:42>, <gregtech:gt.metaitem.01:32307> * 0, 600, 30);

// Energized Silicon - in ball mold
FormingPress.addRecipe(<ProjRed|Core:projectred.core.part:14>, <ProjRed|Core:projectred.core.part:43>, <gregtech:gt.metaitem.01:32307> * 0, 600, 30);


// Infused Silicon Compound = redstone + silicon
AlloySmelter.addRecipe(<ProjRed|Core:projectred.core.part:42>, <gregtech:gt.metaitem.01:2020>, <minecraft:redstone> * 8, 400, 30);

// Glowing Silicon Compound = glowstone + silicon
AlloySmelter.addRecipe(<ProjRed|Core:projectred.core.part:43>, <gregtech:gt.metaitem.01:2020>, <minecraft:glowstone_dust> * 8, 400, 30);

// Electrotine Dust = gold silver redstone
Mixer.addRecipe(<ProjRed|Core:projectred.core.part:56> * 3, [<gregtech:gt.metaitem.01:2303>, <minecraft:redstone>], 300, 8);

// Electrotine Iron Compound
AlloySmelter.addRecipe(<ProjRed|Core:projectred.core.part:57>, <minecraft:iron_ingot>, <ProjRed|Core:projectred.core.part:56> * 8, 400, 30);

// Electrotine Silicon Compound
AlloySmelter.addRecipe(<ProjRed|Core:projectred.core.part:58>, <gregtech:gt.metaitem.01:2020>, <ProjRed|Core:projectred.core.part:56> * 8, 400, 30);

// Data Card
Assembler.addRecipe(<ProjRed|Core:projectred.core.datacard>, <gregtech:gt.metaitem.01:20879>, <gregtech:gt.integrated_circuit:1> * 0, <liquid:molten.redstone> * 288, 200, 16);

// Electrotine Silicon Compound - in ball mold
FormingPress.addRecipe(<ProjRed|Core:projectred.core.part:59>, <ProjRed|Core:projectred.core.part:58>, <gregtech:gt.metaitem.01:32307> * 0, 600, 30);


// Example of Orange Illumar
//ChemicalBath.addRecipe([<ProjRed|Core:projectred.core.part:20>], whiteIllumar, <liquid:dye.chemical.dyeorange> * 50, [10000], 100, 8);
//ChemicalBath.addRecipe([whiteIllumar], <ProjRed|Core:projectred.core.part:20>, <liquid:chlorine> * 50, [10000], 800, 2);

var whiteIllumar = <ProjRed|Core:projectred.core.part:19>;

Mixer.addRecipe(whiteIllumar, [<minecraft:glowstone_dust> * 2, <minecraft:dye:15> * 2], 50, 8);
Mixer.addRecipe(whiteIllumar, null, [<minecraft:glowstone_dust> * 2], <liquid:dye.chemical.dyewhite> * 50, 50, 8);

var Illumare = [
	<ProjRed|Core:projectred.core.part:20>,
	<ProjRed|Core:projectred.core.part:21>,
	<ProjRed|Core:projectred.core.part:22>,
	<ProjRed|Core:projectred.core.part:23>,
	<ProjRed|Core:projectred.core.part:24>,
	<ProjRed|Core:projectred.core.part:25>,
	<ProjRed|Core:projectred.core.part:26>,
	<ProjRed|Core:projectred.core.part:27>,
	<ProjRed|Core:projectred.core.part:28>,
	<ProjRed|Core:projectred.core.part:29>,
	<ProjRed|Core:projectred.core.part:30>,
	<ProjRed|Core:projectred.core.part:31>,
	<ProjRed|Core:projectred.core.part:32>,
	<ProjRed|Core:projectred.core.part:33>,
	<ProjRed|Core:projectred.core.part:34>
] as IItemStack[];

var liquidDye = [
	<liquid:dye.chemical.dyeorange>,
	<liquid:dye.chemical.dyemagenta>,
	<liquid:dye.chemical.dyelightblue>,
	<liquid:dye.chemical.dyeyellow>,
	<liquid:dye.chemical.dyelime>,
	<liquid:dye.chemical.dyepink>,
	<liquid:dye.chemical.dyegray>,
	<liquid:dye.chemical.dyelightgray>,
	<liquid:dye.chemical.dyecyan>,
	<liquid:dye.chemical.dyepurple>,
	<liquid:dye.chemical.dyeblue>,
	<liquid:dye.chemical.dyebrown>,
	<liquid:dye.chemical.dyegreen>,
	<liquid:dye.chemical.dyered>,
	<liquid:dye.chemical.dyeblack>
] as minetweaker.liquid.ILiquidStack[];

for i, dye in liquidDye {
	ChemicalBath.addRecipe([Illumare[i]], whiteIllumar, dye * 50, [10000], 100, 8);
	ChemicalBath.addRecipe([whiteIllumar], Illumare[i], <liquid:chlorine> * 50, [10000], 800, 2);
}

//IC Chips
var gtCraftingTableCover = <gregtech:gt.metaitem.01:32744>;
var ftWorktable = <Forestry:factory2:2>;
var prICWorkbench = <ProjRed|Fabrication:projectred.integration.icblock>;
var prICPrinter = <ProjRed|Fabrication:projectred.integration.icblock:1>;
var prICChip = <ProjRed|Fabrication:projectred.fabrication.icchip>;
var prScrewdriver = <ProjRed|Core:projectred.core.screwdriver>;
var gtEmitterLV = <gregtech:gt.metaitem.01:32680>;
var gtComputerMonitor = <gregtech:gt.metaitem.01:32740>;
var gtAssemblerLV = <gregtech:gt.blockmachines:211>;
var gtScrewdriver = <gregtech:gt.metatool.01:22>;

recipes.remove(prICWorkbench);
recipes.addShaped(prICWorkbench, [[<ore:plateIron>, gtCraftingTableCover, <ore:plateIron>],
		   		  [<ore:plateWood>, ftWorktable, <ore:plateWood>],
		   		  [<ore:plateWood>, <ore:plateWood>, <ore:plateWood>]]);

recipes.remove(prICPrinter);
recipes.addShaped(prICPrinter, [[gtEmitterLV, gtComputerMonitor, gtEmitterLV],
		   		  [<ore:craftingLensRed>, gtAssemblerLV, <ore:craftingLensRed>],
		   		  [<ore:plateSteel>, ftWorktable, <ore:plateSteel>]]);

recipes.remove(prICChip);
//Assembler.addRecipe(prICChip, <ore:circuitGood>.firstItem * 4, <ore:circuitPrimitive>.firstItem * 16, <liquid:molten.solderingalloy> * 576, 400, 30);
//Assembler.addRecipe(prICChip, <ore:circuitGood> * 4, <ore:circuitPrimitive> * 16, <liquid:molten.solderingalloy> * 576, 400, 30);
Assembler.addRecipe(prICChip, <ore:circuitGood> * 4, <ore:circuitBasic> * 8, <liquid:molten.solderingalloy> * 576, 400, 30);

recipes.remove(prScrewdriver);
recipes.addShaped(prScrewdriver, [[null, <ore:craftingToolFile>, <ore:stickIron>],
		   		  [<ore:dyeBlue>, <ore:stickIron>, <ore:craftingToolHardHammer>],
		   		  [<ore:stickWood>, <ore:dyeBlue>, null]]);

recipes.addShapeless(<ProjRed|Fabrication:projectred.fabrication.icchip>, [<ProjRed|Integration:projectred.integration.gate:34>]);