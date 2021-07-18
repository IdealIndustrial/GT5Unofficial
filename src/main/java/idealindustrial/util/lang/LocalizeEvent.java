package idealindustrial.util.lang;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * event that is fired when game postload starts ( before recipe loading)
 * used to make all localization valid
 *
 * also its fired when game reloads resources ( texturepack or lang change, et.c.)
 *
 * if Method is annotated, then it's just called and game expects that
 * all local <String,String> pairs will be loaded to II_Lang
 * it's used for pattern localization for eg. metaGeneratedItem names
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface LocalizeEvent {

    /**
     * ### All parameters are used for Field annotating, only String and String[] can be annotated btw ###
     *
     * key is used like <key, FieldValue> in lang manager, also FieldValue will be updated
     * with properly value if locale changes
     *
     * if key equals "", then FieldName will be use as the key
     */
    String key() default "";

    /**
     * if we shouldn't write FieldValue to II_Lang and only use annotation to change value on locale change
     * if this value is true, don't forget to write it's FieldValue to en_US.lang
     */
    boolean onlyRead() default false;

    /**
     * defines the default String[] length of the field, if it equals -1, FieldValue.length will be used
     * for String[] key() value is used as prefix and element index is used as postfix.
     */
    int length() default -1;

}
