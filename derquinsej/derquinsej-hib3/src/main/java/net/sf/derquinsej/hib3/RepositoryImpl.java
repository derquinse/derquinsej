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

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 * Implementation of the General DAO.
 * @author Andres Rodriguez
 */
public class RepositoryImpl extends GeneralDAOImpl implements Repository {
	/**
	 * Constructs the DAO
	 * @param sessionFactory Hibernate Session factory.
	 */
	public RepositoryImpl(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * Constructs the DAO
	 */
	public RepositoryImpl() {
	}

	@Override
	public void evict(Object object) {
		super.evict(object);
	}

	@Override
	public <T> T first(Class<T> type, Query query) {
		return super.first(type, query);
	}

	@Override
	public Query getNamedQuery(String queryName) {
		return super.getNamedQuery(queryName);
	}

	@Override
	public <T> List<T> list(Class<T> type, Query query) {
		return super.list(type, query);
	}

	@Override
	public <T> List<T> list(Class<T> type, String queryName, Object value) {
		return super.list(type, queryName, value);
	}

	@Override
	public <T> T unique(Class<T> type, Query query) {
		return super.unique(type, query);
	}

	@Override
	public <T> T unique(Class<T> type, String queryName, Object value) {
		return super.unique(type, queryName, value);
	}

}
