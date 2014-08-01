/*
 * Copyright (C) the original author or authors.
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
package net.sf.derquinsej.stats;

import static com.google.common.base.Functions.compose;
import static com.google.common.base.Functions.constant;
import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collections;
import java.util.Map;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ForwardingMap;

/**
 * A concurrent counter map. All the map-modifying operations except add and reset throw
 * UnsupportedOperationException
 * @author Andres Rodriguez
 * @param <K> The type of keys.
 */
public final class CounterMap<K> extends ForwardingMap<K, Counter> {
	/** Loading cache. */
	private final LoadingCache<K, Counter> cache;
	/** Unmodifiable view. */
	private final Map<K, Counter> view;

	/** Constructor. */
	private CounterMap() {
		this.cache = CacheBuilder.newBuilder().build(CacheLoader.from(compose(Counter.creator(), constant(0L))));
		this.view = Collections.unmodifiableMap(cache.asMap());
	}

	@Override
	protected Map<K, Counter> delegate() {
		return view;
	}

	/**
	 * Adds a value to a counter.
	 * @param key Counter key.
	 * @param delta Value to add (>=0)
	 * @return The updated value.
	 * @throws IllegalArgumentException if the argument < 0
	 */
	public long add(K key, long delta) {
		checkArgument(delta >= 0, "The argument %d should be >=0", delta);
		return cache.getUnchecked(key).add(delta);
	}

	/**
	 * Increment the counter.
	 * @param key Counter key.
	 * @return The updated value.
	 */
	public long add(K key) {
		return cache.getUnchecked(key).add();
	}

	/**
	 * Resets a counter to 0.
	 * @param key Counter key.
	 */
	public void reset(K key) {
		cache.getUnchecked(key).reset();
	}

}
