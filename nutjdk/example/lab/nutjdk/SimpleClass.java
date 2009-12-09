package lab.nutjdk;

public class SimpleClass<T> implements SimpleInterface {

	public static int XYZ = 12;

	private String str;

	private int num;

	T abc;

	public SimpleClass(String str, int num) {
		this.num = num;
		this.str = str;
	}

	String evalString() {
		if (num > 10)
			return str + "10";
		return str + num;
	}

	public void doVoid(String s) {
		System.out.println(s);
	}
}
