package cn.enaium;

import cn.enaium.cafully.plugin.annotation.Dependency;
import cn.enaium.cafully.plugin.annotation.Plugin;
import cn.enaium.cafully.plugin.annotation.Transformer;
import cn.enaium.cafully.plugin.api.IInitializer;
import cn.enaium.cafully.plugin.helper.IHelper;
import cn.enaium.transformer.DefaultGradleVersionTransformer;
import cn.enaium.transformer.WrapperConfigurationTransformer;

/**
 * @author Enaium
 */
@Plugin(unique = "keep-gradle-version", name = "Keep Gradle Version", version = "1.0.0", api = ">=1.0.0",
        description = "Use your own version of Gradle when create or open a project", authors = "Enaium",
        transformers = {@Transformer(DefaultGradleVersionTransformer.class), @Transformer(WrapperConfigurationTransformer.class)},
        dependencies = @Dependency(unique = "asm", version = "*"))
public class KeepGradleVersion implements IInitializer {

    public static String version;
    public static boolean open;

    @Override

    public void initialize(IHelper helper) {
        version = helper.config().read().getProperty("version", "7.6");
        open = Boolean.parseBoolean(helper.config().read().getProperty("open", "false"));
    }
}
