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
import java.util.List;

import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.Model.User;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.MessageForm;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.PatternForm;



public interface MessageListService {

	public List<MessageForm> allMessageFormList = new ArrayList<MessageForm>();

	public void add(Message message);
	
	public List<Integer> getUnreadMsg(String uid);
	
	public List<MessageForm> getUnreadedMsg(String uid);
	
	public List<MessageForm> getAllMsgForUser(User user);
	public List<MessageForm> getAllMsgForPattern(Pattern pattern);
	
	public List<MessageForm> get5Msg(List<MessageForm> allMsgList, int unreadedMsgListSize, int index);
	
	public void leerUnreadMsgList(String uid);
	
}
