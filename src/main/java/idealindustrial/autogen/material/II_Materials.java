package idealindustrial.autogen.material;

import gregtech.api.enums.TextureSet;
import idealindustrial.autogen.material.submaterial.BlockType;
import idealindustrial.autogen.oredict.OredictHandler;
import idealindustrial.autogen.oredict.RegisterOresEvent;
import idealindustrial.reflection.events.II_EventListener;
import idealindustrial.tile.covers.CoverRegistry;

import java.awt.*;
import java.util.*;
import java.util.List;

import static idealindustrial.autogen.material.MaterialBuilder.make;
import static idealindustrial.autogen.material.Prefixes.*;

@II_EventListener
public class II_Materials {

    public static final II_Material[] materialsK1 = new II_Material[1000];
    public static final List<II_Material> allMaterials = new ArrayList<>();
    //elements
    public static II_Material iron, copper, tin;

    static {
        Prefixes.postInit();
        init();
    }
    private static void init() {
        initElements();
    }

    private static void initElements() {
        iron = make(0, "Iron")
                .addSolid().addBlock(0, BlockType.METALLIC).setRender(new Color(100, 100, 100), TextureSet.SET_METALLIC)
                .addFluid().setTemperature(1000).addCell().setRender(new Color(156, 2, 2))
                .addGas().setTemperature(5000).addCell().setRender(new Color(243, 95, 83))
                .addPlasma().addCell().setRender(new Color(116, 239, 116))
                .addPrefixes(dust, dustSmall, dustTiny, plate)
                .addExpectedPrefixes(ingot)
                .recipeAutogen().addMetallicActions().add()
                .construct();

        copper = make(1, "Copper")
                .addSolid().addBlock(1, BlockType.METALLIC).setRender(new Color(205, 116, 0), TextureSet.SET_METALLIC)
                .addFluid().setTemperature(1000).addCell().setRender(new Color(83, 46, 2))
                .addPlasma().addCell().setRender(new Color(116, 239, 186))
                .addPrefixes(dust, dustSmall, dustTiny, plate)
                .recipeAutogen().addMetallicActions().add()
                .construct();
        tin = make(2, "Tin")
                .addSolid().addBlock(2, BlockType.METALLIC).setRender(new Color(144, 151, 151), TextureSet.SET_METALLIC)
                .addFluid().setTemperature(1000).addCell().setRender(new Color(189, 186, 186))
                .addPlasma().addCell().setRender(new Color(2, 139, 83))
                .addPrefixes(dust, dustSmall, dustTiny, plate)
                .recipeAutogen().addMetallicActions().add()
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
        if (nameToMaterial.isEmpty()) {
            II_Materials.allMaterials.forEach(m -> nameToMaterial.put(m.name().toLowerCase(), m));
        }
        return nameToMaterial.get(name);
    }


}
