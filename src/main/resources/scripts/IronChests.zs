// --- Created by Jason McRay --- 
// --- Edited by Sapient ---

//Imports

import mods.gregtech.ArcFurnace;
import mods.gregtech.Pulverizer;
import mods.nei.NEI;

# Aliases
var chestCopper = <IronChest:BlockIronChest:3>;
var chestCrystal = <IronChest:BlockIronChest:5>;
var chestDiamond = <IronChest:BlockIronChest:2>;
var chestDirt = <IronChest:BlockIronChest:7>;
var chestGold = <IronChest:BlockIronChest:1>;
var chestIron = <IronChest:BlockIronChest:0>;
var chestObsidian = <IronChest:BlockIronChest:6>;
var chestSilver = <IronChest:BlockIronChest:4>;
var chestWood = <minecraft:chest>;
var dirt = <minecraft:dirt:*>;
var glass = <minecraft:glass>;
var modIronChests = <IronChest:BlockIronChest:*>;
var paneGlass = <minecraft:glass_pane>;
var plankWood = <ore:plankWood>;
var plateCopper = <ore:plateCopper>;
var plateDiamond = <gregtech:gt.metaitem.01:17500>;
var plateGold = <ore:plateGold>;
var plateIron = <ore:plateIron>;
var plateObsidian = <ore:plateObsidian>;
var plateSilver = <ore:plateSilver>;
var dustGraphite = <gregtech:gt.metaitem.01:2865>;
var upgCopperIron = <IronChest:copperIronUpgrade>;
var upgCopperSilver = <IronChest:copperSilverUpgrade>;
var upgDiamondCrystal = <IronChest:diamondCrystalUpgrade>;
var upgDiamondObsidian = <IronChest:diamondObsidianUpgrade>;
var upgGoldDiamond = <IronChest:goldDiamondUpgrade>;
var upgIronGold = <IronChest:ironGoldUpgrade>;
var upgSilverGold = <IronChest:silverGoldUpgrade>;
var upgWoodCopper = <IronChest:woodCopperUpgrade>;
var upgWoodIron = <IronChest:woodIronUpgrade>;
var ingotWroughIron = <gregtech:gt.metaitem.01:11304>;
var ingotAnnealedCopper = <gregtech:gt.metaitem.01:11345>;
var ingotSilver = <gregtech:gt.metaitem.01:11054>;
var ingotGold = <minecraft:gold_ingot>;
var dustDiamond = <gregtech:gt.metaitem.01:2500>;
var dustIron = <gregtech:gt.metaitem.01:2032>;
var dustSilver = <gregtech:gt.metaitem.01:2054>;
var dustGold = <gregtech:gt.metaitem.01:2086>;
var dustCopper = <gregtech:gt.metaitem.01:2035>;

# Recipe Tweaks
// Chest
recipes.removeShaped(modIronChests);
recipes.addShaped(chestCopper, [
	[plateCopper, plateCopper, plateCopper],
	[plateCopper, chestWood, plateCopper],
	[plateCopper, plateCopper, plateCopper]]);

recipes.addShaped(chestIron, [
	[plateIron, plateIron, plateIron],
	[plateIron, chestWood, plateIron],
	[plateIron, plateIron, plateIron]]);


//recipes.addShaped(chestIron, [
//	[plateIron, paneGlass, plateIron],
//	[paneGlass, chestCopper, paneGlass],
//	[plateIron, paneGlass, plateIron]]);


recipes.addShaped(chestSilver, [
	[plateSilver, plateSilver, plateSilver],
	[plateSilver, chestCopper, plateSilver],
	[plateSilver, plateSilver, plateSilver]]);

recipes.addShaped(chestGold, [
	[plateGold, plateGold, plateGold],
	[plateGold, chestIron, plateGold],
	[plateGold, plateGold, plateGold]]);

recipes.addShaped(chestDiamond, [
	[paneGlass, paneGlass, paneGlass],
	[plateDiamond, chestGold, plateDiamond],
	[paneGlass, paneGlass, paneGlass]]);

recipes.addShaped(chestCrystal, [
	[paneGlass, paneGlass, paneGlass],
	[paneGlass, chestDiamond, paneGlass],
	[paneGlass, paneGlass, paneGlass]]);

recipes.addShaped(chestObsidian, [
	[plateObsidian, plateObsidian, plateObsidian],
	[plateObsidian, chestDiamond, plateObsidian],
	[plateObsidian, plateObsidian, plateObsidian]]);

recipes.addShaped(chestDirt, [
	[dirt, dirt, dirt],
	[dirt, chestWood, dirt],
	[dirt, dirt, dirt]]);

// Upgrade
recipes.removeShaped(upgWoodCopper);
recipes.addShaped(upgWoodCopper, [
	[plateCopper, plateCopper, plateCopper],
	[plateCopper, plankWood, plateCopper],
	[plateCopper, plateCopper, plateCopper]]);

