package idealindustrial.autogen.material.submaterial.render;

import idealindustrial.autogen.material.Prefixes;
import idealindustrial.autogen.material.submaterial.MatterState;
import idealindustrial.reflection.ReflectionHelper;
import idealindustrial.textures.IconContainer;
import idealindustrial.textures.TextureManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static idealindustrial.util.misc.II_Paths.TEXTURE_SET;

public class TextureSet {
    private static final Set<String> blockIcons = Stream.of(Prefixes.block, Prefixes.cable01).map(Enum::toString).collect(Collectors.toSet());
    private static final Set<String> itemIcons = Stream.of(Prefixes.ingot, Prefixes.plate, Prefixes.dust, Prefixes.dustSmall, Prefixes.dustTiny, Prefixes.nugget, Prefixes.nuggetBig)
            .map(Enum::toString).collect(Collectors.toSet());
    private static final Set<String> itemWithOverlays = Stream.of(Prefixes.cell, Prefixes.gasCell, Prefixes.plasmaCell, Prefixes.toolHeadDrill)
            .map(Enum::toString).collect(Collectors.toSet());

    static {
        blockIcons.addAll(Arrays.asList("liquid", "gas", "plasma", "ore", "oreSmall"));
    }


    public IconContainer ingot, plate, dust, dustTiny, dustSmall, liquid, gas, plasma, block, cable01, ore, oreSmall, cell, gasCell, plasmaCell, toolHeadDrill;

    private final IconContainer[] icons = new IconContainer[Prefixes.values().length];
    private final String name;

    public TextureSet(String name) {
        this.name = name;
        try {
            init();
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    private void init() throws IllegalAccessException {
        Set<String> textureNames = new HashSet<>();
        Stream.of(blockIcons, itemIcons, itemWithOverlays).forEach(textureNames::addAll);
        for (String texture : textureNames) {

            IconContainer container = null;
            if (blockIcons.contains(texture)) {
                container = TextureManager.INSTANCE.blockTexture(TEXTURE_SET + name + "/" + texture);
            }
            else if (itemIcons.contains(texture)) {
                container = TextureManager.INSTANCE.itemTexture(TEXTURE_SET + name + "/" + texture);
            }
            else if (itemWithOverlays.contains(texture)) {
                container = TextureManager.INSTANCE.itemTextureWithOverlay(TEXTURE_SET + name + "/" + texture);
            }
            if (container == null) {
                continue;
            }
            Field field = ReflectionHelper.getField(getClass(), texture, IconContainer.class);
            if (field != null) {
                field.set(this, container);
            }
            try {
                Prefixes p = Prefixes.valueOf(texture);
                icons[p.ordinal()] = container;
            } catch (Exception e) {
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
