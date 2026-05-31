package dsaa.lab11;

import java.util.Scanner;
import java.util.*;

public class Document implements IWithName {
	public String name;
	public SortedMap<String, Link> link;

	public Document(String name) {
		this.name = name.toLowerCase();
		link = new TreeMap<String, Link>();
	}

	public Document(String name, Scanner scan) {
		this.name = name.toLowerCase();
		link = new TreeMap<String, Link>();
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
					link.put(newLink.ref, newLink);
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
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Link l : link.values()) {
			if (!first) sb.append(", ");
			sb.append(l.toString());
			first = false;
		}
		retStr += sb.toString();
		return retStr;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String getName() {
		return name;
	}
}