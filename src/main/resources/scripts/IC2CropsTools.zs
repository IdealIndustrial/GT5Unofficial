import mods.nei.NEI;
import minetweaker.item.IItemStack;

var Spade = <ic2cropstools:itemSpade>;
var Lens = <ic2cropstools:itemLens>;
var LongStick = <gregtech:gt.metaitem.02:22809>;

recipes.remove(Spade);

recipes.remove(Lens);
recipes.addShaped(Lens,
	[[<minecraft:glass_pane>, <ore:craftingToolFile>, null],
    	[null, <minecraft:string>, null],
	[null, null, LongStick]]);