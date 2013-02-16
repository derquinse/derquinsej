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

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * A hierarchy is a set of objects identified by a key organized in a filesystem
 * tree-like structure. Each level is ordered. Neither null keys nor Null values
 * are allowed.
 * @author Andres Rodriguez
 * @param <K> Type of keys.
 * @param <V> Type of values.
 */
public interface Hierarchy<K, V> {
	/**
	 * Returns a set view of the keys contained in this hierarchy.
	 * @return A set view of the keys contained in this hierarchy.
	 */
	Set<K> keySet();

	/**
	 * Returns all the values of the hierarchy. The iteration order is
	 * unspecified.
	 * @return All the values of the hierarchy.
	 */
	Collection<V> values();

	/**
	 * Returns the first level of the hierarchy. That is, the values that have
	 * no parent.
	 * @return The list of values with no parent.
	 */
	List<V> getFirstLevel();

	/**
	 * Returns the keys of the first level of the hierarchy. That is, the keys that have
	 * no parent.
	 * @return The list of keys with no parent.
	 */
	List<K> getFirstLevelKeys();
	
	/**
	 * Returns the children of the specified key.
	 * @param key The key of the value which children are requested.
	 * @return The list of children, the empty list if it has no children.
	 * @throws NullPointerException if the specified key is {@code null}.
	 * @throws IllegalArgumentException if the specified element is not part of
	 *             the hierarchy.
	 */
	List<V> getChildren(K key);

	/**
	 * Returns the keys of the children of the specified key.
	 * @param key The key of the value which children are requested.
	 * @return The list of children keys, the empty list if it has no children.
	 * @throws NullPointerException if the specified key is {@code null}.
	 * @throws IllegalArgumentException if the specified element is not part of
	 *             the hierarchy.
	 */
	List<K> getChildrenKeys(K key);

	/**
	 * Returns the parent of the specified key.
	 * @param key The key of the value which parent is requested.
	 * @return The parent value or {@code null} if it is a first-level value.
	 * @throws NullPointerException if the specified values is {@code null}.
	 * @throws IllegalArgumentException if the specified values is not part of
	 *             the hierarchy.
	 */
	V getParent(K key);

	/**
	 * Returns the key of the parent of the specified key.
	 * @param key The key of the values which parent is requested.
	 * @return The parent element's key or {@code null} if it is a first-level
	 *         values.
	 * @throws NullPointerException if the specified values is {@code null}.
	 * @throws IllegalArgumentException if the specified values is not part of
	 *             the hierarchy.
	 */
	K getParentKey(K key);

	/**
	 * Checks if the hierarchy contains the specified element.
	 * @param value Element to check
	 * @throws NullPointerException if the specified element is {@code null}.
	 * @return True if the hierarchy contains the element, false otherwise.
	 */
	boolean containsValue(V value);

	/**
	 * Checks if the hierarchy contains a element with the specified key.
	 * @param key Key to check
	 * @return True if the hierarchy contains the element, false otherwise..
	 * @throws NullPointerException if the specified key is {@code null}.
	 */
	boolean containsKey(K key);

	/**
	 * Returns the value with the specified key.
	 * @param key The key to search.
	 * @return The object with the specified key or {@code null} if it not found
	 *         or the provided key is {@code null};
	 * @throws NullPointerException if the specified id is {@code null}.
	 */
	V get(K key);

	/**
	 * Returns whether the hierarchy is empty.
	 * @return True if the hierarchy is empty.
	 */
	boolean isEmpty();

	/**
	 * Returns the number of values in the hierarchy.
	 * @return The number of values in the hierarchy.
	 */
	int size();

	/**
	 * Returns all the descendants of the specified key.
	 * @param key The key of the value which descendants is requested.
	 * @return All descendants. The iteration order is unspecified.
	 * @throws NullPointerException if the specified value is {@code null}.
	 * @throws IllegalArgumentException if the specified value is not part of
	 *             the hierarchy.
	 */
	Collection<V> getDescendants(K key);

	/**
	 * Returns all the keys of the descendants of the specified key.
	 * @param key The key of the value which descendants is requested.
	 * @return All descendants' keys. The iteration order is unspecified.
	 * @throws NullPointerException if the specified value is {@code null}.
	 * @throws IllegalArgumentException if the specified value is not part of
	 *             the hierarchy.
	 */
	Collection<K> getDescendantsKeys(K key);

	/**
	 * Returns a hierarchy rooted at the specified element.
	 * @param key The key root value of the requested hierarchy.
	 * @param includeRoot If the root is to be included in the hierarchy.
	 * @return The requested hierarchy.
	 * @throws NullPointerException if the specified element is {@code null}.
	 * @throws IllegalArgumentException if the specified element is not part of
	 *             the hierarchy.
	 */
	Hierarchy<K, V> getSubhierarchy(K key, boolean includeRoot);

}
