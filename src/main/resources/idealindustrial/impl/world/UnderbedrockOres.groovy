package idealindustrial.impl.world

import idealindustrial.api.world.underbedrock.*
import idealindustrial.impl.autogen.material.II_Material
import idealindustrial.impl.autogen.material.Prefixes
import idealindustrial.impl.world.UnderbedrockLayerProvider
import idealindustrial.impl.world.UndergroundOre
import idealindustrial.impl.world.underbedrock.RandomOrder
import idealindustrial.util.misc.RandomCollection

import static idealindustrial.impl.autogen.material.II_Materials.iron
import static idealindustrial.impl.autogen.material.Prefixes.dust
import static idealindustrial.impl.world.underbedrock.VeinProviderBuilder.square
import static idealindustrial.util.misc.II_StreamUtil.list

class UnderbedrockOres implements UnderbedrockLayerProvider {

    static DifferenceSupplier<UndergroundOre> supplier(II_Material material, Prefixes prefix) {
        return { count -> new UndergroundOre(material, prefix, count) }
    }

    GridGenerationRules<UndergroundOre> getOverworldBase() {
        return new GridGenerationRules<UndergroundOre>() {
            {
                bigOres.add(1,
                        square(supplier(iron, dust), 100, 1000)
                                .setSizes(1, 3)
                                .setExponentialDifference(2)
                                .get()
                )
            }
            WeightedRandom<VeinProvider<UndergroundOre>> bigOres = new RandomCollection<>(),
                                                         smallOres = new RandomCollection<>(),
                                                         sporadicOres = new RandomCollection<>()

            List<WeightedRandom<VeinProvider<UndergroundOre>>> passes = list(
                    bigOres, bigOres, smallOres, smallOres, sporadicOres
            )

            @Override
            int getPassCount() {
                return 1
            }

            @Override
            WeightedRandom<VeinProvider<UndergroundOre>> getProviderForPass(int pass) {
                return passes[pass]
            }

            @Override
            OrderGenerator getOrder(int size, Random random) {
                return new RandomOrder(size, random)
            }

            @Override
            int getGridSize() {
                return 10
            }

            @Override
            int getClusterSize() {
                return 10
            }
        }
    }

    Map<String, GridGenerationRules<UndergroundOre>> dimMap = new HashMap<>()

    void addToMap(String dimName, int layer, GridGenerationRules<UndergroundOre> rules) {
        dimMap.put(dimName.toLowerCase() + layer, rules)
    }

    UnderbedrockOres() {
        addToMap("Overworld", 0, overworldBase)
    }




    @Override
    GridGenerationRules<UndergroundOre> provide(String dimName, int layer) {
        return dimMap.get(dimName.toLowerCase() + layer)
    }
}

