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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementations for number generators.
 * All provided implementations are thread-safe.
 * 
 * @author Andres Rodriguez
 */
public class NumberGenerators {
	private NumberGenerators() {
	}
	
	/**
	 * Creates a new integer generator.
	 * @param initialValue The first value the generator will supply.
	 * @return The created generator.
	 */
	public static NumberGenerator<Integer> newIntGenerator(final int initialValue) {
		return new NumberGenerator<Integer>() {
			private final AtomicInteger value = new AtomicInteger(initialValue);
			
			public Integer get() {
				return value.getAndIncrement();
			}
		};
	}

	/**
	 * Creates a new integer generator with an initial value of zero.
	 * @return The created generator.
	 */
	public static NumberGenerator<Integer> newIntGenerator() {
		return newIntGenerator(0);
	}

	/**
	 * Creates a new long integer generator.
	 * @param initialValue The first value the generator will supply.
	 * @return The created generator.
	 */
	public static NumberGenerator<Long> newLongGenerator(final long initialValue) {
		return new NumberGenerator<Long>() {
			private final AtomicLong value = new AtomicLong(initialValue);
			
			public Long get() {
				return value.getAndIncrement();
			}
		};
	}

	/**
	 * Creates a new long integer generator with an initial value of zero.
	 * @return The created generator.
	 */
	public static NumberGenerator<Long> newLongGenerator() {
		return newLongGenerator(0L);
	}
}
