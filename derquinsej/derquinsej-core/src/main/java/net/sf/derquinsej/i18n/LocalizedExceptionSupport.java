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
package net.sf.derquinsej.i18n;

/**
 * Support class for localized exceptions.
 * @author Andres Rodriguez
 */
final class LocalizedExceptionSupport {
	/** Not instantiable. */
	private LocalizedExceptionSupport() {
		throw new AssertionError();
	}
	
	/** Prefix for fallback messages. */
	private static final String FALLBACK_PREFIX = "Fallback Exception Message :";
	
	/**
	 * Returns the fallback message for a exception.
	 * @param e Exception.
	 * @return Fallback message.
	 */
	static String fallback(Exception e) {
		return FALLBACK_PREFIX + e.getClass().getName();
	}
	
	/**
	 * Returns a fallback message if the provided one is null.
	 * @param message Message to check.
	 * @param e Calling exception.
	 * @return A non-null fallback message.
	 */
	static LocalizedMessage fallbackIfNull(LocalizedMessage message, Exception e) {
		if (message != null) {
			return message;
		} else {
			return new LocalizedMessage(Unlocalized.of(fallback(e)));
		}
	}
}
