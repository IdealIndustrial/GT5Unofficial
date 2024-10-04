// --- Importing Stuff ---

import mods.nei.NEI;
import mods.gregtech.AlloySmelter;
import mods.gregtech.Assembler;

// --- Variables ---

val StorageComponent1K = <appliedenergistics2:item.ItemMultiMaterial:35>;
val StorageComponent4K = <appliedenergistics2:item.ItemMultiMaterial:36>;
val StorageComponent16K = <appliedenergistics2:item.ItemMultiMaterial:37>;
val StorageComponent64K = <appliedenergistics2:item.ItemMultiMaterial:38>;
val StorageComponent256K = <extracells:storage.component>;
val StorageComponent1024K = <extracells:storage.component:1>;
val StorageComponent4096K = <extracells:storage.component:2>;
val StorageComponent16384K = <extracells:storage.component:3>;

val FluidSComponent1K = <extracells:storage.component:4>;
val FluidSComponent4K = <extracells:storage.component:5>;
val FluidSComponent16K = <extracells:storage.component:6>;
val FluidSComponent64K = <extracells:storage.component:7>;
val FluidSComponent256K = <extracells:storage.component:8>;
val FluidSComponent1024K = <extracells:storage.component:9>;
val FluidSComponent4096K = <extracells:storage.component:10>;

val Storage256K = <extracells:storage.physical>;
val Storage1024K = <extracells:storage.physical:1>;
val Storage4096K = <extracells:storage.physical:2>;
val Storage16384K = <extracells:storage.physical:3>;
val BlockContainer = <extracells:storage.physical:4>;

val FluidStorage1K = <extracells:storage.fluid>;
val FluidStorage4K = <extracells:storage.fluid:1>;
val FluidStorage16K = <extracells:storage.fluid:2>;
val FluidStorage64K = <extracells:storage.fluid:3>;
val FluidStorage256K = <extracells:storage.fluid:4>;
val FluidStorage1024K = <extracells:storage.fluid:5>;
val FluidStorage4096K = <extracells:storage.fluid:6>;

val UStorageHousing = <appliedenergistics2:item.ItemMultiMaterial:39>;
val UAdvStorageHousing = <extracells:storage.casing>;
val UFluidStorageHousing = <extracells:storage.casing:1>;

val CertusCircuit = <appliedenergistics2:item.ItemMultiMaterial:23>;
var GoldCircuit = <appliedenergistics2:item.ItemMultiMaterial:22>;
val DiamondCircuit = <appliedenergistics2:item.ItemMultiMaterial:24>;
var NANDChip = <gregtech:gt.metaitem.01:32700>;
val BasicCircuit = <ore:circuitBasic>;
val GoodCircuit = <ore:circuitGood>;
val AdvCircuit = <ore:circuitAdvanced>;
val DataCircuit = <ore:circuitData>;
val DataControlCircuit = <ore:circuitElite>;
val EFlow = <ore:circuitMaster>;
val DataOrb = <ore:circuitUltimate>;
var SoCchip = <gregtech:gt.metaitem.03:32047>;
var ASoCchip = <gregtech:gt.metaitem.03:32049>;

val QuartziteScrew = <ore:screwQuartzite>;
val CertusScrew = <ore:screwCertusQuartz>;
val NQuartzScrew = <ore:screwNetherQuartz>;
val CertusPlate = <gregtech:gt.metaitem.01:17516>;
val AlPlate = <ore:plateAluminium>;
val TitaniumPlate = <ore:plateTitanium>;
val StainlessPlate = <ore:plateStainlessSteel>;
val ChromePlate = <ore:plateChrome>;
val TSteelPlate = <ore:plateTungstenSteel>;
val HSLAPlate = <ore:plateHSLA>;
val ReinforcedIridiumPlate = <ore:plateAlloyIridium>;
val GlassPane = <gregtech:gt.metaitem.01:17890>;

val Screwdriver = <ore:craftingToolScrewdriver>;
val SHammer = <ore:craftingToolSoftHammer>;
val HHammer = <ore:craftingToolHardHammer>;

