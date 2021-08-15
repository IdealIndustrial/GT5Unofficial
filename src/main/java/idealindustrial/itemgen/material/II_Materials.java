package idealindustrial.itemgen.material;

import gregtech.api.enums.TextureSet;
import idealindustrial.itemgen.material.submaterial.BlockType;
import idealindustrial.tile.covers.II_CoverRegistry;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static idealindustrial.itemgen.material.II_MaterialBuilder.make;
import static idealindustrial.itemgen.material.Prefixes.*;

public class II_Materials {

    public static final II_Material[] materialsK1 = new II_Material[1000];
    public static final List<II_Material> allMaterials = new ArrayList<>();
    public static II_Material iron;

    static {
        Prefixes.postInit();
        init();
    }
    private static void init() {
        iron = make(0, "Iron")
                .addSolid().addBlock(0, BlockType.METALLIC).setRender(new Color(100, 100, 100), TextureSet.SET_METALLIC)
                .addFluid().setTemperature(1000).addCell().setRender(new Color(156, 2, 2))
                .addGas().setTemperature(5000).addCell().setRender(new Color(243, 95, 83))
                .addPlasma().addCell().setRender(new Color(116, 239, 116))
                .addPrefixes(dust, dustSmall, dustTiny, plate)
                .addExpectedPrefixes(ingot)
                .construct();
    }


    public static void initMaterialLoops() {
        II_CoverRegistry.init();
    }


}
