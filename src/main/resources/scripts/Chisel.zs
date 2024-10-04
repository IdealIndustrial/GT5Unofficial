import mods.chisel.Groups;
import minetweaker.item.IItemStack;


//******* removes
<ore:cobblestone>.remove(<minecraft:mossy_cobblestone>);
<ore:chest>.remove(<minecraft:ender_chest>);
recipes.remove(<chisel:valentines>);
recipes.remove(<minecraft:sandstone>);

//******* changing of recipes
recipes.remove(<chisel:diamondChisel>);
recipes.addShaped(<chisel:diamondChisel>, [[<ore:plateDiamond>], [<chisel:chisel>]]);
recipes.remove(<chisel:obsidianChisel>);
recipes.addShaped(<chisel:obsidianChisel>, [[<ore:plateObsidian>], [<chisel:chisel>]]);

var plateIron = <gregtech:gt.metaitem.01:17032>;
var plateGold = <gregtech:gt.metaitem.01:17086>;
var stone = <minecraft:stone>;
recipes.remove(<chisel:factoryblock>);
recipes.addShaped(<chisel:factoryblock> * 8 /* original 32 */, [[stone, plateIron, stone], [plateIron, null, plateIron], [stone, plateIron, stone]]);

recipes.remove(<chisel:fantasyblock>);
recipes.addShaped(<chisel:fantasyblock> * 8, [[stone, stone, stone], [stone, plateGold, stone], [stone, stone, stone]]);

recipes.remove(<chisel:tyrian>);
recipes.addShaped(<chisel:tyrian> * 8, [[stone, stone, stone], [stone, plateIron, stone], [stone, stone, stone]]);

recipes.remove(<chisel:road_line>);
mods.gregtech.CuttingSaw.addRecipe([<chisel:road_line> * 4], <minecraft:carpet>, null, 25, 4);

// add saw to slabs recipes
var blocks = [
	<chisel:limestone>,
	<chisel:marble>,
	<chisel:marble_pillar>
] as IItemStack[];
var slabs = [
	<chisel:limestone_slab>,
	<chisel:marble_slab>,
	<chisel:marble_pillar_slab>
] as IItemStack[];
for i, slab in slabs {
	recipes.removeShaped(slab * 6);
	recipes.remove(slab);
	recipes.addShaped(slab * 2, [[<ore:craftingToolSaw>, blocks[i]]]);
} 

// railcraft quarried stone
var stoneQuarried_railcraft = <Railcraft:cube:7>;
var stoneMarble_UBC = <UndergroundBiomes:metamorphicStone:2>;
recipes.addShaped(stoneQuarried_railcraft * 4, [
	[null, stoneMarble_UBC, null],
	[stoneMarble_UBC, null, stoneMarble_UBC],
	[null, stoneMarble_UBC, null]]);
recipes.addShaped(stoneMarble_UBC * 4, [
	[null, stoneQuarried_railcraft, null],
	[stoneQuarried_railcraft, null, stoneQuarried_railcraft],
	[null, stoneQuarried_railcraft, null]]);


//******* groups
// fix reshaping cobbles wtih chisel unification
Groups.removeGroup("cobblestone");
Groups.addGroup("cobblestones");
Groups.addVariation("cobblestones", <minecraft:cobblestone>);
Groups.addVariation("cobblestones", <chisel:cobblestone:1>);
Groups.addVariation("cobblestones", <chisel:cobblestone:2>);
Groups.addVariation("cobblestones", <chisel:cobblestone:3>);
Groups.addVariation("cobblestones", <chisel:cobblestone:4>);
Groups.addVariation("cobblestones", <chisel:cobblestone:5>);
Groups.addVariation("cobblestones", <chisel:cobblestone:6>);
Groups.addVariation("cobblestones", <chisel:cobblestone:7>);
Groups.addVariation("cobblestones", <chisel:cobblestone:8>);
Groups.addVariation("cobblestones", <chisel:cobblestone:9>);
Groups.addVariation("cobblestones", <chisel:cobblestone:10>);
Groups.addVariation("cobblestones", <chisel:cobblestone:11>);
Groups.addVariation("cobblestones", <chisel:cobblestone:12>);
Groups.addVariation("cobblestones", <chisel:cobblestone:13>);
Groups.addVariation("cobblestones", <chisel:cobblestone:14>);
Groups.addVariation("cobblestones", <chisel:cobblestone:15>);

