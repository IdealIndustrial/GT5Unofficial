package gregtech.api.objects;

import net.minecraft.util.EnumChatFormatting;

public enum ColorFormat {
    BAD(EnumChatFormatting.RED),
    BETTER(EnumChatFormatting.YELLOW),
    GOOD(EnumChatFormatting.GREEN),

    NEUTRAL(EnumChatFormatting.AQUA),
    SPECIAL(EnumChatFormatting.LIGHT_PURPLE);

    private final EnumChatFormatting prefix;

    ColorFormat(EnumChatFormatting prefix) {
        this.prefix = prefix;
    }

    public String format(String input) {
        return prefix + input + EnumChatFormatting.RESET;
    }

    public static String format(int value, int minValue, int maxValue) {
        maxValue -= minValue;
        int tempValue = value - minValue;

        tempValue = (int) (((double) tempValue) / (((double) maxValue) / 10000d));
        if (tempValue < 2000)
            return BAD.format(Integer.toString(value));
        if (tempValue > 8000)
            return GOOD.format(Integer.toString(value));
        return BETTER.format(Integer.toString(value));
    }


}
