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
package de.fzi.ALERT.actor.Dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.fzi.ALERT.actor.Model.ActionType;


@Repository
@Transactional
public class ActionTypeDAO extends AbstractDAO<ActionType> {


	public ActionTypeDAO() {
		super(ActionType.class);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	protected SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		return this.sessionFactory;
	}
	
	// return the action name by providing a actiontype id
	public String getActionTypeNameById(int id) {
		ActionType actiontype = (ActionType) this.find(id);
		if(actiontype!=null){
			return actiontype.getActionName();
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ActionType findActionTypeByName(String actionTypeName) {
		
		List<ActionType> atList = new ArrayList<ActionType>();
		atList = sessionFactory.getCurrentSession()
				.createQuery("from ActionType where ActionName = ?")
				.setParameter(0, actionTypeName).list();
		if(atList.size()>0){
			return atList.get(0);
		}else{
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public ActionType findActionTypeByID(int actiontypeID) {
		List<ActionType> atList = new ArrayList<ActionType>();
		atList = sessionFactory.getCurrentSession()
				.createQuery("from ActionType where ActionID = ?")
				.setParameter(0, actiontypeID).list();
		if(atList.size()>0){
			return atList.get(0);
		}else{
			return null;
		}
	}

}
