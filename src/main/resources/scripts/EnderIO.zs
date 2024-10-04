import mods.nei.NEI;
import mods.gregtech.AlloySmelter;
import mods.gregtech.ChemicalBath;
import mods.gregtech.Assembler;
import mods.gregtech.Autoclave;
import mods.gregtech.BlastFurnace;
import mods.gregtech.ChemicalReactor;
import mods.gregtech.FluidSolidifier;
import mods.gregtech.FluidExtractor;

var clearGlass = <EnderIO:blockFusedQuartz:1>;
var clearGlassEnlighten = <EnderIO:blockFusedQuartz:3>;
var conduitBinder = <EnderIO:itemMaterial:1>;
var crystalPulsating = <EnderIO:itemMaterial:5>;
var crystalVibrant = <EnderIO:itemMaterial:6>;
var enderioSilicon = <EnderIO:itemMaterial>;
var fusedQuartz = <EnderIO:blockFusedQuartz>;
var fusedQuartzEnlighten = <EnderIO:blockFusedQuartz:2>;
var gearBasic = <EnderIO:itemMachinePart:1>;
var ingotPulsatingIron = <EnderIO:itemAlloy:5>;
var itemDustDiamond = <gregtech:gt.metaitem.01:2500>;
var itemDustEmerald = <gregtech:gt.metaitem.01:2501>;
var itemDustEnderium = <gregtech:gt.metaitem.01:2321>;
var itemDustEnderPearl = <gregtech:gt.metaitem.01:2532>;
var itemDustPlatinum = <gregtech:gt.metaitem.01:2085>;
var itemDustSilver = <gregtech:gt.metaitem.01:2054>;
var itemRodDarkSteel = <gregtech:gt.metaitem.01:23364>;
var itemDustGlass = <gregtech:gt.metaitem.01:2890>;
var itemDustCertusQuartz = <gregtech:gt.metaitem.01:2516>;
var itemDustNetherQuartz = <gregtech:gt.metaitem.01:2522>;
var itemDustQuartzite = <gregtech:gt.metaitem.01:2523>;
var robotarmMV = <gregtech:gt.metaitem.01:32651>;
var machineChassis = <EnderIO:itemMachinePart>;
var moldBall = <gregtech:gt.metaitem.01:32307>;
var moltenEnderiumBase = <liquid:molten.enderiumbase>;
var moltenVibrantAlloy = <liquid:molten.vibrantalloy>;
var moltenPulsatingIron = <liquid:molten.pulsatingiron>;
var pulsatingCrystal = <EnderIO:itemMaterial:5>;
var tankFluid = <EnderIO:blockTank>;
var tankFluidPressurized = <EnderIO:blockTank:1>;
var craftingCover = <gregtech:gt.metaitem.01:32744>;
var chestIron = <IronChest:BlockIronChest:0>;
var conveyorMV = <gregtech:gt.metaitem.01:32631>;
var pumpMV = <gregtech:gt.metaitem.01:32611>;
var fieldGeneratorMV = <gregtech:gt.metaitem.01:32671>;
var fieldGeneratorHV = <gregtech:gt.metaitem.01:32672>;
var motorMV = <gregtech:gt.metaitem.01:32601>;
var emitterIV = <gregtech:gt.metaitem.01:32684>; 

<ore:itemSilicon>.remove(enderioSilicon);
<ore:gearStone>.remove(gearBasic);

