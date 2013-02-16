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
import static org.testng.Assert.assertTrue;

import java.util.Set;

import org.testng.annotations.Test;

import com.google.common.collect.Sets;

/**
 * Tests for Hierarchies.
 * @author Andres Rodriguez
 */
public class HierarchiesTest {
	private static ImmutableHierarchy.Builder<Integer, Integer> builder() {
		return ImmutableHierarchy.builder();
	}

	/**
	 * Simple.
	 */
	@Test
	public void simple() {
		final Hierarchy<Integer, Integer> h = builder().add(1, 1, null).add(2, 2, null).add(3, 3, null).add(11, 11, 1)
				.get();
		final Set<Integer> keys = Sets.newHashSet(h.keySet());
		assertEquals(keys.size(), 4);
		final HierarchyVisitor<Integer, Integer> visitor = new HierarchyVisitor<Integer, Integer>() {
			public void visit(Integer key, Integer value, Integer parentKey, int position) {
				assertTrue(keys.contains(key));
				keys.remove(key);
			}
		};
		Hierarchies.visitDepthFirst(h, visitor);
		assertTrue(keys.isEmpty());
	}
}
