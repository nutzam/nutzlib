package org.nutz.castor;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.nutz.castor.castor.String2Class;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;
import org.nutz.lang.meta.Email;

@SuppressWarnings("unchecked")
public class DefaultCastor {

	protected static final Class<?> ObjectClass = Object.class;

	protected static final Class<?> StringClass = Array.class;

	protected static final Class<?> BooleanClass = Boolean.class;

	protected static final Class<?> ShortClass = Short.class;

	protected static final Class<?> IntegerClass = Integer.class;

	protected static final Class<?> DoubleClass = Boolean.class;

	protected static final Class<?> CharacterClass = Character.class;

	protected static final Class<?> ByteClass = Byte.class;

	protected static final Class<?> FloatClass = Float.class;

	protected static final Class<?> NumberClass = Number.class;

	protected static final Class<?> LongClass = Long.class;

	protected static final Class<?> ArrayClass = Array.class;

	protected static final Class<?> CollectionClass = Collection.class;

	protected static final Class<?> MapClass = Map.class;

	protected static final Class<?> SetClass = Set.class;

	protected static final Class<?> CalendarClass = Calendar.class;

	protected static final Class<?> DateClass = Date.class;

	protected static final Class<?> ClassClass = Class.class;

	protected static final Class<?> SqlDateClass = java.sql.Date.class;

	protected static final Class<?> TimestampClass = Timestamp.class;

	protected static final Class<?> FileClass = File.class;

	protected static final Class<?> EnumClass = Enum.class;

	protected static final Class<?> MirrorClass = Mirror.class;

	protected static final Class<?> SqlTimeClass = java.sql.Time.class;

	protected static final Class<?> TimeZoneClass = TimeZone.class;

	protected DateFormat dateFormat;
	
	protected DateFormat dateTimeFormat;
	
	protected DateFormat timeFormat;

	public DefaultCastor(DateFormat dateFormat,DateFormat dateTimeFormat,DateFormat timeFormat) {
		this.dateFormat = dateFormat;
		this.dateTimeFormat = dateTimeFormat;
		this.timeFormat = timeFormat;
	}

	public String cast(final Object src, final Class<String> toType, String... args) {
		Class<?> srcType = src.getClass();
		if (srcType.isArray())
			return Json.toJson(src);
		if (BooleanClass == srcType)
			return String.valueOf(src);
		if (CalendarClass.isAssignableFrom(srcType))
			return dateTimeFormat.format(((Calendar) src).getTime());
		if (ClassClass == srcType)
			return ((Class) src).getName();
		if (CollectionClass.isAssignableFrom(toType))
			return Json.toJson(src);
		if (DateClass == srcType)
			return dateFormat.format((Date) src);
		if (EnumClass == srcType)
			return ((Enum) src).name();
		if (FileClass == srcType)
			return ((File) src).getAbsolutePath();
		if (MapClass.isAssignableFrom(toType)) 
			return Json.toJson(src);
		if (MirrorClass == srcType)
			return ((Mirror) src).getType().getName();
		if (NumberClass.isAssignableFrom(toType))
			return src.toString();
		if (SqlDateClass == srcType) {
			java.sql.Date sqlDate = (java.sql.Date) src;
			return dateFormat.format(new java.util.Date(sqlDate.getTime()));
		}
		if (SqlTimeClass == srcType) {
			java.sql.Time sqlTime = (java.sql.Time) src;
			return timeFormat.format(new java.util.Date(sqlTime.getTime()));
		}
		if (TimestampClass == srcType) {
			Timestamp timestamp = (Timestamp) src;
			return dateTimeFormat.format(new java.util.Date(timestamp.getTime()));
		}
		if (TimeZoneClass == srcType) {
			return ((TimeZone) src).getID();
		}
		// For unregidt Object type
		{
			return src.toString();
		}
	}