recipes.remove(gearBasic);
recipes.remove(tankFluid);
recipes.remove(tankFluidPressurized);
recipes.remove(crystalPulsating);
recipes.remove(<EnderIO:itemGliderWing>);
recipes.remove(<EnderIO:itemGliderWing:1>);
recipes.remove(<EnderIO:itemTravelStaff:16>);
recipes.remove(<EnderIO:blockKillerJoe>);
recipes.remove(<EnderIO:blockEnderRail>);
recipes.remove(<EnderIO:blockEnderIo>);
recipes.remove(<EnderIO:blockAlloySmelter>);
recipes.remove(<EnderIO:blockSagMill>);
recipes.remove(<EnderIO:itemPowderIngot:*>); // Powder
recipes.remove(<EnderIO:blockTransceiver>);
recipes.remove(<EnderIO:blockPowerMonitor>);
recipes.remove(<EnderIO:itemMaterial:2>); // binderComposite
recipes.remove(<EnderIO:blockVat>);
recipes.remove(<EnderIO:blockWirelessCharger>);
recipes.remove(<EnderIO:blockZombieGenerator>);
recipes.remove(<EnderIO:blockSolarPanel>);
recipes.remove(<EnderIO:blockSolarPanel:1>);
recipes.remove(<EnderIO:itemEnderFood>);
recipes.remove(<EnderIO:blockSpawnGuard>);
recipes.remove(<EnderIO:blockWeatherObelisk>);
recipes.remove(<EnderIO:blockTelePad>);
recipes.remove(<EnderIO:itemCoordSelector>);
recipes.remove(<EnderIO:blockBuffer:1>);
recipes.remove(<EnderIO:blockBuffer:2>);
recipes.remove(<EnderIO:blockBuffer:3>);
recipes.remove(<EnderIO:blockEnchanter>);
recipes.remove(<EnderIO:blockCapBank:2>); // capacitor 2
recipes.remove(<EnderIO:blockCapBank:3>); // capacitor 3
recipes.remove(<EnderIO:blockCombustionGenerator>);
recipes.remove(<EnderIO:blockStirlingGenerator>);
recipes.remove(<EnderIO:blockElectricLight>);

recipes.removeShapeless(<EnderIO:itemMaterial:3> * 9, [<EnderIO:itemAlloy:5>]);
recipes.removeShapeless(<EnderIO:itemMaterial:4> * 9, [<EnderIO:itemAlloy:2>]);

NEI.hide(<EnderIO:itemGliderWing>);
NEI.hide(<EnderIO:itemGliderWing:1>);
NEI.hide(tankFluid);
NEI.hide(tankFluidPressurized);
NEI.hide(enderioSilicon);
NEI.hide(<EnderIO:blockKillerJoe>);
NEI.hide(<EnderIO:blockEnderRail>);
NEI.hide(<EnderIO:blockEnderIo>);
NEI.hide(<EnderIO:blockAlloySmelter>);
NEI.hide(<EnderIO:blockSagMill>);
NEI.hide(<EnderIO:itemPowderIngot>);
NEI.hide(<EnderIO:blockTransceiver>);
NEI.hide(<EnderIO:blockPowerMonitor>);
NEI.hide(<EnderIO:blockVat>);
NEI.hide(<EnderIO:blockWirelessCharger>);
NEI.hide(<EnderIO:blockStirlingGenerator>);
NEI.hide(<EnderIO:blockCombustionGenerator>);
NEI.hide(<EnderIO:blockZombieGenerator>);
NEI.hide(<EnderIO:blockSolarPanel>);
NEI.hide(<EnderIO:blockSolarPanel:1>);
NEI.hide(<EnderIO:itemEnderFood>);
NEI.hide(<EnderIO:blockSpawnGuard>);
NEI.hide(<EnderIO:blockWeatherObelisk>);
NEI.hide(<EnderIO:blockTelePad>);
NEI.hide(<EnderIO:itemCoordSelector>);
NEI.hide(<EnderIO:blockBuffer>);
NEI.hide(<EnderIO:blockBuffer:1>);
NEI.hide(<EnderIO:blockBuffer:2>);
NEI.hide(<EnderIO:blockBuffer:3>);

furnace.remove(conduitBinder);

