// --- Created by Jason McRay ---
// --- Few scripts created by DreamMasterXXL ---
// --- Edited by Sapient ---

import mods.gregtech.Assembler;
import mods.gregtech.Mixer;
import mods.gregtech.CuttingSaw;
import mods.forestry.Carpenter;
import mods.forestry.ThermionicFabricator;
import mods.nei.NEI;
import minetweaker.item.IItemStack;
import mods.gregtech.FluidExtractor;

# Aliases
var analyzer = <Forestry:core>;
var bcTank = <extracells:certustank>;
var blockGlass = <ore:blockGlass>;
var bogEarth = <Forestry:soil:1>;
var book = <minecraft:book>;
var bottler = <Forestry:factory>;
var cableGold = <ore:cableGt01Gold>;
var carpenter = <Forestry:factory:1>;
var cauldron = <minecraft:cauldron>;
var centrifuge = <Forestry:factory:2>;
var chestWood = <minecraft:chest>;
var compost = <Forestry:fertilizerBio>;
var craftingTable = <minecraft:crafting_table>;
var craftingToolSaw = <ore:craftingToolSaw>;
var craftingToolScrewdriver = <ore:craftingToolScrewdriver>;
var crateBogEarth = <Forestry:cratedBogearth>;
var crateHumus = <Forestry:cratedHumus>;
var dirtAny = <minecraft:dirt:*>;
var dustRedstone = <minecraft:redstone>;
var electricMotorLV = <gregtech:gt.metaitem.01:32600>;
var electricMotorHV = <gregtech:gt.metaitem.01:32602>;
var electricPistonMV = <gregtech:gt.metaitem.01:32641>;
var electricPumpHV = <gregtech:gt.metaitem.01:32612>;
var emitterMV = <gregtech:gt.metaitem.01:32681>;
var enderEye = <minecraft:ender_eye>;
var endStone = <minecraft:end_stone>;
var fAsh = <Forestry:ash>;
var fermenter = <Forestry:factory:3>;
var fGearBronze = <Forestry:gearBronze>;
var fGearCopper = <Forestry:gearCopper>;
var fGearTin = <Forestry:gearTin>;
var gearBronze = <ore:gearBronze>;
var gearCopper = <ore:gearCopper>;
var gearGtSmallAluminium = <ore:gearGtSmallAluminium>;
var gearGtSmallSteel = <ore:gearGtSmallSteel>;
var gearTin = <ore:gearTin>;
var gtAsh = <gregtech:gt.metaitem.01:2815>;
var hardenedCasing = <Forestry:hardenedMachine>;
var HHammer = <ore:craftingToolHardHammer>;
var humus = <Forestry:soil>;
var icAsh = <IC2:itemDust2:3>;
var icCoil = <IC2:itemRecipePart>;
var impregnatedCasing = <Forestry:impregnatedCasing>;
var impregnatedStick = <Forestry:oakStick>;
var liquidSeedOil = <liquid:seedoil>;
var liquidWater = <liquid:water>;
var mcString = <minecraft:string>;
var moistener = <Forestry:factory:4>;
var moltenGlass = <liquid:molten.glass>;
var moltenRedstone = <liquid:molten.redstone>;
var mSolderingAlloy = <liquid:molten.solderingalloy>;
var mRubber = <liquid:molten.rubber>;
var piston = <minecraft:piston>;
var plateBronze = <ore:plateBronze>;
var plateCopper = <ore:plateCopper>;
var plateCupronickel = <ore:plateCupronickel>;
var plateEnderEye = <ore:plateEnderEye>;
var plateGold = <ore:plateGold>;
var plateIron = <ore:plateIron>;
var plateNetherQ = <ore:plateNetherQuartz>;
var plateSilicon = <ore:plateSilicon>;
var pulpWood = <Forestry:woodPulp>;
var pulsatingMesh = <Forestry:craftingMaterial:1>;
var rainMaker = <Forestry:factory:7>;
var rainTank = <Forestry:factory2:1>;
var ringIron = <ore:ringIron>;
var robotArmLV = <gregtech:gt.metaitem.01:32650>;
var screwIron = <ore:screwIron>;
var sensorMV = <gregtech:gt.metaitem.01:32691>;
var silkWisp = <Forestry:craftingMaterial:2>;
var silkWoven = <Forestry:craftingMaterial:3>;
var solderingIron = <Forestry:solderingIron>;
var squeezer = <Forestry:factory:5>;
var steelScrew = <gregtech:gt.metaitem.01:27305>;
var still = <Forestry:factory:6>;
var sturdyCasing = <Forestry:sturdyMachine>;
var thermionicFabricator = <Forestry:factory2>;
var tinRotor = <gregtech:gt.metaitem.02:21057>;
var tubeApatine = <Forestry:thermionicTubes:10>;
var tubeBlaze = <Forestry:thermionicTubes:7>;
var tubeBronze = <Forestry:thermionicTubes:2>;
var tubeCopper = <Forestry:thermionicTubes>;
var tubeDiamond = <Forestry:thermionicTubes:5>;
var tubeEmerald = <Forestry:thermionicTubes:9>;
var tubeEnder = <Forestry:thermionicTubes:12>;
var tubeGold = <Forestry:thermionicTubes:4>;
var tubeIron = <Forestry:thermionicTubes:3>;
var tubeLapis = <Forestry:thermionicTubes:11>;
var tubeObsidian = <Forestry:thermionicTubes:6>;
var tubeRubber = <Forestry:thermionicTubes:8>;
var tubeTin = <Forestry:thermionicTubes:1>;
var unlitCandle = <Forestry:candle>;
var wheat = <minecraft:wheat>;
var wireKanthal = <ore:wireGt02Kanthal>;
var worktable = <Forestry:factory2:2>;
var wrench = <ore:craftingToolWrench>;
var PortableCell = <appliedenergistics2:item.ToolPortableCell>;
var beeAl = <Forestry:beealyzer>;
var treeAl = <Forestry:treealyzer>;
var flutterAl = <Forestry:flutterlyzer>;

