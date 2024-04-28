package applicationstack;

public class StackLinkedStack<A extends Comparable<A>> {
	private Node<A> topNode;

	public void push(A data) {
		Node<A> newNode = new Node<A>(data);
		newNode.setNext(topNode);
		topNode = newNode;
	}

	public Node<A> pop() {
		Node<A> toDel = topNode;
		if (topNode != null)
			topNode = topNode.getNext();
		return toDel;
	}

	public Node<A> peek() {
		return topNode;
	}

	public int length() {
		int length = 0;
		Node<A> curr = topNode;
		while (curr != null) {
			length++;
			curr = curr.getNext();
		}
		return length;
	}

	public boolean isEmpty() {
		return (topNode == null);
	}

	public void clear() {
		topNode = null;
	}
}
