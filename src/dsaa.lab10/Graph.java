package dsaa.lab10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;

public class Graph {
	int arr[][];
	//TODO? Collection to map Document to index of vertex 
	// You can change it
	HashMap<String,Integer> name2Int;
	@SuppressWarnings("unchecked")
	//TODO? Collection to map index of vertex to Document
	// You can change it
	Entry<String, Document>[] arrDoc=(Map.Entry<String, Document>[])new Map.Entry[0];
	
	// The argument type depend on a selected collection in the Main class
	public Graph(SortedMap<String,Document> internet){
		int size=internet.size();
		arr=new int[size][size];

		name2Int = new HashMap<>();
		arrDoc = (Map.Entry<String, Document>[]) new Map.Entry[size];

		// 1. inicjalizacja macierzy, -1 oznacza brak krawędzi.
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j) arr[i][j] = 0;
				else arr[i][j] = -1;
			}
		}

		// 2. mapowanie nazwa -> index.
		int index = 0;
		for (Map.Entry<String, Document> entry : internet.entrySet()) {
			name2Int.put(entry.getKey(), index);
			arrDoc[index] = entry;
			index++;
		}

		// 3. wypełnianie macierzy sąsiedztwa
		for (int i = 0; i < size; i++) {
			Document doc = arrDoc[i].getValue();
			for (Link l : doc.link.values()) {
				if (name2Int.containsKey(l.ref)) {
					int j = name2Int.get(l.ref);
					arr[i][j] = l.weight; // waga krawędzi
				}
			}
		}
	}
	
	public String bfs(String start) {
		if (!name2Int.containsKey(start)) return null;

		int startIndex = name2Int.get(start);
		boolean[] visited = new boolean[arr.length];
		Queue<Integer> queue = new LinkedList<>();
		StringBuilder sb = new StringBuilder();

		queue.add(startIndex);
		visited[startIndex] = true;

		while (!queue.isEmpty()) {
			int curr = queue.poll();
			sb.append(arrDoc[curr].getKey()).append(", ");

			for (int j = 0; j < arr.length; j++) {
				if (curr != j && arr[curr][j] >= 0 && !visited[j]) {
					visited[j] = true;
					queue.add(j);
				}
			}
		}

		// Usuwamy ostatni przecinek i spację
		return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
	}

	public String dfs(String start) {
		if (!name2Int.containsKey(start)) return null;

		int startIndex = name2Int.get(start);
		boolean[] visited = new boolean[arr.length];
		StringBuilder sb = new StringBuilder();

		dfsRecursive(startIndex, visited, sb);

		return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
	}

	private void dfsRecursive(int curr, boolean[] visited, StringBuilder sb) {
		visited[curr] = true;
		sb.append(arrDoc[curr].getKey()).append(", ");

		for (int j = 0; j < arr.length; j++) {
			if (curr != j && arr[curr][j] >= 0 && !visited[j]) {
				dfsRecursive(j, visited, sb);
			}
		}
	}

	public int connectedComponents() {
		DisjointSetForest dsf = new DisjointSetForest(arr.length);

		for (int i = 0; i < arr.length; i++) {
			dsf.makeSet(i);
		}

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (i != j && arr[i][j] >= 0) {
					dsf.union(i, j);
				}
			}
		}

		return dsf.countSets();
	}
}