var mPlatinum = <liquid:molten.platinum>;
var mEuropuim = <liquid:molten.europium>;

val MEChest			= <appliedenergistics2:tile.BlockChest>;
val DEnergyCell			= <appliedenergistics2:tile.BlockDenseEnergyCell>;
val WirelessReceiver		= <appliedenergistics2:item.ItemMultiMaterial:41>;
val AnyWirelessTerminal		= <appliedenergistics2:item.ToolWirelessTerminal:*>;
val FluidTerminal		= <extracells:part.base:3>;
val WirelessFluidTerminal	= <extracells:terminal.fluid.wireless>;
val AnyWirelessFluidTerminal	= <extracells:terminal.fluid.wireless:*>;
val WirelessUniversalTerminal	= <extracells:terminal.universal.wireless>;
val AnyWirelessUniversalTerminal= <extracells:terminal.universal.wireless:*>;
val gtIntCircuit06		= <gregtech:gt.integrated_circuit:6>;

// --- Removing Recipes ---


// ||||| Cells |||||


// --- Storage Cell - 256K
recipes.remove(Storage256K);

// --- Storage Cell - 1024K
recipes.remove(Storage1024K);

// --- Storage Cell - 4096K
recipes.remove(Storage4096K);

// --- Storage Cell - 16384K
recipes.remove(Storage16384K);

// --- Storage Cell Component - 256K
recipes.remove(StorageComponent256K);

// --- Storage Cell Component - 1024K
recipes.remove(StorageComponent1024K);

// --- Storage Cell Component - 4096K
recipes.remove(StorageComponent4096K);

// --- Storage Cell Component - 16384K
recipes.remove(StorageComponent16384K);

// --- Universal Advanced Storage Housing
recipes.remove(UAdvStorageHousing);

//Certus Quartz Tank
recipes.remove(<extracells:certustank>);

//ME Fluid Pattern
recipes.remove(<extracells:pattern.fluid>);

//ME Fluid Export Bus
recipes.remove(<extracells:part.base>);

//ME Fluid Import Bus
recipes.remove(<extracells:part.base:1>);

//ME Fluid Storage Bus
recipes.remove(<extracells:part.base:2>);

//ME Fluid Terminal
recipes.remove(FluidTerminal);

//ME Fluid Level Emitter
recipes.remove(<extracells:part.base:4>);

//ME Fluid Annihilation Plane
recipes.remove(<extracells:part.base:5>);

//ME Fluid Formating Plane
recipes.remove(<extracells:part.base:6>);

//ME Drive Fixture
recipes.remove(<extracells:part.base:7>);

//ME Energy Cell Fixture
recipes.remove(<extracells:part.base:8>);

// ||||| Fluid Cells |||||


// --- Fluid Storage Cell - 1K
recipes.remove(FluidStorage1K);

// --- Fluid Storage Cell - 4K
recipes.remove(FluidStorage4K);

// --- Fluid Storage Cell - 16K
recipes.remove(FluidStorage16K);

// --- Fluid Storage Cell - 64K
recipes.remove(FluidStorage64K);

// --- Fluid Storage Cell - 256K
recipes.remove(FluidStorage256K);

// --- Fluid Storage Cell - 1024K
recipes.remove(FluidStorage1024K);

// --- Fluid Storage Cell - 4096K
recipes.remove(FluidStorage4096K);

// --- Fluid Storage Cell Component - 1K
recipes.remove(FluidSComponent1K);

// --- Fluid Storage Cell Component - 4K
recipes.remove(FluidSComponent4K);

// --- Fluid Storage Cell Component - 16K
recipes.remove(FluidSComponent16K);

// --- Fluid Storage Cell Component - 64K
recipes.remove(FluidSComponent64K);

// --- Fluid Storage Cell Component - 256K
recipes.remove(FluidSComponent256K);

// --- Fluid Storage Cell Component - 1024K
recipes.remove(FluidSComponent1024K);

