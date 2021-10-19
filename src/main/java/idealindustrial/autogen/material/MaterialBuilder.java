package idealindustrial.autogen.material;

import idealindustrial.autogen.fluids.II_Fluids;
import idealindustrial.autogen.material.submaterial.*;
import idealindustrial.autogen.material.submaterial.render.RenderInfo;
import idealindustrial.autogen.material.submaterial.render.SolidRenderInfo;
import idealindustrial.autogen.material.submaterial.render.TextureSet;
import idealindustrial.autogen.recipes.RecipeAction;
import idealindustrial.util.misc.MiscValues;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Andrey Solodovnikov (github.com/AndreySolodovnikov)
 */
public class MaterialBuilder {
    private final int id;
    private final String name;
    private MatterState normalForm = MatterState.Solid; // helps to determine default temperatures
    private RenderInfo render = null;//todo deafult
    private final FluidInfo fluidInfo = new FluidInfo();
    private final Set<Prefixes> prefixes = new HashSet<>();
    private final Set<Prefixes> expectedPrefixes = new HashSet<>();
    private FuelInfo fuelInfo;
    private BlockInfo blockInfo;
    private MaterialAutogenInfo autogenInfo;

    public static MaterialBuilder make(int id, String name, TextureSet textureSet) {
        return new MaterialBuilder(id, name, textureSet);
    }

    public MaterialBuilder(int id, String name, TextureSet textureSet) {
        this.id = id;
        this.name = name;
        this.render = new RenderInfo(textureSet);
    }


    public MaterialBuilder addPrefix(Prefixes... prefix) {
        prefixes.addAll(Arrays.asList(prefix));
        return this;
    }

    public MaterialBuilder addPrefixes(Prefixes... prefixes) {
        this.prefixes.addAll(Arrays.asList(prefixes));
        return this;
    }

    public MaterialBuilder addExpectedPrefixes(Prefixes... prefixes) {
        this.expectedPrefixes.addAll(Arrays.asList(prefixes));
        return this;
    }

    public MaterialBuilder setNormalState(MatterState state) {
        normalForm = state;
        return this;
    }

    public SolidFormBuilder addSolid() {
        return new SolidFormBuilder();
    }

    public LiquidFormBuilder addFluid() {
        return new LiquidFormBuilder(MatterState.Liquid);
    }

    public LiquidFormBuilder addGas() {
        return new LiquidFormBuilder(MatterState.Gas);
    }

    public LiquidFormBuilder addPlasma() {
        return new LiquidFormBuilder(MatterState.Plasma).setTemperature(10_000);
    }

    public AutogenBuilder recipeAutogen() {
        return new AutogenBuilder();
    }


    public II_Material construct() {
        II_Material material = new II_Material(id, name);
        material.renderInfo = render;
        material.enabledPrefixes = prefixes;
        material.blockInfo = blockInfo;
        material.fluidInfo = fluidInfo;
        material.normalForm = normalForm;
        material.expectedPrefixes = expectedPrefixes;
        material.autogenInfo = autogenInfo == null ? new MaterialAutogenInfo(new HashSet<>()) : autogenInfo;
        
        if (id >= 0 && id < 1000) {
            II_Materials.materialsK1[id] = material;
        }
        II_Materials.allMaterials.add(material);
        return material;
    }



    public class SolidFormBuilder {
        public SolidFormBuilder enableBaseComponents() {
            prefixes.add(Prefixes.ingot);
            return this;
        }

        public SolidFormBuilder addBlock(int id, BlockType type) {
            blockInfo = new BlockInfo(id, type);
            return this;
        }

        public MaterialBuilder setRender(Color color) {
            render.setColor(MatterState.Solid, color);
            return MaterialBuilder.this;
        }

    }

    public class LiquidFormBuilder {
        private final MatterState form;
        private int temperature;

        public LiquidFormBuilder(MatterState form) {
            this.form = form;
//            texture = II_Fluids.defaultTexture; //todo: set better default texture
            temperature = 0; // todo: set default temperature
        }

        public LiquidFormBuilder addCell() {
            assert id >= 0 : " Id must not be negative for cells";
            addPrefix(MiscValues.cellToStateMap.inverse().get(form));
            return this;
        }

        public LiquidFormBuilder setTemperature(int temperature) {
            this.temperature = temperature;
            return this;
        }

        public MaterialBuilder setRender(Color color) {
            render.setColor(form, color);
            fluidInfo.set(form, new FluidDef(form, temperature));
            return MaterialBuilder.this;
        }
    }

    public class AutogenBuilder {
        Set<RecipeAction> actions = new HashSet<>();

        public AutogenBuilder addActions(RecipeAction... actions) {
            this.actions.addAll(Arrays.asList(actions));
            return this;
        }

        public AutogenBuilder addMetallicActions() {
            return addActions(RecipeAction.plateBending);
        }

        public MaterialBuilder add() {
            autogenInfo = new MaterialAutogenInfo(actions);
            return MaterialBuilder.this;
        }

    }


    //region FUEL

    public FuelBuilder addFuel(FuelType ofType) {
        return new FuelBuilder(ofType);
    }

    public class FuelBuilder {
        long fuelValue;
        FuelType type;

        public FuelBuilder(FuelType type) {
            this.type = type;
        }

        public FuelBuilder setEnergyValue(long eu) {
            fuelValue = eu;
            return this;
        }

        public MaterialBuilder add() {
            fuelInfo = new FuelInfo(type, fuelValue);
            return MaterialBuilder.this;
        }
    }

    //endregion
}
