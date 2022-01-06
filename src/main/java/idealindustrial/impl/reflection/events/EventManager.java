package idealindustrial.impl.reflection.events;

import idealindustrial.api.reflection.II_EventListener;
import net.minecraft.launchwrapper.Launch;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;

public class EventManager {

    public static final EventManager INSTANCE = new EventManager();
    boolean cached = false;
    List<Class<?>> eventHandlers = null;
    Map<Class<? extends Annotation>, List<Method>> event2methodMap = new HashMap<>();
    Map<Class<? extends Annotation>, List<Field>> event2fieldMap = new HashMap<>();

    public List<Method> getHandlers(Class<? extends Annotation> forEvent) {
        return event2methodMap.computeIfAbsent(forEvent, a -> load(a, Class::getMethods));
    }

    public List<Field> getFields(Class<? extends Annotation> forEvent) {
        return event2fieldMap.computeIfAbsent(forEvent, a -> load(a, Class::getFields));
    }

    private <T extends AnnotatedElement & Member> List<T> load(Class<? extends Annotation> event, Function<Class<?>, T[]> get) {
        List<T> list = new ArrayList<>();
        for (Class<?> listener : getAllHandlers()) {
            for (T member : get.apply(listener) ) {
                if (member.isAnnotationPresent(event)) {
                    if (!Modifier.isStatic(member.getModifiers())) {
                        throw new IllegalStateException("all methods annotated with " + event.getSimpleName() + " must be static");
                    }
                    list.add(member);
                }
            }
        }
        return list;
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    public List<Class<?>> getAllHandlers() {
        if (!cached) {

            Class classLoaderClass = Launch.classLoader.getClass();
            Map<String, Class<?>> cachedClasses;
            try {
                Field field = classLoaderClass.getDeclaredField("cachedClasses");
                field.setAccessible(true);
                cachedClasses = (Map<String, Class<?>>) field.get(Launch.classLoader);
            } catch (NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
                throw new IllegalStateException("cannot load event listener", exception);
            }
            List<Class<?>> list = new ArrayList<>();
            for(Class<?> cl : cachedClasses.values()) {
                if (!cl.isAnnotationPresent(II_EventListener.class)){
                    continue;
                }
                list.add(cl);
            }
            eventHandlers = list;
            cached = true;
        }
        return eventHandlers;
    }

    public void callAll(Class<? extends Annotation> forEvent, Object... args) {
        for (Method method : getHandlers(forEvent)) {
            try {
                method.setAccessible(true);
                int params = method.getParameterCount();
                Object[] theArgs = new Object[params];
                for (int i = 0; i < theArgs.length; i++) {
                    theArgs[i] = i >= args.length ? null : args[i];
                }
                method.invoke(null, theArgs);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new IllegalStateException("error processing event " + forEvent.getSimpleName(), e);
            }
        }
    }


    public void reloadListeners() {
        eventHandlers = null;
        event2methodMap.clear();
        cached = false;
    }
}
