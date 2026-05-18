package dsaa.lab09;

public class DisjointSetLinkedList implements DisjointSetDataStructure {

	private class Element{
		int representant;
		int next;
		int length;
		int last;
	}
	
	private static final int NULL=-1;
	
	Element arr[];
	
	public DisjointSetLinkedList(int size) {
		arr = new Element[size];
		for (int i = 0; i < size; i++) {
			arr[i] = new Element();
		}
	}
	
	@Override
	public void makeSet(int item) {
		arr[item].representant = item;
		arr[item].next = NULL;
		arr[item].length = 1;
		arr[item].last = item;
	}

	@Override
	public int findSet(int item) {
		return arr[item].representant;
	}

	@Override
	public boolean union(int itemA, int itemB) {
		int repA = findSet(itemA);
		int repB = findSet(itemB);

		if (repA == repB) return false;

		int lenA = arr[repA].length;
		int lenB = arr[repB].length;

		if (lenA >= lenB) {
			append(repA, repB);
		} else {
			append(repB, repA);
		}
		return true;
	}

    private void append(int mainRep, int appendedRep) {
		int tailOfMain = arr[mainRep].last;
		arr[tailOfMain].next = appendedRep;

		arr[mainRep].last = arr[appendedRep].last;

		arr[mainRep].length += arr[appendedRep].length;

		int curr = appendedRep;
		while (curr != NULL) {
			arr[curr].representant = mainRep;
			curr = arr[curr].next;
		}
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Disjoint sets as linked list:\n");
		boolean firstLine = true;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i].representant == i) {
				if (!firstLine) sb.append("\n");

				int curr = i;
				boolean firstElem = true;
				while (curr != NULL) {
					if (!firstElem) sb.append(", ");
					sb.append(curr);
					firstElem = false;
					curr = arr[curr].next;
				}
				firstLine = false;
			}
		}
		return sb.toString();
	}

}
