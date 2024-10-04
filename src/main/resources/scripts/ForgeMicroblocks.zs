import mods.gregtech.Lathe;

var stone = <minecraft:stone>;
var stoneRod = <ForgeMicroblock:stoneRod>;
var stoneDust = <gregtech:gt.metaitem.01:2299>;

recipes.remove(stoneRod);
Lathe.addRecipe([stoneRod, stoneDust], stone, 280, 16);

recipes.addShaped(stoneRod,
     [[<ore:craftingToolFile>],
      [null, stone]]);