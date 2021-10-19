package idealindustrial.hooks;

import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import ic2.api.recipe.IRecipeInput;
import ic2.core.AdvRecipe;
import ic2.core.item.ItemIC2FluidContainer;
import ic2.core.util.StackUtil;
import idealindustrial.II_Core;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static ic2.core.AdvRecipe.displayError;

public class II_GameTitlePatch {

    @Hook
    public static void createDisplay(ForgeHooksClient mc) {
        if (true) {
            return;
        }
        Display.setTitle(new String(new byte[]{-48, -104, -48, -104, -48, -95}, StandardCharsets.UTF_8)); // just "ИИС" encoded in utf-8, Im to lazy to fix gradle build encodings
        ResourceLocation icon = new ResourceLocation("gregtech", "textures/title.png");

        try {
            InputStream inputstream = II_Core.class.getResourceAsStream("/assets/" + icon.getResourceDomain() + "/" + icon.getResourcePath());
            Display.setIcon(new ByteBuffer[]{call(inputstream)});
        } catch (IOException ignore) {

        }
    }

    private static ByteBuffer call(InputStream is) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(is);
        int[] rgb = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * rgb.length);
        for (int k : rgb) {
            bytebuffer.putInt(k << 8 | k >> 24 & 255);
        }
        bytebuffer.flip();
        return bytebuffer;
    }


    @Hook(returnCondition = ReturnCondition.ALWAYS)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void addInformation(ItemIC2FluidContainer item, ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        FluidStack fs = item.getFluid(itemStack);
        if (fs != null && fs.getFluid() != null) {
            info.add("< " + fs.getFluid().getLocalizedName(fs) + ", " + fs.amount + " mB >");
            if (b) {
                info.add("< " + fs.getFluid().getUnlocalizedName() + " >");
            }
        } else {
            info.add(StatCollector.translateToLocal("ic2.item.FluidContainer.Empty"));
        }

    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<String> getHudInfo(ItemIC2FluidContainer item, ItemStack itemStack) {
        List<String> info = new LinkedList();
        FluidStack fs = item.getFluid(itemStack);
        if (fs != null && fs.getFluid() != null) {
            info.add("< " + fs.getFluid().getLocalizedName(fs) + ", " + fs.amount + " mB >");
        } else {
            info.add(StatCollector.translateToLocal("ic2.item.FluidContainer.Empty"));
        }

        return info;
    }


    //remove assert
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static List<ItemStack> expand(AdvRecipe recipe, Object o) {
        List<ItemStack> ret = new ArrayList();
        if (o instanceof IRecipeInput) {
            ret.addAll(((IRecipeInput) o).getInputs());
        } else if (o instanceof String) {
            String s = (String) o;
            if (s.startsWith("liquid$")) {
                String name = s.substring(7);
                FluidContainerRegistry.FluidContainerData[] arr$ = FluidContainerRegistry.getRegisteredFluidContainerData();
                int len$ = arr$.length;

                for (FluidContainerRegistry.FluidContainerData data : arr$) {
                    String fluidName = FluidRegistry.getFluidName(data.fluid);
                    if (StackUtil.check(data.filledContainer) && name.equals(fluidName)) {
                        ret.add(data.filledContainer);
                    }
                }
            } else {
                Iterator i$ = OreDictionary.getOres((String) o).iterator();

                while (i$.hasNext()) {
                    ItemStack stack = (ItemStack) i$.next();
                    if (StackUtil.check(stack)) {
                        ret.add(stack);
                    }
                }
            }
        } else if (o instanceof ItemStack) {
            if (StackUtil.check((ItemStack) o)) {
                ret.add((ItemStack) o);
            }
        } else if (o.getClass().isArray()) {
            assert Array.getLength(o) != 0;

            for (int i = 0; i < Array.getLength(o); ++i) {
                ret.addAll(AdvRecipe.expand(Array.get(o, i)));
            }
        } else {
            if (!(o instanceof Iterable)) {
                displayError("unknown type", "Input: " + o + "\nType: " + o.getClass().getName(), (ItemStack) null, false);
                return null;
            }

            Iterator i$ = ((Iterable) o).iterator();

            while (i$.hasNext()) {
                Object o2 = i$.next();
                ret.addAll(AdvRecipe.expand(o2));
            }
        }

        return ret;
    }
}
