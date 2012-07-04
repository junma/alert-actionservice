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

import org.jivesoftware.smack.XMPPException;

public class InstantMsgService {

	public static void sendMsg(ChatClient c, String msgContent, String To)
		    throws XMPPException
	{
	    org.jivesoftware.smack.XMPPConnection.DEBUG_ENABLED = false;
	
	    c.sendMessage(msgContent, To);
	
	    System.out.println("An announce Instant Message has been send to " + To);
	}

}
