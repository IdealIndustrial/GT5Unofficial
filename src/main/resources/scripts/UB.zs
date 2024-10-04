import mods.gregtech.CuttingSaw;
import minetweaker.item.IItemStack;
import mods.gregtech.Pulverizer;
import mods.gregtech.Centrifuge;

var meta = [0, 1, 2, 3, 4, 5, 6, 7] as int[];
var slabs = [
    <UndergroundBiomes:igneousStoneBrickHalfSlab>,
    <UndergroundBiomes:metamorphicStoneBrickHalfSlab>,
    <UndergroundBiomes:igneousStoneHalfSlab>,
    <UndergroundBiomes:metamorphicStoneHalfSlab>,
    <UndergroundBiomes:igneousCobblestoneHalfSlab>,
    <UndergroundBiomes:metamorphicCobblestoneHalfSlab>,
    <UndergroundBiomes:sedimentaryStoneHalfSlab>
] as IItemStack[];
var blocks = [
    <UndergroundBiomes:igneousStoneBrick>,
    <UndergroundBiomes:metamorphicStoneBrick>,
    <UndergroundBiomes:igneousStone>,
    <UndergroundBiomes:metamorphicStone>,
    <UndergroundBiomes:igneousCobblestone>,
    <UndergroundBiomes:metamorphicCobblestone>,
    <UndergroundBiomes:sedimentaryStone>
] as IItemStack[];

for i in meta {
    for j, _ in blocks {
        var slab = slabs[j].definition.makeStack(i);
        var block = blocks[j].definition.makeStack(i);
	recipes.remove(slab);
        CuttingSaw.addRecipe([slab * 2], block, null, 25, 8);
	recipes.addShaped(slab * 2, [[<ore:craftingToolSaw>, block]]);
    }
}


/********* adding pulverizing for sedimentary stone from Underground Biomes, 02.08.2018 *********/
/* 
changes of color in MaterialProperties.cfg
for UB-compatible

greywacke {
	S:"MatRGBA_255,255,255,0,"=120,124,119,0,

dolomite {
	S:"MatRGBA_225,205,205,0,"=130,129,104,0,

siltstone {
	S:"MatRGBA_255,255,255,0,"=189,145,101,0,
*/

var CaCO3 = <gregtech:gt.metaitem.01:3823>;

// Limestone is a sedimentary rock, composed mainly of skeletal fragments of marine organisms such as coral, forams and molluscs. Its major materials are the minerals calcite and aragonite, which are different crystal forms of calcium carbonate (CaCO3). Crystals of calcite, quartz, dolomite or barite may line small cavities in the rock.
Pulverizer.addRecipe([CaCO3, <gregtech:gt.metaitem.01:523> /*tiny quartzite*/], <UndergroundBiomes:sedimentaryStone:0> /*UB Limestone*/, [10000, 1000], 400, 2);

// Chalk is a soft, white, porous, sedimentary carbonate rock, a form of limestone composed of the mineral calcite. The chemical composition of chalk is calcium carbonate, with minor amounts of silt and clay.
// Silt is granular material of a size between sand and clay, SiO4 - KAlSi3O8 – NaAlSi3O8 – CaAl2Si2O8
// Silt -> SiO2 or clay or potassium feldspar
Pulverizer.addRecipe([CaCO3, <gregtech:gt.metaitem.01:876> /*tiny siltstone*/], <UndergroundBiomes:sedimentaryStone:1> /*UB Chalk*/, [10000, 1000], 400, 2);
Centrifuge.addRecipe([<gregtech:gt.metaitem.01:2837> /*SiO2*/, <gregtech:gt.metaitem.01:2805> /*clay*/, <gregtech:gt.metaitem.01:2847> /*feldspar*/], null, <gregtech:gt.metaitem.01:2876> /*Siltstone dust*/, null, null, [4000, 3000, 3000], 320, 8);

// Shale is a fine-grained, clastic sedimentary rock composed of mud that is a mix of flakes of clay minerals and tiny fragments (silt-sized particles) of other minerals, especially quartz and calcite. Some black shales contain abundant heavy metals such as molybdenum, uranium, **vanadium**, and zinc.
Pulverizer.addRecipe([CaCO3, <gregtech:gt.metaitem.01:923> /*tiny vanadium magnetite*/], <UndergroundBiomes:sedimentaryStone:2> /*UB Shale*/, [10000, 1000], 400, 2);

// Siltstone is a sedimentary rock which has a grain size in the silt range, finer than sandstone and coarser than claystones.
Pulverizer.addRecipe([<gregtech:gt.metaitem.01:2876> /*Siltstone dust*/, <gregtech:gt.metaitem.01:805> /*tiny clay*/], <UndergroundBiomes:sedimentaryStone:3> /*UB Siltstone*/, [10000, 1000], 400, 2);

