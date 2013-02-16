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

import static org.testng.Assert.assertEquals;

import java.util.Locale;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for LocalizedBuilder
 * @author Andres Rodriguez
 */
public class LocalizedBuilderTest {
	private static final String HELLO = "hello";
	private static final String HOLA = "hola";
	private static final Locale ES = new Locale("es");

	private LocalizedBuilder<String> builder;

	@BeforeMethod
	public void init() {
		builder = new LocalizedBuilder<String>();
	}

	@Test
	public void builder() {
		builder.setDefault(HELLO);
		builder.put(ES, HOLA);
		final Localized<String> s = builder.get();
		assertEquals(s.get(), HELLO);
		assertEquals(s.get(ES), HOLA);
		assertEquals(s.get(Locale.CANADA), HELLO);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void checkNullKey() {
		builder.put((Locale) null, "hello");
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void checkNullStringKey() {
		builder.put((String) null, "hello");
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void checkNullValue() {
		builder.put(Locale.CANADA, null);
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void noDefault() {
		builder.get();
	}

}
