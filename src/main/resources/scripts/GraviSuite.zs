import mods.gregtech.Assembler;

# Aliases
var advancedElectricJetpack = <GraviSuite:advJetpack:27>.withTag({charge: 0});
var advancedElectricJetpack2 = <GraviSuite:advJetpack:*>;
var advancedLappack = <GraviSuite:advLappack:*>;
var advancedNanoChestPlate = <GraviSuite:advNanoChestPlate:27>.withTag({charge: 0});
var advancedNanoChestPlate2 = <GraviSuite:advNanoChestPlate:*>;
var cablePlatinum = <gregtech:gt.blockmachines:1646>;
var carbonPlate = <IC2:itemPartCarbonPlate>;
var circuitAdvanced = <ore:circuitAdvanced>;
var electricJetpack = <IC2:itemArmorJetpackElectric:*>;
var engineBooster = <GraviSuite:itemSimpleItem:6>;
var ingotGold = <minecraft:gold_ingot>;
var nanoBodyArmor = <IC2:itemArmorNanoChestplate:*>;
var superconductor = <GraviSuite:itemSimpleItem:1>;
var superconductorCover = <GraviSuite:itemSimpleItem>;
var coolingCore = <GraviSuite:itemSimpleItem:2>;
var cell60k = <IC2:reactorCoolantSix:1>;
var advHeatExchanger = <IC2:reactorHeatSwitchDiamond:1>;
var heatReactorPlating = <IC2:reactorPlatingHeat>;
var plateAlloyIridium = <ore:plateAlloyIridium>;
var TransformerZPM = <gregtech:gt.blockmachines:27>;
var GraviChest = <GraviSuite:graviChestPlate:27>.withTag({charge: 0});
var GraviChest2 = <GraviSuite:graviChestPlate:*>;
var QuantumChest = <IC2:itemArmorQuantumChestplate:*>;
var GravitationEngine = <GraviSuite:itemSimpleItem:3>;
var UltLappack = <GraviSuite:ultimateLappack:*>;
var SuperconductorGT = <ore:wireGt01Superconductor>;
var FieldGenLV = <gregtech:gt.metaitem.01:32671>;
var emitterIV = <gregtech:gt.metaitem.01:32684>;
var LapotronicCrystal = <IC2:itemBatLamaCrystal:*>;
var LapotronicOrb = <gregtech:gt.metaitem.01:32597>;
var AdvAlloy = <IC2:itemPartAlloy>;
var plateRIridium = <IC2:itemPartIridium>;
var advDDrill = <GraviSuite:advDDrill>;
var advChainsaw = <GraviSuite:advChainsaw>;
var DrillHV = <gregtech:gt.metatool.01:105>;

var ChainsawHV = <gregtech:gt.metatool.01:115>;
var diamondGear = <gregtech:gt.metaitem.02:31500>;
var sdiamondGear = <gregtech:gt.metaitem.02:20500>;
var diamondBolt = <gregtech:gt.metaitem.01:26500>;
var OCUpgrade = <IC2:upgradeModule>;


// --- Glass Fiber Recipes ---
//recipes.remove(superconductor);
/*recipes.addShaped(superconductor, [
		[superconductorCover, superconductorCover, superconductorCover],
		[SuperconductorGT, SuperconductorGT, SuperconductorGT],
		[superconductorCover, superconductorCover, superconductorCover]]);*/

recipes.remove(advancedElectricJetpack2);
recipes.addShaped(advancedElectricJetpack, [
		[carbonPlate, electricJetpack, carbonPlate],
		[engineBooster, advancedLappack, engineBooster],
		[<ore:wireGt04Platinum>, circuitAdvanced, <ore:wireGt04Platinum>]]);
		
recipes.addShaped(<GraviSuite:advLappack>, [
		[null, <IC2:itemArmorEnergypack:*>, null],
		[null, <ore:circuitAdvanced>, null],
		[null, <ore:batteryMaster>, null]]);

