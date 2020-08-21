package datastructure.doublylinkedlist;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

public class DoublyLinkedList<R> implements List<R>, Iterable<R> {

	private Node<R> head, tail;
	private int size;

	public DoublyLinkedList() {
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(Object o) {
		for (int i = 0; i < size(); i++) {
			R temp = get(i);
			if ((temp != null && temp.equals(o)) || (temp == null && o == null)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean add(R e) {
		int originalSize = size;
		add(size, e);
		if (size == originalSize + 1) {
			return true;
		}
		return false;
	}

	@Override
	public void add(int index, R element) {
		if (index != 0 && index != size)
			if (isInvalidIndex(index))
				throw new IndexOutOfBoundsException();

		Node<R> newNode = new Node<R>(element, null, null);
		if (index == 0) {
			if (head != null) {
				newNode.next = head;
				head.prev = newNode;
				head = newNode;
			} else {
				head = newNode;
			}
			size++;
		} else if (index == size) {
			if (tail != null) {
				newNode.prev = tail;
				tail.next = newNode;
				tail = newNode;
			} else {
				tail = newNode;
				tail.prev = head;
				head.next = tail;
			}
			size++;
		} else {
			Node<R> currentNode = getNode(index);
			newNode.next = currentNode;
			newNode.prev = currentNode.prev;
			currentNode.prev.next = newNode;
			currentNode.prev = newNode;
			size++;
		}

	}

	@Override
	public R remove(int index) {
		if (isInvalidIndex(index)) {
			throw new IndexOutOfBoundsException();
		}
		Node<R> nodeToRemove = head;
		for (int i = 0; i < size(); i++) {
			if (i == index) {
				if (i == 0) {
					head = head.next;
					if (head != null)
						head.prev = null;
				}
			} else if (i == size - 1) {
				tail = tail.prev;
				tail.next = null;
			} else {
				nodeToRemove.prev.next = nodeToRemove.next;
				nodeToRemove.next.prev = nodeToRemove.prev;
			}
			size--;
			return nodeToRemove.value;
		}
		nodeToRemove = nodeToRemove.next;
		return nodeToRemove.value;
	}

	@Override
	public boolean remove(Object o) {
		for (int i = 0; i < size; i++) {
			R temp = get(i);
			if ((temp != null && temp.equals(o)) || (temp == null && o == null)) {
				remove(i);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		int originalSize = size;
		c.forEach(a -> remove(a));
		return (size == originalSize - c.size()) ? true : false;
	}

	@Override
	public boolean addAll(Collection<? extends R> c) {
		int originalSize = size();
		c.forEach(a -> add(a));
		if (size == originalSize + c.size()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends R> c) {
		if (isInvalidIndex(index)) {
			throw new IndexOutOfBoundsException();
		}
		int originalSize = size;
		final AtomicInteger counter = new AtomicInteger(index);
		c.forEach(a -> {
			add(counter.intValue(), a);
			counter.getAndIncrement();
		});

		if (size == originalSize + c.size()) {
			return true;
		}

		return false;
	}

	@Override
	public void clear() {
		size = 0;
		head = tail = null;

	}

	@Override
	public R get(int index) {
		if (isInvalidIndex(index)) {
			throw new IndexOutOfBoundsException();
		}
		return getNode(index).value;
	}

	private boolean isInvalidIndex(int index) {
		return (index >= size || index < 0);
	}

	private Node<R> getNode(int index) {
		Node<R> node = head;
		for (int i = 0; i < size; i++) {
			if (i == index) {
				return node;
			}
			node = node.next;
		}
		return null;
	}

	@Override
	public Iterator<R> iterator() {
		return new DLLIterator();
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		Node<R> node = head;
		for (int i = 0; i < size; i++) {
			array[i] = node.value;
			node = node.next;
		}
		return array;

	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		if (a.length < size) {
			a = (T[]) new Object[size];
		}
		for (int i = 0; i < a.length; i++) {
			a[i] = (T) getNode(i).value;
		}
		return a;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		int count = 0;
		int originalSize = size;
		for (int i = 0; i < size; i++) {
			Node<R> node = getNode(i);
			if (!c.contains(node.value)) {
				remove(node.value);
				count++;
			}
		}
		if (size == originalSize - count) {
			return true;
		}
		return false;
	}

	@Override
	public R set(int index, R element) {
		if (isInvalidIndex(index)) {
			throw new IndexOutOfBoundsException();
		}
		return getNode(index).value = (R) element;
	}

	@Override
	public int indexOf(Object o) {
		int index = -1;
		for (int i = 0; i < size; i++) {
			R temp = get(i);
			if ((temp != null && temp.equals(o)) || (temp == null && o == null)) {
				return i;
			}
		}
		return index;
	}

	@Override
	public int lastIndexOf(Object o) {
		int index = -1;
		for (int i = 0; i < size; i++) {
			R temp = get(i);
			if ((temp != null && temp.equals(o)) || (temp == null && o == null)) {
				index = i;
			}
		}
		return index;
	}

	@Override
	public ListIterator<R> listIterator() {
		return null;
	}

	@Override
	public ListIterator<R> listIterator(int index) {
		return null;
	}

	@Override
	public List<R> subList(int fromIndex, int toIndex) {
		if (isInvalidIndex(fromIndex) || isInvalidIndex(toIndex) || toIndex < fromIndex) {
			throw new IndexOutOfBoundsException();
		}
		List<R> list = new LinkedList<R>();
		while (fromIndex < toIndex) {
			getNode(fromIndex);
			list.add(getNode(fromIndex++).value);
		}
		return list;
	}

	private class Node<E> {
		private E value;
		private Node<E> next, prev;

		private Node(E value) {
			this.value = value;
		}

		private Node(Node<E> next, Node<E> prev) {
			this.next = next;
			this.prev = prev;
		}

		private Node(E value, Node<E> next, Node<E> prev) {
			this.value = value;
			this.next = next;
			this.prev = prev;
		}

	}

	private class DLLIterator implements Iterator<R> {

		private Node<R> current;

		public DLLIterator() {

			current = head;

		}

		@Override
		public boolean hasNext() {

			return current != null;

		}

		@Override
		public R next() {

			Node<R> node = current;

			current = current.next;

			return node.value;

		}

	}

}
