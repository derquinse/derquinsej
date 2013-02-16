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

/**
 * Visitor for hierarchy traversal.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public interface HierarchyVisitor<K, V> {
	/**
	 * Visits an element.
	 * @param key Element key.
	 * @param value Element value.
	 * @param parentKey Parent element's key ({@code null} if first level).
	 * @param position Position within parent's list of children.
	 */
	void visit(K key, V value, K parentKey, int position);
}
