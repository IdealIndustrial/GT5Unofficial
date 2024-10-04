// IIS by Sapient
import mods.nei.NEI;
import mods.gregtech.Mixer;
import mods.forestry.Carpenter;
import mods.gregtech.Assembler;
import mods.gregtech.CuttingSaw;
import mods.gregtech.ChemicalBath;

# Aliases
var backpackApothecary = <Railcraft:backpack.apothecary.t1>;
var backpackApothecaryT2 = <Railcraft:backpack.apothecary.t2>;
var backpackIceman = <Railcraft:backpack.iceman.t1>;
var backpackIcemanT2 = <Railcraft:backpack.iceman.t2>;
var backpackTrack = <Railcraft:backpack.trackman.t1>;
var backpackTrackT2 = <Railcraft:backpack.trackman.t2>;
var benchEngraving = <Railcraft:machine.epsilon:5>;
var gregEngraverBasic = <gregtech:gt.blockmachines:591>;
var brickAbyssal = <Railcraft:brick.abyssal>;
var cablePlatinum = <gregtech:gt.blockmachines:1646>;
var blockSteelGearCasing = <gregtech:gt.blockcasings2:3>;
var circuitAdvanced = <ore:circuitAdvanced>;
var glass = <minecraft:glass>;
var ingotCopper = <ore:ingotCopper>;
var ingotGold = <ore:ingotGold>;
var ironBars = <minecraft:iron_bars>;
var piston = <minecraft:piston>;
var plateCopper = <ore:plateCopper>;
var plateSteel = <ore:plateSteel>;
var plateTinAlloy = <ore:plateTinAlloy>;
var pressurePlateStone = <minecraft:stone_pressure_plate>;
var railAdvanced = <Railcraft:part.rail:1>;
var railElectric = <Railcraft:part.rail:5>;
//var railHS = <Railcraft:part.rail:3>;
var railReinforced = <Railcraft:part.rail:4>;
var railStandard = <Railcraft:part.rail>;
var woodenTie = <Railcraft:part.tie>;
var cokeOven = <Railcraft:machine.alpha:7>;
var dustClay = <gregtech:gt.metaitem.01:2805>;
var sand = <minecraft:sand>;
var water = <liquid:water>;
var wetCokeBrick = <Railcraft:brick.sandy>;
var PortableCell = <appliedenergistics2:item.ToolPortableCell>;
var silkWoven = <Forestry:craftingMaterial:3>;
var woodenRail = <Railcraft:part.rail:2>;
var Rebar = <Railcraft:part.rebar>;
var Locomotive = <Railcraft:cart.loco.steam.solid:0>;
var ELocomotive = <Railcraft:cart.loco.electric>;
var BoilerHP = <gregtech:gt.blockmachines:101>;
var BronzeFirebox = <gregtech:gt.blockcasings3:13>;
var SwitchMotor = <Railcraft:signal:2>;
var SwitchLever = <Railcraft:signal:4>;
var PistonLV = <gregtech:gt.metaitem.01:32640>;
var ControllerCircuit = <Railcraft:part.circuit:0>;
var ReceiverCircuit = <Railcraft:part.circuit:1>;
var SignalCircuit = <Railcraft:part.circuit:2>;
var ICCircuit = <IC2:itemPartCircuit>;
var PlateGold = <gregtech:gt.metaitem.01:17086>;
var PlateLead = <gregtech:gt.metaitem.01:17089>;
var ChemicalGreenDye = <liquid:dye.chemical.dyegreen>;
var ChemicalRedDye = <liquid:dye.chemical.dyered>;
var ChemicalYellowDye = <liquid:dye.chemical.dyeyellow>;
var WaterGreenDye = <liquid:dye.watermixed.dyegreen>;
var WaterRedDye = <liquid:dye.watermixed.dyered>;
var WaterYellowDye = <liquid:dye.watermixed.dyeyellow>;
var TunnelBore = <Railcraft:cart.bore>;
var HSTrack = <Railcraft:track:816>;
//var reinforcedTrack = <Railcraft:track:0>.withTag({track: "railcraft:track.reinforced"});
var ringStainlessSteel = <ore:ringStainlessSteel>;
var ringPlatinum = <ore:ringPlatinum>;
var hhammer = <ore:craftingToolHardHammer>;
var wrench = <ore:craftingToolWrench>;
var MachineController = <gregtech:gt.metaitem.01:32730>;
var motorHV = <gregtech:gt.metaitem.01:32602>;
var ShuntingWire = <Railcraft:machine.delta:0>;
var conveyorLV = <gregtech:gt.metaitem.01:32630>;
var robotarmLV = <gregtech:gt.metaitem.01:32650>;

