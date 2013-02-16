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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

/**
 * Utility methods for hierchies.
 * @author Andres Rodriguez
 */
public final class Hierarchies {
	private Hierarchies() {
		throw new AssertionError();
	}

	/**
	 * Traverses a hierarchy depth-first.
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param hierarchy Hierarchy to traverse.
	 * @param visitor Visitor.
	 */
	public static <K, V> void visitDepthFirst(Hierarchy<K, V> hierarchy, HierarchyVisitor<? super K, ? super V> visitor) {
		checkNotNull(hierarchy);
		checkNotNull(visitor);
		visitDepthFirst(hierarchy, null, hierarchy.getFirstLevelKeys(), visitor);
	}

	/**
	 * Internal traversal method.
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param hierarchy Hierarchy to traverse.
	 * @param parentKey Current parent key.
	 * @param keys Children of the current parent key.
	 * @param visitor Visitor.
	 */
	private static <K, V> void visitDepthFirst(Hierarchy<K, V> hierarchy, K parentKey, List<K> keys,
			HierarchyVisitor<? super K, ? super V> visitor) {
		int i = 0;
		for (final K key : keys) {
			visitor.visit(key, hierarchy.get(key), parentKey, i++);
			visitDepthFirst(hierarchy, parentKey, hierarchy.getChildrenKeys(key), visitor);
		}

	}
}
