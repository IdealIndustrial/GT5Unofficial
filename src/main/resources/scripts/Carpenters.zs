//Created by DreamMasterXXL
// --- Edited by *error user name found* ---
// --- Edited by Sapient ---

// --- Variables ---
val CraftingTable = <minecraft:crafting_table>;
val Torch = <minecraft:torch>;
// -- <minecraft:hardened_clay>;
val HardenedClay = <ore:craftingHardenedClay>;

val CarpentersBlock = <CarpentersBlocks:blockCarpentersBlock>;
val CollapsibleBlock = <CarpentersBlocks:blockCarpentersCollapsibleBlock>;
val CBSafe = <CarpentersBlocks:blockCarpentersSafe>;
val CBLightSensor = <CarpentersBlocks:blockCarpentersDaylightSensor>;
val CBChisel = <CarpentersBlocks:itemCarpentersChisel>;
val CBHammer = <CarpentersBlocks:itemCarpentersHammer>;
val CBTorch = <CarpentersBlocks:blockCarpentersTorch>;
val CBTile = <CarpentersBlocks:itemCarpentersTile>;
val CBBarrier = <CarpentersBlocks:blockCarpentersBarrier>;
val CBGate = <CarpentersBlocks:blockCarpentersGate>;
val CBLadder = <CarpentersBlocks:blockCarpentersLadder>;
val CBGarage = <CarpentersBlocks:blockCarpentersGarageDoor>;

val Frame = <ore:frameGtWood>;
val Plank = <ore:plankWood>;
val Log = <ore:logWood>;

val IronScrew = <ore:screwIron>;
val SteelScrew = <ore:screwSteel>;

val IC2Safe = <IC2:blockPersonal>;
val IC2Scaffold = <IC2:blockScaffold>;

val Flint = <minecraft:flint>;
val MCDaylightSensor = <minecraft:daylight_detector>;
val Stick = <minecraft:stick>;
val Redstone = <ore:dustRedstone>;

val Saw = <ore:craftingToolSaw>;//--10
val HHammer = <ore:craftingToolHardHammer>;//--12
val SHammer = <ore:craftingToolSoftHammer>;//--14
val Wrench = <ore:craftingToolWrench>;//--16
val File = <ore:craftingToolFile>;//--18
val Screwdriver = <ore:craftingToolScrewdriver>;//--22
val Mortar = <ore:craftingToolMortar>;//--24
val Knife = <ore:craftingToolKnife>;//--34
val WireCutter = <ore:craftingToolWireCutter>;//--??

// -- remove Recipes

// --- CBBarrier
recipes.remove(CBBarrier);

// --- Gate
recipes.remove(CBGate);

//Chisel
recipes.remove(CBChisel);
//Hammer
recipes.remove(CBHammer);
//Torch
recipes.remove(<CarpentersBlocks:blockCarpentersTorch>);
//Carpenters Tile
recipes.remove(<CarpentersBlocks:itemCarpentersTile>);
//Collapsible Block
//recipes.remove(<CarpentersBlocks:blockCarpentersCollapsibleBlock>);
//Safe
recipes.remove(<CarpentersBlocks:blockCarpentersSafe>);
//Barrier
recipes.remove(<CarpentersBlocks:blockCarpentersBarrier>);
//Ladder
recipes.remove(CBLadder);
//Daylight Detector
recipes.remove(<CarpentersBlocks:blockCarpentersDaylightSensor>);
//Garage Door
recipes.remove(CBGarage);

//add Recipes

//Chisel
recipes.addShaped(CBChisel, [
[null, null, <ore:toolHeadChiselIron>],
[null, <ore:stickIron>, null],
[CarpentersBlock,null, null]]);
//Hammer
recipes.addShaped(CBHammer, [
[null, null, <ore:toolHeadHammerIron>],
[null, <ore:stickIron>, null],
[CarpentersBlock, null, null]]);

// Torch
recipes.addShaped(CBTorch * 8, [
[Torch, Torch, Torch],
[Torch, CarpentersBlock, Torch],
[Torch, Torch, Torch]]);

//Carpenters Tile
recipes.addShaped(CBTile * 6, [
[HardenedClay, HardenedClay, HardenedClay],
[CarpentersBlock, CarpentersBlock, CarpentersBlock],
[null, null, null]]);

