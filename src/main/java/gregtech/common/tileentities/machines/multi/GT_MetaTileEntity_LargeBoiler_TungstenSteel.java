package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Config;
import net.minecraft.block.Block;

public class GT_MetaTileEntity_LargeBoiler_TungstenSteel
        extends GT_MetaTileEntity_LargeBoiler {
    public static long oxygenPerOperation = 40;
    public void onConfigLoad(GT_Config aConfig) {
        super.onConfigLoad(aConfig);
        oxygenPerOperation = Math.max(1, Math.min(500, aConfig.get(ConfigCategories.machineconfig, "LargeBoiler.TungstenSteel.oxygenPerOperation", 40)));
    }
    public GT_MetaTileEntity_LargeBoiler_TungstenSteel(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_LargeBoiler_TungstenSteel(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_LargeBoiler_TungstenSteel(this.mName);
    }
    
    public String getCasingMaterial(){
    	return "TungstenSteel";
    }

	@Override
	public String getCasingBlockType() {
		return "Machine Casings";
	}

    public Block getCasingBlock() {
        return GregTech_API.sBlockCasings4;
    }

    public byte getCasingMeta() {
        return 0;
    }

    public byte getCasingTextureIndex() {
        return 48;
    }

    public Block getPipeBlock() {
        return GregTech_API.sBlockCasings2;
    }

    public byte getPipeMeta() {
        return 15;
    }

    public Block getFireboxBlock() {
        return GregTech_API.sBlockCasings3;
    }

    public byte getFireboxMeta() {
        return 15;
    }

    public byte getFireboxTextureIndex() {
        return 47;
    }

    public int getEUt() {
        return 1000;
    }

    public int getEfficiencyIncrease() {
        return 4;
    }

    @Override
    int runtimeBoost(int mTime) {
        return mTime * 120 / 100;
    }

    public long getOxygenPerOperation(){
        return oxygenPerOperation;
    }

}
