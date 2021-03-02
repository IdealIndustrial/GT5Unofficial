package idealindustrial.integration.ingameinfo;

import com.github.lunatrius.ingameinfo.integration.Plugin;

public class InGameInfoLoader extends Plugin {
    @Override
    protected String getDependency() {
        return "";
    }

    @Override
    public void load() {
        II_TagVersion.register();
    }
}
