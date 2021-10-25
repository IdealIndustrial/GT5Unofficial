package idealindustrial.autogen.material.submaterial.render;

import idealindustrial.autogen.material.Prefixes;
import idealindustrial.autogen.material.submaterial.MatterState;
import idealindustrial.textures.IconContainer;
import idealindustrial.textures.TextureManager;
import net.minecraft.util.IIcon;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static idealindustrial.util.misc.II_Paths.TEXTURE_SET;

public class TextureSet {
    private static final Set<String> blockIcons = Stream.of(Prefixes.block, Prefixes.cable01).map(Enum::toString).collect(Collectors.toSet());
    private static final Set<String> itemIcons = Stream.of(Prefixes.ingot, Prefixes.plate, Prefixes.dust, Prefixes.dustSmall, Prefixes.dustTiny)
            .map(Enum::toString).collect(Collectors.toSet());
    private static final Set<String> itemWithOverlays = Stream.of(Prefixes.cell, Prefixes.gasCell, Prefixes.plasmaCell, Prefixes.toolHeadDrill)
            .map(Enum::toString).collect(Collectors.toSet());

    static {
        blockIcons.addAll(Arrays.asList("liquid", "gas", "plasma", "ore", "oreSmall"));
    }


    public IconContainer ingot, plate, dust, dustTiny, dustSmall, liquid, gas, plasma, block, cable01, ore, oreSmall, cell, gasCell, plasmaCell, toolHeadDrill;

    private final IconContainer[] icons = new IconContainer[Prefixes.values().length];
    private String name;

    public TextureSet(String name) {
      this.name = name;
        try {
            init();
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    private void init() throws IllegalAccessException {
        for (Field field : getClass().getDeclaredFields()) {
            if (!field.getAnnotatedType().getType().equals(IconContainer.class)) {
                continue;
            }
            if (blockIcons.contains(field.getName())) {
                field.set(this, TextureManager.INSTANCE.blockTexture(TEXTURE_SET + name + "/" + field.getName()));
            }
            if (itemIcons.contains(field.getName())) {
                field.set(this, TextureManager.INSTANCE.itemTexture(TEXTURE_SET + name + "/" + field.getName()));
            }
            if (itemWithOverlays.contains(field.getName())) {
                field.set(this, TextureManager.INSTANCE.itemTextureWithOverlay(TEXTURE_SET + name + "/" + field.getName()));
            }
            try {
                Prefixes p = Prefixes.valueOf(field.getName());
                icons[p.ordinal()] = (IconContainer) field.get(this);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public IconContainer forPrefix(Prefixes prefix) {
        return icons[prefix.ordinal()];
    }

    public IconContainer getFluidTexture(MatterState state) {
        switch (state) {
            case Liquid:
                return liquid;
            case Gas:
                return gas;
            case Plasma:
               return plasma;
            default:
                return ingot;
        }
    }
}
