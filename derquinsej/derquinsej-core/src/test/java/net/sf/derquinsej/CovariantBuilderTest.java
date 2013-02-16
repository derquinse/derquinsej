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

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

/**
 * Tests for CovariantBuilder
 * @author Andres Rodriguez
 */
public class CovariantBuilderTest {
	/**
	 * Test constructor.
	 */
	@Test
	public void constructor() {
		Builder1 b = new Builder1();
		assertNotNull(b);
	}

	/**
	 * Test return type.
	 */
	@Test
	public void returnType() {
		Builder2 b = new Builder2();
		Builder2 b2 = b.setValue(null);
		String s = b2.get();
		assertNull(s);
	}

	private static class Builder1 extends CovariantBuilder<Builder1, String> {
		Builder1() {
		}

		@Override
		protected String doGet() {
			return null;
		}
	}

	private static abstract class StringBuilder<B extends StringBuilder<B>> extends CovariantBuilder<B, String> {
		private String string;

		StringBuilder() {
		}

		public B setValue(String value) {
			this.string = value;
			return thisValue();
		}

		@Override
		protected String doGet() {
			return string;
		}
	}

	private static class Builder2 extends StringBuilder<Builder2> {
		Builder2() {
		}
	}
}