var alvBlock = <Forestry:alveary>;
var alvSwarmer = <Forestry:alveary:2>;
var alvFan = <Forestry:alveary:3>;
var alvHeat = <Forestry:alveary:4>;
var alvHReg = <Forestry:alveary:5>;
var alvStab = <Forestry:alveary:6>;
var alvSieve = <Forestry:alveary:7>;

//var ringStainlessSteel = <appliedenergistics2:item.ItemMultiMaterial:35>;
//var ringPlatinum = <appliedenergistics2:item.ItemMultiMaterial:36>;
var Singularity = <appliedenergistics2:item.ItemMultiMaterial:47>;
var ringStainlessSteel = <ore:ringStainlessSteel>;
var ringPlatinum = <ore:ringPlatinum>;
var scoop = <Forestry:scoop>;

var ComputerMontor = <gregtech:gt.metaitem.01:32740>;

# Item removal
recipes.remove(analyzer);
//NEI.hide(analyzer);

recipes.remove(bottler);
//NEI.hide(bottler);

recipes.remove(centrifuge);
//NEI.hide(centrifuge);

recipes.remove(squeezer);
//NEI.hide(squeezer);

recipes.remove(still);
//NEI.hide(still);

recipes.remove(fGearBronze);
gearBronze.remove(fGearBronze);
NEI.hide(fGearBronze);

recipes.remove(fGearCopper);
gearCopper.remove(fGearCopper);
NEI.hide(fGearCopper);

recipes.remove(fGearTin);
gearTin.remove(fGearTin);
NEI.hide(fGearTin);

recipes.remove(scoop);
NEI.hide(scoop);

# Recipe Tweaks
recipes.remove(carpenter);
recipes.addShaped(carpenter, [
	[plateCopper, bcTank, plateCopper],
	[robotArmLV, sturdyCasing, robotArmLV],
	[gearGtSmallSteel, electricMotorLV, gearGtSmallSteel]]);
recipes.remove(fermenter);
recipes.addShaped(fermenter, [
	[plateCopper, tinRotor, plateCopper],
	[bcTank, sturdyCasing, bcTank],
	[gearGtSmallSteel, electricMotorLV, gearGtSmallSteel]]);
recipes.remove(moistener);
recipes.addShaped(moistener, [
	[plateCopper, bcTank, plateCopper],
	[tinRotor, sturdyCasing, tinRotor],
	[gearGtSmallSteel, electricMotorLV, gearGtSmallSteel]]);
recipes.remove(rainMaker);
recipes.addShaped(rainMaker, [
	[plateCupronickel, sensorMV, plateCupronickel],
	[electricPistonMV, hardenedCasing, electricPistonMV],
	[gearGtSmallAluminium, emitterMV, gearGtSmallAluminium]]);
recipes.remove(thermionicFabricator);
Carpenter.addRecipe(thermionicFabricator, [[steelScrew, bcTank, steelScrew],
                                           [icCoil, sturdyCasing, icCoil],
                                           [steelScrew, electricMotorLV, steelScrew]], moltenRedstone * 1008, 300, worktable);
recipes.remove(rainTank);
recipes.addShaped(rainTank, [
	[plateIron, ringIron, plateIron],
	[plateIron, HHammer, plateIron],
	[plateIron, cauldron, plateIron]]);
	
# GT Integration
//Carpenter.addRecipe(durationTicks, fluidInput, [recipe], box(top), output); old
//Carpenter.addRecipe(output, [recipe], fluidInput, time, box); new
//Assembler.addRecipe(output, input1, input2, liquid, durationTicks, euPerTick);
recipes.remove(humus);
Carpenter.removeRecipe(humus);
Carpenter.addRecipe(humus * 9, [[crateHumus, null, null],
                                [null, null, null], 
                                [null, null, null]], 5);
recipes.remove(bogEarth);
Carpenter.removeRecipe(bogEarth);
Carpenter.addRecipe(bogEarth * 9, [[crateBogEarth, null, null],
                                   [null, null, null],
                                   [null, null, null]], 5);
