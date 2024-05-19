// --- Created by Jason McRay --- 
// --- Edited by Sapient ---

# Gregs SGCraft
import mods.gregtech.ChemicalReactor;
import mods.gregtech.Assembler;
import mods.ic2.Compressor;
import mods.nei.NEI;

# Aliases
var blockNaqAlloy = <SGCraft:naquadahBlock>;
var blockSGChevron = <SGCraft:stargateRing:1>;
var capacitorBankBasic = <EnderIO:blockCapBank:1>;
var capacitorDL = <EnderIO:itemBasicCapacitor:1>;
var capacitorLarge = <SGCraft:ic2Capacitor>;
var casingEV = <gregtech:gt.blockcasings:4>;
var casingMachineLuV = <gregtech:gt.blockcasings:6>;
var circuitMaster = <ore:circuitMaster>;
var conveyorEV = <gregtech:gt.metaitem.01:32633>;
var crystalSGController = <SGCraft:sgControllerCrystal>;
var crystalSGCore = <SGCraft:sgCoreCrystal>;
var dustCarbon = <gregtech:gt.metaitem.01:2010>;
var dustIron = <IC2:itemDust:5>;
var dustNaqAlloy = <gregtech:gt.metaitem.01:2325>;
var dustNaquadah = <gregtech:gt.metaitem.01:2324>;
var emitterEV = <gregtech:gt.metaitem.01:32683>;
var enderEye = <minecraft:ender_eye>;
var fieldGenEV = <gregtech:gt.metaitem.01:32673>;
var glowstone = <minecraft:glowstone_dust>;
var hullEV = <gregtech:gt.blockmachines:14>;
var ingotDarkSteel = <ore:ingotDarkSteel>;
var ingotIron = <ore:ingotIron>;
var ingotNaqAlloy = <ore:ingotNaquadahAlloy>;
var ingotNaqAlloyGT = <gregtech:gt.metaitem.01:11325>;
var ingotNaqAlloySG = <SGCraft:naquadahIngot>;
var ingotRubber = <ore:ingotRubber>;
var ingotSoularium = <ore:ingotSoularium>;
var irisBlade = <SGCraft:sgIrisBlade>;
var irisUpgrade = <SGCraft:sgIrisUpgrade>;
var lapotronChip = <gregtech:gt.metaitem.01:32714>;
var lensAmber = <gregtech:gt.metaitem.01:24514>;
var lensAqua = <gregtech:gt.metaitem.01:24543>;
var lensBlueTopaz = <gregtech:gt.metaitem.01:24513>;
var lensOpal = <gregtech:gt.metaitem.01:24510>;
var lensSapphire = <gregtech:gt.metaitem.01:24503>;
var lensTopaz = <gregtech:gt.metaitem.01:24507>;
var motorEV = <gregtech:gt.metaitem.01:32603>;
var pistonEV = <gregtech:gt.metaitem.01:32643>;
var plateChrome = <ore:plateChrome>;
var plateNaqAlloy = <ore:plateNaquadahAlloy>;
var plateRedstone = <gregtech:gt.metaitem.01:17810>;
var plateTitanium = <gregtech:gt.metaitem.01:17028>;
var redstone = <minecraft:redstone>;
var resonatorEnder = <EnderIO:itemFrankenSkull:3>;
var sandstoneChiseled = <minecraft:sandstone:1>;
var sandstoneSmooth = <minecraft:sandstone:2>;
var sensorEV = <gregtech:gt.metaitem.01:32693>;
var stargateBase = <SGCraft:stargateBase>;
var stargateChevronBlock = <SGCraft:stargateRing:1>;
var stargateController = <SGCraft:stargateController>;
var stargatePowerIC2 = <SGCraft:ic2PowerUnit>;
var stargateRing = <SGCraft:stargateRing>;
var wireNaq8x = <gregtech:gt.blockmachines:1703>;
var wireTungsten8x = <gregtech:gt.blockmachines:1549>;
// var stargateInterfaceOC = <SGCraft:ocInterface>;

# Recipe Removal
recipes.remove(ingotNaqAlloyGT);

# Recipe Tweaks
recipes.remove(stargateBase);
recipes.addShaped(stargateBase, [
    [plateNaqAlloy, sensorEV, plateNaqAlloy],
    [fieldGenEV, hullEV, fieldGenEV],
    [emitterEV, <ore:plateNeutronium>, emitterEV]]);
recipes.remove(stargateRing);
recipes.addShaped(stargateRing, [
    [plateNaqAlloy, motorEV, plateNaqAlloy],
    [emitterEV, casingEV, emitterEV],
    [conveyorEV, motorEV, conveyorEV]]);
recipes.remove(stargateChevronBlock);
recipes.addShaped(stargateChevronBlock, [
    [pistonEV, plateNaqAlloy, pistonEV],
    [emitterEV, casingEV, emitterEV],
    [motorEV, plateNaqAlloy, motorEV]]);
recipes.remove(stargateController);
recipes.addShaped(stargateController, [
    [null, sensorEV, null],
    [emitterEV, hullEV, emitterEV],
    [circuitMaster, <ore:plateNeutronium>, circuitMaster]]);
recipes.remove(capacitorLarge);
// recipes.addShaped(capacitorLarge, [
    // [plateChrome, plateChrome, plateChrome],
    // [ingotRubber, ingotRubber, ingotRubber],
    // [plateChrome, plateChrome, plateChrome]]);
recipes.addShapeless(ingotNaqAlloyGT * 9, [blockNaqAlloy]);
recipes.remove(stargatePowerIC2);
recipes.addShaped(stargatePowerIC2, [
    [<ore:batteryMaster>, wireTungsten8x, <ore:batteryMaster>],
    [wireTungsten8x, <gregtech:gt.blockmachines:14>, wireTungsten8x],
    [<ore:batteryMaster>, circuitMaster, <ore:batteryMaster>]]);
// NEI.overrideName(stargatePowerIC2, "EU Stargate Power Unit");

recipes.remove(irisBlade);
recipes.remove(irisUpgrade);
Assembler.addRecipe(irisBlade, plateTitanium * 4, dustCarbon, 2400, 64);
Assembler.addRecipe(irisUpgrade, irisBlade * 8, motorEV, 3000, 2000);
Assembler.addRecipe(<SGCraft:sgChevronUpgrade>, blockSGChevron, <gregtech:gt.metaitem.01:17129>, 6000, 8000);
recipes.remove(<SGCraft:sgChevronUpgrade>);
// recipes.remove(stargateInterfaceOC);
// recipes.addShaped(stargateInterfaceOC, [
    // [ingotIron, ingotNaqAlloy, ingotIron],
    // [<ore:oc:cable>, <ore:oc:circuitChip1>, <ore:oc:cable>],
    // [ingotIron, <ore:oc:materialCircuitBoardPrinted>, ingotIron]]);

# Recipe Creation


//Naq Alloy Block to Oredict
oreDict.blockNaquadahAlloy.add(blockNaqAlloy);

// stargateController.displayName = "DHD (Dial Home Device)";