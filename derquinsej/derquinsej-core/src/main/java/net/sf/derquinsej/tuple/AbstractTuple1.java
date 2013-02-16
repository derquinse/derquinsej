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
 * Abstract implementation of a 1-element tuple.
 * @author Andres Rodriguez
 * @param <T1> First element type.
 */
abstract class AbstractTuple1<T1> extends AbstractTuple implements Tuple1<T1> {
	/** First element. */
	private final T1 e1;

	/**
	 * Constructor.
	 * @param e1 First element.
	 */
	AbstractTuple1(T1 e1) {
		this.e1 = e1;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.tuple.Tuple1#_1()
	 */
	public T1 _1() {
		return e1;
	}
}
