import mods.gregtech.Assembler;

var EjectCover = <gendustry:EjectCover>;
var ImportCover = <gendustry:ImportCover>;
var ErrorCover = <gendustry:ErrorSensorCover>;
var EjectorUp = <IC2:upgradeModule:3>;
var PullingUp = <IC2:upgradeModule:6>;
var ScentedPaneling = <Forestry:craftingMaterial:6>;
var MaintainanceCover = <gregtech:gt.metaitem.01:32748>;


// --- Item Eject Cover
recipes.remove(EjectCover);
Assembler.addRecipe(EjectCover, EjectorUp, ScentedPaneling * 2, 16 * 20, 32);

// --- Item Import Cover
recipes.remove(ImportCover);
Assembler.addRecipe(ImportCover, PullingUp, ScentedPaneling * 2, 16 * 20, 32);

// --- Sensor Error Cover
recipes.remove(ErrorCover);
Assembler.addRecipe(ErrorCover, MaintainanceCover, ScentedPaneling * 2, <liquid:molten.redstone> * 144 * 2, 20 * 20, 32);

game.setLocalization("tile.gendustry.mutagen.producer.name", "Mutagen Producer");