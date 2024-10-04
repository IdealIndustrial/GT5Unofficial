

# Import
	import mods.gregtech.ChemicalBath;
	import minetweaker.data.IData;
	import minetweaker.item.IItemStack;
	
# Variable
	var FluixGlassCable = <appliedenergistics2:item.ItemMultiPart:16>;
	var FluixCoveredC = <appliedenergistics2:item.ItemMultiPart:36>;
	var FluixSmartCable = <appliedenergistics2:item.ItemMultiPart:56>;
	var FluixDenseCable = <appliedenergistics2:item.ItemMultiPart:76>;
	
	var FluixCoveredCBlack = <appliedenergistics2:item.ItemMultiPart:35>;
	var FluixCoveredCBlue = <appliedenergistics2:item.ItemMultiPart:31>;
	var FluixCoveredCBrown = <appliedenergistics2:item.ItemMultiPart:32>;
	var FluixCoveredCCyan = <appliedenergistics2:item.ItemMultiPart:29>;
	var FluixCoveredCGray = <appliedenergistics2:item.ItemMultiPart:27>;
	var FluixCoveredCGreen = <appliedenergistics2:item.ItemMultiPart:33>;
	var FluixCoveredCLightBlue = <appliedenergistics2:item.ItemMultiPart:23>;
	var FluixCoveredCLightGray = <appliedenergistics2:item.ItemMultiPart:28>;
	var FluixCoveredCLime = <appliedenergistics2:item.ItemMultiPart:25>;
	var FluixCoveredCMagenta = <appliedenergistics2:item.ItemMultiPart:22>;
	var FluixCoveredCOrange = <appliedenergistics2:item.ItemMultiPart:21>;
	var FluixCoveredCPink = <appliedenergistics2:item.ItemMultiPart:26>;
	var FluixCoveredCPurple = <appliedenergistics2:item.ItemMultiPart:30>;
	var FluixCoveredCRed = <appliedenergistics2:item.ItemMultiPart:34>;
	var FluixCoveredCWhite = <appliedenergistics2:item.ItemMultiPart:20>;
	var FluixCoveredCYellow = <appliedenergistics2:item.ItemMultiPart:24>;
	
	var FluixCovered = [FluixCoveredCBlack, FluixCoveredCBlue, FluixCoveredCBrown, FluixCoveredCCyan, FluixCoveredCGray, FluixCoveredCGreen, FluixCoveredCLightBlue, FluixCoveredCLightGray, FluixCoveredCLime, FluixCoveredCMagenta, FluixCoveredCOrange, FluixCoveredCPink, FluixCoveredCPurple, FluixCoveredCRed, FluixCoveredCWhite, FluixCoveredCYellow] as IItemStack[];
	
	var FluixDenseCableBlack = <appliedenergistics2:item.ItemMultiPart:75>;
	var FluixDenseCableBlue = <appliedenergistics2:item.ItemMultiPart:71>;
	var FluixDenseCableBrown = <appliedenergistics2:item.ItemMultiPart:72>;
	var FluixDenseCableCyan = <appliedenergistics2:item.ItemMultiPart:69>;
	var FluixDenseCableGray = <appliedenergistics2:item.ItemMultiPart:67>;
	var FluixDenseCableGreen = <appliedenergistics2:item.ItemMultiPart:73>;
	var FluixDenseCableLightBlue = <appliedenergistics2:item.ItemMultiPart:63>;
	var FluixDenseCableLightGray = <appliedenergistics2:item.ItemMultiPart:68>;
	var FluixDenseCableLime = <appliedenergistics2:item.ItemMultiPart:65>;
	var FluixDenseCableMagenta = <appliedenergistics2:item.ItemMultiPart:62>;
	var FluixDenseCableOrange = <appliedenergistics2:item.ItemMultiPart:61>;
	var FluixDenseCablePink = <appliedenergistics2:item.ItemMultiPart:66>;
	var FluixDenseCablePurple = <appliedenergistics2:item.ItemMultiPart:70>;
	var FluixDenseCableRed = <appliedenergistics2:item.ItemMultiPart:74>;
	var FluixDenseCableWhite = <appliedenergistics2:item.ItemMultiPart:60>;
	var FluixDenseCableYellow = <appliedenergistics2:item.ItemMultiPart:64>;
	
	var FluixDense = [FluixDenseCableBlack, FluixDenseCableBlue, FluixDenseCableBrown, FluixDenseCableCyan, FluixDenseCableGray, FluixDenseCableGreen, FluixDenseCableLightBlue, FluixDenseCableLightGray, FluixDenseCableLime, FluixDenseCableMagenta, FluixDenseCableOrange, FluixDenseCablePink, FluixDenseCablePurple, FluixDenseCableRed, FluixDenseCableWhite, FluixDenseCableYellow] as IItemStack[];
	
	var FluixGlassCableBlack = <appliedenergistics2:item.ItemMultiPart:15>;
	var FluixGlassCableBlue = <appliedenergistics2:item.ItemMultiPart:11>;
	var FluixGlassCableBrown = <appliedenergistics2:item.ItemMultiPart:12>;
	var FluixGlassCableCyan = <appliedenergistics2:item.ItemMultiPart:9>;
	var FluixGlassCableGray = <appliedenergistics2:item.ItemMultiPart:7>;
	var FluixGlassCableGreen = <appliedenergistics2:item.ItemMultiPart:13>;
	var FluixGlassCableLightBlue = <appliedenergistics2:item.ItemMultiPart:3>;
	var FluixGlassCableLightGray = <appliedenergistics2:item.ItemMultiPart:8>;
	var FluixGlassCableLime = <appliedenergistics2:item.ItemMultiPart:5>;
	var FluixGlassCableMagenta = <appliedenergistics2:item.ItemMultiPart:2>;
	var FluixGlassCableOrange = <appliedenergistics2:item.ItemMultiPart:1>;
	var FluixGlassCablePink = <appliedenergistics2:item.ItemMultiPart:6>;
	var FluixGlassCablePurple = <appliedenergistics2:item.ItemMultiPart:10>;
	var FluixGlassCableRed = <appliedenergistics2:item.ItemMultiPart:14>;
	var FluixGlassCableWhite = <appliedenergistics2:item.ItemMultiPart>;
	var FluixGlassCableYellow = <appliedenergistics2:item.ItemMultiPart:4>;
	
	var FluixGlass = [FluixGlassCableBlack, FluixGlassCableBlue, FluixGlassCableBrown, FluixGlassCableCyan, FluixGlassCableGray, FluixGlassCableGreen, FluixGlassCableLightBlue, FluixGlassCableLightGray, FluixGlassCableLime, FluixGlassCableMagenta, FluixGlassCableOrange, FluixGlassCablePink, FluixGlassCablePurple, FluixGlassCableRed, FluixGlassCableWhite, FluixGlassCableYellow] as IItemStack[];
	
	var FluixSmartCableBlack = <appliedenergistics2:item.ItemMultiPart:55>;
	var FluixSmartCableBlue = <appliedenergistics2:item.ItemMultiPart:51>;
	var FluixSmartCableBrown = <appliedenergistics2:item.ItemMultiPart:52>;
	var FluixSmartCableCyan = <appliedenergistics2:item.ItemMultiPart:49>;
	var FluixSmartCableGray = <appliedenergistics2:item.ItemMultiPart:47>;
	var FluixSmartCableGreen = <appliedenergistics2:item.ItemMultiPart:53>;
	var FluixSmartCableLightBlue = <appliedenergistics2:item.ItemMultiPart:43>;
	var FluixSmartCableLightGray = <appliedenergistics2:item.ItemMultiPart:48>;
	var FluixSmartCableLime = <appliedenergistics2:item.ItemMultiPart:45>;
	var FluixSmartCableMagenta = <appliedenergistics2:item.ItemMultiPart:42>;
	var FluixSmartCableOrange = <appliedenergistics2:item.ItemMultiPart:41>;
	var FluixSmartCablePink = <appliedenergistics2:item.ItemMultiPart:46>;
	var FluixSmartCablePurple = <appliedenergistics2:item.ItemMultiPart:50>;
	var FluixSmartCableRed = <appliedenergistics2:item.ItemMultiPart:54>;
	var FluixSmartCableWhite = <appliedenergistics2:item.ItemMultiPart:40>;
	var FluixSmartCableYellow = <appliedenergistics2:item.ItemMultiPart:44>;
	
	var FluixSmart = [FluixSmartCableBlack, FluixSmartCableBlue, FluixSmartCableBrown, FluixSmartCableCyan, FluixSmartCableGray, FluixSmartCableGreen, FluixSmartCableLightBlue, FluixSmartCableLightGray, FluixSmartCableLime, FluixSmartCableMagenta, FluixSmartCableOrange, FluixSmartCablePink, FluixSmartCablePurple, FluixSmartCableRed, FluixSmartCableWhite, FluixSmartCableYellow] as IItemStack[]; 
	
	var LiquidBlack = <liquid:dye.watermixed.dyeblack>;
	var LiquidBlue = <liquid:dye.watermixed.dyeblue>;
	var LiquidBrown = <liquid:dye.watermixed.dyebrown>;
	var LiquidCyan = <liquid:dye.watermixed.dyecyan>;
	var LiquidGray = <liquid:dye.watermixed.dyegray>;
	var LiquidGreen = <liquid:dye.watermixed.dyegreen>;
	var LiquidLightBlue = <liquid:dye.watermixed.dyelightblue>;
	var LiquidLightGray = <liquid:dye.watermixed.dyelightgray>;
	var LiquidLime = <liquid:dye.watermixed.dyelime>;
	var LiquidMagenta =	<liquid:dye.watermixed.dyemagenta>;
	var LiquidOrange = <liquid:dye.watermixed.dyeorange>;
	var LiquidPink = <liquid:dye.watermixed.dyepink>;
	var LiquidPurple = <liquid:dye.watermixed.dyepurple>;
	var LiquidRed = <liquid:dye.watermixed.dyered>;
	var LiquidWhite = <liquid:dye.watermixed.dyewhite>;
	var LiquidYellow = <liquid:dye.watermixed.dyeyellow>;

	var Data = [{RecipeData: 0}, {RecipeData: 1}, {RecipeData: 2}, {RecipeData: 3}, {RecipeData: 4}, {RecipeData: 5}, {RecipeData: 6}, {RecipeData: 7}, {RecipeData: 8}, {RecipeData: 9}, {RecipeData: 10}, {RecipeData: 11}, {RecipeData: 12}, {RecipeData: 13}, {RecipeData: 14}, {RecipeData: 15},] as IData[];
	
	var Chlorine = <liquid:chlorine>;
	