// Track Aliases
//// Vanilla|Standart
var standTrack = <minecraft:rail>;
var standJuncTrack = <Railcraft:track>.withTag({track: "railcraft:track.junction"});
var standSwitchTrack = <Railcraft:track:4767>.withTag({track: "railcraft:track.switch"});
var standWyeTrack = <Railcraft:track:2144>.withTag({track: "railcraft:track.wye"});
var standBoostTrack = <minecraft:golden_rail>;

//// Wooden
var woodenTrack = <Railcraft:track:736>.withTag({track: "railcraft:track.slow"});
var woodenJuncTrack = <Railcraft:track>.withTag({track: "railcraft:track.slow.junction"});
var woodenSwitchTrack = <Railcraft:track:19986>.withTag({track: "railcraft:track.slow.switch"});
var woodenWyeTrack = <Railcraft:track>.withTag({track: "railcraft:track.slow.wye"});
var woodenBoostTrack = <Railcraft:track>.withTag({track: "railcraft:track.slow.boost"});

//// Reinforsed
var reinforcedTrack = <Railcraft:track>.withTag({track: "railcraft:track.reinforced"});
var reinforcedJuncTrack = <Railcraft:track:764>.withTag({track: "railcraft:track.reinforced.junction"});
var reinforcedSwitchTrack = <Railcraft:track>.withTag({track: "railcraft:track.reinforced.switch"});
var reinforcedWyeTrack = <Railcraft:track>.withTag({track: "railcraft:track.reinforced.wye"});
var reinforcedBoostTrack = <Railcraft:track>.withTag({track: "railcraft:track.reinforced.boost"});

//// HS
var hsTrack = <Railcraft:track:816>.withTag({track: "railcraft:track.speed"});
var hsTransTrack = <Railcraft:track:26865>.withTag({track: "railcraft:track.speed.transition"});
var hsSwitchTrack = <Railcraft:track:7916>.withTag({track: "railcraft:track.speed.switch"});
var hsWyeTrack = <Railcraft:track>.withTag({track: "railcraft:track.speed.wye"});
var hsBoostTrack = <Railcraft:track>.withTag({track: "railcraft:track.speed.boost"});

//// Electric
var electricTrack = <Railcraft:track>.withTag({track: "railcraft:track.electric"});
var electricJuncTrack = <Railcraft:track>.withTag({track: "railcraft:track.electric.junction"});
var electricSwitchTrack = <Railcraft:track:10488>.withTag({track: "railcraft:track.electric.switch"});
var electricWyeTrack = <Railcraft:track>.withTag({track: "railcraft:track.electric.wye"});

//// Logic
var trackActivator = <minecraft:activator_rail>;
var trackControl = <Railcraft:track>.withTag({track: "railcraft:track.control"});
var trackCoupler = <Railcraft:track>.withTag({track: "railcraft:track.coupler"});
var trackDetector = <minecraft:detector_rail>;
var trackDetectorDir = <Railcraft:track>.withTag({track: "railcraft:track.detector.direction"});
var trackDisembarking = <Railcraft:track:23575>.withTag({track: "railcraft:track.disembarking"});
var trackDisposal = <Railcraft:track:2264>.withTag({track: "railcraft:track.disposal"});
var trackElevator = <Railcraft:track.elevator>;
var trackEmbarking = <Railcraft:track>.withTag({track: "railcraft:track.embarking"});
var trackGated = <Railcraft:track:19746>.withTag({track: "railcraft:track.gated"});
var trackGatedOneWay = <Railcraft:track>.withTag({track: "railcraft:track.gated.oneway"});
var trackLauncher = <Railcraft:track>.withTag({track: "railcraft:track.launcher"});
var trackLimiter = <Railcraft:track:16093>.withTag({track: "railcraft:track.limiter"});
var trackLocking = <Railcraft:track:20176>.withTag({track: "railcraft:track.locking"});
var trackLoco = <Railcraft:track:30516>.withTag({track: "railcraft:track.locomotive"});
var trackOneWay = <Railcraft:track:30946>.withTag({track: "railcraft:track.oneway"});
var trackPriming = <Railcraft:track:8103>.withTag({track: "railcraft:track.priming"});
var trackRouting = <Railcraft:track>.withTag({track: "railcraft:track.routing"});
var trackStop = <Railcraft:track:32363>.withTag({track: "railcraft:track.buffer.stop"});
var trackSuspended = <Railcraft:track>.withTag({track: "railcraft:track.suspended"});
var trackWhistle = <Railcraft:track>.withTag({track: "railcraft:track.whistle"});

# Recipe tweaks
recipes.remove(railStandard);
recipes.remove(railAdvanced);
recipes.remove(<Railcraft:part.rail:3>); // railHS
recipes.remove(railReinforced);
recipes.remove(railElectric);
//recipes.remove(reinforcedTrack);

mods.railcraft.RockCrusher.removeRecipe(<*>);
mods.railcraft.Rolling.removeRecipe(<*>);
mods.railcraft.BlastFurnace.removeRecipe(<*>);

