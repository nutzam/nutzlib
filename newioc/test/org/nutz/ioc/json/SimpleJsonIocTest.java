package org.nutz.ioc.json;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.json.pojo.Animal;
import org.nutz.ioc.json.pojo.AnimalRace;
import org.nutz.ioc.loader.map.MapLoader;
import org.nutz.json.Json;
import org.nutz.lang.Lang;

public class SimpleJsonIocTest {

	@SuppressWarnings("unchecked")
	private static Ioc I(String... ss) {
		String json = "{";
		json += Lang.concatBy(',', ss);
		json += "}";
		Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) Json
				.fromJson(json);
		return new NutIoc(new MapLoader(map));
	}

	private static String J(String name, String s) {
		return name + " : {" + s + "}";
	}

	private static Animal A(String s) {
		Ioc ioc = I(J("obj", s));
		return ioc.get(Animal.class, "obj");
	}

	@Test
	public void test_normal() {
		Animal a = A("age:23,name:'monkey',race:'MAMMAL'");
		assertEquals(23, a.getAge());
		assertEquals("monkey", a.getName());
		assertEquals(AnimalRace.MAMMAL, a.getRace());
	}

	@Test
	public void test_refer() {
		Ioc ioc = I(J("fox", "type:'org.nutz.ioc.json.pojo.Animal',fields:{name:'Fox'}"), J(
				"rabit", "name:'Rabit',enemies:[{refer:'fox'},{refer:'fox'}]"));
		Animal r = ioc.get(Animal.class, "rabit");
		Animal f = ioc.get(Animal.class, "fox");
		assertEquals(2, r.getEnemies().length);
		assertTrue(f == r.getEnemies()[0]);
		assertTrue(f == r.getEnemies()[1]);
		assertEquals("Fox", f.getName());
		assertEquals("Rabit", r.getName());
	}

	@Test
	public void test_array_and_refer() {
		Ioc ioc = I(J("fox", "name:'Fox'"), J("rabit",
				"name:'Rabit',enemies:[{refer:'fox:org.nutz.ioc.json.pojo.Animal'},null]"));

		Animal r = ioc.get(Animal.class, "rabit");
		Animal f = ioc.get(Animal.class, "fox");
		assertEquals(2, r.getEnemies().length);
		assertTrue(f == r.getEnemies()[0]);
		assertEquals("Fox", f.getName());
		assertEquals("Rabit", r.getName());
		assertNull(r.getEnemies()[1]);
	}

}
