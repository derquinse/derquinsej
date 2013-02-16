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
 * Helper functions to operate on tuples.
 * @author Andres Rodriguez
 */
public final class Tuples {
	/** Not instantiable. */
	private Tuples() {
		throw new AssertionError();
	}

	/**
	 * Builds a 1-element tuple.
	 * @param e1 First element.
	 */
	public static <T1> Tuple1<T1> tuple(T1 e1) {
		return new DefaultTuple1<T1>(e1);
	}
	
	/**
	 * Builds a 2-element tuple.
	 * @param e1 First element.
	 * @param e2 Second element.
	 */
	public static <T1, T2> Tuple2<T1, T2> tuple(T1 e1, T2 e2) {
		return new DefaultTuple2<T1, T2>(e1, e2);
	}
}
