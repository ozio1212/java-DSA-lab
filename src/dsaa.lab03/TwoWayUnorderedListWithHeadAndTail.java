package dsaa.lab03;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class TwoWayUnorderedListWithHeadAndTail<E> implements IList<E>{
	
	private class Element{

		public Element(E e) {
			this.object=e;
		}

		public Element(E e, Element next, Element prev) {
			this.object=e;
			this.next=next;
			this.prev=prev;
		}

		E object;
		Element next=null;
		Element prev=null;
	}
	
	Element head;
	Element tail;
	// używam size
	int size = 0;
	
	private class InnerIterator implements Iterator<E>{
		Element pos;
		
		public InnerIterator() {
			// poczatek od glowy listy
			this.pos=head;
		}

		@Override
		public boolean hasNext() {
			return pos!=null;
		}
		
		@Override
		public E next() {
			if (!hasNext()) throw new java.util.NoSuchElementException();
			E value = pos.object;
			pos=pos.next;
			return value;
		}
	}
	
	private class InnerListIterator implements ListIterator<E>{
		Element p = head;

		@Override
		public boolean hasNext() {
			return p!=null;
		}

		@Override
		public boolean hasPrevious() {
			if (p != null) {
				return p.prev != null;
			} else {
				return tail != null;
			}
		}

		@Override
		public E next() {
			if (!hasNext()) throw new java.util.NoSuchElementException();
			E value = p.object;
			p=p.next;
			return value;
		}


		@Override
		public E previous() {
			if (!hasPrevious()) throw new java.util.NoSuchElementException();

			if (p==null){
				p = tail; // jak za lista to wracamy na ogon
			} else {
				p=p.prev; // cofniecie
			}

			return p.object;
		}

		@Override
		public void add(E e) {throw new UnsupportedOperationException();}

		@Override
		public int nextIndex() {throw new UnsupportedOperationException();}

		@Override
		public int previousIndex() {throw new UnsupportedOperationException();}

		@Override
		public void remove() {throw new UnsupportedOperationException();}

		@Override
		public void set(E e) {throw new UnsupportedOperationException();}
	}
	
	public TwoWayUnorderedListWithHeadAndTail() {
		// make a head and a tail	
		head=null;
		tail=null;
	}
	
	@Override
	public boolean add(E e) {
		Element newElement = new Element(e, null, tail);

		if (tail!=null){
			tail.next=newElement;
		} else {
			head = newElement;
		}
		tail=newElement;
		size++;
		return true;
	}

	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size) {
			throw new NoSuchElementException();
		}

		if (index == size) {
			add(element);
			return;
		}

		if (index == 0){
			Element newElement = new Element(element, head, null);
			head.prev = newElement;
			head = newElement;
			size++;
		} else {
			Element current = getElement(index);
			Element newElement = new Element(element, current, current.prev);
			current.prev.next = newElement;
			current.prev = newElement;
			size++;
		}
	}

	@Override
	public void clear() {
		head = null;
		tail = null;
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
	public E set(int index, E element) {
		Element current = getElement(index);
		E oldValue = current.object;
		current.object = element;
		return oldValue;
	}

	@Override
	public int indexOf(E element) {
		Element current = head;
		int index = 0;
		while (current!=null) {
			if ((current.object == null && element == null) ||
					(current.object != null && current.object.equals(element))) {
				return index;
			}
			current = current.next;
			index++;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return head==null;
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
		unlink(current);
		return current.object;
	}

	@Override
	public boolean remove(E e) {
		Element current = head;
		while (current!=null) {
			if ((current.object == null && e == null) ||
					(current.object != null && current.object.equals(e))) {
				unlink(current);
				return true;
			}
			current = current.next;
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	public String toStringReverse() {
		ListIterator<E> iter=new InnerListIterator();

		while(iter.hasNext()) {
			iter.next();
		}

		StringBuilder sb = new StringBuilder();
		while(iter.hasPrevious()) {
			sb.append("\n").append(iter.previous().toString());
		}
		return sb.toString();
	}

	public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {
		// zabezpiecznie przed dodawaniem tych samych list
		if (this == other || other.isEmpty()){
			return;
		}

		if (this.isEmpty()) {
			this.head=other.head;
			this.tail=other.tail;
		} else {
			// podlaczenie niepustych list
			this.tail.next=other.head;
			other.head.prev=this.tail;
			this.tail=other.tail;
		}

		this.size += other.size;
		other.clear();
	}

	private Element getElement(int index) {
		if (index < 0 || index >= size) {
			throw new NoSuchElementException();
		}

		Element current = head;

		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		return current;
	}

	private void unlink(Element current) {
		if (current.prev!=null) {
			current.prev.next=current.next;
		} else {
			head = current.next;
		}

		if (current.next!=null) {
			current.next.prev=current.prev;
		} else {
			tail = current.prev;
		}
		size--;
	}

    public void moveEnd(int n){
        
        if (n<0 || n>=size || isEmpty()) {
            throw new NoSuchElementException();
        }

        TwoWayUnorderedListWithHeadAndTail<E> temp = new TwoWayUnorderedListWithHeadAndTail<>();

        // nowa lista z tymi pierwszymi n-elementami i odciecie ich od pierwotnej listy
        for (int i = 0; i < n; i++) {
            temp.add(0,this.remove(0));
        }

        // dodanie do pierwotnej listy nowej odworocnej
        this.add(temp);
    }


}

