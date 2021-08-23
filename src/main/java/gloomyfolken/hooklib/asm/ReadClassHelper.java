package gloomyfolken.hooklib.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.io.InputStream;

public class ReadClassHelper {

    public static InputStream getClassData(String className) {
        String classResourceName = '/' + className.replace('.', '/') + ".class";
        return ReadClassHelper.class.getResourceAsStream(classResourceName);
    }

    public static void acceptVisitor(InputStream classData, ClassVisitor visitor) {
        try {
            ClassReader reader = new ClassReader(classData);
            reader.accept(visitor, 0);
            classData.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void acceptVisitor(String className, ClassVisitor visitor) {
        acceptVisitor(getClassData(className), visitor);
    }
}