//Safe
recipes.addShaped(CBSafe, [
[CarpentersBlock, CarpentersBlock, CarpentersBlock],
[CarpentersBlock, <ore:craftingSafe>, CarpentersBlock],
[CarpentersBlock, CarpentersBlock, CarpentersBlock]]);

//Barrier
recipes.addShaped(CBBarrier * 4, [
[CarpentersBlock, <ore:stickWood>, CarpentersBlock],
[CarpentersBlock, <ore:stickWood>, CarpentersBlock],
[CarpentersBlock, null, CarpentersBlock]]);

//Ladder
recipes.addShaped(CBLadder * 2, [
[CarpentersBlock, <ore:stickWood>, CarpentersBlock],
[CarpentersBlock, CarpentersBlock, CarpentersBlock],
[CarpentersBlock, <ore:stickWood>, CarpentersBlock]]);

//Daylight Detector
recipes.addShaped(CBLightSensor, [
[<ore:wireFineRedAlloy>, <minecraft:daylight_detector>, <ore:wireFineRedAlloy>],
[<ore:wireFineRedAlloy>, <ore:plateLapis>, <ore:wireFineRedAlloy>],
[CarpentersBlock, CarpentersBlock, CarpentersBlock]]);

//Garage Door
recipes.addShaped(CBGarage * 5, [
[CarpentersBlock, <ore:plateIron>, CarpentersBlock],
[<ore:plateIron>, CarpentersBlock, <ore:plateIron>],
[CarpentersBlock, <ore:plateIron>, CarpentersBlock]]);

// recipes.addShaped(CarpentersBlock * 5, [
// [Saw, IC2Scaffold],
// [SHammer, File]]);

// --- Crafting Table
recipes.addShaped(CraftingTable, [
[CarpentersBlock, CarpentersBlock],
[CarpentersBlock, CarpentersBlock]]);
//-
recipes.addShapeless(CraftingTable, [CarpentersBlock, Saw, Knife, SHammer]);

// --- CBBarrier
recipes.addShaped(CBBarrier * 2, [
[null, null, null],
[Stick, CarpentersBlock, Stick],
[Stick, CarpentersBlock, Stick]]);
// -
recipes.addShaped(CBBarrier * 4, [
[IronScrew, Screwdriver, IronScrew],
[Stick, CarpentersBlock, Stick],
[Stick, CarpentersBlock, Stick]]);
// -
recipes.addShaped(CBBarrier * 6, [
[SteelScrew, Screwdriver, SteelScrew],
[Stick, CarpentersBlock, Stick],
[Stick, CarpentersBlock, Stick]]);
// -from MC.zs
recipes.addShaped(CBBarrier, [
[null, null, null],
[Stick, Plank, Stick],
[Stick, Plank, Stick]]);
// -
recipes.addShaped(CBBarrier * 2, [
[IronScrew, Screwdriver, IronScrew],
[Stick, Plank, Stick],
[Stick, Plank, Stick]]);
// -
recipes.addShaped(CBBarrier * 4, [
[SteelScrew, Screwdriver, SteelScrew],
[Stick, Plank, Stick],
[Stick, Plank, Stick]]);


// --- Gate
recipes.addShaped(CBGate * 2, [
[null, null, null],
[CarpentersBlock, Stick, CarpentersBlock],
[CarpentersBlock, Stick, CarpentersBlock]]);
// -
recipes.addShaped(CBGate * 4, [
[IronScrew, Screwdriver, IronScrew],
[CarpentersBlock, Stick, CarpentersBlock],
[CarpentersBlock, Stick, CarpentersBlock]]);
// -
recipes.addShaped(CBGate * 6, [
[SteelScrew, Screwdriver, SteelScrew],
[CarpentersBlock, Stick, CarpentersBlock],
[CarpentersBlock, Stick, CarpentersBlock]]);
// -from MC.zs
recipes.addShaped(CBGate, [
[Flint, null, Flint],
[Plank, Stick, Plank],
[Plank, Stick, Plank]]);
// -
recipes.addShaped(CBGate * 2, [
[IronScrew, Screwdriver, IronScrew],
[Plank, Stick, Plank],
[Plank, Stick, Plank]]);
// -
recipes.addShaped(CBGate * 4, [
[SteelScrew, Screwdriver, SteelScrew],
[Plank, Stick, Plank],
[Plank, Stick, Plank]]);