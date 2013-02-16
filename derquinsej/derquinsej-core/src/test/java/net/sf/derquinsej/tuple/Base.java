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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Base class for tuples tests.
 * @author Andres Rodriguez
 */
class Base {
	static final Integer ONE = Integer.valueOf(1);
	static final Integer TWO = Integer.valueOf(2);
	static final String HI = "hi";
	static final String BYE = "bye";
	
	static <T> T notNull(T obj) {
		assertNotNull(obj);
		return obj;
	}

	static <T extends Tuple> T check(T t, Object... objs) {
		assertEquals(t.arity(), objs.length);
		for (int i = 0; i < objs.length; i++) {
			assertEquals(t.get(i), objs[i]);
		}
		if (t instanceof Tuple1<?>) {
			assertEquals(((Tuple1<?>)t)._1(), objs[0]);
			if (t instanceof Tuple2<?,?>) {
				assertEquals(((Tuple2<?,?>)t)._2(), objs[1]);
			}
		}
		return t;
	}
	
	/**
	 * Equality 1.
	 */
	static void equality(Object one) {
		assertTrue(one.equals(one));
		assertFalse(one.equals(null));
		assertEquals(one.hashCode(), one.hashCode());
	}

	/**
	 * Equality 2.
	 */
	static void equality(Object one, Object other) {
		assertFalse(one.equals(other));
		assertFalse(other.equals(one));
	}

	/**
	 * Equality 3.
	 */
	static void equality(Object one, Object two, Object three) {
		assertEquals(one, two);
		assertEquals(two, one);
		assertEquals(three, two);
		assertEquals(two, three);
		assertEquals(one, three);
		assertEquals(three, one);
		assertEquals(one.hashCode(), two.hashCode());
		assertEquals(three.hashCode(), one.hashCode());
		assertEquals(three.hashCode(), two.hashCode());
	}
	

}