recipes.remove(woodenRail);
recipes.remove(Rebar);
recipes.remove(ControllerCircuit);
recipes.remove(ReceiverCircuit);
recipes.remove(SignalCircuit);
recipes.remove(SwitchMotor);
recipes.remove(SwitchLever);

/*
Assembler.addRecipe(ControllerCircuit, <ore:circuitBasic>, PlateGold, WaterRedDye * 24, 120, 5);
Assembler.addRecipe(ControllerCircuit, <ore:circuitBasic>, PlateGold, ChemicalRedDye * 24, 120, 5);
Assembler.addRecipe(ReceiverCircuit, <ore:circuitBasic>, PlateGold, WaterGreenDye * 24, 120, 5);
Assembler.addRecipe(ReceiverCircuit, <ore:circuitBasic>, PlateGold, ChemicalGreenDye * 24, 120, 5);
Assembler.addRecipe(SignalCircuit, <ore:circuitBasic>, PlateGold, WaterYellowDye * 24, 120, 5);
Assembler.addRecipe(SignalCircuit, <ore:circuitBasic>, PlateGold, ChemicalYellowDye * 24, 120, 5);
*/

var gtMeta = [1248, 1368, 1388, 1468, 1588, 1648] as int[];
var wiresCount = [4, 8, 12, 16, 20, 24] as int[];
for i, meta in gtMeta {
	recipes.addShaped(ShuntingWire * wiresCount[i], [
		[null, PlateLead, null],
		[PlateLead, <gregtech:gt.blockmachines>.definition.makeStack(meta), PlateLead],
		[null, PlateLead, null]]);
}

recipes.addShaped(SwitchMotor, [
	[<ore:dyeRed>, <ore:dyeBlack> , <ore:dyeWhite>],
	[PistonLV, ReceiverCircuit, <ore:plateIron>]]);

recipes.addShaped(SwitchLever, [
	[<ore:dyeRed>, <ore:dyeBlack> , <ore:dyeWhite>],
	[PistonLV, <minecraft:lever>, <ore:plateIron>]]);

// upgradeLapotronLoader
recipes.remove(<Railcraft:upgrade.lapotron>);
recipes.addShaped(<Railcraft:upgrade.lapotron>, [
	[glass, glass, glass],
	[cablePlatinum, <IC2:itemBatLamaCrystal:26>, cablePlatinum],
	[glass, circuitAdvanced, glass]]);

// firebox solid
recipes.remove(<Railcraft:machine.beta:5>);
recipes.addShaped(<Railcraft:machine.beta:5>, [
	[brickAbyssal, brickAbyssal, brickAbyssal],
	[brickAbyssal, <minecraft:fire_charge>, brickAbyssal],
	[brickAbyssal, <gregtech:gt.blockmachines:103>, brickAbyssal]]);

// firebox liquid
recipes.remove(<Railcraft:machine.beta:6>);
recipes.addShaped(<Railcraft:machine.beta:6>, [
	[plateSteel, <minecraft:bucket>, plateSteel],
	[ironBars, <minecraft:fire_charge>, ironBars],
	[plateSteel, <gregtech:gt.blockmachines:101>, plateSteel]]);

recipes.remove(benchEngraving);
recipes.addShapeless(benchEngraving, [gregEngraverBasic]);
recipes.addShapeless(gregEngraverBasic, [benchEngraving]);

// detector energy
recipes.remove(<Railcraft:detector:10>);
recipes.addShaped(<Railcraft:detector:10>, [
    [plateTinAlloy, plateTinAlloy, plateTinAlloy],
    [plateTinAlloy, pressurePlateStone, plateTinAlloy],
    [plateTinAlloy, plateTinAlloy, plateTinAlloy]]);

// forceTrackEmitter
recipes.remove(<Railcraft:machine.epsilon:3>);
recipes.addShaped(<Railcraft:machine.epsilon:3>, [
	[plateTinAlloy, ingotCopper, plateTinAlloy],
	[ingotCopper, <ore:blockDiamond>, ingotCopper],
	[plateTinAlloy, ingotCopper, plateTinAlloy]]);

// fluxTransformer
recipes.remove(<Railcraft:machine.epsilon:4>);
recipes.addShaped(<Railcraft:machine.epsilon:4> * 2, [
	[plateCopper, ingotGold, plateCopper],
	[ingotGold, <ore:blockRedstone>, ingotGold],
	[plateCopper, ingotGold, plateCopper]]);

// Remove vanille recipe. Save labels.
recipes.removeShaped(Locomotive);
recipes.addShaped(Locomotive, [
	[BoilerHP, BoilerHP, BronzeFirebox],
	[BoilerHP, BoilerHP, BronzeFirebox],
	[ironBars, <minecraft:minecart>, <minecraft:minecart>]]);

