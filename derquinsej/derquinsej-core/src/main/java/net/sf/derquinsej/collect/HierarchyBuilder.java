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

import com.google.common.base.Supplier;


/**
 * Interface for out-of order builder for hierarchies.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the elements.
 */
public interface HierarchyBuilder<K, V> extends Supplier<Hierarchy<K, V>> {
	/**
	 * Adds a new element to the hierarchy.
	 * @param key Key to add.
	 * @param value Value to add.
	 * @param parentKey Key of the parent element, or {@code null} if it is
	 *            a first level node.
	 * @return This builder.
	 * @throws NullPointerException if key or element are {@code null}.
	 * @throws IllegalArgumentException if the node has been previously
	 *             added.
	 * @throws IllegalStateException if a loop is detected.
	 */
	HierarchyBuilder<K, V> add(K key, V value, K parentKey);

	/**
	 * Adds a branch of another hierarchy new element to the hierarchy.
	 * @param hierarchy Original hierarchy.
	 * @param key Key of the original hierarchy that is the root of
	 *            the branch. If {@code null} the whole hierarchy is added.
	 * @param includeRoot If the root is to be included in the hierarchy.
	 * @param parentKey Key of the parent element, or {@code null} if it is
	 *            a first level node.
	 * @return This builder.
	 * @throws NullPointerException if hierarchy is {@code null}.
	 * @throws IllegalArgumentException if the provided element is not part
	 *             of the provided hierarchy.
	 * @throws IllegalArgumentException if a node of the provided hierarchy
	 *             has been previously added.
	 * @throws IllegalStateException if a loop is detected.
	 */
	HierarchyBuilder<K, V> add(Hierarchy<K, V> hierarchy, K key, boolean includeRoot, K parentKey);

	/**
	 * Builds and returns an immutable hierarchy with the nodes added up to
	 * the method call.
	 * @returns An hierarchy.
	 * @throws IllegalStateException if there are referenced parents that
	 *             are not part of the hierarchy yet.
	 */
	Hierarchy<K, V> get();
}
