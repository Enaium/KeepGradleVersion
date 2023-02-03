/*
 * Copyright (c) 2023 Enaium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