// Remove vanille recipe. Save labels.
recipes.removeShaped(ELocomotive);
recipes.addShaped(ELocomotive, [
	[MachineController, circuitAdvanced, wrench],
	[circuitAdvanced, blockSteelGearCasing, circuitAdvanced],
	[motorHV, <Railcraft:cart.energy.mfe>, motorHV]]);

recipes.removeShaped(TunnelBore);	
recipes.addShaped(TunnelBore, [
	[blockSteelGearCasing, Locomotive, blockSteelGearCasing],
	[BoilerHP, Locomotive, BoilerHP],
	[hhammer, <Railcraft:cart.track.layer>, wrench]]);
	
// Coke Oven to LV age
NEI.overrideName(wetCokeBrick, "Wet Coke Oven Brick");
wetCokeBrick.displayName = "Wet Coke Oven Brick";

recipes.remove(wetCokeBrick);
mods.chisel.Groups.removeVariation(wetCokeBrick);
Mixer.addRecipe(wetCokeBrick, null, [dustClay * 4, sand * 5], water * 100, 20, 16);

recipes.remove(cokeOven);
furnace.addRecipe(cokeOven, wetCokeBrick);

recipes.remove(<Railcraft:stair>);
recipes.remove(<Railcraft:wall.alpha:1>);

// --- Backs ---
recipes.remove(backpackTrack);
Carpenter.addRecipe(backpackTrack, [
	[silkWoven, ringStainlessSteel, silkWoven],
	[silkWoven, <minecraft:rail>, silkWoven],
	[silkWoven, silkWoven, silkWoven]], <liquid:molten.enderiumbase> * 1008, 600);
Carpenter.removeRecipe(backpackTrackT2);
Carpenter.addRecipe(backpackTrackT2, [
	[<Forestry:craftingMaterial:1>, ringPlatinum, <Forestry:craftingMaterial:1>],
	[<Forestry:craftingMaterial:1>, backpackTrack, <Forestry:craftingMaterial:1>],
	[<Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>]], <liquid:molten.enderium> * 1008, 600, null);
recipes.remove(backpackIceman);
Carpenter.addRecipe(backpackIceman, [
	[silkWoven, ringStainlessSteel, silkWoven],
	[silkWoven, <minecraft:snow>, silkWoven],
	[silkWoven, silkWoven, silkWoven]], <liquid:molten.enderiumbase> * 1008, 600);
Carpenter.removeRecipe(backpackIcemanT2);
Carpenter.addRecipe(backpackIcemanT2, [
	[<Forestry:craftingMaterial:1>, ringPlatinum, <Forestry:craftingMaterial:1>],
	[<Forestry:craftingMaterial:1>, backpackIceman, <Forestry:craftingMaterial:1>],
	[<Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>]], <liquid:molten.enderium> * 1008, 600, null);
recipes.remove(backpackApothecary);
Carpenter.addRecipe(backpackApothecary, [
	[silkWoven, ringStainlessSteel, silkWoven],
	[silkWoven, <ore:potionHealing>, silkWoven],
	[silkWoven, silkWoven, silkWoven]], <liquid:molten.enderiumbase> * 1008, 600);
Carpenter.removeRecipe(backpackApothecaryT2);
Carpenter.addRecipe(backpackApothecaryT2, [
	[<Forestry:craftingMaterial:1>, ringPlatinum, <Forestry:craftingMaterial:1>],
	[<Forestry:craftingMaterial:1>, backpackApothecary, <Forestry:craftingMaterial:1>],
	[<Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>, <Forestry:craftingMaterial:1>]], <liquid:molten.enderium> * 1008, 600, null);

//TrackLayer/Relayer
recipes.remove(<Railcraft:cart.track.relayer>);
recipes.addShaped(<Railcraft:cart.track.relayer>, [
	[SignalCircuit, MachineController, SignalCircuit],
	[conveyorLV, blockSteelGearCasing, conveyorLV],
	[robotarmLV, <minecraft:minecart>, robotarmLV]]);
	
// cartTrackUndercutter
recipes.remove(<Railcraft:cart.undercutter>);
recipes.addShaped(<Railcraft:cart.undercutter>, [
	[SignalCircuit, MachineController, SignalCircuit],
	[conveyorLV, blockSteelGearCasing, conveyorLV],
	[<gregtech:gt.metaitem.01:32640>, <minecraft:minecart>, robotarmLV]]);

// cartTrackLayer
recipes.remove(<Railcraft:cart.track.layer>);
recipes.addShaped(<Railcraft:cart.track.layer>, [
	[ReceiverCircuit, MachineController, ReceiverCircuit],
	[conveyorLV, blockSteelGearCasing, conveyorLV],
	[robotarmLV, <minecraft:minecart>, robotarmLV]]);

// cartTrackRemover
recipes.remove(<Railcraft:cart.track.remover>);
recipes.addShaped(<Railcraft:cart.track.remover>, [
	[ControllerCircuit, MachineController, ControllerCircuit],
	[conveyorLV, blockSteelGearCasing, conveyorLV],
	[robotarmLV, <minecraft:minecart>, robotarmLV]]);	

