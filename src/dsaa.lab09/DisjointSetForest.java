package dsaa.lab09;

public class DisjointSetForest implements DisjointSetDataStructure {
	
	private class Element{
		int rank;
		int parent;
	}

	Element []arr;
	
	public DisjointSetForest(int size) {
		arr = new Element[size];
		for (int i = 0; i < size; i++) {
			arr[i] = new Element();
		}
	}
	
	@Override
	public void makeSet(int item) {
		arr[item].parent = item;
		arr[item].rank = 0;
	}

	@Override
	public int findSet(int item) {
		if (item != arr[item].parent) {
			arr[item].parent = findSet(arr[item].parent);
		}
		return arr[item].parent;
	}

	@Override
	public boolean union(int itemA, int itemB) {
		int rootA = findSet(itemA);
		int rootB = findSet(itemB);

		if (rootA == rootB) return false;

		if (arr[rootA].rank < arr[rootB].rank) {
			arr[rootA].parent = rootB;
		} else if (arr[rootA].rank > arr[rootB].rank) {
			arr[rootB].parent = rootA;
		} else {
			arr[rootA].parent = rootB;
			arr[rootB].rank++;
		}
		return true;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Disjoint sets as forest:\n");
		for (int i = 0; i < arr.length; i++) {
			sb.append(i).append(" -> ").append(arr[i].parent);
			if (i < arr.length - 1) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
