package org.nutz.dao.entity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
public @interface Id {
	/**
	 * true : auto increasement
	 */
	boolean auto() default true;

	/**
	 * How to get the new auto increasement value.
	 */
	String[] value() default {};
}