recipes.remove(<EnderIO:item.darkSteel_helmet>);
recipes.remove(<EnderIO:item.darkSteel_chestplate>);
recipes.remove(<EnderIO:item.darkSteel_leggings>);
recipes.remove(<EnderIO:item.darkSteel_boots>);
recipes.remove(<EnderIO:item.darkSteel_sword>);
recipes.remove(<EnderIO:item.darkSteel_pickaxe>);
recipes.remove(<EnderIO:item.darkSteel_axe>);
recipes.remove(<EnderIO:item.darkSteel_shears>);
NEI.hide(<EnderIO:item.darkSteel_helmet:*>);
NEI.hide(<EnderIO:item.darkSteel_chestplate:*>);
NEI.hide(<EnderIO:item.darkSteel_leggings:*>);
NEI.hide(<EnderIO:item.darkSteel_boots:*>);
NEI.hide(<EnderIO:item.darkSteel_sword:*>);
NEI.hide(<EnderIO:item.darkSteel_pickaxe:*>);
NEI.hide(<EnderIO:item.darkSteel_axe:*>);
NEI.hide(<EnderIO:item.darkSteel_shears:*>);

// do not remove this line <ore:plateVibrantAlloy>

recipes.remove(<EnderIO:itemMaterial:7>); // darksteel ball
recipes.addShaped(<EnderIO:itemMaterial:7> * 5, [
	[null, <ore:plateDarkSteel>, null],
	[<ore:plateDarkSteel>, <ore:plateDarkSteel>, <ore:plateDarkSteel>],
	[null, <ore:plateDarkSteel>, null]]);

recipes.remove(<EnderIO:blockReinforcedObsidian>);
recipes.addShaped(<EnderIO:blockReinforcedObsidian>, [
	[<ore:plateDarkSteel>, <EnderIO:blockDarkIronBars>, <ore:plateDarkSteel>],
	[<EnderIO:blockDarkIronBars>, <minecraft:obsidian>, <EnderIO:blockDarkIronBars>],
	[<ore:plateDarkSteel>, <EnderIO:blockDarkIronBars>, <ore:plateDarkSteel>]]);

recipes.remove(<EnderIO:blockDarkIronBars>);
recipes.addShaped(<EnderIO:blockDarkIronBars> * 8, [
	[null, <ore:craftingToolWrench>, null],
	[<ore:stickDarkSteel>, <ore:stickDarkSteel>, <ore:stickDarkSteel>],
	[<ore:stickDarkSteel>, <ore:stickDarkSteel>, <ore:stickDarkSteel>]]);
Assembler.addRecipe(<EnderIO:blockDarkIronBars> * 4, itemRodDarkSteel * 3, <gregtech:gt.integrated_circuit:3> * 0, 300, 4);
NEI.overrideName(<EnderIO:blockDarkIronBars>, "Dark Steel Bars");

recipes.remove(<EnderIO:blockDarkSteelPressurePlate>);
recipes.addShaped(<EnderIO:blockDarkSteelPressurePlate>, [[<ore:plateDarkSteel>, <ore:plateDarkSteel>, <ore:craftingToolHardHammer>]]);

recipes.remove(<EnderIO:blockVacuumChest>);
recipes.addShaped(<EnderIO:blockVacuumChest>, [
    [<ore:plateTungstenSteel>,	crystalPulsating,  <ore:plateTungstenSteel>],
    [<ore:plateObsidian>, 	<minecraft:chest>, <ore:plateObsidian>],
    [<ore:plateTungstenSteel>,	crystalPulsating,  <ore:plateTungstenSteel>]]);

recipes.remove(<EnderIO:itemConduitProbe>);
recipes.addShaped(<EnderIO:itemConduitProbe>, [
	[<ore:plateElectricalSteel>, <ore:wireCopper>, <ore:plateElectricalSteel>],
	[<minecraft:glass_pane>, <minecraft:comparator>, <minecraft:glass_pane>],
	[<ore:plateSilicon>, <EnderIO:itemRedstoneConduit:2>, <ore:plateSilicon>]]);

