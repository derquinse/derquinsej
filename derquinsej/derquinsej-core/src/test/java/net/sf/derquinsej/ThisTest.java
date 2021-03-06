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
package net.sf.derquinsej;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * Tests for This
 * @author Andres Rodriguez
 */
public class ThisTest {
	/**
	 * Test Ok 1.
	 */
	@Test
	public void test1() {
		assertTrue(new Concrete1().thisValue() instanceof Concrete1);
	}

	/**
	 * Test Ok 2.
	 */
	@Test
	public void test2() {
		assertTrue(new Concrete2().thisValue() instanceof Concrete2);
		assertTrue(new Concrete2().otherMethod() instanceof Concrete2);
	}

	/**
	 * Test error.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testError() {
		new Concrete3();
	}

	private static class Concrete1 extends This<Concrete1> {
	}

	private static abstract class Abstract1<T extends Abstract1<T>> extends This<T> {
		T otherMethod() {
			return thisValue();
		}
	}

	private static class Concrete2 extends Abstract1<Concrete2> {
	}

	private static class Concrete3 extends This<Concrete1> {
	}

}
