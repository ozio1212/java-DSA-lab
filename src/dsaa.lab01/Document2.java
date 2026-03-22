package dsaa.lab01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Document2 {
    public static void loadDocument(String name, Scanner scan) {

        Pattern pattern = Pattern.compile("(?<!\\S)link=(?![a-zA-Z0-9_]*__)[A-Za-z](?:[a-zA-Z0-9_]*[A-Za-z])?(?!\\S)");
        String line;

        while(scan.hasNextLine()) {
            line = scan.nextLine();
            if (line.equals("eod")) {
                break;
            }
            var matcher = pattern.matcher(line);

            while (matcher.find()) {
                String link = matcher.group().substring(5).toLowerCase();
                System.out.println(link);
            }
        }
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    public static boolean correctLink(String link) {
        return true;
    }


    static void main() throws FileNotFoundException {
        File plik = new File("dokument.txt");
        Scanner scan = new Scanner(plik);
        loadDocument("dokument.txt",scan);
    }
}
