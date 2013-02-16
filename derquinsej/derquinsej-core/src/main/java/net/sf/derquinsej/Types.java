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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import com.google.common.base.Preconditions;

/**
 * Utility methods to deal with Types.
 * @author Andres Rodriguez
 */
public final class Types {
	/**
	 * Not instantiable.
	 */
	private Types() {
		throw new AssertionError();
	}

	/**
	 * Returns the actual type arguments of the generic superclass of the
	 * provided class.
	 * @param subclass Class to process.
	 * @return The type arguments of the generic superclass.
	 * @throws IllegalArgumentException if the superclass is not a parametrized
	 *             type.
	 */
	public static Type[] getSuperclassTypeArguments(Class<?> subclass) {
		final Type superclass = subclass.getGenericSuperclass();
		Preconditions.checkArgument(superclass instanceof ParameterizedType, "Missing type parameter.");
		final ParameterizedType parameterized = (ParameterizedType) superclass;
		return parameterized.getActualTypeArguments();
	}

	/**
	 * Returns the first actual type argument of the generic superclass of the
	 * provided class.
	 * @param subclass Class to process.
	 * @return The first type argument of the generic superclass.
	 * @throws IllegalArgumentException if the superclass is not a parametrized
	 *             type.
	 */
	public static Type getSuperclassTypeArgument(Class<?> subclass) {
		return getSuperclassTypeArguments(subclass)[0];
	}

	/**
	 * Returns the raw type corresponding to the provided type.
	 * @param type Type.
	 * @return The raw type.
	 */
	public static Class<?> getRawType(Type type) {
		if (type instanceof Class<?>) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			final Type rawType = ((ParameterizedType) type).getRawType();
			Preconditions.checkArgument(rawType instanceof Class<?>, "Unknown case");
			return (Class<?>) rawType;
		} else if (type instanceof GenericArrayType) {
			return Object[].class;
		} else if (type instanceof TypeVariable<?>) {
			return Object.class;
		}
		throw new IllegalArgumentException("Unsupported case");
	}
}
