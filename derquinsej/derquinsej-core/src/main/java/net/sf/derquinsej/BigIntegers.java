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

import java.math.BigInteger;

/**
 * Helper functions for BigIntegers.
 * @author Andres Rodriguez
 */
public final class BigIntegers {
	public static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);
	public static final BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);

	public static boolean fitsInLong(BigInteger b) {
		return LONG_MIN.compareTo(b) <= 0 && LONG_MAX.compareTo(b) >= 0;
	}

}
