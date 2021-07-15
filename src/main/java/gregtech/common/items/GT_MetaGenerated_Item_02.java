package gregtech.common.items;

import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.items.GT_MetaGenerated_Item_X32;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.*;
import gregtech.common.items.behaviors.Behaviour_Arrow;
import gregtech.common.items.behaviors.Behaviour_ProspectorsBook;
import gregtech.common.items.behaviors.Behaviour_ProspectorsBook_Creative;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class GT_MetaGenerated_Item_02 extends GT_MetaGenerated_Item_X32 {
    public static GT_MetaGenerated_Item_02 INSTANCE;
    private final static String aTextCover = "Usable as Cover"; private final static String aTextForestry = "Forestry";

    public GT_MetaGenerated_Item_02() {
        super("metaitem.02", OrePrefixes.toolHeadSword, OrePrefixes.toolHeadPickaxe, OrePrefixes.toolHeadShovel, OrePrefixes.toolHeadAxe, OrePrefixes.toolHeadHoe, OrePrefixes.toolHeadHammer, OrePrefixes.toolHeadFile, OrePrefixes.toolHeadSaw, OrePrefixes.toolHeadDrill, OrePrefixes.toolHeadChainsaw, OrePrefixes.toolHeadWrench, OrePrefixes.toolHeadUniversalSpade, OrePrefixes.toolHeadSense, OrePrefixes.toolHeadPlow, OrePrefixes.toolHeadArrow, OrePrefixes.toolHeadBuzzSaw, OrePrefixes.turbineBlade, null, null, OrePrefixes.wireFine, OrePrefixes.gearGtSmall, OrePrefixes.rotor, OrePrefixes.stickLong, OrePrefixes.springSmall, OrePrefixes.spring, OrePrefixes.arrowGtWood, OrePrefixes.arrowGtPlastic, OrePrefixes.gemChipped, OrePrefixes.gemFlawed, OrePrefixes.gemFlawless, OrePrefixes.gemExquisite, OrePrefixes.gearGt);
        INSTANCE = this;

        int tLastID = 0;
        boolean potions = true; //Разрешаем пить напитки c эффектами при любой сытости
        boolean drinks = false; //Если есть сытость, то не позволяем "переедать"
        //Длительность эффекта напитка
        int OneMin = 1200;
        int TwoMin = 2400;
        int ThreeMin = 4800;
        int FourMin = 9600;

        ItemList.ThermosCan_Dark_Coffee.set(addItem(tLastID = 0, "Dark Coffee", "Coffee, dark, without anything else",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L), potions, false, false, Potion.moveSpeed.id, OneMin, 1, 100, Potion.hunger.id, OneMin/2, 0, 100)));
        ItemList.ThermosCan_Dark_Cafe_au_lait.set(addItem(tLastID = 1, "Dark Coffee au lait", "Keeping you awake the whole night",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L), potions, false, false, Potion.moveSpeed.id, TwoMin, 1, 100, Potion.hunger.id, TwoMin/2, 0, 100)));
        ItemList.ThermosCan_Coffee.set(addItem(tLastID = 2, "Coffee", "Just the regular morning Coffee",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L), potions, false, false, Potion.digSpeed.id, OneMin, 1, 100, Potion.hunger.id, OneMin/2, 0, 100)));
        ItemList.ThermosCan_Cafe_au_lait.set(addItem(tLastID = 3, "Cafe au lait", "Sweet Coffee",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L), potions, false, false, Potion.digSpeed.id, TwoMin, 1, 100, Potion.hunger.id, TwoMin/2, 0, 100)));
        ItemList.ThermosCan_Lait_au_cafe.set(addItem(tLastID = 4, "Lait au cafe", "You want Coffee to your Sugar?",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L), potions, false, false, Potion.digSpeed.id, ThreeMin, 1, 100, Potion.hunger.id, ThreeMin/2, 0, 100)));
        ItemList.ThermosCan_Dark_Chocolate_Milk.set(addItem(tLastID = 5, "Dark Chocolate Milk", "A bit bitter, better add a bit Sugar",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L), potions, false, false, Potion.regeneration.id, OneMin, 0, 100, Potion.moveSpeed.id, OneMin, 0, 100, Potion.digSpeed.id, OneMin, 0, 100, Potion.hunger.id, OneMin/2, 0, 100)));
        ItemList.ThermosCan_Chocolate_Milk.set(addItem(tLastID = 6, "Chocolate Milk", "Sweet Goodness",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L), potions, false, false, Potion.regeneration.id, TwoMin, 0, 100, Potion.moveSpeed.id, TwoMin, 0, 100, Potion.digSpeed.id, TwoMin, 0, 100, Potion.hunger.id, TwoMin/2, 0, 100)));
        ItemList.ThermosCan_Tea.set(addItem(tLastID = 7, "Tea", "Keep calm and carry on",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L), potions, false, false, Potion.digSpeed.id, OneMin, 1, 100, Potion.hunger.id, OneMin/2, 0, 100)));
        ItemList.ThermosCan_Sweet_Tea.set(addItem(tLastID = 8, "Sweet Tea", "How about a Tea Party? In Boston?",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L), potions, false, false, Potion.digSpeed.id, TwoMin, 1, 100, Potion.hunger.id, TwoMin/2, 0, 100)));
        ItemList.ThermosCan_Ice_Tea.set(addItem(tLastID = 9, "Ice Tea", "Surprisingly refreshing!",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L), potions, false, false, Potion.moveSpeed.id, OneMin, 1, 100, Potion.hunger.id, OneMin/2, 0, 100)));

        ItemList.GelledToluene.set(addItem(tLastID = 10, "Gelled Toluene", "Raw Explosive"));

        ItemList.ProspectorsBook.set(addItem(tLastID = 11, "Miner's Diary", "Tells a story of unknown miner ", new Behaviour_ProspectorsBook()));
        ItemList.ProspectorsBookCreative.set(addItem(tLastID = 12,"Miner's Diary Generator","", new Behaviour_ProspectorsBook_Creative()));

        ItemList.Bottle_Purple_Drink.set(addItem(tLastID = 100, "Purple Drink", "Heavy duty drink!",
                new GT_FoodStat(8, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), drinks, false, false, Potion.moveSlowdown.id, 400, 1, 100)));
        ItemList.Bottle_Grape_Juice.set(addItem(tLastID = 101, "Grape Juice", "Never leave it in a warm place for a long time.",
                new GT_FoodStat(1, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), drinks, false, false, Potion.hunger.id, OneMin/2, 0, 100)));
        ItemList.Bottle_Wine.set(addItem(tLastID = 102, "Wine", "Ordinary",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.regeneration.id, OneMin, 1, 100, Potion.hunger.id, OneMin, 1, 100, Potion.confusion.id, TwoMin/10, 0, 50)));
        ItemList.Bottle_Vinegar.set(addItem(tLastID = 103, "Vinegar", "Exquisite",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.confusion.id, 400, 2, 100, Potion.wither.id, 400, 2, 100)));
        ItemList.Bottle_Potato_Juice.set(addItem(tLastID = 104, "Potato Juice", "Ever seen Potato Juice in stores? No? That has a reason.",
                new GT_FoodStat(1, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.hunger.id, OneMin/2, 0, 100)));
        ItemList.Bottle_Vodka.set(addItem(tLastID = 105, "Vodka", "Not to confuse with Water",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.resistance.id, OneMin, 0, 100, Potion.damageBoost.id, OneMin, 1, 100, Potion.hunger.id, OneMin, 1, 100, Potion.moveSlowdown.id, OneMin, 0, 100, Potion.confusion.id, OneMin/2, 0, 50)));
        ItemList.Bottle_Leninade.set(addItem(tLastID = 106, "Leninade", "Let the Communism flow through you!",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.resistance.id, OneMin, 0, 100, Potion.damageBoost.id, OneMin, 1, 100, Potion.hunger.id, OneMin, 1, 100, Potion.confusion.id, OneMin/2, 0, 50)));
        ItemList.Bottle_Mineral_Water.set(addItem(tLastID = 107, "Mineral Water", "Saturates you with minerals, maybe?",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.field_76443_y.id , 1, 0, 40, Potion.regeneration.id, OneMin, 0, 100)));
        //Солёная вода скрыта, так как крафт отключен
        ItemList.Bottle_Salty_Water.set(addItem(tLastID = 108, "Salty Water", "Like Sea Water but less dirty", SubTag.INVISIBLE,
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.field_76443_y.id , 1, 0, 40, Potion.hunger.id, OneMin/2, 2, 100)));
        ItemList.Bottle_Reed_Water.set(addItem(tLastID = 109, "Reed Water", "I guess this tastes better when fermented",
                new GT_FoodStat(1, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), drinks, false, false, Potion.hunger.id, OneMin/2, 0, 100)));
        ItemList.Bottle_Rum.set(addItem(tLastID = 110, "Rum", "A buddle o' rum",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.jump.id, OneMin, 0, 100, Potion.damageBoost.id, OneMin, 1, 100, Potion.hunger.id, OneMin, 1, 100, Potion.confusion.id, OneMin/2, 0, 50)));
        ItemList.Bottle_Pirate_Brew.set(addItem(tLastID = 111, "Pirate Brew", "Set the Sails, we are going to Torrentuga!",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.jump.id, TwoMin, 1, 100, Potion.damageBoost.id, TwoMin, 2, 100, Potion.hunger.id, TwoMin, 1, 100, Potion.confusion.id, OneMin/2, 0, 50, Potion.blindness.id, OneMin/10, 0, 30)));
        ItemList.Bottle_Hops_Juice.set(addItem(tLastID = 112, "Hops Juice", "Every Beer has a start",
                new GT_FoodStat(1, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), drinks, false, false, Potion.hunger.id, OneMin/2, 0, 100)));
        ItemList.Bottle_Dark_Beer.set(addItem(tLastID = 113, "Dark Beer", "Dark Beer, for the real Men",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.digSpeed.id, TwoMin, 1, 100, Potion.hunger.id, TwoMin, 1, 100, Potion.confusion.id, OneMin/10, 0, 50)));
        ItemList.Bottle_Dragon_Blood.set(addItem(tLastID = 114, "Dragon Blood", "FUS RO DAH!",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.digSpeed.id, TwoMin, 2, 100, Potion.hunger.id, TwoMin, 1, 100, Potion.confusion.id, TwoMin/10, 0, 50)));
        ItemList.Bottle_Wheaty_Juice.set(addItem(tLastID = 115, "Wheaty Juice", "Is this liquefied Bread or what?",
                new GT_FoodStat(1, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), drinks, false, false, Potion.hunger.id, OneMin/2, 0, 100)));
        ItemList.Bottle_Scotch.set(addItem(tLastID = 116, "Scotch", "Technically this is just a Whisky",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.field_76444_x.id, TwoMin, 1, 100, Potion.regeneration.id, TwoMin, 1, 100, Potion.hunger.id, TwoMin, 1, 100, Potion.confusion.id, OneMin/2, 0, 50)));
        ItemList.Bottle_Glen_McKenner.set(addItem(tLastID = 117, "Glen McKenner", "Don't hand to easily surprised people, they will shatter it.",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.field_76444_x.id, TwoMin, 2, 100, Potion.regeneration.id, TwoMin, 2, 100, Potion.hunger.id, TwoMin, 2, 100, Potion.confusion.id, OneMin/2, 0, 50)));
        ItemList.Bottle_Wheaty_Hops_Juice.set(addItem(tLastID = 118, "Wheaty Hops Juice", "Also known as 'Duff-Lite'",
                new GT_FoodStat(1, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), drinks, false, false, Potion.hunger.id, OneMin/2, 0, 100)));
        ItemList.Bottle_Beer.set(addItem(tLastID = 119, "Beer", "Good old Beer",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.moveSpeed.id, TwoMin, 1, 100, Potion.digSpeed.id, TwoMin, 1, 100, Potion.hunger.id, TwoMin, 1, 100, Potion.confusion.id, OneMin/10, 0, 50)));
        ItemList.Bottle_Chilly_Sauce.set(addItem(tLastID = 120, "Chilly Sauce", "Spicy",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.confusion.id, OneMin/10, 0, 100, Potion.fireResistance.id, OneMin/10, 0, 100)));
        ItemList.Bottle_Hot_Sauce.set(addItem(tLastID = 121, "Hot Sauce", "Very Spicy, I guess?",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.confusion.id, TwoMin/10, 0, 100, Potion.fireResistance.id, TwoMin/10, 0, 100)));
        ItemList.Bottle_Diabolo_Sauce.set(addItem(tLastID = 122, "Diabolo Sauce", "As if the Devil made this Sauce",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.confusion.id, ThreeMin/10, 1, 100, Potion.fireResistance.id, ThreeMin/10, 0, 100, Potion.poison.id, ThreeMin/10, 0, 100)));
        ItemList.Bottle_Diablo_Sauce.set(addItem(tLastID = 123, "Diablo Sauce", "Diablo always comes back!",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.confusion.id, FourMin/10, 1, 100, Potion.blindness.id, FourMin/10, 2, 50, Potion.fireResistance.id, FourMin/10, 0, 100, Potion.poison.id, FourMin/10, 0, 100)));
        ItemList.Bottle_Snitches_Glitch_Sauce.set(addItem(tLastID = 124, "Old Man Snitches glitched Diablo Sauce", "[Missing No]", SubTag.INVISIBLE,
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.blindness.id, FourMin/5, 2, 100, Potion.fireResistance.id, FourMin/5, 1, 100, Potion.poison.id, FourMin/5, 0, 100))); // Не крафтится?
        ItemList.Bottle_Apple_Juice.set(addItem(tLastID = 125, "Apple Juice", "Made of the Apples from our best Oak Farms",
                new GT_FoodStat(1, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), drinks, false, false, Potion.hunger.id, OneMin/2, 0, 100)));
        ItemList.Bottle_Cider.set(addItem(tLastID = 126, "Cider", "If you have nothing better to do with your Apples",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.moveSpeed.id, TwoMin, 1, 100, Potion.digSpeed.id, TwoMin, 1, 100, Potion.hunger.id, TwoMin, 1, 100, Potion.confusion.id, OneMin/10, 0, 50)));
        ItemList.Bottle_Golden_Apple_Juice.set(addItem(tLastID = 127, "Golden Apple Juice", "A golden Apple in liquid form",
                new GT_FoodStat(4, 0.6F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.field_76444_x.id, OneMin, 1, 100, Potion.regeneration.id, OneMin/10, 1, 100)));
        ItemList.Bottle_Golden_Cider.set(addItem(tLastID = 128, "Golden Cider", "Harder Better Faster Stronger",
                new GT_FoodStat(4, 0.6F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.field_76444_x.id, TwoMin, 1, 100, Potion.regeneration.id, OneMin/5, 1, 100, Potion.moveSpeed.id, TwoMin, 1, 100)));
        ItemList.Bottle_Iduns_Apple_Juice.set(addItem(tLastID = 129, "Idun's Apple Juice", "So you got the Idea of using Notch Apples for a drink?",
                new GT_FoodStat(4, 0.6F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.field_76444_x.id, OneMin, 2, 100, Potion.regeneration.id, OneMin/2, 2, 100, Potion.resistance.id, OneMin, 1, 100, Potion.fireResistance.id, OneMin, 0, 100)));
        ItemList.Bottle_Notches_Brew.set(addItem(tLastID = 130, "Notches Brew", "This is just overpowered",
                new GT_FoodStat(4, 0.6F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.field_76444_x.id, TwoMin, 2, 100, Potion.regeneration.id, OneMin, 2, 100, Potion.resistance.id, TwoMin, 1, 100, Potion.fireResistance.id, TwoMin, 0, 100, Potion.moveSpeed.id, TwoMin, 1, 100)));
        ItemList.Bottle_Lemon_Juice.set(addItem(tLastID = 131, "Lemon Juice", "Maybe adding Sugar will make it less sour",
                new GT_FoodStat(1, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), drinks, false, false, Potion.hunger.id, OneMin/2, 0, 100, Potion.confusion.id, OneMin/10, 0, 100)));
        ItemList.Bottle_Limoncello.set(addItem(tLastID = 132, "Limoncello", "An alcoholic Drink which tastes like Lemons",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.digSpeed.id, TwoMin, 2, 100, Potion.hunger.id, TwoMin, 1, 100, Potion.confusion.id, OneMin/10, 0, 50)));
        ItemList.Bottle_Lemonade.set(addItem(tLastID = 133, "Lemonade", "Cold and refreshing Lemonade",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.moveSpeed.id, TwoMin, 0, 100, Potion.digSpeed.id, TwoMin, 0, 100, Potion.hunger.id, TwoMin/2, 0, 100)));
        ItemList.Bottle_Alcopops.set(addItem(tLastID = 134, "Alcopops", "Don't let your Children drink this junk!",
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.moveSpeed.id, OneMin/2, 2, 100, Potion.jump.id, OneMin/2, 2, 100, Potion.hunger.id, OneMin/2, 2, 100, Potion.confusion.id, OneMin/2, 2, 100)));
        //Взрывной напиток возможно стоит удалить
        ItemList.Bottle_Cave_Johnsons_Grenade_Juice.set(addItem(tLastID = 135, "Cave Johnson's Grenade Juice", "When life gives you Lemons, make Life take them Lemons back!", SubTag.INVISIBLE,
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false).setExplosive()));
        ItemList.Bottle_Milk.set(addItem(tLastID = 136, "Milk", "Got Milk?", OrePrefixes.bottle.get(Materials.Milk),
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false).setMilk()));
        ItemList.Bottle_Holy_Water.set(addItem(tLastID = 137, "Holy Water", "May the holy Planks be with you", OrePrefixes.bottle.get(Materials.HolyWater), SubTag.INVISIBLE,
                new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L), potions, false, false, Potion.poison.id, 100, 1, 100).setMilk()));

        
        
        ItemList.Food_Potato_On_Stick.set(addItem(tLastID = 200, "Potato on a Stick", "Totally looks like a Crab Claw", new GT_FoodStat(1, 0.3F, EnumAction.eat, new ItemStack(Items.stick, 1), false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        ItemList.Food_Potato_On_Stick_Roasted.set(addItem(tLastID = 201, "Roasted Potato on a Stick", "Still looks like a Crab Claw", new GT_FoodStat(6, 0.6F, EnumAction.eat, new ItemStack(Items.stick, 1), false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Raw_Fries.set(addItem(tLastID = 202, "Potato Strips", "It's Potato in Stripe Form", new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Fries.set(addItem(tLastID = 203, "Fries", "Not to confuse with Fry the Delivery Boy", new GT_FoodStat(7, 0.5F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Packaged_Fries.set(addItem(tLastID = 204, "Fries", "Ketchup not included", new GT_FoodStat(7, 0.5F, EnumAction.eat, GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 1L), false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Raw_PotatoChips.set(addItem(tLastID = 205, "Potato Chips (Raw)", "Just like a Potato", new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_PotatoChips.set(addItem(tLastID = 206, "Potato Chips", "Crunchy", new GT_FoodStat(7, 0.5F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_ChiliChips.set(addItem(tLastID = 207, "Chili Chips", "Spicy", new GT_FoodStat(7, 0.6F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Packaged_PotatoChips.set(addItem(tLastID = 208, "Bag of Potato Chips", "Full of delicious Air", new GT_FoodStat(7, 0.5F, EnumAction.eat, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 1L), false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Packaged_ChiliChips.set(addItem(tLastID = 209, "Bag of Chili Chips", "Stop making noises Baj!", new GT_FoodStat(7, 0.6F, EnumAction.eat, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 1L), false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Chum.set(addItem(tLastID = 210, "Chum", "Chum is Fum!", new GT_FoodStat(5, 0.2F, EnumAction.eat, null, true, false, true, Potion.hunger.id, 1000, 4, 100, Potion.confusion.id, 300, 1, 80), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Chum_On_Stick.set(addItem(tLastID = 211, "Chum on a Stick", "Don't forget to try our Chum-balaya", new GT_FoodStat(5, 0.2F, EnumAction.eat, new ItemStack(Items.stick, 1), true, false, true, Potion.hunger.id, 1000, 4, 100, Potion.confusion.id, 300, 1, 80), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Dough_Sugar.set(addItem(tLastID = 212, "Sugary Dough", "Don't eat the Dough before it is baken", new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Food_Dough_Chocolate.set(addItem(tLastID = 213, "Chocolate Dough", "I said don't eat the Dough!", new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Food_Raw_Cookie.set(addItem(tLastID = 214, "Cookie shaped Dough", "For baking Cookies", new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));

        ItemList.Food_Sliced_Buns.set(addItem(tLastID = 220, "Buns", "Pre Sliced", new GT_FoodStat(3, 0.5F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Burger_Veggie.set(addItem(tLastID = 221, "Veggieburger", "No matter how you call this, this is NOT a Burger!", new GT_FoodStat(3, 0.5F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Burger_Cheese.set(addItem(tLastID = 222, "Cheeseburger", "Cheesy!", new GT_FoodStat(3, 0.5F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new ItemData(Materials.Cheese, 907200L)));
        ItemList.Food_Burger_Meat.set(addItem(tLastID = 223, "Hamburger", "The Mc Burger Queen Burger", new GT_FoodStat(3, 0.5F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Burger_Chum.set(addItem(tLastID = 224, "Chumburger", "Fum is Chum!", new GT_FoodStat(5, 0.2F, EnumAction.eat, null, true, false, true, Potion.hunger.id, 1000, 4, 100, Potion.confusion.id, 300, 1, 80), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));

        ItemList.Food_Sliced_Breads.set(addItem(tLastID = 230, "Breads", "Pre Sliced", new GT_FoodStat(5, 0.6F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Sandwich_Veggie.set(addItem(tLastID = 231, "Veggie Sandwich", "Meatless", new GT_FoodStat(7, 0.6F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 32L);
        ItemList.Food_Sandwich_Cheese.set(addItem(tLastID = 232, "Cheese Sandwich", "Say Cheese!", new GT_FoodStat(7, 0.6F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 32L);
        ItemList.Food_Sandwich_Bacon.set(addItem(tLastID = 233, "Bacon Sandwich", "The best Sandwich ever!", new GT_FoodStat(10, 0.8F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 32L);
        ItemList.Food_Sandwich_Steak.set(addItem(tLastID = 234, "Steak Sandwich", "Not a 'Steam Sandwich'", new GT_FoodStat(10, 0.8F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 32L);

        ItemList.Food_Sliced_Baguettes.set(addItem(tLastID = 240, "Baguettes", "Pre Sliced", new GT_FoodStat(8, 0.5F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Large_Sandwich_Veggie.set(addItem(tLastID = 241, "Large Veggie Sandwich", "Just not worth it", new GT_FoodStat(15, 0.8F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Large_Sandwich_Cheese.set(addItem(tLastID = 242, "Large Cheese Sandwich", "I need another cheesy tooltip for this", new GT_FoodStat(15, 0.8F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Large_Sandwich_Bacon.set(addItem(tLastID = 243, "Large Bacon Sandwich", "For Men! (and manly Women)", new GT_FoodStat(20, 1.0F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Large_Sandwich_Steak.set(addItem(tLastID = 244, "Large Steak Sandwich", "Yes, I once accidentially called it 'Steam Sandwich'", new GT_FoodStat(20, 1.0F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);

        ItemList.Food_Raw_Pizza_Veggie.set(addItem(tLastID = 250, "Raw Veggie Pizza", "Into the Oven with it!", new GT_FoodStat(1, 0.2F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Raw_Pizza_Cheese.set(addItem(tLastID = 251, "Raw Cheese Pizza", "Into the Oven with it!", new GT_FoodStat(2, 0.2F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Raw_Pizza_Meat.set(addItem(tLastID = 252, "Raw Mince Meat Pizza", "Into the Oven with it!", new GT_FoodStat(2, 0.2F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));

        ItemList.Food_Baked_Pizza_Veggie.set(addItem(tLastID = 260, "Veggie Pizza", "The next they want is Gluten Free Pizzas...", new GT_FoodStat(3, 0.3F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Baked_Pizza_Cheese.set(addItem(tLastID = 261, "Cheese Pizza", "Pizza Magarita", new GT_FoodStat(4, 0.4F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Baked_Pizza_Meat.set(addItem(tLastID = 262, "Mince Meat Pizza", "Emo Pizza, it cuts itself!", new GT_FoodStat(5, 0.5F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));


        ItemList.Dye_Indigo.set(addItem(tLastID = 410, "Indigo Dye", "Blue Dye", new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 1L), Dyes.dyeBlue));
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            ItemList.DYE_ONLY_ITEMS[i].set(addItem(tLastID = 414 + i, Dyes.get(i).mName + " Dye", "", Dyes.get(i).name(), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 1L)));
        }
        ItemList.Plank_Oak.set(addItem(tLastID = 470, "Oak Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Spruce.set(addItem(tLastID = 471, "Spruce Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Birch.set(addItem(tLastID = 472, "Birch Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Jungle.set(addItem(tLastID = 473, "Jungle Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Acacia.set(addItem(tLastID = 474, "Acacia Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_DarkOak.set(addItem(tLastID = 475, "Dark Oak Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Larch.set(addItem(tLastID = 476, "Larch Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Teak.set(addItem(tLastID = 477, "Teak Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Acacia_Green.set(addItem(tLastID = 478, "Green Acacia Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Lime.set(addItem(tLastID = 479, "Lime Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Chestnut.set(addItem(tLastID = 480, "Chestnut Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Wenge.set(addItem(tLastID = 481, "Wenge Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Baobab.set(addItem(tLastID = 482, "Baobab Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Sequoia.set(addItem(tLastID = 483, "Sequoia Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Kapok.set(addItem(tLastID = 484, "Kapok Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Ebony.set(addItem(tLastID = 485, "Ebony Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Mahagony.set(addItem(tLastID = 486, "Mahagony Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Balsa.set(addItem(tLastID = 487, "Balsa Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Willow.set(addItem(tLastID = 488, "Willow Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Walnut.set(addItem(tLastID = 489, "Walnut Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Greenheart.set(addItem(tLastID = 490, "Greenheart Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Cherry.set(addItem(tLastID = 491, "Cherry Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Mahoe.set(addItem(tLastID = 492, "Mahoe Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Poplar.set(addItem(tLastID = 493, "Poplar Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Palm.set(addItem(tLastID = 494, "Palm Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Papaya.set(addItem(tLastID = 495, "Papaya Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Pine.set(addItem(tLastID = 496, "Pine Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Plum.set(addItem(tLastID = 497, "Plum Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Maple.set(addItem(tLastID = 498, "Maple Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);
        ItemList.Plank_Citrus.set(addItem(tLastID = 499, "Citrus Plank", aTextCover, new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 75);

        ItemList.SFMixture.set(addItem(tLastID = 270, "Super Fuel Binder", "Raw Material"));
        ItemList.MSFMixture.set(addItem(tLastID = 271, "Magic Super Fuel Binder", "Raw Material"));
        
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Oak.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", 'P', new ItemStack(Blocks.wooden_slab, 1, 0)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Spruce.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", 'P', new ItemStack(Blocks.wooden_slab, 1, 1)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Birch.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", 'P', new ItemStack(Blocks.wooden_slab, 1, 2)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Jungle.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", 'P', new ItemStack(Blocks.wooden_slab, 1, 3)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Acacia.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", 'P', new ItemStack(Blocks.wooden_slab, 1, 4)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_DarkOak.get(2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", 'P', new ItemStack(Blocks.wooden_slab, 1, 5)});

        GregTech_API.registerCover(ItemList.Plank_Oak.get(1L), new GT_CopiedBlockTexture(Blocks.planks, 0, 0), null);
        GregTech_API.registerCover(ItemList.Plank_Spruce.get(1L), new GT_CopiedBlockTexture(Blocks.planks, 0, 1), null);
        GregTech_API.registerCover(ItemList.Plank_Birch.get(1L), new GT_CopiedBlockTexture(Blocks.planks, 0, 2), null);
        GregTech_API.registerCover(ItemList.Plank_Jungle.get(1L), new GT_CopiedBlockTexture(Blocks.planks, 0, 3), null);
        GregTech_API.registerCover(ItemList.Plank_Acacia.get(1L), new GT_CopiedBlockTexture(Blocks.planks, 0, 4), null);
        GregTech_API.registerCover(ItemList.Plank_DarkOak.get(1L), new GT_CopiedBlockTexture(Blocks.planks, 0, 5), null);
        GregTech_API.registerCover(ItemList.Plank_Larch.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 0, new ItemStack(Blocks.planks, 1, 0))), 0, 0), null);
        GregTech_API.registerCover(ItemList.Plank_Teak.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 1, new ItemStack(Blocks.planks, 1, 0))), 0, 1), null);
        GregTech_API.registerCover(ItemList.Plank_Acacia_Green.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 2, new ItemStack(Blocks.planks, 1, 0))), 0, 2), null);
        GregTech_API.registerCover(ItemList.Plank_Lime.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 3, new ItemStack(Blocks.planks, 1, 0))), 0, 3), null);
        GregTech_API.registerCover(ItemList.Plank_Chestnut.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 4, new ItemStack(Blocks.planks, 1, 0))), 0, 4), null);
        GregTech_API.registerCover(ItemList.Plank_Wenge.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 5, new ItemStack(Blocks.planks, 1, 0))), 0, 5), null);
        GregTech_API.registerCover(ItemList.Plank_Baobab.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 6, new ItemStack(Blocks.planks, 1, 0))), 0, 6), null);
        GregTech_API.registerCover(ItemList.Plank_Sequoia.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 7, new ItemStack(Blocks.planks, 1, 0))), 0, 7), null);
        GregTech_API.registerCover(ItemList.Plank_Kapok.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 8, new ItemStack(Blocks.planks, 1, 0))), 0, 8), null);
        GregTech_API.registerCover(ItemList.Plank_Ebony.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 9, new ItemStack(Blocks.planks, 1, 0))), 0, 9), null);
        GregTech_API.registerCover(ItemList.Plank_Mahagony.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 10, new ItemStack(Blocks.planks, 1, 0))), 0, 10), null);
        GregTech_API.registerCover(ItemList.Plank_Balsa.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 11, new ItemStack(Blocks.planks, 1, 0))), 0, 11), null);
        GregTech_API.registerCover(ItemList.Plank_Willow.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 12, new ItemStack(Blocks.planks, 1, 0))), 0, 12), null);
        GregTech_API.registerCover(ItemList.Plank_Walnut.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 13, new ItemStack(Blocks.planks, 1, 0))), 0, 13), null);
        GregTech_API.registerCover(ItemList.Plank_Greenheart.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 14, new ItemStack(Blocks.planks, 1, 0))), 0, 14), null);
        GregTech_API.registerCover(ItemList.Plank_Cherry.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 15, new ItemStack(Blocks.planks, 1, 0))), 0, 15), null);
        GregTech_API.registerCover(ItemList.Plank_Mahoe.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 16, new ItemStack(Blocks.planks, 1, 0))), 0, 0), null);
        GregTech_API.registerCover(ItemList.Plank_Poplar.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 17, new ItemStack(Blocks.planks, 1, 0))), 0, 1), null);
        GregTech_API.registerCover(ItemList.Plank_Palm.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 18, new ItemStack(Blocks.planks, 1, 0))), 0, 2), null);
        GregTech_API.registerCover(ItemList.Plank_Papaya.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 19, new ItemStack(Blocks.planks, 1, 0))), 0, 3), null);
        GregTech_API.registerCover(ItemList.Plank_Pine.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 20, new ItemStack(Blocks.planks, 1, 0))), 0, 4), null);
        GregTech_API.registerCover(ItemList.Plank_Plum.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 21, new ItemStack(Blocks.planks, 1, 0))), 0, 5), null);
        GregTech_API.registerCover(ItemList.Plank_Maple.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 22, new ItemStack(Blocks.planks, 1, 0))), 0, 6), null);
        GregTech_API.registerCover(ItemList.Plank_Citrus.get(1L), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 23, new ItemStack(Blocks.planks, 1, 0))), 0, 7), null);

        ItemList.Crop_Drop_Plumbilia.set(addItem(tLastID = 500, "Plumbilia Leaf", "Source of Lead", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 1L)));
        ItemList.Crop_Drop_Argentia.set(addItem(tLastID = 501, "Argentia Leaf", "Source of Silver", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)));
        ItemList.Crop_Drop_Indigo.set(addItem(tLastID = 502, "Indigo Blossom", "Used for making Blue Dye", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 1L)));
        ItemList.Crop_Drop_Ferru.set(addItem(tLastID = 503, "Ferru Leaf", "Source of Iron", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L)));
        ItemList.Crop_Drop_Aurelia.set(addItem(tLastID = 504, "Aurelia Leaf", "Source of Gold", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)));
        ItemList.Crop_Drop_TeaLeaf.set(addItem(tLastID = 505, "Tea Leaf", "Source of Tea", "cropTea", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)));

        ItemList.Crop_Drop_OilBerry.set(addItem(tLastID = 510, "Oil Berry", "Oil in Berry form", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L)));
        ItemList.Crop_Drop_BobsYerUncleRanks.set(addItem(tLastID = 511, "Bobs-Yer-Uncle-Berry", "Source of Emeralds", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)));
        ItemList.Crop_Drop_UUMBerry.set(addItem(tLastID = 512, "UUM Berry", "UUM in Berry form", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L)));
        ItemList.Crop_Drop_UUABerry.set(addItem(tLastID = 513, "UUA Berry", "UUA in Berry form", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L)));

        ItemList.Crop_Drop_MilkWart.set(addItem(tLastID = 520, "Milk Wart", "Source of Milk", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)));

        ItemList.Crop_Drop_Coppon.set(addItem(tLastID = 530, "Coppon Fiber", "ORANGE WOOOOOOOL!!!", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 1L)));

        ItemList.Crop_Drop_Tine.set(addItem(tLastID = 540, "Tine Twig", "Source of Tin", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)));
        setBurnValue(32000 + tLastID, 100);

        ItemList.Crop_Drop_Bauxite.set(addItem(tLastID = 521, "Bauxia Leaf", "Source of Aluminium"));
        ItemList.Crop_Drop_Ilmenite.set(addItem(tLastID = 522, "Titania Leaf", "Source of Titanium"));
        ItemList.Crop_Drop_Pitchblende.set(addItem(tLastID = 523, "Reactoria Leaf", "Source of Uranium"));
        ItemList.Crop_Drop_Uraninite.set(addItem(tLastID = 524, "Uranium Leaf", "Source of Uranite"));
        ItemList.Crop_Drop_Thorium.set(addItem(tLastID = 526, "Thunder Leaf", "Source of Thorium"));
        ItemList.Crop_Drop_Nickel.set(addItem(tLastID = 527, "Nickelback Leaf", "Source of Nickel"));
        ItemList.Crop_Drop_Zinc.set(addItem(tLastID = 528, "Galvania Leaf", "Source of Zinc"));
        ItemList.Crop_Drop_Manganese.set(addItem(tLastID = 529, "Pyrolusium Leaf", "Source of Manganese"));
        ItemList.Crop_Drop_Scheelite.set(addItem(tLastID = 531, "Scheelinium Leaf", "Source of Tungsten"));
        ItemList.Crop_Drop_Platinum.set(addItem(tLastID = 532, "Platina Leaf", "Source of Platinum"));
        ItemList.Crop_Drop_Iridium.set(addItem(tLastID = 533, "Quantaria Leaf", "Source of Iridium"));
        ItemList.Crop_Drop_Osmium.set(addItem(tLastID = 534, "Quantaria Leaf", "Source of Osmium"));
        ItemList.Crop_Drop_Naquadah.set(addItem(tLastID = 535, "Stargatium Leaf", "Source of Naquadah"));
        
        ItemList.Crop_Drop_Chilly.set(addItem(tLastID = 550, "Chilly Pepper", "It is red and hot", "cropChilipepper", new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false, Potion.confusion.id, 200, 1, 40), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Crop_Drop_Lemon.set(addItem(tLastID = 551, "Lemon", "Don't make Lemonade", "cropLemon", new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Crop_Drop_Tomato.set(addItem(tLastID = 552, "Tomato", "Solid Ketchup", "cropTomato", new GT_FoodStat(1, 0.2F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Crop_Drop_MTomato.set(addItem(tLastID = 553, "Max Tomato", "Full Health in one Tomato", "cropTomato", new GT_FoodStat(9, 1.0F, EnumAction.eat, null, false, true, false, Potion.regeneration.id, 100, 100, 100), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Crop_Drop_Grapes.set(addItem(tLastID = 554, "Grapes", "Source of Wine", "cropGrape", new GT_FoodStat(2, 0.3F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Crop_Drop_Onion.set(addItem(tLastID = 555, "Onion", "Taking over the whole Taste", "cropOnion", new GT_FoodStat(2, 0.2F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Crop_Drop_Cucumber.set(addItem(tLastID = 556, "Cucumber", "Not a Sea Cucumber!", "cropCucumber", new GT_FoodStat(1, 0.2F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Crop_Drop_Rape.set(addItem(tLastID = 557, "Rape", "Time to oil up!", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L)));

        ItemList.Food_Cheese.set(addItem(tLastID = 558, "Cheese", "Click the Cheese", "foodCheese", new GT_FoodStat(3, 0.6F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 2L)));
        ItemList.Food_Dough.set(addItem(tLastID = 559, "Dough", "For making Breads", "foodDough", new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Food_Flat_Dough.set(addItem(tLastID = 560, "Flattened Dough", "For making Pizza", new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Food_Raw_Bread.set(addItem(tLastID = 561, "Dough", "In Bread Shape", new GT_FoodStat(1, 0.2F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Food_Raw_Bun.set(addItem(tLastID = 562, "Dough", "In Bun Shape", new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Food_Raw_Baguette.set(addItem(tLastID = 563, "Dough", "In Baguette Shape", new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Food_Baked_Bun.set(addItem(tLastID = 564, "Bun", "Do not teleport Bread!", new GT_FoodStat(3, 0.5F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Baked_Baguette.set(addItem(tLastID = 565, "Baguette", "I teleported nothing BUT Bread!!!", new GT_FoodStat(8, 0.5F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Sliced_Bread.set(addItem(tLastID = 566, "Sliced Bread", "Just half a Bread", new GT_FoodStat(2, 0.3F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Sliced_Bun.set(addItem(tLastID = 567, "Sliced Bun", "Just half a Bun", new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Sliced_Baguette.set(addItem(tLastID = 568, "Sliced Baguette", "Just half a Baguette", new GT_FoodStat(4, 0.3F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)));
        ItemList.Food_Raw_Cake.set(addItem(tLastID = 569, "Cake Bottom", "For making Cake", new GT_FoodStat(2, 0.2F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Food_Baked_Cake.set(addItem(tLastID = 570, "Baked Cake Bottom", "I know I promised you an actual Cake, but well...", new GT_FoodStat(3, 0.3F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));
        ItemList.Food_Sliced_Lemon.set(addItem(tLastID = 571, "Lemon Slice", "Ideal to put on your Drink", new GT_FoodStat(1, 0.075F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)));
        ItemList.Food_Sliced_Tomato.set(addItem(tLastID = 572, "Tomato Slice", "Solid Ketchup", new GT_FoodStat(1, 0.05F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)));
        ItemList.Food_Sliced_Onion.set(addItem(tLastID = 573, "Onion Slice", "ONIONS, UNITE!", new GT_FoodStat(1, 0.05F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)));
        ItemList.Food_Sliced_Cucumber.set(addItem(tLastID = 574, "Cucumber Slice", "QUEWWW-CUMMM-BERRR!!!", new GT_FoodStat(1, 0.05F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)));

        ItemList.Food_Sliced_Cheese.set(addItem(tLastID = 576, "Cheese Slice", "ALIEN ATTACK!!!, throw the CHEEEEESE!!!", new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)));

        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 0), new ItemStack(Items.dye, 2, 1));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 1), new ItemStack(Items.dye, 2, 12));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 2), new ItemStack(Items.dye, 2, 13));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 3), new ItemStack(Items.dye, 2, 7));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 4), new ItemStack(Items.dye, 2, 1));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 5), new ItemStack(Items.dye, 2, 14));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 6), new ItemStack(Items.dye, 2, 7));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 7), new ItemStack(Items.dye, 2, 9));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 8), new ItemStack(Items.dye, 2, 7));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.yellow_flower, 1, 0), new ItemStack(Items.dye, 2, 11));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.double_plant, 1, 0), new ItemStack(Items.dye, 3, 11));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.double_plant, 1, 1), new ItemStack(Items.dye, 3, 13));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.double_plant, 1, 4), new ItemStack(Items.dye, 3, 1));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.double_plant, 1, 5), new ItemStack(Items.dye, 3, 9));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Plumbilia.get(1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Lead, 1L));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Argentia.get(1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Silver, 1L));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Indigo.get(1L), ItemList.Dye_Indigo.get(1L));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_MilkWart.get(1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Milk, 1L));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Coppon.get(1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Copper, 1L));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Tine.get(1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Tin, 1L));

        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Coppon.get(4L), new ItemStack(Blocks.wool, 1, 1));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Plumbilia.get(8L), ItemList.IC2_PlantballCompressed.get(1L));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Argentia.get(8L), ItemList.IC2_PlantballCompressed.get(1L));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Indigo.get(8L), ItemList.IC2_PlantballCompressed.get(1L));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Ferru.get(8L), ItemList.IC2_PlantballCompressed.get(1L));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Aurelia.get(8L), ItemList.IC2_PlantballCompressed.get(1L));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_OilBerry.get(8L), ItemList.IC2_PlantballCompressed.get(1L));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_BobsYerUncleRanks.get(8L), ItemList.IC2_PlantballCompressed.get(1L));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Tine.get(4L), ItemList.IC2_PlantballCompressed.get(1L));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Rape.get(8L), ItemList.IC2_PlantballCompressed.get(1L));
        GT_ModHandler.addCompressionRecipe(new ItemStack(Blocks.red_flower, 8, 32767), ItemList.IC2_PlantballCompressed.get(1L));
        GT_ModHandler.addCompressionRecipe(new ItemStack(Blocks.yellow_flower, 8, 32767), ItemList.IC2_PlantballCompressed.get(1L));

        GT_ModHandler.addPulverisationRecipe(ItemList.Food_Sliced_Cheese.get(1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Cheese, 1L));
        GT_ModHandler.addPulverisationRecipe(ItemList.Dye_Cocoa.get(1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cocoa, 1L));
        GT_ModHandler.addPulverisationRecipe(ItemList.Crop_Drop_Tine.get(1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Wood, 2L));
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.reeds, 1), new ItemStack(Items.sugar, 1), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.melon_block, 1, 0), new ItemStack(Items.melon, 8, 0), new ItemStack(Items.melon_seeds, 1), 80, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.pumpkin, 1, 0), new ItemStack(Items.pumpkin_seeds, 4, 0), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.melon, 1, 0), new ItemStack(Items.melon_seeds, 1, 0), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.wheat, 1, 0), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getIC2Item("crop", 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.stick, 1), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Wood, 2L), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.wool, 1, 32767), new ItemStack(Items.string, 2), new ItemStack(Items.string, 1), 50, false);
        try {
            Object tCrop;
            GT_Utility.getField(tCrop = ic2.api.crops.Crops.instance.getCropList()[13], "mDrop").set(tCrop, ItemList.Crop_Drop_Ferru.get(1L));
            GT_Utility.getField(tCrop = ic2.api.crops.Crops.instance.getCropList()[14], "mDrop").set(tCrop, ItemList.Crop_Drop_Aurelia.get(1L));
        } catch (Throwable e) {
            if (GT_Values.D1) {
                e.printStackTrace(GT_Log.err);
            }
        }
        ItemList.Display_ITS_FREE.set(addItem(tLastID = 766, "ITS FREE", "(or at least almost free)", SubTag.INVISIBLE, new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)));
    }

    public boolean onLeftClickEntity(ItemStack aStack, EntityPlayer aPlayer, Entity aEntity) {
        super.onLeftClickEntity(aStack, aPlayer, aEntity);
        int aDamage = aStack.getItemDamage();
        if ((aDamage >= 25000) && (aDamage < 27000)) {
            if (aDamage >= 26000) {
                return Behaviour_Arrow.DEFAULT_PLASTIC.onLeftClickEntity(this, aStack, aPlayer, aEntity);
            }
            return Behaviour_Arrow.DEFAULT_WOODEN.onLeftClickEntity(this, aStack, aPlayer, aEntity);
        }
        return false;
    }

    public boolean hasProjectile(SubTag aProjectileType, ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        return ((aDamage >= 25000) && (aDamage < 27000)) || (super.hasProjectile(aProjectileType, aStack));
    }

    public EntityArrow getProjectile(SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ) {
        int aDamage = aStack.getItemDamage();
        if ((aDamage >= 25000) && (aDamage < 27000)) {
            if (aDamage >= 26000) {
                return Behaviour_Arrow.DEFAULT_PLASTIC.getProjectile(this, aProjectileType, aStack, aWorld, aX, aY, aZ);
            }
            return Behaviour_Arrow.DEFAULT_WOODEN.getProjectile(this, aProjectileType, aStack, aWorld, aX, aY, aZ);
        }
        return super.getProjectile(aProjectileType, aStack, aWorld, aX, aY, aZ);
    }

    public EntityArrow getProjectile(SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed) {
        int aDamage = aStack.getItemDamage();
        if ((aDamage >= 25000) && (aDamage < 27000)) {
            if (aDamage >= 26000) {
                return Behaviour_Arrow.DEFAULT_PLASTIC.getProjectile(this, aProjectileType, aStack, aWorld, aEntity, aSpeed);
            }
            return Behaviour_Arrow.DEFAULT_WOODEN.getProjectile(this, aProjectileType, aStack, aWorld, aEntity, aSpeed);
        }
        return super.getProjectile(aProjectileType, aStack, aWorld, aEntity, aSpeed);
    }

    public boolean isItemStackUsable(ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        Materials aMaterial = GregTech_API.sGeneratedMaterials[(aDamage % 1000)];
        if ((aDamage >= 25000) && (aDamage < 27000) && (aMaterial != null) && (aMaterial.mEnchantmentTools != null)) {
            Enchantment tEnchant = aMaterial.mEnchantmentTools == Enchantment.fortune ? Enchantment.looting : aMaterial.mEnchantmentTools;
            if (tEnchant.type == EnumEnchantmentType.weapon) {
                NBTTagCompound tNBT = GT_Utility.ItemNBT.getNBT(aStack);
                if (!tNBT.getBoolean("GT.HasBeenUpdated")) {
                    tNBT.setBoolean("GT.HasBeenUpdated", true);
                    GT_Utility.ItemNBT.setNBT(aStack, tNBT);
                    GT_Utility.ItemNBT.addEnchantment(aStack, tEnchant, aMaterial.mEnchantmentToolsLevel);
                }
            }
        }
        return super.isItemStackUsable(aStack);
    }

    public boolean doesShowInCreative(OrePrefixes aPrefix, Materials aMaterial, boolean aDoShowAllItems) {
        return (aDoShowAllItems) || (!aPrefix.name().startsWith("toolHead"));
    }

    public ItemStack onDispense(IBlockSource aSource, ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        if ((aDamage >= 25000) && (aDamage < 27000)) {
            if (aDamage >= 26000) {
                return Behaviour_Arrow.DEFAULT_PLASTIC.onDispense(this, aSource, aStack);
            }
            return Behaviour_Arrow.DEFAULT_WOODEN.onDispense(this, aSource, aStack);
        }
        return super.onDispense(aSource, aStack);
    }

    public final ItemStack getContainerItem(ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        if (aDamage < 32000) {
            return null;
        }
        if (aDamage < 32100) {
            return ItemList.ThermosCan_Empty.get(1L);
        }
        if (aDamage < 32200) {
            return ItemList.Bottle_Empty.get(1L);
        }
        return null;
    }
}