// AdvItemLoader
recipes.remove(<Railcraft:machine.gamma:2>);
recipes.addShaped(<Railcraft:machine.gamma:2>, [
	[plateSteel, <minecraft:redstone>, plateSteel],
	[<minecraft:redstone>, <Railcraft:machine.gamma>, <minecraft:redstone>],
	[plateSteel, conveyorLV, plateSteel]]);

// AdvItemUnloader
recipes.remove(<Railcraft:machine.gamma:3>);
recipes.addShaped(<Railcraft:machine.gamma:3>, [
	[plateSteel, <minecraft:redstone>, plateSteel],
	[<minecraft:redstone>, <Railcraft:machine.gamma:1>, <minecraft:redstone>],
	[plateSteel, conveyorLV, plateSteel]]);

var gtIntCircuit01 = <gregtech:gt.integrated_circuit:1>;
var gtIntCircuit02 = <gregtech:gt.integrated_circuit:2>;
var gtIntCircuit03 = <gregtech:gt.integrated_circuit:3>;
var gtIntCircuit04 = <gregtech:gt.integrated_circuit:4>;

var goldFineWire = <gregtech:gt.metaitem.02:19086>;
var moltenRedstone = <liquid:molten.redstone>;

Assembler.addRecipe(railAdvanced, railStandard, goldFineWire * 2, moltenRedstone * 144, 100, 16);

// Standart
recipes.remove(standTrack);
recipes.remove(standJuncTrack);
recipes.remove(standSwitchTrack);
recipes.remove(standWyeTrack);
recipes.remove(standBoostTrack);

Assembler.addRecipe(standJuncTrack, standTrack * 2, gtIntCircuit01 * 0, 100, 16);
Assembler.addRecipe(standSwitchTrack, standTrack * 2, gtIntCircuit02 * 0, 100, 16);
Assembler.addRecipe(standWyeTrack, standTrack * 2, gtIntCircuit03 * 0, 100, 16);
Assembler.addRecipe(standBoostTrack, standTrack * 2, goldFineWire * 2, moltenRedstone * 144, 100, 16);

//Wooden
recipes.remove(woodenTrack);
recipes.remove(woodenJuncTrack);
recipes.remove(woodenSwitchTrack);
recipes.remove(woodenWyeTrack);
recipes.remove(woodenBoostTrack);

Assembler.addRecipe(woodenJuncTrack, woodenTrack * 2, gtIntCircuit01 * 0, 100, 16);
Assembler.addRecipe(woodenSwitchTrack, woodenTrack * 2, gtIntCircuit02 * 0, 100, 16);
Assembler.addRecipe(woodenWyeTrack, woodenTrack * 2, gtIntCircuit03 * 0, 100, 16);
Assembler.addRecipe(woodenBoostTrack, woodenTrack * 2, goldFineWire * 2, moltenRedstone * 144, 100, 16);

//Reinforced
recipes.remove(reinforcedTrack);
recipes.remove(reinforcedJuncTrack);
recipes.remove(reinforcedSwitchTrack);
recipes.remove(reinforcedWyeTrack);
recipes.remove(reinforcedBoostTrack);

Assembler.addRecipe(reinforcedTrack * 16, railReinforced * 3, gtIntCircuit01 * 0, <liquid:molten.concrete> * 576, 400, 4);
Assembler.addRecipe(reinforcedJuncTrack * 8, reinforcedTrack * 3, gtIntCircuit02 * 0, <liquid:molten.concrete> * 288, 100, 16);
Assembler.addRecipe(reinforcedSwitchTrack * 8, reinforcedTrack * 3, gtIntCircuit03 * 0, <liquid:molten.concrete> * 288, 100, 16);
Assembler.addRecipe(reinforcedWyeTrack * 8, reinforcedTrack * 3, gtIntCircuit04 * 0, <liquid:molten.concrete> * 288, 100, 16);
Assembler.addRecipe(reinforcedBoostTrack * 4, railReinforced, railAdvanced, <liquid:molten.concrete> * 144, 50, 16);

//HS
recipes.remove(hsTrack);
recipes.remove(hsTransTrack);
recipes.remove(hsSwitchTrack);
recipes.remove(hsWyeTrack);
recipes.remove(hsBoostTrack);

Assembler.addRecipe(hsTransTrack, hsTrack * 2, gtIntCircuit01 * 0, moltenRedstone * 144, 100, 16);
Assembler.addRecipe(hsSwitchTrack, hsTrack * 2, gtIntCircuit02 * 0, 100, 16);
Assembler.addRecipe(hsWyeTrack, hsTrack * 2, gtIntCircuit03 * 0, 100, 16);
Assembler.addRecipe(hsBoostTrack, hsTrack * 2, goldFineWire * 2, moltenRedstone * 144, 100, 16);