recipes.remove(advancedNanoChestPlate2);
recipes.addShaped(advancedNanoChestPlate, [
		[carbonPlate, advancedElectricJetpack2, carbonPlate],
		[carbonPlate, nanoBodyArmor, carbonPlate],
		[<ore:wireGt04Platinum>, <ore:circuitElite>, <ore:wireGt04Platinum>]]);
        
recipes.remove(coolingCore);
recipes.addShaped(coolingCore, [
        [cell60k, advHeatExchanger, cell60k],
        [heatReactorPlating, plateAlloyIridium, heatReactorPlating],
        [cell60k, advHeatExchanger, cell60k]]);
        
recipes.remove(engineBooster);
recipes.addShaped(engineBooster, [  
        [<minecraft:glowstone_dust>, <IC2:itemPartAlloy>, <minecraft:glowstone_dust>],
        [<ore:circuitAdvanced>, <IC2:upgradeModule>, <ore:circuitAdvanced>],
        [<IC2:itemPartAlloy>, <IC2:reactorVentDiamond:*>, <IC2:itemPartAlloy>]]);

recipes.remove(GravitationEngine);
recipes.addShaped(GravitationEngine, [
		[emitterIV, superconductor, emitterIV],
		[coolingCore, LapotronicOrb, coolingCore],
		[emitterIV, superconductor, emitterIV]]);

//recipes.remove(GraviChest2);
/*recipes.addShaped(GraviChest, [
		[superconductor, QuantumChest, superconductor],
		[GravitationEngine, FieldGenLV, GravitationEngine],
		[superconductor, UltLappack, superconductor]]);*/

/*recipes.addShaped(<IC2:itemArmorQuantumChestplate>, [
  [AdvAlloy, <GraviSuite:advNanoChestPlate:26>, AdvAlloy],
  [plateRIridium, LapotronicCrystal, plateRIridium],
  [plateRIridium, null, plateRIridium]]);*/

// --- Localization Fixes ---
// game.setLocalization("itemSuperConductorCover.name", "Superconductor Cover");
// game.setLocalization("itemSuperConductor.name", "Superconductor");
// game.setLocalization("itemCoolingCore.name", "Cooling Core");
// game.setLocalization("itemGravitationEngine.name", "Gravitation Engine");
// game.setLocalization("itemMagnetron.name", "Magnetron");
// game.setLocalization("itemVajraCore.name", "Vajra Core");
// game.setLocalization("itemEngineBoost.name", "Engine Booster");
// game.setLocalization("item.sonicLauncher.name", "Sonic Launcher");
// game.setLocalization("RelocatorPortal.name", "Relocator Portal");


// Adv Mining Drill
var diamondDrillTip = <gregtech:gt.metaitem.02:8500>;
game.setLocalization("gt.metaitem.02.8500.name", "Diamond Drill Tip");
recipes.remove(advDDrill);

recipes.addShaped(diamondDrillTip, [
	[<ore:plateDiamond>, <ore:plateSteel>, <ore:plateDiamond>],
	[<ore:plateDiamond>, <ore:plateSteel>, <ore:plateDiamond>],
	[<ore:plateSteel>, <ore:craftingToolHardHammer>, <ore:plateSteel>]]);

recipes.addShaped(advDDrill, [
	[<ore:screwHSSG>, diamondDrillTip, <ore:craftingToolScrewdriver>],
	[<ore:gearGtSmallHSSG>, <gregtech:gt.metaitem.01:32603>, <ore:gearGtSmallHSSG>],
	[<ore:plateHSSG>, <IC2:itemBatLamaCrystal:*>, <ore:plateHSSG>]]);

// Adv Chainsaw
var diamondChainsawTip = <gregtech:gt.metaitem.02:9500>;
game.setLocalization("gt.metaitem.02.9500.name", "Diamond Chainsaw Tip");

recipes.addShaped(diamondChainsawTip, [
	[<ore:plateSteel>, <ore:ringSteel>, <ore:plateSteel>],
	[<ore:plateDiamond>, <ore:craftingToolHardHammer>, <ore:plateDiamond>],
	[<ore:plateSteel>, <ore:ringSteel>, <ore:plateSteel>]]);

