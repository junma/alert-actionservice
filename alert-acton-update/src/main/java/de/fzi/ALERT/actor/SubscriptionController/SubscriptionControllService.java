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

import java.util.List;

import de.fzi.ALERT.actor.Model.Subscription;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.PatternForm;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.Preferences;



public interface SubscriptionControllService {	
	
	public List<Subscription> getSubscriptionList(String uid);
	
	public List<PatternForm> getSubscriptionFormList(String uid);
	
	public Preferences getSubscriptionTypeForPattern(List<PatternForm> patternFormList , String patternId);
	
	public void addSubscription(String patternId , String uid , String actionType);
	
	public void deleteSubscription(String patternId , String uid , String actionType);
	
	public void updateSubscription(String uid, PatternForm patternform);
	
	public PatternForm createNewPatternForm(String patternId, Preferences preferences);
	
	public void updatePatternFormList(List<PatternForm> patternformList , PatternForm patternform);
	
	public List<String> changeToPatternForm(Preferences preferences);
	
	public String preferencesToString(Preferences preferences);
	
	public void modifyMessageAccount(String messageAccount, String uid);
	
	public List<String> getInstantMsgReceiverList(int msgId);
	
//	public void modifyUnreadMsg(String unReadMsg, String userName);
	
//	public List<Integer> getUnreadMsg(String userName);
}