// --- Fluid Storage Cell Component - 4096K
recipes.remove(FluidSComponent4096K);

// --- Universal Advanced Storage Housing
recipes.remove(UFluidStorageHousing);



// --- Adding Back Recipes ---


// ||||| Cells |||||


// --- Storage Cell - 256K
recipes.addShapeless(<extracells:storage.physical:0>, [<extracells:storage.component:0>, <extracells:storage.casing:0>]);

// --- Storage Cell - 1024K
recipes.addShapeless(<extracells:storage.physical:1>, [<extracells:storage.component:1>, <extracells:storage.casing:0>]);

// --- Storage Cell - 4096K
recipes.addShapeless(<extracells:storage.physical:2>, [<extracells:storage.component:2>, <extracells:storage.casing:0>]);

// --- Storage Cell - 16384K
recipes.addShapeless(<extracells:storage.physical:3>, [<extracells:storage.component:3>, <extracells:storage.casing:0>]);

// --- Storage Cell Component - 256K
recipes.addShapedMirrored(StorageComponent256K, [
[DataCircuit, StorageComponent64K, DataCircuit],
[StorageComponent64K, DiamondCircuit, StorageComponent64K],
[DataCircuit, StorageComponent64K, DataCircuit]]);

Assembler.addRecipe(StorageComponent256K, [DataCircuit*3, StorageComponent64K*4, DiamondCircuit], null, 180, 1920);
Assembler.addRecipe(StorageComponent256K, [DataCircuit*3, StorageComponent64K*3, ASoCchip], mEuropuim * 36, 30, 30720);

// --- Storage Cell Component - 1024K
recipes.addShapedMirrored(StorageComponent1024K, [
[DataControlCircuit, StorageComponent256K, DataControlCircuit],
[StorageComponent256K, DiamondCircuit, StorageComponent256K],
[DataControlCircuit, StorageComponent256K, DataControlCircuit]]);

Assembler.addRecipe(StorageComponent1024K, [DataControlCircuit*3, StorageComponent256K*4, DiamondCircuit], null, 180, 1920);
Assembler.addRecipe(StorageComponent1024K, [DataControlCircuit*3, StorageComponent256K*3, ASoCchip], mEuropuim * 36, 30, 30720);

// --- Storage Cell Component - 4096K
recipes.addShapedMirrored(StorageComponent4096K, [
[EFlow, StorageComponent1024K, EFlow],
[StorageComponent1024K, DiamondCircuit, StorageComponent1024K],
[EFlow, StorageComponent1024K, EFlow]]);

Assembler.addRecipe(StorageComponent4096K, [EFlow*3, StorageComponent1024K*4, DiamondCircuit], null, 180, 1920);
Assembler.addRecipe(StorageComponent4096K, [EFlow*3, StorageComponent1024K*3, ASoCchip], mEuropuim * 36, 30, 30720);

// --- Storage Cell Component - 16384K
recipes.addShapedMirrored(StorageComponent16384K, [
[DataOrb, StorageComponent4096K, DataOrb],
[StorageComponent4096K, DiamondCircuit, StorageComponent4096K],
[DataOrb, StorageComponent4096K, DataOrb]]);

Assembler.addRecipe(StorageComponent16384K, [DataOrb*3, StorageComponent4096K*4, DiamondCircuit], null, 180, 1920);
Assembler.addRecipe(StorageComponent16384K, [DataOrb*3, StorageComponent4096K*3, ASoCchip], mEuropuim * 36, 30, 30720);

// --- Universal Advanced Storage Housing
recipes.addShapedMirrored(UAdvStorageHousing, [
[HHammer, GlassPane, CertusScrew],
[ChromePlate, UStorageHousing, ChromePlate],
[CertusScrew, TSteelPlate, Screwdriver]]);
// - Alternative Recipe
recipes.addShapedMirrored(UAdvStorageHousing, [
[Screwdriver, GlassPane, CertusScrew],
[ChromePlate, UStorageHousing, ChromePlate],
[CertusScrew, TSteelPlate, HHammer]]);


