package org.nutz.java;

import org.nutz.java.bytecode.ByteCode;
import org.nutz.java.src.JavaType;

public interface JavaCompiler {

	ByteCode compile(JavaType src);

}