recipes.remove(<EnderIO:blockBuffer>);
recipes.addShaped(<EnderIO:blockBuffer>, [
	[<ore:plateIron>, <ore:plateSteel>, <ore:plateIron>],
	[<ore:plateSteel>, <minecraft:chest>, <ore:plateSteel>],
	[<ore:plateIron>, <ore:plateSteel>, <ore:plateIron>]]);

recipes.remove(<EnderIO:itemYetaWrench>);
recipes.addShaped(<EnderIO:itemYetaWrench>, [
    [<ore:ingotPulsatingIron>, <ore:craftingToolHardHammer>, <ore:ingotPulsatingIron>],
    [null, <BuildCraft|Core:wrenchItem>, null],
    [null, <ore:ingotPulsatingIron>, null]]);

recipes.remove(<EnderIO:blockTravelAnchor>);
recipes.addShaped(<EnderIO:blockTravelAnchor>, [
	[<ore:plateElectricalSteel>, conduitBinder, <ore:plateElectricalSteel>],
	[conduitBinder, pulsatingCrystal, conduitBinder],
	[<ore:plateElectricalSteel>, conduitBinder, <ore:plateElectricalSteel>]]);

recipes.remove(<EnderIO:blockReservoir>);
/*
recipes.addShaped(<EnderIO:blockReservoir> * 2, [
    [fusedQuartz, fusedQuartz, fusedQuartz],
    [fusedQuartz, <gregtech:gt.metaitem.01:32610>, fusedQuartz],
    [fusedQuartz, fusedQuartz, fusedQuartz]]);
*/

recipes.remove(<EnderIO:itemMagnet:16>);
recipes.addShaped(<EnderIO:itemMagnet:16> , [
    [<ore:plateNeodymiumMagnetic>, <ore:plateConductiveIron>, <ore:plateConductiveIron>],
    [null, null, crystalVibrant],
    [<ore:plateNeodymiumMagnetic>, <ore:plateConductiveIron>, <ore:plateConductiveIron>]]);

recipes.remove(<EnderIO:itemBasicCapacitor>);
Assembler.addRecipe(<EnderIO:itemBasicCapacitor>, <gregtech:gt.metaitem.01:32500>, <minecraft:gold_ingot> * 4, <liquid:molten.redstone> * 288, 64, 8); // uses gt battery hull small

recipes.remove(<EnderIO:itemBasicCapacitor:1>); // capacitor DualLayer
Assembler.addRecipe(<EnderIO:itemBasicCapacitor:1>, <EnderIO:itemBasicCapacitor> * 2, <EnderIO:itemMaterial:5> , <liquid:molten.glowstone> * 288, 500, 120);

recipes.remove(<EnderIO:itemBasicCapacitor:2>); // capacitor Octadic
Assembler.addRecipe(<EnderIO:itemBasicCapacitor:2>, <EnderIO:itemBasicCapacitor:1> * 2, <EnderIO:itemMaterial:6>, <liquid:molten.energeticalloy> * 288, 500, 480);

// Fused Quartz
AlloySmelter.addRecipe(fusedQuartz, itemDustGlass, itemDustCertusQuartz * 4, 100, 16);
AlloySmelter.addRecipe(fusedQuartz, itemDustGlass, itemDustNetherQuartz * 4, 100, 16);
AlloySmelter.addRecipe(fusedQuartz, itemDustGlass, itemDustQuartzite * 8, 100, 16);

recipes.remove(crystalVibrant);
Autoclave.addRecipe(crystalVibrant, itemDustEmerald, moltenVibrantAlloy * 144, 8500, 1000, 24);
Autoclave.addRecipe(crystalPulsating, itemDustDiamond, moltenPulsatingIron * 144, 8500, 1000, 24);

