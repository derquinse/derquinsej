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

import java.io.Serializable;

import com.google.common.base.Function;

/**
 * A string holder that implements equals ignoring case.
 * @author Andres Rodriguez
 */
public final class CaseIgnoringString implements Serializable, Comparable<CaseIgnoringString> {
	/** Serial UID. */
	private static final long serialVersionUID = 1050129007442556236L;
	/** Hash code. */
	private final int hash;
	/** Original string. */
	private final String string;

	private CaseIgnoringString(final String string) {
		this.string = string;
		this.hash = string.toLowerCase().hashCode();
	}
	
	private static final Function<String, CaseIgnoringString> VALUEOF_FUNCTION = new Function<String, CaseIgnoringString>() {
		public CaseIgnoringString apply(String from) {
			return valueOf(from);
		};
	};

	/**
	 * Builds a new case ignoring string from a existing string.
	 * @param string Original string.
	 * @return A requested object or {@code null} if the provided string is
	 *         {@code null}.
	 */
	public static CaseIgnoringString valueOf(String string) {
		return string == null ? null : new CaseIgnoringString(string);
	}
	
	/**
	 * Returns the valueOf static method as a function.
	 */
	public static Function<String, CaseIgnoringString> valueOfFunction() {
		return VALUEOF_FUNCTION;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CaseIgnoringString) {
			final CaseIgnoringString cis = (CaseIgnoringString) obj;
			return hash == cis.hash || string.equalsIgnoreCase(cis.string);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(CaseIgnoringString o) {
		return string.compareToIgnoreCase(o.string);
	}
	
	@Override
	public String toString() {
		return string;
	}

}
