package idealindustrial.util.lang;

import cpw.mods.fml.common.registry.LanguageRegistry;
import idealindustrial.reflection.events.EventManager;
import net.minecraft.util.StatCollector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;

public class II_Lang {
    static HashMap<String, String> en_lang = new HashMap<>();

    public static void pushLocalToMinecraft() {
        LanguageRegistry.instance().injectLanguage("en_US", en_lang);
    }

    /**
     * adds Eng localization for
     * key
     * English local
     */
    public static void add(String key, String local) {
        en_lang.put(key, local);
    }

    public static void dumpAll() {
        for (Method method : EventManager.INSTANCE.getHandlers(LocalizeEvent.class)) {
            try {
                method.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("failed to call localize event", e);
            }
        }

        for (Field field : EventManager.INSTANCE.getFields(LocalizeEvent.class)) {
            field.setAccessible(true);
            try {
                Object object = field.get(null);
                String key = key(field);
                if (object instanceof String) {
                    add(key, (String) object);
                }
                else if (object instanceof String[]) {
                    String[] ar = (String[]) object;
                    int length = length(field);
                    for (int i = 0; i < length; i++) {
                        add(key + i, ar[i]);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("failed to get localize event field", e);
            }
        }
        int a = 0;
    }

    public static void postResourcesReload() {
        for (Field field : EventManager.INSTANCE.getFields(LocalizeEvent.class)) {
            field.setAccessible(true);
            try {
                Type type = field.getType();
                String key = key(field);
                if (type == String.class) {
                    field.set(null, StatCollector.translateToLocal(key));
                }
                else if (type ==  String[].class) {
                    String[] ar = (String[]) field.get(null);
                    int length = length(field);
                    for (int i = 0; i < length; i++) {
                        ar[i] = StatCollector.translateToLocal(key + i);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("failed to get localize event field", e);
            }
        }
    }

    private static String key(Field field) {
        LocalizeEvent event = field.getAnnotation(LocalizeEvent.class);
        String key = event.key();
        if (key.equals("")) {
            key = field.getDeclaringClass().getSimpleName().concat(field.getName()).toLowerCase();
        }
        return key;
    }

    private static int length(Field field) throws IllegalAccessException {
        LocalizeEvent event = field.getAnnotation(LocalizeEvent.class);
        int length = event.length();
        if (length < 0) {
            length = ((String[]) field.get(null)).length;
        }
        return length;
    }

    static {
        Class<?> c = LangTest.class;
        //load here all classes that contain locale and may not be cl-inited before game post load start
    }
}
