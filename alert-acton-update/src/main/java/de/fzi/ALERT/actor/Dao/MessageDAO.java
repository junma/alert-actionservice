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
import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.Model.User;

@Repository
@Transactional
public class MessageDAO extends AbstractDAO<Message> {

	public MessageDAO() {
		super(Message.class);
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

	// list all the messages from message table
	@SuppressWarnings("unchecked")
	public List<Message> listMessage() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from Message")
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<Message> findByPatternId(String patternId) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession()
				.createQuery("from Message where PATTERNID = ?")
				.setParameter(0, patternId).list();
	}

	@SuppressWarnings("unchecked")
	public List<Message> findByUser(User user, int msgId) {
		ActionType actiontype = new ActionType();
		actiontype.setActionID(3);
		actiontype.setActionName("WebMessage");
		if (msgId == 0) {
			return sessionFactory
					.getCurrentSession()
					.createQuery(
							"select m from Message m, Subscription a where m.patternId = a.patternID and"
									+ " a.date < m.msgDate and a.uid = ? "
									+ "and a.actiontype = ? ORDER BY m.msgDate DESC")
					.setParameter(0, user).setParameter(1, actiontype).list();
		} else {
			return sessionFactory
					.getCurrentSession()
					.createQuery(
							"select m from Message m, Subscription a where m.patternId = a.patternID and"
									+ " a.date < m.msgDate and m.msgID <= "
									+ msgId
									+ " and a.uid = ? "
									+ "and a.actiontype = ? ORDER BY m.msgDate DESC")
					.setParameter(0, user).setParameter(1, actiontype).list();
		}

	}

	@SuppressWarnings("unchecked")
	public Pattern findPatternByMsgId(int msgId) {

		List<Message> msgList = new ArrayList<Message>();
		msgList = sessionFactory.getCurrentSession()
				.createQuery("from Message where msgID = ?")
				.setParameter(0, msgId).list();

		if (msgList.size() > 0) {
			return msgList.get(0).getPatternId();
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public Message findByMsgId(int megId) {
		
		List<Message> msgList = new ArrayList<Message>();
		msgList = sessionFactory.getCurrentSession()
				.createQuery("from Message where msgID = ?")
				.setParameter(0, megId).list();

		if (msgList.size() > 0) {
			return msgList.get(0);
		} else {
			return null;
		}
	}

}
