package applicationstack;

public class ArrayStack<T> {
	private Object[] s;
	private int n = -1;

	public ArrayStack(int capacity) {
		s = new Object[capacity];
	}

	public boolean isEmpty() {
		return n == -1;
	}

	public int getN() {
		return n;
	}

	public void push(T data) {
		s[++n] = data;
	}

	public Object pop() {
		if (!isEmpty())
			return s[n--];
		return null;
	}

	public String toString() {
		String res = "Top-->";
		for (int i = n; i >= 0; i--)
			res += "[" + s[i] + "]-->";
		return res + "Null";
	}
}