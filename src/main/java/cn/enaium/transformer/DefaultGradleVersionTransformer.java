package cn.enaium.transformer;

import cn.enaium.KeepGradleVersion;
import cn.enaium.cafully.plugin.api.ITransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.security.ProtectionDomain;

/**
 * @author Enaium
 */
public class DefaultGradleVersionTransformer implements ITransformer {
    @Override
    public boolean supportClass(String name) {
        return name.equals("org/gradle/util/internal/DefaultGradleVersion");
    }

    @Override
    public byte[] before(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] basic) throws Exception {
        final ClassReader classReader = new ClassReader(basic);

        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (MethodNode method : classNode.methods) {
            if (method.name.equals("getVersion")) {
                method.instructions.clear();
                method.instructions.add(new LdcInsnNode(KeepGradleVersion.version));
                method.instructions.add(new InsnNode(Opcodes.ARETURN));
            }
        }

        final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
