package idealindustrial.api.reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value = RUNTIME)
@Target(value = FIELD)
public @interface Config {

    /**
     *
     * determines the config category of config property
     * use *class* to define a class name here
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
