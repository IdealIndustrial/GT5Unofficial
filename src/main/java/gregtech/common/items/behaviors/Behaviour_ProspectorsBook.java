package gregtech.common.items.behaviors;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.GT_Block_Ores_Abstract;
import gregtech.common.blocks.GT_TileEntity_Ores;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import scala.tools.nsc.doc.model.Public;

import java.util.*;

import static gregtech.common.GT_UndergroundOil.undergroundOilReadInformation;

public class Behaviour_ProspectorsBook
        extends Behaviour_None {
    private static int radius, near, middle, step;
    public static String[] types = new String[]{Materials.Tin.mName,Materials.Copper.mName,Materials.Redstone.mName,Materials.Iron.mName,Materials.Lead.mName, Materials.BandedIron.mName, Materials.Chalcopyrite.mName, Materials.Tetrahedrite.mName, Materials.Cassiterite.mName, Materials.BrownLimonite.mName, Materials.Nickel.mName};
    public static HashSet<String> prospectingOres = new HashSet<>();

    static {
        radius = 112;
        near = radius / 3;
        near = near + near % 2; // making near value even;
        middle = near * 2;
        step = 4;
        prospectingOres.addAll(Arrays.asList(types));
    }

    public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_ProspectorsBook();
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.prospecting", "Looks old, really old");


    @SideOnly(Side.CLIENT)
    public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if ((GT_Utility.isStringValid(GT_Utility.ItemNBT.getBookTitle(aStack))) && ((aPlayer instanceof EntityPlayerSP))) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(aPlayer, aStack, false));
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if ((GT_Utility.isStringValid(GT_Utility.ItemNBT.getBookTitle(aStack))) && ((aPlayer instanceof EntityPlayerSP))) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(aPlayer, aStack, false));
        }
        return aPlayer instanceof EntityPlayerMP;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack onItemRightClick(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        if ((GT_Utility.isStringValid(GT_Utility.ItemNBT.getBookTitle(aStack))) && ((aPlayer instanceof EntityPlayerSP))) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(aPlayer, aStack, false));
        }
        return aStack;
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        return aList;
    }

    private static String prospectWater(World aWorld, int aX, int aZ) {

        FluidStack tFluid = null;

        Chunk tChunk = aWorld.getChunkFromBlockCoords(aX, aZ);
        int range = 6; //(int)Math.ceil((double)radius / 16);
        int xChunk = (tChunk.xPosition / range) * range - ((tChunk.xPosition < 0 && tChunk.xPosition % range != 0) ? range : 0);
        int zChunk = (tChunk.zPosition / range) * range - ((tChunk.zPosition < 0 && tChunk.zPosition % range != 0) ? range : 0);

        LinkedHashMap<ChunkCoordIntPair, FluidStack> tFluids = new LinkedHashMap<>();
        int oilFieldCount = 0;

        try {
            for (int z = -1; z <= 1; ++z) {
                for (int x = -1; x <= 1; ++x) {
                    ChunkCoordIntPair cInts = aWorld.getChunkFromChunkCoords(x, z).getChunkCoordIntPair();
                    ArrayList<Integer> minMaxValue = new ArrayList<>();

                    for (int i = 0; i < range; i++) {
                        for (int j = 0; j < range; j++) {
                            tChunk = aWorld.getChunkFromChunkCoords(xChunk + i + x * 6, zChunk + j + z * 6);
                            tFluid = undergroundOilReadInformation(tChunk);
                            if (tFluid != null) {
                                if (!tFluids.containsKey(cInts)) {
                                    tFluids.put(cInts, tFluid);
                                }
                                if(tFluid!=null&&GT_Utility.areFluidsEqual(tFluid, new FluidStack(FluidRegistry.WATER,100)))
                                    return (""+(xChunk+x*6)+"|"+(zChunk+z*6)+"|"+tFluid.getLocalizedName());

                            }
                        }
                    }

                    int min = Collections.min(minMaxValue);
                    int max = Collections.max(minMaxValue);

                }
            }
        } catch (Exception e) {/*Do nothing*/}
        return "";
    }

    private static void prospectOres(Map<String,ArrayList<int[]>> aAllOres,Map<String, Integer> aNearOres, Map<String, Integer> aMiddleOres, Map<String, Integer> aFarOres, World aWorld, int aX, int aY, int aZ) {
        int tLeftXBound = aX - radius;
        int tRightXBound = tLeftXBound + 2*radius;

        int tLeftZBound = aZ - radius;
        int tRightZBound = tLeftZBound + 2*radius;

        for (int i = tLeftXBound; i <= tRightXBound; i += step)
            for (int k = tLeftZBound; k <= tRightZBound; k += step) {
                int di = Math.abs(i - aX);
                int dk = Math.abs(k - aZ);

                if (di <= near && dk <= near)
                    prospectHole(i, k, aAllOres, aWorld, aY);
                else if (di <= middle && dk <= middle)
                    prospectHole(i, k, aAllOres, aWorld,aY);
                else
                    prospectHole(i, k, aAllOres, aWorld, aY);
            }
    }

    private static void prospectHole(int i, int k, Map<String, ArrayList<int[]>> aOres, World aWorld,  int aY) {

        for (int j = aY; j > 0; j--) {
            checkForOre(i, j, k, aWorld,aOres);
        }
    }

    private static String checkForOre(int x, int y, int z, World aWorld, Map<String, ArrayList<int[]>> map) {
        Block tBlock = aWorld.getBlock(x, y, z);

        if (tBlock instanceof GT_Block_Ores_Abstract) {
            TileEntity tTileEntity = aWorld.getTileEntity(x, y, z);

            if ((tTileEntity instanceof GT_TileEntity_Ores)
                    && (((GT_TileEntity_Ores) tTileEntity).mMetaData < 16000)) { // Filtering small ores
                Materials tMaterial
                        = GregTech_API.sGeneratedMaterials[((GT_TileEntity_Ores) tTileEntity).mMetaData % 1000];

                if ((tMaterial != null) && (tMaterial != Materials._NULL)) {
                    putOre(map,tMaterial.mName,x,y,z);
                    return tMaterial.mDefaultLocalName;
                }
            }
        }
        return null;
    }

    private static void putOre(Map<String, ArrayList<int[]>> map, String ore, int aX, int aY, int aZ) {
        if(!prospectingOres.contains(ore))
            return;
        ArrayList<int[]> coords = map.get(ore);
        coords = (coords == null) ? new ArrayList<int[]>() : coords;
        coords.add(new int[]{aX,aY,aZ});

        map.put(ore, coords);
    }

    public static ItemStack getBook(World aWorld, int aX, int aY, int aZ, Random aRandom){
        ItemStack aStack = ItemList.ProspectorsBook.get(1);

        HashMap<String, Integer> tNearOres = new HashMap<String, Integer>();
        HashMap<String, Integer> tMiddleOres = new HashMap<String, Integer>();
        HashMap<String, Integer> tFarOres = new HashMap<String, Integer>();
        HashMap<String, ArrayList<int[]>> allOres = new HashMap<>();

        prospectOres(allOres,tNearOres, tMiddleOres, tFarOres, aWorld, aX, aY, aZ);

        String tOil =  prospectWater(aWorld, aX, aZ);

        new StoryGenerator(aRandom,allOres,aStack,tOil,new int[]{aX,aY,aZ});
        return aStack;
    }

    private static class StoryGenerator{

        private final String[] groundLevels = GT_LanguageManager.addStringLocalization("gt.prospectorsbook.groundlevels", "almost at surface|at cave level|deep in solid rock").split("\\|");

        private final String[] caveEvents = GT_LanguageManager.addStringLocalization("gt.prospectorsbook.caveevents",
                "met some cave spiders today, my luck is good though I'm still alive |" +
                "found some malachite ore, I need a bit more iron to forge a new pickaxe|" +
                "tried to dig deeper, I almost swam in lava| "+
                "When expanding the mine came across a cluster of gravel above me, I was almost buried alive.| " +
                "In mine flooded one of lava lake with water. I hope one day I dig up enough diamonds or cobalt to mine resulting obsidian.| " +
                "The trip to the mine turned out to be very productive , I had to finish due to a lack of torches.| " +
                "Throughout my stay in the mine, I was annoyed by the peep of bats. It's pisses me off.| " +
                "In the old mine, I came across a railroad. I’m thinking about getting a minecart.| " +
                "In mine unearthed the nest of silverfish. Argh, they are small and weak, but there are so many.| " +
                "Dug a passage into canyon. It's huge and dark, with few waterfall of lava, need to observe it better next time.| " +
                "In the cave I found some mushrooms. Looks like for dinner, I'll have mushroom soup.| " +
                "I dug up so deep that I came across a rock that I had never seen before. It's dark gray and appears to be even harder than obsidian.| " +
                "I found a chest in an abandoned mine, inside was gold armor for horse. Who and why could leave it?").split("\\|");

        private final String[] rutineEvents = GT_LanguageManager.addStringLocalization("gt.prospectorsbook.rutineevents",
                "Milked my cow -again-, milk can heal every injury!|" +
                "Harvested some wheat and baked bread -again-, delicious |" +
                "Axed oak and got some apples -again- .. what?!|"+
                "Today I met \"crossed out\" -again-, he looks sick: his skin has turned green, I hope it’s not contagious|" +
                "-again- Met a frightened and dirty pig, she was saddled. What's wrong with you people?|" +
                "-again- Someone painted one of my sheep pink, what a jerk.|" +
                "This night -again- slimes from the swamps kept me awake. I need to move away from here.|" +
                "-again- Half a day chasing a chicken, what a shame. Still not seen a single rooster.|" +
                "-again- Spent a few buckets of water to wash redstone dust today. Eh, it would be great if someone did this for me.|" +
                "Sold the ore I mine to Villagers -again-. Seems our relationship is improving: prices have become more profitable.|" +
                "I -again- found an octopus, which trying to get out of the shallow. When I helped him out, he was so scared that dropped his ink bag.|" +
                "Met a woman in a weird hat -again-. She turned out to be a sweet lady and treated me to a drink of her own preparation.|" +
                "Today I saw a herd of donkeys -again-. I thinking of catching one to make it easier еo transport more ore from the mine.").split("\\|");

        private final String[] oreFindEvents = GT_LanguageManager.addStringLocalization("gt.prospectorsbook.orefindevents",
                "I found some -name- ore traces|" +
                "while going back I saw some -name- ore pieces|" +
                "I noticed some -name- ore chunks").split("\\|");

        private final String[] agains = GT_LanguageManager.addStringLocalization("gt.prospectorsbook.agains", "again|once more|one more time").split("\\|");
        private final String[] ands = GT_LanguageManager.addStringLocalization("gt.prospectorsbook.ands", "and then|also").split("\\|");
        private final String[] laters = GT_LanguageManager.addStringLocalization("gt.prospectorsbook.laters", "Later today I|In 2 hours I|Some time after I").split("\\|");

        Random random;

        HashSet<Integer> alreadyUsedMine = new HashSet<>();
        HashSet<Integer> alreadyUsedRutine = new HashSet<>();

        public StoryGenerator(Random aRandom, HashMap<String, ArrayList<int[]>> allOres, ItemStack aBook, String aOil, int[] tilePos){
            random = aRandom;

            NBTTagCompound tNBT = new NBTTagCompound();
            NBTTagList tTagList = new NBTTagList();
            String tPageText = "";
            ArrayList<String> tOres = new ArrayList<>();
            int day = 1;
            for(String oreKey : allOres.keySet()){
                ArrayList<int[]> orePositions = allOres.get(oreKey);
                if(orePositions == null||orePositions.size()==0)
                    continue;
                int[] position = orePositions.get(aRandom.nextInt(orePositions.size()));
                tPageText = EnumChatFormatting.BOLD+ "Day "+day +EnumChatFormatting.RESET + "\n" + getRandomRutineEvent(random);
                tTagList.appendTag(new NBTTagString(tPageText));
                tPageText = laters[random.nextInt(laters.length)] + " "
                        + getRandomMineEvent(random)+" "+ands[random.nextInt(ands.length)]+" "
                        +getRandomOreFoundEvent(random, oreKey)+" "
                        +getOrePositionRelative(position,tilePos);
                tTagList.appendTag(new NBTTagString(tPageText));
                day+=1+random.nextInt(5);

            }
            tPageText = "";
            if(aOil!=""){
                String[] aWater = aOil.split("\\|");
                tPageText += "It seems that there is underground water source at " + getDirectionZ(Integer.valueOf(aWater[1])*16-tilePos[2]) +" and "+ getDirectionX(Integer.valueOf(aWater[0])*16-tilePos[0]) + " ";
            }
            tPageText += "now I'm leaving this place, may be sombody will find my diary and read it";
            tTagList.appendTag(new NBTTagString(tPageText));
            tNBT.setString("author", "unknown miner");
            tNBT.setString("title", "Written long time ago");
            tNBT.setTag("pages", tTagList);
            aBook.setTagCompound(tNBT);
        }

        private String genDay(int aDay, String oreName, int[] orePosition, int[] tilePosition, Random random){
            String out = EnumChatFormatting.BOLD+ "Day "+aDay +EnumChatFormatting.RESET + "\n"
                    + getRandomRutineEvent(random)+" "+laters[random.nextInt(laters.length)] + " "
                    + getRandomMineEvent(random)+" "+ands[random.nextInt(ands.length)]+" "
                    +getRandomOreFoundEvent(random, oreName)+" "
                    +getOrePositionRelative(orePosition,tilePosition);

                    return out;
        }

        private String getGroundLevel(int y){
            if(y>50)
                return groundLevels[0];
            else if (y > 30)
                return groundLevels[1];
            else
                return groundLevels[2];
        }

        private String getRandomMineEvent( Random random){
            int event = random.nextInt(caveEvents.length);
            int i = 0;
            while (alreadyUsedMine.contains(event)&&i<100){
                i++;
                event = random.nextInt(caveEvents.length);
            }
            if(alreadyUsedMine.contains(event))
                return "Not enaugh events LOL";
            alreadyUsedMine.add(event);
            return caveEvents[event];
        }

        private String getRandomRutineEvent( Random random){
            int event = random.nextInt(rutineEvents.length);
            String out = rutineEvents[event];
            if(alreadyUsedRutine.contains(event)){
                out = out.replaceAll("-again-", agains[random.nextInt(agains.length)]);
            }else
                out = out.replaceAll("-again-","");
            alreadyUsedRutine.add(event);
            out+=".";
            return out;
        }

        private String getRandomOreFoundEvent(Random random, String ore){
            int event = random.nextInt(oreFindEvents.length);
            String out = oreFindEvents[event].replaceAll("-name-",ore);
            return out;
        }

        private String getOrePositionRelative(int[] orePosition, int[] tilePosition){
            int aX = orePosition[0] - tilePosition[0];
            int aZ = orePosition[2] - tilePosition[2];
            return "in "+getGroundLevel(orePosition[1])+ " "+ getDirectionZ(aZ) + " and " + getDirectionX(aX);

        }

        private String getDirectionZ(int aZ){
            if(aZ<0)
                return (-aZ)+" m to North";
            else
                return (aZ)+ " m to South";
        }

        private String getDirectionX(int aX){
            if(aX<0)
                return (-aX)+ " m to West";
            else
                return (aX)+ " m to East";
        }
    }
}