ChemicalBath.addRecipe([fusedQuartzEnlighten], fusedQuartz, <liquid:molten.glowstone> * 576, [10000], 100, 8);
ChemicalBath.addRecipe([clearGlassEnlighten], clearGlass, <liquid:molten.glowstone> * 576, [10000], 100, 4);
ChemicalBath.addRecipe([clearGlass], <minecraft:glass>, <liquid:chlorine> * 50, [10000], 400, 2);

ChemicalReactor.addRecipe(itemDustEnderium, null, itemDustEnderPearl, null, moltenEnderiumBase * 144, 200);
ChemicalReactor.addRecipe(null, moltenEnderiumBase * 576, itemDustSilver, itemDustPlatinum, <liquid:molten.tin> * 288, 100);

FluidExtractor.addRecipe(null, conduitBinder, <liquid:molten.concrete> * 36, 10000, 24, 24);

FluidSolidifier.addRecipe(conduitBinder, moldBall * 0, <liquid:molten.concrete> * 36, 20, 4);

recipes.remove(<EnderIO:itemRedstoneConduit>);
recipes.remove(<EnderIO:itemRedstoneConduit:1>);
recipes.remove(<EnderIO:itemRedstoneConduit:2>);
recipes.remove(<EnderIO:itemPowerConduit>);
recipes.remove(<EnderIO:itemPowerConduit:1>);
recipes.remove(<EnderIO:itemPowerConduit:2>);
recipes.remove(<EnderIO:itemItemConduit>);
recipes.remove(<EnderIO:itemLiquidConduit>);
recipes.remove(<EnderIO:itemLiquidConduit:1>);
recipes.remove(<EnderIO:itemLiquidConduit:2>);
recipes.remove(<EnderIO:itemMEConduit>);
recipes.remove(<EnderIO:itemMEConduit:1>);

Assembler.addRecipe(<EnderIO:itemRedstoneConduit>, <gregtech:gt.blockmachines:5170>, <ore:plateRedstoneAlloy>, 50, 30);
Assembler.addRecipe(<EnderIO:itemRedstoneConduit:1>, <EnderIO:itemRedstoneConduit:2>, <minecraft:lever>, 50, 60);
Assembler.addRecipe(<EnderIO:itemRedstoneConduit:2>, <EnderIO:itemRedstoneConduit>, <EnderIO:itemMaterial:1> * 4, 100, 120); 

Assembler.addRecipe(<EnderIO:itemPowerConduit>, <gregtech:gt.blockmachines:1380>, <EnderIO:itemMaterial:1> * 4, <liquid:molten.conductiveiron> * 144, 50, 30);
Assembler.addRecipe(<EnderIO:itemItemConduit>, <gregtech:gt.blockmachines:5612>, <EnderIO:itemMaterial:1> * 4, <liquid:molten.pulsatingiron> * 16, 20, 120);

Assembler.addRecipe(<EnderIO:itemLiquidConduit>, <gregtech:gt.blockmachines:5122>, <minecraft:quartz>, <liquid:molten.glass> * 144, 100, 30);
Assembler.addRecipe(<EnderIO:itemLiquidConduit:1>, <gregtech:gt.blockmachines:5132>, <EnderIO:itemMaterial:1> * 4, <liquid:molten.vibrantalloy> * 16, 100, 120);
Assembler.addRecipe(<EnderIO:itemLiquidConduit:2>, <gregtech:gt.blockmachines:5142>, <EnderIO:blockFusedQuartz:1>, <liquid:molten.enderium> * 16, 100, 480);

Assembler.addRecipe(<EnderIO:itemMEConduit>, <appliedenergistics2:item.ItemMultiPart:16>, <EnderIO:itemMaterial:1> * 4, 50, 120);
Assembler.addRecipe(<EnderIO:itemMEConduit:1>, <appliedenergistics2:item.ItemMultiPart:76>, <EnderIO:itemMaterial:1> * 16, 100, 480);

// more simple lights
recipes.remove(<EnderIO:blockElectricLight:2>);
recipes.addShaped(<EnderIO:blockElectricLight:2>,
	[[null, null, null],
	[null, fusedQuartz, null],
	[<ore:plateIron>, <ore:glowstone>, <ore:plateIron>]]);
