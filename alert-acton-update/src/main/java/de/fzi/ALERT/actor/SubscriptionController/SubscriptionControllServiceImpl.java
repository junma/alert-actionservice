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
package de.fzi.ALERT.actor.SubscriptionController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fzi.ALERT.actor.Dao.ActionTypeDAO;
import de.fzi.ALERT.actor.Dao.MessageDAO;
import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Dao.SubscriptionDAO;
import de.fzi.ALERT.actor.Dao.UserDAO;
import de.fzi.ALERT.actor.Model.ActionType;
import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.Model.Subscription;
import de.fzi.ALERT.actor.Model.User;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.PatternForm;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.Preferences;

@Service
public class SubscriptionControllServiceImpl implements
		SubscriptionControllService {

	private SubscriptionDAO subscriptionDAO;

	@Autowired
	public void setSubscriptionDAO(SubscriptionDAO subscriptionDAO) {
		this.subscriptionDAO = subscriptionDAO;
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

	private ActionTypeDAO actiontypeDAO;

	@Autowired
	public void setActiontypeDAO(ActionTypeDAO actionTypeDAO) {
		this.actiontypeDAO = actionTypeDAO;
	}

	private MessageDAO messageDAO;

	@Autowired
	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	public List<PatternForm> getSubscriptionFormList(String uid) {
		// TODO Auto-generated method stub

		List<Subscription> subscriptionList = new ArrayList<Subscription>();
		List<PatternForm> patternFormList = new ArrayList<PatternForm>();

		List<Pattern> patternList = patternDAO.listPattern();
		int patternListSize = patternList.size();
		if (patternListSize > 0) {
			for (int i = 0; i < patternListSize; i++) {
				Pattern pattern = patternList.get(i);
				if (pattern.getStatus() == 2) {
					patternDAO.remove(patternList.get(i));
					patternList.remove(i);
					patternListSize--;
				} else if (pattern.getStatus() == 1) {
					pattern.setStatus(0);
					patternDAO.edit(pattern);
				}
			}
		}
		patternListSize = patternList.size();

		if (patternListSize > 0) {
			for (int i = 0; i < patternListSize; i++) {

				Pattern pattern = patternList.get(i);
				String patternId = pattern.getPatternID();
				String patternName = pattern.getPatternName();
				User user = userDAO.findUserByUserid(uid).get(0);
				boolean status;
				List<String> actionTypeList = new ArrayList<String>();
				subscriptionList = subscriptionDAO
						.findSubscriptionForUserAndPattern(pattern, user);

				if (!subscriptionList.isEmpty()) {
					status = true;
					int actionListSize = subscriptionList.size();
					for (int j = 0; j < actionListSize; j++) {
						actionTypeList.add(subscriptionList.get(j).getAction()
								.getActionName());
					}
				} else {
					status = false;
				}

				patternFormList.add(new PatternForm(patternName, patternId,
						status, actionTypeList));
			}
		}

		return patternFormList;
	}

	public List<Subscription> getSubscriptionList(String uid) {
		// TODO Auto-generated method stub
		User user = userDAO.findUserByUserid(uid).get(0);
		return subscriptionDAO.listAllByUser(user);
	}

	public void addSubscription(String patternId, String uid,
			String actionTypeName) {
		// TODO Auto-generated method stub

		Subscription action = new Subscription();

		Pattern pattern = patternDAO.findById(patternId);
		User user = userDAO.findUserByUserid(uid).get(0);
		ActionType actionType = actiontypeDAO
				.findActionTypeByName(actionTypeName);

		action.setAction(actionType);
		action.setDate(new Date());
		action.setPatternID(pattern);
		action.setUid(user);
		action.setKeyID(1);

		subscriptionDAO.addSubscription(action);

	}

	public void deleteSubscription(String patternId, String uid,
			String actionType) {
		// TODO Auto-generated method stub
		Pattern pattern = patternDAO.findById(patternId);
		User user = userDAO.findUserByUserid(uid).get(0);
		Subscription action = subscriptionDAO.findUnitSubscription(pattern,
				user, actionType);
		subscriptionDAO.remove(action);
	}

	public void updateSubscription(String uid, PatternForm patternform) {
		// TODO Auto-generated method stub
		String patternId = patternform.getPatternId();
		User user = userDAO.findUserByUserid(uid).get(0);
		Pattern pattern = patternDAO.findById(patternId);

		List<String> actionTypeList = patternform.getActionList();

		List<Subscription> actionList = new ArrayList<Subscription>();
		actionList = subscriptionDAO.findSubscriptionForUserAndPattern(pattern,
				user);

		boolean WebMessageSubed = false;
		if (patternform.isStatus()) {
			int actionTypeListSize = actionTypeList.size();

			for (int i = 0; i < actionTypeListSize; i++) {
				if (actionTypeList.get(i).equals("WebMessage")) {
					WebMessageSubed = true;
				}
			}
		}

		if (!actionList.isEmpty()) {

			for (int i = 0; i < actionList.size(); i++) {

				if (actionList.get(i).getAction().getActionName()
						.equals("WebMessage")
						&& WebMessageSubed) {

				} else if (actionList.get(i).getAction().getActionName()
						.equals("WebMessage")
						&& !WebMessageSubed) {
					subscriptionDAO.deleteSubscription(actionList.get(i));
				} else {

					subscriptionDAO.deleteSubscription(actionList.get(i));
					actionList.remove(i);
					i--;
				}
			}
		}

		if (patternform.isStatus()) {
			int actionTypeListSize = actionTypeList.size();
			for (int i = 0; i < actionTypeListSize; i++) {
				if (actionTypeList.get(i).equals("WebMessage")) {
					if (actionList.size() == 0) {
						this.addSubscription(patternId, uid, "WebMessage");
					}

				} else {
					this.addSubscription(patternId, uid,
							actionTypeList.get(i));
				}
			}
		}

	}

	public void updatePatternFormList(List<PatternForm> patternformList,
			PatternForm patternform) {
		// TODO Auto-generated method stub
		int patternformListSize = patternformList.size();

		if (patternformListSize >= 0) {
			for (int i = 0; i < patternformListSize; i++) {
				if (patternformList.get(i).getPatternId()
						.equals(patternform.getPatternId())) {
					patternformList.get(i).setStatus(patternform.isStatus());
					patternformList.get(i).setActionList(
							patternform.getActionList());

				}

			}
		}

	}

	public List<String> changeToPatternForm(Preferences preferences) {
		// TODO Auto-generated method stub
		List<String> actionList = new ArrayList<String>();

		return actionList;
	}

	@SuppressWarnings("unchecked")
	public PatternForm createNewPatternForm(String patternId,
			Preferences preferences) {
		// TODO Auto-generated method stub

		Pattern pattern = patternDAO.findById(patternId);
		PatternForm patternform = new PatternForm();

		patternform.setPatternName(pattern.getPatternName());
		patternform.setPatternId(patternId);

		if (preferences.getsubRadio().equals("subscription")) {
			patternform.setStatus(true);
			String newAction[] = preferences.getsubChBx();
			List<String> actionList = new ArrayList<String>();
			int newActionListSize = newAction.length;
			for (int i = 0; i < newActionListSize; i++) {
				actionList.add(newAction[i]);

			}
			patternform.setActionList(actionList);
		} else {
			patternform.setStatus(false);
			patternform.setActionList(new ArrayList());
		}

		return patternform;
	}

	public Preferences getSubscriptionTypeForPattern(
			List<PatternForm> patternFormList, String patternId) {
		// TODO Auto-generated method stub
		int patternFormListSize = patternFormList.size();
		List<String> actionTypeList = new ArrayList<String>();
		Preferences preferences = new Preferences();
		if (patternFormListSize >= 0) {
			for (int i = 0; i < patternFormListSize; i++) {
				if (patternFormList.get(i).getPatternId().equals(patternId)) {
					PatternForm patternform = patternFormList.get(i);
					if (patternform.isStatus()) {
						preferences.setsubRadio("subscription");
						if (!patternform.getActionList().isEmpty()) {
							actionTypeList = patternform.getActionList();
							int actionTypeListSize = actionTypeList.size();
							String[] subChBx = new String[actionTypeListSize];
							for (int j = 0; j < actionTypeListSize; j++) {
								subChBx[j] = actionTypeList.get(j);
							}
							preferences.setsubChBx(subChBx);
						}
					} else {
						preferences.setsubRadio("unsubscription");
						preferences.setsubChBx(new String[] { "" });
					}
				}
			}
		}

		return preferences;
	}

	public String preferencesToString(Preferences preferences) {
		String statusString = "#";

		if (preferences.getsubRadio().equals("subscription")) {

			String[] status = preferences.getsubChBx();
			int size = status.length;
			for (int i = 0; i < size; i++)
				statusString = statusString + status[i] + "#";
		} else {
			statusString = statusString + "unsubscribed";
		}

		return statusString;
	}

	public void modifyMessageAccount(String messageAccount, String uid) {
		// TODO Auto-generated method stub
		userDAO.modifyMessageAccount(messageAccount, uid);
	}

	public List<String> getInstantMsgReceiverList(int msgId) {
		// TODO Auto-generated method stub
		Pattern pattern = messageDAO.findPatternByMsgId(msgId);
		ActionType actiontype = new ActionType();
		actiontype.setActionID(2);
		actiontype.setActionName("InstentMessage");
		List<Subscription> userList = subscriptionDAO
				.findSubscriptionsByPatternAndAction(pattern, actiontype);
		List<String> instantMsgReceiverList = new ArrayList<String>();
		for (int i = 0; i < userList.size(); i++) {
			instantMsgReceiverList.add(userList.get(i).getUid()
					.getMessageAccount());
		}
		return instantMsgReceiverList;
	}
}
