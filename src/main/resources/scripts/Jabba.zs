// --- Created by Jason McRay --- 
// --- Edited by Sapient ---

# Aliases
var chestEnder = <minecraft:ender_chest>;
var dolly = <JABBA:mover>;
var HHammer = <ore:craftingToolHardHammer>;
var piston = <minecraft:piston>;
var plankWood = <ore:plankWood>;
var plateEnderium = <ore:plateEnderium>;
var plateIron = <ore:plateIron>;
var tuningFork = <JABBA:tuningFork>;
var upgradeBSpace = <JABBA:upgradeCore:1>;
var wrench = <ore:craftingToolWrench>;
var hammerBarrel = <JABBA:hammer>;
var ingotIron = <ore:ingotIron>;
var stickWood = <ore:stickWood>;
var upgradeStorage9x = <JABBA:upgradeCore:5>;
var upgradeStorage27x = <JABBA:upgradeCore:6>;
var betterBarrel = <JABBA:barrel>;
var diadolly = <JABBA:moverDiamond>;
var steelWheels = <gregtech:gt.metaitem.01:32101>;
var stickSteel = <ore:stickSteel>;
var plateSteel = <ore:plateSteel>;

# Blocks/Items Removal

recipes.remove(upgradeBSpace);
upgradeBSpace.addTooltip(format.red(format.bold("This item is DISABLED!")));

recipes.remove(tuningFork);
tuningFork.addTooltip(format.red(format.bold("This item is DISABLED!")));

diadolly.addTooltip(format.red(format.bold("This item is DISABLED!")));

# Recipe Tweaks
recipes.remove(hammerBarrel);
recipes.addShaped(hammerBarrel, [
	[ingotIron, ingotIron, ingotIron],
	[ingotIron, stickWood, ingotIron],
	[HHammer, stickWood, null]]);

recipes.addShaped(hammerBarrel, [
	[ingotIron, ingotIron, ingotIron],
	[ingotIron, stickWood, ingotIron],
	[null, stickWood, HHammer]]);

recipes.remove(dolly);
recipes.addShaped(dolly, [
	[null, wrench, stickSteel],
	[HHammer, plankWood, stickSteel],
	[plateSteel, plateSteel, steelWheels]]);

recipes.addShaped(upgradeStorage9x * 2, [
    [plateEnderium, piston, plateEnderium],
    [plateEnderium, betterBarrel, plateEnderium],
    [plateEnderium, piston, plateEnderium]]);