// ||||| Fluid Cells |||||


// --- Fluid Storage Cell - 1K
recipes.addShapeless(<extracells:storage.fluid:0>, [<extracells:storage.component:4>, <extracells:storage.casing:1>]);

// --- Fluid Storage Cell - 4K
recipes.addShapeless(<extracells:storage.fluid:1>, [<extracells:storage.component:5>, <extracells:storage.casing:1>]);

// --- Fluid Storage Cell - 16K
recipes.addShapeless(<extracells:storage.fluid:2>, [<extracells:storage.component:6>, <extracells:storage.casing:1>]);

// --- Fluid Storage Cell - 64K
recipes.addShapeless(<extracells:storage.fluid:3>, [<extracells:storage.component:7>, <extracells:storage.casing:1>]);

// --- Fluid Storage Cell - 256K
recipes.addShapeless(<extracells:storage.fluid:4>, [<extracells:storage.component:8>, <extracells:storage.casing:1>]);

// --- Fluid Storage Cell - 1024K
recipes.addShapeless(<extracells:storage.fluid:5>, [<extracells:storage.component:9>, <extracells:storage.casing:1>]);

// --- Fluid Storage Cell - 4096K
recipes.addShapeless(<extracells:storage.fluid:6>, [<extracells:storage.component:10>, <extracells:storage.casing:1>]);

// --- Fluid Storage Cell Component - 1K
var RAMchip = <gregtech:gt.metaitem.03:32039>;
recipes.addShapedMirrored(FluidSComponent1K, [
[RAMchip, <ore:dyeBlue>, RAMchip],
[<ore:dyeBlue>, <extracells:certustank>, <ore:dyeBlue>],
[RAMchip, <ore:dyeBlue>, RAMchip]]);

Assembler.addRecipe(FluidSComponent1K, [RAMchip*3, <ore:dyeBlue>*4, <extracells:certustank>], null, 180, 1920);

// --- Fluid Storage Cell Component - 4K
recipes.addShapedMirrored(FluidSComponent4K, [
[BasicCircuit, FluidSComponent1K, BasicCircuit],
[FluidSComponent1K, <extracells:certustank>, FluidSComponent1K],
[BasicCircuit, FluidSComponent1K, BasicCircuit]]);

Assembler.addRecipe(FluidSComponent4K, [BasicCircuit*3, FluidSComponent1K*4, <extracells:certustank>], null, 180, 1920);

// --- Fluid Storage Cell Component - 16K
recipes.addShapedMirrored(FluidSComponent16K, [
[GoodCircuit, FluidSComponent4K, GoodCircuit],
[FluidSComponent4K, <extracells:certustank>, FluidSComponent4K],
[GoodCircuit, FluidSComponent4K, GoodCircuit]]);

Assembler.addRecipe(FluidSComponent16K, [GoodCircuit*3, FluidSComponent4K*4, <extracells:certustank>], null, 180, 1920);
Assembler.addRecipe(FluidSComponent16K, [RAMchip*32, <ore:dyeBlue>*32, SoCchip], mEuropuim * 36, 30, 30720);

// --- Fluid Storage Cell Component - 64K
recipes.addShapedMirrored(FluidSComponent64K, [
[AdvCircuit, FluidSComponent16K, AdvCircuit],
[FluidSComponent16K, <extracells:certustank>, FluidSComponent16K],
[AdvCircuit, FluidSComponent16K, AdvCircuit]]);

Assembler.addRecipe(FluidSComponent64K, [AdvCircuit*3, FluidSComponent16K*4, <extracells:certustank>], null, 180, 1920);
Assembler.addRecipe(FluidSComponent64K, [AdvCircuit*3, FluidSComponent16K*3, SoCchip], mEuropuim * 36, 30, 30720);

// --- Fluid Storage Cell Component - 256K
recipes.addShapedMirrored(FluidSComponent256K, [
[DataCircuit, FluidSComponent64K, DataCircuit],
[FluidSComponent64K, <extracells:certustank>, FluidSComponent64K],
[DataCircuit, FluidSComponent64K, DataCircuit]]);

