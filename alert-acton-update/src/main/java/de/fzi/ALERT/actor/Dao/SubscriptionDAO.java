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
import java.util.Date;
import java.util.List;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.fzi.ALERT.actor.Model.ActionType;
import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.Model.Subscription;
import de.fzi.ALERT.actor.Model.User;

@Repository
@Transactional
public class SubscriptionDAO extends AbstractDAO<Subscription> {

	public SubscriptionDAO() {
		super(Subscription.class);
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

	// return all the users which have the same name
	@SuppressWarnings("unchecked")
	public List<Subscription> listAllByUser(User user) {

		return sessionFactory.getCurrentSession()
				.createQuery("from Subscription where uid = ?").setParameter(0, user)
				.list();

	}

	// list the actions of the pattern for the user
	@SuppressWarnings("unchecked")
	public List<Subscription> findSubscriptionForUserAndPattern(Pattern pattern, User user) {
		return sessionFactory.getCurrentSession()
				.createQuery("from Subscription where uid = ? and patternID = ?")
				.setParameter(0, user).setParameter(1, pattern).list();
	}

	public Subscription findUnitSubscription(Pattern pattern, User user, String subscriptionName) {
		List<Subscription> subscriptionList = this.findSubscriptionForUserAndPattern(pattern,
				user);
		Subscription subscription = new Subscription();

		if (!subscriptionList.isEmpty()) {
			int actionListSize = subscriptionList.size();

			for (int i = 0; i < actionListSize; i++) {
				if (subscriptionList.get(i).getAction().getActionName()
						.equals(subscriptionName)) {
					subscription = subscriptionList.get(i);
				}
			}
		}
		return subscription;
	}

	public void deleteSubscription(Subscription subscription) {
		if (null != subscription) {
			sessionFactory.getCurrentSession().delete(subscription);
		}
	}

	public void addSubscription(Subscription subscription) {
		this.create(subscription);
	}

	/*
	 * Find users with pattern
	 */
	public List<User> findUsersWithPattern(Pattern pattern) {
		@SuppressWarnings("unchecked")
		List<User> userList = sessionFactory
				.getCurrentSession()
				.createQuery(
						"select distinct uid from Subscription where patternID = ? ")
				.setParameter(0, pattern).list();
		return userList;
	}
	
	/*
	 * find Actions with pattern
	 */
	public List<Subscription> findSubscriptionsWithPattern(Pattern pattern){
		@SuppressWarnings("unchecked")
		List<Subscription> subscriptionList = sessionFactory.getCurrentSession()
				.createQuery("from Subscription where patternID = ?")
				.setParameter(0, pattern).list();
		return subscriptionList;
	}

	public List<Subscription> findSubscriptionsByPatternAndAction(Pattern pattern, ActionType actionType) {
		@SuppressWarnings("unchecked")
		List<Subscription> subscriptionList = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Subscription where patternID = ? and actiontype = ?")
				.setParameter(0, pattern).setParameter(1, actionType).list();
		return subscriptionList;
	}

}
