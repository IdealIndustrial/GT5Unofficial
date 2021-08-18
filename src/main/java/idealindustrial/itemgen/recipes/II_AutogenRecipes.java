package idealindustrial.itemgen.recipes;

import idealindustrial.itemgen.material.II_Material;
import idealindustrial.itemgen.material.II_Materials;
import javafx.scene.paint.Material;

import java.util.ArrayList;
import java.util.List;

public class II_AutogenRecipes {

    public II_BenderAutogenRecipes benderAutogenRecipes = new II_BenderAutogenRecipes();

    public List<II_AutogenRecipeAdder> adders = new ArrayList<>();

    public II_AutogenRecipes() {
        adders.add(benderAutogenRecipes);
    }

    public void init() {
        for (II_Material material : II_Materials.allMaterials) {
            for (II_AutogenRecipeAdder adder : adders) {
                adder.addRecipes(material);
            }
        }
    }
}
