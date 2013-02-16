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
package net.sf.derquinsej;

import static java.lang.Long.MIN_VALUE;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;
import static net.sf.derquinsej.BigIntegers.LONG_MAX;
import static net.sf.derquinsej.BigIntegers.LONG_MIN;
import static net.sf.derquinsej.BigIntegers.fitsInLong;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * Tests for long basic populations.
 * @author Andres Rodriguez
 */
public class BigIntegersTest {
	/**
	 * Fits in long.
	 */
	@Test
	public void testFitsInLong() {
		assertTrue(fitsInLong(ZERO));
		assertTrue(fitsInLong(ONE));
		assertTrue(fitsInLong(TEN));
		assertTrue(fitsInLong(LONG_MAX));
		assertTrue(fitsInLong(LONG_MIN));
		assertEquals(LONG_MAX.longValue(), Long.MAX_VALUE);
		assertEquals(LONG_MIN.longValue(), MIN_VALUE);
	}
}