//Electric
recipes.remove(electricTrack);
recipes.remove(electricJuncTrack);
recipes.remove(electricSwitchTrack);
recipes.remove(electricWyeTrack);

Assembler.addRecipe(electricTrack * 16, railElectric * 3, gtIntCircuit01 * 0, <liquid:molten.concrete> * 576, 400, 4);
Assembler.addRecipe(electricJuncTrack * 8, railElectric * 3, gtIntCircuit02 * 0, <liquid:molten.concrete> * 288, 100, 16);
Assembler.addRecipe(electricSwitchTrack * 8, railElectric * 3, gtIntCircuit03 * 0, <liquid:molten.concrete> * 288, 100, 16);
Assembler.addRecipe(electricWyeTrack * 8, railElectric * 3, gtIntCircuit04 * 0, <liquid:molten.concrete> * 288, 100, 16);


recipes.remove(trackActivator);
recipes.remove(trackControl);
recipes.remove(trackCoupler);
recipes.remove(trackDetector);
recipes.remove(trackDetectorDir);
recipes.remove(trackDisembarking);
recipes.remove(trackDisposal);
recipes.remove(trackElevator);
recipes.remove(trackEmbarking);
recipes.remove(trackGated);
recipes.remove(trackGatedOneWay);
recipes.remove(trackLauncher);
recipes.remove(trackLimiter);
recipes.remove(trackLocking);
recipes.remove(trackLoco);
recipes.remove(trackOneWay);
recipes.remove(trackPriming);
recipes.remove(trackRouting);
recipes.remove(trackStop);
recipes.remove(trackSuspended);
recipes.remove(trackWhistle);

recipes.addShapeless(trackActivator, [standTrack, <minecraft:redstone_torch>]);
recipes.addShapeless(trackControl, [standBoostTrack, <minecraft:redstone>]);
recipes.addShapeless(trackCoupler, [standBoostTrack, <gregtech:gt.metatool.01:20>]);
recipes.addShapeless(trackDetector, [standBoostTrack, pressurePlateStone]);
//recipes.addShapedMirrored(trackDetectorDir, [[pressurePlateStone, standTrack, pressurePlateStone]]);
recipes.addShapedMirrored(trackDetectorDir, [[pressurePlateStone, trackDetector, pressurePlateStone]]);

recipes.addShapeless(trackDisembarking, [standBoostTrack, piston]);
recipes.addShapeless(trackDisposal, [standTrack, plateSteel]);
recipes.addShapeless(trackElevator, [standBoostTrack, railStandard]);
recipes.addShapeless(trackEmbarking, [standBoostTrack, <minecraft:ender_pearl>]);
recipes.addShapeless(trackGated, [standTrack, <minecraft:fence_gate>]);
recipes.addShapeless(trackGatedOneWay, [standBoostTrack, <minecraft:fence_gate>]);
//recipes.addShapedMirrored(trackLauncher, [[<gregtech:gt.metaitem.01:19305>, <minecraft:sticky_piston>, reinforcedTrack]]);
recipes.addShapeless(trackLimiter, [standTrack, <minecraft:comparator>]);
recipes.addShapedMirrored(trackLocking, [[pressurePlateStone, standBoostTrack, pressurePlateStone]]);
recipes.addShapeless(trackLoco, [standTrack, <Railcraft:part.signal.lamp>]);
recipes.addShapedMirrored(trackOneWay, [[piston, standTrack, pressurePlateStone]]);
//recipes.addShapeless(trackPriming, [reinforcedTrack, <minecraft:flint_and_steel>]);
recipes.addShapeless(trackRouting, [standTrack, <Railcraft:routing.ticket.gold>]);
recipes.addShapeless(trackRouting, [standTrack, <Railcraft:routing.ticket>]);
recipes.addShapeless(trackStop, [standTrack, <minecraft:iron_block>]);
recipes.addShapeless(trackSuspended, [standTrack, <gregtech:gt.metatool.01:12> * 0]);
recipes.addShapeless(trackWhistle, [standTrack, <ore:dyeYellow>, <ore:dyeBlack>]);

recipes.remove(<Railcraft:firestone.cut>);
recipes.remove(<Railcraft:firestone.refined:*>);

///////////////////////////////////////////// 2016, Alex Main
// Tanks
var mc_glass_pane  = <minecraft:glass_pane>;
var rc__iron_plate = <Railcraft:part.plate:0>;
var rc_steel_plate = <Railcraft:part.plate:1>;
var ore_pane_glass = <ore:paneGlassColorless>;
var plateIron = <ore:plateIron>;

