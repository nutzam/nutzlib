package org.nutz.java;

import org.nutz.java.cls.ByteCode;
import org.nutz.java.src.JavaType;

public interface JavaCompiler {

	ByteCode compile(JavaType src);

}
