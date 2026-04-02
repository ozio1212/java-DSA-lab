package dsaa.lab04;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E>{

	private class Element{
		public Element(E e) {
			this.object = e;
		}
		public Element(E e, Element next, Element prev) {
			this.object = e;
			this.next = next;
			this.prev = prev;
		}
		// add element e after this
		public void addAfter(Element elem) {
			elem.next = this.next;
			elem.prev = this;
			this.next.prev = elem;
			this.next = elem;
		}
		// assert it is NOT a sentinel
		public void remove() {
			if (this == sentinel){
				return;
			}
			this.prev.next = this.next;
			this.next.prev = this.prev;
		}

		E object;
		Element next=null;
		Element prev=null;
	}

	// pola listy
	Element sentinel;
	int size;

	private class InnerIterator implements Iterator<E>{

		Element pos;

		public InnerIterator() {
			this.pos = sentinel.next;
		}

		@Override
		public boolean hasNext() {
			return pos != sentinel;
		}

		@Override
		public E next() {
			if (!hasNext()) throw new NoSuchElementException();
			E value = pos.object;
			pos = pos.next;
			return value;
		}
	}

	private class InnerListIterator implements ListIterator<E>{

		Element current;

		public InnerListIterator() {
			this.current = sentinel;
		}

		@Override
		public boolean hasNext() {
			return current != sentinel;
		}

		@Override
		public E next() {
			if (!hasNext()) throw new NoSuchElementException();
			E value = current.object;
			current = current.next;
			return value;
		}

		@Override
		public void add(E arg0) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasPrevious() {
			return current.prev != sentinel;
		}

		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E previous() {
			if (!hasPrevious()) throw new NoSuchElementException();
			current = current.prev;
			return current.object;
		}

		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		@Override
		public void set(E arg0) {
			throw new UnsupportedOperationException();
		}
	}

	public TwoWayCycledOrderedListWithSentinel() {
		sentinel = new Element(null);
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		size = 0;
	}

	//@SuppressWarnings("unchecked")
	@Override
	public boolean add(E e) {
		//TODO
		return false;
	}

	private Element getElement(int index) {
		if (index < 0 || index >= size) {
			throw new NoSuchElementException();
		}
		Element current = sentinel.next;
		for (int i  = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}

	private Element getElement(E obj) {
		Element current = sentinel.next;

		while (current != sentinel) {
			if (current.object.equals(obj)) {
				return current;
			}
			current = current.next;
		}
		return null;
	}

	@Override
	public void add(int index, E element) {throw new UnsupportedOperationException();}

	@Override
	public void clear() {
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		size = 0;
	}

	@Override
	public boolean contains(E element) {
		return indexOf(element) != -1;
	}

	@Override
	public E get(int index) {
		return getElement(index).object;
	}

	@Override
	public E set(int index, E element) {throw new UnsupportedOperationException();}

	@Override
	public int indexOf(E element) {
		Element current = sentinel.next;
		int index = 0;

		while (current != sentinel) {
			if(current.object.equals(element)) {
				return index;
			}
			current = current.next;
			index++;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new InnerListIterator();
	}

	@Override
	public E remove(int index) {
		Element current = getElement(index);
		current.remove();
		size--;
		return current.object;
	}

	@Override
	public boolean remove(E e) {
		Element current = getElement(e);
		if (current != null){
			current.remove();
			size--;
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	//@SuppressWarnings("unchecked")
	public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
		//TODO
	}
	
	//@SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeAll(E e) {
		//TODO
	}

}