recipes.remove(advChainsaw);

recipes.addShaped(advChainsaw, [
	[<ore:screwHSSG>, diamondChainsawTip, <ore:craftingToolScrewdriver>],
	[<ore:gearGtSmallHSSG>, <gregtech:gt.metaitem.01:32603>, <ore:gearGtSmallHSSG>],
	[<ore:plateHSSG>, <IC2:itemBatChargeLamaCrystal:*>, <ore:plateHSSG>]]);

recipes.remove(<GraviSuite:relocator>);

// GraviTool
recipes.remove(<GraviSuite:graviTool>);
recipes.addShaped(<GraviSuite:graviTool>, [
	[<ore:plateAlloyCarbon>, <IC2:itemToolHoe:*>, <ore:plateAlloyCarbon>],
	[<ore:plateAlloyAdvanced>, <ore:batteryElite>, <ore:plateAlloyAdvanced>],
	[<IC2:itemToolWrenchElectric:*>, <ore:circuitAdvanced>, <IC2:itemTreetapElectric:*>]]);


// NanoSet
recipes.remove(<IC2:itemArmorNanoHelmet:*>);
recipes.remove(<IC2:itemArmorNanoChestplate:*>);
recipes.remove(<IC2:itemArmorNanoLegs:*>);
recipes.remove(<IC2:itemArmorNanoBoots:*>);

Assembler.addRecipe(<IC2:itemArmorNanoHelmet:27>, [<gregtech:gt.integrated_circuit:0> * 0, <IC2:itemPartCarbonPlate> * 4, <IC2:itemBatCrystal:*>, <IC2:itemNightvisionGoggles:*>], null, 400, 120);
Assembler.addRecipe(<IC2:itemArmorNanoChestplate:27>, [<gregtech:gt.integrated_circuit:1> * 0, <IC2:itemPartCarbonPlate> * 7, <IC2:itemBatCrystal:*>], null, 400, 120);
Assembler.addRecipe(<IC2:itemArmorNanoLegs:27>, [<gregtech:gt.integrated_circuit:2> * 0, <IC2:itemPartCarbonPlate> * 6, <IC2:itemBatCrystal:*>], null, 400, 120);
Assembler.addRecipe(<IC2:itemArmorNanoBoots:27>, [<gregtech:gt.integrated_circuit:3> * 0, <IC2:itemPartCarbonPlate> * 4, <IC2:itemBatCrystal:*>], null, 400, 120);


// Magnetron
recipes.addShaped(<GraviSuite:itemSimpleItem:4>, [
	[<gregtech:gt.metaitem.01:17032>, <gregtech:gt.metaitem.01:17035>, <gregtech:gt.metaitem.01:17032>],
	[<gregtech:gt.metaitem.01:17035>, superconductor, <gregtech:gt.metaitem.01:17035>],
	[<gregtech:gt.metaitem.01:17032>, <gregtech:gt.metaitem.01:17035>, <gregtech:gt.metaitem.01:17032>]]);

// Vajra core
recipes.addShaped(<GraviSuite:itemSimpleItem:5>, [
	[null, <GraviSuite:itemSimpleItem:4>, null],
	[<ore:plateAlloyIridium>, <IC2:blockMachine2:1>, <ore:plateAlloyIridium>],
	[<GraviSuite:itemSimpleItem:1>, <gregtech:gt.blockmachines:22>, <GraviSuite:itemSimpleItem:1>]]);

// Vajra
recipes.addShaped(<GraviSuite:vajra:1>, [
	[<ore:plateNeutronium>, LapotronicCrystal, <ore:plateNeutronium>],
	[<ore:plateAlloyCarbon>, <GraviSuite:itemSimpleItem:5>, <ore:plateAlloyCarbon>],
	[<ore:plateNeutronium>, <gregtech:gt.metaitem.03:32092>, <ore:plateNeutronium>]]);
