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
/**
 * 
 */
package de.fzi.ALERT.actor.ActionActuator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;


public class ChatClient implements MessageListener {
	XMPPConnection connection;

	public void login(String userName, String password) throws XMPPException {
		// You have to put this code before you login
		SASLAuthentication.supportSASLMechanism("PLAIN", 0);
		ConnectionConfiguration config = new ConnectionConfiguration(
				"talk.google.com", 5222, "gmail.com");
		connection = new XMPPConnection(config);

		connection.connect();
		connection.login(userName, password);
	}

	public void sendMessage(String message, String to) throws XMPPException {
		Chat chat = connection.getChatManager().createChat(to, this);
		chat.sendMessage(message);
	}

	public void displayBuddyList() {
		Roster roster = connection.getRoster();
		Collection entries = roster.getEntries();

		System.out.println("\n\n" + entries.size() + " buddy(ies):");
		Iterator i = entries.iterator();

		while (i.hasNext()) {
			RosterEntry r = (RosterEntry) i.next();
			System.out.println(r.getUser());
		}
	}

	public void disconnect() {
		connection.disconnect();
	}

	public void processMessage(Chat chat, Message message) {
		if (message.getType() == Message.Type.chat)
			System.out.println(chat.getParticipant() + " says: "
					+ message.getBody());
	}

	public static void main(String args[]) throws XMPPException, IOException {
		// declare variables
		ChatClient c = new ChatClient();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String msg;

		// turn on the enhanced debugger
		XMPPConnection.DEBUG_ENABLED = true;

		// provide your login information here
		c.login("hiwi.fzi@googlemail.com", "Karlsruhe");

		c.displayBuddyList();
		System.out.println("-----");
		System.out.println("Enter your message in the console.");
		
		System.out.println("-----\n");

		while (!(msg = br.readLine()).equals("bye")) {
			// your buddy's gmail address goes here
//			c.sendMessage(msg, "biyan727@gmail.com");
			c.sendMessage(msg, "shenwei.zheng.kit@googlemail.com");
		}

		c.disconnect();
		System.exit(0);
	}
}
