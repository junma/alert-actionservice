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
package de.fzi.ALERT.actor.ActionActuator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fzi.ALERT.actor.Dao.MessageDAO;
import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Dao.UserDAO;
import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.Model.User;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.MessageForm;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.PatternForm;

@Service
public class MessageListServiceImpl implements MessageListService {

	private MessageDAO messageDAO;

	@Autowired
	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	private PatternDAO patternDAO;

	@Autowired
	public void setPatternDAO(PatternDAO patternDAO) {
		this.patternDAO = patternDAO;
	}

	private UserDAO userDAO;

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void add(Message message) {
		// TODO Auto-generated method stub
	}

	public List<Integer> getUnreadMsg(String uid) {
		// TODO Auto-generated method stub
		String unreadMsg = userDAO.getUnreadMsg(uid);
		String[] unreadMsgStringArray = unreadMsg.split(",");
		int size = unreadMsgStringArray.length;
		List<Integer> unreadMsgList = new ArrayList<Integer>();

		if (size > 1) {
			for (int i = 1; i < size; i++) {
				String num = unreadMsgStringArray[i];
				unreadMsgList.add(Integer.parseInt(num));
			}
		}
		return unreadMsgList;
	}

	public List<MessageForm> getUnreadedMsg(String uid) {
		// TODO Auto-generated method stub
		List<Integer> unreadMsgIdList = this.getUnreadMsg(uid);
		List<MessageForm> unreadedMsgList = new ArrayList<MessageForm>();
		int size = unreadMsgIdList.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				MessageForm messageForm = new MessageForm();
				Message message = messageDAO
						.findByMsgId(unreadMsgIdList.get(i));

				if (null != message) {
					messageForm.setMessageContent(message.getContent());
					messageForm.setMessageDate(message.getMsgDate());
					messageForm.setMessageId(message.getMsgID());
					messageForm.setPatternName(message.getPatternId()
							.getPatternName());
					messageForm.setStatus(true);
					unreadedMsgList.add(messageForm);
				}

			}
		}

		this.leerUnreadMsgList(uid);
		return unreadedMsgList;
	}

	public List<MessageForm> getAllMsgForUser(User user) {
		// TODO Auto-generated method stub
		List<Integer> unreadMsgList = this.getUnreadMsg(user.getUid());
		int size = unreadMsgList.size();
		int lastestMsgId = 0;
		if (size > 0) {
			lastestMsgId = unreadMsgList.get(0);
		}

		List<Message> msgList = messageDAO.findByUser(user, lastestMsgId);

		List<MessageForm> msgFormList = new ArrayList<MessageForm>();
		int msgsize = msgList.size();
		if (msgsize > 0) {
			for (int i = 0; i < msgsize; i++) {
				MessageForm messageForm = new MessageForm();
				Message message = msgList.get(i);
				messageForm.setMessageContent(message.getContent());
				messageForm.setMessageDate(message.getMsgDate());
				messageForm.setMessageId(message.getMsgID());
				messageForm.setPatternName(message.getPatternId()
						.getPatternName());
				messageForm.setStatus(false);
				msgFormList.add(messageForm);
			}
		}

		return msgFormList;
	}
	public List<MessageForm> getAllMsgForPattern(Pattern pattern) {
		// TODO Auto-generated method stub
		
		List<Message> msgList = messageDAO.findByPatternId(pattern.getPatternID().toString());

		List<MessageForm> msgFormList = new ArrayList<MessageForm>();
		int msgsize = msgList.size();
		if (msgsize > 0) {
			for (int i = 0; i < msgsize; i++) {
				MessageForm messageForm = new MessageForm();
				Message message = msgList.get(i);
				messageForm.setMessageContent(message.getContent());
				messageForm.setMessageDate(message.getMsgDate());
				messageForm.setMessageId(message.getMsgID());
				messageForm.setPatternName(message.getPatternId()
						.getPatternName());
				messageForm.setStatus(false);
				msgFormList.add(messageForm);
			}
		}

		return msgFormList;
	}
	public List<MessageForm> get5Msg(List<MessageForm> allMsgList,
			int unreadedMsgListSize, int index) {
		// TODO Auto-generated method stub
		List<MessageForm> showedMsgList = new ArrayList<MessageForm>();
		int beginId = index * 5 + 1;
		int endId = beginId + 4;
		if (allMsgList.size() > 0) {
			for (int i = 0; i < unreadedMsgListSize; i++) {
				allMsgList.get(i).setStatus(true);
			}

			if (allMsgList.size() <= endId) {
				for (int i = beginId; i <= allMsgList.size(); i++) {
					showedMsgList.add(allMsgList.get(i - 1));
				}
			} else {
				for (int i = beginId; i <= endId; i++) {
					showedMsgList.add(allMsgList.get(i - 1));
				}
			}
		}

		return showedMsgList;
	}

	public void leerUnreadMsgList(String uid) {
		// TODO Auto-generated method stub
		userDAO.modifyUnreadMsg("0", uid);
	}

}
