package dsaa.lab11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.SortedMap;

public class Graph {
	int arr[][];
	HashMap<String, Integer> name2Int;

	@SuppressWarnings("unchecked")
	Entry<String, Document>[] arrDoc = (Map.Entry<String, Document>[]) new Map.Entry[0];

	public Graph(SortedMap<String, Document> internet) {
		int size = internet.size();
		arr = new int[size][size];
		name2Int = new HashMap<>();
		arrDoc = (Map.Entry<String, Document>[]) new Map.Entry[size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j) arr[i][j] = 0;
				else arr[i][j] = -1;
			}
		}

		int index = 0;
		for (Map.Entry<String, Document> entry : internet.entrySet()) {
			name2Int.put(entry.getKey(), index);
			arrDoc[index] = entry;
			index++;
		}

		for (int i = 0; i < size; i++) {
			Document doc = arrDoc[i].getValue();
			for (Link l : doc.link.values()) {
				if (name2Int.containsKey(l.ref)) {
					int j = name2Int.get(l.ref);
					arr[i][j] = l.weight;
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


	public String DijkstraSSSP(String startVertexStr) {
		// jeśli nie ma takiego dokumentu, zwracamy null
		if (!name2Int.containsKey(startVertexStr)) return null;

		int start = name2Int.get(startVertexStr);
		int V = arr.length;

		// struktury pomocnicze algorytmu
		int[] dist = new int[V];    // przechowuje najkrótszy dystans z wierzchołka startowego
		int[] prev = new int[V];    // przechowuje indeks poprzednika (do odtwarzania ścieżki)
		boolean[] visited = new boolean[V]; // zaznacza, dla których wierzchołków znamy już ostateczny dystans

		// inicjalizacja wartości początkowych
		for (int i = 0; i < V; i++) {
			dist[i] = Integer.MAX_VALUE; // na początku wszystko jest w odległości nieskończoności
			prev[i] = -1;                // nikt nie ma poprzednika
		}
		dist[start] = 0; // odległość z wierzchołka startowego do niego samego to 0

		// główna pętla
		for (int count = 0; count < V; count++) {

			// 3a. ZACHŁANNE SZUKANIE: Znajdź nieodwiedzony wierzchołek z NAJMNIEJSZYM dystansem
			int u = -1;
			int minDistance = Integer.MAX_VALUE;

			for (int i = 0; i < V; i++) {
				// Skoro iterujemy 'i' od 0 do V, automatycznie przestrzegamy zasady PORZĄDKU LEKSYKOGRAFICZNEGO,
				// gdyby istniały dwa węzły o takim samym minimalnym dystansie.
				if (!visited[i] && dist[i] < minDistance) {
					minDistance = dist[i];
					u = i;
				}
			}

			// Jeśli u == -1, oznacza to, że nie ma już żadnych osiągalnych wierzchołków
			// (reszta grafu to oddzielna "wyspa"). Możemy zakończyć poszukiwania.
			if (u == -1) break;

			// 3b. Odwiedzamy znaleziony wierzchołek
			visited[u] = true;

			// 3c. RELAKSACJA: Aktualizujemy odległości do wszystkich sąsiadów wierzchołka 'u'
			for (int v = 0; v < V; v++) {
				// Rozpatrujemy węzeł 'v' tylko jeśli:
				// - nie był odwiedzony (!visited[v])
				// - krawędź u -> v istnieje (arr[u][v] >= 0)
				// - odległość do 'u' nie jest nieskończonością (co gwarantujemy wybierając 'u' wyżej)
				if (!visited[v] && arr[u][v] >= 0 && dist[u] != Integer.MAX_VALUE) {
					// Jeśli droga przez 'u' jest KRÓTSZA niż bezpośrednio / stara znana droga do 'v'
					if (dist[u] + arr[u][v] < dist[v]) {
						dist[v] = dist[u] + arr[u][v]; // Aktualizuj minimalny dystans
						prev[v] = u;                   // Zapamiętaj, że do 'v' najlepiej dojść z 'u'
					}
				}
			}
		}

		// 4. budowanie wyniku tekstowego do fancy printa
		StringBuilder sb = new StringBuilder();

		// iterujemy po kolei (w porządku leksykograficznym)
		for (int i = 0; i < V; i++) {
			if (i == start) {
				// zwykłe wypisanie dla wierzchołka startowego
				sb.append(arrDoc[start].getKey()).append("=0\n");
			} else if (dist[i] == Integer.MAX_VALUE) {
				// dystans nieskonczonosc
				sb.append("no path to ").append(arrDoc[i].getKey()).append("\n");
			} else {
				// odtwarzanie ścieżki tyłem (od celu do startu)
				List<String> path = new ArrayList<>();
				int curr = i;
				while (curr != -1) {
					path.add(arrDoc[curr].getKey());
					curr = prev[curr];
				}
				// odwrocenie
				Collections.reverse(path);

				sb.append(String.join("->", path))
						.append("=")
						.append(dist[i])
						.append("\n");
			}
		}

		return sb.toString();
	}
}