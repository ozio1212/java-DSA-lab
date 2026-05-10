package dsaa.lab08;

import java.util.Scanner;

public class Document implements IWithName {
	public String name;
	public BST<Link> link;

	private static final int MODVALUE = 100000000;
	private static final int[] SEQUENCE = {7, 11, 13, 17, 19};

	public Document(String name) {
		this.name = name.toLowerCase();
		link = new BST<Link>();
	}

	public Document(String name, Scanner scan) {
		this.name = name.toLowerCase();
		link = new BST<Link>();
		load(scan);
	}

	public void load(Scanner scan) {
		while (scan.hasNext()) {
			String token = scan.next();
			if (token.equalsIgnoreCase("eod")) {
				break;
			}
			if (token.toLowerCase().startsWith("link=")) {
				String linkStr = token.substring(5);
				Link newLink = createLink(linkStr);
				if (newLink != null) {
					link.add(newLink);
				}
			}
		}
	}

	public static boolean isCorrectId(String id) {
		if (id == null || id.length() == 0) return false;
		char firstChar = id.charAt(0);
		if (!Character.isLetter(firstChar)) return false;

		for (int i = 1; i < id.length(); i++) {
			char c = id.charAt(i);
			if (!Character.isLetterOrDigit(c) && c != '_') {
				return false;
			}
		}
		return true;
	}

	static Link createLink(String link) {
		if (link == null || link.isEmpty()) return null;

		int openParen = link.indexOf('(');
		int closeParen = link.indexOf(')');

		if (openParen == -1 && closeParen == -1) {
			if (isCorrectId(link)) {
				return new Link(link.toLowerCase());
			}
		} else if (openParen > 0 && closeParen == link.length() - 1) {
			String id = link.substring(0, openParen);
			if (isCorrectId(id)) {
				try {
					int weight = Integer.parseInt(link.substring(openParen + 1, closeParen));
					return new Link(id.toLowerCase(), weight);
				} catch (NumberFormatException e) {
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String retStr = "Document: " + name + "\n";
		retStr += link.toStringInOrder();
		return retStr;
	}

	public String toStringPreOrder() {
		String retStr = "Document: " + name + "\n";
		retStr += link.toStringPreOrder();
		return retStr;
	}

	public String toStringPostOrder() {
		String retStr = "Document: " + name + "\n";
		retStr += link.toStringPostOrder();
		return retStr;
	}

	@Override
	public int hashCode() {
		if (name == null || name.isEmpty()) {
			return 0;
		}

		int hash = name.charAt(0);
		for (int i = 1; i < name.length(); i++) {
			int seqIndex = (i - 1) % SEQUENCE.length;
			int multiplier = SEQUENCE[seqIndex];
			long tempHash = ((long) hash * multiplier) + name.charAt(i);
			hash = (int) (tempHash % MODVALUE);
		}
		return hash;
	}

	@Override
	public String getName() {
		return name;
	}
}