Groups.removeGroup("glass");
Groups.addGroup("glass2");
Groups.addVariation("glass2", <minecraft:glass>);
Groups.addVariation("glass2", <chisel:glass2>);
Groups.addVariation("glass2", <chisel:glass:1>);
Groups.addVariation("glass2", <chisel:glass:2>);
Groups.addVariation("glass2", <chisel:glass:3>);
Groups.addVariation("glass2", <chisel:glass:4>);
Groups.addVariation("glass2", <chisel:glass:5>);
Groups.addVariation("glass2", <chisel:glass:6>);
Groups.addVariation("glass2", <chisel:glass:7>);
Groups.addVariation("glass2", <chisel:glass:8>);
Groups.addVariation("glass2", <chisel:glass:9>);
Groups.addVariation("glass2", <chisel:glass:10>);
Groups.addVariation("glass2", <chisel:glass:11>);
Groups.addVariation("glass2", <chisel:glass:12>);
Groups.addVariation("glass2", <chisel:glass:13>);
Groups.addVariation("glass2", <chisel:glass:14>);
Groups.addVariation("glass2", <chisel:glass:15>);

Groups.removeGroup("end_stone");
Groups.addGroup("end_stones");
Groups.addVariation("end_stones", <minecraft:end_stone>);
Groups.addVariation("end_stones", <chisel:end_Stone:1>);
Groups.addVariation("end_stones", <chisel:end_Stone:2>);
Groups.addVariation("end_stones", <chisel:end_Stone:3>);
Groups.addVariation("end_stones", <chisel:end_Stone:4>);
Groups.addVariation("end_stones", <chisel:end_Stone:5>);
Groups.addVariation("end_stones", <chisel:end_Stone:6>);
Groups.addVariation("end_stones", <chisel:end_Stone:7>);
Groups.addVariation("end_stones", <chisel:end_Stone:8>);
Groups.addVariation("end_stones", <chisel:end_Stone:9>);
Groups.addVariation("end_stones", <chisel:end_Stone:10>);
Groups.addVariation("end_stones", <chisel:end_Stone:11>);
Groups.addVariation("end_stones", <chisel:end_Stone:12>);
Groups.addVariation("end_stones", <chisel:end_Stone:13>);

// marble support: gt and undrground biomes
Groups.removeGroup("marble");
Groups.addGroup("marble");
Groups.addVariation("marble", <UndergroundBiomes:metamorphicStone:2>);
Groups.addVariation("marble", <chisel:marble:0>);
Groups.addVariation("marble", <chisel:marble:1>);
Groups.addVariation("marble", <chisel:marble:2>);
Groups.addVariation("marble", <chisel:marble:3>);
Groups.addVariation("marble", <chisel:marble:4>);
Groups.addVariation("marble", <chisel:marble:5>);
Groups.addVariation("marble", <chisel:marble:6>);
Groups.addVariation("marble", <chisel:marble:7>);
Groups.addVariation("marble", <chisel:marble:8>);
Groups.addVariation("marble", <chisel:marble:9>);
Groups.addVariation("marble", <chisel:marble:10>);
Groups.addVariation("marble", <chisel:marble:11>);
Groups.addVariation("marble", <chisel:marble:12>);
Groups.addVariation("marble", <chisel:marble:13>);
Groups.addVariation("marble", <chisel:marble:14>);
Groups.addVariation("marble", <chisel:marble:15>);
Groups.addVariation("marble", <gregtech:gt.blockstones:0>);

// UB & GT basalt
Groups.addGroup("basalt");
Groups.addVariation("basalt", <UndergroundBiomes:igneousStone:5>);
Groups.addVariation("basalt", <gregtech:gt.blockstones:8>);


//******* variations
// add UB andesite
recipes.remove(<chisel:andesite>);
Groups.addVariation("andesite", <UndergroundBiomes:igneousStone:3>);

// add GT & RC concrete
furnace.remove(<chisel:concrete>);
Groups.addVariation("concrete", <gregtech:gt.blockconcretes>);
Groups.addVariation("concrete", <Railcraft:cube:1>);

// add UB limestone
Groups.addVariation("limestone", <UndergroundBiomes:sedimentaryStone>);

// add UB & GT black granite
<ore:stoneGraniteBlack>.remove(<chisel:granite>);
<ore:blockGraniteBlack>.remove(<chisel:granite>);
Groups.addVariation("RCAbyssalBlock", <UndergroundBiomes:igneousStone:1>);
Groups.addVariation("RCAbyssalBlock", <gregtech:gt.blockgranites:0>);

// add UB & GT red granite
recipes.remove(<chisel:granite>);
<ore:stoneGranite>.remove(<chisel:granite>);
<ore:blockGranite>.remove(<chisel:granite>);
Groups.addVariation("granite", <UndergroundBiomes:igneousStone>);
Groups.addVariation("granite", <gregtech:gt.blockgranites:8>);

