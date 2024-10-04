/* IIS by Sapient, 2016, v1.09.1 */
import mods.nei.NEI;
import minetweaker.item.IItemStack; 

var blazePowder = <minecraft:blaze_powder>;
var blazeRod = <minecraft:blaze_rod>;
var chest = <minecraft:chest>;
var gemNetherQuartz = <ore:gemNetherQuartz>;
var glass = <ore:blockGlass>;
var logWood = <ore:logWood>;
var potionHealing = <ore:potionHealing>;
var slabWood = <ore:slabWood>;

potionHealing.add(<minecraft:potion:8197>);
potionHealing.add(<minecraft:potion:8261>);
potionHealing.add(<minecraft:potion:8229>);

recipes.remove(<minecraft:ender_chest>);
NEI.hide(<minecraft:ender_chest>);
recipes.remove(<minecraft:potion:*>);
NEI.hide(<minecraft:potion:*>);
recipes.remove(<minecraft:brewing_stand>);
NEI.hide(<minecraft:brewing_stand>);

recipes.remove(<minecraft:repeater>);
recipes.remove(<minecraft:comparator>);
recipes.remove(<minecraft:tnt_minecart>);
recipes.remove(<minecraft:hopper_minecart>);
recipes.remove(<minecraft:rail>);
recipes.remove(<minecraft:golden_rail>);
recipes.remove(<minecraft:dispenser>);
recipes.remove(<minecraft:dropper>);

recipes.removeShapeless(<ore:gemLapis>);
recipes.removeShapeless(<ore:dustLapis>);
recipes.removeShapeless(<ore:gemCoal>);
recipes.removeShapeless(<ore:dustCoal>);

recipes.removeShapeless(blazePowder * 2, [blazeRod]);
//recipes.addShapeless(blazePowder, [blazeRod]);

recipes.remove(chest * 4);
recipes.addShaped(chest * 2, [
    [logWood, logWood, logWood],
    [logWood, null, logWood],
    [logWood, logWood, logWood]]);
recipes.addShaped(chest * 4, [
    [logWood, logWood, logWood],
    [logWood, <ore:craftingToolSaw>, logWood],
    [logWood, logWood, logWood]]);

recipes.remove(<minecraft:daylight_detector>);
recipes.addShaped(<minecraft:daylight_detector>, [
    [glass, glass, glass],
    [gemNetherQuartz, gemNetherQuartz, gemNetherQuartz],
    [slabWood, slabWood, slabWood]]);

recipes.addShaped(<minecraft:flint>, [[<minecraft:gravel>, <minecraft:gravel>], [<minecraft:gravel>, <minecraft:gravel>]]);

// add wood button recipe from wooden planks as stone analogy
recipes.addShapeless(<minecraft:wooden_button>, [<ore:plankWood>]);
// remove double buttons recipe from stone
recipes.remove(<minecraft:stone_button> * 2);

// remove only unsaw recipe
recipes.remove(<minecraft:wooden_slab> * 6);

// add oredict for vanillaplanks (was deleted by UNKNOWN mod, probably GT)

var meta = [0, 1, 2, 3, 4, 5] as int[];
for i in meta {
	var Plank = <minecraft:planks>.definition.makeStack(i);
	<ore:plankWood>.add(Plank);
}

// add saw to slabs recipes
var blocks = [
	<minecraft:stone>,
	<minecraft:sandstone>,
	<minecraft:cobblestone>,
	<minecraft:brick_block>,
	<minecraft:stonebrick>,
	<minecraft:nether_brick>,
	<minecraft:quartz_block>
] as IItemStack[];
var slabs = [
	<minecraft:stone_slab>,
	<minecraft:stone_slab:1>,
	<minecraft:stone_slab:3>,
	<minecraft:stone_slab:4>,
	<minecraft:stone_slab:5>,
	<minecraft:stone_slab:6>,
	<minecraft:stone_slab:7>
] as IItemStack[];
for i, slab in slabs {
	recipes.removeShaped(slab * 6);
	recipes.addShaped(slab * 2, [[<ore:craftingToolSaw>, blocks[i]]]);
}

recipes.remove(<minecraft:tripwire_hook>);
recipes.addShaped(<minecraft:tripwire_hook>, [[<ore:ringIron>, null, <ore:ringIron>], 
						[<ore:stickWood>, <ore:plankWood>, <ore:stickWood>]]);

// Mineral paints
<ore:dyeBlack>.add(<gregtech:gt.metaitem.01:2535>);
<ore:dyeBlack>.add(<gregtech:gt.metaitem.01:2536>);
<ore:dyeBlack>.add(<gregtech:gt.metaitem.01:2816>);
<ore:dyeBlack>.add(<gregtech:gt.metaitem.01:2870>);
<ore:dyeGray>.add(<gregtech:gt.metaitem.01:2815>);
<ore:dyeGreen>.add(<gregtech:gt.metaitem.01:2871>);
<ore:dyeGreen>.add(<gregtech:gt.metaitem.01:2933>);
<ore:dyeGreen>.add(<gregtech:gt.metaitem.01:2906>);
<ore:dyeWhite>.add(<gregtech:gt.metaitem.01:2823>);
<ore:dyeWhite>.add(<gregtech:gt.metaitem.01:2617>);
<ore:dyeWhite>.add(<gregtech:gt.metaitem.01:2904>);
<ore:dyeWhite>.add(<gregtech:gt.metaitem.01:2622>);
<ore:dyeWhite>.add(<gregtech:gt.metaitem.01:2618>);
<ore:dyeRed>.add(<gregtech:gt.metaitem.01:2826>);
<ore:dyeRed>.add(<gregtech:gt.metaitem.01:2917>);
<ore:dyeYellow>.add(<gregtech:gt.metaitem.01:2614>);
<ore:dyeBrown>.add(<gregtech:gt.metaitem.01:2538>);

// Glistering Melon
recipes.remove(<minecraft:speckled_melon>);
recipes.addShaped(<minecraft:speckled_melon>, [
    [<ore:nuggetGold>, <ore:nuggetGold>, <ore:nuggetGold>],
    [<ore:nuggetGold>, <minecraft:melon>, <ore:nuggetGold>],
    [<ore:nuggetGold>, <ore:nuggetGold>, <ore:nuggetGold>]]);

<minecraft:lapis_block>.displayName = "Block of Lapis";
<minecraft:dye:4>.displayName = "Lapis";

// Saddle
recipes.addShaped(<minecraft:saddle>, [
    [<minecraft:string>, <ore:craftingToolKnife>, <minecraft:string>],
    [<minecraft:leather>, <minecraft:leather>, <minecraft:leather>],
    [<minecraft:leather>, <ore:blockWool>, <minecraft:leather>]]);
