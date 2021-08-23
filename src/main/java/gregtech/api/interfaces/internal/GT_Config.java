package gregtech.api.interfaces.internal;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value = RUNTIME)
@Target(value = FIELD)
public @interface GT_Config {

    /**
     *
     * determines the config category of config property
     */

    String category() default "main";

    /**
     *
     * determines name of config value,  by default is field name
     */

    String configName() default "";

    /**
     *
     * determines config comment, by default no comment
     */

    String configComment() default "";
}
