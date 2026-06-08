package dsaa.lab12;

import java.util.LinkedList;

public class KMP implements IStringMatcher {

	@Override
	public LinkedList<Integer> validShifts(String pattern, String text) {
		LinkedList<Integer> result = new LinkedList<>();
		if (pattern == null || text == null || pattern.isEmpty() || text.isEmpty()) {
			return result;
		}

		int m = pattern.length();
		int n = text.length();

		int[] pi = new int[m];
		int k = 0;

		pi[0] = 0;

		for (int q = 1; q < m; q++) {

			while (k > 0 && pattern.charAt(k) != pattern.charAt(q)) {
				k = pi[k - 1];
			}

			if (pattern.charAt(k) == pattern.charAt(q)) {
				k++;
			}
			pi[q] = k;
		}

		int q = 0;
        
		for (int i = 0; i < n; i++) {

			while (q > 0 && pattern.charAt(q) != text.charAt(i)) {
				q = pi[q - 1];
			}

			if (pattern.charAt(q) == text.charAt(i)) {
				q++;
			}

			if (q == m) {

				result.add(i - m + 1);

				q = pi[q - 1];
			}
		}

		return result;
	}

}
