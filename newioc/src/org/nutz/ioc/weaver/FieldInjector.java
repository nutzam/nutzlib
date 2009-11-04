package org.nutz.ioc.weaver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.nutz.castor.Castors;
import org.nutz.ioc.IocContext;
import org.nutz.ioc.ValueProxy;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;

public class FieldInjector {

	private ValueProxy valueProxy;
	private DoSet doSet;

	public FieldInjector(Mirror<?> mirror, String fieldName, ValueProxy vp) {
		valueProxy = vp;
		try {
			Field field = mirror.getField(fieldName);
			try {
				Method setter = mirror.getSetter(field);
				doSet = new DoSetBySetter(setter);
			} catch (NoSuchMethodException e) {
				doSet = new DoSetByField(field);
			}
		} catch (NoSuchFieldException e) {
			throw Lang.wrapThrow(e);
		}
	}

	void inject(IocContext context, Object obj) {
		Object value = valueProxy.get(context);
		doSet.set(obj, value);
	}

	/* ================================================================= */
	interface DoSet {
		void set(Object obj, Object value);
	}

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	class DoSetByField implements DoSet {

		private Field field;

		DoSetByField(Field field) {
			this.field = field;
			this.field.setAccessible(true);
		}

		public void set(Object obj, Object value) {
			Object v = null;
			try {
				v = Castors.me().castTo(value, field.getType());
				field.set(obj, v);
			} catch (Exception e) {
				throw Lang.makeThrow("Fail to set '%s' to field %s.'%s' because: %s", v, field
						.getDeclaringClass().getName(), field.getName(), e.getMessage());
			}
		}
	}

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	class DoSetBySetter implements DoSet {

		private Method setter;
		private Class<?> valueType;

		public DoSetBySetter(Method setter) {
			this.setter = setter;
			valueType = setter.getParameterTypes()[0];
		}

		public void set(Object obj, Object value) {
			Object v = null;
			try {
				v = Castors.me().castTo(value, valueType);
				setter.invoke(obj, v);
			} catch (Exception e) {
				throw Lang.makeThrow("Fail to set '%s' by setter %s.'%s()' because: %s", v, setter
						.getDeclaringClass().getName(), setter.getName(), e.getMessage());
			}
		}

	}

	/* ================================================================= */
}
