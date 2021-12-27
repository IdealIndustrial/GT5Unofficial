package idealindustrial.impl.autogen.material;

import idealindustrial.impl.autogen.material.submaterial.BlockType;
import idealindustrial.impl.oredict.OreDict;
import idealindustrial.impl.oredict.OredictHandler;
import idealindustrial.api.reflection.events.RegisterOresEvent;
import idealindustrial.api.reflection.II_EventListener;
import idealindustrial.impl.textures.Textures;
import idealindustrial.impl.tile.covers.CoverRegistry;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static idealindustrial.impl.autogen.material.MaterialBuilder.make;
import static idealindustrial.impl.autogen.material.Prefixes.*;

@II_EventListener
public class II_Materials {

    public static final II_Material[] materialsK1 = new II_Material[1000];
    public static final List<II_Material> allMaterials = new ArrayList<>();
    //elements
    public static II_Material iron, copper, tin, lightWater, arsenic, arsenicBronze;

    //molecules
    public static II_Material cassiterite;

    static {
        Prefixes.postInit();
        init();
    }
    private static void init() {
        initElements();
        initMolecules();
        initAlloys();
    }



    private static void initElements() {
        iron = make(0, "Iron", Textures.testSet)
                .addSolid().addBlock(0, BlockType.METALLIC).setRender(new Color(100, 100, 100))
                .addFluid().setTemperature(1000).addCell().setRender(new Color(156, 2, 2))
                .addGas().setTemperature(5000).addCell().setRender(new Color(243, 95, 83))
                .addPlasma().addCell().setRender(new Color(116, 239, 116))
                .addPrefixes(ingot, dust, dustSmall, dustTiny, plate, ore, oreSmall)
                .setChemicalFormula("Fe")
                .construct();

        copper = make(1, "Copper", Textures.testSet)
                .addSolid().addBlock(1, BlockType.METALLIC).setRender(new Color(205, 116, 0))
                .addFluid().setTemperature(1000).addCell().setRender(new Color(83, 46, 2))
                .addPlasma().addCell().setRender(new Color(116, 239, 186))
                .addPrefixes(ingot, dust, dustSmall, dustTiny, plate, ore, oreSmall)
                .setChemicalFormula("Cu")
                .construct();
        tin = make(2, "Tin", Textures.testSet)
                .addSolid().addBlock(2, BlockType.METALLIC).setRender(new Color(144, 151, 151))
                .addFluid().setTemperature(1000).addCell().setRender(new Color(189, 186, 186))
                .addPlasma().addCell().setRender(new Color(2, 139, 83))
                .addPrefixes(ingot, dust, dustSmall, dustTiny, plate, ore, oreSmall)
                .setChemicalFormula("Sn")
                .construct();

        lightWater = make(3, "Water2", Textures.testSet)
                .addFluid().setTemperature(1000).addCell().setRender(new Color(27, 255, 255))
                .construct();//todo remove

        arsenic = make(4, "Arsenic", Textures.testSet)
                .addSolid().setRender(new Color(200, 200 ,200))
                .addPrefixes(dust, dustSmall, dustTiny, plate)
                .setChemicalFormula("As")
                .construct();


    }

    private static void initMolecules() {
        cassiterite = make(300, "Cassiterite", Textures.testSet)
                .addSolid().setRender(new Color(151, 151, 151))
                .setChemicalFormula("SnO2")
                .addPrefixes(dust, dustSmall, dustTiny)
                .addOres().add()
                .construct();
    }

    private static void initAlloys() {
        arsenicBronze = make(150, "Arsenic Bronze", Textures.testSet)
                .addSolid().enableBaseComponents().enableTools().setRender(new Color(252, 151, 53))
                .addFluid().addCell().setRender(new Color(255, 132, 0))
                .setChemicalFormula("Cu20As")
                .addPrefixes(nugget, nuggetBig, nuggetBigHot)
                .addTools()
                .addOres().setSmallOreDrops(r -> {
                    ArrayList<ItemStack> is = new ArrayList<>();
                    is.add(OreDict.getMainAsIS(nugget, arsenicBronze, 3 + r.nextInt(3)));
                    if (r.nextInt(5) == 0) {
                        is.add(OreDict.getMainAsIS(nuggetBig, arsenicBronze, 1));
                    }
                    return is;
                }).add()
                .construct();
    }


    public static void initMaterialLoops() {
        CoverRegistry.init();
    }

    @RegisterOresEvent
    public static void registerExpected(OredictHandler handler) {
        for (II_Material material : II_Materials.allMaterials) {
            for (Prefixes prefix : material.getExpectedPrefixes()) {
                handler.registerExpected(prefix, material);
            }
        }
    }

    static Map<String, II_Material> nameToMaterial = new HashMap<>();
    public static II_Material materialForName(String name) {
        name = name.toLowerCase().replace(" ", "");
        if (nameToMaterial.isEmpty()) {
            II_Materials.allMaterials.forEach(m -> nameToMaterial.put(m.name().toLowerCase().replace(" ", ""), m));
        }
        return nameToMaterial.get(name);
    }

    public static II_Material materialForID(int id) {
        if (id >= 0 && id < 1000) {
            return materialsK1[id];
        }
        return null;
    }

    public static II_Material[] getMaterialArray(int arrayID) {
        if (arrayID == 0) {
            return materialsK1;
        }
        return null;
    }


    public static Stream<II_Material> stream() {
        return allMaterials.stream();
    }

    public static void foreach(Consumer<II_Material> consumer) {
        allMaterials.forEach(consumer);
    }
}
