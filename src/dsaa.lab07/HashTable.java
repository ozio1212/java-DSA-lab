package dsaa.lab07;

import java.util.LinkedList;

public class HashTable{
	LinkedList arr[]; // use pure array
	private final static int defaultInitSize=8;
	private final static double defaultMaxLoadFactor=0.7;
	private int size;	
	private final double maxLoadFactor;

	public HashTable() {
		this(defaultInitSize);
	}

	public HashTable(int size) {
		this(size,defaultMaxLoadFactor);
	}

	public HashTable(int initCapacity, double maxLF) {
		this.maxLoadFactor=maxLF;
		this.size = 0;
		this.arr = new LinkedList[initCapacity];

		for (int i = 0; i<initCapacity; i++){
			this.arr[i] = new LinkedList();
		}
	}

	public boolean add(Object elem) {

		int hashIndex = Math.abs(elem.hashCode()) % arr.length;

		LinkedList list = arr[hashIndex];

		if (list.contains(elem)){
			return false;
		}

		list.add(elem);
		size++;

		double currentLoadFactor = (double) size / arr.length;
		if (currentLoadFactor > maxLoadFactor) {
			doubleArray();
		}

		return true;
	}

	
	private void doubleArray() {
		int newCapacity = arr.length * 2;
		LinkedList[] newArr = new LinkedList[newCapacity];

		for (int i = 0; i < newCapacity; i++){
			newArr[i] = new LinkedList();
		}

		for (int i = 0; i < arr.length; i++){
			for (Object e : arr[i]){
				int hashIndex = Math.abs(e.hashCode()) % newCapacity;
				newArr[hashIndex].add(e);
			}
		}

		this.arr = newArr;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < arr.length; i++){
			sb.append(i).append(":");

			if (!arr[i].isEmpty()){
				sb.append(" ");
				boolean isFirst = true;

				for (Object elem : arr[i]){
					if (!isFirst){
						sb.append(", ");
					}

					IWithName doc = (IWithName) elem;
					sb.append(doc.getName());

					isFirst=false;
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	public Object get(Object toFind) {
		int hashIndex = Math.abs(toFind.hashCode()) % arr.length;
		LinkedList list = arr[hashIndex];

		for (Object elem : list) {
			if (elem.equals(toFind)) {
				return elem;
			}
		}
		return null;
	}
	
}

