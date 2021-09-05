package idealindustrial.util.lang;

import idealindustrial.reflection.events.II_EventListener;

@II_EventListener
public class LangTest {

    @LocalizeEvent
    public static void addNumbers() {
        for (int i = 0; i < 10; i++) {
            LangHandler.add("bin:" + i, Integer.toBinaryString(i));
        }
    }

    @LocalizeEvent
    public static String localString = " localized string";
    @LocalizeEvent
    public static String[] localStringsArr = new String[]{"12", "34", " 56"};
}
