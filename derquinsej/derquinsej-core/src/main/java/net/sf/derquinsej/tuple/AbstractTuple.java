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

import static com.google.common.base.Objects.equal;
import static net.sf.derquinsej.HashBuilder.hash;

/**
 * Abstract superclass for tuple implementations.
 * @author Andres Rodriguez
 */
abstract class AbstractTuple implements Tuple {
	/**
	 * Constructor.
	 */
	AbstractTuple() {
	}

	/**
	 * Checks an index.
	 * @param index Index to check.
	 * @throws IndexOutOfBoundsException if index < 0 or index => arity.
	 */
	final void checkIndex(int index) {
		if (index < 0 || index >= arity()) {
			throw new IndexOutOfBoundsException(String.format("Requested element %d but arity is %d", index, arity()));
		}
	}

	@Override
	public int hashCode() {
		int result = 17;
		final int n = arity();
		for (int i = 0; i < n; i++) {
			result = hash(result, get(i));
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Tuple) {
			final Tuple t = (Tuple) obj;
			final int n = arity();
			if (n == t.arity()) {
				for (int i = 0; i < n; i++) {
					if (!equal(get(i), t.get(i))) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
}