recipes.remove(<Forestry:ffarm:*>);
// --- Farm Block
Carpenter.addRecipe(<Forestry:ffarm>.withTag({FarmBlock:0}), [[<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>],
                                                              [<IC2:itemCasing>, <Forestry:thermionicTubes:1>, <IC2:itemCasing>],
                                                              [<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 144, 150, <minecraft:stonebrick>);
Carpenter.addRecipe(<Forestry:ffarm>.withTag({FarmBlock:1}), [[<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>],
                                                              [<IC2:itemCasing>, <Forestry:thermionicTubes:1>, <IC2:itemCasing>],
                                                              [<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 144, 150, <minecraft:stonebrick:1>);
Carpenter.addRecipe(<Forestry:ffarm>.withTag({FarmBlock:2}), [[<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>],
                                                              [<IC2:itemCasing>, <Forestry:thermionicTubes:1>, <IC2:itemCasing>],
                                                              [<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 144, 150, <minecraft:stonebrick:2>);
Carpenter.addRecipe(<Forestry:ffarm>.withTag({FarmBlock:3}), [[<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>],
                                                              [<IC2:itemCasing>, <Forestry:thermionicTubes:1>, <IC2:itemCasing>],
                                                              [<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 144, 150, <minecraft:brick_block>);
Carpenter.addRecipe(<Forestry:ffarm>.withTag({FarmBlock:4}), [[<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>],
                                                              [<IC2:itemCasing>, <Forestry:thermionicTubes:1>, <IC2:itemCasing>],
                                                              [<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 144, 150, <minecraft:sandstone:2>);
Carpenter.addRecipe(<Forestry:ffarm>.withTag({FarmBlock:5}), [[<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>],
                                                              [<IC2:itemCasing>, <Forestry:thermionicTubes:1>, <IC2:itemCasing>],
                                                              [<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 144, 150, <minecraft:sandstone:1>);
Carpenter.addRecipe(<Forestry:ffarm>.withTag({FarmBlock:6}), [[<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>],
                                                              [<IC2:itemCasing>, <Forestry:thermionicTubes:1>, <IC2:itemCasing>],
                                                              [<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 144, 150, <minecraft:nether_brick>);
Carpenter.addRecipe(<Forestry:ffarm>.withTag({FarmBlock:7}), [[<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>],
                                                              [<IC2:itemCasing>, <Forestry:thermionicTubes:1>, <IC2:itemCasing>],
                                                              [<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 144, 150, <minecraft:stonebrick:3>);
Carpenter.addRecipe(<Forestry:ffarm>.withTag({FarmBlock:8}), [[<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>],
                                                              [<IC2:itemCasing>, <Forestry:thermionicTubes:1>, <IC2:itemCasing>],
                                                              [<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 144, 150, <minecraft:quartz_block>);
Carpenter.addRecipe(<Forestry:ffarm>.withTag({FarmBlock:9}), [[<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>],
                                                              [<IC2:itemCasing>, <Forestry:thermionicTubes:1>, <IC2:itemCasing>],
                                                              [<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 144, 150, <minecraft:quartz_block:1>);
Carpenter.addRecipe(<Forestry:ffarm>.withTag({FarmBlock:10}), [[<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>],
                                                               [<IC2:itemCasing>, <Forestry:thermionicTubes:1>, <IC2:itemCasing>],
                                                               [<gregtech:gt.metaitem.01:27305>, <IC2:itemCasing>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 144, 150, <minecraft:quartz_block:2>);
// --- Farm Gearbox
Carpenter.addRecipe(<Forestry:ffarm:2>.withTag({FarmBlock:0}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.02:20305>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.02:20305>],
                                                                [null, <gregtech:gt.metaitem.02:20305>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:0}));
Carpenter.addRecipe(<Forestry:ffarm:2>.withTag({FarmBlock:1}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.02:20305>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.02:20305>],
                                                                [null, <gregtech:gt.metaitem.02:20305>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:1}));
Carpenter.addRecipe(<Forestry:ffarm:2>.withTag({FarmBlock:2}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.02:20305>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.02:20305>],
                                                                [null, <gregtech:gt.metaitem.02:20305>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:2}));
Carpenter.addRecipe(<Forestry:ffarm:2>.withTag({FarmBlock:3}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.02:20305>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.02:20305>],
                                                                [null, <gregtech:gt.metaitem.02:20305>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:3}));
Carpenter.addRecipe(<Forestry:ffarm:2>.withTag({FarmBlock:4}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.02:20305>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.02:20305>],
                                                                [null, <gregtech:gt.metaitem.02:20305>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:4}));
Carpenter.addRecipe(<Forestry:ffarm:2>.withTag({FarmBlock:5}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.02:20305>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.02:20305>],
                                                                [null, <gregtech:gt.metaitem.02:20305>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:5}));
Carpenter.addRecipe(<Forestry:ffarm:2>.withTag({FarmBlock:6}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.02:20305>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.02:20305>],
                                                                [null, <gregtech:gt.metaitem.02:20305>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:6}));
Carpenter.addRecipe(<Forestry:ffarm:2>.withTag({FarmBlock:7}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.02:20305>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.02:20305>],
                                                                [null, <gregtech:gt.metaitem.02:20305>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:7}));
Carpenter.addRecipe(<Forestry:ffarm:2>.withTag({FarmBlock:8}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.02:20305>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.02:20305>],
                                                                [null, <gregtech:gt.metaitem.02:20305>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:8}));
Carpenter.addRecipe(<Forestry:ffarm:2>.withTag({FarmBlock:9}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.02:20305>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.02:20305>],
                                                                [null, <gregtech:gt.metaitem.02:20305>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:9}));
Carpenter.addRecipe(<Forestry:ffarm:2>.withTag({FarmBlock:10}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.02:20305>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.02:20305>],
                                                                 [null, <gregtech:gt.metaitem.02:20305>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:10}));
// --- Farm Hatch
Carpenter.addRecipe(<Forestry:ffarm:3>.withTag({FarmBlock:0}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.01:32630>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32630>],
                                                                 [null, <minecraft:hopper>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:0}));
Carpenter.addRecipe(<Forestry:ffarm:3>.withTag({FarmBlock:1}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.01:32630>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32630>],
                                                                 [null, <minecraft:hopper>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:1}));
Carpenter.addRecipe(<Forestry:ffarm:3>.withTag({FarmBlock:2}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.01:32630>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32630>],
                                                                 [null, <minecraft:hopper>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:2}));
Carpenter.addRecipe(<Forestry:ffarm:3>.withTag({FarmBlock:3}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.01:32630>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32630>],
                                                                 [null, <minecraft:hopper>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:3}));
Carpenter.addRecipe(<Forestry:ffarm:3>.withTag({FarmBlock:4}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.01:32630>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32630>],
                                                                 [null, <minecraft:hopper>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:4}));
Carpenter.addRecipe(<Forestry:ffarm:3>.withTag({FarmBlock:5}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.01:32630>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32630>],
                                                                 [null, <minecraft:hopper>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:5}));
Carpenter.addRecipe(<Forestry:ffarm:3>.withTag({FarmBlock:6}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.01:32630>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32630>],
                                                                 [null, <minecraft:hopper>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:6}));
Carpenter.addRecipe(<Forestry:ffarm:3>.withTag({FarmBlock:7}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.01:32630>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32630>],
                                                                 [null, <minecraft:hopper>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:7}));
Carpenter.addRecipe(<Forestry:ffarm:3>.withTag({FarmBlock:8}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.01:32630>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32630>],
                                                                 [null, <minecraft:hopper>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:8}));
Carpenter.addRecipe(<Forestry:ffarm:3>.withTag({FarmBlock:9}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.01:32630>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32630>],
                                                                 [null, <minecraft:hopper>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:9}));
Carpenter.addRecipe(<Forestry:ffarm:3>.withTag({FarmBlock:10}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                  [<gregtech:gt.metaitem.01:32630>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32630>],
                                                                  [null, <minecraft:hopper>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:10}));
// --- Farm Valve
Carpenter.addRecipe(<Forestry:ffarm:4>.withTag({FarmBlock:0}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.01:32610>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32610>],
                                                                [null, <gregtech:gt.metaitem.01:28880>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:0}));
