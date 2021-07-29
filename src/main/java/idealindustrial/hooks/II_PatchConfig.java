package idealindustrial.hooks;

import cpw.mods.fml.relauncher.FMLInjectionData;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class II_PatchConfig {

    private static class FieldedConfig {
        String[] comments;
        Field field;
        boolean defaultValue;

        public FieldedConfig(Field field, boolean defaultValue, String... comments) {
            this.comments = comments;
            this.field = field;
            this.defaultValue = defaultValue;
        }

        void loadConfig(Scanner scanner) {
            for (int i = 0; i <= comments.length; i++)
                scanner.nextLine();
            try {
                String f = scanner.nextLine();
                field.set(null, Boolean.parseBoolean(f.split("=")[1]));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        void writeConfig(PrintWriter writer) {
            for (String comment : comments) {
                writer.println(comment);
            }
            writer.println("\tvalue=" + defaultValue);
            writer.println();

        }

        public String[] getComments() {
            return comments;
        }
    }

    protected static FieldedConfig[] configs;

    static {
        Class<? extends HookLoader> cl = HookLoader.class;
        Field[] ar = Stream.of("ec2Faces", "neiGui", "gtMats", "mineIcon", "ae2SpatialFix", "opis", "worldMultiThread", "euToRf",
                "neiIde").map(s -> {
            try {
                return cl.getDeclaredField(s);
            } catch (NoSuchFieldException exception) {
                exception.printStackTrace();
                return null;
            }
        }).filter(Objects::nonNull).toArray(Field[]::new);
        configs = new FieldedConfig[]{
                new FieldedConfig(ar[0], false, "\t#EC2 fluid face fix (fixes bug in fluid interfaces when all fluids swapped)"),
                new FieldedConfig(ar[1], true, "\t#AE2 GUI NEI fix ( fixes nei tabs overlap on AE2 and EC2 guis)"),
                new FieldedConfig(ar[2], false, "\t#GT Material ids remap feature (enables GT_Materials Remap)"),
                new FieldedConfig(ar[3], true, "\t#Replace minecraft window title and icon"),
                new FieldedConfig(ar[4], false, "\t#Fix GT tiles in AE2 spatial pylons"),
                new FieldedConfig(ar[5], false, "\t#Fix Opis GT Tile Names"),
                new FieldedConfig(ar[6], false, "\t#Some patches to load server worlds in different threads (may not work on custom cores)"),
                new FieldedConfig(ar[7], false, "\t#Integration between GT, IC2 chargers and RF consumers. Also energy conduits connect to GT sources"),
                new FieldedConfig(ar[8], false, "\t#Just a fix for strange NEI behavior in IDE, useless for regular players")

        };
    }

    public static void load() {
        File minecraftDirectory = (File) FMLInjectionData.data()[6];
        File configuration = new File(minecraftDirectory, "config/II_Patches.cfg");
        if (!configuration.exists()) {
            try {
                generateConfig(configuration);
            } catch (IOException exception) {
                exception.printStackTrace();
                return;
            }
        }
        try {
            readConfig(configuration);
        } catch (Exception e) {
            if (configuration.exists())
                configuration.delete();
            try {
                configuration.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            try {
                generateConfig(configuration);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }


    private static void readConfig(File file) {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        scanner.nextLine();
        scanner.nextLine();

        for (FieldedConfig config : configs) {
            config.loadConfig(scanner);
        }
    }

    private static void generateConfig(File file) throws IOException {
        PrintWriter writer;
        writer = new PrintWriter(new FileWriter(file));

        writer.println("#THIS IS NOT REGULAR MINECRAFT .cfg FILE, DO NOT MAKE ANY STRUCT CHANGES HERE OR EVERYTHING WILL BE BROKEN#");
        writer.println("#change only values, not formatting#");
        writer.println("Patches:");
        for (FieldedConfig config : configs) {
            config.writeConfig(writer);
        }
        writer.flush();

    }
}
