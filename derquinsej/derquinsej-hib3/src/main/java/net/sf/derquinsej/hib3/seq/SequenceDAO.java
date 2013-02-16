/*
 * Copyright 2007 the original author or authors.
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
package net.sf.derquinsej.hib3.seq;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.common.base.Preconditions;

/**
 * Data access object for the sequence entity. As it represents a very specific
 * case, it does not follow normal best-practices (interface-based, etc.).
 * WARNING: Transactional sequences are a source of locks and may hurt
 * scalability.
 * @author Andres Rodriguez
 */
public class SequenceDAO {
	/** Hibernate Session factory. */
	private final SessionFactory sessionFactory;

	/**
	 * Initialized the DAO
	 * @param sessionFactory Hibernate Session factory.
	 */
	public SequenceDAO(final SessionFactory sessionFactory) {
		this.sessionFactory = Preconditions.checkNotNull(sessionFactory, "The session factory is mandatory.");
	}

	private static String nonNull(String id) {
		return Preconditions.checkNotNull(id, "The sequence name (id) is mandatory.");
	}

	/**
	 * Gets the current value of a sequence.
	 * @param id Sequence name.
	 * @return The current value of the specified sequence.
	 * @throws SequenceNotFoundException if the sequence is not found.
	 */
	public long getCurrentValue(final String id) throws SequenceNotFoundException {
		nonNull(id);
		final Session session = sessionFactory.getCurrentSession();
		final Sequence sequence = (Sequence) session.get(Sequence.class, id, LockMode.READ);
		if (sequence == null) {
			throw new SequenceNotFoundException(id);
		}
		return sequence.getCurrent();
	}

	/**
	 * Gets the next value of a sequence. The row is locked in the current
	 * transaction.
	 * @param id Sequence name.
	 * @return The next value of the specified sequence.
	 * @throws SequenceNotFoundException if the sequence is not found.
	 */
	public long getNextValue(final String id) throws SequenceNotFoundException {
		nonNull(id);
		final Session session = sessionFactory.getCurrentSession();
		final Sequence sequence = (Sequence) session.get(Sequence.class, id, LockMode.UPGRADE);
		if (sequence == null) {
			throw new SequenceNotFoundException(id);
		}
		final long next = sequence.getNext();
		session.update(sequence);
		return next;
	}

	/**
	 * Gets the next value of a sequence, creating it if it does not exist.
	 * @param id Sequence name.
	 * @param initial Initial value.
	 * @param increment Increment between values.
	 * @return The next value of the specified sequence.
	 */
	public long getNextValue(final String id, final long initial, final long increment) {
		nonNull(id);
		final Session session = sessionFactory.getCurrentSession();
		Sequence sequence = (Sequence) session.get(Sequence.class, id, LockMode.UPGRADE);
		if (sequence != null) {
			return sequence.getNext();
		}
		// Create a new sequence and save it.
		sequence = new Sequence(id, initial, increment);
		session.save(sequence);
		// TODO: handle duplicates.
		return initial;
	}
}
