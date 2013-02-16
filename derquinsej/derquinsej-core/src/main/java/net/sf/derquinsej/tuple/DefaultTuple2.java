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
 * Default implementation of a 2-element tuple.
 * @author Andres Rodriguez
 * @param <T1> First element type.
 * @param <T2> Second element type.
 */
final class DefaultTuple2<T1, T2> extends AbstractTuple2<T1, T2> {
	/** Hash code. */
	private final int hash;

	/**
	 * Constructor.
	 * @param e1 First element.
	 * @param e2 Second element.
	 */
	DefaultTuple2(T1 e1, T2 e2) {
		super(e1, e2);
		this.hash = hash(hash(17, e1), e2);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.tuple.Tuple#arity()
	 */
	public int arity() {
		return 2;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.tuple.Tuple#get(int)
	 */
	public Object get(int index) {
		checkIndex(index);
		return (index == 0) ? _1() : _2();
	}
	
	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Tuple) {
			final Tuple t = (Tuple) obj;
			if (2 == t.arity() && hash == t.hashCode()) {
				return equal(_1(), t.get(0)) && equal(_2(), t.get(1));
			}
		}
		return false;
	}
	
}