Carpenter.addRecipe(<Forestry:ffarm:4>.withTag({FarmBlock:1}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.01:32610>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32610>],
                                                                [null, <gregtech:gt.metaitem.01:28880>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:1}));
Carpenter.addRecipe(<Forestry:ffarm:4>.withTag({FarmBlock:2}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.01:32610>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32610>],
                                                                [null, <gregtech:gt.metaitem.01:28880>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:2}));
Carpenter.addRecipe(<Forestry:ffarm:4>.withTag({FarmBlock:3}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.01:32610>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32610>],
                                                                [null, <gregtech:gt.metaitem.01:28880>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:3}));
Carpenter.addRecipe(<Forestry:ffarm:4>.withTag({FarmBlock:4}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.01:32610>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32610>],
                                                                [null, <gregtech:gt.metaitem.01:28880>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:4}));
Carpenter.addRecipe(<Forestry:ffarm:4>.withTag({FarmBlock:5}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.01:32610>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32610>],
                                                                [null, <gregtech:gt.metaitem.01:28880>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:5}));
Carpenter.addRecipe(<Forestry:ffarm:4>.withTag({FarmBlock:6}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.01:32610>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32610>],
                                                                [null, <gregtech:gt.metaitem.01:28880>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:6}));
Carpenter.addRecipe(<Forestry:ffarm:4>.withTag({FarmBlock:7}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.01:32610>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32610>],
                                                                [null, <gregtech:gt.metaitem.01:28880>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:7}));
Carpenter.addRecipe(<Forestry:ffarm:4>.withTag({FarmBlock:8}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.01:32610>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32610>],
                                                                [null, <gregtech:gt.metaitem.01:28880>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:8}));
Carpenter.addRecipe(<Forestry:ffarm:4>.withTag({FarmBlock:9}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<gregtech:gt.metaitem.01:32610>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32610>],
                                                                [null, <gregtech:gt.metaitem.01:28880>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:9}));
Carpenter.addRecipe(<Forestry:ffarm:4>.withTag({FarmBlock:10}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<gregtech:gt.metaitem.01:32610>, <gregtech:gt.metaitem.01:32600>, <gregtech:gt.metaitem.01:32610>],
                                                                 [null, <gregtech:gt.metaitem.01:28880>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:10}));
// --- Farm Control
Carpenter.addRecipe(<Forestry:ffarm:5>.withTag({FarmBlock:0}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<ore:circuitBasic>, <gregtech:gt.metaitem.01:32600>, <ore:circuitBasic>],
                                                                [null, <gregtech:gt.blockmachines:1246>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:0}));