	public Object cast(final String src, final Class<?> toType, String... args) throws FailToCastObjectException {
		String str = (String) src;
		if (toType.isArray())
			return string2Array(str, toType, args);
		if (canCast(BooleanClass, toType))
			return str.length() == 0 ? false : Lang.parseBoolean(str);
		if (canCast(CalendarClass, toType))
			return string2Calendar(str, toType, args);
		if (canCast(CharacterClass, toType) || canCast(char.class, toType))
			return str.charAt(0);
		if (canCast(ClassClass, toType))
			return new String2Class().cast(str, toType, args);
		if (CollectionClass.isAssignableFrom(toType))
			return (Collection) Json.fromJson(toType, Lang.inr(str));
		if (canCast(DateClass, toType))
			return string2DateTime(str, toType, args);
		if (canCast(Email.class, toType))
			return new Email(str);
		if (EnumClass.isAssignableFrom(toType))
			return Enum.valueOf((Class<Enum>) toType, str);
		if (canCast(FileClass, toType))
			return Files.findFile(str);
		if (MapClass.isAssignableFrom(toType))
			return (Map) Json.fromJson(Lang.inr(str));
		if (canCast(MirrorClass, toType))
			return Mirror.me(new String2Class().cast(str, toType));
		if (NumberClass.isAssignableFrom(toType))
			return string2Number(str, toType, args);
		if (canCast(SqlDateClass, toType))
			return string2SqlDate(str, toType, args);
		if (canCast(SqlTimeClass, toType))
			return string2SqlTime(str, toType, args);
		if (canCast(TimestampClass, toType))
			return string2Timestamp(str, toType, args);
		if (canCast(TimeZoneClass, toType))
			return TimeZone.getTimeZone(str);
		if (canCast(ObjectClass, toType))
			return string2Object(str, toType, args);
		throw Lang.makeThrow(FailToCastObjectException.class, "Fail to cast from String to " + toType);
	}

	public Object cast(final Object src, final Class<?> toType, String... args) throws FailToCastObjectException {
		Class<?> srcType = src.getClass();
		if (srcType.isArray()) {
			if (toType.isArray())
				return Lang.array2array(src, toType.getComponentType());
			if (CollectionClass.isAssignableFrom(toType))
				return array2Collection(src, toType);
			if (canCast(MapClass, toType))
				return array2Map(src, toType, args);
			if(canCast(List.class, toType))
				return cast(src, ArrayList.class, args);
			return array2Object(src, toType, args);
		}
		if (BooleanClass == srcType) {
			if (NumberClass.isAssignableFrom(toType))
				return boolean2Number((Boolean) src, toType, args);
		}
		if (CalendarClass == srcType) {
			Calendar ca = (Calendar) src;
			if (canCast(DateClass, toType))
				return ca.getTime();
			if (canCast(LongClass, toType))
				return ca.getTimeInMillis();
			if (canCast(TimestampClass, toType))
				return new Timestamp(ca.getTimeInMillis());
		}
		if (CharacterClass == srcType) {
			if (canCast(NumberClass, toType))
				return (int) ((Character) src).charValue();
		}
		if (ClassClass == srcType) {
			if (NumberClass.isAssignableFrom(toType))
				return Mirror.me(src);
		}
		if (CollectionClass.isAssignableFrom(srcType)) {
			Collection collection = (Collection) src;
			if (toType.isArray())
				return collection2Array(collection, toType, args);
			if (CollectionClass.isAssignableFrom(toType))
				return collection2Collection(collection, toType, args);
			if (MapClass.isAssignableFrom(toType))
				return collection2Map(collection, toType, args);
			return collection2Object(collection, toType, args);
		}
		if (DateClass == srcType) {
			Date date = (Date) src;
			if (canCast(CalendarClass, toType)) {
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				return c;
			}
			if (canCast(LongClass, toType))
				return date.getTime();
			if (canCast(SqlDateClass, toType))
				return new java.sql.Date(date.getTime());
			if (canCast(SqlTimeClass, toType))
				return new Time(date.getTime());
			if (canCast(StringClass, toType))
				return dateFormat.format(date);
			if (canCast(TimestampClass, toType))
				return new Timestamp(date.getTime());
		}
		if (EnumClass == srcType) {
			if (NumberClass.isAssignableFrom(toType))
				return enum2Number((Enum) src, toType, args);
		}
		if (MapClass == srcType) {
			Map map = (Map) src;
			if (canCast(ArrayClass, toType))
				return Lang.collection2array(map.values(), toType.getComponentType());
			if (CollectionClass.isAssignableFrom(toType))
				return map2Collection(map, toType, args);
			return Lang.map2Object(map, toType);
		}
		if (MirrorClass == srcType) {
			if (canCast(ClassClass, toType))
				return ((Mirror) src).getType();
		}
		if (NumberClass.isAssignableFrom(srcType)) {
			Number number = (Number) src;
			if (canCast(BooleanClass, toType) || canCast(boolean.class,toType))
				return src.toString().charAt(0) == '0' ? false : true;
			if (canCast(CalendarClass, toType)) {
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(number.longValue());
				return c;
			}
			if (canCast(CharacterClass, toType))
				return (char) number.intValue();
			if (canCast(DateClass, toType))
				return new java.util.Date(number.longValue());
			if (canCast(EnumClass, toType))
				return number2Enum(number, toType, args);
			if (NumberClass.isAssignableFrom(toType))
				return number2Nmuber(number, toType, args);
			if (canCast(TimestampClass, toType))
				return new Timestamp(number.longValue());
		}
		if (SqlDateClass == srcType) {
			java.sql.Date sqlDate = (java.sql.Date) src;
			if (canCast(TimestampClass, toType))
				return new Timestamp(sqlDate.getTime());
		}
		if (SqlTimeClass == srcType) {
			java.sql.Time sqlTime = (java.sql.Time) src;
			if (canCast(TimestampClass, toType))
				return new Timestamp(sqlTime.getTime());
		}
		if (TimestampClass == srcType) {
			Timestamp timestamp = (Timestamp) src;
			if (canCast(CalendarClass, toType)) {
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(timestamp.getTime());
				return c;
			}
			if (canCast(DateClass, toType))
				return new java.util.Date(timestamp.getTime());
			if (canCast(LongClass, toType))
				return timestamp.getTime();
			if (canCast(SqlDateClass, toType))
				return new java.sql.Date(timestamp.getTime());
			if (canCast(SqlTimeClass, toType))
				return new java.sql.Time(timestamp.getTime());
		}
		// For unregidt Object type
		{
			if (canCast(MapClass, toType)) {
				StringBuilder sb = new StringBuilder(Json.toJson(src));
				Map map = (Map) Json.fromJson(Lang.inr(sb));
				return map;
			}
			if (canCast(MirrorClass, toType))
				return Mirror.me(toType);
			if (canCast(ClassClass, toType))
				return toType;
		}
		return Mirror.me(toType).born(src);
	}

