package dsaa.lab03;

import java.util.Scanner;

public class Document{
	public String name;
	public TwoWayUnorderedListWithHeadAndTail<Link> link;
	public Document(String name, Scanner scan) {
		this.name=name;
		link=new TwoWayUnorderedListWithHeadAndTail<Link>();
		load(scan);
	}
	public void load(Scanner scan) {

		while(scan.hasNextLine()) {

			String line = scan.nextLine();

			// end of a document
			if(line.equals("eod")){
				break;
			}

			// rozdzielenie słów do tablicy
			// białe znaki, a znak \s oznacza dowolny biały znak, a + oznacza jeden lub więcej.
			String[] words = line.split("\\s+");

			// szukanie linków i sprawdzanie poprawnosci
			for (String word : words) {
				word = word.toLowerCase();
				if(word.startsWith("link=")) {
					String link = word.substring(5);
					if(correctLink(link)) {
						// dodanie linku do listy zamiast wyprintowanie jak na liscie 1
						this.link.add(new Link(link));
					}
				}
			}
		}
	}
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the beginning)
	private static boolean correctLink(String link) {
		// Sprawdzenie czy link zaczyna sie literka i czy nie jest pusty
		if(link.length()==0 || !Character.isLetter(link.charAt(0))){
			return false;
		}

		// sprawdzenie poprawnosci pozostalej czesci linku
		for(int i=1 ; i<link.length() ; i++) {
			if(!Character.isLetterOrDigit(link.charAt(i)) && link.charAt(i)!='_') {
				return false;
			}
		}
		// jezeli wszystko jest okej zwracamy true
		return true;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("Document: ").append(name); //

		for (Link link : link) {
			sb.append("\n").append(link.ref);
		}
		return sb.toString();
	}
	
	public String toStringReverse() {
		String retStr="Document: "+name;
		return retStr+link.toStringReverse();
	}

}