recipes.addShaped(<EnderIO:blockElectricLight>,
	[[null, null, null],
	[null, fusedQuartz, null],
	[<ore:plateIron>, <ore:springSmallRedAlloy>, <ore:plateIron>]]);
recipes.addShaped(<EnderIO:blockElectricLight> * 2, // alternate recipe
	[[null, null, null],
	[null, fusedQuartz, null],
	[<ore:plateIron>, <ore:springMolybdenum>, <ore:plateIron>]]);

// Plates
recipes.remove(<EnderIO:itemXpTransfer>);
recipes.addShaped(<EnderIO:itemXpTransfer>,
	[[null, <ore:craftingToolFile>, <ore:plateSoularium>],
	[null, <ore:plateEnergeticAlloy>, null],
	[<ore:plateSoularium>, null, null]]);

recipes.remove(<EnderIO:blockPoweredSpawner>); // Spawner
recipes.addShaped(<EnderIO:blockPoweredSpawner>, [
    [<ore:plateElectricalSteel>, emitterIV, <ore:plateElectricalSteel>],
    [<ore:plateElectricalSteel>, machineChassis, <ore:plateElectricalSteel>],
    [<ore:itemVibrantCrystal>, <ore:circuitElite>, <ore:itemVibrantCrystal>]]);

recipes.remove(<EnderIO:blockInventoryPanel>);
//recipes.addShaped(<EnderIO:blockInventoryPanel>, [
//    [<ore:plateDarkSteel>, <EnderIO:itemFunctionUpgrade>, <ore:plateDarkSteel>],
//    [<ore:itemPulsatingCrystal>, <EnderIO:itemFrankenSkull:4>, <ore:itemPulsatingCrystal>],
//    [<ore:plateDarkSteel>, tankFluid /*disabled item*/, <ore:plateDarkSteel>]]);

recipes.remove(<EnderIO:blockCapBank:1>);
recipes.addShaped(<EnderIO:blockCapBank:1>, [
    [<ore:wireGt02Iron>, <EnderIO:itemBasicCapacitor>, <ore:wireGt02Iron>],
    [<EnderIO:itemBasicCapacitor>, <ore:batteryElite> /*IC2:itemBatCrystal:26*/, <EnderIO:itemBasicCapacitor>],
    [<ore:wireGt02Iron>, <EnderIO:itemBasicCapacitor>, <ore:wireGt02Iron>]]);

recipes.remove(<EnderIO:itemBasicFilterUpgrade:1>);
Assembler.addRecipe(<EnderIO:itemBasicFilterUpgrade:1> * 4, <EnderIO:itemBasicFilterUpgrade> * 4, <gregtech:gt.metaitem.01:32729>, 200, 64);

recipes.remove(<EnderIO:itemFunctionUpgrade>);
recipes.remove(<EnderIO:itemExtractSpeedUpgrade>);
recipes.remove(<EnderIO:itemExtractSpeedUpgrade:1>);
Assembler.addRecipe(<EnderIO:itemFunctionUpgrade>, [<ore:plateConductiveIron>, <ore:plateEnderEye>, <ore:circuitBasic>], null, 100, 64);
Assembler.addRecipe(<EnderIO:itemExtractSpeedUpgrade>, [<ore:plateElectricalSteel>, <ore:plateAluminium>, <ore:circuitBasic>], null, 100, 64);
Assembler.addRecipe(<EnderIO:itemExtractSpeedUpgrade:1>, [<ore:plateElectricalSteel>, <ore:plateRedAlloy>, <ore:circuitBasic>], null, 100, 64);

recipes.remove(<EnderIO:itemSoulVessel>);
ChemicalBath.addRecipe([<EnderIO:itemSoulVessel>], <minecraft:glass_bottle>, <liquid:molten.soularium> * 144, [10000], 100, 8);

