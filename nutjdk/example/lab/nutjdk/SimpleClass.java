package lab.nutjdk;

public class SimpleClass {

	private String str;

	private int num;
 
	public SimpleClass(String str, int num) {
		this.num = num;
		this.str = str;
	}

	String evalString() {
		if (num > 10)
			return str + "10";
		return str + num;
	}

}
