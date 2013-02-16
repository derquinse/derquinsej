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

/**
 * A builder class for hash codes. Based on Item 9 of Effective Java 2nd
 * Edition. This class is not thread-safe.
 * @author Andres Rodriguez
 */
public final class HashBuilder {
	/**
	 * Adds an integer to a hash code computation.
	 * @param hash Current hash.
	 * @param number Number to add.
	 * @return Updated hash code.
	 */
	public static int hash(int hash, int number) {
		return 31 * hash + number;
	}

	/**
	 * Adds an object to a hash code computation.
	 * @param hash Current hash.
	 * @param obj Object to add.
	 * @return Updated hash code.
	 */
	public static int hash(int hash, Object obj) {
		return hash(hash, hash(obj));
	}

	/**
	 * Computes the hash of an object.
	 * @param obj Object hash.
	 * @return Updated hash code.
	 */
	public static int hash(Object obj) {
		return obj == null ? 0 : obj.hashCode();
	}
	
	/**
	 * Computes the hash of a long.
	 * @param number Number to hash.
	 * @return The hash code.
	 */
	public static int hash(long number) {
		return (int) (number ^ (number >>> 32));
	}

	/**
	 * Computes the hash of a float.
	 * @param number Number to hash.
	 * @return The hash code.
	 */
	public static int hash(float number) {
		return Float.floatToIntBits(number);
	}

	/**
	 * Computes the hash of a double.
	 * @param number Number to hash.
	 * @return The hash code.
	 */
	public static int hash(double number) {
		return hash(Double.doubleToLongBits(number));
	}
	
	/** Current hash code. */
	private int hash;

	/**
	 * Constructs a new builder.
	 * @param initialValue A non-zero initial value.
	 * @throws IllegalArgumentException if the initial value is zero.
	 */
	public HashBuilder(int initialValue) throws IllegalArgumentException {
		if (initialValue == 0) {
			throw new IllegalArgumentException("Initial value must be non-zero");
		}
		hash = initialValue;
	}

	/**
	 * Constructs a new builder.
	 */
	public HashBuilder() {
		this(17);
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HashBuilder) {
			return hash == obj.hashCode();
		}
		return false;
	}

	/**
	 * Combine a partial hash with the current hash.
	 * @param h Partial hash to combine.
	 */
	private void combine(int h) {
		hash = hash(hash, h);
	}

	/**
	 * Adds a boolean to the computation.
	 * @param b The boolean to add.
	 * @return This builder.
	 */
	public HashBuilder add(boolean b) {
		combine(b ? 1 : 0);
		return this;
	}

	/**
	 * Adds a byte to the computation.
	 * @param b The byte to add.
	 * @return This builder.
	 */
	public HashBuilder add(byte b) {
		combine((int) b);
		return this;
	}

	/**
	 * Adds a char to the computation.
	 * @param c The char to add.
	 * @return This builder.
	 */
	public HashBuilder add(char c) {
		combine((int) c);
		return this;
	}

	/**
	 * Adds a short to the computation.
	 * @param s The char to add.
	 * @return This builder.
	 */
	public HashBuilder add(short s) {
		combine((int) s);
		return this;
	}

	/**
	 * Adds an integer to the computation.
	 * @param i The integer to add.
	 * @return This builder.
	 */
	public HashBuilder add(int i) {
		combine(i);
		return this;
	}

	/**
	 * Adds a long to the computation.
	 * @param l The long to add.
	 * @return This builder.
	 */
	public HashBuilder add(long l) {
		combine(hash(l));
		return this;
	}

	/**
	 * Adds a float to the computation.
	 * @param f The float to add.
	 * @return This builder.
	 */
	public HashBuilder add(float f) {
		combine(hash(f));
		return this;
	}

	/**
	 * Adds a double to the computation.
	 * @param d The double to add.
	 * @return This builder.
	 */
	public HashBuilder add(double d) {
		return add(hash(d));
	}

	/**
	 * Adds an object to the computation.
	 * @param object The object to add.
	 * @return This builder.
	 */
	public HashBuilder add(Object object) {
		return add(hash(object));
	}

}
