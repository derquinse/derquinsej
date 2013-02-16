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

import com.google.common.base.Supplier;

/**
 * An object that supplies a per-thread object of the specified type.
 * The object is acquired from a provided supplier.
 * 
 * @author Andres Rodriguez
 */
public final class ThreadLocalSupplier<T> implements Supplier<T> {
	private final ThreadLocal<T> threadLocal = new ThreadLocal<T>();
	private final Supplier<T> supplier;
	
	/**
	 * Initializes the supplier
	 * @param supplier The supplier to be used to create objects for new threads.
	 */
	public ThreadLocalSupplier(final Supplier<T> supplier) {
		this.supplier = supplier;
	}
	
	/**
	 * Retrieve the instance bounded to the current thread.
	 * A new one will be acquired in the first call from each thread.
	 */
	public T get() {
		T item = threadLocal.get();
		if ( item == null ) {
			item = supplier.get();
			threadLocal.set(item);
		}
		return item;
	}
}
