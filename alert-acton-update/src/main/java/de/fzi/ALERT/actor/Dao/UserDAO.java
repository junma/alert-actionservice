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

import de.fzi.ALERT.actor.Model.Subscription;
import de.fzi.ALERT.actor.Model.User;

@Repository
@Transactional
// public class UserDAOImpl implements UserDAO {
public class UserDAO extends AbstractDAO<User> {

	public UserDAO() {
		super(User.class);
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

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers(){
		return sessionFactory.getCurrentSession()
				.createQuery("from User").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findByUserName(String userName) {
		// TODO Auto-generated method stub

		return sessionFactory.getCurrentSession()
				.createQuery("from User where Username = ?")
				.setParameter(0, userName).list();
	}

	// reurn the action list for the user
	/*
	 * public List<Action> getActionList(String username) { // TODO
	 * Auto-generated method stub User user = (User) sessionFactory
	 * .getCurrentSession() .createQuery(
	 * "FROM User as p LEFT JOIN FETCH  p.actionCollection WHERE p.username=?")
	 * .setParameter(0, username).uniqueResult(); return new
	 * ArrayList<Action>(user.getActiontbCollection()); }
	 */

	@SuppressWarnings("unchecked")
	public void modifyMessageAccount(String messageAccount, String uid) {
		
		List<User> userList = new ArrayList<User>();
		userList = sessionFactory.getCurrentSession()
				.createQuery("from User where uid = ?")
				.setParameter(0, uid).list();
		if(userList.size()>0){
			User user = userList.get(0);
			user.setMessageAccount(messageAccount);
			sessionFactory.getCurrentSession().saveOrUpdate(user);
		}else{
			
		}
		
	}

	@SuppressWarnings("unchecked")
	public void modifyUnreadMsg(String unreadMsg, String uid) {
		List<User> userList = new ArrayList<User>();
		userList = sessionFactory.getCurrentSession()
				.createQuery("from User where uid = ?")
				.setParameter(0, uid).list();
		if(userList.size()>0){
			User user = userList.get(0);
			user.setUnreadmsg(unreadMsg);
			sessionFactory.getCurrentSession().saveOrUpdate(user);
		}
	}

	@SuppressWarnings("unchecked")
	public String getUnreadMsg(String uid) {
		List<User> userList = new ArrayList<User>();
		userList = sessionFactory.getCurrentSession()
				.createQuery("from User where uid = ?")
				.setParameter(0, uid).list();
		if(userList.size()>0){
			return userList.get(0).getUnreadmsg();
		}else{
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<User> findUserByUserid(String uID) {
		return sessionFactory.getCurrentSession()
		.createQuery("from User where uid = ?")
		.setParameter(0, uID).list();
	}
	public void addUser(User user){
		this.create(user);
		
		}

}
