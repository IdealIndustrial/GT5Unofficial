# Aliases
var chunkLoader = <ChickenChunks:chickenChunkLoader:0>;
var plateObsidian = <ore:plateObsidian>;
var plateGold = <ore:plateGold>;
var IndustrialDiamond = <IC2:itemPartIndustrialDiamond>;
var EnderPearl = <minecraft:ender_pearl>;

recipes.remove(chunkLoader);
recipes.addShaped(chunkLoader, [[null, EnderPearl, null],
				[plateGold, plateGold, plateGold],
				[plateObsidian, IndustrialDiamond, plateObsidian]]);



