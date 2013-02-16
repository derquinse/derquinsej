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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

/**
 * An immutable and thread-safe implementation of hierarchy.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the elements.
 */
public abstract class ImmutableHierarchy<K, V> implements Hierarchy<K, V> {
	private static final Empty EMPTY = new Empty();

	private ImmutableHierarchy() {
	}

	/**
	 * Returns an empty immutable hierarchy.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> ImmutableHierarchy<K, V> of() {
		return (ImmutableHierarchy<K, V>) EMPTY;
	}

	/**
	 * Returns a new builder.
	 */
	public static <K, V> Builder<K, V> builder() {
		return new Builder<K, V>();
	}

	/**
	 * Creates a new immutable hierarchy.
	 * @param <K> Type of the keys.
	 * @param <V> Type of the elements.
	 * @param elements Elements.
	 * @param key Element to key function.
	 * @param parentKey Element to parent key function.
	 * @return The hierachy.
	 * @throws NullPointerException if any argument or element is {@code null}.
	 * @throws IllegalArgumentException if the node has been previously added.
	 * @throws IllegalStateException if a loop is detected.
	 * @throws IllegalStateException if there are referenced parents that are
	 *             not part of the hierarchy yet.
	 */
	public static <K, V> ImmutableHierarchy<K, V> of(Iterable<V> elements, Function<? super V, ? extends K> key,
			Function<? super V, ? extends K> parentKey) {
		final Builder<K, V> builder = builder();
		for (V element : elements) {
			builder.add(key.apply(element), element, parentKey.apply(element));
		}
		return builder.get();
	}

	/**
	 * Creates a new immutable hierarchy.
	 * @param <K> Type of the keys.
	 * @param <V> Type of the elements.
	 * @param elements Elements.
	 * @param ordering Ordering to apply before building the hierarchy.
	 * @param key Element to key function.
	 * @param parentKey Element to parent key function.
	 * @return The hierachy.
	 * @throws NullPointerException if any argument or element is {@code null}.
	 * @throws IllegalArgumentException if the node has been previously added.
	 * @throws IllegalStateException if a loop is detected.
	 * @throws IllegalStateException if there are referenced parents that are
	 *             not part of the hierarchy yet.
	 */
	public static <K, V> ImmutableHierarchy<K, V> of(Iterable<V> elements, Ordering<V> ordering,
			Function<? super V, ? extends K> key, Function<? super V, ? extends K> parentKey) {
		return of(ordering.sortedCopy(elements), key, parentKey);
	}

	private static void checkKey(Object key) {
		Preconditions.checkNotNull(key, "Elements with a null key are not allowed");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.derquinsej.collect.Hierarchy#getSubhierarchy(java.lang.Object,
	 * boolean)
	 */
	public abstract ImmutableHierarchy<K, V> getSubhierarchy(K key, boolean includeRoot);

	private static final class Immutable<K, V> extends ImmutableHierarchy<K, V> {
		private final ImmutableMap<K, V> entries;
		private final ImmutableList<K> firstLevel;
		private final ImmutableMap<K, K> parents;
		private final ImmutableListMultimap<K, K> children;
		private final Function<K, V> function;

		private Immutable(final Builder<K, V> builder) {
			this.entries = ImmutableMap.copyOf(builder.entries);
			this.firstLevel = ImmutableList.copyOf(builder.firstLevel);
			this.parents = ImmutableMap.copyOf(builder.parents);
			this.children = builder.children.build();
			this.function = Functions.forMap(entries);
		}

		private void checkMemberKey(K key) {
			checkKey(key);
			Preconditions.checkArgument(entries.containsKey(key),
					"There is no element with the provided key in the hierarchy");
		}

		@Override
		public ImmutableHierarchy<K, V> getSubhierarchy(K key, boolean includeRoot) {
			checkMemberKey(key);
			final Builder<K, V> builder = builder();
			builder.add(this, key, includeRoot, null);
			return builder.get();
		}

		public boolean containsKey(K key) {
			checkKey(key);
			return entries.containsKey(key);
		}

		public boolean containsValue(V value) {
			Preconditions.checkNotNull(value);
			return entries.containsValue(value);
		}

		public V get(K key) {
			checkKey(key);
			return entries.get(key);
		}

		public List<V> getChildren(K key) {
			return Lists.transform(getChildrenKeys(key), function);
		}

		public List<K> getChildrenKeys(K key) {
			checkMemberKey(key);
			return children.get(key);
		}

		public Collection<V> getDescendants(K key) {
			checkKey(key);
			final List<K> keys = Lists.newLinkedList();
			getDescendantsKeys(key, keys);
			return Collections.unmodifiableList(Lists.transform(keys, function));
		}

		private void getDescendantsKeys(K key, List<K> keys) {
			final List<K> list = children.get(key);
			if (list.isEmpty()) {
				return;
			}
			keys.addAll(list);
			for (final K k : list) {
				getDescendantsKeys(k, keys);
			}
			return;
		};

		public Collection<K> getDescendantsKeys(K key) {
			checkKey(key);
			final List<K> keys = Lists.newLinkedList();
			getDescendantsKeys(key, keys);
			return Collections.unmodifiableList(keys);
		}

		public List<V> getFirstLevel() {
			return Lists.transform(firstLevel, function);
		}

