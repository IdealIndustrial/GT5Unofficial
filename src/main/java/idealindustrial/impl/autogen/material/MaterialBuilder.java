package idealindustrial.impl.autogen.material;

import idealindustrial.impl.autogen.material.submaterial.*;
import idealindustrial.impl.autogen.material.submaterial.chem.ChemicalInfo;
import idealindustrial.impl.autogen.material.submaterial.chem.ChemicalStack;
import idealindustrial.impl.autogen.material.submaterial.chem.Element;
import idealindustrial.impl.autogen.material.submaterial.chem.parser.FormulaParser;
import idealindustrial.impl.autogen.material.submaterial.render.RenderInfo;
import idealindustrial.impl.autogen.material.submaterial.render.TextureSet;
import idealindustrial.impl.autogen.recipes.RecipeAction;
import idealindustrial.impl.blocks.II_Blocks;
import idealindustrial.impl.blocks.ores.TileOres;
import idealindustrial.util.misc.MiscValues;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.*;
import java.util.function.Function;

import static idealindustrial.impl.autogen.material.Prefixes.toolHeadDrill;
import static idealindustrial.impl.autogen.material.Prefixes.toolHeadPickaxe;

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
    @SuppressWarnings("FieldCanBeLocal")
    private FuelInfo fuelInfo;
    private BlockInfo blockInfo;
    private MaterialAutogenInfo autogenInfo;
    private ChemicalInfo chemicalInfo;
    private WorldOreInfo oreInfo;

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

    public MaterialBuilder addTools() {
        return addPrefixes(toolHeadPickaxe, toolHeadDrill);
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

    @Deprecated
    public AutogenBuilder recipeAutogen() {
        return new AutogenBuilder();
    }

    public MaterialBuilder setChemicalFormula(String str) {
        chemicalInfo = new ChemicalInfo(FormulaParser.parse(str));
        return this;
    }

    public OreBuilder addOres() {
        return new OreBuilder();
    }


    public II_Material construct() {
        II_Material material = new II_Material(id, name);
        material.renderInfo = render;
        material.enabledPrefixes = prefixes;
        material.blockInfo = blockInfo;
        material.fluidInfo = fluidInfo;
        material.normalForm = normalForm;
        material.expectedPrefixes = expectedPrefixes;
        material.autogenInfo = autogenInfo == null ? new MaterialAutogenInfo() : autogenInfo;
        material.chemicalInfo = chemicalInfo == null ? new ChemicalInfo(new ChemicalStack(Element.NULL, 1)) : chemicalInfo;
        material.oreInfo = oreInfo;
        if (id >= 0 && id < 1000) {
            II_Materials.materialsK1[id] = material;
        }
        II_Materials.allMaterials.add(material);
        material.chemicalInfo.putToMap(material);

        return material;
    }


    public class SolidFormBuilder {
        public SolidFormBuilder enableBaseComponents() {
            prefixes.add(Prefixes.ingot);
            prefixes.add(Prefixes.plate);
            prefixes.add(Prefixes.dust);
            prefixes.add(Prefixes.dustSmall);
            prefixes.add(Prefixes.dustTiny);
            prefixes.add(Prefixes.nugget);
            return this;
        }

        public SolidFormBuilder enableTools() {
            Arrays.stream(Prefixes.values()).filter(p -> p.name().toLowerCase().startsWith("toolhead")).forEach(prefixes::add);
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

    @Deprecated
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
            return MaterialBuilder.this;
        }

    }

    public class OreBuilder {
        private ArrayList<ItemStack> makeList(Prefixes prefix) {
            ArrayList<ItemStack> list = new ArrayList<>();
            list.add(new ItemStack(II_Blocks.INSTANCE.blockOres, 1, TileOres.getMeta(id, prefix)));
            return list;
        }

        Function<Random, ArrayList<ItemStack>> drops = random -> makeList(Prefixes.ore),
                dropsSmall = random -> makeList(Prefixes.oreSmall);

        public OreBuilder setSmallOreDrops(Function<Random, ArrayList<ItemStack>> function) {
            dropsSmall = function;
            return this;
        }

        public OreBuilder setOreDrops(Function<Random, ArrayList<ItemStack>> function) {
            drops = function;
            return this;
        }



        public MaterialBuilder add() {
            prefixes.add(Prefixes.oreSmall);
            prefixes.add(Prefixes.ore);
            oreInfo = new WorldOreInfo(drops, dropsSmall);
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