// iron tank wall - remove railcarft recipe with only rc iron plates
recipes.removeShaped(<Railcraft:machine.beta>, [[rc__iron_plate, rc__iron_plate], [rc__iron_plate, rc__iron_plate]]);
// iron tank wall - remove gt recipe with anyIron (=wroughtIron)
recipes.removeShaped(<Railcraft:machine.beta>, [[wrench, <ore:plateIron>, <ore:plateIron>], [hhammer, <ore:plateIron>, <ore:plateIron>]]);
// iron tank wall - add recipe with plateIron oredict
recipes.addShaped(<Railcraft:machine.beta> * 8,    [[wrench, plateIron, plateIron], [hhammer, plateIron, plateIron]]);

// steel tank wall - remove rc recipe
recipes.removeShaped(<Railcraft:machine.beta:13>, [[rc_steel_plate, rc_steel_plate], [rc_steel_plate, rc_steel_plate]]);

// iron tank valve - delete recipe that has not ore dict support
recipes.removeShaped(<Railcraft:machine.beta:2>, [[ironBars, rc__iron_plate, ironBars], [rc__iron_plate, <minecraft:lever>, rc__iron_plate], [ironBars, rc__iron_plate, ironBars]]);
// iron tank valve - ore dict support
recipes.addShaped(<Railcraft:machine.beta:2> * 8, [[ironBars, plateIron, ironBars], [plateIron, <minecraft:lever>, plateIron], [ironBars, plateIron, ironBars]]);

// steel tank valve - delete recipe that has not ore dict support
recipes.removeShaped(<Railcraft:machine.beta:15>, [[ironBars, rc_steel_plate, ironBars], [rc_steel_plate, <minecraft:lever>, rc_steel_plate], [ironBars, rc_steel_plate, ironBars]]);
// steel tank valve - ore dict support
recipes.addShaped(<Railcraft:machine.beta:15> * 8, [[ironBars, plateSteel, ironBars], [plateSteel, <minecraft:lever>, plateSteel], [ironBars, plateSteel, ironBars]]);

// iron tank glass - delete recipe that has not ore dict support
recipes.removeShaped(<Railcraft:machine.beta:1>, [[mc_glass_pane, rc__iron_plate, mc_glass_pane], [rc__iron_plate, mc_glass_pane, rc__iron_plate], [mc_glass_pane, rc__iron_plate, mc_glass_pane]]);
// iron tank glass - ore dict support
recipes.addShaped(<Railcraft:machine.beta:1> * 8, [[ore_pane_glass, plateIron, ore_pane_glass], [plateIron, ore_pane_glass, plateIron], [ore_pane_glass, plateIron, ore_pane_glass]]);

// steel tank glass - delete recipe that has not ore dict support
recipes.removeShaped(<Railcraft:machine.beta:14>, [[mc_glass_pane, rc_steel_plate, mc_glass_pane], [rc_steel_plate, mc_glass_pane, rc_steel_plate], [mc_glass_pane, rc_steel_plate, mc_glass_pane]]);
// steel tank glass - ore dict support
recipes.addShaped(<Railcraft:machine.beta:14> * 8, [[ore_pane_glass, plateSteel, ore_pane_glass], [plateSteel, ore_pane_glass, plateSteel], [ore_pane_glass, plateSteel, ore_pane_glass]]);

// SLABS 0-43
var slabIngredients = [
	<Railcraft:brick.sandy:0>,
	<Railcraft:brick.infernal:0>,
	<Railcraft:cube:1>,
	<minecraft:snow>,
	<minecraft:ice>,
	<minecraft:packed_ice>,
	<ore:blockIron>,
	<ore:blockGold>,
	<ore:blockDiamond>,
	<Railcraft:brick.frostbound:0>,

	<Railcraft:brick.quarried:0>,
	<Railcraft:brick.bleachedbone:0>,
	<Railcraft:brick.bloodstained:0>,
	<Railcraft:brick.abyssal:0>,
	<Railcraft:brick.sandy:1>,
	<Railcraft:brick.infernal:1>,
	<Railcraft:brick.frostbound:1>,
	<Railcraft:brick.quarried:1>,
	<Railcraft:brick.bleachedbone:1>,
	<Railcraft:brick.bloodstained:1>,

	<Railcraft:brick.abyssal:1>,
	<Railcraft:brick.nether:1>,
	<Railcraft:brick.sandy:2>,
	<Railcraft:brick.infernal:2>,
	<Railcraft:brick.frostbound:2>,
	<Railcraft:brick.quarried:2>,
	<Railcraft:brick.bleachedbone:2>,
	<Railcraft:brick.bloodstained:2>,
	<Railcraft:brick.abyssal:2>,
	<Railcraft:brick.nether:2>,

	<Railcraft:brick.sandy:5>,
	<Railcraft:brick.infernal:5>,
	<Railcraft:brick.frostbound:5>,
	<Railcraft:brick.quarried:5>,
	<Railcraft:brick.bleachedbone:5>,
	<Railcraft:brick.bloodstained:5>,
	<Railcraft:brick.abyssal:5>,
	<Railcraft:brick.nether:5>,
	<Railcraft:cube:8>,
	<minecraft:obsidian>,

	<ore:blockCopper>,
	<ore:blockTin>,
	<ore:blockLead>,
	<ore:blockSteel>
] as minetweaker.item.IIngredient[];

