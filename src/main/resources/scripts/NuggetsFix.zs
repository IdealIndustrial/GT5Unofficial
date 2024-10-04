import minetweaker.oredict.IOreDictEntry;

var mortarTool = <ore:craftingToolMortar>;
var commonTinyPiles = [
	<ore:dustTinyGold>,
	<ore:dustTinySilver>,
	<ore:dustTinyTin>,
	<ore:dustTinyCopper>,
	<ore:dustTinyIron>,
	<ore:dustTinyLead>
] as IOreDictEntry[];
var commonNuggets = [
	<ore:nuggetGold>,
	<ore:nuggetSilver>,
	<ore:nuggetTin>,
	<ore:nuggetCopper>,
	<ore:nuggetIron>,
	<ore:nuggetLead>
] as IOreDictEntry[];

for i, tinyPile in commonTinyPiles {
	recipes.addShaped(tinyPile.firstItem, [[commonNuggets[i]], [mortarTool]]);
}

furnace.remove(<ore:nuggetSteel>);
recipes.remove(<ore:nuggetSteel>);
recipes.remove(<ore:nuggetGold>);
recipes.remove(<ore:nuggetIron>);
recipes.remove(<ore:nuggetCopper>);
recipes.remove(<ore:nuggetTin>);