// No mob's heads
recipes.remove(<EnderIO:blockSliceAndSplice>);
recipes.addShaped(<EnderIO:blockSliceAndSplice>, [
    [<ore:wireCopper>, motorMV, <ore:wireCopper>],
    [<ore:circuitGood>, machineChassis, <ore:circuitGood>],
    [<ore:plateSoularium>, <ore:plateSoularium>, <ore:plateSoularium>]]);

recipes.remove(<EnderIO:blockSoulBinder>); // Soul Binder
recipes.addShaped(<EnderIO:blockSoulBinder>, [
    [<ore:plateSoularium>, fieldGeneratorHV, <ore:plateSoularium>],
    [<ore:circuitElite>, machineChassis, <ore:circuitElite>],
    [<ore:plateSoularium>, <ore:nuggetPlutonium241> /*some radioactive element*/, <ore:plateSoularium>]]);

//recipes.addShaped(<EnderIO:blockKillerJoe>, [
//	[<ore:stickDarkSteel>, robotarmMV, <ore:stickDarkSteel>],
//	[<ore:wireCopper>, machineChassis, <ore:wireCopper>],
//	[<ore:circuitGood>, fusedQuartz, <ore:circuitGood>]]);


// Dark steel for machine chassi
recipes.remove(machineChassis);
recipes.addShaped(machineChassis, [
	[<ore:plateDarkSteel>, <ore:craftingToolHardHammer>, <ore:plateDarkSteel>],
	[<ore:plateDarkSteel>, <ore:frameGtDarkSteel>,       <ore:plateDarkSteel>],
	[<ore:plateDarkSteel>, <ore:craftingToolWrench>,     <ore:plateDarkSteel>]]);
Assembler.addRecipe(machineChassis, [<ore:plateDarkSteel> * 6, <ore:frameGtDarkSteel>], null, 50, 16);

// Industrial parts for EnderIO
recipes.remove(<EnderIO:blockPainter>);
recipes.addShaped(<EnderIO:blockPainter>, [
	[<ore:glass>, <ore:circuitGood>, <ore:glass>],
	[conveyorMV, machineChassis, pumpMV],
	[<ore:wireCopper>, <ore:circuitGood>, <ore:wireCopper>]]);

recipes.remove(<EnderIO:blockCrafter>);
recipes.addShaped(<EnderIO:blockCrafter>, [
	[robotarmMV, <ore:circuitGood>, robotarmMV],
	[craftingCover, machineChassis, conveyorMV],
	[<ore:wireCopper>, <ore:circuitGood>, <ore:wireCopper>]]);

recipes.remove(<EnderIO:blockFarmStation>);
recipes.addShaped(<EnderIO:blockFarmStation>, [
	[<ore:plateDarkSteel>, robotarmMV, <ore:plateDarkSteel>],
	[crystalPulsating, machineChassis, crystalPulsating],
	[<ore:plateDarkSteel>, <ore:circuitGood>, <ore:plateDarkSteel>]]);

recipes.remove(<EnderIO:blockExperienceObelisk>);
recipes.addShaped(<EnderIO:blockExperienceObelisk>,
	[[null, <EnderIO:itemXpTransfer>, null],
	[null, fieldGeneratorMV, null],
	[<ore:plateSoularium>, machineChassis, <ore:plateSoularium>]]);

recipes.remove(<EnderIO:blockAttractor>);
recipes.addShaped(<EnderIO:blockAttractor>,
	[[null, <ore:itemAttractorCrystal>, null],
	[<ore:plateEnergeticAlloy>, fieldGeneratorMV, <ore:plateEnergeticAlloy>],
	[<ore:plateSoularium>, machineChassis, <ore:plateSoularium>]]);

Assembler.addRecipe(<EnderIO:itemConduitFacade>, conduitBinder * 8, <gregtech:gt.integrated_circuit:8> * 0, 16, 8);