Assembler.addRecipe(FluidSComponent256K, [DataCircuit*3, FluidSComponent64K*4, <extracells:certustank>], null, 180, 1920);
Assembler.addRecipe(FluidSComponent256K, [DataCircuit*3, FluidSComponent64K*3, ASoCchip], mEuropuim * 36, 30, 30720);

// --- Fluid Storage Cell Component - 1024K
recipes.addShapedMirrored(FluidSComponent1024K, [
[DataControlCircuit, FluidSComponent256K, DataControlCircuit],
[FluidSComponent256K, <extracells:certustank>, FluidSComponent256K],
[DataControlCircuit, FluidSComponent256K, DataControlCircuit]]);

Assembler.addRecipe(FluidSComponent1024K, [DataControlCircuit*3, FluidSComponent256K*4, <extracells:certustank>], null, 180, 1920);
Assembler.addRecipe(FluidSComponent1024K, [DataControlCircuit*3, FluidSComponent256K*3, ASoCchip], mEuropuim * 36, 30, 30720);

// --- Fluid Storage Cell Component - 4096K
recipes.addShapedMirrored(FluidSComponent4096K, [
[EFlow, FluidSComponent1024K, EFlow],
[FluidSComponent1024K, <extracells:certustank>, FluidSComponent1024K],
[EFlow, FluidSComponent1024K, EFlow]]);

Assembler.addRecipe(FluidSComponent4096K, [EFlow*3, FluidSComponent1024K*4, <extracells:certustank>], null, 180, 1920);
Assembler.addRecipe(FluidSComponent4096K, [EFlow*3, FluidSComponent1024K*3, ASoCchip], mEuropuim * 36, 30, 30720);

// --- Universal Fluid Storage Housing
recipes.addShapedMirrored(UFluidStorageHousing, [
[HHammer, CertusPlate, NQuartzScrew],
[AlPlate, GlassPane, AlPlate],
[NQuartzScrew, StainlessPlate, Screwdriver]]);

recipes.addShapedMirrored(UFluidStorageHousing, [
[Screwdriver, CertusPlate, NQuartzScrew],
[AlPlate, GlassPane, AlPlate],
[NQuartzScrew, StainlessPlate, HHammer]]);

Assembler.addRecipe(UFluidStorageHousing, [CertusPlate, GlassPane, AlPlate * 2, <gregtech:gt.metaitem.01:27522> * 2, StainlessPlate, gtIntCircuit06 * 0], null, 90, 20);

// --- Quartz Tank
AlloySmelter.addRecipe(<extracells:certustank>, <appliedenergistics2:tile.BlockQuartzGlass> * 8, <gregtech:gt.metaitem.01:32308> * 0, 800, 32);

// --- ME Fluid Pattern
recipes.addShapeless(<extracells:pattern.fluid>, [<extracells:storage.casing:1>, <ore:bucketEmpty>]);

// --- ME Fluid Export Bus
recipes.addShapeless(<extracells:part.base>, [<appliedenergistics2:item.ItemMultiPart:260>, <ore:plateLapis>, <ore:plateLapis>, <ore:plateLapis>]);

// --- ME Fluid Import Bus
recipes.addShapeless(<extracells:part.base:1>, [<appliedenergistics2:item.ItemMultiPart:240>, <ore:plateLapis>, <ore:plateLapis>, <ore:plateLapis>]);

// --- ME Fluid Storage Bus
recipes.addShaped(<extracells:part.base:2>, [
[<appliedenergistics2:item.ItemMultiPart:440>, <minecraft:piston>, <minecraft:sticky_piston>],
[<appliedenergistics2:item.ItemMultiMaterial:22>, <ore:plateLapis>, <ore:plateLapis>],
[null, null, null]]);