# Removing recipes
	# Removing crafting coloured cables
		for i, RecipeData in Data {
			recipes.remove (FluixCovered[i]);
		}
		for i, RecipeData in Data {
			recipes.remove (FluixDense[i]);
		}
		for i, RecipeData in Data {
			recipes.remove (FluixGlass[i]);
		}
		for i, RecipeData in Data {
			recipes.remove (FluixSmart[i]);
		}

# Adding recipes
	# Coloured cables
		//ChemicalBath.addRecipe([OutputArray], InputStack, InputFluid, [OutputArrayChances], Time in Ticks, EnergyUsage);
		# FluixCovered
			ChemicalBath.addRecipe ([FluixCoveredCBlack], FluixCoveredC, LiquidBlack * 144, [10000], 2, 2);
			ChemicalBath.addRecipe ([FluixCoveredCBlue], FluixCoveredC, LiquidBlue * 144, [10000], 2, 2);
			ChemicalBath.addRecipe ([FluixCoveredCBrown], FluixCoveredC, LiquidBrown * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCCyan], FluixCoveredC, LiquidCyan * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCGray], FluixCoveredC, LiquidGray * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCGreen], FluixCoveredC, LiquidGreen * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCLightBlue], FluixCoveredC, LiquidLightBlue * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCLightGray], FluixCoveredC, LiquidLightGray * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCLime], FluixCoveredC, LiquidLime * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCMagenta], FluixCoveredC, LiquidMagenta * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCOrange], FluixCoveredC, LiquidOrange * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCPink], FluixCoveredC, LiquidPink * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCPurple], FluixCoveredC, LiquidPurple * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCRed], FluixCoveredC, LiquidRed * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCWhite], FluixCoveredC, LiquidWhite * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixCoveredCYellow], FluixCoveredC, LiquidYellow * 144, [10000], 2, 2);
		# FluixDense
			ChemicalBath.addRecipe ([FluixDenseCableBlack], FluixDenseCable, LiquidBlack * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableBlue], FluixDenseCable, LiquidBlue * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableBrown], FluixDenseCable, LiquidBrown * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableCyan], FluixDenseCable, LiquidCyan * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableGray], FluixDenseCable, LiquidGray * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableGreen], FluixDenseCable, LiquidGreen * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableLightBlue], FluixDenseCable, LiquidLightBlue * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableLightGray], FluixDenseCable, LiquidLightGray * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableLime], FluixDenseCable, LiquidLime * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableMagenta], FluixDenseCable, LiquidMagenta * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableOrange], FluixDenseCable, LiquidOrange * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCablePink], FluixDenseCable, LiquidPink * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCablePurple], FluixDenseCable, LiquidPurple * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableRed], FluixDenseCable, LiquidRed * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableWhite], FluixDenseCable, LiquidWhite * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixDenseCableYellow], FluixDenseCable, LiquidYellow * 144, [10000], 2, 2);
		# FluixGlass
			ChemicalBath.addRecipe ([FluixGlassCableBlack], FluixGlassCable, LiquidBlack * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableBlue], FluixGlassCable, LiquidBlue * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableBrown], FluixGlassCable, LiquidBrown * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableCyan], FluixGlassCable, LiquidCyan * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableGray], FluixGlassCable, LiquidGray * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableGreen], FluixGlassCable, LiquidGreen * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableLightBlue], FluixGlassCable, LiquidLightBlue * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableLightGray], FluixGlassCable, LiquidLightGray * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableLime], FluixGlassCable, LiquidLime * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableMagenta], FluixGlassCable, LiquidMagenta * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableOrange], FluixGlassCable, LiquidOrange * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCablePink], FluixGlassCable, LiquidPink * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCablePurple], FluixGlassCable, LiquidPurple * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableRed], FluixGlassCable, LiquidRed * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableWhite], FluixGlassCable, LiquidWhite * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixGlassCableYellow], FluixGlassCable, LiquidYellow * 144, [10000], 2, 2);
		# FluixSmart
			ChemicalBath.addRecipe ([FluixSmartCableBlack], FluixSmartCable, LiquidBlack * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableBlue], FluixSmartCable, LiquidBlue * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableBrown], FluixSmartCable, LiquidBrown * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableCyan], FluixSmartCable, LiquidCyan * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableGray], FluixSmartCable, LiquidGray * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableGreen], FluixSmartCable, LiquidGreen * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableLightBlue], FluixSmartCable, LiquidLightBlue * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableLightGray], FluixSmartCable, LiquidLightGray * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableLime], FluixSmartCable, LiquidLime * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableMagenta], FluixSmartCable, LiquidMagenta * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableOrange], FluixSmartCable, LiquidOrange * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCablePink], FluixSmartCable, LiquidPink * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCablePurple], FluixSmartCable, LiquidPurple * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableRed], FluixSmartCable, LiquidRed * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableWhite], FluixSmartCable, LiquidWhite * 144, [10000], 2, 2);
            ChemicalBath.addRecipe ([FluixSmartCableYellow], FluixSmartCable, LiquidYellow * 144, [10000], 2, 2);
			
	# Cleaning cables
		# FluixCovered
			for i, RecipeData in Data {
				ChemicalBath.addRecipe ([FluixCoveredC], FluixCovered[i], Chlorine * 50, [10000], 20, 2);
			}
		# FluixDense
			for i, RecipeData in Data {
				ChemicalBath.addRecipe ([FluixDenseCable], FluixDense[i], Chlorine * 50, [10000], 20, 2);
			}
		# FluixGlass
			for i, RecipeData in Data {
				ChemicalBath.addRecipe ([FluixGlassCable], FluixGlass[i], Chlorine * 50, [10000], 20, 2);
			}
		# FluixSmart
			for i, RecipeData in Data {
				ChemicalBath.addRecipe ([FluixSmartCable], FluixSmart[i], Chlorine * 50, [10000], 20, 2);
			}
