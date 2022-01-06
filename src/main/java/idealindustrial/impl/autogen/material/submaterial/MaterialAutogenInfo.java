package idealindustrial.impl.autogen.material.submaterial;

import java.lang.reflect.Field;
import java.util.*;

public class MaterialAutogenInfo {


    Set<HeatType> heatTypes = new HashSet<>();
    Set<MetalFormType> metalFormTypes = new HashSet<>();
    public int heatingHardness;
    public int toolSpeed;
    public int toolLevel;
    public int toolHardness;
    public int oreTier;

    public boolean isHeatTypeAllowed(HeatType type) {
        return heatTypes.contains(type);
    }

    public void parseFrom(Map<String, Integer> header, List<String> values) {
        new Parser(header, values).parse();
    }


    public enum HeatType {
        PrimitiveForge("PF");
        final String name;

        HeatType(String name) {
            this.name = name;
        }

        public static HeatType parse(String s) {
            return Arrays.stream(values()).filter(t -> t.name.equals(s)).findFirst().orElseThrow(() -> new IllegalArgumentException("cannot parse HeatType " + s));
        }
    }

    public enum MetalFormType {
        PlateRolling("P");
        final String name;

        MetalFormType(String name) {
            this.name = name;
        }

        public static MetalFormType parse(String s) {
            return Arrays.stream(values()).filter(t -> t.name.equals(s)).findFirst().orElseThrow(() -> new IllegalArgumentException("cannot parse MetalFormType " + s));
        }
    }

    private static List<String> primitiveFields = Arrays.asList("Heating Hardness", "Ore Tier", "Tool Speed", "Tool Level", "Tool Hardness");

    private class Parser {
        Map<String, Integer> header;
        List<String> values;

        public Parser(Map<String, Integer> header, List<String> values) {
            this.header = header;
            this.values = values;
        }

        void parse() {
            String heatData = values.get(header.get("Heat Types"));
            if (!heatData.equals("")) {
                Arrays.stream(heatData.split(",")).forEach(s -> heatTypes.add(HeatType.parse(s)));
            }
            String metalFormData = values.get(header.get("Metal Form Types"));
            if (!metalFormData.equals("")) {
                Arrays.stream(metalFormData.split(",")).forEach(s -> metalFormTypes.add(MetalFormType.parse(s)));
            }
            primitiveFields.forEach(this::parseField);
        }

        private void parseField(String name) {
            try {
                Integer id = header.get(name);
                if (id == null) {
                    throw new IllegalStateException("Error, no column for " + name + "is presented");
                }
                String data = values.get(id);
                Field field = MaterialAutogenInfo.class.getDeclaredField(name.substring(0, 1).toLowerCase().concat(name.substring(1).replace(" ", "")));
                field.setAccessible(true);
                Class<?> type = field.getType();
                if (type.equals(int.class)) {
                    int parsed = data.equals("") ? 0 : Integer.parseInt(data);
                    field.set(MaterialAutogenInfo.this, parsed);
                } else if (type.equals(long.class)) {
                    long parsed = data.equals("") ? 0 : Long.parseLong(data);
                    field.set(MaterialAutogenInfo.this, parsed);
                } else if (type.equals(String.class)) {
                    field.set(MaterialAutogenInfo.this, data);
                } else {
                    throw new IllegalArgumentException("Unknown data type " + type);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalStateException("error", e);
            }
        }
    }


}
