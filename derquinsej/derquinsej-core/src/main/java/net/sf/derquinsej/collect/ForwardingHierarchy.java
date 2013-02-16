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

import com.google.common.collect.ForwardingObject;

/**
 * A hierarchy that forward its method calls to a delegated hierarchy.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the elements.
 */
public abstract class ForwardingHierarchy<K, V> extends ForwardingObject implements Hierarchy<K, V> {
	public ForwardingHierarchy() {
	}
	
	@Override
	protected abstract Hierarchy<K, V> delegate();

	public boolean containsKey(K key) {
		return delegate().containsKey(key);
	}

	public boolean containsValue(V value) {
		return delegate().containsValue(value);
	}

	public V get(K key) {
		return delegate().get(key);
	}

	public List<V> getChildren(K key) {
		return delegate().getChildren(key);
	}
	
	public List<K> getChildrenKeys(K key) {
		return delegate().getChildrenKeys(key);
	}

	public Collection<V> getDescendants(K key) {
		return delegate().getDescendants(key);
	}
	
	public Collection<K> getDescendantsKeys(K key) {
		return delegate().getDescendantsKeys(key);
	}

	public List<V> getFirstLevel() {
		return delegate().getFirstLevel();
	}
	
	public List<K> getFirstLevelKeys() {
		return delegate().getFirstLevelKeys();
	}

	public V getParent(K key) {
		return delegate().getParent(key);
	}

	public K getParentKey(K key) {
		return delegate().getParentKey(key);
	}

	public Hierarchy<K, V> getSubhierarchy(K key, boolean includeRoot) {
		return delegate().getSubhierarchy(key, includeRoot);
	}

	public boolean isEmpty() {
		return delegate().isEmpty();
	}

	public Set<K> keySet() {
		return delegate().keySet();
	}

	public int size() {
		return delegate().size();
	}

	public Collection<V> values() {
		return delegate().values();
	}

}
