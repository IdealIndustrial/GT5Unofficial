import mods.nei.NEI;
import minetweaker.item.IItemStack;

recipes.remove(<betterbuilderswands:wandIron>);
recipes.addShaped(<betterbuilderswands:wandIron>, [
	[null, <minecraft:stick>, null],
	[<ore:craftingToolFile>, <minecraft:stick>, null],
	[<ore:plateWroughtIron>, <ore:ingotWroughtIron>, <ore:craftingToolHardHammer>]]);

recipes.remove(<betterbuilderswands:wandDiamond>);
recipes.addShaped(<betterbuilderswands:wandDiamond>, [
	[null, <minecraft:stick>, null],
	[<ore:craftingToolFile>, <minecraft:stick>, null],
	[<ore:plateDiamond>, <ore:gemDiamond>, <ore:craftingToolHardHammer>]]);

recipes.remove(<betterbuilderswands:wandUnbreakable:12>);
recipes.addShaped(<betterbuilderswands:wandUnbreakable:12>, [
	[null, <minecraft:stick>, null],
	[<ore:craftingToolFile>, <minecraft:stick>, null],
	[<ore:plateNetherStar>, <minecraft:nether_star>, <ore:craftingToolHardHammer>]]);