recipes.removeShaped(upgWoodIron);
recipes.addShaped(upgWoodIron, [
	[plateIron, plateIron, plateIron],
	[plateIron, plankWood, plateIron],
	[plateIron, plateIron, plateIron]]);


recipes.removeShaped(upgCopperIron);
//recipes.addShaped(upgCopperIron, [
//	[plateIron, paneGlass, plateIron],
//	[paneGlass, plateCopper, paneGlass],
//	[plateIron, paneGlass, plateIron]]);


recipes.removeShaped(upgCopperSilver);
recipes.addShaped(upgCopperSilver, [
	[plateSilver, plateSilver, plateSilver],
	[plateSilver, plateCopper, plateSilver],
	[plateSilver, plateSilver, plateSilver]]);

recipes.removeShaped(upgIronGold);
recipes.addShaped(upgIronGold, [
	[plateGold, plateGold, plateGold],
	[plateGold, plateIron, plateGold],
	[plateGold, plateGold, plateGold]]);

recipes.removeShaped(upgSilverGold);
//recipes.addShaped(upgSilverGold, [
//	[plateGold, paneGlass, plateGold],
//	[paneGlass, plateSilver, paneGlass],
//	[plateGold, paneGlass, plateGold]]);

recipes.removeShaped(upgGoldDiamond);
recipes.addShaped(upgGoldDiamond, [
	[paneGlass, paneGlass, paneGlass],
	[plateDiamond, plateGold, plateDiamond],
	[paneGlass, paneGlass, paneGlass]]);
   
recipes.removeShaped(upgDiamondCrystal);
recipes.addShaped(upgDiamondCrystal, [
	[paneGlass, paneGlass, paneGlass],
	[paneGlass, plateObsidian, paneGlass],
	[paneGlass, paneGlass, paneGlass]]);

recipes.removeShaped(upgDiamondObsidian);
recipes.addShaped(upgDiamondObsidian, [
	[plateObsidian, plateObsidian, plateObsidian],
	[plateObsidian, paneGlass, plateObsidian],
	[plateObsidian, plateObsidian, plateObsidian]]);

	
//Arc Furnace

ArcFurnace.addRecipe([ingotAnnealedCopper * 8], chestCopper, <liquid:oxygen> * 488, [10000], 1200, 96);	
ArcFurnace.addRecipe([ingotWroughIron * 8], chestIron, <liquid:oxygen> * 488, [10000], 1200, 96);
ArcFurnace.addRecipe([ingotAnnealedCopper * 8, ingotSilver * 8], chestSilver, <liquid:oxygen> * 488, [10000, 10000], 1200, 96);
ArcFurnace.addRecipe([ingotWroughIron * 8, ingotGold * 8], chestGold, <liquid:oxygen> * 488, [10000, 10000], 1200, 96);

ArcFurnace.addRecipe([ingotWroughIron * 8, ingotGold * 8, <minecraft:glass> * 2, dustGraphite * 16], chestDiamond, <liquid:oxygen> * 488, [10000, 10000, 10000, 10000], 1200, 96);
ArcFurnace.addRecipe([ingotWroughIron * 8, ingotGold * 8, <minecraft:glass> * 5, dustGraphite * 16], chestCrystal, <liquid:oxygen> * 488, [10000, 10000, 10000, 10000], 1200, 96);
ArcFurnace.addRecipe([ingotWroughIron * 8, ingotGold * 8, <minecraft:obsidian> * 8, dustGraphite * 16], chestObsidian, <liquid:oxygen> * 488, [10000, 10000, 10000, 10000], 1200, 96);


//Pulverizer
//Pulverizer.addRecipe([dustCopper * 8], chestCopper, [10000], 660, 4);
//Pulverizer.addRecipe([dustIron * 4, dustCopper * 4], chestIron, [10000, 10000], 660, 4);
//Pulverizer.addRecipe([dustDiamond * 2, dustGold * 8, dustSilver * 8, dustIron * 8], chestDiamond, [10000, 10000, 10000, 10000], 660, 4);
//Pulverizer.addRecipe([dustGold * 8, dustSilver * 8, dustIron * 8, dustCopper * 8], chestGold, [10000, 10000, 10000, 10000], 660, 4);
//Pulverizer.addRecipe([dustSilver * 8, dustIron * 8, dustCopper * 8], chestSilver, [10000, 10000, 10000], 660, 4);

// Tooltips
<minecraft:chest>.addTooltip("27 slots (9x3)");
<minecraft:trapped_chest>.addTooltip("27 slots (9x3)");
<IronChest:BlockIronChest:0>.addTooltip("54 slots (9x6)");
<IronChest:BlockIronChest:1>.addTooltip("81 slots (9x9)");
<IronChest:BlockIronChest:2>.addTooltip("108 slots (12x9)");
<IronChest:BlockIronChest:3>.addTooltip("45 slots (9x5)");
<IronChest:BlockIronChest:4>.addTooltip("72 slots (9x8)");
<IronChest:BlockIronChest:5>.addTooltip("108 slots (12x9)");
<IronChest:BlockIronChest:6>.addTooltip("108 slots (12x9)");