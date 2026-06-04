package dsaa.lab12;

import java.util.Scanner;

public class LinesReader {
		String concatLines(int howMany, Scanner scanner) {
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < howMany; i++) {
				if (scanner.hasNextLine()) {
					sb.append(scanner.nextLine());
				}
			}

			return sb.toString();
		}

}
