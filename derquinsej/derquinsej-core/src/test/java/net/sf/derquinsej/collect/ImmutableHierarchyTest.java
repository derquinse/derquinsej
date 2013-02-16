/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.derquinsej.collect;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.testng.annotations.Test;

/**
 * Tests for ImmutableHierarchy.
 * @author Andres Rodriguez
 */
public class ImmutableHierarchyTest {
	private static ImmutableHierarchy.Builder<Integer, Integer> builder() {
		return ImmutableHierarchy.builder();
	}

	/**
	 * Empty hierarchy.
	 */
	@Test
	public void empty() {
		Hierarchy<Integer, Integer> h = builder().get();
		assertNotNull(h);
		assertNotNull(h.getFirstLevel());
		assertTrue(h.getFirstLevel().isEmpty());
		assertFalse(h.containsValue(1));
		assertFalse(h.containsKey(1));
	}

	/**
	 * Null contains.
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void nullContains() {
		Hierarchy<Integer, Integer> h = builder().get();
		h.containsValue(null);
	}

	/**
	 * Null contains key.
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void nullContainsKey() {
		Hierarchy<Integer, Integer> h = builder().get();
		h.containsKey(null);
	}

	/**
	 * Not found.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void notFound1() {
		builder().get().getChildren(1);
	}

	/**
	 * Not found.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void notFound2() {
		builder().get().getChildrenKeys(1);
	}

	/**
	 * Not found.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void notFound3() {
		builder().get().getParent(1);
	}

	/**
	 * Not found.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void notFound4() {
		builder().get().getParentKey(1);
	}

	private void check(Hierarchy<Integer, Integer> h, Integer yes, Integer no) {
		assertNotNull(h);
		assertTrue(h.containsValue(yes));
		assertTrue(h.containsKey(yes));
		assertFalse(h.containsValue(no));
		assertFalse(h.containsKey(no));
	}

	private void checkList(List<Integer> list, int size, Integer yes, Integer no) {
		assertNotNull(list);
		assertTrue(list.size() == size);
		assertTrue(list.contains(yes));
		assertFalse(list.contains(no));
	}

	/**
	 * One element.
	 */
	@Test
	public void one() {
		Hierarchy<Integer, Integer> h = builder().add(1, 1, null).get();
		check(h, 1, 2);
		checkList(h.getFirstLevel(), 1, 1, 2);
	}

	/**
	 * Duplicate element.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void dup1() {
		builder().add(1, 1, null).add(1, 1, null);
	}

	/**
	 * Duplicate element.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void dup2() {
		builder().add(1, 1, null).add(new Integer(1), 1, null);
	}

	private void checkTwo(ImmutableHierarchy.Builder<Integer, Integer> builder) {
		final Hierarchy<Integer, Integer> h = builder.get();
		check(h, 1, 3);
		check(h, 2, 3);
		checkList(h.getFirstLevel(), 1, 1, 2);
		checkList(h.getChildrenKeys(1), 1, 2, 1);
		checkList(h.getChildren(1), 1, 2, 1);
		assertEquals(h.getParent(2), Integer.valueOf(1));
		assertEquals(h.getParentKey(2), Integer.valueOf(1));
	}

	/**
	 * Two elements in order.
	 */
	@Test
	public void twoIO() {
		checkTwo(builder().add(1, 1, null).add(2, 2, 1));
	}

	/**
	 * Two elements out of order.
	 */
	@Test
	public void twoOO() {
		checkTwo(builder().add(2, 2, 1).add(1, 1, null));
	}

	/**
	 * Unadded element.
	 */
	@Test(expectedExceptions = IllegalStateException.class)
	public void unadded() {
		builder().add(2, 2, 1).get();
	}

	/**
	 * Loop.
	 */
	@Test(expectedExceptions = IllegalStateException.class)
	public void loop1() {
		builder().add(2, 2, 1).add(1, 1, 2);
	}

	/**
	 * Loop.
	 */
	@Test(expectedExceptions = IllegalStateException.class)
	public void loop2() {
		builder().add(2, 2, 1).add(3, 3, 2).add(4, 4, 3).add(1, 1, 4);
	}

	/**
	 * Descendants.
	 */
	@Test
	public void descendants() {
		final Hierarchy<Integer, Integer> h = builder().add(1, 1, null).add(2, 2, 1).add(3, 3, 2).add(4, 4, 3).add(5, 5, 3).get();
		Collection<Integer> of1 = h.getDescendants(1);
		assertFalse(of1.contains(1));
		assertTrue(of1.contains(2));
		assertTrue(of1.contains(3));
		assertTrue(of1.contains(4));
		assertTrue(of1.contains(5));
		Collection<Integer> of3 = h.getDescendants(3);
		assertFalse(of3.contains(1));
		assertFalse(of3.contains(2));
		assertFalse(of3.contains(3));
		assertTrue(of3.contains(4));
		assertTrue(of3.contains(5));
	}

	/**
	 * Descendants.
	 */
	@Test
	public void parent() {
		final Hierarchy<Integer, Integer> h = builder().add(1, 1, null).add(2, 2, 1).add(3, 3, 2).add(4, 4, 3).add(5, 5, 3).get();
		assertNull(h.getParentKey(1));
		assertNull(h.getParent(1));
		assertEquals(h.getParent(2), Integer.valueOf(1));
		assertEquals(h.getParent(3), Integer.valueOf(2));
		assertEquals(h.getParent(4), Integer.valueOf(3));
		assertEquals(h.getParent(5), Integer.valueOf(3));
	}
	
	/**
	 * Subhierarchy.
	 */
	@Test
	public void subhierarchy() {
		final Hierarchy<Integer, Integer> h = builder().add(1, 1, null).add(2, 2, 1).add(3, 3, 2).add(4, 4, 3).add(5, 5, 3).get();
		final Hierarchy<Integer, Integer> h2 = h.getSubhierarchy(3, true);
		assertFalse(h2.containsKey(1));
		assertFalse(h2.containsKey(2));
		assertTrue(h2.containsKey(3));
		assertTrue(h2.containsKey(4));
		assertTrue(h2.containsKey(5));
	}


}