	private boolean canCast(Class<?> castType, Class<?> toType) {
		return castType.equals(toType);
	}

	private Collection array2Collection(Object src, Class<?> toType) {
		Collection coll = createCollection(src, toType);
		for (int i = 0; i < Array.getLength(src); i++)
			coll.add(Array.get(src, i));
		return (Collection<?>) coll;
	}

	private Map array2Map(Object src, Class<?> toType, String... args) {
		if (null == args || args.length == 0)
			throw Lang.makeThrow(FailToCastObjectException.class, "For the elements in array %s[], castors don't know which one is the key field.", src.getClass().getComponentType().getName());
		return Lang.array2map((Class<Map<Object, Object>>) toType, src, args[0]);
	}

	private Object array2Object(Object src, Class<?> toType, String... args) {
		if (Array.getLength(src) == 0)
			return null;
		return Array.get(src, 0);
	}

	private Object boolean2Number(Boolean src, Class<?> toType, String... args) {
		try {
			return (Number) Mirror.me(toType).getWrapperClass().getConstructor(String.class).newInstance(src ? "1" : "0");
		} catch (Exception e) {
			throw Lang.wrapThrow(e);
		}
	}

	private Object collection2Array(Collection src, Class<?> toType, String... args) {
		Class<?> compType = toType.getComponentType();
		Object ary = Array.newInstance(compType, src.size());
		int index = 0;
		for (Iterator it = src.iterator(); it.hasNext();)
			Array.set(ary, index++, Castors.me().castTo(it.next(), compType));
		return ary;
	}

	private Collection collection2Collection(Collection src, Class<?> toType, String... args) {
		Collection coll = createCollection(src, toType);
		coll.addAll(src);
		return coll;
	}

