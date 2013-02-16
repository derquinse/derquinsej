/*
 * Copyright 2008-2009 the original author or authors.
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
package net.sf.derquinsej.tuple;

/**
 * Base interface for tuples. If tuples are to be placed in sets or used as keys
 * in maps, it is strongly recommended to use only immutable as tuple elements.
 * objects into this collection. If an element is modified after being placed in
 * the tuple, the collection the tuple has been inserted in may not function
 * correctly. In fact, implementations may cache hash value at construction time.
 * @author Andres Rodriguez
 */
public interface Tuple {
	/**
	 * Returns the tuple's arity.
	 * @return The tuple's arity (>0).
	 */
	int arity();

	/**
	 * Returns the element at position index (0 <= index < arity).
	 * @param index The requested index.
	 * @return The requested element.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	Object get(int index);
}