Carpenter.addRecipe(<Forestry:ffarm:5>.withTag({FarmBlock:1}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<ore:circuitBasic>, <gregtech:gt.metaitem.01:32600>, <ore:circuitBasic>],
                                                                [null, <gregtech:gt.blockmachines:1246>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:1}));
Carpenter.addRecipe(<Forestry:ffarm:5>.withTag({FarmBlock:2}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<ore:circuitBasic>, <gregtech:gt.metaitem.01:32600>, <ore:circuitBasic>],
                                                                [null, <gregtech:gt.blockmachines:1246>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:2}));
Carpenter.addRecipe(<Forestry:ffarm:5>.withTag({FarmBlock:3}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<ore:circuitBasic>, <gregtech:gt.metaitem.01:32600>, <ore:circuitBasic>],
                                                                [null, <gregtech:gt.blockmachines:1246>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:3}));
Carpenter.addRecipe(<Forestry:ffarm:5>.withTag({FarmBlock:4}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<ore:circuitBasic>, <gregtech:gt.metaitem.01:32600>, <ore:circuitBasic>],
                                                                [null, <gregtech:gt.blockmachines:1246>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:4}));
Carpenter.addRecipe(<Forestry:ffarm:5>.withTag({FarmBlock:5}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<ore:circuitBasic>, <gregtech:gt.metaitem.01:32600>, <ore:circuitBasic>],
                                                                [null, <gregtech:gt.blockmachines:1246>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:5}));
Carpenter.addRecipe(<Forestry:ffarm:5>.withTag({FarmBlock:6}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<ore:circuitBasic>, <gregtech:gt.metaitem.01:32600>, <ore:circuitBasic>],
                                                                [null, <gregtech:gt.blockmachines:1246>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:6}));
Carpenter.addRecipe(<Forestry:ffarm:5>.withTag({FarmBlock:7}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<ore:circuitBasic>, <gregtech:gt.metaitem.01:32600>, <ore:circuitBasic>],
                                                                [null, <gregtech:gt.blockmachines:1246>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:7}));
Carpenter.addRecipe(<Forestry:ffarm:5>.withTag({FarmBlock:8}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<ore:circuitBasic>, <gregtech:gt.metaitem.01:32600>, <ore:circuitBasic>],
                                                                [null, <gregtech:gt.blockmachines:1246>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:8}));
Carpenter.addRecipe(<Forestry:ffarm:5>.withTag({FarmBlock:9}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                [<ore:circuitBasic>, <gregtech:gt.metaitem.01:32600>, <ore:circuitBasic>],
                                                                [null, <gregtech:gt.blockmachines:1246>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:9}));
Carpenter.addRecipe(<Forestry:ffarm:5>.withTag({FarmBlock:10}), [[null, <gregtech:gt.metaitem.02:20305>, null],
                                                                 [<ore:circuitBasic>, <gregtech:gt.metaitem.01:32600>, <ore:circuitBasic>],
                                                                 [null, <gregtech:gt.blockmachines:1246>, null]], <liquid:molten.redstone> * 144, 300, <Forestry:ffarm>.withTag({FarmBlock:10}));

//Frames


//Carpenter.addRecipe(<Forestry:frameUntreated> *2 , [[<ore:stickWood>, <ore:stickWood>, <ore:stickWood>],
//                                                                [<ore:stickWood>, null, <ore:stickWood>],
//                                                                [<ore:stickWood>, <ore:stickWood>, <ore:stickWood>]], null, 300, <minecraft:string>);

Assembler.addRecipe(<Forestry:frameUntreated> * 2,
 [<ore:stickWood> * 8, <minecraft:string>, <gregtech:gt.integrated_circuit:2> * 0], null, 10*20, 60);

											
//Carpenter.addRecipe(<Forestry:frameImpregnated> *2 , [[<Forestry:oakStick>, <Forestry:oakStick>, <Forestry:oakStick>],
//                                                                [<Forestry:oakStick>, null, <Forestry:oakStick>],
//                                                                [<Forestry:oakStick>, <Forestry:oakStick>, <Forestry:oakStick>]], null, 300, <minecraft:string>);
					
Assembler.addRecipe(<Forestry:frameImpregnated> * 2,
 [<Forestry:oakStick> * 8, <minecraft:string>, <gregtech:gt.integrated_circuit:2> * 0], null, 10*20, 60);
										 
//Soldering Iron
Carpenter.removeRecipe(solderingIron);

Carpenter.addRecipe(solderingIron, [
 [<minecraft:redstone>, <gregtech:gt.metaitem.01:26035>, null],
 [<ore:stickIron>, <minecraft:redstone>, null],
 [null, null, null]],
 mRubber * 432, 60, <gregtech:gt.metaitem.02:19314>);

//Assembler.addRecipe(solderingIron,
// [<minecraft:redstone> * 2, <gregtech:gt.metaitem.01:26035>, <ore:stickIron>, 
//  <gregtech:gt.metaitem.02:19314>, <gregtech:gt.integrated_circuit:4> * 0], mRubber * 432, 10*20, 60);

//Assembler.addRecipe(solderingIron,
// [<minecraft:redstone> * 2, <ore:boltCopper>, <ore:stickIron>, 
//  <ore:wireFineSolderingAlloy>, <gregtech:gt.integrated_circuit:4> * 0], mRubber * 432, 10*20, 60);

