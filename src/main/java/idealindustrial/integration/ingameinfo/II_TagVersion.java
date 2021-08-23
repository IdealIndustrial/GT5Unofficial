package idealindustrial.integration.ingameinfo;

import com.github.lunatrius.ingameinfo.tag.TagIntegration;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import idealindustrial.II_Core;

public class II_TagVersion extends TagIntegration {
    @Override
    public String getCategory() {
        return "iim";
    }


    public static void register() {
        TagRegistry.INSTANCE.register((new II_TagVersion().setName("iimver")));
    }

    @Override
    public String getValue() {
        return II_Core.getVersion();
    }
}
