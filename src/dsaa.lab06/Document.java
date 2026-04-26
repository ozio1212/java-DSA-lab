package dsaa.lab06;

import java.util.ListIterator;
import java.util.Scanner;

public class Document{
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;
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


	public int[] getWeights() {
		int[] weights = new int[link.size()];
		int index = 0;

		for(Link link : link) {
			weights[index] = link.weight;
			index++;
		}

		return weights;
	}

	public static void showArray(int[] arr) {
		if (arr==null || arr.length==0) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i < arr.length-1) {
				sb.append(" ");
			}
		}

		System.out.println(sb.toString());
	}

	void bubbleSort(int[] arr) {
		showArray(arr);

		int n = arr.length;

		for (int i = 0; i < n - 1; i++){

			for(int j = n-1; j > i; j--){

				if(arr[j] < arr[j-1]){
					int temp = arr[j];
					arr[j] = arr[j-1];
					arr[j-1] = temp;
				}

			}
			showArray(arr);
		}
	}

	public void insertSort(int[] arr) {
		showArray(arr);

		int n = arr.length;

		for (int i = n - 2; i >= 0 ; i--){

			int key = arr[i];
			int j = i + 1;

			while (j < n && arr[j] < key){
				arr[j-1] = arr[j];
				j++;
			}

			arr[j-1] = key;

			showArray(arr);
		}
	}
	public void selectSort(int[] arr) {
		showArray(arr);

		int n = arr.length;

		for (int i = n-1; i>0; i--){

			int maxIndex = 0;

			for (int j = 1; j <= i; j++){
				if(arr[j] > arr[maxIndex]){
					maxIndex = j;
				}
			}

			int temp = arr[i];
			arr[i] = arr[maxIndex];
			arr[maxIndex] = temp;

			showArray(arr);
		}
	}

	public void iterativeMergeSort(int[] arr) {
		showArray(arr);

		int n = arr.length;
		int[] temp = new int[n];

		for (int currSize = 1; currSize < n; currSize *= 2) {

			for (int leftStart = 0; leftStart < n - 1; leftStart += 2 * currSize) {

				int mid = Math.min(leftStart + currSize - 1, n - 1);
				int rightEnd = Math.min(leftStart + 2 * currSize - 1, n - 1);

				merge(arr, temp, leftStart, mid, rightEnd);
			}

			showArray(arr);
		}
	}

	public void radixSort(int[] arr) {
		showArray(arr);

		for (int exp = 1; exp <= 100; exp *= 10) {
			countingSort(arr, exp);

			showArray(arr);
		}
	}

	private void merge(int[] arr, int[] temp, int left, int mid, int right) {
		int i = left;
		int j = mid + 1;
		int k = left;

		while (i <= mid && j <= right) {
			if (arr[i] <= arr[j]) {
				temp[k++] = arr[i++];
			} else {
				temp[k++] = arr[j++];
			}
		}

		while (i <= mid) {
			temp[k++] = arr[i++];
		}

		while (j <= right) {
			temp[k++] = arr[j++];
		}

		for (i = left; i <= right; i++) {
			arr[i] = temp[i];
		}
	}

	private void countingSort(int[] arr, int exp) {
		int n = arr.length;
		int[] output = new int[n];
		int[] count = new int[10];

		for (int i = 0; i < n; i++) {
			int digit = (arr[i] / exp) % 10;
			count[digit]++;
		}

		for (int i = 1; i < 10; i++) {
			count[i] += count[i - 1];
		}

		for (int i = n - 1; i >= 0; i--) {
			int digit = (arr[i] / exp) % 10;

			output[count[digit] - 1] = arr[i];

			count[digit]--;
		}

		for (int i = 0; i < n; i++) {
			arr[i] = output[i];
		}
	}
}