// Dolomite is an anhydrous carbonate mineral composed of calcium magnesium carbonate, ideally CaMg(CO3)2. Solid solution exists between dolomite, the iron-dominant ankerite and the manganese-dominant kutnohorite.[9] Small amounts of iron in the structure give the crystals a yellow to brown tint. Manganese substitutes in the structure also up to about three percent MnO. A high manganese content gives the crystals a rosy pink color. Lead, zinc, and cobalt also substitute in the structure for magnesium.
Pulverizer.addRecipe([<gregtech:gt.metaitem.01:2914> /*dust dolomite*/, <gregtech:gt.metaitem.01:943> /*tiny MnO2*/], <UndergroundBiomes:sedimentaryStone:5> /*UB Dolomite*/, [10000, 1000], 400, 2);

// Greywacke or graywacke (German grauwacke, signifying a grey, earthy rock) is a variety of sandstone generally characterized by its hardness, dark color, and poorly sorted angular grains of quartz, feldspar, and small rock fragments or lithic fragments set in a compact, clay-fine matrix.
// Greywacke can contain a very great variety of minerals, the principal ones being quartz, orthoclase and plagioclase feldspars, calcite, iron oxides and graphitic, carbonaceous matters, together with (in the coarser kinds) fragments of such rocks as felsite, chert, slate, gneiss, various schists, and quartzite. Among other minerals found in them are biotite, chlorite, tourmaline, epidote, apatite, garnet, hornblende, augite, sphene and pyrites. The cementing material may be siliceous or argillaceous and is sometimes calcareous
Pulverizer.addRecipe([<gregtech:gt.metaitem.01:2868> /*graywacke dust*/, <gregtech:gt.metaitem.01:516> /*tiny quartz*/], <UndergroundBiomes:sedimentaryStone:6> /*UB Greywacke*/, [10000, 1000], 400, 2);
Centrifuge.addRecipe([<gregtech:gt.metaitem.01:2837> * 4 /*impure SiO2*/, <gregtech:gt.metaitem.01:2516> /*quartz*/, <gregtech:gt.metaitem.01:2523> /*quartzite*/, <gregtech:gt.metaitem.01:2527>/*red garnet*/, <gregtech:gt.metaitem.01:2528>/*yellow garnet*/], null, <gregtech:gt.metaitem.01:2868> * 8 /*Greywacke dust*/, null, null, [10000, 10000, 10000, 10000, 10000], 400, 8);

// Chert is a hard, fine-grained sedimentary rock composed of crystals of quartz (silica) that are very small (microcrystalline or cryptocrystalline).[1] Quartz (silica) is the mineral form of silicon dioxide (SiO2).
Pulverizer.addRecipe([<gregtech:gt.metaitem.01:2837> /*impure SiO2*/, <gregtech:gt.metaitem.01:816> /*dark ashes*/], <UndergroundBiomes:sedimentaryStone:7> /*UB Chert*/, [5000, 1000], 400, 2);

// add UGB Ores to oredict
val oreRedstone = <ore:oreRedstone>;
oreRedstone.add(<UndergroundBiomes:igneous_oreRedstone:*>);
oreRedstone.add(<UndergroundBiomes:sedimentary_oreRedstone:*>);
oreRedstone.add(<UndergroundBiomes:metamorphic_oreRedstone:*>);
val oreCoal = <ore:oreCoal>;
oreCoal.add(<UndergroundBiomes:igneous_oreCoal:*>);
oreCoal.add(<UndergroundBiomes:sedimentary_oreCoal:*>);
oreCoal.add(<UndergroundBiomes:metamorphic_oreCoal:*>);
val oreDiamond = <ore:oreDiamond>;
oreDiamond.add(<UndergroundBiomes:igneous_oreDiamond:*>);
oreDiamond.add(<UndergroundBiomes:sedimentary_oreDiamond:*>);
oreDiamond.add(<UndergroundBiomes:metamorphic_oreDiamond:*>);
val oreLapis = <ore:oreLapis>;
oreLapis.add(<UndergroundBiomes:igneous_oreLapis:*>);
oreLapis.add(<UndergroundBiomes:sedimentary_oreLapis:*>);
oreLapis.add(<UndergroundBiomes:metamorphic_oreLapis:*>);
val oreEmerald = <ore:oreEmerald>;
oreEmerald.add(<UndergroundBiomes:igneous_oreEmerald:*>);
oreEmerald.add(<UndergroundBiomes:sedimentary_oreEmerald:*>);
oreEmerald.add(<UndergroundBiomes:metamorphic_oreEmerald:*>);
val oreGold = <ore:oreGold>;
oreGold.add(<UndergroundBiomes:igneous_oreGold:*>);
oreGold.add(<UndergroundBiomes:sedimentary_oreGold:*>);
oreGold.add(<UndergroundBiomes:metamorphic_oreGold:*>);
val oreIron = <ore:oreIron>;
oreIron.add(<UndergroundBiomes:igneous_oreIron:*>);
oreIron.add(<UndergroundBiomes:sedimentary_oreIron:*>);
oreIron.add(<UndergroundBiomes:metamorphic_oreIron:*>);
