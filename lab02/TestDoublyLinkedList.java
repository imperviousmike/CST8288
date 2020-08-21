package datastructure.doublylinkedlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/*
 * Author: Michael Daly 040966083
 */
public class TestDoublyLinkedList {

	List<String> list;
	private final static String FIRST_ENTRY = "First";
	private final static String SECOND_ENTRY = "Second";
	private final static String THIRD_ENTRY = "Third";
	private final static String FOURTH_ENTRY = "Fourth";
	private final static String FIFTH_ENTRY = "Fifth";
	private int originalSize;

	@BeforeEach
	final void setUp() {
		list = new LinkedList<>();
		list.addAll(Arrays.asList(FIRST_ENTRY, SECOND_ENTRY, THIRD_ENTRY, FOURTH_ENTRY, FIFTH_ENTRY));
		originalSize = list.size();
	}

	@Test
	final void testCurrentSize() {
		final AtomicInteger counter = new AtomicInteger();
		list.forEach(a -> counter.getAndIncrement());
		assertEquals(list.size(), counter.get());
	}

	@Test
	final void testEmptySize() {
		list.clear();
		assertEquals(list.size(), 0);
	}

	@Test
	final void testAddSize() {
		list.add("Second");
		assertEquals(list.size(), originalSize + 1);
	}

	@Test
	final void testNullSize() {
		list = null;
		assertThrows(NullPointerException.class, () -> list.size());
	}

	@Test
	final void testClearFullList() {
		list.clear();
		assertEquals(list.size(), 0);
	}

	@Test
	final void testClearNullList() {
		list = null;
		assertThrows(NullPointerException.class, () -> list.clear());
	}

	@Test
	final void testContains() {
		Arrays.asList(FIRST_ENTRY, SECOND_ENTRY, THIRD_ENTRY, FOURTH_ENTRY, FIFTH_ENTRY).forEach(a -> list.contains(a));
	}

	@Test
	final void testNullContains() {
		list = null;
		assertThrows(NullPointerException.class, () -> list.contains(FIRST_ENTRY));
	}

	@Test
	final void testAddOne() {
		list.add("Add");
		// is the added entry in the list?
		assertTrue(list.contains("Add"));
		// has the size increased by one?
		assertEquals(list.size(), originalSize + 1);
		// is the entry at the end the newly added one?
		assertEquals(list.get(list.size() - 1), "Add");
	}

	@Test
	final void testAddAtIndex() {
		list.add(0, "Add");
		// is the added entry in the list?
		assertTrue(list.contains("Add"));
		// has the size increased by one?
		assertEquals(list.size(), originalSize + 1);
		// is the entry at the end the newly added one?
		assertEquals(list.get(0), "Add");
	}

	@Test
	final void testAddDuplicate() {
		int position = list.lastIndexOf(THIRD_ENTRY);
		list.add(position, THIRD_ENTRY);
		assertTrue(list.get(position) == THIRD_ENTRY);
		assertEquals(list.lastIndexOf(THIRD_ENTRY), position + 1);

	}

	@Test
	final void testAddOutofBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(list.size() + 1, "Add"));
	}

	@Test
	final void testRemoveAtIndex() {
		list.remove(0);
		assertTrue(list.size() == originalSize - 1);
		assertTrue(!list.contains(FIRST_ENTRY));

	}

	@Test
	final void testRemoveEntry() {
		list.remove(THIRD_ENTRY);
		assertTrue(list.size() == originalSize - 1);
		assertTrue(!list.contains(THIRD_ENTRY));

	}

	@Test
	final void testRemoveOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size() + 1));
	}

	@Test
	final void testRemoveNonExistantEntry() {
		list.remove("Test");
		assertTrue(list.size() == originalSize);
	}

	@Test
	final void testSetOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(originalSize + 1, FIRST_ENTRY));
	}

	@Test
	final void testSetAtIndex() {
		int index = list.lastIndexOf(FOURTH_ENTRY);
		list.set(index, SECOND_ENTRY);
		assertTrue(!list.contains(FOURTH_ENTRY));
		assertEquals(list.lastIndexOf(SECOND_ENTRY), index);
		assertEquals(list.get(index), SECOND_ENTRY);
	}

	@Test
	final void testSetNull() {
		assertThrows(NullPointerException.class, () -> list.set((Integer) null, FIRST_ENTRY));
	}

}
