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
package net.sf.derquinsej.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.SecureRandom;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for Streams
 * @author Andres Rodriguez
 */
public class StreamsTest {
	private byte[] original;

	@BeforeClass
	public void init() {
		final int n = 8192;
		original = new byte[n];
		final SecureRandom random = new SecureRandom();
		random.nextBytes(original);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullArgument() throws IOException {
		Streams.consume(null, true);
	}

	@Test
	public void copy() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(original);
		final byte[] copy = Streams.consume(bais, true);
		Assert.assertEquals(copy, original);
	}
}
