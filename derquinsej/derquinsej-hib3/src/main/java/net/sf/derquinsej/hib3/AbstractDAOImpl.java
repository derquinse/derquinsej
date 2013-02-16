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
package net.sf.derquinsej.hib3;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.common.base.Preconditions;

/**
 * Abstract implementation for general and generic DAO.
 * @author Andres Rodriguez
 */
public abstract class AbstractDAOImpl implements DAO {
	/** Hibernate Session factory. */
	private SessionFactory sessionFactory;

	private static SessionFactory check(SessionFactory sessionFactory) {
		return checkNotNull(sessionFactory, "A session factory must be provided.");
	}

	/**
	 * Constructs the DAO
	 * @param sessionFactory Hibernate Session factory.
	 */
	public AbstractDAOImpl(final SessionFactory sessionFactory) {
		this.sessionFactory = check(sessionFactory);
	}

	/**
	 * Constructs the DAO
	 */
	public AbstractDAOImpl() {
	}

	/**
	 * Sets the session factory to use.
	 * @param sessionFactory Hibernate session factory.
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Returns the current session.
	 * @return The current session.
	 */
	protected final Session getSession() {
		Preconditions.checkState(sessionFactory != null, "A session factory must have been provided");
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.hib3.DAO#clear()
	 */
	public void clear() {
		getSession().clear();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.hib3.DAO#flush()
	 */
	public void flush() {
		getSession().flush();

	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.hib3.DAO#sync()
	 */
	public void sync() {
		final Session s = getSession();
		s.flush();
		s.clear();
	}
	
	/**
	 * Remove the provided instance from the session cache.
	 * @param object The persistent instance to remove.
	 */
	protected void evict(Object object) {
		getSession().evict(object);
	}

	/**
	 * Returns a externally defined named query.
	 * @param queryName Query name.
	 * @return The requested query.
	 */
	protected Query getNamedQuery(String queryName) {
		Preconditions.checkNotNull(queryName);
		return getSession().getNamedQuery(queryName);
	}

	/**
	 * Returns the results of a query as a list of a specified class.
	 * @param type Entity type.
	 * @param query Query to execute.
	 * @return The results.
	 */
	protected <T> List<T> list(Class<T> type, Query query) {
		Preconditions.checkNotNull(query);
		@SuppressWarnings("unchecked")
		final List<T> list = query.list();
		return list;
	}

	/**
	 * Returns the first result of a query returning instances of a specified
	 * class.
	 * @param type Entity type.
	 * @param query Query to execute.
	 * @return The first result or {@code null} if there are no results.
	 */
	protected <T> T first(Class<T> type, Query query) {
		final List<T> list = list(type, query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * Returns the unique result of a query returning instances of a specified
	 * class.
	 * @param type Entity type.
	 * @param query Query to execute.
	 * @return The unique result or {@code null} if there are no results.
	 * @throws NonUniqueResultException if there are more than one result.
	 */
	protected <T> T unique(Class<T> type, Query query) {
		Preconditions.checkNotNull(query);
		@SuppressWarnings("unchecked")
		final T result = (T) query.uniqueResult();
		return result;
	}
	
	/**
	 * Returns the results of a named query with a single parameter as a list of a specified class.
	 * @param type Entity type.
	 * @param queryName Query name.
	 * @param value Non-null parameter value.
	 * @return The results.
	 */
	protected <T> List<T> list(Class<T> type, String queryName, Object value) {
		Preconditions.checkNotNull(queryName);
		Preconditions.checkNotNull(value);
		return list(type, getNamedQuery(queryName).setParameter(0, value));
	}
	

	/**
	 * Returns the unique result of a named query with a single parameter returning instances of a specified
	 * class.
	 * @param type Entity type.
	 * @param queryName Query name.
	 * @param value Non-null parameter value.
	 * @return The unique result or {@code null} if there are no results.
	 * @throws NonUniqueResultException if there are more than one result.
	 */
	protected <T> T unique(Class<T> type, String queryName, Object value) {
		Preconditions.checkNotNull(queryName);
		Preconditions.checkNotNull(value);
		return unique(type, getNamedQuery(queryName).setParameter(0, value));
	}
	
}
