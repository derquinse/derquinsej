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

import static net.sf.derquinsej.i18n.LocalizedExceptionSupport.fallbackIfNull;

import java.util.Locale;

/**
 * Runtime exception with a localized message.
 * @author Andres Rodriguez
 */
@SuppressWarnings("serial")
public abstract class LocalizedRuntimeException extends RuntimeException implements Localized<String> {
	/** Localized message. */
	private final LocalizedMessage message;

	protected LocalizedRuntimeException(LocalizedMessage message, Throwable cause) {
		super(cause);
		this.message = fallbackIfNull(message, this);
	}

	@Override
	public final String getMessage() {
		return get();
	}

	@Override
	public String getLocalizedMessage() {
		return get();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.i18n.Localized#get()
	 */
	public String get() {
		return message.get();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.i18n.Localized#get(java.util.Locale,
	 * java.lang.Object)
	 */
	public String get(Locale locale, String fallback) {
		return message.get(locale, fallback);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.i18n.Localized#get(java.util.Locale)
	 */
	public String get(Locale locale) {
		return message.get(locale);
	}
}