//Beealyzer
Carpenter.removeRecipe(beeAl);
/*
Carpenter.addRecipe(beeAl, [
 [<ore:plateGlass>, ComputerMontor, <ore:plateGlass>],
 [<ore:circuitAdvanced>, <ore:circuitData>, <ore:circuitAdvanced>],
 [<ore:plateAluminium>, plateSilicon, <ore:plateAluminium>]],
 <liquid:molten.solderingalloy> * 288, 120, <Forestry:beeCombs>);
*/

Assembler.addRecipe(beeAl,
 [<ore:plateGlass> * 2, ComputerMontor, <ore:circuitAdvanced> * 2,
  <ore:plateAluminium> * 2, <ore:beeComb>, <gregtech:gt.integrated_circuit:6> * 0], mSolderingAlloy * 288, 30*20, 120);

//Treealyzer
Carpenter.removeRecipe(treeAl);
/*
Carpenter.addRecipe(treeAl, [
 [<ore:plateGlass>, ComputerMontor, <ore:plateGlass>],
 [<ore:circuitAdvanced>, <ore:circuitData>, <ore:circuitAdvanced>],
 [<ore:plateAluminium>, plateSilicon, <ore:plateAluminium>]],
 <liquid:molten.solderingalloy> * 288, 120, <minecraft:sapling>);
*/

Assembler.addRecipe(treeAl,
 [<ore:plateGlass> * 2, ComputerMontor, <ore:circuitAdvanced> * 2,
  <ore:plateAluminium> * 2, <minecraft:sapling>, <gregtech:gt.integrated_circuit:6> * 0], mSolderingAlloy * 288, 30*20, 120);

//Flutterlyzer
Carpenter.removeRecipe(flutterAl);
/*
Carpenter.addRecipe(flutterAl, [
 [<ore:plateGlass>, ComputerMontor, <ore:plateGlass>],
 [<ore:circuitAdvanced>, <ore:circuitData>, <ore:circuitAdvanced>],
 [<ore:plateAluminium>, plateSilicon, <ore:plateAluminium>]],
 <liquid:molten.solderingalloy> * 288, 120, <Forestry:butterflyGE>);
*/

Assembler.addRecipe(flutterAl,
 [<ore:plateGlass> * 2, ComputerMontor, <ore:circuitAdvanced> * 2,
  <ore:plateAluminium> * 2, <minecraft:yellow_flower>, <gregtech:gt.integrated_circuit:6> * 0], mSolderingAlloy * 288, 30*20, 120);

Assembler.addRecipe(flutterAl,
 [<ore:plateGlass> * 2, ComputerMontor, <ore:circuitAdvanced> * 2,
  <ore:plateAluminium> * 2, <minecraft:red_flower>, <gregtech:gt.integrated_circuit:6> * 0], mSolderingAlloy * 288, 30*20, 120);


//Mixer.addRecipe(output, liquidOutput, [input1, input2, input3, input4], liquidInput, durationTicks, euPerTick);
recipes.remove(compost);
Mixer.addRecipe (compost, null, [wheat * 4, dirtAny], liquidWater * 100, 64, 8);
Mixer.addRecipe (compost, null, [fAsh * 4, dirtAny], liquidWater * 100, 64, 8);
Mixer.addRecipe (compost, null, [gtAsh * 4, dirtAny], liquidWater * 100, 64, 8);
Mixer.addRecipe (compost, null, [icAsh * 4, dirtAny], liquidWater * 100, 64, 8);
recipes.remove(<Forestry:sturdyMachine>);
recipes.addShaped(<Forestry:sturdyMachine>, [
	[<ore:plateBronze>, <ore:screwSteel>, <ore:plateBronze>],
	[<ore:screwSteel>, <gregtech:gt.blockcasings:1>, <ore:screwSteel>],
	[<ore:plateBronze>, <ore:screwSteel>, <ore:plateBronze>]]);
Assembler.addRecipe(<Forestry:sturdyMachine>, <gregtech:gt.blockcasings:1>, <gregtech:gt.metaitem.01:17300> * 4, 800, 32);
Carpenter.removeRecipe(hardenedCasing);
Carpenter.removeRecipe(impregnatedCasing);
recipes.remove(pulsatingMesh);
Carpenter.removeRecipe(silkWoven);
Carpenter.removeRecipe(<Forestry:chipsets>);
Carpenter.addRecipe(<Forestry:chipsets>, [[<gregtech:gt.metaitem.01:27032>, <gregtech:gt.metaitem.01:29032>, <gregtech:gt.metaitem.01:27032>],
                                          [<gregtech:gt.metaitem.01:32700>, <gregtech:gt.metaitem.02:19032>, <gregtech:gt.metaitem.01:32700>],
                                          [<gregtech:gt.metaitem.01:27032>, <gregtech:gt.metaitem.01:29032>, <gregtech:gt.metaitem.01:27032>]],<liquid:molten.redstone> * 1152, 100, <gregtech:gt.metaitem.01:32710>);
