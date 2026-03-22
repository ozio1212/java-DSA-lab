package dsaa.lab01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Document {
	public static void loadDocument(String name, Scanner scan) {

		while(scan.hasNextLine()) {

			String line = scan.nextLine();

			// end of document
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
							System.out.println(link);
						}
					}
			}
		}
	}
	
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	public static boolean correctLink(String link) {

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


	static void main() throws FileNotFoundException {
		File plik = new File("dokument.txt");
		Scanner scan = new Scanner(plik);
		loadDocument("dokument.txt",scan);
	}
}
