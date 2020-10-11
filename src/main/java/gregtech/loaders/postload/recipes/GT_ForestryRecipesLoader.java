package gregtech.loaders.postload.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import forestry.plugins.PluginFarming;
import forestry.farming.blocks.BlockRegistryFarming;
import forestry.farming.render.EnumFarmBlockTexture;
import forestry.farming.blocks.BlockFarmType;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class GT_ForestryRecipesLoader implements Runnable {

    private final static String aTextForestry = "Forestry";
    public static BlockRegistryFarming blocks;
	
    protected void registerItemsAndBlocks() {
          blocks = PluginFarming.blocks;
    }	

    @Override
    public void run() {
        registerItemsAndBlocks();

        GT_Values.RA.addLatheRecipe(GT_ModHandler.getModItem(aTextForestry, "slabs", 1L, GT_Values.W), new ItemStack(Items.bowl, 1), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Wood, 1), 50, 8);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Copper, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 0), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.AnnealedCopper, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 0), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tin, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 1), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 2), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 3), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 3), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 4), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 5), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Obsidian, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 6), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Blaze, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 7), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 8), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Emerald, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 9), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Apatite, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 10), 64, 28);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedstoneAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 4L), ItemList.Circuit_Parts_Glass_Tube.get(1L)}, Materials.Glass.getMolten(144L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 11), 64, 28);

        GT_Values.RA.addMixerRecipe(ItemList.IC2_Fertilizer.get(1L, new Object[0]), new ItemStack(Blocks.dirt, 8, 32767), GT_Values.NI, GT_Values.NI, Materials.Water.getFluid(1000L), GT_Values.NF, GT_ModHandler.getModItem(aTextForestry, "soil", 8L, 0), 64, 16);
        GT_Values.RA.addMixerRecipe(ItemList.FR_Fertilizer.get(1L, new Object[0]), new ItemStack(Blocks.dirt, 8, 32767), GT_Values.NI, GT_Values.NI, Materials.Water.getFluid(1000L), GT_Values.NF, GT_ModHandler.getModItem(aTextForestry, "soil", 8L, 0), 64, 16);
        GT_Values.RA.addMixerRecipe(ItemList.FR_Compost.get(1L, new Object[0]), new ItemStack(Blocks.dirt, 8, 32767), GT_Values.NI, GT_Values.NI, Materials.Water.getFluid(1000L), GT_Values.NF, GT_ModHandler.getModItem(aTextForestry, "soil", 8L, 0), 64, 16);
        GT_Values.RA.addMixerRecipe(ItemList.FR_Mulch.get(1L, new Object[0]), new ItemStack(Blocks.dirt, 8, 32767), GT_Values.NI, GT_Values.NI, Materials.Water.getFluid(1000L), GT_Values.NF, GT_ModHandler.getModItem(aTextForestry, "soil", 9L, 0), 64, 16);
        GT_Values.RA.addMixerRecipe(new ItemStack(Blocks.sand, 1, 32767), new ItemStack(Blocks.dirt, 1, 32767), GT_Values.NI, GT_Values.NI, Materials.Water.getFluid(250L), GT_Values.NF, GT_ModHandler.getModItem(aTextForestry, "soil", 2L, 1), 16, 16);
        GT_Values.RA.addFluidSmelterRecipe(GT_ModHandler.getModItem(aTextForestry, "Phosphorus", 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Phosphorus, 1L), Materials.Lava.getFluid(800L), 1000, 256, 128);

        GT_Values.RA.addAssemblerRecipe(ItemList.FR_Wax.get(6L, new Object[0]), new ItemStack(Items.string, 1, 32767), Materials.Water.getFluid(600L), GT_ModHandler.getModItem(aTextForestry, "candle", 24L, 0), 64, 8);
        GT_Values.RA.addAssemblerRecipe(ItemList.FR_Wax.get(2L, new Object[0]), ItemList.FR_Silk.get(1L, new Object[0]), Materials.Water.getFluid(200L), GT_ModHandler.getModItem(aTextForestry, "candle", 8L, 0), 16, 8);
        GT_Values.RA.addAssemblerRecipe(ItemList.FR_Silk.get(9L, new Object[0]), ItemList.Circuit_Integrated.getWithDamage(0L, 9L, new Object[0]), Materials.Water.getFluid(500L), GT_ModHandler.getModItem(aTextForestry, "craftingMaterial", 1L, 3), 64, 8);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem(aTextForestry, "propolis", 5L, 2), ItemList.Circuit_Integrated.getWithDamage(0L, 5L, new Object[0]), GT_Values.NF, GT_ModHandler.getModItem(aTextForestry, "craftingMaterial", 1L, 1), 16, 8);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem(aTextForestry, "sturdyMachine", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Diamond, 4L), Materials.Water.getFluid(5000L), ItemList.FR_Casing_Hardened.get(1L, new Object[0]), 64, 32);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), GT_Values.NF, ItemList.FR_Casing_Sturdy.get(1L, new Object[0]), 32, 16);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 6L), Materials.Water.getFluid(1000L), GT_ModHandler.getModItem(aTextForestry, "chipsets", 1L, 0), 16, 8);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 6L), Materials.Water.getFluid(1000L), GT_ModHandler.getModItem(aTextForestry, "chipsets", 1L, 1), 32, 16);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iron, 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 6L), Materials.Water.getFluid(1000L), GT_ModHandler.getModItem(aTextForestry, "chipsets", 1L, 2), 48, 24);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.WroughtIron, 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 6L), Materials.Water.getFluid(1000L), GT_ModHandler.getModItem(aTextForestry, "chipsets", 1L, 2), 48, 24);
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 6L), Materials.Water.getFluid(1000L), GT_ModHandler.getModItem(aTextForestry, "chipsets", 1L, 3), 64, 32);
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem(aTextForestry, "craftingMaterial", 5L, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 5L, new Object[0]), GT_Values.NF, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.EnderPearl, 1L), 64, 8);

        GT_Values.RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 1L), new ItemStack(Blocks.sand, 2, 32767), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), GT_Values.NI, GT_Values.NF, GT_Values.NF, ItemList.FR_Fertilizer.get(2L, new Object[0]), 64, 16);
        GT_Values.RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 1L), new ItemStack(Blocks.sand, 2, 32767), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), GT_Values.NI, GT_Values.NF, GT_Values.NF, ItemList.FR_Fertilizer.get(4L, new Object[0]), 64, 16);
        GT_Values.RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Apatite, 1L), new ItemStack(Blocks.sand, 2, 32767), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), GT_Values.NI, GT_Values.NF, GT_Values.NF, ItemList.FR_Fertilizer.get(8L, new Object[0]), 64, 16);
        GT_Values.RA.addMixerRecipe(ItemList.FR_Compost.get(1L, new Object[0]), new ItemStack(Blocks.sand, 2, 32767), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), GT_Values.NI, GT_Values.NF, GT_Values.NF, ItemList.FR_Fertilizer.get(4L, new Object[0]), 64, 16);

        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 0));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 2));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 3));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 4));
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 5));

        ItemStack basic = blocks.farm.get(BlockFarmType.BASIC, 1);
        ItemStack gearbox = blocks.farm.get(BlockFarmType.GEARBOX, 1);
        ItemStack hatch = blocks.farm.get(BlockFarmType.HATCH, 1);
        ItemStack valve = blocks.farm.get(BlockFarmType.VALVE, 1);
        ItemStack control = blocks.farm.get(BlockFarmType.CONTROL, 1);

        for (EnumFarmBlockTexture block : EnumFarmBlockTexture.values()) {
            NBTTagCompound compound = new NBTTagCompound();
            block.saveToCompound(compound);

            basic.setTagCompound(compound);
            gearbox.setTagCompound(compound);
            hatch.setTagCompound(compound);
            valve.setTagCompound(compound);
            control.setTagCompound(compound);

            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
                            ItemList.IC2_Item_Casing_Copper.get(4L),
                            GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 4L),
                            GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 1),
                            block.getBase(),
                            GT_Utility.getIntegratedCircuit(5)},
                    Materials.Redstone.getMolten(144),
                    basic, 600, 30);

            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
                            ItemList.Electric_Motor_LV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 4),
                            basic,
                            GT_Utility.getIntegratedCircuit(5)},
                    Materials.Redstone.getMolten(144),
                    gearbox, 600, 30);

            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
                            ItemList.Conveyor_Module_LV.get(2L),
                            ItemList.Electric_Motor_LV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1),
                            GT_ModHandler.getModItem("minecraft", "hopper", 1L, 0),
                            basic,
                            GT_Utility.getIntegratedCircuit(5)},
                    Materials.Redstone.getMolten(144),
                    hatch, 600, 30);

            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
                            ItemList.Electric_Pump_LV.get(2L),
                            ItemList.Electric_Motor_LV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1),
                            GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 1L),
                            basic,
                            GT_Utility.getIntegratedCircuit(5)},
                    Materials.Redstone.getMolten(144),
                    valve, 600, 30);

            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
                            ItemList.Electric_Motor_LV.get(1L),
                            GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1),
                            GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2),
                            basic,
                            GT_Utility.getIntegratedCircuit(5)},
                    Materials.Redstone.getMolten(144),
                    control, 600, 30);

        }
    }
}
