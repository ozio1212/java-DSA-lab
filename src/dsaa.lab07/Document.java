package dsaa.lab07;

import java.util.ListIterator;
import java.util.Scanner;

public class Document implements IWithName{

	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;

	private static final int MODVALUE = 100000000;
	private static final int[] SEQUENCE = {7, 11, 13, 17, 19};

	public Document(String name) {
		this.name = name.toLowerCase();
		link = new TwoWayCycledOrderedListWithSentinel<Link>();
	}

	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new TwoWayCycledOrderedListWithSentinel<Link>();
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
					Link parsedLink = createLink(link);

					if(parsedLink!=null) {
						// dodanie linku do listy zamiast wyprintowanie jak na liscie 1
						this.link.add(parsedLink);
					}
				}
			}
		}
	}

	public static boolean isCorrectId(String id) {
		if (id == null || id.length() == 0 || !Character.isLetter(id.charAt(0))) {
			return false;
		}
		for (int i = 1; i < id.length(); i++) {
			if(!Character.isLetterOrDigit(id.charAt(i)) && id.charAt(i) != '_') {
				return false;
			}
		}
		return true;
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	static Link createLink(String link) {

		int openBracket = link.indexOf('(');

		if (openBracket == -1) {
			if (isCorrectId(link)) {
				return new Link(link);
			}
			return null;
		} else {

			String linkID = link.substring(0, openBracket);

			int closeBracket = link.indexOf(')',openBracket);

			if (closeBracket != -1 && isCorrectId(linkID)) {
				String weightString = link.substring(openBracket + 1, closeBracket);

				try {
					int weight = Integer.parseInt(weightString);
					if (weight > 0) {
						return new Link(linkID, weight);
					}
				} catch (NumberFormatException e) {
					return null;
				}
			}
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Document: ").append(name);

		int count = 0;

		for (Link link : link) {
			if (count % 10 == 0){
				sb.append("\n");
			} else {
				sb.append(" ");
			}

			sb.append(link.toString());
			count++;
		}

		return sb.toString();
	}

	public String toStringReverse() {
		StringBuilder sb = new StringBuilder();
		sb.append("Document: ").append(name);

		ListIterator<Link> iter=link.listIterator();

		while(iter.hasNext()) {
			iter.next();
		}

		int count = 0;

		while (iter.hasPrevious()) {
			if (count % 10 == 0){
				sb.append("\n");
			} else {
				sb.append(" ");
			}
			sb.append(iter.previous().toString());
			count++;
		}

		return sb.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object object){
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return  false;
		Document other = (Document) object;
		return name.equals(other.name);
	}

	@Override
	public int hashCode(){
		if (name == null || name.isEmpty()){
			return 0;
		}

		int hash = name.charAt(0);

		for (int i = 1 ; i < name.length() ; i++){
			int seqIndex = (i-1) % SEQUENCE.length;
			int multiplier = SEQUENCE[seqIndex];

			hash = ( ( (hash*multiplier) + name.charAt(i) ) % MODVALUE);
		}

		return hash;
	}
}

