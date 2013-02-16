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

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.MIN_VALUE;

/**
 * Helper functions for Longs.
 * @author Andres Rodriguez
 */
public final class Longs {
	public static boolean sumFits(long a, long b) {
		if (a == 0 || b == 0) {
			return true;
		}
		if (a > 0 && b > 0) {
			return MAX_VALUE - a >= b;
		}
		if (a < 0 && b < 0) {
			return MIN_VALUE - a <= b;
		}
		return true;
	}

}