		public List<K> getFirstLevelKeys() {
			return firstLevel;
		}

		public V getParent(K key) {
			final K parentKey = getParentKey(key);
			return parentKey != null ? get(parentKey) : null;
		}

		public K getParentKey(K key) {
			checkMemberKey(key);
			return parents.get(key);
		}

		public boolean isEmpty() {
			return entries.isEmpty();
		}

		public Set<K> keySet() {
			return entries.keySet();
		}

		public int size() {
			return entries.size();
		}

		public Collection<V> values() {
			return entries.values();
		}

	}

	/**
	 * Builder for immutable hierarchies.
	 * @author Andres Rodriguez
	 * @param <K> Type of the keys.
	 * @param <V> Type of the elements.
	 */
	public static final class Builder<K, V> implements HierarchyBuilder<K, V> {
		private final Map<K, V> entries = Maps.newHashMap();
		private final Set<K> unaddedParents = Sets.newHashSet();
		private final List<K> firstLevel = Lists.newLinkedList();
		private final Map<K, K> parents = Maps.newHashMap();
		private final ImmutableListMultimap.Builder<K, K> children = ImmutableListMultimap.builder();

		private Builder() {
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.collect.HierarchyBuilder#add(java.lang.Object,
		 * java.lang.Object, java.lang.Object)
		 */
		public Builder<K, V> add(K key, V value, K parentKey) {
			Preconditions.checkArgument(!entries.containsKey(key), "Duplicate entries not allowed");
			if (parentKey == null) {
				firstLevel.add(key);
			} else {
				K p = parentKey;
				final Set<K> visited = Sets.newHashSet();
				visited.add(key);
				while (p != null) {
					Preconditions.checkState(visited.add(p), "Loop detected");
					p = parents.get(p);
				}
				children.put(parentKey, key);
				parents.put(key, parentKey);
				if (!entries.containsKey(parentKey)) {
					unaddedParents.add(parentKey);
				}
			}
			unaddedParents.remove(key);
			entries.put(key, value);
			return this;
		}

		public Builder<K, V> add(Hierarchy<K, V> hierarchy, K key, boolean includeRoot, K parentKey) {
			Preconditions.checkNotNull(hierarchy);
			Preconditions.checkArgument(key == null || hierarchy.containsKey(key));
			final Collection<K> keys;
			final K rootKey;
			if (key == null) {
				keys = hierarchy.keySet();
				rootKey = null;
			} else {
				keys = hierarchy.getDescendantsKeys(key);
				rootKey = key;
				if (includeRoot) {
					add(key, hierarchy.get(key), parentKey);
				}
			}
			for (K k : keys) {
				final K currentParent = hierarchy.getParentKey(k);
				final K newParent;
				if (Objects.equal(currentParent, rootKey)) {
					newParent = rootKey;
				} else {
					newParent = currentParent;
				}
				add(k, hierarchy.get(k), newParent);
			}
			return this;
		}

		/**
		 * Builds and returns an immutable hierarchy with the nodes added up to
		 * the method call.
		 * @returns An immutable hierarchy.
		 * @throws IllegalStateException if there are referenced parents that
		 *             are not part of the hierarchy yet.
		 */
		public ImmutableHierarchy<K, V> get() {
			Preconditions.checkState(unaddedParents.isEmpty(),
					"There are referenced parents that have not been added yet.");
			return new Immutable<K, V>(this);
		}

	}

	private static final class Empty extends ImmutableHierarchy<Object, Object> {
		private Empty() {
		}

		private IllegalArgumentException illegal() {
			return new IllegalArgumentException("The provided key is not part of the hierarchy.");
		}

		@Override
		public ImmutableHierarchy<Object, Object> getSubhierarchy(Object key, boolean includeRoot) {
			Preconditions.checkNotNull(key);
			throw illegal();
		}

		public boolean containsKey(Object key) {
			Preconditions.checkNotNull(key);
			return false;
		}

		public boolean containsValue(Object element) {
			Preconditions.checkNotNull(element);
			return false;
		}

		public Object get(Object key) {
			Preconditions.checkNotNull(key);
			throw illegal();
		}

		public List<Object> getChildren(Object key) {
			Preconditions.checkNotNull(key);
			throw illegal();
		}

		public List<Object> getChildrenKeys(Object key) {
			Preconditions.checkNotNull(key);
			throw illegal();
		}

		public Collection<Object> getDescendants(Object key) {
			Preconditions.checkNotNull(key);
			throw illegal();
		}

		public Collection<Object> getDescendantsKeys(Object key) {
			Preconditions.checkNotNull(key);
			throw illegal();
		}

		public List<Object> getFirstLevel() {
			return ImmutableList.of();
		}

		public List<Object> getFirstLevelKeys() {
			return ImmutableList.of();
		}

		public Object getParent(Object key) {
			Preconditions.checkNotNull(key);
			throw illegal();
		}

		public Object getParentKey(Object key) {
			Preconditions.checkNotNull(key);
			throw illegal();
		}

		public boolean isEmpty() {
			return true;
		}

		public Set<Object> keySet() {
			return ImmutableSet.of();
		}

		public int size() {
			return 0;
		}

		public Collection<Object> values() {
			return ImmutableSet.of();
		}
	}
}