val toolSaw = <ore:craftingToolSaw>;

for i, block in slabIngredients {
	var slab = <Railcraft:slab>.definition.makeStack(i);
	recipes.remove(slab);
	recipes.addShaped(slab * 2, [[toolSaw, block]]);
}
///////////////////////////////////////////// rc's bug fixes
// metal zabor must have recipe
mods.gregtech.Lathe.addRecipe([<Railcraft:post:2> * 2], <gregtech:gt.blockmachines:4128>, 150, 16);
// metal platform must have name
game.setLocalization("tile.railcraft.post.metal.unpainted.platform.name", "Unpainted Metallic Platform");

// Rebar
recipes.remove(<Railcraft:post:1>);
recipes.addShaped(<Railcraft:post:1> * 32, [	[<ore:stone>, <Railcraft:part.rebar>, <ore:stone>],
						[<ore:stone>, <Railcraft:part.rebar>, <ore:stone>],
						[<ore:stone>, <Railcraft:part.rebar>, <ore:stone>]]);

// Concreate / Wooden platform 
recipes.remove(<Railcraft:slab:2>);
CuttingSaw.addRecipe([<Railcraft:slab:2> * 2], <Railcraft:cube:1>, null, 25, 8);
CuttingSaw.addRecipe([<Railcraft:slab:38> * 2], <Railcraft:cube:8>, null, 25, 8);
recipes.remove(<Railcraft:cube:1>);
recipes.addShaped(<Railcraft:cube:1>, [[<Railcraft:slab:2>], [<Railcraft:slab:2>]]);

ChemicalBath.addRecipe([<Railcraft:part.tie>], <Forestry:slabs:*>, <liquid:creosote> * 500, [10000], 160, 5);
ChemicalBath.addRecipe([<Railcraft:part.tie>], <Forestry:slabsFireproof:*>, <liquid:creosote> * 500, [10000], 160, 5);
ChemicalBath.addRecipe([<Railcraft:part.tie>], <minecraft:wooden_slab:*>, <liquid:creosote> * 500, [10000], 160, 5);

ChemicalBath.addRecipe([<Railcraft:cube:8>], <Forestry:logs:*>, <liquid:creosote> * 500, [10000], 160, 5);
ChemicalBath.addRecipe([<Railcraft:cube:8>], <Forestry:logsFireproof:*>, <liquid:creosote> * 500, [10000], 160, 5);
ChemicalBath.addRecipe([<Railcraft:cube:8>], <minecraft:log:*>, <liquid:creosote> * 500, [10000], 160, 5);

recipes.removeShapeless(<Railcraft:post>);
recipes.addShapeless(<Railcraft:post> * 16, [<Railcraft:part.tie>]);


// Locomotive paint
var colors_as_byte = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15] as byte[];
var colors_as_ore = [
	<ore:dyeBlack>, 	//  0
	<ore:dyeRed>, 		//  1
	<ore:dyeGreen>, 	//  2
	<ore:dyeBrown>, 	//  3
	<ore:dyeBlue>, 		//  4
	<ore:dyePurple>, 	//  5
	<ore:dyeCyan>, 		//  6
	<ore:dyeLightGray>, 	//  7
	<ore:dyeGray>, 		//  8
	<ore:dyePink>, 		//  9
	<ore:dyeLime>, 		// 10
	<ore:dyeYellow>, 	// 11
	<ore:dyeLightBlue>, 	// 12
	<ore:dyeMagenta>, 	// 13
	<ore:dyeOrange>, 	// 14
	<ore:dyeWhite>, 	// 15
] as minetweaker.item.IIngredient[];

for c1, oreColor1 in colors_as_ore {
	for c2, oreColor2 in colors_as_ore {
		
		recipes.addShaped(Locomotive.withTag({model: "railcraft:default", primaryColor: c1 as byte, secondaryColor: c2 as byte}), [
			[null, oreColor1, null], 
			[null, Locomotive, null], 
			[null, oreColor2, null]]);

		recipes.addShaped(ELocomotive.withTag({model: "railcraft:default", primaryColor: c1 as byte, secondaryColor: c2 as byte}), [
			[null, oreColor1, null], 
			[null, ELocomotive, null], 
			[null, oreColor2, null]]);
	}
}

// Wire Support Frame
recipes.remove(<Railcraft:frame>);
Assembler.addRecipe(<Railcraft:frame> * 6, <Railcraft:part.rebar> * 5, <gregtech:gt.metaitem.01:17032> * 3, null, 64, 8);

// TNT Cart
recipes.addShapeless(<minecraft:tnt>, [<Railcraft:cart.tnt.wood>]);