Carpenter.removeRecipe(<Forestry:chipsets:1>);
Carpenter.addRecipe(<Forestry:chipsets:1>, [[<gregtech:gt.metaitem.01:27300>, <gregtech:gt.metaitem.01:29300>, <gregtech:gt.metaitem.01:27300>],
                                            [<gregtech:gt.metaitem.01:32700>, <gregtech:gt.metaitem.02:19300>, <gregtech:gt.metaitem.01:32700>],
                                            [<gregtech:gt.metaitem.01:27300>, <gregtech:gt.metaitem.01:29300>, <gregtech:gt.metaitem.01:27300>]], <liquid:molten.redstone> * 1152, 400, <gregtech:gt.metaitem.01:32710>);
Carpenter.removeRecipe(<Forestry:chipsets:2>);
Carpenter.addRecipe(<Forestry:chipsets:2>, [[<gregtech:gt.metaitem.01:27305>, <gregtech:gt.metaitem.01:29305>, <gregtech:gt.metaitem.01:27305>],
                                            [<gregtech:gt.metaitem.01:32715>, <gregtech:gt.metaitem.02:19305>, <gregtech:gt.metaitem.01:32715>],
                                            [<gregtech:gt.metaitem.01:27305>, <gregtech:gt.metaitem.01:29305>, <gregtech:gt.metaitem.01:27305>]], <liquid:molten.redstone> * 1152, 400, <gregtech:gt.metaitem.01:32719>);
Carpenter.removeRecipe(<Forestry:chipsets:3>);
Carpenter.addRecipe(<Forestry:chipsets:3>, [[<gregtech:gt.metaitem.01:27303>, <gregtech:gt.metaitem.01:29303>, <gregtech:gt.metaitem.01:27303>],
                                            [<gregtech:gt.metaitem.01:32715>, <gregtech:gt.metaitem.02:19303>, <gregtech:gt.metaitem.01:32715>],
                                            [<gregtech:gt.metaitem.01:27303>, <gregtech:gt.metaitem.01:29303>, <gregtech:gt.metaitem.01:27303>]], <liquid:molten.redstone> * 1152, 400, <gregtech:gt.metaitem.01:32719>);
ThermionicFabricator.removeCast(tubeCopper);
ThermionicFabricator.removeCast(tubeTin);
ThermionicFabricator.removeCast(tubeBronze);
ThermionicFabricator.removeCast(tubeIron);
ThermionicFabricator.removeCast(tubeGold);
ThermionicFabricator.removeCast(tubeDiamond);
ThermionicFabricator.removeCast(tubeObsidian);
ThermionicFabricator.removeCast(tubeBlaze);
ThermionicFabricator.removeCast(tubeRubber);
ThermionicFabricator.removeCast(tubeEmerald);
ThermionicFabricator.removeCast(tubeApatine);
ThermionicFabricator.removeCast(tubeLapis);
ThermionicFabricator.removeCast(tubeEnder);
Carpenter.removeRecipe(impregnatedStick);
Carpenter.removeRecipe(pulpWood);
recipes.remove(<Forestry:canEmpty>);
Assembler.addRecipe(<Forestry:canEmpty>, <gregtech:gt.metaitem.01:17057> * 2, <minecraft:glass_pane>, 120, 8);
Assembler.addRecipe(tubeEnder * 4, enderEye * 2, endStone * 5, moltenGlass * 72, 64, 32);

// --- Backs ---
recipes.remove(<Forestry:adventurerBag>);
recipes.remove(<Forestry:minerBag>);
recipes.remove(<Forestry:diggerBag>);
recipes.remove(<Forestry:foresterBag>);
recipes.remove(<Forestry:hunterBag>);
recipes.remove(<Forestry:builderBag>);
Carpenter.removeRecipe(<Forestry:adventurerBagT2>);
NEI.hide(<Forestry:adventurerBagT2>);
Carpenter.removeRecipe(<Forestry:minerBagT2>);
Carpenter.removeRecipe(<Forestry:diggerBagT2>);
Carpenter.removeRecipe(<Forestry:foresterBagT2>);
Carpenter.removeRecipe(<Forestry:hunterBagT2>);
Carpenter.removeRecipe(<Forestry:builderBagT2>);

// --- Miner's Backpack
Carpenter.addRecipe(<Forestry:minerBag>, [
	[silkWoven, ringStainlessSteel, silkWoven],
	[silkWoven, <ore:ingotIron>, silkWoven],
	[silkWoven, silkWoven, silkWoven]], <liquid:molten.enderiumbase> * 1008, 600);
// --- Miner's Backpack Woven
Carpenter.addRecipe(<Forestry:minerBagT2>, [[<Forestry:craftingMaterial:1>, <Forestry:minerBag>, <Forestry:craftingMaterial:1>],
                                            [<Forestry:craftingMaterial:1>, ringPlatinum, <Forestry:craftingMaterial:1>],
                                            [<Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>]], <liquid:molten.enderium> * 1008, 600, Singularity);

// --- Digger's Backpack
Carpenter.addRecipe(<Forestry:diggerBag>, [
	[silkWoven, ringStainlessSteel, silkWoven],
	[silkWoven, <ore:stone>, silkWoven],
	[silkWoven, silkWoven, silkWoven]], <liquid:molten.enderiumbase> * 1008, 600);