recipes.addShaped(<extracells:part.base:2>, [
[<appliedenergistics2:tile.BlockInterface>, <minecraft:piston>, <minecraft:sticky_piston>],
[<appliedenergistics2:item.ItemMultiMaterial:22>, <ore:plateLapis>, <ore:plateLapis>],
[null, null, null]]);

// --- ME Fluid Terminal
recipes.addShaped(FluidTerminal, [
[<ore:stickLapis>, QuartziteScrew, <appliedenergistics2:item.ItemMultiMaterial:43>],
[<ore:itemIlluminatedPanel>, <ore:circuitGood>, CertusPlate],
[<ore:stickLapis>, Screwdriver, <appliedenergistics2:item.ItemMultiMaterial:44>]]);

// --- ME Wireless Fluid Terminal
recipes.remove(WirelessFluidTerminal);
recipes.addShaped(WirelessFluidTerminal, [
[WirelessReceiver, FluidTerminal, WirelessReceiver],
[<ore:plateNetherQuartz>, DiamondCircuit, <ore:plateNetherQuartz>],
[<ore:plateNetherQuartz>, DEnergyCell, <ore:plateNetherQuartz>]]);

// --- ME Wireless Universal Terminal
recipes.remove(AnyWirelessUniversalTerminal);
//recipes.addShapeless(WirelessUniversalTerminal, [AnyWirelessTerminal, AnyWirelessFluidTerminal]);

recipes.addShapeless(<extracells:terminal.universal.wireless>.withTag({modules:3}), [AnyWirelessTerminal, AnyWirelessFluidTerminal]);



// --- ME Fluid Level Emitter
recipes.addShapeless(<extracells:part.base:4>, [<appliedenergistics2:item.ItemMultiPart:280>, <ore:plateLapis>]);

// --- ME Fluid Annihilation Plane
recipes.addShapeless(<extracells:part.base:5>, [<appliedenergistics2:item.ItemMultiPart:300>, <ore:plateLapis>, <ore:plateLapis>, <ore:plateLapis>]);

// --- ME Fluid Formating Plane
recipes.addShapeless(<extracells:part.base:6>, [<appliedenergistics2:item.ItemMultiPart:320>, <ore:plateLapis>, <ore:plateLapis>, <ore:plateLapis>]);

// --- ME Drive Fixture
recipes.addShapeless(<extracells:part.base:7>, [<appliedenergistics2:tile.BlockDrive>, <ore:plateIron>, <ore:plateIron>, <ore:plateLapis>]);

// --- ME Energy Cell Fixture
recipes.addShapeless(<extracells:part.base:8>, [<appliedenergistics2:tile.BlockEnergyCell>, <ore:plateIron>, <ore:plateIron>, <ore:plateLapis>]);

// --- Hiding Stuff ---
NEI.hide(<extracells:pattern.fluid>);
NEI.addEntry(<extracells:pattern.fluid>.withTag({display: {Name: "ME Fluid Pattern [WIP]"}}));
// --- Cyrillic Search Support
NEI.addEntry(<extracells:pattern.fluid>.withTag({display: {Name: "МЭ жидкостный шаблон [WIP]"}}));

//Me Fluid Interface
var FluidInt = <extracells:ecbaseblock:0>;
var FluidIntPanel = <extracells:part.base:9>;
var Int = <appliedenergistics2:tile.BlockInterface>;

recipes.remove(FluidInt);
recipes.remove(FluidIntPanel);
recipes.addShapeless(FluidInt, [Int, <ore:dyeBlue>]);
recipes.addShapeless(FluidInt, [FluidIntPanel]);
recipes.addShapeless(FluidIntPanel, [FluidInt]);

// ME Block Container
var FormationCore = <appliedenergistics2:item.ItemMultiMaterial:43>;
recipes.remove(BlockContainer);
recipes.addShapeless(BlockContainer, [StorageComponent64K, UStorageHousing, FormationCore]);

// Portable Fluid Storage
recipes.remove(<extracells:storage.fluid.portable>);
recipes.addShapeless(<extracells:storage.fluid.portable>, [MEChest, FluidStorage1K, DEnergyCell]);