package dsaa.lab02;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<E> implements IList<E>{

	// Pojedynczy klocek naszej listy.
	// Przechowuje dane (object) oraz wskaźnik na kolejny węzeł w łańcuchu (next).
	private class Element{
		public Element(E e) {
			this.object=e;
		}
		E object;
		Element next=null;
	}
	
	Element sentinel;

	// ITERATOR DO PRZECHODZENIA PO LISCIE
	private class InnerIterator implements Iterator<E>{

		Element current;

		public InnerIterator() {
			// iterator zaczyna od pierwszego elementu
			this.current=sentinel.next;
		}
		@Override
		public boolean hasNext() {
			// zwracamy true dopóki mamy jakiekolwiek istniejace polaczenie
			return current != null;
		}
		
		@Override
		public E next() {
			// Najpierw sprawdzamy, czy istnieje kolejne elementy
			if (!hasNext()) throw new NoSuchElementException();

			// zapisujemy najpierw wartosc aktualnego klocka
			E value=current.object;
			// przesuwamy wskaznik na wartosc kolejnego klocka
			current=current.next;
			return value;
		}
	}
	
	public OneWayLinkedList() {
		// make a sentinel
		// inicjalizacja straznika, dzieki temu lista nigdy nie jest pusta
		// do tego latwiej implementowac metody i unikac wyjatkow
		sentinel=new Element(null);
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e) {
		// zaczynamy od strażnika i idziemy na sam koniec listy, do momentu, gdy next bedzie null
		Element current=sentinel;

		while(current.next!=null) {
			current=current.next;
		}

		// gdy jestesmy na ostatnim elemencie tworzymy nowy element i podpinamy go jako next
		current.next = new Element(e);
		return true;
	}

	@Override
	public void add(int index, E element) throws NoSuchElementException {
		// sprawdzamy czy index jest prawidlowy
		if (index < 0) throw new NoSuchElementException();

		Element current=sentinel;

		// Szukamy elementu przed miejscem wstawienia
		// np. gdy chcemy wstawić element na index 2 to musimy zatrzymac się na index 1
		for(int i=0;i<index;i++) {
			if (current.next == null) throw new NoSuchElementException();
			current=current.next;
		}

		// tworzymy nowy element i podpinamy go jako next
		Element newElement=new Element(element);
		newElement.next=current.next;
		current.next=newElement;
	}

	@Override
	public void clear() {
		// tutaj po prostu trzeba odciac wskaznik na kolejny element od straznika
		// reszta listy zostanie usunieta
		sentinel.next=null;
	}

	@Override
	public boolean contains(E element) {
		// dzięki metodzie indexOf, metoda contains robi się super prosta
		// trzeba sprawdzić czy index jest inny niz -1
		return indexOf(element) != -1;
	}

	@Override
	public E get(int index) throws NoSuchElementException {
		if (index < 0) throw new NoSuchElementException();

		// Tutaj nie musimy modyfikować wskaźników, więc zaczynamy podróż
		// od razu od pierwszego prawdziwego elementu (indeks 0)
		Element current = sentinel.next;

		for(int i = 0; i < index; i++) {
			if (current == null) throw new NoSuchElementException();
			current = current.next;
		}

		if (current == null) throw new NoSuchElementException();
		return current.object;
	}

	@Override
	public E set(int index, E element) throws NoSuchElementException {
		if (index < 0) throw new NoSuchElementException();

		Element curr = sentinel.next;

		for(int i = 0; i < index; i++) {
			if (curr == null) throw new NoSuchElementException();
			curr = curr.next;
		}

		if (curr == null) throw new NoSuchElementException();

		// Nadpisujemy wartość (obiekt) w znalezionym węźle,
		// ale musimy pamiętać o zwróceniu starej wartości z tej metody
		E oldValue = curr.object;
		curr.object = element;
		return oldValue;
	}

	@Override
	public int indexOf(E element) {
		Element current=sentinel.next;
		int index=0;

		// iterujemy po liscie element po elemencie,
		// trzeba użyć equals() zamiast opreatora ==
		while(current!=null) {
			if((current.object == null && element == null) ||
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
		// trzeba sprawdzić, czy za strażnikiem cokolwiek stoi
		return sentinel.next==null;
	}

	@Override
	public E remove(int index) throws NoSuchElementException {
		if (index < 0) throw new NoSuchElementException();

		Element current=sentinel;

		// podobnie jak przy dodawaniu, zatrzymujemy się przed elementem do usunięcia
		// i dzięki temu mamy dostęp do jego poprzednika i możemy zmodyfikować jego wskaźnik 'next'
		for(int i=0;i<index;i++) {
			if(current.next==null) throw new NoSuchElementException();
			current=current.next;
		}

		if(current.next==null) throw new NoSuchElementException(); // zabezpieczenie zeby index nie celowal w nulla

		E removedVal = current.next.object;
		// pomijamy usuwany węzeł. Poprzednik zaczyna wskazywać na następnika usuwanego węzła
		// a usunięty węzeł zostanie z czasem zniszczony
		current.next=current.next.next;
		return removedVal;
	}

	@Override
	public boolean remove(E e) {

		Element current=sentinel;

		// trzeba patrzeć o krok do przodu (current.next), żeby móc przepiąć wskaźnik obecnego elementu
		// tutaj trzeba użyć equals() aby porownac element z listy z argumentem do usuneicia

		while(current.next!=null) {
			// sprawdzamy czy argument jest null i czy obecny obiekt jest null
			// i czy obecny obiekt jest tym ktory chcemy usunac
			if((current.next.object == null && e == null) ||
					(current.next.object != null && current.next.object.equals(e))) {

				current.next=current.next.next; // odpinanie obecnego elementu
				return true;
			}
			// przeskok dalej aby sprawdzac kolejne elementy
			current=current.next;
		}
		// nie udało sie :c
		return false;
	}

	@Override
	public int size() {
		int length=0;
		Element current = sentinel.next;

		// iterujemy od pierwszego prawdziwego elementu aż do końca listy
		while(current!=null) {
			length++;
			current=current.next;
		}

		return length;
	}
	
}

