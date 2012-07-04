/*******************************************************************************
 * Copyright 2012 FZI-HIWI
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/* This DAO is used to provide some functions which are used by other DAO.
 * But here only find() is usable.
 * */

package de.fzi.ALERT.actor.Dao;

import java.io.Serializable;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public abstract class AbstractDAO<T> {
	
	private Class<T> entityClass;
	
	public AbstractDAO(Class<T> entityClass){
		this.entityClass = entityClass;
	}
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected abstract SessionFactory getSessionFactory();
	
	public void create(T entity){
		sessionFactory.getCurrentSession().save(entity);
		sessionFactory.getCurrentSession().flush();
	}
	
	public void edit(T entity){
		sessionFactory.getCurrentSession().merge(entity);
	}
	
	public void remove(T entity){
		sessionFactory.getCurrentSession().delete(entity);
	}
	
	public Object find(Serializable id){
		return sessionFactory.getCurrentSession().load(entityClass,id);
	}
	
}
