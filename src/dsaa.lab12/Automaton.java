package dsaa.lab12;

import java.util.LinkedList;

public class Automaton implements IStringMatcher {

	@Override
	public LinkedList<Integer> validShifts(String pattern, String text) {
		LinkedList<Integer> result = new LinkedList<>();
		if (pattern == null || text == null || pattern.isEmpty() || text.isEmpty()) {
			return result;
		}

		int m = pattern.length();
		int n = text.length();

		int lowerCode = Integer.MAX_VALUE;
		int biggerCode = Integer.MIN_VALUE;


		for (int i = 0; i < m; i++) {
			char c = pattern.charAt(i);
			if (c < lowerCode) lowerCode = c;
			if (c > biggerCode) biggerCode = c;
		}

		for (int i = 0; i < n; i++) {
			char c = text.charAt(i);
			if (c < lowerCode) lowerCode = c;
			if (c > biggerCode) biggerCode = c;
		}

		int alphabetSize = biggerCode - lowerCode + 1;

		int[][] delta = new int[m + 1][alphabetSize];

		int[] pi = computePrefixFunction(pattern);

		for (int q = 0; q <= m; q++) {
			for (int a = 0; a < alphabetSize; a++) {
				char charA = (char) (a + lowerCode);

				if (q < m && pattern.charAt(q) == charA) {
					delta[q][a] = q + 1;
				} else if (q == 0) {
					delta[q][a] = 0;
				} else {
					delta[q][a] = delta[pi[q - 1]][a];
				}
			}
		}

		int q = 0;
		for (int i = 0; i < n; i++) {
			int charIndex = text.charAt(i) - lowerCode;

			q = delta[q][charIndex];

			if (q == m) {
				result.add(i - m + 1);
			}
		}

		return result;
	}

	private int[] computePrefixFunction(String pattern) {
		int m = pattern.length();
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
		return pi;
	}
}
