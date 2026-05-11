package dsaa.lab08;

public class BST<T> {
	private class Node{
		T value;
		Node left,right,parent;

		public Node(T v) {
			value=v;
		}

		public Node(T value, Node left, Node right, Node parent) {
			super();
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}

	private Node root=null;
	private int size = 0;

	public BST() {
	}

	public T getElement(T toFind) {
		Node current = root;
		Comparable<T> searchKey = (Comparable<T>) toFind;

		while(current != null){

			int cmp = searchKey.compareTo(current.value);

			if(cmp == 0){
				return current.value;
			} else if (cmp < 0){
				current = current.left;
			} else {
				current = current.right;
			}
		}
		return null;
	}

	public T successor(T elem) {
		Node node = findNode(elem);
		if (node == null) return null;

		if (node.right != null) {
			Node current = node.right;
			while (current.left != null) {
				current = current.left;
			}
			return current.value;
		}

		Node parent = node.parent;
		Node current = node;
		while (parent != null && current == parent.right) {
			current = parent;
			parent = parent.parent;
		}

		if (parent == null) return null;
		return parent.value;
	}


	public String toStringInOrder() {
		StringBuilder sb = new StringBuilder();
		inOrderTraversal(root, sb);
		return formatOutput(sb);
	}

	public String toStringPreOrder() {
		StringBuilder sb = new StringBuilder();
		preOrderTraversal(root, sb);
		return formatOutput(sb);
	}

	public String toStringPostOrder() {
		StringBuilder sb = new StringBuilder();
		postOrderTraversal(root, sb);
		return formatOutput(sb);
	}


	public boolean add(T elem) {
		if (root == null) {
			root = new Node(elem, null, null, null);
			size++;
			return true;
		}

		Node current = root;
		Comparable<T> key = (Comparable<T>) elem;

		while (true) {
			int cmp = key.compareTo(current.value);

			if (cmp == 0) {
				return false;
			} else if (cmp < 0) {
				if (current.left == null) {
					current.left = new Node(elem, null, null, current);
					size++;
					return true;
				}
				current = current.left;
			} else {
				if (current.right == null) {
					current.right = new Node(elem, null, null, current);
					size++;
					return true;
				}
				current = current.right;
			}
		}
	}

	public T remove(T value) {
		Node node = findNode(value);
		if (node == null) return null;

		T removedValue = node.value;

		if (node.left != null && node.right != null) {
			Node succ = node.right;
			while (succ.left != null) succ = succ.left;

			node.value = succ.value;
			node = succ;
		}

		Node replacement = (node.left != null) ? node.left : node.right;

		if (replacement != null) {
			replacement.parent = node.parent;
			if (node.parent == null) {
				root = replacement;
			} else if (node == node.parent.left) {
				node.parent.left = replacement;
			} else {
				node.parent.right = replacement;
			}
		} else if (node.parent == null) {
			root = null;
		} else {
			if (node == node.parent.left) {
				node.parent.left = null;
			} else {
				node.parent.right = null;
			}
		}

		size--;
		return removedValue;
	}
	
	public void clear() {
		root = null;
		size = 0;
	}

	public int size() {
		return size;
	}


	private Node findNode(T value) {
		Node current = root;
		Comparable<T> searchKey = (Comparable<T>) value;

		while (current != null) {
			int cmp = searchKey.compareTo(current.value);
			if (cmp == 0) return current;
			else if (cmp < 0) current = current.left;
			else current = current.right;
		}
		return null;
	}

	private void inOrderTraversal(Node node, StringBuilder sb) {
		if (node == null) return;
		inOrderTraversal(node.left, sb);
		sb.append(node.value.toString()).append(", ");
		inOrderTraversal(node.right, sb);
	}

	private void preOrderTraversal(Node node, StringBuilder sb) {
		if (node == null) return;
		sb.append(node.value.toString()).append(", ");
		preOrderTraversal(node.left, sb);
		preOrderTraversal(node.right, sb);
	}

	private void postOrderTraversal(Node node, StringBuilder sb) {
		if (node == null) return;
		postOrderTraversal(node.left, sb);
		postOrderTraversal(node.right, sb);
		sb.append(node.value.toString()).append(", ");
	}

	private String formatOutput(StringBuilder sb) {
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 2);
		}
		return sb.toString();
	}

	public void removeBigger(T value){

		if (root == null) return;
		Comparable<T> key = (Comparable<T>) value;

		while (root != null && key.compareTo(root.value) < 0) {
			root = root.left;
		}

		// jesli kazdy eleemnt byl wiekszy to puste drzewo
		if (root == null) {
			size = 0;
			return;
		}

		root.parent = null;

		// sprawdzenie prawej strony roota
		Node current = root;
		while (current != null) {
			if (current.right != null && key.compareTo(current.right.value) < 0) {
				Node leftOfRight = current.right.left;
				current.right = leftOfRight;

				if (leftOfRight != null) {
					leftOfRight.parent = current;
				}

			} else {
				current = current.right;
			}
		}
	}

}
