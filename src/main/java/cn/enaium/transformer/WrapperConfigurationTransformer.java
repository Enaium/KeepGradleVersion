package cn.enaium.transformer;

import cn.enaium.KeepGradleVersion;
import cn.enaium.cafully.plugin.api.ITransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.security.ProtectionDomain;

/**
 * @author Enaium
 */
public class WrapperConfigurationTransformer implements ITransformer {
    @Override
    public boolean supportClass(String name) {
        System.out.println(name.equals("org/gradle/wrapper/WrapperConfiguration") && KeepGradleVersion.open);
        return name.equals("org/gradle/wrapper/WrapperConfiguration") && KeepGradleVersion.open;
    }

    @Override
    public byte[] before(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] basic) throws Exception {
        final ClassReader classReader = new ClassReader(basic);

        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (MethodNode method : classNode.methods) {
            if (method.name.equals("getDistribution")) {
                method.instructions.clear();
                method.instructions.add(new LdcInsnNode(String.format("https://services.gradle.org/distributions/gradle-%s-bin.zip", KeepGradleVersion.version)));
                method.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/net/URI", "create", "(Ljava/lang/String;)Ljava/net/URI;"));
                method.instructions.add(new InsnNode(Opcodes.ARETURN));
            }
        }

        final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
