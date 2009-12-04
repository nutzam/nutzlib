package org.nutz.java;

import org.nutz.java.cls.JavaClass;
import org.nutz.java.src.JavaSource;

public interface JavaCompiler {

	JavaClass compile(JavaSource src);

}