// --- Digger's Backpack Woven
Carpenter.addRecipe(<Forestry:diggerBagT2>, [[<Forestry:craftingMaterial:1>, <Forestry:diggerBag>, <Forestry:craftingMaterial:1>],
                                             [<Forestry:craftingMaterial:1>, ringPlatinum, <Forestry:craftingMaterial:1>],
                                             [<Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>]], <liquid:molten.enderium> * 1008, 600, Singularity);

// --- Forester's Backpack
Carpenter.addRecipe(<Forestry:foresterBag>, [
	[silkWoven, ringStainlessSteel, silkWoven],
	[silkWoven, <ore:logWood>, silkWoven],
	[silkWoven, silkWoven, silkWoven]], <liquid:molten.enderiumbase> * 1008, 600);
// --- Forester's Backpack Woven
Carpenter.addRecipe(<Forestry:foresterBagT2>, [[<Forestry:craftingMaterial:1>, <Forestry:foresterBag>, <Forestry:craftingMaterial:1>],
                                               [<Forestry:craftingMaterial:1>, ringPlatinum, <Forestry:craftingMaterial:1>],
                                               [<Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>]], <liquid:molten.enderium> * 1008, 600, Singularity);

// --- Hunter's Backpack
Carpenter.addRecipe(<Forestry:hunterBag>, [
	[silkWoven, ringStainlessSteel, silkWoven],
	[silkWoven, <minecraft:feather>, silkWoven],
	[silkWoven, silkWoven, silkWoven]], <liquid:molten.enderiumbase> * 1008, 600);
// --- Hunter's Backpack Woven
Carpenter.addRecipe(<Forestry:hunterBagT2>, [[<Forestry:craftingMaterial:1>, <Forestry:hunterBag>, <Forestry:craftingMaterial:1>],
                                             [<Forestry:craftingMaterial:1>, ringPlatinum, <Forestry:craftingMaterial:1>],
                                             [<Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>]], <liquid:molten.enderium> * 1008, 600, Singularity);

// --- Builder's Backpack
Carpenter.addRecipe(<Forestry:builderBag>, [
	[silkWoven, ringStainlessSteel, silkWoven],
	[silkWoven, <minecraft:fence>, silkWoven],
	[silkWoven, silkWoven, silkWoven]], <liquid:molten.enderiumbase> * 1008, 600);
// --- Builder's Backpack Woven
Carpenter.addRecipe(<Forestry:builderBagT2>, [[<Forestry:craftingMaterial:1>, <Forestry:builderBag>, <Forestry:craftingMaterial:1>],
                                              [<Forestry:craftingMaterial:1>, ringPlatinum, <Forestry:craftingMaterial:1>],
                                              [<Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>]], <liquid:molten.enderium> * 1008, 600, Singularity);

// Alveary
recipes.remove(alvSwarmer);
recipes.remove(alvFan);
recipes.remove(alvHeat);
recipes.remove(alvHReg);
recipes.remove(alvStab);
recipes.remove(alvSieve);

recipes.addShaped(alvSwarmer, [
	[tubeDiamond, chestWood, tubeDiamond],
	[plateSilicon, alvBlock, plateSilicon],
	[tubeDiamond, plateGold, tubeDiamond]]);
recipes.addShaped(alvFan, [
	[tubeGold, <ore:rotorSteel>, tubeGold],
	[plateSilicon, electricMotorHV, plateSilicon],
	[cableGold, alvBlock, <ore:circuitAdvanced>]]);
recipes.addShaped(alvHeat, [
	[wireKanthal, tubeGold, wireKanthal],
	[wireKanthal, plateSilicon, wireKanthal],
	[cableGold, alvBlock, <ore:circuitAdvanced>]]);
recipes.addShaped(alvHReg, [
	[<ore:pipeMediumSteel>, electricPumpHV, <ore:pipeMediumSteel>],
	[<ore:cellEmpty>, <ore:pipeMediumSteel>, <ore:cellEmpty>],
	[cableGold, alvBlock, <ore:circuitAdvanced>]]);
recipes.addShaped(alvStab, [
	[plateNetherQ, plateEnderEye, plateNetherQ],
	[<ore:dustGlowstone>, alvBlock, <ore:dustRedstone>],
	[plateNetherQ, plateEnderEye, plateNetherQ]]);
recipes.addShaped(alvStab, [
	[plateNetherQ, plateEnderEye, plateNetherQ],
	[<ore:dustRedstone>, alvBlock, <ore:dustGlowstone>],
	[plateNetherQ, plateEnderEye, plateNetherQ]]);
recipes.addShaped(alvSieve, [
	[impregnatedStick, chestWood, impregnatedStick],
	[impregnatedStick, alvBlock, impregnatedStick],
	[silkWoven, silkWoven, silkWoven]]);


//Wooden planks
var WoodPulp = <gregtech:gt.metaitem.01:2809>;

//Fertilizer
recipes.remove(<Forestry:fertilizerCompound>);
recipes.addShapeless(<Forestry:fertilizerCompound>, [<IC2:itemFertilizer>]);
recipes.addShapeless(<IC2:itemFertilizer>, [<Forestry:fertilizerCompound>]);

// Lava
FluidExtractor.addRecipe(null, <Forestry:phosphor>, <liquid:lava> * 800, 10000, 128, 4);