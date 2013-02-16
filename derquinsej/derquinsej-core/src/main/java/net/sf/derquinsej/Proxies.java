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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Utility methods for Dynamic Proxies.
 * @author Andres Rodriguez
 */
public final class Proxies {
	/**
	 * Not instantiable.
	 */
	private Proxies() {
		throw new AssertionError();
	}

	/** Always null invocation handler. */
	private static final InvocationHandler ALWAYS_NULL = new InvocationHandler() {
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return null;
		}
	};

	/** Unsupported operation invocation handler. */
	private static final InvocationHandler UNSUPPORTED = new InvocationHandler() {
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			throw new UnsupportedOperationException();
		}
	};

	public static <T> T alwaysNull(Class<T> type) {
		return type.cast(Proxy.newProxyInstance(Proxies.class.getClassLoader(), new Class<?>[] { type }, ALWAYS_NULL));
	}

	public static Object alwaysNull(Class<?>... interfaces) {
		return Proxy.newProxyInstance(Proxies.class.getClassLoader(), interfaces, ALWAYS_NULL);
	}

	public static <T> T unsupported(Class<T> type) {
		return type.cast(Proxy.newProxyInstance(Proxies.class.getClassLoader(), new Class<?>[] { type }, UNSUPPORTED));
	}

	public static Object unsupported(Class<?>... interfaces) {
		return Proxy.newProxyInstance(Proxies.class.getClassLoader(), interfaces, UNSUPPORTED);
	}

}
