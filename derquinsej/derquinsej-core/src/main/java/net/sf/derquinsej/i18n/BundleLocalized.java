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

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.google.common.base.Preconditions;

/**
 * A localized objects backed by a resource bundle.
 * @author Andres Rodriguez
 */
public final class BundleLocalized<T> extends AbstractLocalized<T> {
	/** Default suffix. */
	public static final String SUFFIX = "Bundle";
	/** Bundle base name. */
	private final String baseName;
	/** Bundle key. */
	private final String key;
	
	/**
	 * Default constructor.
	 * @param baseName Bundle base name.
	 * @param key Bundle key.
	 */
	public BundleLocalized(String baseName, String key) {
		Preconditions.checkNotNull(baseName);
		Preconditions.checkNotNull(key);
		this.baseName = baseName;
		this.key = key;
	}

	/**
	 * Class-based constructor. The base name is the class name plus the default suffix.
	 * @param baseName Bundle base name.
	 * @param key Bundle key.
	 */
	public BundleLocalized(Class<?> type, String key) {
		this(type.getName() + SUFFIX, key);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.i18n.Localized#get(java.util.Locale)
	 */
	public T get(Locale locale) {
		try {
			final ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
			@SuppressWarnings("unchecked")
			final T value = (T)bundle.getObject(key);
			return value;
		} catch(MissingResourceException e) {
			throw new UnableToLocalizeException(e);
		} catch(ClassCastException e) {
			throw new UnableToLocalizeException(e);
		}
	}
}
