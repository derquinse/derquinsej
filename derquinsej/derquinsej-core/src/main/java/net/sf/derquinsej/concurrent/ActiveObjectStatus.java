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
package net.sf.derquinsej.concurrent;

/**
 * Enumeration of the possible status for active objects. A status is said to be
 * permanent if a call to a lifecycle-changing method is required for such
 * status to change. A status is said to be transient if it will eventually
 * change to a permanent one without any action.
 * @author Andres Rodriguez
 */
public enum ActiveObjectStatus {
	/** The object is active and accepting requests. */
	ON(true),
	/** The object is in process of being started. */
	STARTING(false),
	/** The object is in process of being stopped. */
	STOPPING(false),
	/** The object is inactive and does not accept requests. */
	OFF(true);

	/** True if it's a permanent status. */
	private final boolean permanent;

	private ActiveObjectStatus(final boolean permanent) {
		this.permanent = permanent;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public boolean isTransient() {
		return !permanent;
	}
}