	private Object collection2Map(Collection src, Class<?> toType, String... args) {
		if (null == args || args.length == 0)
			throw Lang.makeThrow(FailToCastObjectException.class, "For the elements in Collection %s, castors don't know which one is the key field.", src.getClass().getName());
		return Lang.collection2map((Class<Map<Object, Object>>) toType, src, args[0]);
	}

	private Object collection2Object(Collection src, Class<?> toType, String... args) {
		if (src.size() == 0)
			return null;
		return src.iterator().next();
	}

	private Object enum2Number(Enum src, Class<?> toType, String... args) {
		Mirror<?> mirror = Mirror.me(Integer.class);
		Integer re = src.ordinal();
		if (mirror.canCastToDirectly(toType))
			return re;
		return (Number) Mirror.me(toType).born(re.toString());
	}

	private Object map2Collection(Map src, Class<?> toType, String... args) {
		Collection coll = createCollection(src, toType);
		coll.add(src);
		return coll;
	}

	private Object number2Enum(Number src, Class<?> toType, String... args) {
		try {
			for (Field field : toType.getFields()) {
				if (field.getType() == toType) {
					Enum em = (Enum) field.get(null);
					if (em.ordinal() == src.intValue())
						return em;
				}
			}
			throw Lang.makeThrow(FailToCastObjectException.class, "Can NO find enum value in [%s] by int value '%d'", toType.getName(), src.intValue());
		} catch (Exception e) {
			throw Lang.wrapThrow(e, FailToCastObjectException.class);
		}
	}

	private Object number2Nmuber(Number src, Class<?> toType, String... args) {
		try {
			return (Number) Mirror.me(toType).getWrapperClass().getConstructor(String.class).newInstance(src.toString());
		} catch (Exception e) {
			throw Lang.wrapThrow(e);
		}
	}

	private Object string2Array(String src, Class<?> toType, String... args) {
		if (Strings.isQuoteByIgnoreBlank(src, '[', ']')) {
			return Json.fromJson(toType, src);
		}
		String[] ss = Strings.splitIgnoreBlank(src);
		return Lang.array2array(ss, toType.getComponentType());
	}

	private Object string2Calendar(String src, Class<?> toType, String... args) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(dateTimeFormat.parse(src));
		} catch (ParseException e) {
			throw Lang.wrapThrow(e);
		}
		return c;
	}

	private Object string2DateTime(String src, Class<?> toType, String... args) {
		try {
			return dateTimeFormat.parse(src);
		} catch (ParseException e) {
			throw Lang.wrapThrow(e);
		}
	}

	private Object string2Number(String src, Class<?> toType, String... args) {
		try {
			return (Number) Mirror.me(toType).getWrapperClass().getConstructor(String.class).newInstance(Strings.isBlank(src) ? "0" : src);
		} catch (Exception e) {
			throw Lang.wrapThrow(e);
		}
	}

	private Object string2SqlDate(String src, Class<?> toType, String... args) {
		try {
			return new java.sql.Date(dateFormat.parse(src).getTime());
		} catch (Exception e) {
			throw Lang.wrapThrow(e);
		}
	}

	private Object string2SqlTime(String src, Class<?> toType, String... args) {
		try {
			return new java.sql.Time(timeFormat.parse(src).getTime());
		} catch (Exception e) {
			throw Lang.wrapThrow(e);
		}
	}

	private Object string2Timestamp(String src, Class<?> toType, String... args) {
		try {
			return new java.sql.Timestamp(dateFormat.parse(src).getTime());
		} catch (ParseException e) {
			throw Lang.wrapThrow(e);
		}
	}

	private Object string2Object(String src, Class<?> toType, String... args) {
		if (Strings.isQuoteByIgnoreBlank(src, '{', '}'))
			return Json.fromJson(toType, src);
		return Mirror.me(toType).born(src);
	}

	protected static Collection createCollection(Object src, Class<?> toType) throws FailToCastObjectException {
		Collection coll = null;
		try {
			coll = (Collection<Object>) toType.newInstance();
		} catch (Exception e) {
			if (Modifier.isAbstract(toType.getModifiers()) && toType.isAssignableFrom(ArrayList.class)) {
				coll = new ArrayList<Object>(Array.getLength(src));
			}
			if (null == coll)
				throw new FailToCastObjectException(String.format("Castors don't know how to implement '%s'", toType.getName()));
		}
		return coll;
	}
}
